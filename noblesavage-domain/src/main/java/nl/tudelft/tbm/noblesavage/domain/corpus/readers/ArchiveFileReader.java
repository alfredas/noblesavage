package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.ReaderParameter;

import org.apache.log4j.Logger;

public class ArchiveFileReader implements CorpusReader {

    private static final Logger log = Logger.getLogger(ArchiveFileReader.class);

    private static final ReaderParameter[] PARAMETERS = {
            new ReaderParameter("name", "Name", "Name  of the corpus", "text"),
            new ReaderParameter(
                    "fileUrl",
                    "Upload file (zip)",
                    "Upload a zip file containing eitehr pdf, html or txt files. State the date or each file in the file name in the format dd-mm-yyyy",
                    "file") };

    public static final String READER_NAME = "Upload Archive (zip)";

    private static String projectFilesLocation = "/tmp/noblesavage-files/";

    @Override
    public Corpus getCorpus(HashMap<String, String> parameters) {
        if (!parameters.containsKey("fileUrl")) {
            log.error("fileUrl not found");
            return null;
        }

        String fileUrl = projectFilesLocation + parameters.get("fileUrl");
        log.info("fileUrl: " + fileUrl);

        List<Article> articles = new ArrayList<Article>();

        File archive = new File(fileUrl);

        for (File f : Unzipper.unzipProjectFile(archive)) {
            String name = f.getName();
            String url = f.getAbsolutePath();
            Date date = matchDate(name);
            Article article = new Article(url, name, "", null, date, null);
            articles.add(article);
        }

        Corpus corpus = new Corpus(parameters.get("name"), "from archive " + archive.getName(), this.getReaderName(), articles, null);
        corpus.setLang(parameters.get("language"));
        return corpus;
    }

    private Date matchDate(String fileName) {
        Pattern datePattern = Pattern.compile("(\\d{2})-(\\d{2})-(\\d{4})");
        Matcher dateMatcher = datePattern.matcher(fileName);
        if (dateMatcher.find()) {
            Calendar calendar = Calendar.getInstance();
            int day = Integer.parseInt(dateMatcher.group(1));
            int month = Integer.parseInt(dateMatcher.group(2));
            int year = Integer.parseInt(dateMatcher.group(3));
            calendar.set(year, month, day);
            return calendar.getTime();
        }
        return null;
    }

    @Override
    public List<ReaderParameter> getParameters() {
        return Arrays.asList(PARAMETERS);
    }

    public String getReaderName() {
        return READER_NAME;
    }

}
