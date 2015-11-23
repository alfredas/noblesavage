package nl.tudelft.tbm.noblesavage.domain.article;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class ArticleDocument {

  public static final String PATH_FIELD = "path";
  public static final String CONTENTS_FIELD = "contents";

  public static Document getDocument(Article article) {
    if (article.getText() != null) {
      Document doc = new Document();
      doc.add(new Field("path", article.getUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
      doc.add(new Field("contents", article.getText(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
      return doc;
    } else {
      return null;
    }
  }
}
