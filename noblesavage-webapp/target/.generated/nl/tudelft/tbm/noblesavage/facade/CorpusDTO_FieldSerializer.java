package nl.tudelft.tbm.noblesavage.facade;

@SuppressWarnings("deprecation")
public class CorpusDTO_FieldSerializer {
  private static native java.lang.Long getId(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::id;
  }-*/;
  
  private static native void  setId(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.Long value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::id = value;
  }-*/;
  
  private static native java.lang.String getKey(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::key;
  }-*/;
  
  private static native void  setKey(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::key = value;
  }-*/;
  
  private static native java.lang.String getLang(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::lang;
  }-*/;
  
  private static native void  setLang(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::lang = value;
  }-*/;
  
  private static native java.lang.String getName(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::name;
  }-*/;
  
  private static native void  setName(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::name = value;
  }-*/;
  
  private static native java.lang.String getReaderType(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::readerType;
  }-*/;
  
  private static native void  setReaderType(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::readerType = value;
  }-*/;
  
  private static native java.lang.String getTags(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::tags;
  }-*/;
  
  private static native void  setTags(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::tags = value;
  }-*/;
  
  private static native java.lang.String getUrl(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::url;
  }-*/;
  
  private static native void  setUrl(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::url = value;
  }-*/;
  
  private static native int getWordcount(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::wordcount;
  }-*/;
  
  private static native void  setWordcount(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, int value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::wordcount = value;
  }-*/;
  
  private static native java.lang.String getWordfrequency(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) /*-{
    return instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::wordfrequency;
  }-*/;
  
  private static native void  setWordfrequency(nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance, java.lang.String value) /*-{
    instance.@nl.tudelft.tbm.noblesavage.facade.CorpusDTO::wordfrequency = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setId(instance, (java.lang.Long) streamReader.readObject());
    setKey(instance, streamReader.readString());
    setLang(instance, streamReader.readString());
    setName(instance, streamReader.readString());
    setReaderType(instance, streamReader.readString());
    setTags(instance, streamReader.readString());
    setUrl(instance, streamReader.readString());
    setWordcount(instance, streamReader.readInt());
    setWordfrequency(instance, streamReader.readString());
    
  }
  
  public static native nl.tudelft.tbm.noblesavage.facade.CorpusDTO instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @nl.tudelft.tbm.noblesavage.facade.CorpusDTO::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, nl.tudelft.tbm.noblesavage.facade.CorpusDTO instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeObject(getId(instance));
    streamWriter.writeString(getKey(instance));
    streamWriter.writeString(getLang(instance));
    streamWriter.writeString(getName(instance));
    streamWriter.writeString(getReaderType(instance));
    streamWriter.writeString(getTags(instance));
    streamWriter.writeString(getUrl(instance));
    streamWriter.writeInt(getWordcount(instance));
    streamWriter.writeString(getWordfrequency(instance));
    
  }
  
}
