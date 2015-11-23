package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.article.ArticleDocument;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.Directory;

public class BasicStatisticsService implements CorpusStatisticsService {

  private static final Logger log = Logger.getLogger(BasicStatisticsService.class);

  private CorpusIndexingService corpusIndexingService;

  public Map<String, Integer> getTermFrequency(Corpus corpus, int numberOfTerms) {
    HashMap<String, Integer> termFrequencyMap = new HashMap<String, Integer>();
    Directory directory = corpusIndexingService.getIndex(corpus);

    try {
      IndexReader reader = IndexReader.open(directory);
      if (reader == null) {
        log.error("Could not instantiate reader for directory");
      }
      log.info("retreaved index with " + reader.numDocs() + " documents");
      for (int i = 0; i < reader.numDocs(); i++) {
        TermFreqVector vector = reader.getTermFreqVector(i, ArticleDocument.CONTENTS_FIELD);
        if (vector == null) {
          log.error("Could not retrieve term frequency vector for document " + reader.document(i).getField(ArticleDocument.PATH_FIELD));
        } else {
          String terms[] = vector.getTerms();
          int[] frequencies = vector.getTermFrequencies();
          for (int x = 0; x < terms.length; x++) {
            String term = terms[x];
            int frequency = frequencies[x];
            if (termFrequencyMap.get(term) == null) {
              termFrequencyMap.put(term, frequency);
            } else {
              int f = termFrequencyMap.remove(term);
              f += frequency;
              termFrequencyMap.put(term, f);
            }
          }
        }
      }

      if (!termFrequencyMap.isEmpty()) {

        Map<String, Integer> sorted = sortByValue(termFrequencyMap);
        Map<String, Integer> output = new LinkedHashMap<String, Integer>();
        Set<String> keys = sorted.keySet();
        int count = 0;
        for (String key : keys) {
          if (count < numberOfTerms) {
            output.put(key, sorted.get(key));
            count++;
          } else {
            break;
          }
        }
        return output;
      }
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
    return null;
  }

  public int getWordCount(Corpus corpus) {
    int wordCount = 0;
    try {
      for (Article article : corpus.getArticles()) {
        String text = article.getText();
        if (text != null) {
          wordCount += article.getText().split(" ").length;
        }
      }
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
    return wordCount;
  }

  @SuppressWarnings("rawtypes")
  private static Map<String, Integer> sortByValue(Map<String, Integer> map) {

    
    List<Map.Entry> list = new LinkedList<Map.Entry>(map.entrySet());

    Collections.sort(list, new Comparator<Map.Entry>() {
      public int compare(Map.Entry o1, Map.Entry o2) {
        
        int result = ((Comparable) o1.getValue()).compareTo(o2.getValue());
        if (result != 0) {
          return -result;
        }
        return 0;
      }
    });

    Map<String, Integer> result = new LinkedHashMap<String, Integer>();

    for (Entry entry : list) {
      result.put((String) entry.getKey(), (Integer) entry.getValue());
    }
    return result;
  }

  public CorpusIndexingService getCorpusIndexingService() {
    return corpusIndexingService;
  }

  public void setCorpusIndexingService(CorpusIndexingService corpusIndexingService) {
    this.corpusIndexingService = corpusIndexingService;
  }

}
