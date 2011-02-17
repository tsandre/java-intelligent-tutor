package itjava.view;

import itjava.model.ResultEntry;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 */
@WebServlet(description = "Sets the user level", urlPatterns = { "/userLevelSelect" })
public class userLevelSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<ResultEntry> sourceCodes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userLevelSelect() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String currurl = request.getParameter("currurl");
		session.setAttribute("userLevel", request.getParameter("newuserlevel"));
		String redirectURL2 = currurl;
		response.sendRedirect(redirectURL2);
	}

}
