package itjava.model;

import java.util.ArrayList;
import java.util.Iterator;

import itjava.data.DorminComponent;
import itjava.data.TutorialTemplate;

public class TutorialStore {

	public Tutorial tutorial;
	public ArrayList<String> linesOfCode;

	public void setLinesOfCode(ArrayList<String> linesOfCode) {
		this.linesOfCode = Convertor.CleanArrayListOfString(linesOfCode);
	}

	public ArrayList<WordInfo> wordInfoList;

	private ArrayList<String> _variableDeclarations;
	private String _initComponentFunctionDeclaration;
	
	private ArrayList<String> _labelVariables;
	private ArrayList<String> _textVariables;
	private ArrayList<String> _buttonVariables;
	private ArrayList<String> _comboVariables;
	
	public TutorialStore() {
		_variableDeclarations = new ArrayList<String>();
		_initComponentFunctionDeclaration = new String();
		_labelVariables = new ArrayList<String>();
		_textVariables = new ArrayList<String>();
		_comboVariables = new ArrayList<String>();
		_buttonVariables = new ArrayList<String>();
	}
	
	public Tutorial GenerateTutorial(String tutorialName) {
		this.tutorial = new Tutorial();
		if (this.linesOfCode == null || this.linesOfCode.size() == 0) {
			System.err.println("Input param for GenerateTutorial are NULL");
			return null;
		}

		CreateVariableDeclarations();
		CreateInitComponentsFunction();

		tutorial.tutorialName = tutorialName;
		tutorial.AddComment("Tutor created automatically by TutorialStore");

		tutorial.AppendCode(TutorialTemplate.packageDeclaration);
		tutorial.AppendCode(TutorialTemplate.importDeclaration);
		tutorial.AppendCode(TutorialTemplate.classDeclaration(tutorialName));
		tutorial.AppendCode(TutorialTemplate.mainFunctionDeclaration(tutorialName));
		tutorial.AppendCode(_initComponentFunctionDeclaration); //Generated in CreateXYZ() above
		for (String variableDeclaration : _variableDeclarations) {
			tutorial.AppendCode(variableDeclaration);
		}
		tutorial.AppendCode(TutorialTemplate.endClassDeclaration);

		return this.tutorial;
	}

	private void CreateInitComponentsFunction() {
		_initComponentFunctionDeclaration = TutorialTemplate.initComponentsBegin();
		
		for(String labelVariable: _labelVariables) {
			_initComponentFunctionDeclaration += TutorialTemplate.initComponentDeclaration(labelVariable, "DorminLabel");
		}
		
		for(String buttonVariable: _buttonVariables) {
			_initComponentFunctionDeclaration += TutorialTemplate.initComponentDeclaration(buttonVariable, "DorminButton");
		}
		
		for(String textVariable: _textVariables) {
			_initComponentFunctionDeclaration += TutorialTemplate.initComponentDeclaration(textVariable, "DorminTextField");
		}
		
		AddComponentsToPanel();
		
		_initComponentFunctionDeclaration += TutorialTemplate.initComponentsEnd();
		
		
	}

	private void AddComponentsToPanel() {
		
		ArrayList<Integer> lineNumbersForBlankedWords = new ArrayList<Integer>();
		for(WordInfo currWordInfo: wordInfoList) {
			lineNumbersForBlankedWords.add(currWordInfo.lineNumber);
		}
		int indexOfLinesOfCode = 1;
		int x = 0, y = 10, height = 25, width;
		Iterator<WordInfo> wordInfoIterator = wordInfoList.iterator();
		for (String lineOfCode:linesOfCode) {
			String firstPartOfLineOfCode = lineOfCode;
			if(lineNumbersForBlankedWords.contains(indexOfLinesOfCode)) {
				WordInfo currWordInfo = wordInfoIterator.next();
				if (currWordInfo.lineNumber != indexOfLinesOfCode) {
					System.err.println("WordInfoList and LinesOfCode List not in sync..");
					System.err.println("OR multiple words on single line");
				}
				int beginIndex = currWordInfo.columnNumber + currWordInfo.wordLength();
				String restOfLine = lineOfCode.trim().substring(beginIndex);
				DorminComponent dorminLabel = new DorminComponent();
				width = restOfLine.length() * 8;
				String lblName = "lblLine" + indexOfLinesOfCode
				+ "Col" + currWordInfo.columnNumber;
				dorminLabel.Label(restOfLine, lblName, x + (8 * beginIndex) , y, height, width);
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminLabel);
				
				String nameOfTextField = "txtLine" + indexOfLinesOfCode + "Col" + currWordInfo.columnNumber;
				DorminComponent dorminText = new DorminComponent();
				width = currWordInfo.wordLength() * 8;
				dorminText.TextField(nameOfTextField, x + (8 * currWordInfo.columnNumber), y, height, width);
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminText);
				
				firstPartOfLineOfCode = lineOfCode.substring(0, currWordInfo.columnNumber);
			}
			
			DorminComponent dorminLabel = new DorminComponent();
			dorminLabel.Label(firstPartOfLineOfCode, "lblLine" + indexOfLinesOfCode, x, y, height, firstPartOfLineOfCode.length() * 8);
			_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminLabel);
			
			y += height;
			indexOfLinesOfCode++;
		}

		DorminComponent doneButton = new DorminComponent();
		doneButton.Button("Done", "doneButton", 20, y + 20, 20, 60);
		_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(doneButton);
	}

	private void CreateVariableDeclarations() {
		_variableDeclarations.add(TutorialTemplate.ctatOptionsDeclaration("cTAT_Options1"));
		_variableDeclarations.add(TutorialTemplate.buttonDeclaration("doneButton"));
		this._buttonVariables.add("doneButton");
		
		for (int indexOfLinesOfCode = 1; indexOfLinesOfCode <= linesOfCode.size(); indexOfLinesOfCode++) {
			String labelName = "lblLine" + indexOfLinesOfCode;
			this._labelVariables.add(labelName);
			this._variableDeclarations.add(TutorialTemplate.labelDeclaration(labelName));
		}
		
		if (wordInfoList != null || wordInfoList.size() != 0) {
			for (WordInfo wordInfo : wordInfoList) {

				String variableName = "Line" + wordInfo.lineNumber
						+ "Col" + wordInfo.columnNumber;
				
				switch (wordInfo.blankType) {
				case Text:
					String txtName = "txt" + variableName;
					this._variableDeclarations.add(TutorialTemplate.textFieldDeclaration(txtName));
					this._textVariables.add(txtName);
					break;

				case Combo:
					System.err.println("Not implemented");
					break;
				}
								
				String labelName = "lbl" + variableName;
				this._labelVariables.add(labelName);
				this._variableDeclarations.add(TutorialTemplate.labelDeclaration(labelName));
			}			
		}

	}

}
