package itjava.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import itjava.data.DorminComponent;
import itjava.data.LocalMachine;
import itjava.db.DBConnection;

public class TutorialStore {

	private Tutorial _tutorial;
	public ArrayList<String> _linesOfCode;

	private ArrayList<WordInfo> _wordInfoList;
	private ArrayList<WordInfo> _oriWordInfoList;

	private ArrayList<String> _variableDeclarations;
	private String _initComponentFunctionDeclaration;
	
	private ArrayList<String> _labelVariables;
	private ArrayList<String> _textVariables;
	private ArrayList<String> _buttonVariables;
	private ArrayList<String> _comboVariables;
	

	private ArrayList<LabelData> labelDataList;
	private ArrayList<EdgeData> edgeDataList;
	private final ArrayList<Integer> _lineNumbersForHighlightedWords;
	private final ArrayList<Integer> _lineNumbersForBlankedWords;
	
	public TutorialStore() {
		_variableDeclarations = new ArrayList<String>();
		_initComponentFunctionDeclaration = new String();
		_labelVariables = new ArrayList<String>();
		_textVariables = new ArrayList<String>();
		_comboVariables = new ArrayList<String>();
		_buttonVariables = new ArrayList<String>();
		
		labelDataList = new ArrayList<LabelData>();
		edgeDataList = new ArrayList<EdgeData>();
		_lineNumbersForHighlightedWords = new ArrayList<Integer>();
		_lineNumbersForBlankedWords = new ArrayList<Integer>();
	}
	
	public Tutorial GenerateTutorial(Tutorial tutorial) throws Exception {
		if (tutorial.getLinesOfCode() == null || tutorial.getLinesOfCode().size() == 0) {
			throw new Exception("Lines of code for tutorial are NULL");
		}
		
		_tutorial = tutorial;
		_linesOfCode = tutorial.getLinesOfCode();
		_oriWordInfoList = tutorial.getOriginalWordInfoList();

		if (tutorial.getWordInfoList() != null) {
			if (tutorial.getWordInfoList().size() > 0) {
				_wordInfoList = tutorial.getWordInfoList();
			}
		}
		else if (_oriWordInfoList == null) {
			throw new NullPointerException("WordInfoList & OriWordInfoList are null");
		}
		
		CreateVariableDeclarations();
		CreateInitComponentsFunction();

		_tutorial.AddComment("Tutor created automatically by TutorialStore");

		_tutorial.AppendCode(TutorialTemplate.packageDeclaration);
		_tutorial.AppendCode(TutorialTemplate.importDeclaration);
		_tutorial.AppendCode(TutorialTemplate.classDeclaration(_tutorial.getTutorialName()));
		_tutorial.AppendCode(TutorialTemplate.mainFunctionDeclaration(_tutorial.getTutorialName()));
		_tutorial.AppendCode(_initComponentFunctionDeclaration); //Generated in CreateInitComp..() above
		for (String variableDeclaration : _variableDeclarations) {
			_tutorial.AppendCode(variableDeclaration);
		}
		_tutorial.AppendCode(TutorialTemplate.endClassDeclaration);
		SaveTutorialFile();
		_tutorial.setEdgeDataList(edgeDataList);
		_tutorial.setLabelDataList(labelDataList);
		return _tutorial;
	}

	
	private void SaveTutorialFile() {
		BufferedWriter writer;
		try {
			File file = new File(LocalMachine.home + "generated/" + _tutorial.getTutorialName() + ".java");
			if (file.exists()) {
				file.delete();
			}
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(_tutorial.tutorialCode);
			writer.close();
		} catch (IOException e) {
			System.err.println("Error saving tutorial file..");
			e.printStackTrace();
		}
	}

	private void CreateInitComponentsFunction() throws Exception {
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

	private void AddComponentsToPanel() throws Exception {
		
		Iterator<WordInfo> wordInfoIterator = null;
		Iterator<WordInfo> highlightInfoIterator = null;
		
		if (_wordInfoList != null) {
			wordInfoIterator = _wordInfoList.iterator();
		}
		highlightInfoIterator = _oriWordInfoList.iterator();
			
		int indexOfLinesOfCode = 1;
		int x = 0, y = 10, height = 25, width, indent = 0;
		String prevLineOfCode = " ";
		
		for (String lineOfCode:_linesOfCode) {
			lineOfCode = lineOfCode.replace("\\\"", "\"");
			String firstPartOfLineOfCode = lineOfCode;
			if (prevLineOfCode.contains("{")) {
				indent += 20;
			}
			else if (lineOfCode.contains("}")) {
				indent -= 20;
			}
			if(_lineNumbersForBlankedWords.contains(indexOfLinesOfCode)) {
				WordInfo currWordInfo = wordInfoIterator.next();
				highlightInfoIterator.next(); // This is necessary to keep the highlightInfoIterator in sync
				if (currWordInfo.lineNumber != indexOfLinesOfCode) {
					System.err.println("WordInfoList and LinesOfCode List not in sync..");
					throw new Exception("Multiple words on single line");
				}
				int beginIndex = currWordInfo.columnNumber + currWordInfo.wordLength();
				String restOfLine = lineOfCode.trim().substring(beginIndex);
				DorminComponent dorminLabel = new DorminComponent();
				width = restOfLine.length() * 8;
				String lblName = "lblLine" + indexOfLinesOfCode
									+ "Col" + currWordInfo.columnNumber;
				dorminLabel.Label(restOfLine, lblName, x + (8 * beginIndex) + indent, y, height, width);
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminLabel, false);
				
				labelDataList.add(new LabelData(lblName, restOfLine));
				
				String nameOfTextField = "txtLine" + indexOfLinesOfCode + "Col" + currWordInfo.columnNumber;
				DorminComponent dorminText = new DorminComponent();
				width = currWordInfo.wordLength() * 8;
				dorminText.TextField(nameOfTextField, x + (8 * currWordInfo.columnNumber) + indent, y, height, width);
				
				edgeDataList.add(new EdgeData(currWordInfo.wordToBeBlanked,nameOfTextField));
			
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminText, false);
				firstPartOfLineOfCode = lineOfCode.trim().substring(0, currWordInfo.columnNumber);
			}
			else if(_lineNumbersForHighlightedWords.contains(indexOfLinesOfCode)) {
				WordInfo currWordInfo = highlightInfoIterator.next();
				if (currWordInfo.lineNumber != indexOfLinesOfCode) {
					System.err.println("OriginalWordInfoList and LinesOfCode List not in sync..");
//					throw new Exception("Multiple words on single line");
				}
				int beginIndex = currWordInfo.columnNumber + currWordInfo.wordLength();
				String restOfLine = lineOfCode.trim().substring(beginIndex);
				DorminComponent dorminLabel = new DorminComponent();
				width = restOfLine.length() * 8;
				String lblName = "lblLine" + indexOfLinesOfCode
									+ "Col" + currWordInfo.columnNumber;
				dorminLabel.Label(restOfLine, lblName, x + (8 * beginIndex) + indent, y, height, width);
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminLabel, false);
				
				labelDataList.add(new LabelData(lblName, restOfLine));
				
				/*String nameOfTextField = "txtLine" + indexOfLinesOfCode + "Col" + currWordInfo.columnNumber;
				DorminComponent dorminText = new DorminComponent();
				width = currWordInfo.wordLength() * 8;
				dorminText.TextField(nameOfTextField, x + (8 * currWordInfo.columnNumber) + indent, y, height, width);
				
				edgeDataList.add(new EdgeData(currWordInfo.wordToBeBlanked,nameOfTextField));
			
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminText, false);*/
				
				int highlightBeginIndex = currWordInfo.columnNumber;
				String highLightLine = lineOfCode.trim().substring(highlightBeginIndex, beginIndex);
				DorminComponent highlightLabel = new DorminComponent();
				width = currWordInfo.wordLength() * 8;
				String hlLblName = "hlLblLine" + indexOfLinesOfCode;
				highlightLabel.Label(highLightLine, hlLblName, x + (8 * highlightBeginIndex) + indent, y, height, width);
				_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(highlightLabel, true);
				
				labelDataList.add(new LabelData(hlLblName, highLightLine));
				
				firstPartOfLineOfCode = lineOfCode.trim().substring(0, currWordInfo.columnNumber);
			}
			
			DorminComponent dorminLabel = new DorminComponent();
			dorminLabel.Label(firstPartOfLineOfCode, "lblLine" + indexOfLinesOfCode, x + indent, y, height, firstPartOfLineOfCode.length() * 8);
			labelDataList.add(new LabelData("lblLine" + indexOfLinesOfCode, firstPartOfLineOfCode));
			
			_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(dorminLabel, false);
			
			y += height;
			indexOfLinesOfCode++;
			prevLineOfCode = lineOfCode;
		}
/*
		DorminComponent hintButton = new DorminComponent();
		hintButton.Button("Help", "hint", 90, y + 20, 20, 60);*/
		DorminComponent doneButton = new DorminComponent();
		doneButton.Button("Done", "doneButton", 20, y + 20, 20, 90);
	
		
		/*labelDataList.add(new LabelData("hint","Help"));*/
		labelDataList.add(new LabelData("doneButton","Done"));
		edgeDataList.add(new EdgeData("Done","doneButton"));
		/*_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(hintButton);*/
		_initComponentFunctionDeclaration += TutorialTemplate.addComponentToPanel(doneButton, false);
	}

	private void CreateVariableDeclarations() {
		_variableDeclarations.add(TutorialTemplate.ctatOptionsDeclaration("cTAT_Options1"));
		_variableDeclarations.add(TutorialTemplate.buttonDeclaration("doneButton"));
		this._buttonVariables.add("doneButton");
		Iterator<WordInfo> wordInfoIterator = null;

		if(_wordInfoList !=  null) {
			wordInfoIterator = _wordInfoList.iterator();
			for (WordInfo currWordInfo : _wordInfoList) {
				_lineNumbersForBlankedWords.add(currWordInfo.lineNumber);
			}
		}
		
		Iterator<WordInfo> highlightInfoIterator = _oriWordInfoList.iterator();

		
		for (WordInfo currWordInfo : _oriWordInfoList) {
			_lineNumbersForHighlightedWords.add(currWordInfo.lineNumber);
		}
		
		for (int indexOfLinesOfCode = 1; indexOfLinesOfCode <= _linesOfCode.size(); indexOfLinesOfCode++) {
			String labelName = "lblLine" + indexOfLinesOfCode;
			this._labelVariables.add(labelName);
			this._variableDeclarations.add(TutorialTemplate.labelDeclaration(labelName));

			if(_lineNumbersForBlankedWords.contains(indexOfLinesOfCode)) {
				WordInfo wordInfo = wordInfoIterator.next();
				highlightInfoIterator.next(); //To keep 2 lists in sync
				
				String variableName = "Line" + wordInfo.lineNumber
						+ "Col" + wordInfo.columnNumber;
				String restLabelName = "lbl" + variableName;
				this._labelVariables.add(restLabelName);
				this._variableDeclarations.add(TutorialTemplate.labelDeclaration(restLabelName));
				
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
			}
			else if (_lineNumbersForHighlightedWords.contains(indexOfLinesOfCode)) {
				WordInfo wordInfo = highlightInfoIterator.next();
				
				String variableName = "Line" + wordInfo.lineNumber
						+ "Col" + wordInfo.columnNumber;
				String restLabelName = "lbl" + variableName;
				this._labelVariables.add(restLabelName);
				this._variableDeclarations.add(TutorialTemplate.labelDeclaration(restLabelName));
				
					/*String txtName = "txt" + variableName;
					this._variableDeclarations.add(TutorialTemplate.textFieldDeclaration(txtName));
					this._textVariables.add(txtName);*/
				String hlLblName = "hlLblLine" + indexOfLinesOfCode;
				this._labelVariables.add(hlLblName);
				this._variableDeclarations.add(TutorialTemplate.labelDeclaration(hlLblName));
			}
		}
	}
	
	/**
	 * Stores the statistical information of a {@link Tutorial} to database
	 * @param tutorialList List of tutorials to be stored
	 * @param tutorialInfoId The corresponding foreign key.
	 * @return Number of rows inserted.
	 */
	public static int InsertDeliverableInfo(ArrayList<Tutorial> tutorialList, int tutorialInfoId) {
		Connection conn = null;
		int rowsInserted = 0;
		try {
			conn = DBConnection.GetConnection();
			for (Tutorial tutorial : tutorialList) {
				String insertSql = "insert into DeliverableInfo" +
						"(deliverableName, tutorialInfoId, deliverableType, difficultyLevel, numOfBlanks)" +
						" values (?,?,?,?,?)";
				PreparedStatement insertStmt = conn.prepareStatement(insertSql);
				insertStmt.setString(1, tutorial.getTutorialName());
				insertStmt.setInt(2, tutorialInfoId);
				insertStmt.setString(3, tutorial.getType());
				insertStmt.setInt(4, tutorial.getDifficulty());
				insertStmt.setInt(5, (tutorial.getWordInfoList() == null) ? 0 : tutorial.getWordInfoList().size());
				rowsInserted += insertStmt.executeUpdate();
				ResultSet rs = insertStmt.getGeneratedKeys();
				while (rs.next() && tutorial.getType().equals("Quiz")) {
					DeliverableInfoStore.InsertBlankWordsInfo(rs.getInt(1), tutorial.getWordInfoList());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowsInserted;
	}
	
}

