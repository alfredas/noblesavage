package nl.tudelft.tbm.noblesavage.domain.semantics;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class StopWords {

    public static int ENGLISH = 0;
    public static int DUTCH = 1;

    static String[] stopWordsEnglish = { "a", "about", "above", "across", "after", "again", "against", "all", "almost", "alone", "along",
            "already", "also", "although", "always", "among", "an", "and", "another", "any", "anybody", "anyone", "anything", "anywhere",
            "are", "area", "areas", "around", "as", "ask", "asked", "asking", "asks", "at", "away", "b", "back", "backed", "backing",
            "backs", "be", "became", "because", "become", "becomes", "been", "before", "began", "behind", "being", "beings", "best",
            "better", "between", "big", "both", "but", "by", "c", "came", "can", "cannot", "case", "cases", "certain", "certainly",
            "clear", "clearly", "come", "could", "d", "did", "differ", "different", "differently", "do", "does", "done", "down", "down",
            "downed", "downing", "downs", "during", "e", "each", "early", "either", "end", "ended", "ending", "ends", "enough", "even",
            "evenly", "ever", "every", "everybody", "everyone", "everything", "everywhere", "f", "face", "faces", "fact", "facts", "far",
            "felt", "few", "find", "finds", "first", "for", "four", "from", "full", "fully", "further", "furthered", "furthering",
            "furthers", "g", "gave", "general", "generally", "get", "gets", "give", "given", "gives", "go", "going", "good", "goods",
            "got", "great", "greater", "greatest", "group", "grouped", "grouping", "groups", "h", "had", "has", "have", "having", "he",
            "her", "here", "herself", "high", "high", "high", "higher", "highest", "him", "himself", "his", "how", "however", "i", "if",
            "important", "in", "interest", "interested", "interesting", "interests", "into", "is", "it", "its", "itself", "j", "just", "k",
            "keep", "keeps", "kind", "knew", "know", "known", "knows", "l", "large", "largely", "last", "later", "latest", "least", "less",
            "let", "lets", "like", "likely", "long", "longer", "longest", "m", "made", "make", "making", "man", "many", "may", "me",
            "member", "members", "men", "might", "more", "most", "mostly", "mr", "mrs", "much", "must", "my", "myself", "n", "necessary",
            "need", "needed", "needing", "needs", "never", "new", "new", "newer", "newest", "next", "no", "nobody", "non", "noone", "not",
            "nothing", "now", "nowhere", "number", "numbers", "o", "of", "off", "often", "old", "older", "oldest", "on", "once", "one",
            "only", "open", "opened", "opening", "opens", "or", "order", "ordered", "ordering", "orders", "other", "others", "our", "out",
            "over", "p", "part", "parted", "parting", "parts", "per", "perhaps", "place", "places", "point", "pointed", "pointing",
            "points", "possible", "present", "presented", "presenting", "presents", "problem", "problems", "put", "puts", "q", "quite",
            "r", "rather", "really", "right", "right", "room", "rooms", "rss", "s", "said", "same", "saw", "say", "says", "second",
            "seconds", "see", "seem", "seemed", "seeming", "seems", "sees", "several", "shall", "she", "should", "show", "showed",
            "showing", "shows", "side", "sides", "since", "small", "smaller", "smallest", "so", "some", "somebody", "someone", "something",
            "somewhere", "state", "states", "still", "still", "such", "sure", "t", "take", "taken", "than", "that", "the", "their", "them",
            "then", "there", "therefore", "these", "they", "thing", "things", "think", "thinks", "this", "those", "though", "thought",
            "thoughts", "three", "through", "thus", "to", "today", "together", "too", "took", "toward", "turn", "turned", "turning",
            "turns", "two", "u", "under", "until", "up", "upon", "us", "use", "used", "uses", "v", "very", "w", "want", "wanted",
            "wanting", "wants", "was", "way", "ways", "we", "well", "wells", "went", "were", "what", "when", "where", "whether", "which",
            "while", "who", "whole", "whose", "why", "will", "with", "within", "without", "work", "worked", "working", "works", "would",
            "x", "y", "year", "years", "yet", "you", "young", "younger", "youngest", "your", "yours", "z" };

    static String[] stopWordsDutch = { "de", "en", "van", "ik", "te", "dat", "die", "in", "een", "hij", "het", "niet", "zijn", "is", "was",
            "op", "aan", "met", "als", "voor", "had", "er", "maar", "om", "hem", "dan", "zou", "of", "wat", "mijn", "men", "dit", "zo",
            "door", "over", "ze", "zich", "bij", "ook", "tot", "je", "mij", "uit", "der", "daar", "haar", "naar", "heb", "hoe", "heeft",
            "hebben", "deze", "u", "want", "nog", "zal", "me", "zij", "nu", "ge", "geen", "omdat", "iets", "worden", "toch", "al", "waren",
            "veel", "meer", "doen", "toen", "moet", "ben", "zonder", "kan", "hun", "dus", "alles", "onder", "ja", "eens", "hier", "wie",
            "werd", "altijd", "doch", "wordt", "wezen", "kunnen", "ons", "zelf", "tegen", "na", "reeds", "wil", "kon", "niets", "uw",
            "iemand", "geweest", "andere" };

    public static List<String> getStopwordsList(int language) {
        if (language == ENGLISH) {
            return Collections.unmodifiableList(Arrays.asList(stopWordsEnglish));
        } else if (language == DUTCH) {
            return Collections.unmodifiableList(Arrays.asList(stopWordsDutch));
        }
        return null;
    }

    public static Set<String> getStopwordsSet(int language) {
        return new HashSet<String>(getStopwordsList(language));
    }

}
