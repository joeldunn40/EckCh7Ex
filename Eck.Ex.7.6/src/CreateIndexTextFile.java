/** Eck Exercise 7.6
 * Read text file selected by user and create list of words in alphabetical order.
 * readNextWord written by Eck.
 * @author jd07
 *
 */

import java.util.ArrayList;

import javax.xml.soap.Text;

import textio.TextIO;

public class CreateIndexTextFile {
	public static void main(String[] args) {
		String word = new String(); // variable to capture new word
		ArrayList<String> wordList = new ArrayList<String>(); // word list
		
		// Specify file directly
		TextIO.readFile("src/randomText.txt");

		// Ask user to define file
//		TextIO.readUserSelectedFile();
//		String fn = TextIO.getInputFileName();
//		System.out.println(fn);
		while (true) {
			word = readNextWord();
			if (word == null)
				break;
			word = word.toLowerCase();
//			System.out.println(word + " : " + wordList.contains(word));
			if ( wordList.contains(word) == false)
				wordList.add(word); // Only add word if not already included
		}
		System.out.println(wordList.size());
		wordList.sort(null);
		System.out.println(wordList);
//		System.out.println("done");
	}
	
	
	
	
	/**
	 * Read the next word from TextIO, if there is one.  First, skip past
	 * any non-letters in the input.  If an end-of-file is encountered before 
	 * a word is found, return null.  Otherwise, read and return the word.
	 * A word is defined as a sequence of letters.  Also, a word can include
	 * an apostrophe if the apostrophe is surrounded by letters on each side.
	 * @return the next word from TextIO, or null if an end-of-file is 
	 *     encountered
	 */
	private static String readNextWord() {
	   char ch = TextIO.peek(); // Look at next character in input.
	   while (ch != TextIO.EOF && ! Character.isLetter(ch)) {
	          // Skip past non-letters.
	      TextIO.getAnyChar();  // Read the character.
	      ch = TextIO.peek();   // Look at the next character.
	   }
	   if (ch == TextIO.EOF) // Encountered end-of-file
	      return null;
	   // At this point, we know the next character is a letter, so read a word.
	   String word = "";  // This will be the word that is read.
	   while (true) {
	      word += TextIO.getAnyChar();  // Append the letter onto word.
	      ch = TextIO.peek();  // Look at next character.
	      if ( ch == '\'' ) {
	            // The next character is an apostrophe.  Read it, and
	            // if the following character is a letter, add both the
	            // apostrophe and the letter onto the word and continue
	            // reading the word.  If the character after the apostrophe
	            // is not a letter, the word is done, so break out of the loop.
	         TextIO.getAnyChar();   // Read the apostrophe.
	         ch = TextIO.peek();    // Look at char that follows apostrophe.
	         if (Character.isLetter(ch)) {
	            word += '\'' + TextIO.getAnyChar();
	            ch = TextIO.peek();  // Look at next char.
	         }
	         else
	            break;
	      }
	      if ( ! Character.isLetter(ch) ) {
	            // If the next character is not a letter, the word is
	    	  // finished, so break out of the loop.
	          break;
	       }
	       // If we haven't broken out of the loop, next char is a letter.
	    }
	    return word;  // Return the word that has been read.
	 }
}
