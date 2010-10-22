/**
 * 
 */
package itjava.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Aniket, Matt
 *
 */
public class ResultEntryStore {
	private static ArrayList<String> _setOfLinks;
	private static ArrayList<ResultEntry> _resultEntries;
	
	public ResultEntryStore(ArrayList<String> setOfLinks){
		_setOfLinks = setOfLinks;
	}
	
	public ArrayList<ResultEntry> createResultEntryList() {
		_resultEntries = new ArrayList<ResultEntry>();
		
		for(int i=0;i<_setOfLinks.size();i++){
			try {
				URL url = new URL(_setOfLinks.get(i));
				url.openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				String finalContents = "";
				while ((inputLine = reader.readLine()) != null) {
					finalContents += "\n" + inputLine.replaceAll("<code", "<pre").replaceAll("code>", "pre>");
				}
				Document doc = Jsoup.parse(finalContents);
				Elements eles = doc.getElementsByTag("pre");
				ResultEntry newEntry = new ResultEntry(Convertor.FormatCode(eles.text()), _setOfLinks.get(i), eles.size());
				_resultEntries.add(newEntry);
	
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
