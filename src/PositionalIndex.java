import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PositionalIndex {
	PreProcessing pre;
	File folder;
	int numDoc;
	String folderName;
	List<String> allUniqueTerms;
	HashMap<String, TDFT> dict;
	// Inverted Index, term -> postings. A posting is all the indices of the term in
	// a particular document
	HashMap<String, Posting[]> termPostings;
	HashMap<String, Integer> uniqueWordIndex;

	public PositionalIndex(String folderName) {
		this.folderName = folderName;
		folder = new File(this.folderName);
		numDoc = folder.list().length;
		initTermPosting();
	}
	
	void initTermPosting() {
        List<String> allUniqueTerms = new ArrayList<>();
        File[] contents = folder.listFiles();
        HashMap<String, Integer> uniqueWordIndex = new HashMap<>();
        try {
            int clen = contents.length;
        } catch (NullPointerException e) {
            System.out.println(folder + " has no files");
            e.printStackTrace();
        }
        
        HashMap<String, ArrayList<Posting>> tempTermPostings = new HashMap<String, ArrayList<Posting>>();
        int uniqueWords = 0;
        String currentWord;
        for (int i = 0; i < contents.length; i++) {
            if (contents[i].isFile()) {
                String[] words = pre.process(contents[i]);
                HashMap<String, String> map = new HashMap<String, String>();
                for (int j = 0; j < words.length; j++) {
                    currentWord = words[j];
                    if (map.containsKey(currentWord)) {
                    	map.put(currentWord, j + "");
                    }
                    else {
                    	map.put(currentWord, map.get(currentWord) + " " + j);
                    }
                    if (!uniqueWordIndex.containsKey(currentWord)) {
                        allUniqueTerms.add(currentWord);
                        uniqueWordIndex.put(currentWord, uniqueWords);
                        uniqueWords++;
                    }
                }
                // bit too messy
                for (String term: map.keySet()) {
                	String doc = contents[i].getName();
                	String[] temp = map.get(term).split("\\s");
                	int[] pos = new int[temp.length];
                	for (int j = 0; j < temp.length; j++) {
                		pos[j] = Integer.parseInt(temp[j]);
                	}
                	Posting p = new Posting(doc, pos);
                	ArrayList<Posting> tempArr;
                	if (tempTermPostings.containsKey(term)) {
                		tempArr = tempTermPostings.get(term);
                	} else {
                		tempArr = new ArrayList<Posting>();
                	}
                	tempArr.add(p);
                	tempTermPostings.put(term, tempArr);
                }
            }
        }
        for (String term: tempTermPostings.keySet()) {
        	ArrayList<Posting> temp = tempTermPostings.get(term);
        	Posting[] parr = (Posting[]) temp.toArray();
        	termPostings.put(term, parr);
        }
        this.uniqueWordIndex = uniqueWordIndex;
        this.allUniqueTerms = allUniqueTerms;
    }

	int termFrequency(String term, String doc) {

		Posting[] postings = termPostings.get(term);
		for (Posting posting : postings) {
			if (posting.doc.equals(doc)) {
				return posting.getPoss().length;
			}
		}
		return 0;
	}

	int docFrequency(String term) {
		return termPostings.get(term).length;
	}

	String postingsList(String term) {
		Posting[] postings = termPostings.get(term);
		String repr = "[";
		for (int i = 0; i < postings.length; i++) {
			if (i != 0) {
				repr += ",";
			}

			Posting posting = postings[i];
			String docRepr = "<";
			docRepr += posting.doc;
			docRepr += ":";
			for (int j = 0; j < posting.getPoss().length; j++) {
				if (j != 0) {
					docRepr += ",";
				}
				docRepr += posting.getPoss()[j];
			}
			docRepr += ">";

			repr += docRepr;
		}
		repr += "]";
		return repr;
	}

	double weight(String term, String doc) {
		return 0;
	}

	double TPScore(String query, String doc) {
		String[] queryWords = query.split("\\s");
		if (queryWords.length == 1 || queryWords.length == 0) {
			return 0;
		}
		double denom = 0;
		for (int i = 0; i < queryWords.length - 1; i++) {
			String t1 = queryWords[i];
			String t2 = queryWords[i + 1];
			denom += dist(doc, t1, t2);
		}
		double ret = (double) queryWords.length / denom;
		return ret;
	}

	double dist(String doc, String t1, String t2) {
		Posting[] postings1 = termPostings.get(t1);
		Posting[] postings2 = termPostings.get(t2);

		int[] poss1 = new int[0];
		int[] poss2 = new int[0];
		for (Posting posting : postings1) {
			if (posting.doc.equals(doc)) {
				poss1 = posting.getPoss();
			}
		}

		for (Posting posting : postings2) {
			if (posting.doc.equals(doc)) {
				poss2 = posting.getPoss();
			}
		}

		if (poss1.length == 0 || poss2.length == 0) {
			return 17;
		}

		// we can do it in linear time, given that the postings are sorted.
		int i = 0;
		int j = 0;
		int minDiff = Integer.MAX_VALUE;
		while (i != poss1.length - 1 && j != poss2.length - 1) {
			if (poss1[i] < poss2[j]) {
				int newDiff = poss1[j] - poss1[i];
				if (newDiff < minDiff) {
					minDiff = newDiff;
				}
				i++;
			} else {
				j++;
			}
		}
		return minDiff;
	}

	double VSScore(String query, String doc) {
		String[] queryWords = query.split("\\s");
		if (queryWords.length == 0) {
			return 0;
		}

		double[] vectorD = new double[queryWords.length];
		double[] vectorQ = new double[queryWords.length];

		String term;
		double weight, queryWeight, sum = 0;
		for (int i = 0; i < queryWords.length; i++) {
			term = queryWords[i];
			queryWeight = 0;
			for (int j = 0; j < queryWords.length; j++) {
				if (term.equals(queryWords[j]))
					queryWeight += 1.0;
			}
			weight = Math.sqrt(termFrequency(term, doc)) + Math.log10(((double) numDoc) / docFrequency(term));
			sum = sum + weight * queryWeight;
			vectorD[i] = weight;
			vectorQ[i] = queryWeight;
		}
		
		double[] base = new double[queryWords.length];
		Arrays.fill(base, 0.0);
		double distD = vectorDist(base, vectorD);
		double distQ = vectorDist(base, vectorQ);
		
		return sum / (distD*distQ);
	}
	
	public static double vectorDist(double[] array1, double[] array2)
    {
        double sum = 0.0;
        for(int i=0;i<array1.length;i++) {
           sum = sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.sqrt(sum);
    }

	double Relevance(String query, String doc) {
		return 0.6*TPScore(query, doc) + 0.4*VSScore(query, doc);
	}
}

class TDFT {
	public String term;
	public int docFreq;

	public TDFT(String term, int dft) {
		this.term = term;
		this.docFreq = dft;
	}
}

class Posting {
	public String doc;
	private int[] poss;
	
	Posting(String doc, int[] poss) {
		this.doc = doc;
		this.poss = poss;
	}
	
	public void setPoss(int[] poss) {
		this.poss = poss;
		int last = Integer.MIN_VALUE;
		for (int pos : poss) {
			if (pos < last) {
				System.out.println("You must sort the postings.");
				System.exit(-1);
			}
			last = pos;
		}
	}

	public int[] getPoss() {
		return poss;
	}
}
