/**
 * 
 */
package itjava.scraper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Bharat
 * 
 */
public class FAQRetriever {

	public HashMap<Integer, ArrayList<ScrapeData>> getFAQforTutorial(int tutorialInfoId) {
		Connection _conn = null;
		HashMap<Integer, ArrayList<ScrapeData>> faqItemMap = null;
		try {
			_conn = DBConnection.GetConnection();
			
			String faqItemsQuery = "select scrapefaq.*, ScrapeFAQSelection.faqType as faqType from ScrapeFAQSelection, ScrapeFAQ, ScrapeQuery where ScrapeFAQSelection.scrapeId=ScrapeFAQ.scrapeId and ScrapeFaq.searchQueryId=ScrapeQuery.searchQueryId and ScrapeQuery.tutorialInfoId=? and ScrapeFAQSelection.faqType<>5;";
			PreparedStatement returnFaqItems = _conn
					.prepareStatement(faqItemsQuery);
			returnFaqItems.setInt(1, tutorialInfoId);
			ArrayList<ScrapeData> faqItemList = new ArrayList<ScrapeData>();
			ArrayList<ScrapeData> tutorialItemList = new ArrayList<ScrapeData>();
			ArrayList<ScrapeData> articleItemList = new ArrayList<ScrapeData>();
			ArrayList<ScrapeData> rlItemList = new ArrayList<ScrapeData>();
			ResultSet faqItems = returnFaqItems.executeQuery();
			while (faqItems.next()) {
				ScrapeData faqItem = new ScrapeData();
				faqItem.setScrapeId(faqItems.getInt("scrapeId"));
				// faqItem.setInfoQuery(faqItems.getInt("searchQueryId"));
				faqItem.setInfoScrapeSite(faqItems.getString("websiteName"));
				faqItem.setInfoScrapeSource(faqItems.getString("scrapeSource"));
				faqItem.setInfoTopic(faqItems.getString("topicHeading"));
				faqItem.setInfoTopicLinkPreview(faqItems
						.getString("linkPreview"));
				faqItem.setInfoTopicResultindex(faqItems
						.getInt("searchResultOrder"));
				faqItem.setInfoTopicURL(faqItems.getString("topicURL"));
				switch (faqItems.getInt("faqType")) {
				case 1:
					faqItemList.add(faqItem);
					break;
				case 2:
					tutorialItemList.add(faqItem);
					break;
				case 3:
					articleItemList.add(faqItem);
					break;
				case 4:
					rlItemList.add(faqItem);
					break;
				}
				
			}
			
			faqItemMap = new HashMap<Integer, ArrayList<ScrapeData>>();
			faqItemMap.put(new Integer(1), faqItemList);
			faqItemMap.put(new Integer(2), tutorialItemList);
			faqItemMap.put(new Integer(3), articleItemList);
			faqItemMap.put(new Integer(4), rlItemList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnection.CloseConnection(_conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	return faqItemMap;
	}

}
