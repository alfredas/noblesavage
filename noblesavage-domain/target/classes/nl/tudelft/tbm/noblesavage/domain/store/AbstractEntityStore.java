package nl.tudelft.tbm.noblesavage.domain.store;

import java.util.List;

public interface AbstractEntityStore<T extends AbstractEntity> {

  public T load(Long id);

  public T findById(Long id);

  public void save(T entity);

  public void delete(Long id);

  public void delete(T entity);

  public List<T> findAll();

}
