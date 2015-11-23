package nl.tudelft.tbm.noblesavage.domain.semantics;

import nl.tudelft.tbm.noblesavage.domain.corpus.Corpus;
import nl.tudelft.tbm.noblesavage.domain.corpus.CorpusStore;
import nl.tudelft.tbm.noblesavage.domain.semantics.RAMDirWrapper.DirectoryWrap;

import org.apache.log4j.Logger;
import org.apache.lucene.store.Directory;

public class CachingCorpusIndexingService implements CorpusIndexingService {

  private static final Logger log = Logger.getLogger(CachingCorpusIndexingService.class);

  private CorpusStore corpusStore;
  private CorpusIndexingService fallbackIndexingService;

  public Directory getIndex(Corpus c) {
    log.info("getting corpus");
    Corpus corpus = corpusStore.findById(c.getId());
    RAMDirWrapper wrapper = new RAMDirWrapper();
    if (corpus != null) {
      byte[] index = corpus.getIndex();
      String header = corpus.getIndexheader();
      if ((index != null) && (header != null)) {
        log.info("got cached corpus index with length: " + index.length);
        DirectoryWrap wrap = wrapper.new DirectoryWrap(header, index);
        Directory directory = wrapper.unwrap(wrap);
        return directory;
      } else {
        log.info("did not find index - going to create one using " + fallbackIndexingService.getClass().getName());
        Directory directory = fallbackIndexingService.getIndex(c);
        DirectoryWrap wrap = wrapper.wrap(directory);
        corpus.setIndexheader(wrap.getHeader());
        corpus.setIndex(wrap.getContents());
        corpusStore.save(corpus);
        log.info("created and saved index for corpus #" + corpus.getId() + " '" + corpus.getName() + "'");
        return directory;
      }
    } else {
      log.error("did not find corpus " + c);
    }
    return null;
  }

  public Directory getIndex(Corpus c, int fromYear, int toYear) {
    return fallbackIndexingService.getIndex(c, fromYear, toYear);
  }

  public CorpusStore getCorpusStore() {
    return corpusStore;
  }

  public void setCorpusStore(CorpusStore corpusStore) {
    this.corpusStore = corpusStore;
  }

  public CorpusIndexingService getFallbackIndexingService() {
    return fallbackIndexingService;
  }

  public void setFallbackIndexingService(CorpusIndexingService fallbackIndexingService) {
    this.fallbackIndexingService = fallbackIndexingService;
  }

}
