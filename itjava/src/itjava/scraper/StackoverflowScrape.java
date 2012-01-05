package itjava.scraper;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

/**
 * @author Vasanth Krishnamoorthy
 * Fires an empty search query in 'sourceforge.net' and retrieve the link to the next page.
 */

public class StackoverflowScrape{
	public static LinkedHashSet <ScrapeData> ScrapeQuery(String queryText){
		LinkedHashSet <ScrapeData> StackoverflowScrapeObj = new LinkedHashSet <ScrapeData> ();
		try {
		String websiteName = "stackoverflow.com";
		String scrapeSource = "Stackoverflow";
		String url = "http://stackoverflow.com/search?q=";
		String userInput = queryText;
		String tags = "[java] ";
		String query = tags.concat(userInput);
		
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
	  	HtmlPage page = webClient.getPage(url);
	  	HtmlElement	searchGoogle = page.getHtmlElementById("google-search");
	  	HtmlTextInput searchbar = searchGoogle.getOneHtmlElementByAttribute("input", "type", "text");
	  	searchbar.setValueAttribute(query);
	  	HtmlSubmitInput searchbutton = searchGoogle.getOneHtmlElementByAttribute("input", "type", "submit");
	  	HtmlPage resultsPage = searchbutton.click();
	  	HtmlElement results = resultsPage.getHtmlElementById("ires");

	  	List <HtmlElement> linkHeadings  = results.getElementsByAttribute("h3", "class", "r"); 
	  	List <HtmlElement> shortLinks = results.getElementsByTagName("cite");
	  //	List <HtmlElement> numAnswers  = results.getElementsByAttribute("div", "class", "f kb");
	  	List <HtmlElement> siteLinesList = results.getElementsByAttribute("span", "class", "st");
	  	
	  	ArrayList<String> topic = new ArrayList<String>();
	  	ArrayList<String> topicURL = new ArrayList<String>();
	  	ArrayList<String> shortURL = new ArrayList<String>();
	  	ArrayList<String> answerCount = new ArrayList<String>();
	  	ArrayList<String> linkPreview = new ArrayList<String>();
	  	
	  	for(HtmlElement resTitleElement : linkHeadings) {
	  		String resTitleString = resTitleElement.getTextContent();
	  		topic.add(resTitleString);	//ADD
	  		Iterable <HtmlElement> resAnchorList = resTitleElement.getHtmlElementsByTagName("a");
	  		HtmlElement resAnchor = resAnchorList.iterator().next();
	  		URL resTitleLink = new URL(resAnchor.getAttribute("href"));
	  		topicURL.add(resTitleLink.toString()); //ADD
	  		System.out.println("Testing heading loop");
	  		System.out.println(resTitleString+" : "+resTitleLink);
	  	} 

	  	for(HtmlElement shortLink : shortLinks) {
	  		String resLinkPrevString = shortLink.getTextContent();
	  		shortURL.add(resLinkPrevString); //ADD
	  		System.out.println("Testing link-preview loop");
	  		System.out.println("Shortened Link : "+resLinkPrevString);
	  	} 
	  	
//	  	for(HtmlElement numAnswer : numAnswers) {
//	  		String numAnswerString = numAnswer.getTextContent();
//	  		answerCount.add(numAnswerString);
//	  		System.out.println("Testing No. of Answers loop");
//	  		System.out.println("No. of Answers : "+numAnswerString);
//	  	} 
	  	
	  	for(HtmlElement siteLines : siteLinesList) {
	  		String siteLinesString = siteLines.getTextContent();
	  		linkPreview.add(siteLinesString);
	  		System.out.println("Testing Site Lines loop");
	  		System.out.println("Site's short Content : "+siteLinesString);
	  	}
	  	
	  	for(int i =0; i < 5 ; i++) {
	  		ScrapeData scrapeDataObj = new ScrapeData();
	  		scrapeDataObj.setInfoQuery(userInput);
	  		scrapeDataObj.setInfoTopic(topic.get(i));
	  		scrapeDataObj.setInfoTopicURL(topicURL.get(i));
	  		scrapeDataObj.setInfoTopicShortURL(shortURL.get(i));
	  		scrapeDataObj.setInfoTopicLinkPreview(linkPreview.get(i));
	  		scrapeDataObj.setInfoTopicResultindex(i + 1);
	  		scrapeDataObj.setInfoScrapeSite(websiteName);
	  		scrapeDataObj.setInfoScrapeSource(scrapeSource);
	  		
	  		StackoverflowScrapeObj.add(scrapeDataObj);
	  	}
  	    }
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Result count of Stackoverflow :"+StackoverflowScrapeObj.size());
		return StackoverflowScrapeObj;
	}
} 