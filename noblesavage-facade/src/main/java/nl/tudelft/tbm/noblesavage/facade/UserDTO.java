package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String password;
  private boolean admin;

  public UserDTO() {
    super();
  }

  public UserDTO(Long id, String username, String password, boolean admin) {
    super();
    this.id = id;
    this.username = username;
    this.password = password;
    this.admin = admin;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

	public String getUsername() {
  	return username;
  }

	public void setUsername(String username) {
  	this.username = username;
  }

	public String getPassword() {
  	return password;
  }

	public void setPassword(String password) {
  	this.password = password;
  }

	public boolean isAdmin() {
  	return admin;
  }

	public void setAdmin(boolean admin) {
  	this.admin = admin;
  }

}
