package nl.tudelft.tbm.noblesavage.domain.semantics;

public class SearchResult {

  private float score;
  private String term;

  public SearchResult(float score, String term) {
    super();
    this.score = score;
    this.term = term;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

}
