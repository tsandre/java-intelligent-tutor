package itjava.tests;

/**
 * Yahoo! Web Services Example: Parse search results
 *
 * @author Daniel Jones www.danieljones.org
 * Copyright 2007
 * 
 * This example illustrates how easy it is to parse a Yahoo! Web Service
 * XML response via XPath.
 * 
 * XPath expressions are much more straight forward than navigating the DOM. 
 * Java 5 introduced the javax.xml.xpath package, an XML object-model
 * independent library for querying documents with XPath. 
 * 
 * Learn more about XPath here: 
 * http://www.ibm.com/developerworks/xml/library/x-javaxpathapi.html
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class YahooWebServiceParseResults {

	/**
	 * This example illustrates how easy it is to parse a Yahoo! Web Service XML
	 * result with XPath.
	 */
	@Test
	public void GetSearchResult() {
		String query = "Scanner%20Java%20example";
		String request = "http://boss.yahooapis.com/ysearch/web/v1/"
				+ query
				+ "?appid=zfau5aPV34ETbq9mWU0ui5e04y0rIewg1zwvzHb1tGoBFK2nSCU1SKS2D4zphh2rd3Wf&format=xml&count=50";
		try {
			URL url = new URL(request);
			System.out.println("Host : " + url.getHost());
			URLConnection conn = url.openConnection();
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
				System.out.println(ele.text());	
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
