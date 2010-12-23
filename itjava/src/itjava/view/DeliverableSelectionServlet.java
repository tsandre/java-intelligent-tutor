package itjava.view;

import itjava.data.LogData;
import itjava.model.DeliverableInfoStore;
import itjava.model.DeliverableLauncher;
import itjava.model.LogDataStore;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;

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
		Date testDate;
		Float score;
		int deliverableId = (Integer) session.getAttribute("deliverableId");
		int studentId = (Integer) session.getAttribute("studentId");
		HashMap<String, Integer> whereClause = new HashMap<String, Integer>();
		whereClause.put("deliverableId", deliverableId);
		int numOfBlanks = (Integer) DeliverableInfoStore.Select("numOfBlanks", whereClause);
		LogData logData = null;
		try {
			logData = LogDataStore.CreateLogData(numOfBlanks, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem creating log data");
		}
		
		DeliverableLauncher.SetScore(studentId, deliverableId, logData.getScore());
		String deliverableName = null;
		String tutorialInfoId = null;
		String nextPage = "studentMainTest.jsp?id=" + tutorialInfoId  + "&deliName=" + deliverableName;
	}

}
