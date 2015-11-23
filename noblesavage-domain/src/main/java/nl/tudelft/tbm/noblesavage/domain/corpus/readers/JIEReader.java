package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.http.SavageHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JIEReader extends DefaultHandler {
    private static final Logger log = Logger.getLogger(JIEReader.class.getName());

    private static final String[] PARAMETER_NAMES = { "startYear", "endYear" };
    public static final String READER_NAME = "Journal of Industrial Ecology";

    private final SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy h:mm");

    private ArrayList<Article> results;

    private boolean readingDivItem;
    private boolean readingArticleHeading;
    private boolean readArticleHeading;
    private boolean readingArticleLinks;
    private boolean readingArticleTitle;

    private String articleURLHTML = null;
    private String articleURLPDF = null;
    private String articleHeadingText = null;
    private Date articleDate = null;
    private String articleTitle = null;

    private String serverUrl = null;

    ArrayList<String> issueURLs = null;

    public Corpus getCorpus(HashMap<String, String> parameters) {

        ArrayList<Article> articles = new ArrayList<Article>();

        issueURLs = new ArrayList<String>();

        int startYear = Integer.parseInt(parameters.get("startYear"));
        int endYear = Integer.parseInt(parameters.get("endYear"));
        String[] urls = createURLs(startYear, endYear);

        for (String url : urls) {
            parseURI(url);
        }

        for (String url : issueURLs) {
            log.info("about to parse issue: " + url);
            parseURI(url);
            log.info("found " + results.size() + " articles in issue");
            articles.addAll(results);
        }
        Corpus corpus = new Corpus(READER_NAME + " from " + startYear + " to " + endYear, urls[0], READER_NAME, articles, null);
        return corpus;
    }

    private void parseURI(String url) {
        results = new ArrayList<Article>();

        readingDivItem = false;
        readingArticleHeading = false;
        readArticleHeading = false;
        readingArticleLinks = false;
        readingArticleTitle = false;

        articleURLHTML = null;
        articleURLPDF = null;
        articleTitle = null;
        articleDate = null;
        articleHeadingText = null;

        serverUrl = url.substring(0, url.indexOf("/", 8));

        try {
            log.info("Will parse URL: " + url);
            HttpGet httpget = new HttpGet(url);
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
        } finally {

        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (localName.equalsIgnoreCase("div")) {
            if ((attributes.getValue("class") != null) && attributes.getValue("class").equalsIgnoreCase("item")) {
                readingDivItem = true;
            }
        }

        if (localName.equalsIgnoreCase("a") && readingDivItem) {
            if (attributes.getValue("href") != null) {
                String href = attributes.getValue("href");
                issueURLs.add(serverUrl + href);
            }
        }

        if (localName.equalsIgnoreCase("p")) {
            if ((attributes.getValue("class") != null) && attributes.getValue("class").equalsIgnoreCase("article-heading")) {
                readingArticleHeading = true;
            }
            if (readArticleHeading) {
                readingArticleLinks = true;
            }
        }

        if (localName.equalsIgnoreCase("a") && readingArticleLinks) {
            if ((attributes.getValue("href") != null) && (attributes.getValue("href").indexOf("HTMLSTART") > 0)) {
                String aurl = serverUrl + attributes.getValue("href");
                articleURLHTML = aurl.replace("HTMLSTART", "main.html,ftx_abs");

            } else if ((attributes.getValue("href") != null) && (attributes.getValue("href").indexOf("PDFSTART") > 0)) {
                String href = attributes.getValue("href");
                String id = href.substring(18, href.indexOf("/PDFSTART"));
                articleURLPDF = "http://download.interscience.wiley.com/cgi-bin/fulltext?ID=" + id + "&PLACEBO=IE.pdf&mode=pdf";
            }

        }

        if (localName.equalsIgnoreCase("strong") && readingArticleHeading) {
            readingArticleTitle = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (readingArticleHeading) {
            if (articleHeadingText == null) {
                articleHeadingText = new String(ch, start, length);
            } else {
                articleHeadingText += new String(ch, start, length);
            }
        }

        if (readingArticleTitle) {
            if (articleTitle == null) {
                articleTitle = new String(ch, start, length);
            } else {
                articleTitle += new String(ch, start, length);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase("div") && readingDivItem) {
            readingDivItem = false;
        }

        if (localName.equalsIgnoreCase("strong") && readingArticleTitle) {
            readingArticleTitle = false;
        }

        if (localName.equalsIgnoreCase("p") && readingArticleHeading) {
            readingArticleHeading = false;
            readArticleHeading = true;

            if (articleHeadingText != null) {
                int start = articleHeadingText.indexOf("Published Online:") + 17;

                if ((articleTitle == null) || (articleTitle == "")) {
                    articleTitle = articleHeadingText.substring(0, start);
                }

                int end = articleHeadingText.indexOf("DOI:");
                String dateString = articleHeadingText.substring(start, end);
                dateString = dateString.trim();
                dateString = dateString.replace("  ", " ");
                try {
                    articleDate = sdf.parse(dateString);
                } catch (ParseException pe) {
                    articleDate = new Date();
                }
            }
        }

        if (localName.equalsIgnoreCase("p") && readingArticleLinks) {
            readArticleHeading = false;
            readingArticleLinks = false;

            String articleUrl = (articleURLHTML != null) ? articleURLHTML : articleURLPDF;

            Article article = new Article(articleUrl, articleTitle, "", null, articleDate, null);

            log.info("adding article with url: " + articleUrl + " title: " + articleTitle);
            results.add(article);

            articleTitle = null;
            articleURLHTML = null;
            articleURLPDF = null;
            articleDate = null;

        }

    }

    @Override
    public void endDocument() {

    }

    protected static String[] createURLs(int startYear, int endYear) {
        int len = endYear - startYear + 1;
        if (len > 0) {
            String[] urls = new String[len];
            for (int i = 0; i < len; i++) {
                urls[i] = "http://onlinelibrary.wiley.com/journal/10.1111/(ISSN)1530-9290/issues?activeYear=" + (startYear + i);
            }
            return urls;
        }
        return null;
    }

    protected static String createName(String keys, int startYear, int endYear, int numberOfArticles) {
        return keys + " from " + startYear + " to " + endYear + " #" + numberOfArticles;
    }

    public String[] getParameterNames() {
        return PARAMETER_NAMES;
    }
}
