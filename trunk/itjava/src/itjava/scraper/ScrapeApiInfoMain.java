package itjava.scraper;

import java.util.LinkedHashSet;

public class ScrapeApiInfoMain {
	public static void main(String[] args) {
		String query = "Reading a file using Java";
		ScrapeResult scrapeFinalObj = InfoScrape.ScrapeSites(query);
	}
}
