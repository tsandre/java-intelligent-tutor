/**
 * 
 */
package itjava.model;

import itjava.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Aniket
 *
 */
public class DeliverableInfoStore {

	public static ArrayList<Object> Select(String attribute, HashMap<String, Integer> whereClause) {
		Connection conn = null;
		ArrayList<Object> retValue = null;
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
