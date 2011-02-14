/**
 * 
 */
package itjava.db;

import itjava.data.LocalMachine;

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
					.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists TutorialInfo;");
			stat.executeUpdate("create table TutorialInfo " +
					"(tutorialInfoId INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"folderName varchar(50)," +
					"tutorialName varchar(50)," +
					"tutorialDescription varchar(200) default \" \"," +
					"numExamples integer default 0," +
					"numQuizes integer default 0," +
					"userLevel varchar(50)," +
					"creationDate Date default CURRENT_DATE," +
					"createdBy varchar(20) default \"testuser\"," +
					"timesAccessed integer default 0," +
					"constraint uniqTutorialInfo_folderNameCreatedBy unique(folderName, createdBy) " +
					"on conflict replace" +
					");");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from TutorialInfo;");
			while (rs.next()) {
				System.out.println("TutorialInfoId = " + rs.getString("tutorialInfoId"));
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
