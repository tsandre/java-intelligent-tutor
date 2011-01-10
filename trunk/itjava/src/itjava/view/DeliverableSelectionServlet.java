package itjava.view;

import itjava.data.LogData;
import itjava.model.DeliverableInfoStore;
import itjava.model.DeliverableLauncher;
import itjava.model.LogDataStore;
import itjava.model.WordInfoStore;
import itjava.util.Concordance;
import itjava.util.KeyValue;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Servlet implementation class DeliverableSelectionServlet
 */
@WebServlet("/DeliverableSelectionServlet")
public class DeliverableSelectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliverableSelectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		KeyValue<Integer, String> deliveryKeyValue = (KeyValue<Integer, String>) session.getAttribute("deliveryKeyValue");
		DeliverableLauncher deliverableLauncher = (DeliverableLauncher) session.getAttribute("deliverableLauncher");
		int studentId = (Integer) session.getAttribute("studentId");
		int tutorialInfoId = (Integer) session.getAttribute("tutorialInfoId");
		HashMap<String, Integer> whereClause = new HashMap<String, Integer>();
		whereClause.put("deliverableId", deliveryKeyValue.getKey());
		int numOfBlanks = DeliverableInfoStore.SelectNumOfBlanks(whereClause).get(0);
		LogData logData = null;
		try {
			Concordance<String> hintsAvailable = new Concordance<String>();
			hintsAvailable = WordInfoStore.SelectWordInfo(whereClause);
			logData = LogDataStore.CreateLogData(numOfBlanks, hintsAvailable);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem creating log data");
		}
		int scoreId = deliverableLauncher.SetScore(deliveryKeyValue.getKey(), logData.getScore());
		deliveryKeyValue = deliverableLauncher.GetNextDeliverableName(deliveryKeyValue.getKey(), scoreId);
		session.setAttribute("deliveryKeyValue", deliveryKeyValue);
		String nextPage;
		if(deliveryKeyValue == null)
		{
		nextPage = "studentFinalPage.jsp";
		}
		else
		{
		nextPage = "studentMainTest.jsp?id=" + tutorialInfoId;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
