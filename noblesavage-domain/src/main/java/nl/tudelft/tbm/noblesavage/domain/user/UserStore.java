package nl.tudelft.tbm.noblesavage.domain.user;

import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntityStore;

public interface UserStore extends AbstractEntityStore<User> {

	public User findByUsername(String username);
	
}