package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;

public class CorpusFacade_Proxy extends RemoteServiceProxy implements nl.tudelft.tbm.noblesavage.facade.CorpusFacadeAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "nl.tudelft.tbm.noblesavage.facade.CorpusFacade";
  private static final String SERIALIZATION_POLICY ="9A90C1743C10097759E8D10D49524F3E";
  private static final nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer SERIALIZER = new nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer();
  
  public CorpusFacade_Proxy() {
    super(GWT.getModuleBaseURL(),
      "corpus.rpc", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void createCorpus(java.util.HashMap parameters, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.createCorpus", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("createCorpus");
      streamWriter.writeInt(1);
      streamWriter.writeString("java.util.HashMap/962170901");
      streamWriter.writeObject(parameters);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.createCorpus", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.createCorpus", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void deleteCorpus(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.deleteCorpus", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("deleteCorpus");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeObject(corpusDTO);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.deleteCorpus", requestId, "requestSerialized"));
      doInvoke(ResponseReader.VOID, "CorpusFacade_Proxy.deleteCorpus", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void deleteNonUniqueArticlesInCorpus(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.deleteNonUniqueArticlesInCorpus", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("deleteNonUniqueArticlesInCorpus");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeObject(corpusDTO);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.deleteNonUniqueArticlesInCorpus", requestId, "requestSerialized"));
      doInvoke(ResponseReader.INT, "CorpusFacade_Proxy.deleteNonUniqueArticlesInCorpus", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getArticleCount(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getArticleCount", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getArticleCount");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeObject(corpusDTO);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getArticleCount", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getArticleCount", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getArticles(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getArticles", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getArticles");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeObject(corpusDTO);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getArticles", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getArticles", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getCachedCorpora(com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getCachedCorpora", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getCachedCorpora");
      streamWriter.writeInt(0);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getCachedCorpora", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getCachedCorpora", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getCorpusReaderTypes(com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getCorpusReaderTypes", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getCorpusReaderTypes");
      streamWriter.writeInt(0);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getCorpusReaderTypes", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getCorpusReaderTypes", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getReaderParameters(java.lang.String type, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getReaderParameters", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getReaderParameters");
      streamWriter.writeInt(1);
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeString(type);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getReaderParameters", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getReaderParameters", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getSearchMethods(com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getSearchMethods", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getSearchMethods");
      streamWriter.writeInt(0);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getSearchMethods", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getSearchMethods", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getTermFrequency(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, int numberOfTerms, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getTermFrequency", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getTermFrequency");
      streamWriter.writeInt(2);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeString("I");
      streamWriter.writeObject(corpusDTO);
      streamWriter.writeInt(numberOfTerms);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getTermFrequency", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getTermFrequency", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getWordCount(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getWordCount", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getWordCount");
      streamWriter.writeInt(1);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeObject(corpusDTO);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.getWordCount", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.getWordCount", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void mergeCorpora(nl.tudelft.tbm.noblesavage.facade.CorpusDTO[] corpusDTO, java.lang.String name, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.mergeCorpora", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("mergeCorpora");
      streamWriter.writeInt(2);
      streamWriter.writeString("[Lnl.tudelft.tbm.noblesavage.facade.CorpusDTO;/3008274993");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeObject(corpusDTO);
      streamWriter.writeString(name);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.mergeCorpora", requestId, "requestSerialized"));
      doInvoke(ResponseReader.VOID, "CorpusFacade_Proxy.mergeCorpora", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void removeTextFromCorpus(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, java.lang.String text, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.removeTextFromCorpus", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("removeTextFromCorpus");
      streamWriter.writeInt(2);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeObject(corpusDTO);
      streamWriter.writeString(text);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.removeTextFromCorpus", requestId, "requestSerialized"));
      doInvoke(ResponseReader.INT, "CorpusFacade_Proxy.removeTextFromCorpus", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void replaceTextFromCorpus(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, java.lang.String search, java.lang.String replace, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.replaceTextFromCorpus", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("replaceTextFromCorpus");
      streamWriter.writeInt(3);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeObject(corpusDTO);
      streamWriter.writeString(search);
      streamWriter.writeString(replace);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.replaceTextFromCorpus", requestId, "requestSerialized"));
      doInvoke(ResponseReader.INT, "CorpusFacade_Proxy.replaceTextFromCorpus", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void searchCorpus(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, java.lang.String term, int searchMethod, int fromYear, int toYear, int frequency, int numberOfResults, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.searchCorpus", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("searchCorpus");
      streamWriter.writeInt(7);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeString("I");
      streamWriter.writeString("I");
      streamWriter.writeString("I");
      streamWriter.writeString("I");
      streamWriter.writeString("I");
      streamWriter.writeObject(corpusDTO);
      streamWriter.writeString(term);
      streamWriter.writeInt(searchMethod);
      streamWriter.writeInt(fromYear);
      streamWriter.writeInt(toYear);
      streamWriter.writeInt(frequency);
      streamWriter.writeInt(numberOfResults);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.searchCorpus", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CorpusFacade_Proxy.searchCorpus", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void stemKeywords(nl.tudelft.tbm.noblesavage.facade.CorpusDTO corpusDTO, java.lang.String keywords, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.stemKeywords", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("stemKeywords");
      streamWriter.writeInt(2);
      streamWriter.writeString("nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeObject(corpusDTO);
      streamWriter.writeString(keywords);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("CorpusFacade_Proxy.stemKeywords", requestId, "requestSerialized"));
      doInvoke(ResponseReader.STRING, "CorpusFacade_Proxy.stemKeywords", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
}
