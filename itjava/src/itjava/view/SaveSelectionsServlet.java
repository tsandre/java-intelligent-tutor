package itjava.view;

import itjava.model.Tutorial;
import itjava.model.WordInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SaveSelectionsServlet
 */
@WebServlet("/SaveSelectionsServlet")
public class SaveSelectionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> approvalList;
	private ArrayList<List<String>> wordsList;
	private ArrayList<Integer> difficultyList;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveSelectionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int currentIndex = (Integer) session.getAttribute("currentIndex");
		
		String nextExample = request.getParameter("btnSubmit");
		int nextIndex;
		approvalList = (ArrayList<String>) session.getAttribute("approvalList");
		wordsList = (ArrayList<List<String>>) session.getAttribute("wordsList");
		difficultyList = (ArrayList<Integer>) session.getAttribute("difficultyList");
		
		if (nextExample.equals("Next Example >>")) {
			nextIndex = currentIndex + 1;
		}
		else {
			nextIndex = currentIndex - 1;
		}
		
		String approved = request.getParameter("radioApproval");
		if (approved.equals("Quiz")) {
			List<String> selectedWordInfoIndices = Arrays.asList(request.getParameterValues("cbxWordInfo"));
			int difficultyLevel = Integer.parseInt(request.getParameter("difficultyLevel"));
			
			approvalList.set(currentIndex, "Quiz");
			wordsList.set(currentIndex, selectedWordInfoIndices);
			difficultyList.set(currentIndex, difficultyLevel);
			
			session.setAttribute("wordsList", wordsList);
			session.setAttribute("difficultyList", difficultyList);
		}
		else {
			approvalList.set(currentIndex, approved);
		}
		session.setAttribute("approvalList", approvalList);
		int tutorialListSize = (Integer) session.getAttribute("tutorialListSize");
		String nextPage = "Error.jsp";
		if (nextIndex == tutorialListSize) {
			int firstSkipped = FirstSkippedExample();
			if ( firstSkipped == -1) {
				nextPage = "TutorialDeliveryServlet";
			}
			else {
				nextPage = "tutorialSelection.jsp?index=" + firstSkipped;
			}
		}
		else {
			nextPage = "tutorialSelection.jsp?index=" + nextIndex;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

	private int FirstSkippedExample() {
		int index = 0;
		for (String approvalChoice : approvalList) {
			if (approvalChoice == null) {
				return index;
			}
			index++;
		}
		return -1;
	}

}
