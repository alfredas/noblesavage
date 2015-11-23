package nl.tudelft.tbm.noblesavage.facade;

@SuppressWarnings("deprecation")
public class UserDTO_FieldSerializer {
  private static native boolean getAdmin(nl.tudelft.tbm.noblesavage.facade.UserDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::admin;
  }-*/;
  
  private static native void  setAdmin(nl.tudelft.tbm.noblesavage.facade.UserDTO instance, boolean value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::admin = value;
  }-*/;
  
  private static native java.lang.Long getId(nl.tudelft.tbm.noblesavage.facade.UserDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::id;
  }-*/;
  
  private static native void  setId(nl.tudelft.tbm.noblesavage.facade.UserDTO instance, java.lang.Long value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::id = value;
  }-*/;
  
  private static native java.lang.String getPassword(nl.tudelft.tbm.noblesavage.facade.UserDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::password;
  }-*/;
  
  private static native void  setPassword(nl.tudelft.tbm.noblesavage.facade.UserDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::password = value;
  }-*/;
  
  private static native java.lang.String getUsername(nl.tudelft.tbm.noblesavage.facade.UserDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::username;
  }-*/;
  
  private static native void  setUsername(nl.tudelft.tbm.noblesavage.facade.UserDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.UserDTO::username = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, nl.tudelft.tbm.noblesavage.facade.UserDTO instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setAdmin(instance, streamReader.readBoolean());
    setId(instance, (java.lang.Long) streamReader.readObject());
    setPassword(instance, streamReader.readString());
    setUsername(instance, streamReader.readString());
    
  }
  
  public static native nl.tudelft.tbm.noblesavage.facade.UserDTO instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @nl.tudelft.tbm.noblesavage.facade.UserDTO::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, nl.tudelft.tbm.noblesavage.facade.UserDTO instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeBoolean(getAdmin(instance));
    streamWriter.writeObject(getId(instance));
    streamWriter.writeString(getPassword(instance));
    streamWriter.writeString(getUsername(instance));
    
  }
  
}
