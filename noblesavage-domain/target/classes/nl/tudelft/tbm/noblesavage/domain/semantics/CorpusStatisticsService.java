package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.util.Map;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

public interface CorpusStatisticsService {
  public Map<String, Integer> getTermFrequency(Corpus corpus, int numberOfTerms);

  public int getWordCount(Corpus corpus);
}
