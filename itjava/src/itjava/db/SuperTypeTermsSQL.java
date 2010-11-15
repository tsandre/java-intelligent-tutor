/**
 * 
 */
package itjava.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author student
 * 
 */
public class SuperTypeTermsSQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager
					.getConnection("jdbc:sqlite:samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists SuperTypeTerms;");
			stat.executeUpdate("create table SuperTypeTerms (term varchar(30)  PRIMARY KEY DESC, numOccurrences int);");
			PreparedStatement prep = conn
					.prepareStatement("insert into SuperTypeTerms values (?, ?);");
			prep.setString(1, "test");
			prep.setInt(2, 1);
			prep.addBatch();

			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			ResultSet rs = stat.executeQuery("select * from SuperTypeTerms;");
			while (rs.next()) {
				System.out.println("Term = " + rs.getString("term"));
				System.out.println("Occurrences = "
						+ rs.getInt("numOccurrences"));
			}
			rs.close();
			prep = conn.prepareStatement("delete from SuperTypeTerms");
			prep.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
