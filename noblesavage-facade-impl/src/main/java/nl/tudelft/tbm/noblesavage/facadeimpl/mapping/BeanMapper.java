package nl.tudelft.tbm.noblesavage.facadeimpl.mapping;

import java.util.Collection;
import java.util.List;

public interface BeanMapper {
  public <T> T map(Object source, Class<T> destinationClass);

  public <T> T map(Object source, T destination);

  public <T> T map(Object source, Class<T> destinationClass, String mapId);

  public <T> T map(Object source, T destination, String mapId);

  public <T> List<T> mapList(List<?> sourceObjects, Class<T> destinationClass);

  public <T> Collection<T> mapCollection(Collection<?> sourceObjects, Class<T> destinationClass);

}
