package nl.tudelft.tbm.noblesavage.domain.article.parsers;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.http.SavageHttpClient;
import nl.tudelft.tbm.noblesavage.domain.semantics.TextService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebPageParser extends DefaultHandler implements ArticleParser {

    private static final Logger log = Logger.getLogger(WebPageParser.class.getName());

    private String text;
    private boolean ignoreText;

    public String getText(Article article) {

        log.info("Will parse article: " + article.getUrl());

        text = null;
        ignoreText = false;
        /*
         * try { SAXParser sp =
         * org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
         * sp.parse(article.getUrl(), this); } catch (Exception err) {
         * log.error(err.getMessage(), err); } if (text != null) { return
         * TextService.doAll(text); } else { return null; }
         */

        try {
            HttpGet httpget = new HttpGet(article.getUrl());
            httpget.addHeader("REFERER", "http://news.google.com/archivesearch");
            HttpResponse response = SavageHttpClient.getClient().execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream is = entity.getContent();
                if (is != null) {
                    String text = getText(is);
                    entity.consumeContent();
                    return text;
                }
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return null;
    }

    public String getText(InputStream is) throws SAXException, IOException {
        SAXParser sp = org.ccil.cowan.tagsoup.jaxp.SAXParserImpl.newInstance(null);
        sp.parse(is, this);

        if (text != null) {
            return TextService.doAll(text);
        }
        return null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equalsIgnoreCase("script") || localName.equalsIgnoreCase("style")) {
            ignoreText = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase("script") || localName.equalsIgnoreCase("style")) {
            ignoreText = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!ignoreText) {
            if (text == null) {
                text = new String(ch, start, length);
            } else {
                text += " " + new String(ch, start, length);
            }
        }
    }

}
