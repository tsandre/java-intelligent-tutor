/**
 * 
 */
package itjava.scraper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Bharat
 * 
 */
public class FAQRetriever {
	
	
	
	

	public void getFAQforTutorial(int tutorialInfoId) {
		Connection _conn = null;
		try {
			_conn = DBConnection.GetConnection();
			//Should change to tutorialInfoId
			String faqItemsQuery = "select ScrapeFAQ.* from ScrapeFAQ, ScrapeFAQSelection, ScrapeQuery where ScrapeFAQ.searchQueryId = ScrapeQuery.searchQueryId and ScrapeFAQ.scrapeId = ScrapeFAQSelection.scrapeId and ScrapeQuery.searchQueryId = ?;";
			PreparedStatement returnFaqItems = _conn
					.prepareStatement(faqItemsQuery);
			returnFaqItems.setInt(1, tutorialInfoId);

			ResultSet faqItems = returnFaqItems.executeQuery();
			while (faqItems.next()) {
				 System.out.println("Result: "+faqItems.getInt("scrapeId"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnection.CloseConnection(_conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
