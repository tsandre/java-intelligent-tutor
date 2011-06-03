package itjava.view;

import itjava.data.LogData;
import itjava.model.DeliverableInfoStore;
import itjava.model.DeliverableLauncher;
import itjava.model.LogDataStore;
import itjava.model.WordInfoStore;
import itjava.util.Concordance;
import itjava.util.KeyValue;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeliverableSelectionServlet
 */
@WebServlet("/DeliverableSelection2Servlet")
public class DeliverableSelection2Servlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliverableSelection2Servlet() {
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
                int tutorialInfoId = (Integer) session.getAttribute("tutorialInfoId");
                HashMap<String, Integer> whereClause = new HashMap<String, Integer>();
                whereClause.put("deliverableId", deliveryKeyValue.getKey());
                int numOfBlanks = DeliverableInfoStore.SelectNumOfBlanks(whereClause).get(0);
                LogData logData = null;
                try {
                        Concordance<String> hintsAvailable = new Concordance<String>();
                        hintsAvailable = WordInfoStore.SelectWordInfo(whereClause);
                        System.out.println("numOfBlanks: " + numOfBlanks + ", hints available: " + hintsAvailable);
                        logData = LogDataStore.CreateLogData(numOfBlanks, hintsAvailable, 0);
                } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Problem creating log data");
                }
                System.out.println(logData.getScore());
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
                nextPage = "savedTutorsDetails.jsp?id=" + tutorialInfoId;
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
                dispatcher.forward(request, response);
        }

}
