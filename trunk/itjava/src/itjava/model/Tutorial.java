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
	private ArrayList<EdgeData> _edgeDataList;
	private ArrayList<LabelData> _labelDataList;

	public Tutorial() {
		
	}
	public Tutorial(ArrayList<WordInfo> wordInfoListIn,
			ArrayList<String> linesOfCodeIn, String sourceUrlIn) {
		this.wordInfoList = wordInfoListIn;
		this.linesOfCode = linesOfCodeIn;
		this.sourceUrl = sourceUrlIn;
	}

	public void AppendCode(String currLineOfCode) {
		this.tutorialCode += currLineOfCode + "\n";
	}
	
	public void AddComment(String currComment) {
		this.tutorialCode += "\n// " + currComment + "\n"; 
	}

	public void setLabelDataList(ArrayList<LabelData> labelDataList) {
		_labelDataList = labelDataList;
	}

	public ArrayList<LabelData> getLabelDataList() {
		return _labelDataList;
	}

	public void setEdgeDataList(ArrayList<EdgeData> edgeDataList) {
		_edgeDataList = edgeDataList;
	}

	public ArrayList<EdgeData> getEdgeDataList() {
		return _edgeDataList;
	}

}
