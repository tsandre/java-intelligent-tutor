/**
 * 
 */
package itjava.model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Aniket, Matt
 *
 */
public class LinkStore {
	private static String _query;
	private static HashSet<String> _setOfLinks;
	
	public static HashSet<String> CreateLinks(String query) {
		_setOfLinks = new HashSet<String>();
		_query = query;
		_setOfLinks.addAll(GoogleSearch());
		//_setOfLinks.addAll(BingSearch(_query));
		//_setOfLinks.addAll(YahooSearch(_query));
		return _setOfLinks;
	}

	private static ArrayList<String> GoogleSearch() {
		ArrayList<String> googleSearchResults = new ArrayList<String>();
		// TODO Populate googleSearchResults with url strings
		return googleSearchResults;
	}
}
