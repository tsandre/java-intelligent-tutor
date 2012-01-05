package itjava.scraper;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.jsoup.Jsoup;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;


/**
 * @author Vasanth Krishnamoorthy
 * 
 */

public class JavaBlogsScrape {
	public static LinkedHashSet <ScrapeData> ScrapeQuery(String queryText){
		LinkedHashSet <ScrapeData> JavaBlogsScrapeObj = new LinkedHashSet <ScrapeData> ();
		try {
		String websiteName = "javablogs.com";
		String scrapeSource = "JavaBlogs";
		String url = "http://javablogs.com/Search.action?query=";
		String baseUrl = "http://javablogs.com";

		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
	  	HtmlPage page = webClient.getPage(url);
	  	HtmlForm searchForm = page.getFormByName("searchForm");
	  	DomNodeList<HtmlElement> inputTags = searchForm.getElementsByTagName("input");
	  	HtmlElement searchBar = inputTags.get(0);
	  	searchBar.setAttribute("value", queryText);
	  	HtmlSubmitInput searchButton = (HtmlSubmitInput) inputTags.get(1);
	  	HtmlPage resultsPage = searchButton.click();
	  	HtmlForm searchResultForm = resultsPage.getFormByName("searchForm");
	  	HtmlElement enclosingDiv = searchResultForm.getEnclosingElement("div");
	  	
	  //	List <HtmlElement> searchDivs = enclosingDiv.getElementsByAttribute("a", "class", "blogentrylink");
	  	List <HtmlElement> linkHeadingAnchor  = enclosingDiv.getElementsByAttribute("a", "class", "blogentrylink"); //problem
	  	List <HtmlElement> linkSites  = enclosingDiv.getElementsByAttribute("a", "title", "View blog details"); //problem
	  	List <HtmlElement> linkPreviews  = enclosingDiv.getElementsByAttribute("div", "class", "blogentrysummary");
	 
	  	ArrayList<String> topic = new ArrayList<String>();
	  	ArrayList<String> topicURL = new ArrayList<String>();
	  	ArrayList<String> siteInfo = new ArrayList<String>();
	  	ArrayList<String> linkPreview = new ArrayList<String>();
	  	
	  	for(HtmlElement resTitleElement : linkHeadingAnchor) {
	  		String resTitleString = resTitleElement.getTextContent().trim();
	  		topic.add(resTitleString);	//ADD
	  		String resTitleLink = resTitleElement.getAttribute("href");
	  		String finalUrl = baseUrl.concat(resTitleLink);
	  		topicURL.add(finalUrl); //ADD
	  	//	System.out.println("Testing heading loop");
	  		System.out.println(resTitleString+" : "+finalUrl);
	  	} 
	  	
	  	for(HtmlElement resLinkSite : linkSites) {
	  		String resLinkString = resLinkSite.getTextContent();
	  		siteInfo.add(resLinkString);	//ADD
	  	//	System.out.println("Testing site loop");
	  		System.out.println("Site : "+resLinkString);
	  	} 
	  	
	  	for(HtmlElement resLinkPreview : linkPreviews) {
	  		String resPreviewString = resLinkPreview.getTextContent().trim().replaceAll(" +", " ");
	  		resPreviewString = Jsoup.parse(resPreviewString).text();
	  		// We could probably copy the entire <HTML> if needed and add it as such while displaying.
	  		linkPreview.add(resPreviewString);	//ADD
	  	//	System.out.println("Testing preview loop");
	  		System.out.println("Link Preview : "+resPreviewString);
	  	}
	  	
	  	for(int j =0; j < 5; j++) {
	  		ScrapeData scrapeDataObj = new ScrapeData();
	  		scrapeDataObj.setInfoQuery(queryText);
	  		scrapeDataObj.setInfoTopic(topic.get(j));
	  		scrapeDataObj.setInfoTopicURL(topicURL.get(j));
	  		scrapeDataObj.setInfoTopicLinkPreview(linkPreview.get(j));
	  		scrapeDataObj.setInfoScrapeSite(siteInfo.get(j));
	  		scrapeDataObj.setInfoScrapeSource(scrapeSource);
	  		scrapeDataObj.setInfoTopicResultindex(j + 1);
	  		JavaBlogsScrapeObj.add(scrapeDataObj);
	  	}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	System.out.println("Result count of JavaBlogs :"+JavaBlogsScrapeObj.size());
	return JavaBlogsScrapeObj;
	}
}
 