/**
 * 
 */
package itjava.util;

import itjava.model.WordInfo;

import java.util.Comparator;

/**
 * @author Aniket
 *
 */	
public class WordInfoComparator implements Comparator<WordInfo>{
		@Override
		public int compare(WordInfo w1, WordInfo w2) {
			return ((w1).lineNumber > (w2).lineNumber) ? 1 : 0;
		}
	}
