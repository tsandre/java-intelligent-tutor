package itjava.model;


import java.io.File;
import java.util.ArrayList;

public class Tutorial {
	private String _tutorialName;
	private String _readableName;
	public String tutorialCode = "";
	private ArrayList<WordInfo> wordInfoList;
	private ArrayList<String> linesOfCode;
	
	public File tutorialClassFile;
	public int difficultyLevel;
	public String sourceUrl = "";
	private String path = "";
	private ArrayList<EdgeData> _edgeDataList;
	private ArrayList<LabelData> _labelDataList;

	public Tutorial() {
		
	}
	public Tutorial(String tutorialName, String readableName, ArrayList<WordInfo> wordInfoListIn,
			ArrayList<String> linesOfCodeIn, String sourceUrlIn) {
		_tutorialName = tutorialName;
		_readableName = readableName.trim().replaceAll("\\s", "");
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
	public void setTutorialName(String tutorialName) {
		this._tutorialName = tutorialName;
	}
	public String getTutorialName() {
		return _tutorialName;
	}
	/**
	 * @return the wordInfoList
	 */
	public ArrayList<WordInfo> getWordInfoList() {
		return wordInfoList;
	}
	/**
	 * @param wordInfoList the wordInfoList to set
	 */
	public void setWordInfoList(ArrayList<WordInfo> wordInfoList) {
		this.wordInfoList = wordInfoList;
	}

	/**
	 * @return the linesOfCode
	 */
	public ArrayList<String> getLinesOfCode() {
		return linesOfCode;
	}
	/**
	 * @param linesOfCode the linesOfCode to set
	 */
	public void setLinesOfCode(ArrayList<String> linesOfCode) {
		this.linesOfCode = linesOfCode;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public void setReadableName(String _readableName) {
		this._readableName = _readableName;
	}
	public String getReadableName() {
		return _readableName;
	}
	public void setDifficulty(int difficulty) {
		this.difficultyLevel = difficulty;
	}
	public int getDifficulty() {
		return this.difficultyLevel;
	}

}
