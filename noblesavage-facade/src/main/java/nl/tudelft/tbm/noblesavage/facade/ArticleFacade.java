package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("article.rpc")
public interface ArticleFacade extends RemoteService {

  String getArticleText(ArticleDTO article);

  void deleteArticle(ArticleDTO article);

}
