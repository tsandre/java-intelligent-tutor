/**
 * 
 */
package itjava.db;



import itjava.data.LocalMachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Aniket
 *
 */
public final class DBConnection{
	public static Connection GetConnection() throws ClassNotFoundException, SQLException {
		//Class.forName("org.sqlite.JDBC");
		Class.forName("com.mysql.jdbc.Driver");
		
		return DriverManager.getConnection("jdbc:mysql:" + "//localhost:3306/" + "jtdb","root","mysqlroot");
	}
	
	public static void CloseConnection(Connection conn) throws SQLException {
		conn.close();
	}
	
	
	
}
