package nl.tudelft.tbm.noblesavage.facade;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchResultDTO implements IsSerializable {

  private static final long serialVersionUID = 1L;

  private String term;
  private float score;

  public SearchResultDTO() {
    super();
  }

  public SearchResultDTO(String term, float score) {
    super();
    this.term = term;
    this.score = score;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

}
