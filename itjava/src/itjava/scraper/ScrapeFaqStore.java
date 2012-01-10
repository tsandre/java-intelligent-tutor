package itjava.scraper;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @author Vasanth
 *
 */
public class ScrapeFaqStore {
	private Connection _conn;
	public LinkedHashSet<ScrapeData> ScrapeFaqRepoUpdate(int searchQueryId, LinkedHashSet<ScrapeData> scrapeResultsObj) {
		/*Writing file name & URL to DB*/
	//	int faqCount = scrapeResultsObj.size();
		try {
			GetConnection();
			String tempTopicHeading, tempLinkPreview, tempTopicURL, tempScrapeSource, tempWebsite;
			int tempSearchResultOrder, rowsInserted = 0, currentScrapeid = 0;
			Iterator <ScrapeData> scrapeObj = scrapeResultsObj.iterator();
			while(scrapeObj.hasNext()) {
				ScrapeData temp = (ScrapeData) scrapeObj.next();
				tempTopicHeading = temp.getInfoTopic();
				tempTopicURL = temp.getInfoTopicURL();
				tempLinkPreview = temp.getInfoTopicLinkPreview();
				tempScrapeSource = temp.getInfoScrapeSource();
				tempWebsite = temp.getInfoScrapeSite();
				tempSearchResultOrder = temp.getInfoTopicResultindex();
				
			String insertSql = "insert into ScrapeFAQ" +
			"(searchQueryId, topicHeading, topicURL, searchResultOrder, linkPreview, scrapeSource, websiteName)" +
			" values (?,?,?,?,?,?,?)";
			PreparedStatement insertScrapeFAQ = _conn.prepareStatement(insertSql);
			insertScrapeFAQ.setInt(1, searchQueryId);
			insertScrapeFAQ.setString(2, tempTopicHeading);
			insertScrapeFAQ.setString(3, tempTopicURL);
			insertScrapeFAQ.setInt(4, tempSearchResultOrder);
			insertScrapeFAQ.setString(5, tempLinkPreview);
			insertScrapeFAQ.setString(6, tempScrapeSource);
			insertScrapeFAQ.setString(7, tempWebsite);
			int inserted = insertScrapeFAQ.executeUpdate();
			rowsInserted = rowsInserted + inserted;
			
			String scrapeIdQuery = "select scrapeId from ScrapeFAQ where searchQueryId = ? and searchResultOrder = ?";
			PreparedStatement returnscrapeId = _conn.prepareStatement(scrapeIdQuery);
			returnscrapeId.setInt(1, searchQueryId);
			returnscrapeId.setInt(2, tempSearchResultOrder);
			ResultSet scrapeIdResult = returnscrapeId.executeQuery();
			while (scrapeIdResult.next()){
				currentScrapeid = scrapeIdResult.getInt("scrapeId");
			}
			temp.setScrapeId(currentScrapeid);
			}
			System.out.println("Num of rows inserted in table ScrapeQuery: " + rowsInserted);
			PreparedStatement fileNameSql = _conn.prepareStatement("select scrapeId, topicHeading, topicURL  from ScrapeFAQ where searchQueryId = ?");
			fileNameSql.setInt(1, searchQueryId);
			ResultSet rs = fileNameSql.executeQuery();
			while (rs.next()) {
				System.out.println("Scrape Id : " + rs.getInt("scrapeId"));
				System.out.println("Topic Heading : " + rs.getString("topicHeading"));
				System.out.println("Topic URL : " + rs.getString("topicURL"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				System.out.println("Scrape FAQ inserted in table successfully");
				DBConnection.CloseConnection(_conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return scrapeResultsObj;
	}
		private void GetConnection() throws Exception {
			_conn = DBConnection.GetConnection();
	}
	
}