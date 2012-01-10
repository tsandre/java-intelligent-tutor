/**
 * 
 */
package itjava.scraper;

import java.util.LinkedHashSet;

/**
 * @author Vasanth
 * 
 */
public class ScrapeResult {

	private LinkedHashSet <ScrapeData> scrapeResultsObj = new LinkedHashSet <ScrapeData> ();
	private int searchQueryId;

	
	public void setScrapeResultsObj(LinkedHashSet <ScrapeData> scrapeResultsObj) {
		this.scrapeResultsObj = scrapeResultsObj;
	}
	public LinkedHashSet <ScrapeData> getScrapeResultsObj() {
		return scrapeResultsObj;
	}
	public void setSearchQueryId(int searchQueryId) {
		this.searchQueryId = searchQueryId;
	}
	public int getSearchQueryId() {
		return searchQueryId;
	}

	
	
}

