package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user.rpc")
public interface UserFacade extends RemoteService {

	UserDTO login(String user, String pass);
  
}
