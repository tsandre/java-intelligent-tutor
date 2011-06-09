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
 * Servlet implementation class TutorialSelectionServlet
 */
@WebServlet("/TutorialSelectionServlet")
public class TutorialSelectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TutorialSelectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String approved = request.getParameter("radioApproval");
		HttpSession session = request.getSession(true);
		ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>) session.getAttribute("tutorialList");
                //ArrayList<String> tutorialDescriptionList = (ArrayList<String>) session.getAttribute("tutorialDescriptionList");
		int currentIndex = (Integer) session.getAttribute("currentIndex");
		
		ArrayList<Tutorial> approvedTutorialList;
		int approvedListIndex;
		
		if (approved.equals("Yes")) {
			approvedTutorialList = (ArrayList<Tutorial>) session.getAttribute("approvedTutorialList");
			approvedListIndex = approvedTutorialList.size();
			List<String> selectedWordInfoIndices = Arrays.asList(request.getParameterValues("cbxWordInfo"));
			if (selectedWordInfoIndices == null) {
				System.err.println("Check boxes not selected");
			}
			else{
				Tutorial currentTutorial = tutorialList.get(currentIndex);
				ArrayList<WordInfo> approvedWordInfoList = new ArrayList<WordInfo>();
				int wordInfoIndex = 0;
				for (WordInfo wordInfo : currentTutorial.getWordInfoList()) {
					if(selectedWordInfoIndices.contains(Integer.toString(wordInfoIndex))) {
						approvedWordInfoList.add(wordInfo);
					}
					wordInfoIndex++;
				}
				currentTutorial.setWordInfoList(approvedWordInfoList);
				currentTutorial.difficultyLevel = Integer.parseInt(request.getParameter("difficultyLevel"));
				
				approvedTutorialList.add(currentTutorial);
				System.out.println("Approved tutorial# : " + approvedListIndex);
				
				session.removeAttribute("approvedTutorialList");
				session.setAttribute("approvedTutorialList", approvedTutorialList);
			}
		}
		else {
			System.err.println("Tutorial not approved:");
			System.err.println(tutorialList.get(currentIndex));
			System.err.println("-----------------------------");
		}
		
		if (++currentIndex < tutorialList.size()) {  //Not reached the last tutorial in the list
			session.setAttribute("currentIndex", currentIndex);
			RequestDispatcher dispatcher = request.getRequestDispatcher("tutorialSelection.jsp");
			dispatcher.forward(request, response);
		}
		else { //Last tutorial reached
			RequestDispatcher dispatcher = request.getRequestDispatcher("TutorialDeliveryServlet");
			dispatcher.forward(request, response);
		}
		
	}

}
