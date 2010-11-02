/**
 * 
 */
package itjava.model;

import java.util.regex.*;
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
				URLConnection urlconn = url.openConnection();
				urlconn.setReadTimeout(5000);
				int urlPadding = 0; //Padding is used to uniquely identify each piece of code in spite of same url.
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				String finalContents = "";
				while ((inputLine = reader.readLine()) != null) {
					finalContents += "\n" + inputLine.replaceAll("<code", "<pre").replaceAll("code>", "pre>");
				}
				
				Document doc = Jsoup.parse(finalContents);
				Elements eles = doc.getElementsByTag("pre");
				ResultEntry newEntry;
				for(int j=0;j<eles.size();j++){
					if(eles.get(j).text().length()>=30){ 
						Pattern pattern = Pattern.compile("/\\*.*\\*/", Pattern.DOTALL);
					    Matcher matcher = pattern.matcher(eles.get(j).text());
					    Pattern pattern2 = Pattern.compile("^ *//.*");
					    Matcher matcher2 = pattern2.matcher(matcher.replaceAll(""));
					    Pattern pattern3 = Pattern.compile("[0-9]+: *");
					    Matcher matcher3 = pattern3.matcher(matcher2.replaceAll(""));
					    int NumOpenBraces = matcher3.replaceAll("").replaceAll("[^{]","").length();
					    int NumClosedBraces = matcher3.replaceAll("").replaceAll("[^}]","").length();
					    if(NumOpenBraces != 0 && NumOpenBraces == NumClosedBraces){
							newEntry = new ResultEntry(Convertor.FormatCode(matcher3.replaceAll("")), (urlPadding++ +_setOfLinks.get(i).toString()), matcher3.replaceAll("").length());
							_resultEntries.add(newEntry);
					    }
					}
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
