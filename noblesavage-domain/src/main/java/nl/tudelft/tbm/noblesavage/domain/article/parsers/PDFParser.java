package nl.tudelft.tbm.noblesavage.domain.article.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.http.SavageHttpClient;
import nl.tudelft.tbm.noblesavage.domain.semantics.TextService;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFParser implements ArticleParser {

    private static final Logger log = Logger.getLogger(PDFParser.class.getName());
    private static final boolean USE_FORCE = true;
    private static final String ENCODING = "UTF-8";

    public String getText(Article article) {

        log.info("Will parse article: " + article.getUrl());
        HttpEntity entity = null;
        try {
            HttpGet httpget = new HttpGet(article.getUrl());
            HttpResponse response = SavageHttpClient.getClient().execute(httpget);
            entity = response.getEntity();

            if (entity != null) {
                Header contentType = entity.getContentType();
                if (contentType != null) {
                    if (contentType.getValue().equalsIgnoreCase("text/html")) {
                        InputStream is = entity.getContent();
                        StringBuilder sb = new StringBuilder();
                        String line;
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                            while ((line = reader.readLine()) != null) {
                                sb.append(line).append("\n");
                            }
                        } catch (Exception err) {
                            log.error(err.getMessage(), err);
                        }
                        log.warn("Was expecting PDF, got HTML instead with content :\n" + sb.toString());
                    } else {
                        InputStream is = entity.getContent();
                        StringWriter writer = pdfToTextWriter(is);
                        entity.consumeContent();
                        String text = writer.toString();
                        if (text != null) {
                            return TextService.doAll(scrubEvilChars(text));
                        }
                    }
                }
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        if (entity != null) {
            try {
                entity.consumeContent();
            } catch (IOException err) {
                log.error(err.getMessage(), err);
            }
        }
        return null;
    }

    public StringWriter pdfToTextWriter(InputStream is) throws IOException {
        if (is != null) {
            PDDocument document = null;
            StringWriter writer = new StringWriter();
            try {
                document = PDDocument.load(is, USE_FORCE);
                PDFTextStripper stripper = new PDFTextStripper(ENCODING);
                stripper.writeText(document, writer);
                log.info("Parsed PDF");
            } catch (IOException err) {
                log.error(err.getMessage(), err);
            } finally {
                if (document != null) {
                    document.close();
                }
            }
            return writer;
        }
        return null;
    }

    public String scrubEvilChars(String content) {
        StringBuilder sbTemp = new StringBuilder(content.length());
        for (char currentChar : content.toCharArray()) {
            if ((currentChar > 7) && (currentChar < 127)) {
                sbTemp.append(currentChar);
            }
        }
        return sbTemp.toString();
    }

}