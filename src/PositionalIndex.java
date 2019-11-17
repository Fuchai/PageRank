public class PositionalIndex {
    String folderName;

    public PositionalIndex(String folderName) {
        this.folderName = folderName;
    }

    int termFrequency(String term, String doc){
        return 0;
    }

    String postingsList(String term){
        return "[<>]";
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
