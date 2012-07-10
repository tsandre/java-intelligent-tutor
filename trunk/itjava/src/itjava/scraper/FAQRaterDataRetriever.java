/**
 * 
 */
package itjava.scraper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bharat, Vasanth
 * 
 */
public class FAQRaterDataRetriever {

	public boolean isScrapeRatedByUser(String userID, Integer scrapeID) {
		Connection _conn = null;
		try {
			_conn = DBConnection.GetConnection();
			String isScrapeRatedByUserQuery = "select FR.userRatingYes, FR.userRatingNo, FR.timeAccessed from FAQUserRatingLog FR where FR.scrapeId=? and FR.user=? ;";
			PreparedStatement isScrapeRatedByUserPS = _conn
					.prepareStatement(isScrapeRatedByUserQuery);
			isScrapeRatedByUserPS.setInt(1, scrapeID);
			isScrapeRatedByUserPS.setString(2, userID);
			if (isScrapeRatedByUserPS.executeQuery().next() == false) {
				isScrapeRatedByUserPS.close();
				_conn.close();
				return false;
			} else {
				isScrapeRatedByUserPS.close();
				_conn.close();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public Map<String, Integer> getGeneralScrapeRatingData(Integer scrapeId) {
		Connection _conn = null;
		try {
			_conn = DBConnection.GetConnection();
			String scrapeRatingInfoQuery = "select count(FR.userRatingYes) from FAQUserRatingLog FR where FR.scrapeId=? AND FR.userRatingYes=1 ;";
			PreparedStatement scrapeRatingInfoPS = _conn
					.prepareStatement(scrapeRatingInfoQuery);
			scrapeRatingInfoPS.setInt(1, scrapeId);
			ResultSet scrapeYesResult = scrapeRatingInfoPS.executeQuery();
			scrapeYesResult.next();
			Integer userRatingYesCount = scrapeYesResult.getInt(1);
			scrapeYesResult.close();
			scrapeRatingInfoPS .close();
			_conn.close();
			
			_conn = DBConnection.GetConnection();
			String scrapeRatingNoInfoQuery = "select count(FR.userRatingNo) from FAQUserRatingLog FR where FR.scrapeId=? AND FR.userRatingNo=1 ;";
			PreparedStatement scrapeRatingNoInfoPS = _conn
					.prepareStatement(scrapeRatingNoInfoQuery);
			scrapeRatingNoInfoPS.setInt(1, scrapeId);
			ResultSet scrapeNoResult = scrapeRatingNoInfoPS.executeQuery();
			scrapeNoResult.next();
			Integer userRatingNoCount = scrapeNoResult.getInt(1);
			scrapeNoResult.close();
			scrapeRatingNoInfoPS .close();
			_conn.close();
			Map<String, Integer> scrapeRating = new HashMap<String, Integer>();
			scrapeRating.put("RatingYes", userRatingYesCount);
			scrapeRating.put("RatingNo", userRatingNoCount);
			return scrapeRating;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
			
	}

	public UserRatingData getUserRatingDetailsForScrape(String userID, Integer scrapeID) {
		Connection _conn = null;
		try {
			_conn = DBConnection.GetConnection();
			String isScrapeRatedByUserQuery = "select FR.ratingLogId, FR.userRatingYes, FR.userRatingNo, FR.timeAccessed from FAQUserRatingLog FR where FR.scrapeId=? and FR.user=? ;";
			PreparedStatement isScrapeRatedByUserPS = _conn
					.prepareStatement(isScrapeRatedByUserQuery);
			isScrapeRatedByUserPS.setInt(1, scrapeID);
			isScrapeRatedByUserPS.setString(2, userID);
			ResultSet userRatingDataRS = isScrapeRatedByUserPS.executeQuery();
			UserRatingData userRating = new UserRatingData();
			if (userRatingDataRS.next() == true) {
				
				userRating.setRatingLogId(userRatingDataRS.getInt(1));
				userRating.setScrapeId(scrapeID);
				userRating.setUser(userID);
				userRating.setTimeAccessed(userRatingDataRS.getString(4));
				userRating.setUserRatingNo(userRatingDataRS.getInt(3));
				userRating.setUserRatingYes(userRatingDataRS.getInt(2));

			}
			userRatingDataRS.close();
			isScrapeRatedByUserPS.close();
			_conn.close();
			return userRating;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
