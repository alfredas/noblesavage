package nl.tudelft.tbm.noblesavage.facade;

@SuppressWarnings("deprecation")
public class ArticleDTO_FieldSerializer {
  private static native java.util.Date getDateCreated(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::dateCreated;
  }-*/;
  
  private static native void  setDateCreated(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, java.util.Date value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::dateCreated = value;
  }-*/;
  
  private static native java.lang.Long getId(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::id;
  }-*/;
  
  private static native void  setId(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, java.lang.Long value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::id = value;
  }-*/;
  
  private static native java.lang.String getTags(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::tags;
  }-*/;
  
  private static native void  setTags(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::tags = value;
  }-*/;
  
  private static native java.lang.String getTitle(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::title;
  }-*/;
  
  private static native void  setTitle(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::title = value;
  }-*/;
  
  private static native java.lang.String getUrl(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::url;
  }-*/;
  
  private static native void  setUrl(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::url = value;
  }-*/;
  
  private static native int getWordcount(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::wordcount;
  }-*/;
  
  private static native void  setWordcount(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, int value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::wordcount = value;
  }-*/;
  
  private static native java.lang.String getWordfrequency(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::wordfrequency;
  }-*/;
  
  private static native void  setWordfrequency(nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.ArticleDTO::wordfrequency = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setDateCreated(instance, (java.util.Date) streamReader.readObject());
    setId(instance, (java.lang.Long) streamReader.readObject());
    setTags(instance, streamReader.readString());
    setTitle(instance, streamReader.readString());
    setUrl(instance, streamReader.readString());
    setWordcount(instance, streamReader.readInt());
    setWordfrequency(instance, streamReader.readString());
    
  }
  
  public static native nl.tudelft.tbm.noblesavage.facade.ArticleDTO instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @nl.tudelft.tbm.noblesavage.facade.ArticleDTO::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, nl.tudelft.tbm.noblesavage.facade.ArticleDTO instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeObject(getDateCreated(instance));
    streamWriter.writeObject(getId(instance));
    streamWriter.writeString(getTags(instance));
    streamWriter.writeString(getTitle(instance));
    streamWriter.writeString(getUrl(instance));
    streamWriter.writeInt(getWordcount(instance));
    streamWriter.writeString(getWordfrequency(instance));
    
  }
  
}
