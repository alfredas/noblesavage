package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

public class NewYorkTimesReader extends GoogleNewsReaderWrapper {
	
	public static final String READER_NAME = "New York Times";
	
	public NewYorkTimesReader() {
		this("new york times");
	}
	
	
	public NewYorkTimesReader(String source) {
		super(source);
	}
	
	public String getReaderName() {
		  return READER_NAME;
    }
	
}
