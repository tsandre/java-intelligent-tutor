package itjava.tests;

import java.net.URL;

import org.junit.Test;

import com.google.gdata.client.codesearch.CodeSearchService;
import com.google.gdata.data.codesearch.CodeSearchFeed;

public class CodeSearchTest {

	@Test
	public void CodeSearchReturnsEntries() {
		try {
			CodeSearchService codeSearchService = new CodeSearchService(
					"exampleCo-exampleApp-1");

			URL feedUrl = new URL(
					"http://www.google.com/codesearch/feeds/search?q=malloc");

			// Send the request and receive the response:
			CodeSearchFeed resultFeed = codeSearchService.getFeed(feedUrl,
					CodeSearchFeed.class);
			System.out.println("Number of Entries Received: "
					+ resultFeed.getEntries().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
