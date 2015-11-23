package nl.tudelft.tbm.noblesavage.client;

import nl.tudelft.tbm.noblesavage.facade.UserDTO;

public class ClientState {
  private UserDTO user = null;

	public UserDTO getUser() {
  	return user;
  }
	
	public void setUser(UserDTO user) {
  	this.user = user;
  }
	
	public boolean isLoggedIn() {
		return (user != null);
	}
	
	public String getUsername() {
		if (this.user != null) {
			return this.user.getUsername();
		} 
		return null;
	}
	
	public boolean isAdmin() {
		if (this.user != null) {
			return this.user.isAdmin();
		} 
		return false;
	}

}
