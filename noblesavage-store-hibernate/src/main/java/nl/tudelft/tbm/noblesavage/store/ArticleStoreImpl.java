package nl.tudelft.tbm.noblesavage.store;

import java.util.Date;
import java.util.List;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.article.ArticleStore;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class ArticleStoreImpl extends AbstractEntityStoreImpl<Article> implements ArticleStore {

  @Override
  public List<Article> findByCorpusAndDateRange(Corpus corpus, Date startDate, Date endDate) {
    return this.findByCriteria(Restrictions.eq("corpus", corpus), Restrictions.between("date", startDate, endDate));
  }
  
  public int deleteNonUniqueArticlesInCorpus(Corpus corpus) {
  	Query query = this.getSession().getNamedQuery("ArticleStoreImpl.deleteNonUniqueArticlesInCorpus");
  	query.setInteger("corpusId", corpus.getId().intValue());
  	return query.executeUpdate();
  }

}
