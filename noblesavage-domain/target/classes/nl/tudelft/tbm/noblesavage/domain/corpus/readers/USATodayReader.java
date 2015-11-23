package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

public class USATodayReader extends GoogleNewsReaderWrapper {
	public static final String READER_NAME = "USA Today";
	
	public USATodayReader() {
		this("usa today");
	}
	
	public USATodayReader(String source) {
		super(source);
	}
	
	public String getReaderName() {
		  return READER_NAME;
    }
	
}
