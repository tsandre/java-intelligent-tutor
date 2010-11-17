package itjava.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		response.getWriter().println("Reached the end of the list.. AWESOME..");
/*
		<div id="divTutorialName">
		<div class="step passive">STEP 4</div>
			Please enter a name for the Tutorial : <input type="text" id="tutorialName" name="tutorialName" />
		</div>*/
	}

}
