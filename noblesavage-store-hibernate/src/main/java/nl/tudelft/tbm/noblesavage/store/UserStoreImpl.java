package nl.tudelft.tbm.noblesavage.store;

import nl.tudelft.tbm.noblesavage.domain.user.User;
import nl.tudelft.tbm.noblesavage.domain.user.UserStore;

import org.hibernate.criterion.Restrictions;

public class UserStoreImpl extends AbstractEntityStoreImpl<User> implements UserStore {

  @Override
  public User findByUsername(String username) {
    return this.findUniqueByCriteria(Restrictions.eq("username", username));
  }

}
