package itjava.tests;

//import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import itjava.presenter.*;
import itjava.model.*;

//import com.google.gdata.client.codesearch.CodeSearchService;
//import com.google.gdata.data.codesearch.CodeSearchEntry;
//import com.google.gdata.data.codesearch.CodeSearchFeed;

public class CodeSearchTest {

	@Test
	public void CodeSearchReturnsEntries() {
		try {
			CodeSearchPresenter newCodePresenter = new CodeSearchPresenter("Scanner%20Java%20example");
			//ArrayList<String> currLinks = new ArrayList<String>();
			//String[] myLinks = newCodePresenter.ShowLinks();
			//for(int i=0;i<myLinks.length;i++){
			//	currLinks.add(myLinks[i]);
			//	System.out.println(myLinks[i]);
			//}
			//ResultEntryStore myEntryStore = new ResultEntryStore(currLinks);
			//ArrayList<ResultEntry> myResults = new ArrayList<ResultEntry>();
			//myResults = myEntryStore.getResults();
			ArrayList<ResultEntry> entries = newCodePresenter.SearchNext();
			for(int i=0;i<entries.size();i++){
				System.out.println("///////////////" + i + "///////////////////\n");
				System.out.println(entries.get(i).text);
			}
			
			ArrayList<ResultEntry> entries2 = newCodePresenter.SearchNext();
			for(int i=0;i<entries2.size();i++){
				System.out.println("///////////////" + i + "-" + 2 + "///////////////////\n");
				System.out.println(entries2.get(i).text);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			
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
				entry.getPackage().generate(xmlWriter,
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
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}
}
