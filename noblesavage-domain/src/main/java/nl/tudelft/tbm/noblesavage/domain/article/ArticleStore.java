package nl.tudelft.tbm.noblesavage.domain.article;

import java.util.Date;
import java.util.List;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntityStore;

public interface ArticleStore extends AbstractEntityStore<Article> {

  public List<Article> findByCorpusAndDateRange(Corpus corpus, Date startDate, Date endDate);
  
  public int deleteNonUniqueArticlesInCorpus(Corpus corpus);
  
}
