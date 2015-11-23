package nl.tudelft.tbm.noblesavage.domain.semantics;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

import org.apache.lucene.store.Directory;

public interface CorpusIndexingService {

  public Directory getIndex(Corpus corpus);

  public Directory getIndex(Corpus corpus, int fromYear, int toYear);

}
