package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ArticleFacadeAsync {

  void getArticleText(ArticleDTO article, AsyncCallback<String> callback);

  void deleteArticle(ArticleDTO article, AsyncCallback<Void> callback);

}
