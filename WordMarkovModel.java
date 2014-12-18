/**
 * Name: Matt Elgart
 * NetID: mje21
 * Collaborators: Matthew Faw
 * Resources: StackOverflow
 * WordMarkovModel for Markov Text Generation Program
 */

import java.util.*;

public class WordMarkovModel extends AbstractModel {
	Map<Integer, HashMap<WordNgram, ArrayList<String>>> bigMap = new HashMap<Integer, HashMap<WordNgram, ArrayList<String>>>();
	protected String[] myWords;
	protected Random myRandom;
	public static final int DEFAULT_COUNT = 100;
	public static int RANDOM_SEED = 1234;
	
	public WordMarkovModel() {
        myRandom = new Random(RANDOM_SEED);
    }
	
	public void initialize(Scanner s) {
        double start = System.currentTimeMillis();
        int count = readWords(s);
        double end = System.currentTimeMillis();
        double time = (end - start) / 1000.0;
        super.messageViews("#read: " + count + " words in: " + time + " secs");
    }
	
	protected int readWords(Scanner s) {
		myWords = s.useDelimiter("\\Z").next().split("\\s+");
        s.close();
        return myWords.length;
	}

	public void process(Object o) {
		String temp = (String) o;
        String[] nums = temp.split("\\s+");
        int k = Integer.parseInt(nums[0]);
        int numWords = DEFAULT_COUNT;
        if (nums.length > 1) {
            numWords = Integer.parseInt(nums[1]);
        }
        
        double stime = System.currentTimeMillis();
        String text = makeNGram(k, numWords);
        double etime = System.currentTimeMillis();
        double time = (etime - stime) / 1000.0;
        this.messageViews("time to generate: " + time);
        this.notifyViews(text);
	}

	protected String makeNGram(int k, int numWords) {
		// Update the map for k-grams if necessary
				
				String[] myWrapAroundString = new String[myWords.length + k];
				for (int i = 0; i < myWords.length; i++) myWrapAroundString[i] = myWords[i];
				for (int i = 0; i < k; i++) myWrapAroundString[myWords.length + i] = myWords[i];
				if (!bigMap.containsKey(k)) {
					double mapStart = System.currentTimeMillis();
					bigMap.put(k, new HashMap<WordNgram, ArrayList<String>>());
					HashMap<WordNgram, ArrayList<String>> smallMap = bigMap.get(k);
					WordNgram ngram = new WordNgram(myWrapAroundString, 0, 1);
					for (int j = 0; j<myWords.length; j++) {
						ngram = new WordNgram(myWrapAroundString, j, k);
						if (!smallMap.containsKey(ngram)) {
							smallMap.put(ngram, new ArrayList<String>());
						}
						ArrayList<String> list = smallMap.get(ngram);
						String str = myWrapAroundString[j+k];
						list.add(str);
					}
					double mapEnd = System.currentTimeMillis();
					double mapTime = (mapEnd-mapStart) / 1000.0;
					System.out.println("time to make map: " + mapTime + " seconds");
				}
				System.out.println("number of keys (WordNgrams in the map): " + bigMap.get(k).keySet().size());
				// Appending to StringBuilder is faster than appending to String
		        StringBuilder build = new StringBuilder();
		        // Pick a random starting index
		        double generateStart = System.currentTimeMillis();
		        int start = myRandom.nextInt(myWords.length - k + 1);
		        WordNgram seed = new WordNgram(myWords, start, k);
		        for (int i = 0; i < numWords; i++) {
		        	int pick = myRandom.nextInt(bigMap.get(k).get(seed).size());
		        	String str = (((bigMap.get(k)).get(seed)).get(pick));
		        	build.append(str);
		        	build.append(" ");
		        	seed.updateSeed(str);
		        }
		        build.deleteCharAt(build.length() -1);
		        double generateEnd = System.currentTimeMillis();
		        double generateTime = (generateEnd - generateStart) / 1000.0;
		        System.out.println("time to generate characters: " + generateTime + " seconds");
		        return build.toString();
	}
	
}