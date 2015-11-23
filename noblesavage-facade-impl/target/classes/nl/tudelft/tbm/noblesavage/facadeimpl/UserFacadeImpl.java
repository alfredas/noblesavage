package nl.tudelft.tbm.noblesavage.facadeimpl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import nl.tudelft.tbm.noblesavage.domain.user.User;
import nl.tudelft.tbm.noblesavage.domain.user.UserStore;
import nl.tudelft.tbm.noblesavage.facade.UserDTO;
import nl.tudelft.tbm.noblesavage.facade.UserFacade;
import nl.tudelft.tbm.noblesavage.facadeimpl.mapping.BeanMapper;

public class UserFacadeImpl implements UserFacade {
	
	private UserStore userStore;
	private BeanMapper beanMapper;
	
	private static final Logger log = Logger.getLogger(UserFacadeImpl.class);
	
	@Override
	@Transactional
	public UserDTO login(String username, String password) {
		User user = this.userStore.findByUsername(username);
		log.info("looking for user with username: " + username);
		if (user != null) {
			log.info("found user with username: " + username);
		}
		if ( (user != null) && (user.getPassword().equals(password)) ) {
			return this.beanMapper.map(user, UserDTO.class);
		} else if (user != null) {
			log.info("but passwords did not match: " + user.getPassword() + " <> " + password);
		}
		return null;
	}

	public UserStore getUserStore() {
  	return userStore;
  }

	public void setUserStore(UserStore userStore) {
  	this.userStore = userStore;
  }

	public BeanMapper getBeanMapper() {
  	return beanMapper;
  }

	public void setBeanMapper(BeanMapper beanMapper) {
  	this.beanMapper = beanMapper;
  }

}
