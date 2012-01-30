package itjava.view;

import itjava.model.Convertor;
import itjava.model.DeliverableLauncher;
import itjava.model.ResultEntry;
import itjava.model.Tutorial;
import itjava.model.TutorialInfo;
import itjava.model.TutorialInfoStore;
import itjava.presenter.CodeSearchPresenter;
import itjava.presenter.WordInfoPresenter;
import itjava.scraper.InfoScrape;
import itjava.scraper.ScrapeData;
import itjava.scraper.StackoverflowScrape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FAQSearchServlet
 */
@WebServlet(description = "Retrieves the search param from user and passes to TutorSearchPresenter", urlPatterns = { "/TutorSearchServlet" })
public class FAQSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<ResultEntry> sourceCodes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FAQSearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		String query = (String) request.getParameter("query");
//		query = query.replaceAll("\\W", " ").replaceAll("\\s+", " ").trim();
//		HttpSession session = request.getSession(true);
//		session.setAttribute("query", query);
//		String faqSearchQuery = (String) session.getAttribute("query");
//		LinkedHashSet <ScrapeData> scrapeDataObj = new LinkedHashSet <ScrapeData>();
//		scrapeDataObj = InfoScrape.ScrapeSites(query, 0);
//		request.setAttribute("scrapedFAQ", scrapeDataObj);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("FAQSelection.jsp");
//        dispatcher.forward(request, response);
		
	}

}
