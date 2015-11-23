package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.io.IOException;
import java.util.HashMap;
import org.apache.log4j.Logger;

import org.apache.lucene.store.Directory;

import pitt.search.semanticvectors.DocVectors;
import pitt.search.semanticvectors.TermVectorsFromLucene;
import pitt.search.semanticvectors.VectorStore;

public class VectorStoreBuildingService {

  private static int SEED_LENGTH = 20;
  private static int MIN_FREQUENCY = 10;
  private static int NON_ALPHA_CHARS = 0;
  private static int TRAINING_CYCLES = 0;

  public static String TERMS = "terms";
  public static String DOCUMENTS = "documents";

  private static boolean INMEMORY_INDEXING = true;
  private static String[] contentsfields = { "contents" };
  private static boolean RANDOM_VECTORS = false;

  private static final Logger log = Logger.getLogger(VectorStoreBuildingService.class.getName());

  /**
   * Builds term vector and document vector stores from a Lucene index
   * (directory).
   */
  public HashMap<String, VectorStore> build(Directory directory) throws IOException {

    TermVectorsFromLucene termVectors = null;
    DocVectors docVectors = null;

    log.info("Building WordSpace:");

    // CREATE TERM VECTORS
    if (RANDOM_VECTORS) {
      // Create elemental (random index) term vectors. Recommended to
      // iterate at least once (i.e. training cycles = 2) to
      // obtain semantic term vectors
      log.info("1a) Creating random term vectors ...");
      termVectors = new TermVectorsFromLucene(directory, SEED_LENGTH, MIN_FREQUENCY, NON_ALPHA_CHARS, contentsfields);
    } else {
      log.info("1b) Creating semantic term vectors ...");
      termVectors = new TermVectorsFromLucene(directory, SEED_LENGTH, MIN_FREQUENCY, NON_ALPHA_CHARS, null, contentsfields);
    }

    // CREATE DOCUMENT VECTORS
    if (INMEMORY_INDEXING) {
      log.info("2a) Creating document vectors 'in memory'");
      docVectors = new DocVectors(termVectors);

      // TRAIN
      for (int i = 0; i < TRAINING_CYCLES; i++) {
        log.info("Retraining with learned document vectors. Round: " + i);
        termVectors = new TermVectorsFromLucene(directory, SEED_LENGTH, MIN_FREQUENCY, NON_ALPHA_CHARS, docVectors, contentsfields);
        docVectors = new DocVectors(termVectors);
      }

    } else {
      log.info("2b) Creating document vectors 'incremental'");
      // vecStore gets written to termFile
      // vecWriter.WriteVectors(termFile, vecStore);
      // IncrementalDocVectors idocVectors = new
      // IncrementalocVectors(vecStore, directory, Flags.contentsfields,
      // docFileName);
    }

    log.info("3) Returning vectors");
    // At end of training, convert document vectors from ID keys to pathname
    // keys.
    VectorStore writeableDocVectors = docVectors.makeWriteableVectorStore();
    
    HashMap<String, VectorStore> returnMap = new HashMap<String, VectorStore>();
    returnMap.put(TERMS, termVectors);
    returnMap.put(DOCUMENTS, writeableDocVectors);

    return returnMap;
  }
}
