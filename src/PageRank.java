import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class PageRank {
    private final File graphFile;
    private final String graphFileName;
    private final double epsilon;
    private final double beta;
    HashMap<String, Integer> nameNode;
    String[] nodeName;
    // this is M matrix
    boolean[][] fromToMatrix;
    int[] totalLinks;
    private int totalNodes;
    double[] rank;

    public static void main(String[] args) {
        PageRank pr = new PageRank("data/WikiSportsGraph.txt", 0.1, 0.85);
        System.out.println(Arrays.toString(pr.topKPageRank(5)));

    }

    public PageRank(String graphFileName, double epsilon, double beta) {
        this.graphFileName = graphFileName;
        this.epsilon = epsilon;
        this.beta = beta;
        this.graphFile = new File(graphFileName);
        processGraph(graphFile);
        approxPageRank();
    }

    private void approxPageRank() {
        double[] p = new double[totalNodes];
        Arrays.fill(p, 1.0 / totalNodes);
        boolean converged = false;
        int step=0;
        while (!converged) {
            double[] newP=walk(p);
            if (norm(p, newP)<=epsilon){
                converged=true;
            }
            p=newP;
            step++;
            if (step == 100000) {
                System.out.println("This is too many runs. I will abort the program");
                System.exit(-1);
            }
        }
        rank=p;
    }

    private double norm(double[] p, double[] newP) {
        double normSum=0;
        for (int i = 0; i < p.length; i++) {
            normSum+=(p[i]-newP[i])*(p[i]-newP[i]);
        }
        double ret=Math.sqrt(normSum);
        return ret;
    }

    private double[] walk(double[] oldRank) {
        int n = oldRank.length;
        double[] newRank = new double[n];
        Arrays.fill(newRank, (1-beta)/n);
        for (int p = 0; p < n; p++) {
            if (totalLinks[p]!=0){
                // if not zero
                boolean[] toLinks = fromToMatrix[p];
                double delta =beta*oldRank[p]/totalLinks[p];
                for (int q = 0; q < n; q++) {
                    if (toLinks[q]){
                        newRank[q]+=delta;
                    }
                }
            }else{
                // if zero
                double delta =beta*oldRank[p]/n;
                for (int q = 0; q < n; q++) {
                    newRank[q]+=delta;
                }
            }
        }
        return newRank;
    }

    public void processGraph(File graphFile){
        try {
            Scanner scan;
            scan = new Scanner(graphFile);
            if (scan.hasNextInt()){
                totalNodes=scan.nextInt();
                fromToMatrix = new boolean[totalNodes][totalNodes];
                nodeName = new String[totalNodes];
                totalLinks = new int[totalNodes];
            }else{
                System.out.println("Your file contains no vertices count");
                System.exit(-1);
                return;
            }
            nameNode = new HashMap<>();
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if (!line.equals("")){
                    String[] words=line.split("\\s");
                    String fromNode=words[0];
                    String toNode=words[1];
                    Integer fromIdx;
                    Integer toIdx;
                    fromIdx= nameNode.get(fromNode);
                    toIdx= nameNode.get(toNode);
                    if (fromIdx==null){
                        fromIdx= nameNode.size();
                        nameNode.put(fromNode,fromIdx);
                        nodeName[fromIdx]=fromNode;
                    }
                    if (toIdx==null){
                        toIdx= nameNode.size();
                        nameNode.put(toNode,toIdx);
                        nodeName[toIdx]=toNode;
                    }
                    if (fromToMatrix[fromIdx][toIdx]==false){
                        fromToMatrix[fromIdx][toIdx]=true;
                    }
                }
            }

            Arrays.fill(totalLinks, 0);
            for (int i = 0; i < fromToMatrix.length; i++) {
                boolean[] toPages=fromToMatrix[i];
                int linksCnt=0;
                for (int j = 0; j < toPages.length; j++) {
                    if (toPages[j]){
                        linksCnt++;
                    }
                }
                totalLinks[i]=linksCnt;
            }
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file " +graphFile);
            System.out.println("Current path: "+System.getProperty("user.dir"));
            System.exit(-1);
            return;
        }
    }

    double pageRankOf(int vertex){
        return rank[vertex];
    }

    double[] trustRank(){
        double[] trust = new double[0];
        return trust;
    }

    int numEdges(){
        int total=0;
        for (int i = 0; i < totalLinks.length; i++) {
            total+=totalLinks[i];
        }
        return total;
    }

    int[] topKPageRank(int k){
        int[] indices= new int[k];
        double[] values = new double[k];
        Arrays.fill(values, Double.MIN_VALUE);
        double min = Double.MIN_VALUE;
        for (int i = 0; i < rank.length; i++) {
            double value;
            value=rank[i];
            if (value>min){
                for (int j = 0; j < k; j++) {
                    if (values[j]<value){
                        values[j]=value;
                        indices[j]=i;
                        break;
                    }
                }

                min = Double.MAX_VALUE;
                for (int j = 0; j < k; j++) {
                    if (values[j]<min){
                        min=values[j];
                    }
                }
            }
        }
        return indices;
    }
}


