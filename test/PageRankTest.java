import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class PageRankTest {

    @Test
    void topKPageRank() {
        String dataPath=DataPath.dataPath;
        PageRank pr = new PageRank(dataPath+"/correctGraph.txt", 0.1, 0.85);
        int k = 5;
        int[] topkr = pr.topKPageRank(k);
        System.out.println(Arrays.toString(topkr));
        double[] rank = pr.rank;
        for (int bigval : topkr
        ) {
            int cnt = 0;
            for (double r : rank
            ) {
                if (bigval < r) {
                    cnt++;
                }
                if (cnt==6){
                    assertTrue(cnt != 6);
                }
            }
        }
    }
    
    @Test
    void numEdges() {
    	PageRank pr = new PageRank(DataPath.dataPath+"/correctGraph.txt", 0.01, 0.85);
    	System.out.println("numEdges : " + pr.numEdges());
    }

    @Test
    void main() {
        System.out.println("epsilon=0.01, beta=0.85");
        PageRank pr = new PageRank(DataPath.dataPath+"/correctGraph.txt", 0.01, 0.85);
        System.out.println("Steps:"+pr.stepsTaken);
        System.out.println("epsilon=0.01, beta=0.25");
        pr = new PageRank(DataPath.dataPath+"/WikiSportsGraph.txt", 0.01, 0.25);
        System.out.println("Steps:"+pr.stepsTaken);
    }

    @Test
    void trustRank() {
        PageRank pr = new PageRank(DataPath.dataPath+"/correctGraph.txt", 0.01, 0.85);
        double[] ret=pr.trustRank(pr.pageRank());
        System.out.println(Arrays.toString(ret));
        System.out.println("Steps taken for trust rank: "+pr.trustStepsTaken);
    }
    
    
}