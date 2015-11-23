package nl.tudelft.tbm.noblesavage.facade;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CorpusFacadeAsync {

    void getCachedCorpora(AsyncCallback<CorpusDTO[]> callback);

    void createCorpus(HashMap<String, String> parameters, AsyncCallback<CorpusDTO> callback);

    void deleteCorpus(CorpusDTO corpusDTO, AsyncCallback<Void> callback);

    void deleteNonUniqueArticlesInCorpus(CorpusDTO corpusDTO, AsyncCallback<Integer> callback);

    void mergeCorpora(CorpusDTO[] corpusDTO, String name, AsyncCallback<Void> callback);

    void removeTextFromCorpus(CorpusDTO corpusDTO, String text, AsyncCallback<Integer> callback);

    void replaceTextFromCorpus(CorpusDTO corpusDTO, String search, String replace, AsyncCallback<Integer> callback);

    void searchCorpus(CorpusDTO corpusDTO, String term, int searchMethod, int fromYear, int toYear, int frequency, int numberOfResults,
            AsyncCallback<Map<Integer, SearchResultDTO[]>> callback);

    void getSearchMethods(AsyncCallback<String[]> callback);

    void getCorpusReaderTypes(AsyncCallback<String[]> callback);

    void getReaderParameters(String type, AsyncCallback<ReaderParameterDTO[]> callback);

    void getArticles(CorpusDTO corpusDTO, AsyncCallback<ArticleDTO[]> callback);

    void getTermFrequency(CorpusDTO corpusDTO, int numberOfTerms, AsyncCallback<Map<String, Integer>> callback);

    void getWordCount(CorpusDTO corpusDTO, AsyncCallback<Integer> callback);

    void getArticleCount(CorpusDTO corpusDTO, AsyncCallback<Integer> callback);

    void stemKeywords(CorpusDTO corpusDTO, String keywords, AsyncCallback<String> callback);

}
