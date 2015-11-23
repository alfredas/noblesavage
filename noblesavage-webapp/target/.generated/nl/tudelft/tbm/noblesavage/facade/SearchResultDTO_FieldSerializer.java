package nl.tudelft.tbm.noblesavage.facade;

@SuppressWarnings("deprecation")
public class SearchResultDTO_FieldSerializer {
  private static native float getScore(nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.SearchResultDTO::score;
  }-*/;
  
  private static native void  setScore(nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instance, float value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.SearchResultDTO::score = value;
  }-*/;
  
  private static native java.lang.String getTerm(nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.SearchResultDTO::term;
  }-*/;
  
  private static native void  setTerm(nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.SearchResultDTO::term = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setScore(instance, streamReader.readFloat());
    setTerm(instance, streamReader.readString());
    
  }
  
  public static native nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, nl.tudelft.tbm.noblesavage.facade.SearchResultDTO instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeFloat(getScore(instance));
    streamWriter.writeString(getTerm(instance));
    
  }
  
}
