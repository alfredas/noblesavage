package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;

public class UserFacade_Proxy extends RemoteServiceProxy implements nl.tudelft.tbm.noblesavage.facade.UserFacadeAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "nl.tudelft.tbm.noblesavage.facade.UserFacade";
  private static final String SERIALIZATION_POLICY ="CB900D8BCCAF6BDBC96305F3C539AB01";
  private static final nl.tudelft.tbm.noblesavage.facade.UserFacade_TypeSerializer SERIALIZER = new nl.tudelft.tbm.noblesavage.facade.UserFacade_TypeSerializer();
  
  public UserFacade_Proxy() {
    super(GWT.getModuleBaseURL(),
      "user.rpc", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void login(java.lang.String user, java.lang.String pass, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("UserFacade_Proxy.login", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("login");
      streamWriter.writeInt(2);
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeString("java.lang.String/2004016611");
      streamWriter.writeString(user);
      streamWriter.writeString(pass);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("UserFacade_Proxy.login", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "UserFacade_Proxy.login", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
}
