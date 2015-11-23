package nl.tudelft.tbm.noblesavage.domain.article.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.activation.MimetypesFileTypeMap;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.semantics.TextService;

import org.apache.log4j.Logger;

public class FileParser implements ArticleParser {

    private static final Logger log = Logger.getLogger(FileParser.class);

    @Override
    public String getText(Article article) {
        log.info("will parse article from file: " + article.getUrl());
        File file = new File(article.getUrl());
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        log.info("mime type: " + mimeType);

        if (mimeType.indexOf("text/plain") >= 0) {
            // text
            return readTextFile(file);
        } else if (mimeType.indexOf("application/pdf") >= 0 || mimeType.indexOf("application/octet-stream") >= 0) {
            // pdf
            PDFParser pdfParser = new PDFParser();
            try {
                StringWriter writer = pdfParser.pdfToTextWriter(new FileInputStream(file));
                String text = writer.toString();
                if (text != null) {
                    return TextService.doAll(pdfParser.scrubEvilChars(text));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

        } else if (mimeType.indexOf("text/html") >= 0) {
            // html
            WebPageParser webPageParser = new WebPageParser();
            try {
                return webPageParser.getText(new FileInputStream(file));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return null;
    }

    private String readTextFile(File file) {
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return contents.toString();
    }
}
