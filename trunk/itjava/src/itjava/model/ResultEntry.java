/**
 * 
 */
package itjava.model;

import java.util.HashMap;

/**
 * @author Aniket
 *
 */
public class ResultEntry extends HashMap<String, String> {
	public String text;
	public String url;
	public int length;
	
	/**
	 * @param inText Text to be set for this result Entry
	 * @param inUrl URL of the resource
	 * @param inLength Length of the text
	 */
	public ResultEntry(String inText, String inUrl, int inLength) {
		text = inText;
		url = inUrl;
		length = inLength;
	}
	
	public String toString() {
		return "URL : " + this.url + "\n" + this.text + "\n";
	}
}
