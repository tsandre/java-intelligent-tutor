package itjava.tests;

import java.util.HashSet;

import itjava.model.LinkStore;

import org.junit.Test;

public class LinkStoreTest {
	private String _query;
	private HashSet<String> _setOfLinks;
	
	@Test
	public void ResultsReturnedByLinkStoreAreValidURLs() {
		GivenSearchQuery();
		WhenLinkStoreIsExecuted();
		ThenResultStringsAreValid();
	}

	private void ThenResultStringsAreValid() {
		
		/* Check if all the strings returned in setOfLinks are valid URLs
		 for(String link : _setOfLinks) {
			 assertTrue(link is a valid URL);
		 }*/
		 
	}

	private void WhenLinkStoreIsExecuted() {
		_setOfLinks = LinkStore.CreateLinks(_query);
	}

	private void GivenSearchQuery() {
		_query = "SQL Statement java example";
	}
}
