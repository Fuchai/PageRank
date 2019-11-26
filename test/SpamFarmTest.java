import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SpamFarmTest {

	private PageRank pr = new PageRank(DataPath.dataPath+"/correctGraph.txt", 0.1, 0.85);
	private int target = Integer.parseInt(pr.nodeName[pr.getMinIndex(pr.pageRank())]);

	@Test
	void CreateAllSpamFarm() {
		System.out.println("Target node name : " + target);
		SpamFarm sf = new SpamFarm(DataPath.dataPath+"/correctGraph.txt", target, 500);
        try {
			sf.createSpam(DataPath.dataPath+"/testfile.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void FarmTargetPageRank() {
		System.out.println("target node name: "+target);
		System.out.println("internal node index: "+pr.nameNode.get(""+target));
		PageRank spamRank = new PageRank(DataPath.dataPath+"/testfile.txt", 0.1, 0.85);


		double[] ret=pr.trustRank(pr.pageRank());
		double[] ret1=spamRank.trustRank(spamRank.pageRank());

		System.out.println("Page rank of target pr   : " + pr.pageRankOf(target));
		System.out.println("Page rank of target spamRank  : " + spamRank.pageRankOf(target));

		System.out.println("Page trust rank of target pr  : " + pr.trustRank[pr.nameNode.get(""+target)]);
		System.out.println("Page trust rank of target spamRank : " + spamRank.trustRank[spamRank.nameNode.get(""+target)]);

		System.out.println("Top 10 Page Rank of pr   : " + Arrays.toString(pr.topKPageRank(10)));
		System.out.println("Top 10 Page Rank of spamRank  : " + Arrays.toString(spamRank.topKPageRank(10)));

		TopKHelper t = new TopKHelper();
		t.topK(ret, 10);
		System.out.println("Top 10 Trust Rank of pr  : " + Arrays.toString(pr.indicesToNames(t.indices)));
		t.topK(ret1, 10);
		System.out.println("Top 10 Trust Rank of spamRank : " + Arrays.toString(spamRank.indicesToNames(t.indices)));
	}
}
