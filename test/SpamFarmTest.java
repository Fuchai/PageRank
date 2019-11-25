import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SpamFarmTest {

	private PageRank pr = new PageRank(DataPath.dataPath+"/correctGraph.txt", 0.1, 0.85);
	private int target = Integer.parseInt(pr.nodeName[pr.getMinIndex(pr.pageRank())]);

	@Test
	void CreateAllSpamFarm() {
		System.out.println("TARGET INDEX : " + target);
		SpamFarm sf = new SpamFarm(DataPath.dataPath+"/correctGraph.txt", target, 500);
        try {
			sf.createSpam(DataPath.dataPath+"/testfile.txt");
			System.out.println(sf.getTargetString());
			System.out.println(pr.nodeName[target - 1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void FarmTargetPageRank() {
		PageRank pr1 = new PageRank(DataPath.dataPath+"/testfile.txt", 0.1, 0.85);


		double[] ret=pr.trustRank(pr.pageRank());
		double[] ret1=pr1.trustRank(pr1.pageRank());

		System.out.println("Page rank of target pr   : " + pr.pageRankOf(target - 1));
		System.out.println("Page rank of target pr1  : " + pr1.pageRankOf(target - 1));

		System.out.println("Page Trust of target pr  : " + pr.trustRank[target - 1]);
		System.out.println("Page Trust of target pr1 : " + pr1.trustRank[target - 1]);

		System.out.println("Top 10 Page Rank of pr   : " + Arrays.toString(pr.topKPageRank(10)));
		System.out.println("Top 10 Page Rank of pr1  : " + Arrays.toString(pr1.topKPageRank(10)));

		TopKHelper t = new TopKHelper();
		t.topK(ret, 10);
		System.out.println("Top 10 Trust Rank of pr  : " + Arrays.toString(t.indices));
		t.topK(ret1, 10);
		System.out.println("Top 10 Trust Rank of pr1 : " + Arrays.toString(t.indices));
	}
}
