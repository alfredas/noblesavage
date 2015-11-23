package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

public class WashingtonPostReader extends GoogleNewsReaderWrapper {
	
	public static final String READER_NAME = "Washington Post";
	
	public WashingtonPostReader() {
		this("washington post");
	}
	
	public WashingtonPostReader(String source) {
		super(source);
	}
	
	public String getReaderName() {
		  return READER_NAME;
    }

}
