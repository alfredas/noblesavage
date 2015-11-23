package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.article.ArticleDocument;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class BasicCorpusIndexingService implements CorpusIndexingService {

    private static final Logger log = Logger.getLogger(BasicCorpusIndexingService.class.getName());
    private final SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");

    public Directory getIndex(Corpus corpus) {
        Directory indexDirectory = new RAMDirectory();
        try {
            log.info("creating index");
            Analyzer analyzer = null;
            if (corpus.getLang().equals("dutch")) {
                analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Dutch", StopWords.getStopwordsSet(StopWords.DUTCH));
                log.info("Using dutch analyzer");
            } else {
                analyzer = new SnowballAnalyzer(Version.LUCENE_30, "English", StopWords.getStopwordsSet(StopWords.ENGLISH));
                log.info("Using english analyzer");
            }

            IndexWriter writer = new IndexWriter(indexDirectory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);

            for (Article article : corpus.getArticles()) {
                if (ArticleDocument.getDocument(article) != null) {
                    writer.addDocument(ArticleDocument.getDocument(article));
                }
            }

            writer.optimize();
            writer.close();

        } catch (IOException e) {
            log.error("error indexing query " + corpus, e);
        }

        return indexDirectory;
    }

    public Directory getIndex(Corpus corpus, int fromYear, int toYear) {
        Directory indexDirectory = new RAMDirectory();
        try {
            log.info("creating index from " + fromYear + " to " + toYear);
            Analyzer analyzer = null;
            if (corpus.getLang().equals("dutch")) {
                analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Dutch", StopWords.getStopwordsSet(StopWords.DUTCH));
            } else {
                analyzer = new SnowballAnalyzer(Version.LUCENE_30, "English", StopWords.getStopwordsSet(StopWords.ENGLISH));
            }
            IndexWriter writer = new IndexWriter(indexDirectory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);

            for (Article article : corpus.getArticles()) {
                Date articleDate = article.getDateCreated();
                if (articleDate != null) {
                    int year = Integer.parseInt(simpleDateformat.format(articleDate));
                    if ((year >= fromYear) && (year < toYear)) {
                        if (ArticleDocument.getDocument(article) != null) {
                            writer.addDocument(ArticleDocument.getDocument(article));
                            log.info("Added article " + article.getTitle() + " dated " + year);
                        }
                    }
                }
            }

            writer.optimize();
            writer.close();

        } catch (IOException e) {
            log.error("error indexing query " + corpus, e);
        }

        return indexDirectory;

    }

}