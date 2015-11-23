package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserFacadeAsync {

  void login(String user, String pass, AsyncCallback<UserDTO> callback);

}
