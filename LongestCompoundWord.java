import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// this program calculate the longest and the second longest words in a file

public class LongestCompoundWord {

	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("Input_02.txt");

		// Called from Trie Class
		Trie trie = new Trie();
		LinkedList<Pair<String>> queue = new LinkedList<Pair<String>>();
		
		// used to calculate the total amount of compound words
		HashSet<String> compoundWords = new HashSet<String>();
		
		// scan the file
		Scanner s = new Scanner(file);

		String word;				// a word
		List<Integer> sufIndices;	// indices of suffixes of a word
		
		// read words from the file
		// fill up the queue with words which have suffixes, who are
		// candidates to be compound words
		// insert each word in trie
		while (s.hasNext()) {
			word = s.next();		
			sufIndices = trie.getSuffixesStartIndices(word);
		
			for (int i : sufIndices) {
				if (i >= word.length())		// if index is out of bound
					break;					// it means suffixes of the word has
											// been added to the queue if there is any
				queue.add(new Pair<String>(word, word.substring(i)));
			}
	
			trie.insert(word);
		}
		
		Pair<String> p;				// a pair of word and its remaining suffix
		int maxLength = 0;			// longest compound word length
		//int sec_maxLength = 0;		// second longest compound word length		
		String longest = "";		// longest compound word
		String sec_longest = "";	// second longest compound word

		while (!queue.isEmpty()) {
			p = queue.removeFirst();
			word = p.second();
			
			sufIndices = trie.getSuffixesStartIndices(word);
			
			// if no suffixes found, which means no prefixes found
			// discard the pair and check the next pair
			if (sufIndices.isEmpty()) {
				continue;
			}
			
			//System.out.println(word);
			for (int i : sufIndices) {
				if (i > word.length()) { // sanity check 
					break;
				}
				
				if (i == word.length()) { // no suffix, means it is a compound word
					// check if the compound word is the longest
					// if it is update both longest and second longest
					// words records
					if (p.first().length() > maxLength) {
						//sec_maxLength = maxLength;
						sec_longest = longest;
						maxLength = p.first().length();
						longest = p.first();
					}
			
					compoundWords.add(p.first());	// the word is compound word
					
				} else {
					queue.add(new Pair<String>(p.first(), word.substring(i)));
				}
			}
		}
		
		// print out the results
		System.out.println("Longest Compound Word: " + longest);
		System.out.println("Second Longest Compound Word: " + sec_longest);
	}
}