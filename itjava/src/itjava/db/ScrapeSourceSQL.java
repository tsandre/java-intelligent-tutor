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
public class ScrapeSourceSQL {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists ScrapeSource;");
			stat.executeUpdate("create table ScrapeSource (" +
					" scrapeSourceId integer primary key autoincrement," +
					" scrapeSource varchar(100)"+
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from ScrapeSource;");
			while (rs.next()) {
				System.out.println("Id : " + rs.getString("scrapeSourceId"));
				System.out.println("Scrape Source : " + rs.getString("scrapeSource"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("ScrapeSource table created successfully !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
