package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

public class WallStreetJournalReader extends GoogleNewsReaderWrapper {
	
	public static final String READER_NAME = "Wall Street Journal";
	
	public WallStreetJournalReader() {
		this("wall street journal");
	}
	
	public WallStreetJournalReader(String source) {
		super(source);
	}
	
	public String getReaderName() {
		  return READER_NAME;
    }
	
}

