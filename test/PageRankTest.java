import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class PageRankTest {

    @Test
    void topKPageRank() {

        PageRank pr = new PageRank("C:/Users/JasonHu/Desktop/data/WikiSportsGraph.txt", 0.1, 0.85);
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
}