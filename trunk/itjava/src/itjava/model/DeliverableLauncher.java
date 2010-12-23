/**
 * 
 */
package itjava.model;

import itjava.db.DBConnection;
import itjava.util.KeyValue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Aniket
 *
 */
public class DeliverableLauncher {
	/**
	 * Finds the easiest of the deliverables available corresponding to the tutorialId passes as parameter
	 * @param tutorialId Primary key of the {@link TutorialInfo}
	 * @return < id, Name> of the deliverable resource.
	 */
	public static KeyValue<Integer,String> GetFirstDeliverableName(int tutorialId) {
		Connection conn = null;
		String deliverableName = null;
		int deliverableId = -1;
		
		try {
			 conn = DBConnection.GetConnection();
			 String getFirstSql = "select min(deliverableName), deliverableId from DeliverableInfo " + 
			 	"where tutorialInfoId = ? " +
			 	" and deliverableType = ?";
			 PreparedStatement selectStmt = conn.prepareStatement(getFirstSql);
			 selectStmt.setInt(1, tutorialId);
			 selectStmt.setString(2, "Example");
			 
			 ResultSet rs = selectStmt.executeQuery();
			 if (rs.next()) {
				 deliverableName = rs.getString(1);
				 deliverableId = rs.getInt(2);
			 }
			 else {
				 selectStmt.setInt(1, tutorialId);
				 selectStmt.setString(2, "Quiz");
				 //TODO: Add to whereclause : min(difficultylevel)
				 
				 rs = selectStmt.executeQuery();
				 if (rs.next()) {
					 deliverableName = rs.getString(1);
					 deliverableId = rs.getInt(2);
				 }
				 else {
					 System.err.println("0 tuples for TutorialInfoId : " + tutorialId);	 
				 }
			 }
			 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
		return new KeyValue<Integer, String>(deliverableId, deliverableName);
	}

	public static void SetScore(int studentId, int deliverableId, int score) {
		Connection conn = null;
		try {
			conn = DBConnection.GetConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnection.CloseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
