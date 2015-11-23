package nl.tudelft.tbm.noblesavage.facadeimpl;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.article.ArticleStore;
import nl.tudelft.tbm.noblesavage.facade.ArticleDTO;
import nl.tudelft.tbm.noblesavage.facade.ArticleFacade;
import nl.tudelft.tbm.noblesavage.facadeimpl.mapping.BeanMapper;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class ArticleFacadeImpl implements ArticleFacade {

  private ArticleStore articleStore;
  private BeanMapper beanMapper;

  private static final Logger log = Logger.getLogger(ArticleFacadeImpl.class);

  @Override
  @Transactional
  public void deleteArticle(ArticleDTO articleDTO) {
    log.info("called deleteArticle");
    try {
      Article article = articleStore.findById(articleDTO.getId());
      if (article != null) {
        String name = article.getTitle();
        articleStore.delete(article);
        log.info("deleteArticle: deleted '" + name + "' article");
      } else {
        log.error("Article with id=" + articleDTO.getId() + " was not found");
      }
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
  }

  @Override
  @Transactional
  public String getArticleText(ArticleDTO articleDTO) {
    try {
      Article article = articleStore.findById(articleDTO.getId());
      if (article != null) {
        log.info("got article text");
        return article.getText();
      } else {
        log.error("Article with id=" + articleDTO.getId() + " was not found");
      }
    } catch (Exception err) {
      log.error(err.getMessage(), err);
    }
    return null;
  }

  public ArticleStore getArticleStore() {
    return articleStore;
  }

  public void setArticleStore(ArticleStore articleStore) {
    this.articleStore = articleStore;
  }

  public BeanMapper getBeanMapper() {
    return beanMapper;
  }

  public void setBeanMapper(BeanMapper beanMapper) {
    this.beanMapper = beanMapper;
  }

}
