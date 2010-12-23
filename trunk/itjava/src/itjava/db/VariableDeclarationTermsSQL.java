/**
 * 
 */
package itjava.db;

import itjava.data.LocalMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author student
 * 
 */
public class VariableDeclarationTermsSQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists VariableDeclarationTerms;");
			stat.executeUpdate("create table VariableDeclarationTerms (term varchar(30)  PRIMARY KEY DESC, numOccurrences int);");
			PreparedStatement prep = conn
					.prepareStatement("insert into VariableDeclarationTerms values (?, ?);");
			prep.setString(1, "test");
			prep.setInt(2, 1);
			prep.addBatch();

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from VariableDeclarationTerms;");
			while (rs.next()) {
				System.out.println("Term = " + rs.getString("term"));
				System.out.println("Occurrences = "
						+ rs.getInt("numOccurrences"));
			}
			rs.close();
			prep = conn.prepareStatement("delete from VariableDeclarationTerms");
			prep.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
