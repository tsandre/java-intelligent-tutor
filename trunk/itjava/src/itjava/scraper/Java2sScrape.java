package itjava.scraper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import com.luxmedien.googlecustomsearch.GoogleCustomSearch;
import com.luxmedien.googlecustomsearch.json.GoogleResponse;
import com.luxmedien.googlecustomsearch.json.Item;
import org.jsoup.*;

/**
 * @author Vasanth Krishnamoorthy
 * 
 */

public class Java2sScrape {
		public static LinkedHashSet <ScrapeData> ScrapeQuery(String queryText){
			String websiteName = "java2s.com";
			String scrapeSource = "Java2s";
			LinkedHashSet <ScrapeData> Java2sScrapeObj = new LinkedHashSet <ScrapeData> ();
			ArrayList<String> googleSearchResults = new ArrayList<String>();
			String googleSearchContext = "011045704107476092050:liivikkqg0k&start=";
			String googleSearchAPIKey = "AIzaSyCdECK7u3Erfb23uPqCEl2lESCDw4cwOXA";
			try {
				for(int i=1;i<10;i+=10) {
				GoogleCustomSearch customSearchInstance = new GoogleCustomSearch(googleSearchAPIKey, googleSearchContext+Integer.toString(i));
				PrintStream printStreamOriginal = System.out;
				System.setOut(new PrintStream(new OutputStream() {
					@Override
					public void write(int b) throws IOException {
						// NO OPERATION
					}
				}));

				GoogleResponse gResponse = customSearchInstance.getSearchResults(queryText+" site:java2s.com");
				//Call Restoration
				//Restoring SOP to actual stream
				System.setOut(printStreamOriginal);
				int resultIndex = 0;
				for (Item item : gResponse.getItems()) {
					String urlText = item.getLink().getHref();
					urlText = Jsoup.parse(urlText).text();
					String title = item.getTitle();
					title = Jsoup.parse(title).text();
					String summary = item.getSummary().trim();
					summary = Jsoup.parse(summary).text();
					String urlEnding = urlText.toUpperCase();
					if (!urlEnding.endsWith(".PDF") && !urlEnding.endsWith(".DOC") && !urlEnding.endsWith(".PPT")) {
					googleSearchResults.add(urlText);
					}
					System.out.println("Topic: "+title);
					System.out.println("Link: "+urlText);
					System.out.println("Preview: "+summary +"\n");
					resultIndex++;
					if (resultIndex < 6){
					ScrapeData scrapeDataObj = new ScrapeData();
					scrapeDataObj.setInfoQuery(queryText);
			  		scrapeDataObj.setInfoTopic(title);
			  		scrapeDataObj.setInfoTopicURL(urlText);
			  		scrapeDataObj.setInfoTopicLinkPreview(summary);
			  		scrapeDataObj.setInfoScrapeSite(websiteName);
			  		scrapeDataObj.setInfoScrapeSource(scrapeSource);
			  		scrapeDataObj.setInfoTopicResultindex(resultIndex);
			  		Java2sScrapeObj.add(scrapeDataObj);
					}
				}
				}
			} catch (Exception e) {			
				e.printStackTrace();
			}
			System.out.println("Result count of jGuru :"+Java2sScrapeObj.size());
			return Java2sScrapeObj;
		}
} 