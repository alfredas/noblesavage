package nl.tudelft.tbm.noblesavage.domain.semantics;

public class TextService {

  public static String fixHyphenation(String text) {
    return text.replaceAll("-[ \t]*\n[ \t]*", "");
  }

  public static String fixSpaces(String text) {
    return text.replaceAll("[\\s]+", " ");
  }

  public static String removeNumbers(String text) {
    return text.replaceAll("[\\s]+[0-9]+[\\s]+", " ");
  }

  public static String doAll(String text) {
    return removeNumbers(fixSpaces(fixHyphenation(text)));
  }
}
