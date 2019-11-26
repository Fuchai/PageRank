import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PositionalIndexTest {

	@Test
	void weightVSS() {
		PositionalIndex pi=new PositionalIndex("./test resources/files");
		System.out.println(pi.numDoc);
		System.out.println(pi.termFrequency("bbbb", "ab.txt"));
		System.out.println(pi.docFrequency("bbbb"));
        System.out.println(pi.weight("bbbb","ab.txt"));
	}

    @Test
    void initTermPosting() {
        PositionalIndex pi=new PositionalIndex(DataPath.dataPath+"/IR");
        System.out.println(pi.docFrequency("ball"));
        System.out.println(pi.postingsList("ball"));
    }

    @Test
    void initTermPosting2() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        System.out.println(pi.docFrequency("asdf"));
        System.out.println(pi.postingsList("asdf"));
    }

    @Test
    void allUniqueTerms() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        System.out.println(String.join(", ",pi.allUniqueTerms()));
    }

    @Test
    void termFrequency() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        assertTrue(pi.termFrequency("aaaa","ab.txt")==3);
        assertTrue(pi.termFrequency("aaa","ab.txt")==3);
        assertTrue(pi.termFrequency("bbbb","ab.txt")==4);
        assertTrue(pi.termFrequency("bbb","ab.txt")==3);
        assertTrue(pi.termFrequency("bbbb","b.txt")==4);

    }

    @Test
    void TPScore1() {
        PositionalIndex pi=new PositionalIndex("./test resources/files");
        double tps=pi.TPScore("b a","ab.txt");
        assertTrue(tps-0.2<1e-4);
    }

    @Test
    void VSScore1() {
        PositionalIndex pi = new PositionalIndex("./test resources/files");
        System.out.println(pi.VSScore("b a", "ab.txt"));
    }

    @Test
    void VSScoreManual() {
        PositionalIndex pi = new PositionalIndex("./test resources/files");
        System.out.println(pi.VSScore("b a", "ab.txt"));
        // both b and a have dft=2
        // N=3
        // in target doc, both have 1 occurrence
        System.out.println("vj:("+1*Math.log10(3.0/2)+","+1*Math.log10(3.0/2)+")");
        // both have the same angle: \pi/4
    }

    @Test
    void VSScoreManual2() {
        // N=3
        // tf b=1
        // tf aa=2
        // df b=2
        // df aa=4
        // hand calculated
        // b, then aa
        double[] vj={Math.sqrt(1)*Math.log10(3.0/2), Math.sqrt(2)*Math.log10(3.0/2)};
        System.out.println("vj:("+vj[0]+","+vj[1]+")");
        // both have the same angle: \pi/4
        double len1=PositionalIndex.vectorLen(vj);
        double [] vq={1,1};
        double len2=PositionalIndex.vectorLen(vq);
        System.out.println("hand calculated:");
        double handc=(vj[0]+vj[1])/len1/len2;
        System.out.println(handc);

        PositionalIndex pi = new PositionalIndex("./test resources/files");
        System.out.println("program return:");
        System.out.println(pi.VSScore("b aa", "ab.txt"));

        assertTrue(handc-pi.VSScore("b aa", "ab.txt")<1e-4);
        assertTrue(pi.VSScore("b aa", "ab.txt")-handc<1e-4);
    }


    @Test
    void Relevance() {
        String query = "a aa";
        PositionalIndex pi = new PositionalIndex("./test resources/files");
        String[] docs = pi.docs;
        int numDoc = pi.numDoc;
        for (int i = 0; i < numDoc; i++) {
            double score;
            System.out.println(docs[i]);
            score=pi.TPScore(query, docs[i]);
            System.out.println(docs[i]);
            System.out.println("TPScore: "+ score);
            score=pi.VSScore(query,docs[i]);
            System.out.println("VSScore: "+score);
            System.out.println();
        }
    }

    @Test
    void Relevance2() {
        String query = "baseball league";
        PositionalIndex pi = new PositionalIndex(DataPath.dataPath+"/IR");
        String[] docs = pi.docs;
        int numDoc = pi.numDoc;
        for (int i = 0; i < numDoc; i++) {
            double score;
            System.out.println(docs[i]);
            score=pi.TPScore(query, docs[i]);
            System.out.println(docs[i]);
            System.out.println("TPScore: "+ score);
            score=pi.VSScore(query,docs[i]);
            System.out.println("VSScore: "+score);
            System.out.println();
        }
    }

    @Test
    void vectorDist() {
        double[] array1 = {1, 2};
        double[] array2 = {4, 6};
        assertTrue(Math.abs(PositionalIndex.vectorDist(array1, array2) - 5) < 1e-4);
    }

    @Test
    void TestQuery1() {
    	PositionalIndex pi=new PositionalIndex(DataPath.dataPath+"/IR");
    	String query = "national";
    	File[] files = pi.folder.listFiles();
    	TopKHelper t = new TopKHelper();
    	double[] TPSList = new double[files.length];
    	double[] VSSList = new double[files.length];
    	double[] RelevanceList = new double[files.length];
    	for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            	String doc = files[i].getName();
            	TPSList[i] = pi.TPScore(query, doc);
            	VSSList[i] = pi.VSScore(query, doc);
            	RelevanceList[i] = pi.Relevance(query, doc);
            }
    	}
    	t.topK(TPSList, 10);
    	System.out.println("Top 10 files TPS : " + Arrays.toString(t.indices));
    	t.topK(VSSList, 10);
    	System.out.println("Top 10 files VSS : " + Arrays.toString(t.indices));
    	t.topK(RelevanceList, 10);
    	System.out.println("Top 10 files Relevance : " + Arrays.toString(t.indices));
    	QueryProcessor q = new QueryProcessor(DataPath.dataPath+"/IR");
    	ArrayList<String> ret=q.topKDocs(query, 10);
    	System.out.println("Top 10 files Relevance : " + ret.toString());
    }

    @Test
    void TestQuery2() {
    	PositionalIndex pi=new PositionalIndex(DataPath.dataPath+"/IR");
    	String query = "bad luck";
    	File[] files = pi.folder.listFiles();
    	TopKHelper t = new TopKHelper();
    	double[] TPSList = new double[files.length];
    	double[] VSSList = new double[files.length];
    	double[] RelevanceList = new double[files.length];
    	for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            	String doc = files[i].getName();
            	TPSList[i] = pi.TPScore(query, doc);
            	VSSList[i] = pi.VSScore(query, doc);
            	RelevanceList[i] = pi.Relevance(query, doc);
            }
    	}
    	t.topK(TPSList, 10);
    	System.out.println("Top 10 files TPS : " + Arrays.toString(t.indices));
    	t.topK(VSSList, 10);
    	System.out.println("Top 10 files VSS : " + Arrays.toString(t.indices));
    	t.topK(RelevanceList, 10);
    	System.out.println("Top 10 files Relevance : " + Arrays.toString(t.indices));
    	QueryProcessor q = new QueryProcessor(DataPath.dataPath+"/IR");
    	ArrayList<String> ret=q.topKDocs(query, 10);
    	System.out.println("Top 10 files Relevance : " + ret.toString());
    }

    @Test
    void TestQuery3() {
    	PositionalIndex pi=new PositionalIndex(DataPath.dataPath+"/IR");
    	String query = "advantage of propitious";
    	File[] files = pi.folder.listFiles();
    	TopKHelper t = new TopKHelper();
    	double[] TPSList = new double[files.length];
    	double[] VSSList = new double[files.length];
    	double[] RelevanceList = new double[files.length];
    	for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            	String doc = files[i].getName();
            	TPSList[i] = pi.TPScore(query, doc);
            	VSSList[i] = pi.VSScore(query, doc);
            	RelevanceList[i] = pi.Relevance(query, doc);
            }
    	}
    	t.topK(TPSList, 10);
    	System.out.println("Top 10 files TPS : " + Arrays.toString(t.indices));
    	t.topK(VSSList, 10);
    	System.out.println("Top 10 files VSS : " + Arrays.toString(t.indices));
    	t.topK(RelevanceList, 10);
    	System.out.println("Top 10 files Relevance : " + Arrays.toString(t.indices));
    	QueryProcessor q = new QueryProcessor(DataPath.dataPath+"/IR");
    	ArrayList<String> ret=q.topKDocs(query, 10);
    	System.out.println("Top 10 files Relevance : " + ret.toString());
    }

    @Test
    void TestQuery4() {
    	PositionalIndex pi=new PositionalIndex(DataPath.dataPath+"/IR");
    	String query = "exact origin of the phrase";
    	File[] files = pi.folder.listFiles();
    	TopKHelper t = new TopKHelper();
    	double[] TPSList = new double[files.length];
    	double[] VSSList = new double[files.length];
    	double[] RelevanceList = new double[files.length];
    	for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            	String doc = files[i].getName();
            	TPSList[i] = pi.TPScore(query, doc);
            	VSSList[i] = pi.VSScore(query, doc);
            	RelevanceList[i] = pi.Relevance(query, doc);
            }
    	}
    	t.topK(TPSList, 10);
    	System.out.println("Top 10 files TPS : " + Arrays.toString(t.indices));
    	t.topK(VSSList, 10);
    	System.out.println("Top 10 files VSS : " + Arrays.toString(t.indices));
    	t.topK(RelevanceList, 10);
    	System.out.println("Top 10 files Relevance : " + Arrays.toString(t.indices));
    	QueryProcessor q = new QueryProcessor(DataPath.dataPath+"/IR");
    	ArrayList<String> ret=q.topKDocs(query, 10);
    	System.out.println("Top 10 files Relevance : " + ret.toString());
    }

    @Test
    void TestQuery5() {
    	PositionalIndex pi=new PositionalIndex(DataPath.dataPath+"/IR");
    	String query = "the diamond was in the north end of the block";
    	File[] files = pi.folder.listFiles();
    	TopKHelper t = new TopKHelper();
    	double[] TPSList = new double[files.length];
    	double[] VSSList = new double[files.length];
    	double[] RelevanceList = new double[files.length];
    	for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            	String doc = files[i].getName();
            	TPSList[i] = pi.TPScore(query, doc);
            	VSSList[i] = pi.VSScore(query, doc);
            	RelevanceList[i] = pi.Relevance(query, doc);
            }
    	}
    	t.topK(TPSList, 10);
    	System.out.println("Top 10 files TPS : " + Arrays.toString(t.indices));
    	t.topK(VSSList, 10);
    	System.out.println("Top 10 files VSS : " + Arrays.toString(t.indices));
    	t.topK(RelevanceList, 10);
    	System.out.println("Top 10 files Relevance : " + Arrays.toString(t.indices));
    	QueryProcessor q = new QueryProcessor(DataPath.dataPath+"/IR");
    	ArrayList<String> ret=q.topKDocs(query, 10);
    	System.out.println("Top 10 files Relevance : " + ret.toString());
    }
}
