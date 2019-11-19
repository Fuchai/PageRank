import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PreProcessing {
	
	public static void main(String[] args) {
		PreProcessing pr = new PreProcessing();
		File tempFile = new File(DataPath.dataPath+"/testPre.txt");
        String[] arr = pr.process(tempFile);
        for (int i = 0; i < arr.length; i++) {
        	System.out.println(arr[i] + i);
        }
    } 
	
	public PreProcessing() {
		
	}

    // from the last project
	public static String[] process(File file) {

		Scanner s;
        try {
            s = new Scanner(file);
            ArrayList<String> list = new ArrayList<>();

            while (s.hasNext()) {
                String word = s.next();
                word = word.toLowerCase();
                if (!word.matches("([0-9]+)\\.([0-9])+")){
                    word = word.replaceAll("[.,;:?'(){}\\[\\]\"]", "");
                }

                list.add(word);
            }
            String[] ret = new String[list.size()];
            ret = list.toArray(ret);
            return ret;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath());
            e.printStackTrace();
            System.exit(-10);
        }
        // will not reach
        return null;
    }


}
