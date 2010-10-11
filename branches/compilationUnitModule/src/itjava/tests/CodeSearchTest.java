package itjava.tests;

import java.net.URL;

import org.junit.Test;

import com.google.gdata.client.codesearch.CodeSearchService;
import com.google.gdata.data.codesearch.CodeSearchEntry;
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
			for (CodeSearchEntry entry : resultFeed.getEntries()) {
				// Default Gdata elements
				System.out.println("\tId: " + entry.getId());
				System.out.println("\tTitle: " + entry.getTitle());
				System.out.println("\tLink: " + entry.getHtmlLink().getHref());
				System.out.println("\tUpdated: " + entry.getUpdated());
				System.out.println("\tAuthor: "
						+ entry.getAuthors().get(0).getName());
				if (entry.getRights() != null)
					System.out.println("\tLicense:"
							+ entry.getRights().getPlainText());
				// Codesearch Elements
				System.out.println("\tPackage: ");
				System.out.println("\t\t Name:" + entry.getPackage().getName());
				System.out.println("\t\t URI:" + entry.getPackage().getUri());
				/*entry.getPackage().generate(xmlWriter,
						codesearchService.getExtensionProfile());
				System.out.println("XML: ");
				writer.flush();
				System.out.println("");
				System.out.println("\tFile: " + entry.getFile().getName());
				entry.getFile().generate(xmlWriter,
						codesearchService.getExtensionProfile());
				System.out.println("XML: ");
				writer.flush();
				System.out.println("");
				System.out.println("\tMatches: ");
				for (Match m : entry.getMatches()) {
					System.out.println(m.getLineNumber() + ": "
							+ m.getLineText().getPlainText());
					m.generate(xmlWriter,
							codesearchService.getExtensionProfile());
					System.out.println("XML: ");
					writer.flush();
					System.out.println("");
				}*/
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
