/**
 * 
 */
package itjava.view;

import itjava.scraper.DBConnection;
import itjava.scraper.FAQRaterDataRetriever;
import itjava.scraper.ScrapeData;
import itjava.scraper.UserRatingData;

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
@WebServlet("/FaqRatingClickServlet")
public class FaqRatingClickServlet extends HttpServlet {

	private Connection _conn;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In servlet");
		System.out.println(request.getParameter("scrapeId"));
		Integer scrapeId = Integer.valueOf(request.getParameter("scrapeId"));
		HttpSession session = request.getSession(true);
		String userId = (String) request.getParameter("username");
		System.out.println("userid: " + userId);
		System.out.println("Yes: " + request.getParameter("userRatingYes"));
		System.out.println("No: " + request.getParameter("userRatingNo"));
		Integer userRatingYes = Integer.parseInt((String) request
				.getParameter("userRatingYes"));
		Integer userRatingNo = Integer.parseInt((String) request
				.getParameter("userRatingNo"));

		try {

			_conn = DBConnection.GetConnection();

			FAQRaterDataRetriever faqRater = new FAQRaterDataRetriever();
			if (faqRater.isScrapeRatedByUser(userId, scrapeId)) {
				String insertQuery = "delete from FAQUserRatingLog"
						+ " where user=? and scrapeId=?;";

				PreparedStatement addFaqSelection = _conn
						.prepareStatement(insertQuery);
				addFaqSelection.setString(1, userId);

				// TODO: user Id should go here.
				addFaqSelection.setInt(2, scrapeId);
				addFaqSelection.executeUpdate();
				System.out.println("deleted old row");

			}
			String insertQuery = "insert into FAQUserRatingLog"
					+ "(user, scrapeId, userRatingYes, userRatingNo, timeAccessed)"
					+ " values (?,?,?,?,datetime('now','localtime'));";
			PreparedStatement addFaqSelection = _conn
					.prepareStatement(insertQuery);
			addFaqSelection.setString(1, userId);

			// TODO: user Id should go here.
			addFaqSelection.setInt(2, scrapeId);
			addFaqSelection.setInt(3, userRatingYes);
			addFaqSelection.setInt(4, userRatingNo);
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
