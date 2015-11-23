package nl.tudelft.tbm.noblesavage.facade;

@SuppressWarnings("deprecation")
public class ReaderParameterDTO_FieldSerializer {
  private static native java.lang.String getDesrciption(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::desrciption;
  }-*/;
  
  private static native void  setDesrciption(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::desrciption = value;
  }-*/;
  
  private static native java.lang.String getName(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::name;
  }-*/;
  
  private static native void  setName(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::name = value;
  }-*/;
  
  private static native java.lang.String getParam(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::param;
  }-*/;
  
  private static native void  setParam(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::param = value;
  }-*/;
  
  private static native java.lang.String getType(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::type;
  }-*/;
  
  private static native void  setType(nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::type = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setDesrciption(instance, streamReader.readString());
    setName(instance, streamReader.readString());
    setParam(instance, streamReader.readString());
    setType(instance, streamReader.readString());
    
  }
  
  public static native nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeString(getDesrciption(instance));
    streamWriter.writeString(getName(instance));
    streamWriter.writeString(getParam(instance));
    streamWriter.writeString(getType(instance));
    
  }
  
}
