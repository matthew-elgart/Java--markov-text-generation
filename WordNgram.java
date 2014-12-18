/*
 * This class encapsulates N words/strings so that the
 * group of N words can be treated as a key in a map or an
 * element in a set, or an item to be searched for in an array.
 * <P>
 * @author Matt Elgart
 * NetID: mje21
 * Collaborators: Matthew Faw
 * Resources: StackOverflow
 */

public class WordNgram implements Comparable<WordNgram>{
    
    public String[] myWords;
    
    /*
     * Store the n words that begin at index start of array list as
     * the N words of this N-gram.
     * @param list contains at least n words beginning at index start
     * @start is the first of the N words to be stored in this N-gram
     * @n is the number of words to be stored (the n in this N-gram)
     */
    public WordNgram(String[] list, int start, int n) {
        myWords = new String[n];
        System.arraycopy(list, start, myWords, 0, n);
    }
    
    /**
     * Return value that meets criteria of compareTo conventions.
     * @param wg is the WordNgram to which this is compared
     * @return appropriate value less than zero, zero, or greater than zero
     */
    public int compareTo(WordNgram wg) {
        int count = 0;
    	if (equals(wg)) return 0;
        for (int k = 0; k < myWords.length; k++) {
        	int diff = myWords[k].compareTo(wg.myWords[k]);
        	if (diff != 0)
        		return diff;
        	count += diff;
        }
        return count;
    }
    
    /**
     * Return true if this N-gram is the same as the parameter: all words the same.
     * @param o is the WordNgram to which this one is compared
     * @return true if o is equal to this N-gram
     */
    public boolean equals(Object o) {
        WordNgram wg = (WordNgram) o;
        for (int k = 0; k < myWords.length; k++) {
        	if (!myWords[k].equals(wg.myWords[k])) {
        		return false;
        	}
        }
        return true;
    }
    
    /**
     * Returns a good value for this N-gram to be used in hashing.
     * @return value constructed from all N words in this N-gram
     */
    public int hashCode() {
        int sum = 0;
        for (int k = 0; k < myWords.length; k++) {
        	sum = 57*sum + myWords[k].hashCode();
        }
        return sum;
    }
    
    public void updateSeed(String str) {
    	for (int i = 0; i < myWords.length -1; i++) {
    		myWords[i] = myWords[i+1];
    	}
    	myWords[myWords.length-1] = str;
    }
}
