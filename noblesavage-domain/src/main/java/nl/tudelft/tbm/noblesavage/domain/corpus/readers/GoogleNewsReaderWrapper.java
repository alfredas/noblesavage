package nl.tudelft.tbm.noblesavage.domain.corpus.readers;

import org.apache.log4j.Logger;

public class GoogleNewsReaderWrapper extends GoogleNewsReader {
	
	private String source;
	private static final Logger log = Logger.getLogger(GoogleNewsReaderWrapper.class);
	
	
	public GoogleNewsReaderWrapper(String source) {
		this.source = source;
	}
	
	protected String createURL(String keys, int startYear, int endYear, int numberOfArticles) {
		log.info("creating url with source:" + this.getSource());
	    int pageLimit = Math.min(GoogleNewsReader.RESULT_LIMIT, numberOfArticles);
	    String encodedKeys = keys;
	    try {
	    	encodedKeys = encodedKeys.replace(" ", "+");
	    	String escapedSource = this.getSource().replace(" ", "%20");
	    	encodedKeys = "source%3A%22" + escapedSource + "%22+" + encodedKeys;
	      // encodedKeys = URLEncoder.encode(encodedKeys, "utf-8");
	    } catch (Exception err) {
	    }
	    return "http://news.google.com/archivesearch?as_user_ldate=" + startYear + "&as_user_hdate=" + endYear + "&q=" + encodedKeys
	        + "&scoring=a&num=" + pageLimit;
	  }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
