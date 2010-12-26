package itjava.model;

import java.util.ArrayList;

import itjava.data.BlankType;

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
	public ArrayList<String> hintsAvailable;
	
	public WordInfo() {
		this.lineNumber = -1;
		this.columnNumber = -1;
		this.blankType = null;
		this.wordToBeBlanked = "";
	}
}
