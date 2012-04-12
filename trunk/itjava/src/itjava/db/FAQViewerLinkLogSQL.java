package itjava.db;

import itjava.data.LocalMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vasanth
 * 
 */
public class FAQViewerLinkLogSQL {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists FAQViewerLinkLog;");
			stat.executeUpdate("create table FAQViewerLinkLog (" +
					" logId integer primary key autoincrement," +
					" user varchar(100)," +
					" scrapeId integer," +
					" timeAccessed varchar(50)" +					
					" );");
			conn.setAutoCommit(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("ScrapeFAQSelection table created successfully.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
