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
public class QuizInfoSQL {

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
			stat.executeUpdate("drop table if exists QuizInfo;");
			stat.executeUpdate("create table QuizInfo (" +
					" quizId integer primary key autoincrement," +
					" quizName varchar(15)," +
					" tutorialId integer references TutorialInfo(tutorialId)," +
					" difficultyLevel integer," +
					" constraint uniqQuizInfo unique(quizName, tutorialId) " +
					" );");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from QuizInfo;");
			while (rs.next()) {
				System.out.println("QuizName = " + rs.getString("quizName"));
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
