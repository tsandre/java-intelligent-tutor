package itjava.view;

import itjava.model.BRDStore;
import itjava.model.Tutorial;
import itjava.model.WordInfo;
import itjava.presenter.TutorialPresenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TutorialDeliveryServlet
 */
@WebServlet("/TutorialDeliveryServlet")
public class TutorialDeliveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TutorialDeliveryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter display = response.getWriter();
		response.setContentType("text/html");
		display.println("Reached the end of the list.. AWESOME..");
		HttpSession session = request.getSession(true);
		ArrayList<Tutorial> approvedTutorialList = new ArrayList<Tutorial>();

		ArrayList<String> approvalList = (ArrayList<String>) session.getAttribute("approvalList");
		ArrayList<List<String>> wordsList = (ArrayList<List<String>>) session.getAttribute("wordsList");
		ArrayList<Integer> difficultyList = (ArrayList<Integer>) session.getAttribute("difficultyList");
		ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>)session.getAttribute("tutorialList");
		
		int tutorialIndex = 0;
		int approvedIndex = 1;
		for(Tutorial tutorial : tutorialList) {
			Tutorial currentTutorial = tutorialList.get(tutorialIndex);
			String folderName = currentTutorial.getReadableName();
			if (approvalList.get(tutorialIndex).equals("Quiz")) {
				List<String> selectedWordInfoIndices = wordsList.get(tutorialIndex);
				Integer difficulty = difficultyList.get(tutorialIndex);
				ArrayList<WordInfo> approvedWordInfoList = new ArrayList<WordInfo>();
				int wordInfoIndex = 0;
				for (WordInfo wordInfo : currentTutorial.getWordInfoList()) {
					if(selectedWordInfoIndices.contains(Integer.toString(wordInfoIndex))) {
						approvedWordInfoList.add(wordInfo);
					}
					wordInfoIndex++;
				}
				currentTutorial.setWordInfoList(approvedWordInfoList);
				currentTutorial.setDifficulty(difficulty);
				currentTutorial.setTutorialName("Tutorial" + approvedIndex);
				approvedTutorialList.add(currentTutorial);
				approvedIndex++;
			}
			else if (approvalList.get(tutorialIndex).equals("Example")) {
				currentTutorial.setWordInfoList(null);
				currentTutorial.setTutorialName("Example" + approvedIndex);
				approvedTutorialList.add(tutorialList.get(tutorialIndex));
				approvedIndex++;
			}
			tutorialIndex++;
		}
		TutorialPresenter tutorialPresenter = new TutorialPresenter();
		ArrayList<Tutorial> finalTutorialList = tutorialPresenter.GetFinalTutorialList(approvedTutorialList);
		for (Tutorial finalTutorial: finalTutorialList) {
			display.println("<a href=\"/itjava/delivery/" + finalTutorial.getReadableName() + "/"
					+ finalTutorial.getTutorialName() + ".jnlp\">" + finalTutorial.getReadableName() + "</a> <br />");
		}
	}

}
