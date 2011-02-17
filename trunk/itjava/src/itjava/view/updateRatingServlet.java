package itjava.view;

import itjava.model.ResultEntry;
import itjava.db.*;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 */
@WebServlet(description = "Updates the star rating", urlPatterns = { "/updateRatingServlet" })
public class updateRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<ResultEntry> sourceCodes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateRatingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement ucpst = null;
		ResultSet rs = null;

	   try
	   {
		   HttpSession session = request.getSession(false);
		   String rating = request.getParameter("ratingValue");
		   System.out.println(rating);
		   String tutorID = request.getParameter("tutorID");
		   String userName = (String) session.getAttribute("userName");
		   String userLevel;
		   if(session.getAttribute("userLevel") != null){
			   userLevel = (String) session.getAttribute("userLevel");
		   }else{
			   userLevel = "unknown";
		   }
		   
		   conn = DBConnection.GetConnection();
		   String getrating = "SELECT ratingId FROM TutorRatings WHERE tutorId=? AND userName=? AND userLevel=?";
		   ucpst = conn.prepareStatement(getrating);
		   ucpst.setString(1, tutorID);
		   ucpst.setString(2, userName);
		   ucpst.setString(3, userLevel);
		   rs = ucpst.executeQuery();
		   if(!rs.next()){
			   String insertrating = "INSERT INTO TutorRatings(tutorId, userName, userLevel, rating) VALUES(?, ?, ?, ?)";
			   ucpst = conn.prepareStatement(insertrating);
			   ucpst.setString(1, tutorID);
			   ucpst.setString(2, userName);
			   ucpst.setString(3, userLevel);
			   ucpst.setString(4, rating);
			   ucpst.execute();
		   }else{
			   String updaterating = "UPDATE TutorRatings SET rating=? WHERE tutorId=? AND userName=? AND userLevel=?";
			   ucpst = conn.prepareStatement(updaterating);
			   ucpst.setString(1, rating);
			   ucpst.setString(2, tutorID);
			   ucpst.setString(3, userName);
			   ucpst.setString(4, userLevel);
			   ucpst.executeUpdate();
		   }
		   RequestDispatcher dispatcher = request.getRequestDispatcher("ratingupdated.jsp");
		   dispatcher.forward(request, response);
	   }
	   catch(Exception e) {
  	     e.printStackTrace();
  	   }
  	   finally {
  		 try{
			   conn.close();
		   }catch(Exception e){
			 e.printStackTrace();
		   }
  	   }
	}

}
