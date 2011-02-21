package itjava.view;

import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.ResultEntry;
import itjava.model.Tutorial;
import itjava.model.WordInfo;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class EditSnippetServlet
 */
@WebServlet("/EditSnippetServlet")
public class EditSnippetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditSnippetServlet() {
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
		ArrayList<Tutorial> tutorialList = (ArrayList<Tutorial>) session.getAttribute("tutorialList");
		String url = request.getParameter("url");
		if (submitValue.equals("Compile")) {
			compile(request, session, url);
			redirect(request, response, "editSnippet.jsp");	
		}
		else if(submitValue.equals("Confirm Changes")) {
			CompilationUnitFacade facade = compile(request, session, url);
			if (facade != null) {
				ArrayList<WordInfo> wordsList = new ArrayList<WordInfo>();
				Tutorial oldTutorial = tutorialList.get(currentIndex);
				oldTutorial.setFacade(facade);
				oldTutorial.setOriginalWordInfoList(wordsList);
				oldTutorial.setWordInfoList(wordsList);
				tutorialList.remove(currentIndex);
				tutorialList.add(currentIndex, oldTutorial);
				session.setAttribute("tutorialList", tutorialList);
				redirect(request, response, "tutorialSelection.jsp?index=" + currentIndex);
			}
			else {
				redirect(request, response, "editSnippet.jsp?error=1");
			}
		}
		else if (submitValue.equals("Cancel Changes")) {
			redirect(request, response, "tutorialSelection.jsp?index=" + currentIndex);
		}
	}

	/**
	 * @param request
	 * @param session
	 * @param url
	 */
	private CompilationUnitFacade compile(HttpServletRequest request, HttpSession session,
			String url) {
		String code = request.getParameter("taEdit").replace("\n", "").replace("\r", "");
		ResultEntry entry = new ResultEntry(code, url, code.length());
		ArrayList<ResultEntry> resultEntryList = new ArrayList<ResultEntry>();
		resultEntryList.add(entry);
		CompilationUnitStore cuStore = new CompilationUnitStore();
		ArrayList<CompilationUnitFacade> facadeList = cuStore.createCompilationUnitFacadeList(resultEntryList);
		if (facadeList != null && facadeList.size() == 1 && facadeList.get(0) != null) {
			session.setAttribute("facade", facadeList.get(0));
			return facadeList.get(0);
		}
		return null;
	}
	
	private void redirect(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}
