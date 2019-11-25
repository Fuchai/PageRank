import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SpamFarmTest {
	
	public static void main(String[] args) {
		CreateAllSpamFram();
		FarmTargetPageRank();
	}
	
	@Test
	static
	void CreateAllSpamFram() {
		int target = 2;
		SpamFarm sf = new SpamFarm(DataPath.dataPath+"/WikiSportsGraph.txt", target);
        try {
			sf.createSpam(DataPath.dataPath+"/testfile.txt");
			sf.createSpam2(DataPath.dataPath+"/testfile2.txt");
			sf.createSpam3(DataPath.dataPath+"/testfile3.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	static
	void FarmTargetPageRank() {
		PageRank pr = new PageRank(DataPath.dataPath+"/WikiSportsGraph.txt", 0.1, 0.85);
		PageRank pr1 = new PageRank(DataPath.dataPath+"/testfile.txt", 0.1, 0.85);
		PageRank pr2 = new PageRank(DataPath.dataPath+"/testfile2.txt", 0.1, 0.85);
		PageRank pr3 = new PageRank(DataPath.dataPath+"/testfile3.txt", 0.1, 0.85);
		
		System.out.println("Page rank of target pr : " + pr.pageRankOf(1));
		System.out.println("Page rank of target pr1: " + pr1.pageRankOf(1));
		System.out.println("Page rank of target pr2: " + pr2.pageRankOf(1));
		System.out.println("Page rank of target pr3: " + pr3.pageRankOf(1));
		
		System.out.println("Page rank among top: " + pr.pageRankOf(354));
		
		System.out.println("Top 5 Page Rank of pr  : " + Arrays.toString(pr.topKPageRank(5)));
		System.out.println("Top 5 Page Rank of pr1 : " + Arrays.toString(pr1.topKPageRank(5)));
		System.out.println("Top 5 Page Rank of pr2 : " + Arrays.toString(pr2.topKPageRank(5)));
		System.out.println("Top 5 Page Rank of pr3 : " + Arrays.toString(pr3.topKPageRank(5)));
	}
}
