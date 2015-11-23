package nl.tudelft.tbm.noblesavage.store;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.article.ArticleStore;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusStore;

public class CorpusStoreImpl extends AbstractEntityStoreImpl<Corpus> implements CorpusStore {

  private ArticleStore articleStore;

  @Override
  public void delete(Corpus corpus) {
    if (corpus.getArticles() != null) {
      for (Article article : corpus.getArticles()) {
        articleStore.delete(article);
      }
    }
    super.delete(corpus);
  }

  public ArticleStore getArticleStore() {
    return articleStore;
  }

  public void setArticleStore(ArticleStore articleStore) {
    this.articleStore = articleStore;
  }

}
