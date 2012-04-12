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
public class ScrapeQuerySQL {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists ScrapeQuery;");
			stat.executeUpdate("create table ScrapeQuery (" +
					" searchQueryId integer primary key autoincrement," +
					" searchQueryText varchar(200)," +
					" tutorialInfoId integer" +
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from ScrapeQuery;");
			while (rs.next()) {
				System.out.println("Query Id : " + rs.getString("searchQueryId"));
				System.out.println("Search Query : " + rs.getString("searchQueryText"));
				System.out.println("Tutorial Info Id : " + rs.getInt("tutorialInfoId"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("ScrapeQuery table created successfully !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
