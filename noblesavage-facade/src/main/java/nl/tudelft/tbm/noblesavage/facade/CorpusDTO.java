package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CorpusDTO implements IsSerializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String url;
    private String name;
    private String key;
    private String readerType;
    private String lang;
    private String tags;
    private String wordfrequency;
    private int wordcount;

    public CorpusDTO() {
        super();
    }

    public CorpusDTO(Long id, String url, String name, String key, String readerType, String tags) {
        super();
        this.id = id;
        this.url = url;
        this.name = name;
        this.key = key;
        this.readerType = readerType;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getWordfrequency() {
        return wordfrequency;
    }

    public void setWordfrequency(String wordfrequency) {
        this.wordfrequency = wordfrequency;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
