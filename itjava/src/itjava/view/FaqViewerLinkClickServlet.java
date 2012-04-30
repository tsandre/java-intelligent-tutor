/**
 * 
 */
package itjava.view;

import itjava.scraper.DBConnection;
import itjava.scraper.ScrapeData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.core.dom.EnumDeclaration;

/**
 * @author Bharat
 * 
 */
@WebServlet("/FaqViewerLinkClickServlet")
public class FaqViewerLinkClickServlet extends HttpServlet {

	private Connection _conn;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In servlet");
		System.out.println(request.getParameter("scrapeId"));
		HttpSession session = request.getSession(true);
		String userId = (String) session.getAttribute("userName");
		System.out.println("userid: "+userId);
		

		try {

			_conn = DBConnection.GetConnection();

			String insertQuery = "insert into FAQViewerLinkLog"
					+ "(user, scrapeId, timeAccessed)"
					+ " values (?,?,datetime('now','localtime'));";
			PreparedStatement addFaqSelection = _conn
					.prepareStatement(insertQuery);
			addFaqSelection.setInt(2,
					Integer.valueOf(request.getParameter("scrapeId")));
			
			//TODO: user Id should go here. 
			addFaqSelection.setString(1, userId);
			addFaqSelection.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				response.getWriter().println(
						"Values inserted in DB successfully.");
				_conn.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
