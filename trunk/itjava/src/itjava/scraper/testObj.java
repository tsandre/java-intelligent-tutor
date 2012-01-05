package itjava.scraper;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @author Vasanth Krishnamoorthy
 * Fires an empty search query in 'sourceforge.net' and retrieve the link to the next page.
 */

public class testObj {
	public static void Test(LinkedHashSet <ScrapeData> testObj){
		int count = testObj.size();
		System.out.println("Total Results: "+count);
		Iterator <ScrapeData> test = testObj.iterator();
		while (test.hasNext()){
			ScrapeData temp = test.next();
			System.out.println("Topic: "+temp.getInfoTopic());
			System.out.println("Link: "+temp.getInfoTopicURL());
			System.out.println("Source: "+temp.getInfoScrapeSite());
		}
	}
}