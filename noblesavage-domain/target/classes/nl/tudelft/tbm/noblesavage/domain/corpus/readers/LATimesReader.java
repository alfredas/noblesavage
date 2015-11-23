package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

public class LATimesReader extends GoogleNewsReaderWrapper {
	
	public static final String READER_NAME = "LA Times";
	
	public LATimesReader() {
		this("los angeles times");
	}
	
	public LATimesReader(String source) {
		super(source);
	}
	
	public String getReaderName() {
		  return READER_NAME;
    }

}
