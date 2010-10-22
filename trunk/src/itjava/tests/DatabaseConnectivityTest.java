/**
 * 
 */
package itjava.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

/**
 * @author dahotre
 *
 */
public class DatabaseConnectivityTest {

	@Test
	public void ConnectDBTestManually () {
		 Connection conn = null;

         try
         {
             String userName = "javaintelligent";
             String password = "OSUaccess2010";
             String url = "jdbc:mysql://h50mysql117.secureserver.net";
//             String url = "jdbc:mysql://10.0.11.148:3306/javaintelligent/";
             Class.forName ("com.mysql.jdbc.Driver");
             conn = DriverManager.getConnection (url, userName, password);
             assertTrue(conn.isValid(2));
             System.out.println ("Database connection established");
             conn.close();
         }
         catch (Exception e)
         {
        	 e.printStackTrace();
             System.err.println ("Cannot connect to database server");
         }

	}
}
