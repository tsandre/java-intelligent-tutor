/**
 * 
 */
package itjava.view;

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
@WebServlet("/FAQSelectionForm")
public class CodeUploadServlet extends HttpServlet {

	private Connection _conn;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// to get the content type information from JSP Request Header
		String contentType = request.getContentType();
		
		
		if ((contentType != null)
				&& (contentType.indexOf("multipart/form-data") >= 0)) {
			
				FileItemFactory factory = new DiskFileItemFactory();
	           ServletFileUpload upload = new ServletFileUpload(factory);
	           List items = null;
	           try {
	                   items = upload.parseRequest(request);
	           } catch (FileUploadException e) {
	                   e.printStackTrace();
	           }
	           Iterator itr = items.iterator();
	           while (itr.hasNext()) {
	           FileItem item = (FileItem) itr.next();
	           if (item.isFormField()) {
	           } else {
	                   try {
	                           String itemName = item.getName();
	                           System.out.println("item: "+itemName);
	                           File file = new File(itemName);
	                           item.write(file);
	                           FileInputStream fis = new FileInputStream(file);
	                           BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fis)));
	                           String strLine;
	                           while((strLine=br.readLine())!=null) {
	                        	   System.out.println(strLine);
	                           }
	                           br.close();
	                   } catch (Exception e) {
	                           e.printStackTrace();
	                   }
	           }
	           }
			

		}

	}
}
