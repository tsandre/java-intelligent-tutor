package itjava.view;

import itjava.model.Convertor;
import itjava.model.DeliverableLauncher;
import itjava.model.ResultEntry;
import itjava.model.Tutorial;
import itjava.model.TutorialInfo;
import itjava.model.TutorialInfoStore;
import itjava.presenter.CodeSearchPresenter;
import itjava.presenter.WordInfoPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TutorSearchServlet
 */
@WebServlet(description = "Retrieves the search param from user and passes to TutorSearchPresenter", urlPatterns = { "/TutorSearchServlet" })
public class TutorSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<ResultEntry> sourceCodes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TutorSearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String query = (String) request.getParameter("query");
		query = query.replaceAll("\\W", " ").replaceAll("\\s+", " ").trim();
		HttpSession session = request.getSession(true);
		
		session.setAttribute("query", query);
		
		HashMap<String, String> filter = new HashMap<String, String>();
		filter.put("tutorialDescription", " LIKE \"%" + (String) session.getAttribute("query") + "%\"");
		filter.put("tutorialName", " LIKE \"%" + (String) session.getAttribute("query") + "%\"");
		
		ArrayList<TutorialInfo> tutorialInfoList = TutorialInfoStore.SearchTutorialsOr(filter);
		session.setAttribute("tutorialInfoList", tutorialInfoList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("tutorsearch.jsp?index=0");
		
		
		dispatcher.forward(request, response);
	}

}
