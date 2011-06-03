package itjava.db;

import itjava.data.LocalMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseAccess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + LocalMachine.home + "samples/itjava.db");
			java.sql.Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from "+ args[0] +";");
			int numOfRows = 0;
			while (rs.next()) {
				System.out.println("Term = " + rs.getString("term"));
				System.out.println("Occurrences = "
						+ rs.getInt("numOccurrences"));
				numOfRows++;
			}
			System.out.println("Num of rows in  table " + args[0] + ": " + numOfRows);
		}
		catch(ArrayIndexOutOfBoundsException a) {
			System.err.println("Enter table name as argument..");
			a.printStackTrace();
		}
		catch (Exception e) {
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
