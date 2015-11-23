package nl.tudelft.tbm.noblesavage.facadeimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.tudelft.tbm.noblesavage.domain.article.Article;
import nl.tudelft.tbm.noblesavage.domain.article.ArticleStore;
import nl.tudelft.tbm.noblesavage.domain.article.parsers.ArticleParser;
import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusReader;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusReaderType;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusStore;
import nl.tudelft.tbm.noblesavage.domain.semantics.CorpusStatisticsService;
import nl.tudelft.tbm.noblesavage.domain.semantics.SearchResult;
import nl.tudelft.tbm.noblesavage.domain.semantics.SearchService;
import nl.tudelft.tbm.noblesavage.facade.ArticleDTO;
import nl.tudelft.tbm.noblesavage.facade.CorpusDTO;
import nl.tudelft.tbm.noblesavage.facade.CorpusFacade;
import nl.tudelft.tbm.noblesavage.facade.ReaderParameterDTO;
import nl.tudelft.tbm.noblesavage.facade.SearchResultDTO;
import nl.tudelft.tbm.noblesavage.facadeimpl.mapping.BeanMapper;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class CorpusFacadeImpl implements CorpusFacade {

    private CorpusStore corpusStore;
    private ArticleStore articleStore;
    private BeanMapper beanMapper;
    private SearchService searchService;
    private CorpusStatisticsService corpusStatisticsService;

    public final static String READER_TYPE_STRING = "readerType";

    private static final Logger log = Logger.getLogger(CorpusFacadeImpl.class);

    @Transactional
    public CorpusDTO[] getCachedCorpora() {
        log.info("called getCachedCorpora");
        List<Corpus> list = null;
        try {
            list = corpusStore.findAll();

            if ((list != null) && (list.size() > 0)) {
                log.info("getCachedCorpora: got " + list.size() + " corpora");
                return beanMapper.mapList(list, CorpusDTO.class).toArray(new CorpusDTO[0]);
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        log.warn("No corpora found. List was " + ((list == null) ? "null" : "empty"));
        return null;
    }

    @Transactional
    public CorpusDTO createCorpus(HashMap<String, String> parameters) {
        log.info("called createCorpus");
        String readerType = parameters.get(READER_TYPE_STRING);
        try {
            CorpusReader reader = (CorpusReader) CorpusReaderType.getReaderTypeByTitle(readerType).getReaderClass().newInstance();
            Corpus corpus = reader.getCorpus(parameters);

            if ((corpus != null) && (corpus.getArticles() != null) && (corpus.getArticles().size() > 0)) {
                ArticleParser parser = (ArticleParser) CorpusReaderType.getReaderTypeByTitle(readerType).getParserClass().newInstance();
                for (Article article : corpus.getArticles()) {
                    String text = parser.getText(article);
                    if (text != null) {
                        article.setText(text);
                    }
                }

                corpusStore.save(corpus);

                for (Article article : corpus.getArticles()) {
                    article.setCorpus(corpus);
                    articleStore.save(article);
                }
                log.info("createCorpus: saved '" + corpus.getName() + "' corpus with id: " + corpus.getId() + "; #"
                        + corpus.getArticles().size() + "' articles");
                return beanMapper.map(corpus, CorpusDTO.class);
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }

        return null;
    }

    @Transactional
    public void deleteCorpus(CorpusDTO corpusDTO) {
        log.info("called deleteCorpus");
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                String name = corpus.getName();
                corpusStore.delete(corpus);
                log.info("deleteCorpus: deleted '" + name + "' corpus");
            } else {
                log.error("Corpus with id=" + corpusDTO.getId() + " was not found");
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
    }

    @Transactional
    public int deleteNonUniqueArticlesInCorpus(CorpusDTO corpusDTO) {
        log.info("called deleteNonUniqueArticlesInCorpus");
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                log.info("deleteNonUniqueArticlesInCorpus: deleted non-unique articles in corpus '" + corpus.getName());
                return articleStore.deleteNonUniqueArticlesInCorpus(corpus);
            } else {
                log.error("Corpus with id=" + corpusDTO.getId() + " was not found");
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return -1;
    }

    @Transactional
    public int removeTextFromCorpus(CorpusDTO corpusDTO, String text) {
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                int count = 0;
                for (Article article : corpus.getArticles()) {
                    String articleText = article.getText();
                    if (articleText.indexOf(text) >= 0) {
                        article.setText(articleText.replace(text, ""));
                        articleStore.save(article);
                        count++;
                    }
                }
                return count;
            } else {
                log.error("Corpus with id=" + corpusDTO.getId() + " was not found");
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return -1;
    }

    @Transactional
    public int replaceTextFromCorpus(CorpusDTO corpusDTO, String search, String replace) {
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                int count = 0;
                for (Article article : corpus.getArticles()) {
                    String articleText = article.getText();
                    if (articleText.indexOf(search) >= 0) {
                        article.setText(articleText.replace(search, replace));
                        articleStore.save(article);
                        count++;
                    }
                }
                return count;
            } else {
                log.error("Corpus with id=" + corpusDTO.getId() + " was not found");
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return -1;
    }

    @Transactional
    public void mergeCorpora(CorpusDTO[] corpusDTO, String name) {
        if (corpusDTO != null && corpusDTO.length > 0 && name != null) {
            try {
                ArrayList<Article> articles = new ArrayList<Article>();
                for (int i = 0; i < corpusDTO.length; i++) {
                    CorpusDTO c = corpusDTO[i];
                    Corpus corpus = corpusStore.findById(c.getId());
                    for (Article article : corpus.getArticles()) {
                        Article newArticle = new Article(article.getUrl(), article.getTitle(), article.getText(), null,
                                article.getDateCreated(), article.getTags());
                        articles.add(newArticle);
                    }
                }

                Corpus corpus = new Corpus(name, "", "MERGED", articles, "");

                corpusStore.save(corpus);

                for (Article article : corpus.getArticles()) {
                    article.setCorpus(corpus);
                    articleStore.save(article);
                }
            } catch (Exception err) {
                log.error("Error merging corpora", err);
            }
        } else {
            log.error("missing name or corpora to merge");
        }
    }

    @Transactional
    public Map<Integer, SearchResultDTO[]> searchCorpus(CorpusDTO corpusDTO, String term, int searchMethod, int fromYear, int toYear,
            int frequency, int numberOfResults) {

        log.info("called searchCorpus");
        String[] searchTerms = term.split(" ");

        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                log.info("searchCorpus: searching corpus " + corpus.getName());

                Map<Integer, List<SearchResult>> map = searchService.getSearchResults(corpus, searchTerms, searchMethod, fromYear, toYear,
                        frequency, numberOfResults);

                Map<Integer, SearchResultDTO[]> resultMap = new HashMap<Integer, SearchResultDTO[]>();
                for (Integer i : map.keySet()) {
                    resultMap.put(i, beanMapper.mapList(map.get(i), SearchResultDTO.class).toArray(new SearchResultDTO[0]));
                }
                return resultMap;
            } else {
                log.warn("searchCorpus: corpus is null");
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return null;
    }

    @Transactional
    public String stemKeywords(CorpusDTO corpusDTO, String keywords) {
        Corpus corpus = corpusStore.findById(corpusDTO.getId());
        return searchService.stemKeywords(corpus, keywords);
    }

    public String[] getSearchMethods() {
        log.info("called getSearchMethods");
        return SearchService.SEARCH_METHODS;
    }

    public String[] getCorpusReaderTypes() {
        log.info("called getCorpusReaderTypes");
        CorpusReaderType[] types = CorpusReaderType.values();
        String[] names = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            names[i] = types[i].toString();
        }
        return names;
    }

    public ReaderParameterDTO[] getReaderParameters(String name) {
        log.info("called getReaderParameters");
        try {
            CorpusReader reader = (CorpusReader) CorpusReaderType.getReaderTypeByTitle(name).getReaderClass().newInstance();
            return beanMapper.mapList(reader.getParameters(), ReaderParameterDTO.class).toArray(new ReaderParameterDTO[0]);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return new ReaderParameterDTO[0];
    }

    @Transactional
    public ArticleDTO[] getArticles(CorpusDTO corpusDTO) {
        log.info("called getArticles");
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if ((corpus != null) && (corpus.getArticles() != null) && (corpus.getArticles().size() > 0)) {
                log.info("getArticles: got copus " + corpus.getName() + " with " + corpus.getArticles().size() + " articles");
                return beanMapper.mapList(corpus.getArticles(), ArticleDTO.class).toArray(new ArticleDTO[0]);
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }

        log.warn("getArticles: either corpus was null or it has no articles");
        return null;
    }

    @Transactional
    public Map<String, Integer> getTermFrequency(CorpusDTO corpusDTO, int numberOfTerms) {
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                return corpusStatisticsService.getTermFrequency(corpus, numberOfTerms);
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return null;
    }

    @Transactional
    public Integer getWordCount(CorpusDTO corpusDTO) {
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                return corpusStatisticsService.getWordCount(corpus);
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return -1;
    }

    @Transactional
    public Integer getArticleCount(CorpusDTO corpusDTO) {
        try {
            Corpus corpus = corpusStore.findById(corpusDTO.getId());
            if (corpus != null) {
                return corpus.getArticles().size();
            }
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return -1;
    }

    public CorpusStore getCorpusStore() {
        return corpusStore;
    }

    public void setCorpusStore(CorpusStore corpusStore) {
        this.corpusStore = corpusStore;
    }

    public ArticleStore getArticleStore() {
        return articleStore;
    }

    public void setArticleStore(ArticleStore articleStore) {
        this.articleStore = articleStore;
    }

    public BeanMapper getBeanMapper() {
        return beanMapper;
    }

    public void setBeanMapper(BeanMapper beanMapper) {
        this.beanMapper = beanMapper;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public CorpusStatisticsService getCorpusStatisticsService() {
        return corpusStatisticsService;
    }

    public void setCorpusStatisticsService(CorpusStatisticsService corpusStatisticsService) {
        this.corpusStatisticsService = corpusStatisticsService;
    }

}
