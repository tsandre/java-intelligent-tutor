/**
 * 
 */
package itjava.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author student
 * 
 */
public class TutorialInfoSQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager
					.getConnection("jdbc:sqlite:samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists TutorialInfo;");
			stat.executeUpdate("create table TutorialInfo " +
					"(tutorialId INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"folderName varchar(50)," +
					"tutorialName varchar(50)," +
					"tutorialDescription varchar(200)," +
					"numExamples integer," +
					"numQuizes integer," +
					"creationDate Date," +
					"createdBy integer," +
					"timesAccessed integer" +
					");");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from TutorialInfo;");
			while (rs.next()) {
				System.out.println("TutorialId = " + rs.getString("tutorialId"));
				System.out.println("folderName = " + rs.getString("folderName"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
