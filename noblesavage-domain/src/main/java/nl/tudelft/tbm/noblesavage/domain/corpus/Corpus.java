package nl.tudelft.tbm.noblesavage.domain.corpus;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntity;

@Entity
@SequenceGenerator(name = "id-sequence", sequenceName = "corpus_id_seq", allocationSize = 1)
public class Corpus extends AbstractEntity {

    @Basic
    @Column(length = 500, nullable = false)
    private String name;

    @Basic
    @Column(length = 2000, nullable = false)
    private String url;

    @Basic
    @Column(length = 100, nullable = true)
    private String readerType;

    @OneToMany(mappedBy = "corpus")
    private List<Article> articles;

    @Basic
    @Column(length = 50, nullable = true)
    private String lang;

    @Basic
    @Column(length = 2000, nullable = true)
    private String tags;

    @Lob
    private byte[] index;

    @Basic
    @Column(length = 2000, nullable = true)
    private String indexheader;

    @Basic
    @Column(length = 2000, nullable = true)
    private String wordfrequency;

    @Basic
    @Column(nullable = true)
    private Integer wordcount;

    public Corpus() {
        super();
    }

    public Corpus(String name, String url, String readerType, List<Article> articles, String tags) {
        super();
        this.name = name;
        this.url = url;
        this.readerType = readerType;
        this.articles = articles;
        this.tags = tags;
    }

    public Corpus(String name, String url, String readerType, List<Article> articles, String tags, byte[] index, String indexheader,
            String wordfrequency, Integer wordcount) {
        super();
        this.name = name;
        this.url = url;
        this.readerType = readerType;
        this.articles = articles;
        this.tags = tags;
        this.index = index;
        this.indexheader = indexheader;
        this.wordfrequency = wordfrequency;
        this.wordcount = wordcount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public byte[] getIndex() {
        return index;
    }

    public void setIndex(byte[] index) {
        this.index = index;
    }

    public String getWordfrequency() {
        return wordfrequency;
    }

    public void setWordfrequency(String wordfrequency) {
        this.wordfrequency = wordfrequency;
    }

    public Integer getWordcount() {
        return wordcount;
    }

    public void setWordcount(Integer wordcount) {
        this.wordcount = wordcount;
    }

    public String getIndexheader() {
        return indexheader;
    }

    public void setIndexheader(String indexheader) {
        this.indexheader = indexheader;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
