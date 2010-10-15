/**
 * 
 */
package itjava.model;

import java.util.ArrayList;

/**
 * @author Aniket, Matt
 *
 */
public class ResultEntryStore {
	private static ArrayList<String> _setOfLinks;
	private static ArrayList<ResultEntry> _resultEntries;
	
	public static ArrayList<ResultEntry> createResultEntryList(ArrayList<String> setOfLinks) {
		_setOfLinks = setOfLinks;
		_resultEntries = new ArrayList<ResultEntry>();
		/*
		 * Copy & alter code from JSoupTest.java
		 * 
		 */
		return _resultEntries;
	}
	
}
