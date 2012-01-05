package itjava.scraper;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;


/**
 * @author Vasanth Krishnamoorthy
 * 
 */

public class DzoneScrape {
	public static LinkedHashSet <ScrapeData> ScrapeQuery(String queryText){
		LinkedHashSet <ScrapeData> DzoneScrapeObj = new LinkedHashSet <ScrapeData> ();
		try {
			String websiteName = "dzone.com";
			String scrapeSource = "DZone";
			String url = "http://www.dzone.com/links/search.html?query=";
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		  	HtmlPage page = webClient.getPage(url);
		  	HtmlElement searchForm = page.getElementById("localSearch");
		  	HtmlElement searchBar = searchForm.getElementById("localSearchQuery");
		  	searchBar.setAttribute("value", queryText);
		  	HtmlSubmitInput searchButton = (HtmlSubmitInput) searchForm.getOneHtmlElementByAttribute("input", "class", "hiddenSubmit");
		  	HtmlPage resultsPage = searchButton.click();
		  	
		  	HtmlElement searchResultsDiv = resultsPage.getElementById("content-inner");
		  	List <HtmlElement> linkHeading  = searchResultsDiv.getElementsByAttribute("a", "rel", "bookmark"); //problem
		  	List <HtmlElement> linkSource  = searchResultsDiv.getElementsByAttribute("p", "class", "fineprint byline"); //problem
		  	List <HtmlElement> linkPreviews  = searchResultsDiv.getElementsByAttribute("p", "class", "description");
		 
		  	ArrayList<String> topic = new ArrayList<String>();
		  	ArrayList<String> topicURL = new ArrayList<String>();
		  	ArrayList<String> siteInfo = new ArrayList<String>();
		  	ArrayList<String> linkPreview = new ArrayList<String>();
		  	int i = 0;
		  	
		  	for(HtmlElement resTitleElement : linkHeading) {
		  		String resTitleString = resTitleElement.getTextContent().trim().replaceAll(" +", " ");
		  		topic.add(resTitleString);	//ADD
		  		String finalUrl = resTitleElement.getAttribute("href").trim().replaceAll(" +", " ");
		  		topicURL.add(finalUrl); //ADD
		  		System.out.println("Testing heading loop");
		  		System.out.println(resTitleString+" : "+finalUrl);
		  	} 
		  	
		  	for(HtmlElement resLinkSite : linkSource) {
		  		Iterable<HtmlElement> resLink = resLinkSite.getChildElements();
		  		String resLinkString = "";
		  		int elemcount = 1;
		  		for (HtmlElement temp : resLink){
		  			if (elemcount == 1){
		  				resLinkString += temp.getTextContent() + " via ";
		  			}
		  			else {
		  				resLinkString += temp.getTextContent();
		  			}
		  			elemcount++;
		  		}
		  //		String resLinkString = resLinkSite.getTextContent().trim().replaceAll(" +", " ");
		  		if (i%2 == 0){
		  		siteInfo.add(resLinkString);	//ADD
		  		System.out.println("Testing site loop");
		  		System.out.println("Site : "+resLinkString);
		  		}
		  		i++;
		  	} 
		  	
		  	for(HtmlElement resLinkPreview : linkPreviews) {
		  		String resPreviewString = resLinkPreview.getTextContent().trim().replaceAll(" +", " ");
		  		// We could probably copy the entire <HTML> if needed and add it as such while displaying.
		  		linkPreview.add(resPreviewString);	//ADD
		  		System.out.println("Testing preview loop");
		  		System.out.println("Link Preview : "+resPreviewString);
		  	}
		  	
		  	for(int j =0; j < 5; j++) {
		  		ScrapeData scrapeDataObj = new ScrapeData();
		  		scrapeDataObj.setInfoQuery(queryText);
		  		scrapeDataObj.setInfoTopic(topic.get(j));
		  		scrapeDataObj.setInfoTopicURL(topicURL.get(j));
		  		scrapeDataObj.setInfoTopicLinkPreview(linkPreview.get(j));
		  		scrapeDataObj.setInfoScrapeSite(siteInfo.get(j));
		  		scrapeDataObj.setInfoTopicResultindex(j + 1);
		  		scrapeDataObj.setInfoScrapeSource(scrapeSource);
		  		
		  		DzoneScrapeObj.add(scrapeDataObj);
		  	}
		  	}
			catch (Exception e) {
				e.printStackTrace();
			}
		System.out.println("Result count of Dzone :"+DzoneScrapeObj.size());
		return DzoneScrapeObj;
	}
} 