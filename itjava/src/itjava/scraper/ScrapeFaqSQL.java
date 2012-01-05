package itjava.scraper;

import itjava.data.LocalMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vasanth
 * 
 */
public class ScrapeFaqSQL {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists ScrapeFAQ;");
			stat.executeUpdate("create table ScrapeFAQ (" +
					" scrapeId integer primary key autoincrement," +
					" searchQueryId integer references SearchQueryTable(searchQueryId)," +
					" topicHeading varchar(100)," +
					" topicURL varchar(200)," + 
					" searchResultOrder integer," +
					" linkPreview varchar(500)," + 
					" scrapeSource varchar(20)," + 
					" websiteName varchar(20)," + 
					" constraint uniqScrapeFAQ unique(scrapeId) " +
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from ScrapeFAQ;");
			while (rs.next()) {
				System.out.println("Topic : " + rs.getString("topicHeading"));
				System.out.println("Search Query Id : " + rs.getString("searchQueryId"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("ScrapeFAQ table created successfully !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
