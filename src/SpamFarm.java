import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SpamFarm {
	private final File graphFile;
	private int numSpamPages;
	private int totalNodes;
    String graphFileName;
    String targetString;
    int target;

    public SpamFarm(String graphFileName, int target, int numSpamPages) {
        this.graphFileName = graphFileName;
        this.target = target;
        this.graphFile = new File(graphFileName);
//        PageRank pr = new PageRank(graphFileName);
        targetString = target + "";
        this.numSpamPages = numSpamPages;
    }

    String getTargetString() {
    	return targetString;
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

        String currFromNode = "";
        String targetNode = "";
        String[] words;
        while((currentLine = reader.readLine()) != null) {
        	words=currentLine.split("\\s");
        	if (!currFromNode.equals(words[0])) {
        		currFromNode = words[0];
        		if (targetString.equals(words[0])) {
        			targetNode = currFromNode;
        			for (int i = 1; i <= numSpamPages; i++) {
        				int n = totalNodes + i;
        				writer.write(currFromNode + " " + n + System.getProperty("line.separator"));
        			}
        		}
        	}
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        for (int i = 1; i <= numSpamPages; i++) {
			int n = totalNodes + i;
			writer.write(n + " " + targetNode + System.getProperty("line.separator"));
		}

        writer.close();
        reader.close();
    }
}
