package itjava.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Tutorial {
	private String _tutorialName;
	private String _readableName;
	private String _type;
	public String tutorialCode = "";
	private ArrayList<WordInfo> wordInfoList;
	private ArrayList<WordInfo> originalWordInfoList;
	private CompilationUnitFacade _facade;
	private ArrayList<String> _linesOfCode;
	
	public File tutorialClassFile;
	public int difficultyLevel;
	private String path = "";
	private ArrayList<EdgeData> _edgeDataList;
	private ArrayList<LabelData> _labelDataList;

	public Tutorial() {
		
	}
	public Tutorial(String tutorialName, String readableName, ArrayList<WordInfo> wordInfoListIn,
			CompilationUnitFacade facade, ArrayList<WordInfo> originalWordInfoList) {
		_tutorialName = tutorialName;
		_readableName = readableName.trim().replaceAll("\\s", "");
		this.wordInfoList = wordInfoListIn;
		this.originalWordInfoList = originalWordInfoList;
		try {
			this.setFacade(facade);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
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
		return this.getFacade().getLinesOfCode();
	}
	/**
	 * @param linesOfCode the linesOfCode to set
	 * @throws IOException 
	 */
	public void setLinesOfCode(String unformattedSource) throws IOException {
		_linesOfCode = Convertor.StringToArrayListOfStrings(unformattedSource);
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
	public void setType(String type) {
		this._type = type;
	}
	public String getType() {
		return this._type;
	}
	public void setOriginalWordInfoList(ArrayList<WordInfo> originalWordInfoList) {
		this.originalWordInfoList = originalWordInfoList;
	}
	public ArrayList<WordInfo> getOriginalWordInfoList() {
		return originalWordInfoList;
	}
	/**
	 * @param facade the facade to set
	 * @throws IOException 
	 */
	public void setFacade(CompilationUnitFacade facade) throws IOException {
		_facade = facade;
		this.setLinesOfCode(_facade.getUnformattedSource());
	}
	/**
	 * @return the facade
	 */
	public CompilationUnitFacade getFacade() {
		return _facade;
	}
	
	public String getUrl() {
		return _facade.getUrl();
	}
	
	
	/**
	 * TODO: Verifies whether the wordlist of this tutorial has a wordInfo whose string value equals to newWord
	 * @param newWord Word to find in the wordlist
	 * @return
	 */
	public boolean contains(String newWord) {
		return false;
	}
	
	public Set<Integer> getLineNumbersUsed() {
		HashSet<Integer> lineNumbersUsed = new HashSet<Integer>();
		for (WordInfo wordInfo: this.getWordInfoList()) {
			lineNumbersUsed.add(wordInfo.lineNumber);
		}
		return lineNumbersUsed;
	}

}
