package nl.tudelft.tbm.noblesavage.facade;

@SuppressWarnings("deprecation")
public class ArticleDTO_Array_Rank_1_FieldSerializer {
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, nl.tudelft.tbm.noblesavage.facade.ArticleDTO[] instance) throws com.google.gwt.user.client.rpc.SerializationException{
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static nl.tudelft.tbm.noblesavage.facade.ArticleDTO[] instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException{
    int rank = streamReader.readInt();
    return new nl.tudelft.tbm.noblesavage.facade.ArticleDTO[rank];
  }
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, nl.tudelft.tbm.noblesavage.facade.ArticleDTO[] instance) throws com.google.gwt.user.client.rpc.SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
}
