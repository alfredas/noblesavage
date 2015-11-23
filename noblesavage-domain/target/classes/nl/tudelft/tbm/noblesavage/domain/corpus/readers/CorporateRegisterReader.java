package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.http.SavageHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CorporateRegisterReader extends DefaultHandler {
    private static final Logger log = Logger.getLogger(CorporateRegisterReader.class.getName());

    private static final String[] PARAMETER_NAMES = { "companyPageUrl", "companyName", "username", "password" };
    public static final String READER_NAME = "Corporate Registry";

    private static final String LOGIN = "http://www.corporateregister.com/accounts/enter.cgi";

    static SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");

    private ArrayList<Article> results;
    private String reportURL = null;
    private String serverUrl = null;
    private String currentUrl = null;
    ArrayList<String> reportURLs = null;
    private boolean loggedIn = false;

    private boolean flagReadingTD = false;
    private boolean flagReadPublishedTD = false;
    private boolean flagReadingDate = false;
    private boolean flagReadingTitle = false;
    private boolean flagReadingLink = false;

    private String articleTitle = null;
    private String tdText = null;
    private String dateText = null;
    private Date articleDate = null;
    private String linkText = null;
    private String articleUrl = null;

    private String parsedURL = null;

    private boolean login(String user, String password) {
        try {
            log.info("Logging into " + READER_NAME + " with user " + user);
            HttpPost httpPost = new HttpPost(LOGIN);
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("username", user));
            formparams.add(new BasicNameValuePair("password", password));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse response = SavageHttpClient.getClient().execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            response.getEntity().consumeContent();
            entity.consumeContent();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                log.info("Logged in with user " + user);
                return true;
            } else {
                log.error("Login returned HTTP code: " + statusCode);
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return false;
    }

    public Corpus getCorpus(HashMap<String, String> parameters) {

        reportURLs = new ArrayList<String>();

        if (!isLoggedIn()) {
            if (login(parameters.get("username"), parameters.get("password"))) {
                setLoggedIn(true);
            } else {
                log.error("failed to log in into " + READER_NAME);
                return null;
            }
        }

        ArrayList<Article> articles = new ArrayList<Article>();

        String companyUrl = parameters.get("companyPageUrl");

        parseURI(companyUrl);

        for (String reportUrl : reportURLs) {
            log.info("about to parse report: " + reportUrl);
            parseURI(reportUrl);
            articles.addAll(results);
        }

        Corpus corpus = new Corpus(READER_NAME + " for " + parameters.get("companyName"), parameters.get("companyPageUrl"), READER_NAME,
                articles, null);
        return corpus;
    }

    private void parseURI(String url) {

        parsedURL = url;

        results = new ArrayList<Article>();
        serverUrl = url.substring(0, url.indexOf("/", 8));
        currentUrl = url.substring(0, url.lastIndexOf("/"));

        // reset flags
        flagReadingTD = false;
        flagReadPublishedTD = false;
        tdText = null;
        flagReadingDate = false;
        dateText = null;
        articleDate = null;
        flagReadingTitle = false;
        articleTitle = null;
        linkText = null;
        flagReadingLink = false;
        articleUrl = null;

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
            }
            entity.consumeContent();
        } catch (SAXException se) {
            log.error(se.getMessage(), se);
        } catch (IOException ie) {
            log.error(ie.getMessage(), ie);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (localName.equalsIgnoreCase("a")) {
            if (attributes.getValue("href") != null) {
                if (attributes.getValue("href").indexOf("http://") < 0) {
                    if (attributes.getValue("href").startsWith("/")) {
                        reportURL = serverUrl + attributes.getValue("href");
                    } else {
                        reportURL = currentUrl + "/" + attributes.getValue("href");
                    }
                } else {
                    reportURL = attributes.getValue("href");
                }
            }
            flagReadingLink = true;
        }

        if (localName.equalsIgnoreCase("img")) {
            if ((attributes.getValue("src") != null) && (attributes.getValue("src").equals("/img/s_7.png"))) {
                reportURLs.add(reportURL);
            }
        }

        if (localName.equalsIgnoreCase("td")) {
            if ((attributes.getValue("width") != null) && (attributes.getValue("width").equals("160"))) {
                flagReadingTD = true;
            } else if ((attributes.getValue("width") != null) && (attributes.getValue("width").equals("360"))) {
                if (flagReadPublishedTD) {
                    flagReadingDate = true;
                    flagReadPublishedTD = false;
                }
            }
        }

        if (localName.equalsIgnoreCase("h1")) {
            flagReadingTitle = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (flagReadingTD) {
            if (tdText == null) {
                tdText = new String(ch, start, length);
            } else {
                tdText += new String(ch, start, length);
            }
        }

        if (flagReadingDate) {
            if (dateText == null) {
                dateText = new String(ch, start, length);
            } else {
                dateText += new String(ch, start, length);
            }
        }

        if (flagReadingTitle) {
            if (articleTitle == null) {
                articleTitle = new String(ch, start, length);
            } else {
                articleTitle += new String(ch, start, length);
            }
        }

        if (flagReadingLink) {
            if (linkText == null) {
                linkText = new String(ch, start, length);
            } else {
                linkText += new String(ch, start, length);
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase("td") && flagReadingTD) {
            flagReadingTD = false;
            if ((tdText != null) && tdText.trim().equalsIgnoreCase("published")) {
                flagReadPublishedTD = true;
            }
            tdText = null;
        }

        if (localName.equalsIgnoreCase("td") && flagReadingDate) {
            flagReadingDate = false;
            if (dateText.trim().length() > 0) {
                try {
                    articleDate = sdf.parse(dateText);
                } catch (Exception err) {
                    log.error("error parsing date from string: " + dateText);
                    articleDate = new Date();
                }
            }
        }
        if (localName.equalsIgnoreCase("h1")) {
            flagReadingTitle = false;
        }
        if (localName.equalsIgnoreCase("a")) {
            flagReadingLink = false;
            if ((linkText != null) && linkText.trim().equalsIgnoreCase("view pdf")) {
                articleUrl = reportURL;
            }
            linkText = null;
        }

    }

    @Override
    public void endDocument() {
        if (articleUrl != null) {
            Article article = new Article(articleUrl, articleTitle, "", null, articleDate, null);
            results.add(article);
            log.info("added an article with url: " + article.getUrl() + " and title: " + article.getTitle());
        } else {
            log.info("no articles found on " + parsedURL);
        }
    }

    protected static String createName() {
        return "";
    }

    public String[] getParameterNames() {
        return PARAMETER_NAMES;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        loggedIn = isLoggedIn;
    }

}