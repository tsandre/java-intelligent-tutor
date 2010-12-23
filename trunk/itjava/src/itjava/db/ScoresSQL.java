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
public class ScoresSQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBConnection.GetConnection();
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists Scores;");
			stat.executeUpdate("create table Scores (" +
					" scoreId integer primary key autoincrement," +
					" deliverableId integer references DeliverableInfo(deliverableId), " +
					" studentId integer, " + //	references StudentInfo(studentId) 
					" score numeric(5, 2), " +
					" testDate date, " +
					" constraint uniqScore unique(deliverableId, studentId, testDate) " +
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from scores;");
			while (rs.next()) {
				System.out.println("studentId = " + rs.getInt("studentId"));
				System.out.println("score = " + rs.getFloat("score"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
