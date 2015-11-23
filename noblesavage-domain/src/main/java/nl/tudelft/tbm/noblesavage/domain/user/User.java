package nl.tudelft.tbm.noblesavage.domain.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntity;

@Entity
@SequenceGenerator(name = "id-sequence", sequenceName = "user_id_seq", allocationSize = 1)
public class User extends AbstractEntity {

  @Basic
  @Column(length = 2000, nullable = false)
  private String username;

  @Basic
  @Column(length = 2000, nullable = true)
  private String password;
  
  @Basic
  @Column(nullable = true)
  private boolean admin;

  public User() {
    super();
  }

  public User(String username, String password, boolean admin) {
    super();
    this.username = username;
    this.password = password;
    this.admin = admin;
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
