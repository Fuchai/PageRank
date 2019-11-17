public class PageRank {
    String graphFileName;
    double epsilon;
    double beta;

    public static void main(String[] args) {
        System.out.println("Hello");
    }

    public PageRank(String graphFileName, double epsilon, double beta) {
        this.graphFileName = graphFileName;
        this.epsilon = epsilon;
        this.beta = beta;
    }

    double pageRankOf(int vertex){
        return 1;
    }

    double[] trustRank(){
        double[] trust = new double[0];
        return trust;
    }

    int numEdges(){
        return 0;
    }

    int[] topKPageRank(int k){
        int[] ret = new int[0];
        return ret;
    }
}
