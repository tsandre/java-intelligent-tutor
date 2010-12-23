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
 * @author Aniket
 * 
 */
public class DeliverableInfoSQL {

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
			stat.executeUpdate("drop table if exists DeliverableInfo;");
			stat.executeUpdate("create table DeliverableInfo (" +
					" deliverableId integer primary key autoincrement," +
					" deliverableName varchar(15)," +
					" tutorialInfoId integer references TutorialInfo(tutorialInfoId)," +
					" deliverableType char(7)," + 
					" difficultyLevel integer default 0," +
					" numOfBlanks integer default 0, " +
					" constraint uniqDeliverableInfo unique(deliverableName, tutorialInfoId) " +
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from DeliverableInfo;");
			while (rs.next()) {
				System.out.println("DeliverableName = " + rs.getString("deliverableName"));
				System.out.println("TutorialInfoId = " + rs.getString("tutorialInfoId"));
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
