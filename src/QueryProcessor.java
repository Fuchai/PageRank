import java.util.ArrayList;

public class QueryProcessor extends PositionalIndex{

    public QueryProcessor(String folderName) {
        super(folderName);
    }

    ArrayList<String> topKDocs(String query, int k){

//        long start = System.currentTimeMillis();
        double[] reles=new double[numDoc];
        for (int i = 0; i < numDoc; i++) {
        	System.out.println(VSScore(query, docs[i]));
        	System.out.println(TPScore(query, docs[i]));
            reles[i]=Relevance(query, docs[i]);
        }
        TopKHelper topk = new TopKHelper();
        topk.topK(reles, k);
        ArrayList<String> ret = new ArrayList<>();
        for (int index:topk.indices
             ) {
            ret.add(docs[index]);
        }
//        long end = System.currentTimeMillis();
//        System.out.println("TopK search:"+(end-start));
        return ret;
    }
}
