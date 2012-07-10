package itjava.scraper;


import java.util.ArrayList;
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
public class testGoogleCustomSearch{
	public static void main(String args[]){
		GoogleSearch("Connecting to Database");
	}
	private static ArrayList<String> GoogleSearch(String query) {
		ArrayList<String> googleSearchResults = new ArrayList<String>();
		String googleSearchContext = "011045704107476092050:liivikkqg0k";
		String googleSearchAPIKey = "AIzaSyCdECK7u3Erfb23uPqCEl2lESCDw4cwOXA";
		try {
			NetHttpTransport nht = new NetHttpTransport();
			JacksonFactory jf = new JacksonFactory();
			Customsearch customsearch = new Customsearch(nht, jf);
			Customsearch.Cse.List request = customsearch.cse().list(query+" site:java2s.com");
			request.setKey(googleSearchAPIKey);
			request.setCx(googleSearchContext);

			for(long iResultIndex=1; iResultIndex<50;iResultIndex+=10) {
				request.setStart(iResultIndex);
				Search results = request.execute();
				List<Result> searchResults = results.getItems();
				for(Result item:searchResults) {
					String urlText = item.getLink();
					String urlEnding = urlText.toUpperCase();
					System.out.println("GoogleResult: "+urlText);
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
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return googleSearchResults;
	}

}

