package itjava.view;

import itjava.model.BRDStore;
import itjava.model.Tutorial;
import itjava.model.TutorialInfo;
import itjava.model.TutorialInfoStore;
import itjava.model.TutorialStore;
import itjava.model.WordInfo;
import itjava.presenter.TutorialPresenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
	private ArrayList<String> approvalList;
	private ArrayList<List<String>> wordsList;
	private ArrayList<HashMap<String, ArrayList<String>>> hintsMapList;
	private ArrayList<Integer> difficultyList;
	private ArrayList<Tutorial> tutorialList;
	private ArrayList<Tutorial> approvedTutorialList;
	private HttpSession session;
	private PrintWriter display;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TutorialDeliveryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		display = response.getWriter();
		response.setContentType("text/html");
		session = request.getSession(false);

		TutorialInfo tutorialInfo = new TutorialInfo();
		approvalList = (ArrayList<String>) session.getAttribute("approvalList");
		wordsList = (ArrayList<List<String>>) session.getAttribute("wordsList");
		difficultyList = (ArrayList<Integer>) session.getAttribute("difficultyList");
		tutorialList = (ArrayList<Tutorial>)session.getAttribute("tutorialList");
		hintsMapList = (ArrayList<HashMap<String, ArrayList<String>>>) session.getAttribute("hintsMapList");
		
		approvedTutorialList = AbsorbUserApprovalsInTutorialList(tutorialInfo);
		ProcessMetaData(request, tutorialInfo);
		TutorialPresenter tutorialPresenter = new TutorialPresenter();
		ArrayList<Tutorial> finalTutorialList = tutorialPresenter.GetFinalTutorialList(approvedTutorialList);
		SaveChoicesToDisk(tutorialInfo, finalTutorialList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp?myaction=5");;
		if(session.getAttribute("userLevel") == "unknown"){
			dispatcher = request.getRequestDispatcher("index.jsp?myaction=5");
			dispatcher.forward(request, response);
		}else if(session.getAttribute("userLevel") == "student"){
			dispatcher = request.getRequestDispatcher("savedTutors.jsp");
			dispatcher.forward(request, response);
		}else{
			dispatcher = request.getRequestDispatcher("teachers.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * Save the tutorial name and tutorial description given by the user.
	 * @param request
	 */
	private void ProcessMetaData(HttpServletRequest request, TutorialInfo tutorialInfo) {
		// TODO: Save userinfo in session and delete this line later
		//session.setAttribute("username", "Aniket");
		//
		tutorialInfo.setCreatedBy((String) session.getAttribute("userName"));
		if(session.getAttribute("userLevel")!=null){
			tutorialInfo.setUserLevel((String) session.getAttribute("userLevel"));
		}else{
			tutorialInfo.setUserLevel("unknown");
		}
		tutorialInfo.setTutorialName(request.getParameter("txtTutorialName"));
		tutorialInfo.setTutorialDescription(request.getParameter("txtTutorialDescription"));
	}

	/**
	 * Calls TutorialInfoStore which performs the task to save the tutorial information to disk, in this case
	 * to a table called "TutorialInfo" in the database.
	 * @param finalTutorialList 
	 */
	private void SaveChoicesToDisk(TutorialInfo tutorialInfo, ArrayList<Tutorial> finalTutorialList) {
		System.out.println("Username: " + tutorialInfo.getCreatedBy());
		int tutorialInfoId = TutorialInfoStore.InsertInfo(tutorialInfo);
		tutorialInfo.setTutorialId(tutorialInfoId);
		int rowsInsertedInDeliverableInfo = TutorialStore.InsertDeliverableInfo(finalTutorialList, tutorialInfoId);
	}

	/**
	 * Assimilates the choices made by the teacher as to which snippets should be converted to 
	 * quiz, examples and which ones are to be discarded. 
	 * @return List of approved tutorials
	 */
	private ArrayList<Tutorial> AbsorbUserApprovalsInTutorialList(TutorialInfo tutorialInfo) {
		
		ArrayList<Tutorial> approvedTutorialList = new ArrayList<Tutorial>();
		int tutorialIndex = 0;
		int approvedIndex = 1;
		Iterator<Tutorial> itTutorialList = tutorialList.iterator();
		while(itTutorialList.hasNext()) { //Iterate through all the tutorials in the session
			Tutorial currentTutorial = itTutorialList.next();
			String folderName = currentTutorial.getReadableName();
			if (approvalList.get(tutorialIndex).equals("Quiz")) {
				tutorialInfo.setNumQuizes(tutorialInfo.getNumQuizes() + 1);
				List<String> selectedWordInfoIndices = wordsList.get(tutorialIndex);
				HashMap<String, ArrayList<String>> currTutorialHintsMap = hintsMapList.get(tutorialIndex);
				Integer difficulty = difficultyList.get(tutorialIndex);
				ArrayList<WordInfo> approvedWordInfoList = new ArrayList<WordInfo>();
				int wordInfoIndex = 0;
				for (WordInfo wordInfo : currentTutorial.getWordInfoList()) {
					if(selectedWordInfoIndices.contains(Integer.toString(wordInfoIndex))) {
						wordInfo.hintsAvailable = currTutorialHintsMap.get(Integer.toString(wordInfoIndex));
						approvedWordInfoList.add(wordInfo);
					}
					wordInfoIndex++;
				}
				currentTutorial.setWordInfoList(approvedWordInfoList);
				currentTutorial.setDifficulty(difficulty);
				currentTutorial.setTutorialName("Quiz" + approvedIndex);
				currentTutorial.setType("Quiz");
				approvedTutorialList.add(currentTutorial);
				approvedIndex++;
			}
			else if (approvalList.get(tutorialIndex).equals("Example")) {
				tutorialInfo.setNumExamples(tutorialInfo.getNumExamples() + 1);
				currentTutorial.setWordInfoList(null);
				currentTutorial.setTutorialName("Example" + approvedIndex);
				currentTutorial.setType("Example");
				approvedTutorialList.add(tutorialList.get(tutorialIndex));
				approvedIndex++;
			}
			tutorialIndex++;
		}
		tutorialInfo.setFolderName(approvedTutorialList.get(0).getReadableName());
		return approvedTutorialList;
	}

}
