package itjava.model;


import java.io.File;
import java.util.ArrayList;

public class Tutorial {
	public String tutorialName;
	public String tutorialCode = "";
	public ArrayList<WordInfo> wordInfoList;
	public ArrayList<String> linesOfCode;
	public File tutorialClassFile;
	public int difficultyLevel;
	public String sourceUrl = "";
	public String path = "";
	
	public void AppendCode(String currLineOfCode) {
		this.tutorialCode += currLineOfCode + "\n";
	}
	
	public void AddComment(String currComment) {
		this.tutorialCode += "\n// " + currComment + "\n"; 
	}

}
