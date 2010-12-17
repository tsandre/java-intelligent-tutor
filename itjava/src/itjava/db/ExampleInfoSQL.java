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
public class ExampleInfoSQL {

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
			stat.executeUpdate("drop table if exists ExampleInfo;");
			stat.executeUpdate("create table ExampleInfo (" +
					" exampleId integer primary key autoincrement," +
					" exampleName varchar(15)," +
					" tutorialId integer references TutorialInfo(tutorialId)," +
					" constraint uniqExamples unique(exampleName, tutorialId) " +
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from ExampleInfo;");
			while (rs.next()) {
				System.out.println("ExampleName = " + rs.getString("exampleName"));
				System.out.println("TutorialId = " + rs.getString("tutorialId"));
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
