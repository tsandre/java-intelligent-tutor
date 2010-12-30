package itjava.view;

import itjava.model.Tutorial;
import itjava.model.WordInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		ArrayList<String> approvalList = (ArrayList<String>) session.getAttribute("approvalList");
		ArrayList<List<String>> wordsList = (ArrayList<List<String>>) session.getAttribute("wordsList");
		ArrayList<Integer> difficultyList = (ArrayList<Integer>) session.getAttribute("difficultyList");
		ArrayList<HashMap<String, ArrayList<String>>> hintsMapList = (ArrayList<HashMap<String, ArrayList<String>>>)session.getAttribute("hintsMapList");
		ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>) session.getAttribute("tutorialList");
		if (nextExample.equals("Next Snippet >>")) {
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
			HashMap<String, ArrayList<String>> currHintsMaps = new HashMap<String, ArrayList<String>>();
			for (String selectedWordIndex : selectedWordInfoIndices) {
				ArrayList<String> hintsList = new ArrayList<String>();
				for (int hintNum = 1; hintNum <= 2; hintNum++) {
					String hint = request.getParameter("txtHint_" + selectedWordIndex + "_" + hintNum);
					if (hint != null) {
						hintsList.add(hint);
					}
				}
				//TODO : Find the actual value of the blank
				int currentWordIndex = Integer.parseInt(selectedWordIndex);
				ArrayList<WordInfo> currentWordInfo = tutorialList.get(currentIndex).getWordInfoList();
				String currentWord = currentWordInfo.get(currentWordIndex).wordToBeBlanked;
				hintsList.add(currentWord);
				currHintsMaps.put(selectedWordIndex, hintsList);
			}
			hintsMapList.set(currentIndex, currHintsMaps);
			
			session.setAttribute("hintsMapList", hintsMapList);
			session.setAttribute("wordsList", wordsList);
			session.setAttribute("difficultyList", difficultyList);
		}
		else {
			approvalList.set(currentIndex, approved);
		}
		session.setAttribute("approvalList", approvalList);
		int tutorialListSize = (Integer) session.getAttribute("tutorialListSize");
		String nextPage = "Error.jsp";
		if (nextIndex == tutorialListSize) { //Reached end of list
			int firstSkipped = FirstSkippedExample(approvalList);
			if ( firstSkipped == -1) { //If no snippets are skipped
				nextPage = "tutorialMetaData.jsp";
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

	/**
	 * Returns the integer index of the first skipped snippet 
	 * from the list displayed on tutorialSelection.jsp.
	 * If none are skipped returns -1.
	 * @param approvalList
	 * @return -1 or index
	 */
	private int FirstSkippedExample(ArrayList<String> approvalList) {
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
