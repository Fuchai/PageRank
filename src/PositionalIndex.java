import java.util.HashMap;

public class PositionalIndex {
    String folderName;
    HashMap<String, TDFT> dict;
    HashMap<String, Posting[]> termPostings;

    public PositionalIndex(String folderName) {
        this.folderName = folderName;
    }

    int termFrequency(String term, String doc){
        Posting[] postings = termPostings.get(term);
        for (Posting posting:postings
             ) {
            if(posting.doc.equals(doc)){
                return posting.poss.length;
            }
        }
        return 0;
    }

    int docFrequency(String term){
        return dict.get(term).docFreq;
    }

    String postingsList(String term){
        Posting[] postings = termPostings.get(term);
        String repr="[";
        for (int i = 0; i < postings.length; i++) {
            if (i != 0) {
                repr+=",";
            }

            Posting posting=postings[i];
            String docRepr="<";
            docRepr+=posting.doc;
            docRepr+=":";
            for (int j = 0; j < posting.poss.length; j++) {
                if (j!=0){
                    docRepr+=",";
                }
                docRepr+=posting.poss[j];
            }
            docRepr+=">";

            repr+=docRepr;
        }
        repr+="]";
        return repr;
    }

    double weight(String term, String doc){
        return 0;
    }

    double TPScore(String query, String doc){
        return 0;
    }

    double VSScore(String query, String doc){
        return 0;
    }

    double Relevance(String query, String doc){
        return 0;
    }
}

class TDFT{
    public String term;
    public int docFreq;

    public TDFT(String term, int dft) {
        this.term = term;
        this.docFreq = dft;
    }
}

class Posting{
    public String doc;
    public int[] poss;
}