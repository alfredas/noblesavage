package nl.tudelft.tbm.noblesavage.domain.corpus;

import nl.tudelft.tbm.noblesavage.domain.article.parsers.FileParser;
import nl.tudelft.tbm.noblesavage.domain.article.parsers.WebPageParser;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.ArchiveFileReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.GoogleNewsReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.LATimesReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.NewYorkTimesReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.USATodayReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.WallStreetJournalReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.readers.WashingtonPostReader;

public enum CorpusReaderType {
  GOOGLE_NEWS_SEARCH(GoogleNewsReader.READER_NAME, GoogleNewsReader.class, WebPageParser.class),

  NEW_YORK_TIMES(NewYorkTimesReader.READER_NAME, NewYorkTimesReader.class, WebPageParser.class),

  WALL_STREET_JOURNAL(WallStreetJournalReader.READER_NAME, WallStreetJournalReader.class, WebPageParser.class),

  USA_TODAY(USATodayReader.READER_NAME, USATodayReader.class, WebPageParser.class),

  LA_TIMES(LATimesReader.READER_NAME, LATimesReader.class, WebPageParser.class),

  WASHINGTON_POST(WashingtonPostReader.READER_NAME, WashingtonPostReader.class, WebPageParser.class),

  ARCHIVE_READER(ArchiveFileReader.READER_NAME, ArchiveFileReader.class, FileParser.class);

  // JIE(JIEReader.READER_NAME, JIEReader.class, JIEArticleParser.class),

  // CORPORATE_REGISTER(CorporateRegisterReader.READER_NAME,
  // CorporateRegisterReader.class, PDFParser.class);

  private String title;
  private Class<?> readerName;
  private Class<?> parserName;

  CorpusReaderType(String title, Class<?> readerName, Class<?> parserName) {
    this.title = title;
    this.readerName = readerName;
    this.parserName = parserName;
  }

  @Override
  public String toString() {
    return title;
  }

  public Class<?> getReaderClass() {
    return readerName;
  }

  public Class<?> getParserClass() {
    return parserName;
  }

  public static CorpusReaderType getReaderTypeByTitle(String title) {
    for (CorpusReaderType readerType : CorpusReaderType.values()) {
      if (readerType.toString().equals(title)) {
        return readerType;
      }
    }
    return null;
  }

}