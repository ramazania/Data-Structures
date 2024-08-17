/**
 * Name: Ali RAmazani
 * Email: ramazani@carleton.edu 
 * Description: Using recursive backtracking to form words in a puzzle.
 */

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {

    private Set<String> dictionary;  // set of words in the dictionary
    private Set<String> prefixes;    // set of prefixes to dictionary words

    // Constructs a new BoggleSolver with a dictionary of strings d
	public BoggleSolver(String[] d) {
        dictionary = new HashSet<>();
        for (String word : d) {
            dictionary.add(word);
        }
        prefixes = new HashSet<>();
        prefixes.add("");
        for (String word : dictionary) {
            for (int i = 1; i < word.length(); i++) {
                prefixes.add(word.substring(0, i));
            }
        }
	}

    // Returns a set of words that can be made on board
	public Set<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new HashSet<String>();

        // YOUR CODE HERE
        String wordSoFar = "";
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                getAllValidWordsHelper(board, words, wordSoFar, row, col);
            }
        }
        return words;
        
    }    

    // helper method
    private void getAllValidWordsHelper(BoggleBoard board, Set<String> words, 
                                        String wordSoFar, int row, int col) {
        wordSoFar += board.getLetter(row, col);
        board.visit(row, col);

        if (wordSoFar.length() >= 3 && dictionary.contains(wordSoFar)) { 
            words.add(wordSoFar);
        }
            for (int i = row - 1; i <= row + 1; i++) {
                for (int k = col - 1; k <= col + 1; k++) {
                    if (board.isValidLocation(i, k) && !board.isVisited(i, k)
                    && prefixes.contains(wordSoFar)) {
                        getAllValidWordsHelper(board, words, wordSoFar,
                        i, k);
                   
                    }
                    
                }
            }
        board.unvisit(row, col); 
        wordSoFar = "" + wordSoFar.charAt(wordSoFar.length() - 1);

    }
}
