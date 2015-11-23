package nl.tudelft.tbm.noblesavage.domain.article.parsers;

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

public class JIEArticleParser extends DefaultHandler implements ArticleParser {

  private static final Logger log = Logger.getLogger(JIEArticleParser.class.getName());

  private String text;
  private boolean isArticleText;

  private final PDFParser pdfParser = new PDFParser();

  public String getText(Article article) {

    log.info("Will parse article: " + article.getUrl());

    text = null;
    isArticleText = false;

    try {
      // TODO: fix this to act according content-type not extension
      if (article.getUrl().endsWith("pdf")) {
        log.info("treating as pdf");
        return pdfParser.getText(article);
      } else {
        log.info("treating as html");
        HttpGet httpget = new HttpGet(article.getUrl());
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
        if (text != null) {
          return TextService.doAll(text);
        }
      }
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
    return null;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (localName.equalsIgnoreCase("div")) {
      if ((attributes.getValue("class") != null) && attributes.getValue("class").equalsIgnoreCase("articletext")) {
        isArticleText = true;
      }
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (isArticleText) {
      if (text == null) {
        text = new String(ch, start, length);
      } else {
        text += " " + new String(ch, start, length);
      }
    }
  }
}
