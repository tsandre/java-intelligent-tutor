package itjava.scraper;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

/**
 * @author Vasanth Krishnamoorthy
 */

public class InfoScrape{
	public static LinkedHashSet<ScrapeData> ScrapeSites(String query){
		LinkedHashSet <ScrapeData> scrapeResultsObj = new LinkedHashSet <ScrapeData> ();
		LinkedHashSet <ScrapeData> scrapeResultsFinalObj = new LinkedHashSet <ScrapeData> ();
		ScrapeResult scrapeFinalResult = new ScrapeResult();
		int searchQueryId = 0;
		try {
			scrapeResultsObj.addAll(StackOverflow(query));
			scrapeResultsObj.addAll(Dzone(query));
			scrapeResultsObj.addAll(JavaBlogs(query));
			scrapeResultsObj.addAll(Java2s(query));
			scrapeResultsObj.addAll(JGuru(query));
			
			testObj.Test(scrapeResultsObj);
			ScrapeQueryStore dbstore = new ScrapeQueryStore();
			scrapeFinalResult = dbstore.ScrapeQueryRepoUpdate(scrapeResultsObj, query);
			scrapeResultsFinalObj = scrapeFinalResult.getScrapeResultsObj();
			searchQueryId = scrapeFinalResult.getSearchQueryId();
			testScrapeId.printScrapeId(scrapeResultsFinalObj);
			System.out.println("Ta da... All iz Well in Repo");
			System.out.println("Search Query Id :"+searchQueryId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			return scrapeResultsFinalObj;
	}

	private static LinkedHashSet <ScrapeData> StackOverflow(String query) {
		LinkedHashSet <ScrapeData> StackOverflowResults = StackoverflowScrape.ScrapeQuery(query);
		return StackOverflowResults;
	}

	private static LinkedHashSet <ScrapeData> Dzone(String query) {
		LinkedHashSet <ScrapeData> DzoneResults = DzoneScrape.ScrapeQuery(query);
		return DzoneResults;
	}
	
	private static LinkedHashSet <ScrapeData> JavaBlogs(String query) {
		LinkedHashSet <ScrapeData> JavaBlogsResults = JavaBlogsScrape.ScrapeQuery(query);
		return JavaBlogsResults;
	}
	
	private static LinkedHashSet <ScrapeData> Java2s(String query) {
		LinkedHashSet <ScrapeData> Java2sResults = Java2sScrape.ScrapeQuery(query);
		return Java2sResults;
	}

	private static LinkedHashSet <ScrapeData> JGuru(String query) {
		LinkedHashSet <ScrapeData> JGuruResults = jGuruScrape.ScrapeQuery(query);
		return JGuruResults;
	}



}