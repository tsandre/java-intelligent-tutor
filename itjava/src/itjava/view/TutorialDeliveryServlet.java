package itjava.view;

import itjava.model.Tutorial;
import itjava.presenter.TutorialPresenter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
		display.println("Reached the end of the list.. AWESOME..");
		HttpSession session = request.getSession(true);
		ArrayList<Tutorial> approvedTutorialList = (ArrayList<Tutorial>) session.getAttribute("approvedTutorialList");
		
		TutorialPresenter tutorialPresenter = new TutorialPresenter();
		ArrayList<Tutorial> finalTutorialList = tutorialPresenter.GetFinalTutorialList(approvedTutorialList);
		for (Tutorial finalTutorial: finalTutorialList) {
			display.println("<a href=\"/delivery/" + finalTutorial.getTutorialName() + ".jnlp\">" + finalTutorial.get_readableName() + "</a> <br />");
		}
	}

}
