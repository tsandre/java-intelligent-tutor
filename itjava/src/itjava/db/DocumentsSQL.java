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
public class DocumentsSQL {

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
			stat.executeUpdate("drop table if exists Documents;");
			stat.executeUpdate("create table Documents (fileName INTEGER PRIMARY KEY AUTOINCREMENT, url varchar(100));");
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from Documents;");
			while (rs.next()) {
				System.out.println("FileName = " + rs.getString("fileName"));
				System.out.println("URL = " + rs.getString("url"));
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
