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
@WebServlet("/FAQSelectionForm")
public class FAQSelectionForm extends HttpServlet {

	private Connection _conn;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Enumeration<String> pNames = request.getParameterNames();
		//
		// while(pNames.hasMoreElements()) {
		// String pName = pNames.nextElement();
		// System.out.println(pName+": "+request.getParameter(pName));
		// }

		HttpSession session = request.getSession(false);
		

		

		try {
			LinkedHashSet<ScrapeData> scrapeFinalObj = (LinkedHashSet<ScrapeData>) session
					.getAttribute("scrapeFinalObj");
			_conn = DBConnection.GetConnection();
			int startScrapeId = 0, endScrapeId = 0;
			LinkedHashSet<Integer> scrapeIDSet = new LinkedHashSet<Integer>();
			for (ScrapeData item : scrapeFinalObj) {
				scrapeIDSet.add(item.getScrapeId());
			}

			for (Integer scrapeId : scrapeIDSet) {
				int faqTypeValue = Integer.parseInt(request
						.getParameter("faqTypeSelector" + scrapeId));
				int faqTARatingValue = Integer.parseInt(request
						.getParameter("FAQTARating" + scrapeId));
				String insertQuery = "insert into ScrapeFAQSelection"
						+ "(scrapeId, faqType, faqRatingTA)"
						+ " values (?,?,?)";
				PreparedStatement addFaqSelection = _conn
						.prepareStatement(insertQuery);
				addFaqSelection.setInt(1, scrapeId);
				addFaqSelection.setInt(2, faqTypeValue);
				addFaqSelection.setInt(3, faqTARatingValue);
				addFaqSelection.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				response.getWriter().println("Values inserted in DB successfully.");
				_conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
