package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.ReaderParameter;
import nl.tudelft.tbm.noblesavage.domain.http.SavageHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GoogleNewsReader extends DefaultHandler implements CorpusReader {

  private static final Logger log = Logger.getLogger(GoogleNewsReader.class);
  protected static final int RESULT_LIMIT = 10;

  private static final ReaderParameter[] PARAMETERS = {
      new ReaderParameter("numberOfArticles", "Number of articles", "Number of articles to fetch", "number"),
      new ReaderParameter("startYear", "Start year", "Articles published starting with year", "number"),
      new ReaderParameter("endYear", "End year", "Articles published before year", "number"),
      new ReaderParameter("keys", "Search keys", "Search keys for Google News search", "text")};
  public static final String READER_NAME = "Google News";

  private ArrayList<Article> results;

  private boolean readingResultTable;
  private boolean readingArticleTitle;
  private boolean readingDate;
  private boolean readSourceSpan;

  private String articleURL = null;
  private String articleDate = null;
  private String articleTitle = null;
  private String nextUrl = null;

  private String serverUrl = null;

  public Corpus getCorpus(HashMap<String, String> parameters) {

    ArrayList<Article> articles = new ArrayList<Article>();

    // parameters
    int numberOfArticles = Integer.parseInt(parameters.get("numberOfArticles"));
    int startYear = Integer.parseInt(parameters.get("startYear"));
    int endYear = Integer.parseInt(parameters.get("endYear"));
    String keys = parameters.get("keys");

    // construct url
    String url = this.createURL(keys, startYear, endYear, numberOfArticles);

    // set the next url to be parsed
    this.setNextUrl(url);

    // see how many times to go through the result list
    // so if we ask for 120 articles and the result limit is 100
    // then we'd have to advance the list twice (c = 2);
    int count = Math.max(1, (int) Math.ceil((double) numberOfArticles / RESULT_LIMIT));
    int i = 0;

    while ((this.getNextUrl() != null) && (i < count)) {
      parseURI(this.getNextUrl());
      int limit = Math.min((numberOfArticles - articles.size()), this.getResults().size());
      articles.addAll(this.getResults().subList(0, limit));
      i++;
      log.info("looped the result list: " + i + " number of articles recorded: " + articles.size() + " count is: "
          + count);
    }

    Corpus corpus = new Corpus(createName(keys, startYear, endYear, numberOfArticles), url, this.getReaderName(),
        articles, null);

    return corpus;
  }

  public String getReaderName() {
    return READER_NAME;
  }

  private void parseURI(String URI) {
    results = new ArrayList<Article>();
    readingResultTable = false;
    readingArticleTitle = false;
    readSourceSpan = false;
    readingDate = false;

    articleURL = null;
    articleTitle = null;
    articleDate = null;
    nextUrl = null;

    serverUrl = URI.substring(0, URI.indexOf("/", 8));
    log.info("will parse url: " + URI);

    try {
      HttpGet httpget = new HttpGet(URI);
      HttpResponse response = SavageHttpClient.getClient().execute(httpget);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        InputStream is = entity.getContent();
        if (is != null) {
          SAXParser sp = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
          sp.parse(is, this);
        }
        entity.consumeContent();
      }
    } catch (SAXException se) {
      log.error(se.getMessage(), se);
    } catch (IOException ie) {
      log.error(ie.getMessage(), ie);
    }

  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (localName.equalsIgnoreCase("table")) {
      if ((attributes.getValue("class") != null) && attributes.getValue("class").equalsIgnoreCase("ts")) {
        readingResultTable = true;
      }
    }

    if (localName.equalsIgnoreCase("a") && readingResultTable && (articleURL == null)) {
      if (attributes.getValue("href") != null) {
        String href = attributes.getValue("href");
        articleURL = href;
        readingArticleTitle = true;
      }
    }

    if (localName.equalsIgnoreCase("a") && attributes.getValue("id") != null
        && attributes.getValue("id").equalsIgnoreCase("pnnext")) {
      if (attributes.getValue("href") != null) {
        String href = attributes.getValue("href");
        if (href.indexOf("http://") != 0) {
          nextUrl = serverUrl + href;
        } else {
          nextUrl = href;
        }
      }
    }

    if (articleDate == null && localName.equalsIgnoreCase("span") && readingResultTable
        && (attributes.getValue("class") != null) && attributes.getValue("class").equalsIgnoreCase("f xsm")) {
      if (readSourceSpan) {
        readingDate = true;
      } else {
        readSourceSpan = true;
      }
    }

  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (readingArticleTitle) {
      if (articleTitle == null) {
        articleTitle = new String(ch, start, length);
      } else {
        articleTitle += new String(ch, start, length);
      }
    }
    if (readingDate) {
      if (articleDate == null) {
        articleDate = new String(ch, start, length);
      } else {
        articleDate += new String(ch, start, length);
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (localName.equalsIgnoreCase("a") && readingArticleTitle) {
      readingArticleTitle = false;
    }

    if (localName.equalsIgnoreCase("table") && readingResultTable) {
      readingResultTable = false;

      DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
      Date date = null;
      try {
        date = df.parse(articleDate);
      } catch (ParseException pe) {
        log.error(pe.getMessage(), pe);
      }

      Article article = new Article(articleURL, articleTitle, "", null, date, null);

      results.add(article);

      articleURL = null;
      articleTitle = null;
      articleDate = null;
    }

    if (localName.equalsIgnoreCase("span") && readingDate) {
      readingDate = false;
      readSourceSpan = false;
    }
  }

  @Override
  public void endDocument() {

  }

  private ArrayList<Article> getResults() {
    return results;
  }

  private String getNextUrl() {
    return nextUrl;
  }

  private void setNextUrl(String url) {
    this.nextUrl = url;
  }

  protected String createURL(String keys, int startYear, int endYear, int numberOfArticles) {
    int pageLimit = Math.min(RESULT_LIMIT, numberOfArticles);
    String encodedKeys = keys;
    try {
      encodedKeys = encodedKeys.replace(" ", "+");
      // encodedKeys = URLEncoder.encode(encodedKeys, "utf-8");
    } catch (Exception err) {
    }
    return "https://www.google.com/search?q=stratfor&tbm=nws&tbs=ar:1#q=" + encodedKeys
        + "&hl=en&sa=X&ei=yLG7TpPOG4TqOY2n-LsI&ved=0CBgQpwUoCw&source=lnt&tbs=cdr:1%2Ccd_min%3A1%2F1%2F" + startYear
        + "%2Ccd_max%3A1%2F1%2F" + endYear
        + "&tbm=nws&bav=on.2,or.r_gc.r_pw.r_cp.,cf.osb&fp=73b968a085b00b88&biw=1920&bih=1064";

    // return "http://news.google.com/archivesearch?as_user_ldate=" + startYear
    // + "&as_user_hdate=" + endYear + "&q=" + encodedKeys + "&scoring=a&num=" +
    // pageLimit;

  }

  protected static String createName(String keys, int startYear, int endYear, int numberOfArticles) {
    return keys + " from " + startYear + " to " + endYear + " #" + numberOfArticles;
  }

  public List<ReaderParameter> getParameters() {
    return Arrays.asList(PARAMETERS);
  }

}
