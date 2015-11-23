package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;

public class ArticleFacade_Proxy extends RemoteServiceProxy implements nl.tudelft.tbm.noblesavage.facade.ArticleFacadeAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "nl.tudelft.tbm.noblesavage.facade.ArticleFacade";
  private static final String SERIALIZATION_POLICY ="A440039242D167FB6458B9473CC0BE40";
  private static final nl.tudelft.tbm.noblesavage.facade.ArticleFacade_TypeSerializer SERIALIZER = new nl.tudelft.tbm.noblesavage.facade.ArticleFacade_TypeSerializer();
  
  public ArticleFacade_Proxy() {
    super(GWT.getModuleBaseURL(),
      "article.rpc", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void deleteArticle(nl.tudelft.tbm.noblesavage.facade.ArticleDTO article, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("ArticleFacade_Proxy.deleteArticle", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("deleteArticle");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.ArticleDTO/466476560");
      streamWriter.writeObject(article);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("ArticleFacade_Proxy.deleteArticle", requestId, "requestSerialized"));
      doInvoke(ResponseReader.VOID, "ArticleFacade_Proxy.deleteArticle", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getArticleText(nl.tudelft.tbm.noblesavage.facade.ArticleDTO article, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("ArticleFacade_Proxy.getArticleText", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getArticleText");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.ArticleDTO/466476560");
      streamWriter.writeObject(article);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("ArticleFacade_Proxy.getArticleText", requestId, "requestSerialized"));
      doInvoke(ResponseReader.STRING, "ArticleFacade_Proxy.getArticleText", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
}
