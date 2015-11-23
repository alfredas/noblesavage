package nl.tudelft.tbm.noblesavage.domain.corpus;

import java.util.HashMap;
import java.util.List;

public interface CorpusReader {

    public Corpus getCorpus(HashMap<String, String> parameters);

    public List<ReaderParameter> getParameters();
}
