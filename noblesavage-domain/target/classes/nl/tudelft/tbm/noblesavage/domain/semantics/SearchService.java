package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import pitt.search.semanticvectors.CompoundVectorBuilder;
import pitt.search.semanticvectors.LuceneUtils;
import pitt.search.semanticvectors.ObjectVector;
import pitt.search.semanticvectors.VectorSearcher;
import pitt.search.semanticvectors.VectorStore;
import pitt.search.semanticvectors.VectorUtils;

public class SearchService {

    public static final int SUM = 0;
    public static final int SPARSESUM = 1;
    public static final int TENSOR = 2;
    public static final int CONVOLUTION = 3;
    public static final int SUBSPACE = 4;
    public static final int MAXSIM = 5;
    public static final int PERMUTATION = 6;
    public static final int BALANCED_PERMUTATION = 7;
    public static final int PRINTQUERY = 8;

    public static final int YEARLY_FREQUENCY = 0;
    public static final int MONTHLY_FREQUENCY = 1;

    public static String[] SEARCH_METHODS = { "SUM", "SPARSESUM", "TENSOR", "CONVOLUTION", "SUBSPACE", "MAXSIM", "PERMUTATION",
            "BALANCED_PERMUTATION" };

    private static final Logger log = Logger.getLogger(SearchService.class.getName());

    private CorpusIndexingService corpusIndexingService;

    private VectorStoreBuildingService vectorStoreBuildingService;

    private UserVectorStore userVectorStore;

    /**
     * Search wrapper that returns the list of ObjectVectors.
     */
    public ObjectVector[] getSearchResultVectors(int searchType, Directory indexDir, String[] queryParams, int numResults,
            VectorStore termStore, VectorStore docStore) {
        LinkedList<pitt.search.semanticvectors.SearchResult> results = search(searchType, indexDir, queryParams, numResults, termStore,
                docStore);
        ObjectVector[] resultsList = new ObjectVector[results.size()];
        for (int i = 0; i < results.size(); ++i) {
            String term = ((ObjectVector) results.get(i).getObject()).getObject().toString();
            float[] tmpVector = termStore.getVector(term);
            resultsList[i] = new ObjectVector(term, tmpVector);
        }
        return resultsList;
    }

    /**
     * Takes a user's query, creates a query vector, and searches a vector
     * store.
     */
    public LinkedList<pitt.search.semanticvectors.SearchResult> search(int searchType, Directory indexDir, String[] queryParams,
            int numResults, VectorStore termStore, VectorStore docStore) {

        VectorStore queryVector, searchVector;
        LuceneUtils luceneUtils = null;

        searchVector = queryVector = termStore;
        try {
            luceneUtils = new LuceneUtils(indexDir);
        } catch (IOException e) {
            log.error("Couldn't open Lucene index.", e);
        }
        VectorSearcher vecSearcher = null;
        try {
            switch (searchType) {
            case SUM:
                // Primary method.
                vecSearcher = new VectorSearcher.VectorSearcherCosine(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case SPARSESUM:
                // Option for quantizing to sparse vectors before
                // comparing. This is for experimental purposes to see how much
                // we lose by compressing to a sparse bit vector.
                vecSearcher = new VectorSearcher.VectorSearcherCosineSparse(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case TENSOR:
                // Tensor product.
                vecSearcher = new VectorSearcher.VectorSearcherTensorSim(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case CONVOLUTION:
                // Convolution product.
                vecSearcher = new VectorSearcher.VectorSearcherConvolutionSim(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case SUBSPACE:
                // Quantum disjunction / subspace similarity.
                vecSearcher = new VectorSearcher.VectorSearcherSubspaceSim(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case MAXSIM:
                // Ranks by maximum similarity with any of the query terms.
                vecSearcher = new VectorSearcher.VectorSearcherMaxSim(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case PERMUTATION:
                // Permutes query vectors such that the most likely term in the
                // position of the "?" is retrieved
                vecSearcher = new VectorSearcher.VectorSearcherPerm(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case BALANCED_PERMUTATION:
                // Permutes query vectors such that the most likely term in the
                // position of the "?" is retrieved
                vecSearcher = new VectorSearcher.BalancedVectorSearcherPerm(queryVector, searchVector, luceneUtils, queryParams);
                break;
            case PRINTQUERY:
                float[] qV = CompoundVectorBuilder.getQueryVector(queryVector, luceneUtils, queryParams);
                VectorUtils.printVector(qV);
                return new LinkedList<pitt.search.semanticvectors.SearchResult>();
            }
        } catch (Exception err) {
            log.error("Zero vector", err);
        }
        return vecSearcher.getNearestNeighbors(numResults);
    }

    public Map<Integer, List<SearchResult>> getSearchResults(Corpus corpus, String[] searchTerms, int searchMethod, int fromYear,
            int toYear, int frequency, int numberOfResults) {

        Map<Integer, List<SearchResult>> results = new HashMap<Integer, List<SearchResult>>();

        switch (frequency) {
        case YEARLY_FREQUENCY:
            for (int i = fromYear; i < toYear; i++) {
                List<SearchResult> list = getSearchResultsForPeriod(corpus, searchTerms, searchMethod, i, i + 1, numberOfResults);
                log.info("year: " + i + " list: " + list.size());
                results.put(i, list);
            }
            break;
        case MONTHLY_FREQUENCY:
            log.error("Monthly search not implemented");
            break;
        }

        return results;
    }

    public String stemKeywords(Corpus corpus, String keywords) {
        try {
            Analyzer analyzer = null;
            if (corpus.getLang().equals("dutch")) {
                analyzer = new SnowballAnalyzer(Version.LUCENE_30, "Dutch", StopWords.getStopwordsSet(StopWords.DUTCH));
                log.info("Using dutch analyzer");
            } else {
                analyzer = new SnowballAnalyzer(Version.LUCENE_30, "English", StopWords.getStopwordsSet(StopWords.ENGLISH));
                log.info("Using english analyzer");
            }
            TokenStream stream = analyzer.tokenStream("field", new StringReader(keywords));
            TermAttribute termAtt = (TermAttribute) stream.getAttribute(TermAttribute.class);
            String stemmedKeywords = "";

            while (stream.incrementToken()) {
                stemmedKeywords += termAtt.term() + " ";
            }
            return stemmedKeywords.trim();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private List<SearchResult> getSearchResultsForPeriod(Corpus corpus, String[] searchTerms, int searchMethod, int fromYear, int toYear,
            int numberOfResults) {
        List<SearchResult> mapedResults = new ArrayList<nl.tudelft.tbm.noblesavage.domain.semantics.SearchResult>();

        Directory indexDir = corpusIndexingService.getIndex(corpus, fromYear, toYear);
        HashMap<String, VectorStore> vectorSpaceMap = null;
        if (userVectorStore.hasVectorStore(corpus, fromYear, toYear)) {
            vectorSpaceMap = userVectorStore.getVectorStore(corpus, fromYear, toYear);
            log.info("found vector store with the user");
        } else {
            try {
                vectorSpaceMap = vectorStoreBuildingService.build(indexDir);
                userVectorStore.setVectorStore(corpus, fromYear, toYear, vectorSpaceMap);
                log.info("created new vector store");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        try {
            List<pitt.search.semanticvectors.SearchResult> results = search(searchMethod, indexDir, searchTerms, numberOfResults,
                    vectorSpaceMap.get(VectorStoreBuildingService.TERMS), vectorSpaceMap.get(VectorStoreBuildingService.DOCUMENTS));
            for (int i = 0; i < results.size(); i++) {
                mapedResults.add(new SearchResult(results.get(i).getScore(), ((ObjectVector) results.get(i).getObject()).getObject()
                        .toString()));
            }
        } catch (Exception err) {
            log.error(err.getMessage());
        }
        return mapedResults;
    }

    public CorpusIndexingService getCorpusIndexingService() {
        return corpusIndexingService;
    }

    public void setCorpusIndexingService(CorpusIndexingService corpusIndexingService) {
        this.corpusIndexingService = corpusIndexingService;
    }

    public VectorStoreBuildingService getVectorStoreBuildingService() {
        return vectorStoreBuildingService;
    }

    public void setVectorStoreBuildingService(VectorStoreBuildingService vectorStoreBuildingService) {
        this.vectorStoreBuildingService = vectorStoreBuildingService;
    }

    public UserVectorStore getUserVectorStore() {
        return userVectorStore;
    }

    public void setUserVectorStore(UserVectorStore userVectorStore) {
        this.userVectorStore = userVectorStore;
    }

}