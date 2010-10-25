/**
 * 
 */
package itjava.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author Aniket
 *
 */
public class TruncateAllTables {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			ArrayList<String> tableNames = new ArrayList<String>();
			tableNames.add("ClassInstanceTerms");
			tableNames.add("MethodInvocationTerms");
			tableNames.add("VariableDeclarationTerms");
			tableNames.add("Documents");
			tableNames.add("PropertyAssignmentTerms");
			tableNames.add("ImportTerms");
			tableNames.add("SuperTypeTerms");
			for (String tableName : tableNames) {
				int rowsDeleted = stat.executeUpdate("delete from " + tableName);
				System.out.println(rowsDeleted + " rows deleted from "
						+ tableName);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
