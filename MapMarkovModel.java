/**
 * Name: Matt Elgart
 * NetID: mje21
 * Collaborators: Matthew Faw
 * Resources: StackOverflow
 * MapMarkovModel for Markov Text Generation Program
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapMarkovModel extends MarkovModel {
	Map<Integer, HashMap<String, ArrayList<Character>>> bigMap = new HashMap<Integer, HashMap<String, ArrayList<Character>>>();
	
	
	@Override protected String makeNGram(int k, int numLetters) {
		// Update the map for k-grams if necessary
		String myWrapAroundString = myString + myString.substring(0, k);
		if (!bigMap.containsKey(k)) {
			double mapStart = System.currentTimeMillis();
			bigMap.put(k, new HashMap<String, ArrayList<Character>>());
			HashMap<String, ArrayList<Character>> smallMap = bigMap.get(k);
			for (int j = 0; j<myString.length(); j++) {
				String ngram = myWrapAroundString.substring(j, j+k);
				if (!smallMap.containsKey(ngram)) {
					smallMap.put(ngram, new ArrayList<Character>());
				}
				ArrayList<Character> list = smallMap.get(ngram);
				char ch = myWrapAroundString.charAt(j+k);
				list.add(ch);
			}
			double mapEnd = System.currentTimeMillis();
			double mapTime = (mapEnd-mapStart) / 1000.0;
			System.out.println("time to make map: " + mapTime + " seconds");
		}
		// Appending to StringBuilder is faster than appending to String
        StringBuilder build = new StringBuilder();
        // Pick a random starting index
        double generateStart = System.currentTimeMillis();
        int start = myRandom.nextInt(myString.length() - k + 1);
        String seed = myString.substring(start, start + k);
        for (int i = 0; i < numLetters; i++) {
        	int pick = myRandom.nextInt(bigMap.get(k).get(seed).size());
        	char ch = (((bigMap.get(k)).get(seed)).get(pick));
        	build.append(ch);
        	seed = seed.substring(1) + ch;
        }
        double generateEnd = System.currentTimeMillis();
        double generateTime = (generateEnd - generateStart) / 1000.0;
        System.out.println("time to generate characters: " + generateTime + " seconds");
        return build.toString();
	}
}