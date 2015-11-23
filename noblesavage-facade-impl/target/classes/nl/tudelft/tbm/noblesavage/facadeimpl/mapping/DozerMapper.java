package nl.tudelft.tbm.noblesavage.facadeimpl.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DozerMapper implements BeanMapper {
  private net.sf.dozer.util.mapping.MapperIF mapper;

  public void setMapper(net.sf.dozer.util.mapping.MapperIF mapper) {
    this.mapper = mapper;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T map(Object source, Class<T> destinationClass) {
    return source == null ? null : (T) mapper.map(source, destinationClass);
  }

  @Override
  public <T> T map(Object source, T destination) {
    if (source == null) {
      return null;
    }
    mapper.map(source, destination);
    return destination;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T map(Object source, Class<T> destinationClass, String mapId) {
    return source == null ? null : (T) mapper.map(source, destinationClass, mapId);
  }

  @Override
  public <T> T map(Object source, T destination, String mapId) {
    if (source == null) {
      return null;
    }
    mapper.map(source, destination, mapId);
    return destination;
  }

  @Override
  public <T> List<T> mapList(List<?> sourceObjects, Class<T> destinationClass) {
    List<T> list = new ArrayList<T>();
    for (Object o : sourceObjects) {
      list.add(this.map(o, destinationClass));
    }
    return list;
  }

  @Override
  public <T> Collection<T> mapCollection(Collection<?> sourceObjects, Class<T> destinationClass) {
    List<T> list = new ArrayList<T>();
    for (Object o : sourceObjects) {
      list.add(this.map(o, destinationClass));
    }
    return list;
  }
}
