package nl.tudelft.tbm.noblesavage.facade;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("corpus.rpc")
public interface CorpusFacade extends RemoteService {

    CorpusDTO[] getCachedCorpora();

    CorpusDTO createCorpus(HashMap<String, String> parameters);

    void deleteCorpus(CorpusDTO corpusDTO);

    int deleteNonUniqueArticlesInCorpus(CorpusDTO corpusDTO);

    void mergeCorpora(CorpusDTO[] corpusDTO, String name);

    int removeTextFromCorpus(CorpusDTO corpusDTO, String text);

    int replaceTextFromCorpus(CorpusDTO corpusDTO, String search, String replace);

    Map<Integer, SearchResultDTO[]> searchCorpus(CorpusDTO corpusDTO, String term, int searchMethod, int fromYear, int toYear,
            int frequency, int numberOfResults);

    String[] getSearchMethods();

    String[] getCorpusReaderTypes();

    ReaderParameterDTO[] getReaderParameters(String type);

    ArticleDTO[] getArticles(CorpusDTO corpusDTO);

    Map<String, Integer> getTermFrequency(CorpusDTO corpusDTO, int numberOfTerms);

    Integer getWordCount(CorpusDTO corpusDTO);

    Integer getArticleCount(CorpusDTO corpusDTO);

    String stemKeywords(CorpusDTO corpusDTO, String keywords);

}
