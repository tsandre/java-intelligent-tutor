/**
 * 
 */
package itjava.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Aniket, Matt
 *
 */
public class ResultEntryStore {
	private static ArrayList<URL> _setOfLinks;
	private static ArrayList<ResultEntry> _resultEntries;
	
	public static ArrayList<ResultEntry> createResultEntryList(HashMap<String, String> fileContentsToUrlMap) {
		_resultEntries = new ArrayList<ResultEntry>();
		for (Entry<String, String> entry : fileContentsToUrlMap.entrySet()) {
			ResultEntry newEntry = new ResultEntry(entry.getKey(), entry.getValue(), 0);
			_resultEntries.add(newEntry);
		}
		return _resultEntries;
	}
	
	public static ArrayList<ResultEntry> createResultEntryList(ArrayList<URL> setOfLinks) {
		_setOfLinks = setOfLinks;
		_resultEntries = new ArrayList<ResultEntry>();
		
		for(int i=0;i<_setOfLinks.size();i++){
			try {
				URL url = _setOfLinks.get(i);
				url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				String finalContents = "";
				while ((inputLine = reader.readLine()) != null) {
					finalContents += "\n" + inputLine.replaceAll("<code", "<pre").replaceAll("code>", "pre>");
				}
				Document doc = Jsoup.parse(finalContents);
				Elements eles = doc.getElementsByTag("pre");
				if(eles.hasText()){
				ResultEntry newEntry = new ResultEntry(Convertor.FormatCode(eles.text()), _setOfLinks.get(i).toString(), eles.size());
				_resultEntries.add(newEntry);
				}
	
			} catch (Exception e) {
				System.err.println(e.toString() + " thorwn by following URL : " + _setOfLinks.get(i));
			}
		}
		
		return _resultEntries;
	}
	
	public ArrayList<ResultEntry> getResults(){
		return _resultEntries;
	}
	
}
