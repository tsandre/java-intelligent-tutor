/**
 * 
 */
package itjava.view;

import itjava.data.LocalMachine;
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
@WebServlet("/codeSearchForm")
public class CodeUploadServlet extends HttpServlet {

	private Connection _conn;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// to get the content type information from JSP Request Header
		String contentType = request.getContentType();
		File fileDir = new File(LocalMachine.home + "IndustryFiles/");
		if (!fileDir.exists()) {
			System.out.println("Dir Made: " + fileDir.mkdir());
		} else {
			System.out.println("DirPresent: " + fileDir.isDirectory());
		}

		ArrayList<String> filesList = new ArrayList<String>();

		String fileSavePath = LocalMachine.home + "IndustryFiles/";
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
						System.out.println("item: " + itemName);
						File file = new File(fileSavePath + itemName);

						int i = 1;
						while (file.exists() == true) {
							file = new File(fileSavePath
									+ itemName.substring(0,
											itemName.indexOf(".java")) + "_"
									+ i + ".java");
							System.out.println("File Exists. Now trying : "
									+ itemName.substring(0,
											itemName.indexOf(".java")) + "_"
									+ i + ".java");
							i++;
						}
						item.write(file);
						System.out.println("fileWritten to: "
								+ file.getAbsolutePath());
						filesList.add(file.getAbsolutePath());

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}

			System.out.println(LocalMachine.home + "IndustryFiles/");
			System.out.println(filesList);
			JavaSourceCodeIndexer codeIndexer = new JavaSourceCodeIndexer();
			codeIndexer.Indexer();
			
			// At this point, filesList should be populated with absolute file
			// paths. You may use this to call you method. Also, this servlet
			// has to be redirected to an output page. Now it is just blank.
	        RequestDispatcher dispatcher = request.getRequestDispatcher("searchIndustry.jsp");
	        dispatcher.forward(request, response);
		}

	}
}
