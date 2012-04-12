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
public class FAQUserRatingLogSQL {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists FAQUserRatingLog;");
			stat.executeUpdate("create table FAQUserRatingLog (" +
					" ratingLogId integer primary key autoincrement," +
					" user varchar(100)," +
					" scrapeId integer," +
					" userRatingYes integer," +
					" userRatingNo integer," +
					" timeAccessed varchar(50)" +					
					" );");
			conn.setAutoCommit(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("FAQUserRatingLog table created successfully.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
