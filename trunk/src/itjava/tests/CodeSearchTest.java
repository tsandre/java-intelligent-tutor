package itjava.tests;

//import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import itjava.presenter.*;
import itjava.model.*;

//import com.google.gdata.client.codesearch.CodeSearchService;
//import com.google.gdata.data.codesearch.CodeSearchEntry;
//import com.google.gdata.data.codesearch.CodeSearchFeed;

public class CodeSearchTest {

	@Test
	public void CodeSearchReturnsEntries() {
		try {
			CodeSearchPresenter newCodePresenter = new CodeSearchPresenter("java jdbc example");
			ArrayList<ResultEntry> entries = newCodePresenter.SearchNext();
			for(int i=0;i<entries.size();i++){
				System.out.println("\n-----------------------------------------------------");
				System.out.println("URL: " + entries.get(i).url + ", OPT: " + i + ", Length: " + entries.get(i).length);
				System.out.println("-----------------------------------------------------\n");
				System.out.println(entries.get(i).text);
			}
			
			ArrayList<ResultEntry> entries2 = newCodePresenter.SearchNext();
			for(int i=0;i<entries2.size();i++){
				System.out.println("\n-----------------------------------------------------");
				System.out.println("URL: " + entries2.get(i).url + ", OPT: " + i + ", Length: " + entries2.get(i).length);
				System.out.println("-----------------------------------------------------\n");
				System.out.println(entries2.get(i).text);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
