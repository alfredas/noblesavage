package nl.tudelft.tbm.noblesavage.facade;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleDTO implements IsSerializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private String url;
  private String title;
  private Date dateCreated;
  private String tags;
  private String wordfrequency;
  private int wordcount;

  public ArticleDTO() {
    super();
  }

  public ArticleDTO(Long id, String url, String title, Date dateCreated) {
    super();
    this.id = id;
    this.url = url;
    this.title = title;
    this.dateCreated = dateCreated;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public int getWordcount() {
    return wordcount;
  }

  public void setWordcount(int wordcount) {
    this.wordcount = wordcount;
  }

}
