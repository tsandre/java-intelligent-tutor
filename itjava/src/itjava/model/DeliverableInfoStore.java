/**
 * 
 */
package itjava.model;

import itjava.db.DBConnection;
import itjava.util.KeyValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Aniket
 *
 */
public class DeliverableInfoStore {

	public static ArrayList<Integer> Select(String attribute, HashMap<String, Integer> whereClause) {
		Connection conn = null;
		ArrayList<Integer> retValue = new ArrayList<Integer>();
		try {
			conn = DBConnection.GetConnection();
			String selectSql = "select ? from deliverableInfo";
			if (whereClause != null) {
				if (!whereClause.isEmpty()) {
					selectSql += " where ";
					for (Entry<String, Integer> entry : whereClause.entrySet()) {
						selectSql += entry.getKey() + "=" + entry.getValue();
					}
				}
			}
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			selectStmt.setString(1, attribute);
			ResultSet rs = selectStmt.executeQuery();
			while (rs.next()) {
				retValue.add(rs.getInt(1));
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
	
	public static ArrayList<ArrayList<Object>> SelectKeyValue(ArrayList<String> attributes, HashMap<String, Integer> whereClause) {
		Connection conn = null;
		ArrayList<ArrayList<Object>> retValue = new ArrayList<ArrayList<Object>>();
		try {
			conn = DBConnection.GetConnection();
			String selectSql = "select ?,? from deliverableInfo";
			if (whereClause != null) {
				if (!whereClause.isEmpty()) {
					selectSql += " where ";
					for (Entry<String, Integer> entry : whereClause.entrySet()) {
						selectSql += entry.getKey() + "=" + entry.getValue();
					}
				}
			}
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			selectStmt.setString(1, attributes.get(0));
			selectStmt.setString(2, attributes.get(1));
			ResultSet rs = selectStmt.executeQuery();
			while (rs.next()) {
				ArrayList<Object> currRetVals = new ArrayList<Object>();
				currRetVals.add(rs.getInt(1));
				currRetVals.add(rs.getString(2));
				retValue.add(currRetVals);
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
	
	/**
	 * Inserts Name of the blank and corresponding number of Hints
	 * @param deliverableId
	 * @param wordInfoList
	 * @return Number of rows inserted in the database table
	 */
	public static int InsertBlankWordsInfo(int deliverableId, ArrayList<WordInfo> wordInfoList) {
		Connection conn = null;
		int rowsInserted = 0;
		try {
			conn = DBConnection.GetConnection();
			for (WordInfo wordInfo : wordInfoList) {
				String insertSql = "insert into WordInfo" +
						"(deliverableId, wordToBeBlanked, blankName, numOfHints)" +
						" values (?,?,?,?)";
				PreparedStatement insertStmt = conn.prepareStatement(insertSql);
				insertStmt.setInt(1, deliverableId);
				insertStmt.setString(2, wordInfo.wordToBeBlanked);
				insertStmt.setString(3, "txtLine" + wordInfo.lineNumber + "Col" + wordInfo.columnNumber);
				insertStmt.setInt(4, wordInfo.hintsAvailable.size());
				rowsInserted += insertStmt.executeUpdate();
			}
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
		return rowsInserted;
	}

}
