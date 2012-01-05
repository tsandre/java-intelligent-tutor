package itjava.scraper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Vasanth
 *
 */
public class testScrape {

	private HashMap<String, String> whereClause;

	@Test
	public void ScrapeCode() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		ConnectSite();

	}
	
	@Test
	public void ConnectSite() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String url = "http://stackoverflow.com/search?q=";
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
	  	HtmlPage currentPage = webClient.getPage(url);
	}

	private void ThenCheckIfTableHasInsertedEntries() {
		whereClause = new HashMap<String, String>();
		whereClause.put("folderName", "folderName1");
	//	assertEquals("currentUser1", TutorialInfoStore.SelectInfo(whereClause).get(0).getCreatedBy());
		whereClause.clear();
		whereClause.put("tutorialName", "Tutorial Name2");
		//assertEquals("description...", TutorialInfoStore.SelectInfo(whereClause).get(0).getTutorialDescription());
	}
}
