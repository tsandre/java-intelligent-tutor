/**
 * 
 */
package itjava.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import itjava.data.Scores;
import itjava.db.DBConnection;

/**
 * @author Aniket
 *
 */
public class ScoresStore {

	public static int InsertScores(Scores scores) {
		Connection conn = null;
		int scoreId = -1;
		try {
			conn = DBConnection.GetConnection();
			String insertSql = "insert into Scores (deliverableId, studentId, score)" +
					" values(?, ?, ?)";
			PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, scores.getDeliverableId());
			insertStmt.setInt(2, scores.getStudentId());
			insertStmt.setInt(3, scores.getScore());
			int rowsInserted = insertStmt.executeUpdate();
			if (rowsInserted != 1) throw new SQLException("Problem inserting score data");
			ResultSet rs = insertStmt.getGeneratedKeys();
			if (rs.next()) {
				scoreId = rs.getInt(1);
			}
			else {
				throw new SQLException("Problem inserting score data");
			}
			//scoreId = get automatically generated key
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
		return scoreId;
	}

	public static ArrayList<Object> Select(String attribute, HashMap<String, String> whereClause) {
		Connection conn = null;
		ArrayList<Object> retValue = null;
		try {
			conn = DBConnection.GetConnection();
			String selectSql = "select ? from Scores";
			if (whereClause != null) {
				if (!whereClause.isEmpty()) {
					selectSql += " where ";
					for (Entry<String, String> entry : whereClause.entrySet()) {
						selectSql += entry.getKey() + "=" + entry.getValue();
					}
				}
			}
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			selectStmt.setString(1, attribute);
			ResultSet rs = selectStmt.executeQuery();
			while (rs.next()) {
				retValue.add(rs.getObject(1));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retValue;
	}

}
