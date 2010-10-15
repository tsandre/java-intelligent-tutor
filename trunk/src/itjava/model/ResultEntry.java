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
	
	public ResultEntry(String inText, String inUrl, int inLength) {
		text = inText;
		url = inUrl;
		length = inLength;
	}
}
