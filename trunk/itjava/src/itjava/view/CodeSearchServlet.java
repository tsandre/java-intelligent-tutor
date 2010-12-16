package itjava.view;

import itjava.model.Convertor;
import itjava.model.ResultEntry;
import itjava.model.Tutorial;
import itjava.presenter.CodeSearchPresenter;
import itjava.presenter.WordInfoPresenter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession(true);
		
		session.setAttribute("query", query);
		
		WordInfoPresenter wordInfoPresenter = new WordInfoPresenter();
		CodeSearchPresenter codeSearchPresenter = new CodeSearchPresenter(query);
		wordInfoPresenter.SetCompilationUnitListAndAccessRepository( query.replaceAll("\\s+", ""), codeSearchPresenter.SearchNext());

		ArrayList<Tutorial> tutorialList = wordInfoPresenter.GenerateWordInfoMap();
		session.setAttribute("tutorialList", tutorialList);
		
		ArrayList<String> approvalList = new ArrayList<String>();
		ArrayList<ArrayList<String>> wordsList = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> difficultyList = new ArrayList<Integer>(); 
		
		for (int initializer = 0; initializer < tutorialList.size(); initializer++) {
			approvalList.add(null);
			wordsList.add(null);
			difficultyList.add(null);
		}
		
		session.setAttribute("approvalList", approvalList);
		session.setAttribute("wordsList", wordsList);
		session.setAttribute("difficultyList", difficultyList);
		session.setAttribute("tutorialListSize", tutorialList.size());
		RequestDispatcher dispatcher = request.getRequestDispatcher("tutorialSelection.jsp?index=0");
		
		dispatcher.forward(request, response);
	}

}
