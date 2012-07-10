package itjava.scraper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import org.jsoup.*;

/**
 * @author Vasanth
 * 
 */

public class Java2sScrape {
	public static LinkedHashSet <ScrapeData> ScrapeQuery(String queryText){
		String websiteName = "java2s.com";
		String scrapeSource = "Java2s";
		LinkedHashSet <ScrapeData> Java2sScrapeObj = new LinkedHashSet <ScrapeData> ();
		ArrayList<String> googleSearchResults = new ArrayList<String>();
		String googleSearchContext = "011045704107476092050:liivikkqg0k";
		String googleSearchAPIKey = "AIzaSyCdECK7u3Erfb23uPqCEl2lESCDw4cwOXA";
		try {
			NetHttpTransport nht = new NetHttpTransport();
			JacksonFactory jf = new JacksonFactory();
			Customsearch customsearch = new Customsearch(nht, jf);
			Customsearch.Cse.List request = customsearch.cse().list(queryText+" site:java2s.com");
			request.setKey(googleSearchAPIKey);
			request.setCx(googleSearchContext);

			for(long iResultIndex=1; iResultIndex<20; iResultIndex+=10) {
				request.setStart(iResultIndex);
				Search results = request.execute();
				List<Result> searchResults = results.getItems();
				int count = 0;
				for(Result item:searchResults) {
					String urlText = item.getLink();
					String urlEnding = urlText.toUpperCase();
					System.out.println("Java2S: "+urlText);
					urlText = Jsoup.parse(urlText).text();
					String title = item.getTitle();
					title = Jsoup.parse(title).text();
					//		System.out.println("Title: "+title);
					String summary = item.getSnippet().trim();
					summary = Jsoup.parse(summary).text();
					//		System.out.println("Summary: "+summary);
					if (!urlEnding.endsWith(".PDF") && !urlEnding.endsWith(".DOC") && !urlEnding.endsWith(".PPT")) {
						googleSearchResults.add(urlText);
					}
					count++;
					System.out.println("Topic: "+title);
					System.out.println("Link: "+urlText);
					System.out.println("Preview: "+summary +"\n");
					iResultIndex++;
					if (count < 6){
						ScrapeData scrapeDataObj = new ScrapeData();
						scrapeDataObj.setInfoQuery(queryText);
						scrapeDataObj.setInfoTopic(title);
						scrapeDataObj.setInfoTopicURL(urlText);
						scrapeDataObj.setInfoTopicLinkPreview(summary);
						scrapeDataObj.setInfoScrapeSite(websiteName);
						scrapeDataObj.setInfoScrapeSource(scrapeSource);
						scrapeDataObj.setInfoTopicResultindex(count);
						Java2sScrapeObj.add(scrapeDataObj);
					}
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		System.out.println("Result count of Java2s :"+Java2sScrapeObj.size());
		return Java2sScrapeObj;
	}
} 