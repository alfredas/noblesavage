package nl.tudelft.tbm.noblesavage.domain.article;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.store.AbstractEntity;

@Entity
@SequenceGenerator(name = "id-sequence", sequenceName = "article_id_seq", allocationSize = 1)
public class Article extends AbstractEntity {

  @Basic
  @Column(length = 2000, nullable = false)
  private String url;

  @Basic
  @Column(length = 2000, nullable = true)
  private String title;

  @Lob
  private String text;

  @ManyToOne
  @JoinColumn(name = "corpus_id")
  private Corpus corpus;

  @Column(nullable = false)
  private Date dateCreated;

  @Basic
  @Column(length = 2000, nullable = true)
  private String tags;

  @Basic
  @Column(length = 2000, nullable = true)
  private String wordfrequency;

  @Basic
  @Column(nullable = true)
  private Integer wordcount;

  public Article() {
    super();
  }

  public Article(String url, String title, String text, Corpus corpus, Date dateCreated, String tags) {
    super();
    this.url = url;
    this.title = title;
    this.text = text;
    this.corpus = corpus;
    this.dateCreated = dateCreated;
    this.tags = tags;
  }

  public Article(String url, String title, String text, Corpus corpus, Date dateCreated, String tags, String wordfrequency,
      Integer wordcount) {
    super();
    this.url = url;
    this.title = title;
    this.text = text;
    this.corpus = corpus;
    this.dateCreated = dateCreated;
    this.tags = tags;
    this.wordfrequency = wordfrequency;
    this.wordcount = wordcount;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Corpus getCorpus() {
    return corpus;
  }

  public void setCorpus(Corpus corpus) {
    this.corpus = corpus;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
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

  public Integer getWordcount() {
    return wordcount;
  }

  public void setWordcount(Integer wordcount) {
    this.wordcount = wordcount;
  }

}
