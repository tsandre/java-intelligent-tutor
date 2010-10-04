package itjava.data;

public class WordInfo {	
	public String wordToBeBlanked;
	public int lineNumber;
	public int columnNumber;
	public int wordLength() {
		return wordToBeBlanked.length();
	}
	public BlankType blankType;
	//Options for combo
	//Number of lines for text area	
}
