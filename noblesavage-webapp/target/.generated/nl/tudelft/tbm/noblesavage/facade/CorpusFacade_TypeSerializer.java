package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.impl.Serializer;

public class CorpusFacade_TypeSerializer extends com.google.gwt.user.client.rpc.impl.SerializerBase {
  private static final MethodMap methodMap = JavaScriptObject.createObject().cast();
  private static final JsArrayString signatureMap = JavaScriptObject.createArray().cast();
  protected MethodMap getMethodMap() { return methodMap; }
  protected JsArrayString getSignatureMap() { return signatureMap; }
  
  static {
    registerMethods();
    registerSignatures();
  }
  private static native java.util.HashMap create_com_google_gwt_user_client_rpc_core_java_util_HashMap_CustomFieldSerializer(SerializationStreamReader streamReader) throws SerializationException /*-{
    return @java.util.HashMap::new()();
  }-*/;
  
  private static native java.util.IdentityHashMap create_com_google_gwt_user_client_rpc_core_java_util_IdentityHashMap_CustomFieldSerializer(SerializationStreamReader streamReader) throws SerializationException /*-{
    return @java.util.IdentityHashMap::new()();
  }-*/;
  
  private static native void registerSignatures() /*-{
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @com.google.gwt.i18n.client.impl.DateRecord::class,
      "com.google.gwt.i18n.client.impl.DateRecord/112389920");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException::class,
      "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.lang.Integer::class,
      "java.lang.Integer/3438268394");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.lang.Long::class,
      "java.lang.Long/4227064769");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.lang.String::class,
      "java.lang.String/2004016611");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.lang.String[]::class,
      "[Ljava.lang.String;/2600011424");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.sql.Date::class,
      "java.sql.Date/3996530531");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.sql.Time::class,
      "java.sql.Time/831929183");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.sql.Timestamp::class,
      "java.sql.Timestamp/1769758459");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.util.Date::class,
      "java.util.Date/1659716317");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.util.HashMap::class,
      "java.util.HashMap/962170901");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.util.IdentityHashMap::class,
      "java.util.IdentityHashMap/3881143367");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.util.LinkedHashMap::class,
      "java.util.LinkedHashMap/1551059846");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @java.util.TreeMap::class,
      "java.util.TreeMap/1575826026");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.ArticleDTO::class,
      "nl.tudelft.tbm.noblesavage.facade.ArticleDTO/466476560");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.ArticleDTO[]::class,
      "[Lnl.tudelft.tbm.noblesavage.facade.ArticleDTO;/4134483985");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.CorpusDTO::class,
      "nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.CorpusDTO[]::class,
      "[Lnl.tudelft.tbm.noblesavage.facade.CorpusDTO;/3008274993");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO::class,
      "nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO/1948190380");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO[]::class,
      "[Lnl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO;/2161925340");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO::class,
      "nl.tudelft.tbm.noblesavage.facade.SearchResultDTO/3193050695");
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerSignature(Lcom/google/gwt/core/client/JsArrayString;Ljava/lang/Class;Ljava/lang/String;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::signatureMap,
      @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO[]::class,
      "[Lnl.tudelft.tbm.noblesavage.facade.SearchResultDTO;/257888090");
    
  }-*/;
  
  private static native void registerMethods() /*-{
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "com.google.gwt.i18n.client.impl.DateRecord/112389920" , [
        @com.google.gwt.i18n.client.impl.DateRecord_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.i18n.client.impl.DateRecord_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lcom/google/gwt/i18n/client/impl/DateRecord;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533" , [
        @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lcom/google/gwt/user/client/rpc/IncompatibleRemoteServiceException;),
        @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Lcom/google/gwt/user/client/rpc/IncompatibleRemoteServiceException;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.lang.Integer/3438268394" , [
        @com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/lang/Integer;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.lang.Long/4227064769" , [
        @com.google.gwt.user.client.rpc.core.java.lang.Long_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.Long_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/lang/Long;),
        @com.google.gwt.user.client.rpc.core.java.lang.Long_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/lang/Long;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.lang.String/2004016611" , [
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/lang/String;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/lang/String;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "[Ljava.lang.String;/2600011424" , [
        @com.google.gwt.user.client.rpc.core.java.lang.String_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Ljava/lang/String;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.sql.Date/3996530531" , [
        @com.google.gwt.user.client.rpc.core.java.sql.Date_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.sql.Date_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/sql/Date;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.sql.Time/831929183" , [
        @com.google.gwt.user.client.rpc.core.java.sql.Time_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.sql.Time_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/sql/Time;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.sql.Timestamp/1769758459" , [
        @com.google.gwt.user.client.rpc.core.java.sql.Timestamp_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.sql.Timestamp_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/sql/Timestamp;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.util.Date/1659716317" , [
        @com.google.gwt.user.client.rpc.core.java.util.Date_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.Date_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/Date;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.util.HashMap/962170901" , [
        @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::create_com_google_gwt_user_client_rpc_core_java_util_HashMap_CustomFieldSerializer(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.HashMap_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/HashMap;),
        @com.google.gwt.user.client.rpc.core.java.util.HashMap_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/HashMap;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.util.IdentityHashMap/3881143367" , [
        @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::create_com_google_gwt_user_client_rpc_core_java_util_IdentityHashMap_CustomFieldSerializer(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.IdentityHashMap_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/IdentityHashMap;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.util.LinkedHashMap/1551059846" , [
        @com.google.gwt.user.client.rpc.core.java.util.LinkedHashMap_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.LinkedHashMap_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/LinkedHashMap;),
        @com.google.gwt.user.client.rpc.core.java.util.LinkedHashMap_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/LinkedHashMap;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "java.util.TreeMap/1575826026" , [
        @com.google.gwt.user.client.rpc.core.java.util.TreeMap_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.TreeMap_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/TreeMap;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "nl.tudelft.tbm.noblesavage.facade.ArticleDTO/466476560" , [
        @nl.tudelft.tbm.noblesavage.facade.ArticleDTO_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.ArticleDTO_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lnl/tudelft/tbm/noblesavage/facade/ArticleDTO;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "[Lnl.tudelft.tbm.noblesavage.facade.ArticleDTO;/4134483985" , [
        @nl.tudelft.tbm.noblesavage.facade.ArticleDTO_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.ArticleDTO_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Lnl/tudelft/tbm/noblesavage/facade/ArticleDTO;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "nl.tudelft.tbm.noblesavage.facade.CorpusDTO/1897433974" , [
        @nl.tudelft.tbm.noblesavage.facade.CorpusDTO_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.CorpusDTO_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lnl/tudelft/tbm/noblesavage/facade/CorpusDTO;),
        @nl.tudelft.tbm.noblesavage.facade.CorpusDTO_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Lnl/tudelft/tbm/noblesavage/facade/CorpusDTO;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "[Lnl.tudelft.tbm.noblesavage.facade.CorpusDTO;/3008274993" , [
        @nl.tudelft.tbm.noblesavage.facade.CorpusDTO_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.CorpusDTO_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Lnl/tudelft/tbm/noblesavage/facade/CorpusDTO;),
        @nl.tudelft.tbm.noblesavage.facade.CorpusDTO_Array_Rank_1_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;[Lnl/tudelft/tbm/noblesavage/facade/CorpusDTO;)
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO/1948190380" , [
        @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lnl/tudelft/tbm/noblesavage/facade/ReaderParameterDTO;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "[Lnl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO;/2161925340" , [
        @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Lnl/tudelft/tbm/noblesavage/facade/ReaderParameterDTO;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "nl.tudelft.tbm.noblesavage.facade.SearchResultDTO/3193050695" , [
        @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lnl/tudelft/tbm/noblesavage/facade/SearchResultDTO;),
      ]);
    
    @com.google.gwt.user.client.rpc.impl.SerializerBase::registerMethods(Lcom/google/gwt/user/client/rpc/impl/SerializerBase$MethodMap;Ljava/lang/String;Lcom/google/gwt/core/client/JsArray;)(
      @nl.tudelft.tbm.noblesavage.facade.CorpusFacade_TypeSerializer::methodMap,
      "[Lnl.tudelft.tbm.noblesavage.facade.SearchResultDTO;/257888090" , [
        @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @nl.tudelft.tbm.noblesavage.facade.SearchResultDTO_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Lnl/tudelft/tbm/noblesavage/facade/SearchResultDTO;),
      ]);
    
  }-*/;
  
  private static void raiseSerializationException(String msg) throws SerializationException {
    throw new SerializationException(msg);
  }
  
}
