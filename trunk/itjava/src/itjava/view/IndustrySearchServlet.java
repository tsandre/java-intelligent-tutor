/**
 * 
 */
package itjava.view;

import itjava.data.LocalMachine;
import itjava.industry.JavaCodeSearch;
import itjava.industry.JavaSourceCodeIndexer;
import itjava.scraper.DBConnection;
import itjava.scraper.ScrapeData;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.eclipse.jdt.core.dom.EnumDeclaration;

/**
 * @author Bharat
 * 
 */
//@WebServlet("/codeSearchForm")
public class IndustrySearchServlet extends HttpServlet {

	private Connection _conn;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String query = (String) request.getParameter("query");
		query = query.replaceAll("\\W", " ").replaceAll("\\s+", " ").trim();
		HttpSession session = request.getSession(true);
		session.setAttribute("query", query);
		String faqSearchQuery = (String) session.getAttribute("query");
		ArrayList <String> fileResults = new ArrayList <String>();
		fileResults = JavaCodeSearch.industrySearch(faqSearchQuery);
		request.setAttribute("fileLocation", fileResults);
		for(String path: fileResults){
			System.out.println(path);
		}
        RequestDispatcher dispatcher = request.getRequestDispatcher("codeResults.jsp");
        dispatcher.forward(request, response);
		
	}
	
	
}
