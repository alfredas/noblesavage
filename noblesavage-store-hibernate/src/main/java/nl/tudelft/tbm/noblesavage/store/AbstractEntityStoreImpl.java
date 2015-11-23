package nl.tudelft.tbm.noblesavage.store;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntity;
import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntityStore;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class AbstractEntityStoreImpl<T extends AbstractEntity> implements AbstractEntityStore<T> {

  @SuppressWarnings("unchecked")
  private final Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  // Hibernate SessionFactory
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Override
  public T load(Long id) {
    return (T) this.getSession().load(this.entityClass, id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T findById(Long id) {
    return (T) this.getSession().get(this.entityClass, id);
  }

  @Override
  public void save(T entity) {
    this.getSession().save(entity);
  }

  @Override
  public void delete(Long id) {
    this.delete(this.findById(id));
  }

  @Override
  public void delete(T entity) {
    this.getSession().delete(entity);
  }

  @Override
  public List<T> findAll() {
    return this.findByCriteria();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  protected Session getSession() {
    return this.sessionFactory.getCurrentSession();
  }

  protected Class<T> getEntityClass() {
    return this.entityClass;
  }

  protected Criteria createCriteria() {
    return this.getSession().createCriteria(this.getEntityClass());
  }

  protected List<T> findByCriteria(Criterion... criterionArray) {
    return this.findByCriteria(null, criterionArray);
  }

  @SuppressWarnings("unchecked")
  protected List<T> findByCriteria(Order order, Criterion... criterionArray) {
    Criteria criteria = this.createCriteria();
    for (Criterion criterion : criterionArray) {
      criteria.add(criterion);
    }
    if (order != null) {
      criteria.addOrder(order);
    }
    return criteria.list();
  }

  @SuppressWarnings("unchecked")
  protected T findUniqueByCriteria(Criterion... criterionArray) {
    Criteria criteria = this.createCriteria();
    for (Criterion criterion : criterionArray) {
      criteria.add(criterion);
    }
    return (T) criteria.uniqueResult();
  }

  protected T findUniqueByProperty(String propertyName, Object value) {
    return this.findUniqueByCriteria(Restrictions.eq(propertyName, value));
  }
}