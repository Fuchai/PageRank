import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SpamFarm {
	private final File graphFile;
	private int numSpamPages;
	private int totalNodes;
    String graphFileName;
    int target;
    
    public static void main(String[] args) {
    	SpamFarm sf = new SpamFarm("./data/WikiSportsGraph.txt", 2);
        try {
			sf.createSpam("./data/testfile.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public SpamFarm(String graphFileName, int target) {
        this.graphFileName = graphFileName;
        this.target = target;
        this.graphFile = new File(graphFileName);
        numSpamPages = 10;
    }

    void createSpam(String fileName) throws IOException{
        File tempFile = new File(fileName);

        BufferedReader reader = new BufferedReader(new FileReader(graphFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        
        String currentLine;

        currentLine = reader.readLine();
        try {
        	totalNodes = Integer.parseInt(currentLine);
        } catch (NumberFormatException e) {
        	System.out.println(currentLine + " is not a valid"); 
        }
        writer.write(totalNodes + numSpamPages + System.getProperty("line.separator"));
        
        int count = 0;
        String currFromNode = "";
        String targetNode = "";
        String[] words;
        while((currentLine = reader.readLine()) != null) {
        	words=currentLine.split("\\s");
        	if (!currFromNode.equals(words[0])) {
        		if (count++ == target) {
        			targetNode = currFromNode;
        			for (int i = 1; i <= numSpamPages; i++) {
        				writer.write(currFromNode + " " + "/wiki/n" + i + System.getProperty("line.separator"));
        			}
        		}
        		currFromNode = words[0];
        	}
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        
        for (int i = 1; i <= numSpamPages; i++) {
			writer.write("/wiki/n" + i + " " + targetNode + System.getProperty("line.separator"));
		}
        
        writer.close();
        reader.close();
    }
}
