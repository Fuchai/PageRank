import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SpamFarmTest {
	
	private PageRank pr = new PageRank(DataPath.dataPath+"/WikiSportsGraph.txt", 0.1, 0.85);
	private int target = pr.getMinIndex(pr.pageRank()) + 1;
	
	@Test
	void CreateAllSpamFarm() {
		System.out.println("TARGET INDEX : " + target);
		System.out.println("Target page: "+ pr.nodeName[target]);
		SpamFarm sf = new SpamFarm(DataPath.dataPath+"/WikiSportsGraph.txt", target, 500);
        try {
			sf.createSpam(DataPath.dataPath+"/testfile.txt");
			sf.createSpam2(DataPath.dataPath+"/testfile2.txt");
			sf.createSpam3(DataPath.dataPath+"/testfile3.txt");
			System.out.println(sf.getTargetString());
			System.out.println(pr.nodeName[target - 1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void FarmTargetPageRank() {
		PageRank pr1 = new PageRank(DataPath.dataPath+"/testfile.txt", 0.1, 0.85);
		PageRank pr2 = new PageRank(DataPath.dataPath+"/testfile2.txt", 0.1, 0.85);
		PageRank pr3 = new PageRank(DataPath.dataPath+"/testfile3.txt", 0.1, 0.85);
		
		
		double[] ret=pr.trustRank(pr.pageRank());
		double[] ret1=pr1.trustRank(pr1.pageRank());
		double[] ret2=pr2.trustRank(pr2.pageRank());
		double[] ret3=pr3.trustRank(pr3.pageRank());
		
		System.out.println("Page rank of target pr   : " + pr.pageRankOf(target - 1));
		System.out.println("Page rank of target pr1  : " + pr1.pageRankOf(target - 1));
		System.out.println("Page rank of target pr2  : " + pr2.pageRankOf(target - 1));
		System.out.println("Page rank of target pr3  : " + pr3.pageRankOf(target - 1));
		
		System.out.println("Page Trust of target pr  : " + pr.trustRank[target - 1]);
		System.out.println("Page Trust of target pr1 : " + pr1.trustRank[target - 1]);
		System.out.println("Page Trust of target pr2 : " + pr2.trustRank[target - 1]);
		System.out.println("Page Trust of target pr3 : " + pr3.trustRank[target - 1]);
		
		System.out.println("Top 10 Page Rank of pr   : " + Arrays.toString(pr.topKPageRank(10)));
		System.out.println("Top 10 Page Rank of pr1  : " + Arrays.toString(pr1.topKPageRank(10)));
		System.out.println("Top 10 Page Rank of pr2  : " + Arrays.toString(pr2.topKPageRank(10)));
		System.out.println("Top 10 Page Rank of pr3  : " + Arrays.toString(pr3.topKPageRank(10)));
		
		TopKHelper t = new TopKHelper();
		t.topK(ret, 10);
		System.out.println("Top 10 Trust Rank of pr  : " + Arrays.toString(t.indices));
		t.topK(ret1, 10);
		System.out.println("Top 10 Trust Rank of pr1 : " + Arrays.toString(t.indices));
		t.topK(ret2, 10);
		System.out.println("Top 10 Trust Rank of pr2 : " + Arrays.toString(t.indices));
		t.topK(ret3, 10);
		System.out.println("Top 10 Trust Rank of pr3 : " + Arrays.toString(t.indices));
	}
}
