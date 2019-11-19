import java.util.HashMap;

public class PositionalIndex {
    String folderName;
    HashMap<String, TDFT> dict;
    // term -> postings. A posting is all the indices of the term in a particular document
    HashMap<String, Posting[]> termPostings;

    public PositionalIndex(String folderName) {
        this.folderName = folderName;
    }

    int termFrequency(String term, String doc) {

        Posting[] postings = termPostings.get(term);
        for (Posting posting : postings
        ) {
            if (posting.doc.equals(doc)) {
                return posting.getPoss().length;
            }
        }
        return 0;
    }

    String postingsList(String term) {
        Posting[] postings = termPostings.get(term);
        String repr = "[";
        for (int i = 0; i < postings.length; i++) {
            if (i != 0) {
                repr += ",";
            }

            Posting posting = postings[i];
            String docRepr = "<";
            docRepr += posting.doc;
            docRepr += ":";
            for (int j = 0; j < posting.getPoss().length; j++) {
                if (j != 0) {
                    docRepr += ",";
                }
                docRepr += posting.getPoss()[j];
            }
            docRepr += ">";

            repr += docRepr;
        }
        repr += "]";
        return repr;
    }

    double weight(String term, String doc) {
        return 0;
    }

    double TPScore(String query, String doc) {
        String[] queryWords = query.split("\\s");
        if (queryWords.length == 1 || queryWords.length == 0) {
            return 0;
        }
        double denom = 0;
        for (int i = 0; i < queryWords.length - 1; i++) {
            String t1 = queryWords[i];
            String t2 = queryWords[i + 1];
            denom += dist(doc, t1, t2);
        }
        double ret = (double) queryWords.length / denom;
        return ret;
    }

    double dist(String doc, String t1, String t2) {
        Posting[] postings1 = termPostings.get(t1);
        Posting[] postings2 = termPostings.get(t2);

        int[] poss1 = new int[0];
        int[] poss2 = new int[0];
        for (Posting posting : postings1) {
            if (posting.doc.equals(doc)) {
                poss1 = posting.getPoss();
            }
        }


        for (Posting posting : postings2) {
            if (posting.doc.equals(doc)) {
                poss2 = posting.getPoss();
            }
        }

        if (poss1.length == 0 || poss2.length == 0) {
            return 17;
        }

        // we can do it in linear time, given that the postings are sorted.
        int i = 0;
        int j = 0;
        int minDiff = Integer.MAX_VALUE;
        while (i != poss1.length - 1 && j != poss2.length - 1) {
            if (poss1[i] < poss2[j]) {
                int newDiff = poss1[j] - poss1[i];
                if (newDiff < minDiff) {
                    minDiff = newDiff;
                }
                i++;
            } else {
                j++;
            }
        }
        return minDiff;
    }

    double VSScore(String query, String doc) {
        return 0;
    }

    double Relevance(String query, String doc) {
        return 0;
    }
}

class TDFT {
    public String term;
    public int docFreq;

    public TDFT(String term, int dft) {
        this.term = term;
        this.docFreq = dft;
    }
}

class Posting {
    public String doc;
    private int[] poss;

    public void setPoss(int[] poss) {
        this.poss = poss;
        int last = Integer.MIN_VALUE;
        for (int pos :
                poss) {
            if (pos < last) {
                System.out.println("You must sort the postings.");
                System.exit(-1);
            }
            last = pos;
        }
    }

    public int[] getPoss() {
        return poss;
    }
}
