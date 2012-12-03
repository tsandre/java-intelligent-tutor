package itjava.view;

import itjava.model.Convertor;
import itjava.model.ResultEntry;
import itjava.model.Tutorial;
import itjava.presenter.CodeSearchPresenter;
import itjava.presenter.WordInfoPresenter;
import itjava.scraper.InfoScrape;
import itjava.scraper.ScrapeData;


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
 * @author Vasanth
 * 
 */

/**
 * Servlet implementation class CodeSearchServlet
 */

@WebServlet(description = "Retrieves the search param from user and passes to CodeSearchPresenter", urlPatterns = { "/CodeSearchServlet" })
public class CodeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<ResultEntry> sourceCodes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeSearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String query = (String) request.getParameter("query");
		query = query.replaceAll("\\W", " ").replaceAll("\\s+", " ").trim(); 
		// \W 	A non-word character: [^\w] and \s 	A whitespace character: [ \t\n\x0B\f\r]
		HttpSession session = request.getSession(true);
		
		session.setAttribute("query", query);
		
		WordInfoPresenter wordInfoPresenter = new WordInfoPresenter();
		CodeSearchPresenter codeSearchPresenter = new CodeSearchPresenter(query);
		wordInfoPresenter.SetCompilationUnitListAndAccessRepository( query.replaceAll("\\s+", ""), codeSearchPresenter.SearchNext());

		ArrayList<Tutorial> tutorialList = wordInfoPresenter.GenerateWordInfoMap();
                //ArrayList<String> tutorialDescriptionList = new ArrayList<String>();
		
		
		ArrayList<String> approvalList = new ArrayList<String>();
		ArrayList<ArrayList<String>> wordsList = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> difficultyList = new ArrayList<Integer>(); 
		ArrayList<HashMap<String, ArrayList<String>>> hintsMapList = new ArrayList<HashMap<String,ArrayList<String>>>();
		/* Vasanth - Api Info Code Starts here - Commented temporarily */
		
	//	LinkedHashSet<ScrapeData> scrapeFinalObj = InfoScrape.ScrapeSites(query);
		
//		LinkedHashSet <ScrapeData> scrapeDataObj = new LinkedHashSet <ScrapeData>();
//		scrapeDataObj = InfoScrape.ScrapeSites(query);
			
		/* New Api Info Code Ends here */
		for (int initializer = 0; initializer < tutorialList.size(); initializer++) {
			approvalList.add(null);
			wordsList.add(null);
			difficultyList.add(null);
			hintsMapList.add(null);
		}
// Vasanth - FAQ code 		
//		session.setAttribute("scrapeFinalObj", scrapeFinalObj);
// Vasanth - FAQ code ends		
		session.setAttribute("approvalList", approvalList);
		session.setAttribute("wordsList", wordsList);
		session.setAttribute("difficultyList", difficultyList);
		session.setAttribute("hintsMapList", hintsMapList);
		session.setAttribute("tutorialListSize", tutorialList.size());
		session.setAttribute("tutorialList", tutorialList);
		/* Vasanth - Sets the API info object to session - Commented temporarily */
		// session.setAttribute("scrapedFAQ", scrapeDataObj);
		/* End of Api info code */
		
		
                //session.setAttribute("tutorialDescriptionList", tutorialDescriptionList);
                //session.setAttribute("tutorialDescriptionListSize", 0);
		RequestDispatcher dispatcher = request.getRequestDispatcher("tutorialSelection.jsp?index=0");
		
		
		dispatcher.forward(request, response);
	}

}
