package itjava.view;

import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.Tutorial;
import itjava.model.TutorialStore;
import itjava.model.WordInfo;
import itjava.model.WordInfoStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;

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
		
		String submitValue = request.getParameter("btnSubmit");
		int nextIndex = -999;
		ArrayList<String> approvalList = (ArrayList<String>) session.getAttribute("approvalList");
		ArrayList<List<String>> wordsList = (ArrayList<List<String>>) session.getAttribute("wordsList");
		ArrayList<Integer> difficultyList = (ArrayList<Integer>) session.getAttribute("difficultyList");
		ArrayList<HashMap<String, ArrayList<String>>> hintsMapList = (ArrayList<HashMap<String, ArrayList<String>>>)session.getAttribute("hintsMapList");
		ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>) session.getAttribute("tutorialList");
                //ArrayList<String> tutorialDescriptionList = (ArrayList<String>) session.getAttribute("tutorialDescriptionList");
		Tutorial currentTutorial = tutorialList.get(currentIndex);
                
		if (submitValue.equals("Next Snippet >>")) {
                    String lastdesc = (String) request.getParameter("exDescription");
                    currentTutorial.setTutorialDescription((String) request.getParameter("exDescription"));
                    nextIndex = currentIndex + 1;
		}
		else if(submitValue.equals("<< Previous Snippet")) {
                        currentTutorial.setTutorialDescription((String) request.getParameter("exDescription"));
			nextIndex = currentIndex - 1;
                    
		}
		else if (submitValue.equals("Edit the snippet")) {
			session.setAttribute("facade", tutorialList.get(currentIndex).getFacade());
			RequestDispatcher dispatcher = request.getRequestDispatcher("editSnippet.jsp");
			dispatcher.forward(request, response);
		}
		else if (submitValue.equals("Verify & Save")){
                        HashSet<Boolean> wordFoundSet = new HashSet<Boolean>();
			String[] newWords = request.getParameter("txtNewWord").split(",");
			
			CompilationUnitFacade facade = currentTutorial.getFacade();
			Set<Integer> lineNumbersUsed = currentTutorial.getLineNumbersUsed();
			ArrayList<WordInfo> wordInfoList = currentTutorial.getWordInfoList();
			LinkedHashSet<String> selectedWordInfoIndices = new LinkedHashSet<String>();
			int newPosition = -999;
			if (request.getParameterValues("cbxWordInfo") != null) {
				selectedWordInfoIndices.addAll((Arrays.asList(request.getParameterValues("cbxWordInfo"))));
			}
			
			for(String newWord: newWords) {
				if ( currentTutorial.contains(newWord) ) continue; //TODO :Check current wordsList
				else {
					WordInfo newWordInfo = null;
					try {
						ASTNode astNode = CompilationUnitStore.findWordType(currentTutorial.getFacade(), newWord.trim());
						if (astNode != null) {
							switch (astNode.getNodeType()) {
							case ASTNode.SIMPLE_NAME :
								System.out.println("Method inv found");
								newWordInfo = WordInfoStore.createWordInfo(facade.getLinesOfCode(), (SimpleName) astNode, lineNumbersUsed, false);
								break;
								
							case ASTNode.IMPORT_DECLARATION : 
								System.out.println("import decl found");
								newWordInfo = WordInfoStore.createWordInfo(facade.getLinesOfCode(), ((ImportDeclaration) astNode).getName(), lineNumbersUsed, false);
								break;
								
							case ASTNode.SIMPLE_TYPE :
								System.out.println("classinstance found");
								newWordInfo = WordInfoStore.createWordInfo(facade.getLinesOfCode(), (SimpleType) astNode, lineNumbersUsed, false);
								break;
								
							default:
								System.out.println("Incorrect ASTNode type returned");
								break;
									
							}
							newPosition = WordInfoStore.addWordInfoToList(wordInfoList, newWordInfo, selectedWordInfoIndices);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
					approvalList.set(currentIndex, "Quiz");
					
					if (request.getParameter("difficultyLevel") != null) {
						int difficultyLevel = Integer.parseInt(request.getParameter("difficultyLevel"));
						difficultyList.set(currentIndex, difficultyLevel);
					}
					
					if (newPosition != -999) {
						wordFoundSet.add(true);
					}
					else {
						wordFoundSet.add(false);
					}
					
				}
				
			}
			ArrayList<String> listOfSelectedIndices = new ArrayList<String>(selectedWordInfoIndices);
			wordsList.set(currentIndex, listOfSelectedIndices);
			
			ProcessHints(request, currentIndex, hintsMapList, tutorialList,
					listOfSelectedIndices);
			

			currentTutorial.setWordInfoList(wordInfoList);
                        tutorialList.remove(currentIndex);
			tutorialList.add(currentIndex, currentTutorial);
                        //tutorialDescriptionList.add(currentIndex, request.getParameter("exDescription"));
			session.setAttribute("tutorialList", tutorialList);
			session.setAttribute("approvalList", approvalList);
			session.setAttribute("difficultyList", difficultyList);
			session.setAttribute("wordsList", wordsList);
			session.setAttribute("hintsMapList", hintsMapList);
                       // session.setAttribute("tutorialDescriptionList", tutorialDescriptionList);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("tutorialSelection.jsp?index=" + currentIndex + "&wordFound=" + wordFoundSet.contains(true));
			dispatcher.forward(request, response);
		}
		
		if (nextIndex != -999) { // If Next or Previous is pressed
		String approved = request.getParameter("radioApproval");
		if (approved.equals("Quiz")) {
			ArrayList<String> selectedWordInfoIndices = new ArrayList<String>(Arrays.asList(request.getParameterValues("cbxWordInfo")));
			int difficultyLevel = Integer.parseInt(request.getParameter("difficultyLevel"));
			
			approvalList.set(currentIndex, "Quiz");
			wordsList.set(currentIndex, selectedWordInfoIndices);
			difficultyList.set(currentIndex, difficultyLevel);
			
			ProcessHints(request, currentIndex, hintsMapList, tutorialList,
					selectedWordInfoIndices);
			
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
				//Hook for FAQ_Screen
			}
			else {
				nextPage = "tutorialSelection.jsp?index=" + firstSkipped;
			}
		}
		else {
			nextPage = "tutorialSelection.jsp?nothing=123&index=" + nextIndex;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		}
	}

	/**
	 * @param request
	 * @param currentIndex
	 * @param hintsMapList
	 * @param tutorialList
	 * @param selectedWordInfoIndices
	 * @throws NumberFormatException
	 */
	private void ProcessHints(HttpServletRequest request, int currentIndex,
			ArrayList<HashMap<String, ArrayList<String>>> hintsMapList,
			ArrayList<Tutorial> tutorialList,
			ArrayList<String> selectedWordInfoIndices)
			throws NumberFormatException {
		
		HashMap<String, ArrayList<String>> currHintsMaps = new HashMap<String, ArrayList<String>>();
		for (String selectedWordIndex : selectedWordInfoIndices) {
			ArrayList<String> hintsList = new ArrayList<String>();
			
			int currentWordIndex = Integer.parseInt(selectedWordIndex);
			ArrayList<WordInfo> currentWordInfo = tutorialList.get(currentIndex).getWordInfoList();
			String currentWord = currentWordInfo.get(currentWordIndex).wordToBeBlanked;
			
			for (int hintNum = 1; hintNum <= 2; hintNum++) {
				String hint = request.getParameter("txtHint_" + selectedWordIndex + "_" + hintNum);
				if (hint != null ) {
					if (!hint.trim().equals("") && !hint.equalsIgnoreCase(currentWord)){
						hintsList.add(hint);
					}
				}
			}
			
			hintsList.add(currentWord);
			currHintsMaps.put(selectedWordIndex, hintsList);
		}
		hintsMapList.set(currentIndex, currHintsMaps);
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
