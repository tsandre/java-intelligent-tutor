/**
 * 
 */
package itjava.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Aniket, Matt
 *
 */
public class LinkStore {
	private static String _query;
	private static LinkedHashSet<String> _setOfLinks;
	
	public static LinkedHashSet<String> CreateLinks(String query) {
		_setOfLinks = new LinkedHashSet<String>();
		_query = query;
		//_setOfLinks.addAll(GoogleSearch());
		//_setOfLinks.addAll(BingSearch());
		_setOfLinks.addAll(YahooSearch());
		return _setOfLinks;
	}

	private static ArrayList<String> YahooSearch() {
		ArrayList<String> yahooSearchResults = new ArrayList<String>();
		// TODO Populate googleSearchResults with url strings
		try {
			String request = "http://boss.yahooapis.com/ysearch/web/v1/"
				+ URLEncoder.encode(_query, "UTF-8")
				+ "?appid=zfau5aPV34ETbq9mWU0ui5e04y0rIewg1zwvzHb1tGoBFK2nSCU1SKS2D4zphh2rd3Wf&format=xml&count=50";
		
			URL url = new URL(request);
			System.out.println("Host : " + url.getHost());
			url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String inputLine;
			String finalContents = "";
			while ((inputLine = reader.readLine()) != null) {
				finalContents += "\n" + inputLine;
			}
			Document doc = Jsoup.parse(finalContents);
			Elements eles = doc.getElementsByTag("url");
			for (Element ele : eles) {
				yahooSearchResults.add(ele.text());	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yahooSearchResults;
	}
}
