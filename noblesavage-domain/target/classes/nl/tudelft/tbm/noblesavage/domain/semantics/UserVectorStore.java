package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.util.HashMap;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

import pitt.search.semanticvectors.VectorStore;

public class UserVectorStore {
	
	private HashMap<String, HashMap<String, VectorStore>> store = new HashMap<String, HashMap<String,VectorStore>>();
	
	public HashMap<String, VectorStore> getVectorStore(Corpus corpus, int yearFrom, int yearTo) {
		String key = corpus.getId() + "-" + yearFrom + "-" + yearTo;
		return store.get(key);
	}
	
	public boolean hasVectorStore(Corpus corpus, int yearFrom, int yearTo) {
		String key = corpus.getId() + "-" + yearFrom + "-" + yearTo;
		return store.containsKey(key);
	}
	
	public void setVectorStore(Corpus corpus, int yearFrom, int yearTo, HashMap<String, VectorStore> vStore) {
		String key = corpus.getId() + "-" + yearFrom + "-" + yearTo;
		store.put(key, vStore);
	}
	
	

}
