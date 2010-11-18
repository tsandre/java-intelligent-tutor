package itjava.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import itjava.data.BlankType;
import itjava.data.LocalMachine;
import itjava.model.BRDStore;
import itjava.model.Tutorial;
import itjava.model.WordInfo;
import itjava.presenter.TutorialPresenter;

import java.io.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TutorialPresenterTest {

	private WordInfo wordInfo;
	private ArrayList<WordInfo> wordInfoList;
	private ArrayList<String> linesOfCode;
	private TutorialPresenter tutorialPresenter;
	private Tutorial tutorial;
	private String[] _bounds;

	@Before
	public final void SetUp() {
		wordInfo = new WordInfo();

		wordInfoList = new ArrayList<WordInfo>();

		linesOfCode = new ArrayList<String>();

		tutorialPresenter = new TutorialPresenter();
	}

	//START	Tests
	@Test
	public final void GetTutorialReturnsNullOnPassingInvalidParams() {
		wordInfo.wordToBeBlanked = "new";
		wordInfo.lineNumber = 2;
		wordInfo.blankType = BlankType.Text;
		wordInfo.columnNumber = 10;
		wordInfoList.add(wordInfo);
		tutorial = tutorialPresenter.GetTutorial("SampleGUI", linesOfCode,
				wordInfoList, "blank source");
		assertEquals(null, tutorial);
	}
	

	@Test
	public final void GetTutorialReturnsNotNullTutorial() {
		GivenValidWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		ThenReturnedTutorialIsNotNull();
	}

	@Test
	public final void GetTutorialWithMultipleWordsReturnsNotNullTutorial() {
		GivenValidMultipleWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		ThenReturnedTutorialIsNotNull();
	}

	@Test
	public final void TutorialCodeHasBalancedParanthesis() {
		GivenValidWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		ThenNumberofParanthesisIsBalanced();
	}

	@Test
	public final void MultipleWordsTutorialCodeHasBalancedParanthesis() {
		GivenValidMultipleWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		ThenNumberofParanthesisIsBalanced();
	}

	@Test
	public final void AllDataListArePopulated() {
		GivenValidMultipleWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		ThenEdgeDataListIsPopulated();
		ThenLabelDataListIsPopulated();
	}
	
	@Test
	public final void BRDIsGenerated() {
		GivenValidMultipleWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		WhenBRDStoreIsCalled();
		ThenBrdFilesAreStored();
	}
	
	@Test
	public final void DeliverableIsGenerated() {
		GivenValidMultipleWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		WhenBRDStoreIsCalled();
		WhenDeployBatchScriptIsCalled();
		WhenGeneratedFilesAreRenamed();
		WhenDeliveryBatchScriptIsCalled();
		ThenAllDeliverablesArePresentInDeliveryFolder();
	}
	
	private void WhenDeliveryBatchScriptIsCalled() {
		// TODO Auto-generated method stub
		try
		{
		System.out.println("Running the batch script for Copying files to Delivery Folder");
		String command = "cmd /C start C:/Project/myworkspace/itjava/automate/autoCopyDeliveryFiles.bat";
		Runtime rt = Runtime.getRuntime();
		rt.exec(command);
		//rt.exec("cmd /c start /MIN ...");
		System.out.println("Finished running the autoCopyDeliveryFiles batch script");
		}
		catch(Exception e) {
		System.out.println("Error creating the FileInfo panel: " +e);
		e.printStackTrace();
		}
	}

	private void WhenDeployBatchScriptIsCalled() {
		// TODO Auto-generated method stub
		try
		{
		System.out.println("Running the batch script for Building Webserver Files");
		String command = "cmd /C start C:/Project/myworkspace/itjava/automate/autoBuild.bat";
		Runtime rt = Runtime.getRuntime();
		rt.exec(command);
		System.out.println("Finished running the autoBuild batch script");
		}
		catch(Exception e) {
		System.out.println("Error creating the FileInfo panel: " +e);
		e.printStackTrace();
		}
	}

	private void ThenAllDeliverablesArePresentInDeliveryFolder() {
		// TODO Auto-generated method stub
		
	}

	private void WhenGeneratedFilesAreRenamed() {
		// TODO Auto-generated method stub
		String NewName = tutorial.tutorialName;
		String tempLocation= "C:/Users/Vasanth K/AppData/Local/VirtualStore/Program Files (x86)/Cognitive Tutor Authoring Tools/deploy-tutor/temp/";
		String HtmlToRename = tempLocation+"java.html";
		String RenamedHTML = tempLocation+NewName+".html";
		String JarToRename = tempLocation+"java.jar";
		String RenamedJAR = tempLocation+NewName+".jar";
		String JnlpToRename = tempLocation+"java.jnlp";
		String RenamedJNLP = tempLocation+NewName+".jnlp";
		File NewJAR = new File(RenamedJAR);
		File NewJNLP = new File(RenamedJNLP);
		File NewHTML = new File(RenamedHTML);
		File JARfile = new File(JarToRename);
		File JNLPfile = new File(JnlpToRename);
		File Htmlfile = new File(HtmlToRename);
		JARfile.renameTo(NewJAR);
		JNLPfile.renameTo(NewJNLP);
		Htmlfile.renameTo(NewHTML);
	}


	private void WhenBRDStoreIsCalled() {
		BRDStore.GenerateBRD(tutorial);
	}

	private void ThenBrdFilesAreStored() {
		//TODO Write a function that will test if .brd files are saved in the directory generated
		// check if the file has name tutorial.tutorialName

		String fileToCheck = LocalMachine.home+"generated/"+tutorial.tutorialName+".brd";
		File f = new File(fileToCheck);
		assertTrue(f.exists());

	}
	
/*	public int CheckIfBRDExists(){
		int exists;
		String fileToCheck = "generated/"+tutorial.tutorialName+".brd";
		File f = new File(fileToCheck);
		  if(f.exists()){
			exists =  1;
			return exists;
		  }
		  else{
			exists =  0;
			return exists;
		  }
	}
*/
	private void ThenLabelDataListIsPopulated() {
		System.out.println(this.tutorial.getLabelDataList());
		assertTrue(this.tutorial.getLabelDataList().size() > 0);
	}

	private void ThenEdgeDataListIsPopulated() {
		assertTrue(this.tutorial.getEdgeDataList().size() > 0);
	}

	@Test
	public final void SplitLabelsAreDeclaredInitiatedAndAdded() {
		GivenValidLinesOfCode();
		GivenValidWordInfoList();
		WhenGetTutorialIsCalled();
		ThenLabelSyntaxHasPositiveIndices();
	}

	@Test
	public final void TextFieldHasPositiveWidthAndHigherXCoord() {
		GivenValidLinesOfCode();
		GivenValidWordInfoList();
		WhenGetTutorialIsCalled();
		ThenWidthIsPositive();
		ThenXCoordIsHigherThanPriorComponent();
	}

	@Test
	public void ButtonFieldIsAdded() {
		GivenValidLinesOfCode();
		GivenValidWordInfoList();
		WhenGetTutorialIsCalled();
		ThenDoneButtonSyntaxHasPositiveIndices();
		ThenButtonFieldHasProperBounds();
	}
	//END Tests
	
	private void GivenValidMultipleWordInfoList() {

		wordInfo.wordToBeBlanked = "out";
		wordInfo.lineNumber = 3;
		wordInfo.columnNumber = 7;
		wordInfo.blankType = BlankType.Text;
		wordInfoList.add(wordInfo);
		wordInfo = new WordInfo();
		wordInfo.wordToBeBlanked = "static";
		wordInfo.blankType = BlankType.Text;
		wordInfo.lineNumber = 2;
		wordInfo.columnNumber = 7;
		wordInfoList.add(wordInfo);
	}

	private void ThenButtonFieldHasProperBounds() {
		String subStringCode = tutorial.tutorialCode
				.substring(tutorial.tutorialCode
						.indexOf("doneButton.setBounds(")
						+ ("doneButton.setBounds(").length());
		subStringCode = subStringCode.substring(0,
				subStringCode.indexOf(')') - 1);
		_bounds = subStringCode.split(",");
		assertTrue(Integer.parseInt(_bounds[1]) > 20);
	}

	private void ThenDoneButtonSyntaxHasPositiveIndices() {
		assertTrue(tutorial.tutorialCode
				.indexOf("private pact.DorminWidgets.DorminButton doneButton;") > 0);
		assertTrue(tutorial.tutorialCode
				.indexOf("doneButton = new pact.DorminWidgets.DorminButton();") > 0);
		assertTrue(tutorial.tutorialCode.indexOf("add(doneButton);") > 0);
	}

	private void ThenXCoordIsHigherThanPriorComponent() {
		assertTrue(Integer.parseInt(_bounds[1]) > 20);
	}

	private void ThenWidthIsPositive() {
		String subStringCode = tutorial.tutorialCode
				.substring(tutorial.tutorialCode
						.indexOf("txtLine3Col7.setBounds(")
						+ ("txtLine3Col7.setBounds(").length());
		subStringCode = subStringCode.substring(0,
				subStringCode.indexOf(')') - 1);
		_bounds = subStringCode.split(",");
		assertTrue(Integer.parseInt(_bounds[2]) > 0);
	}

	private void ThenLabelSyntaxHasPositiveIndices() {
		assertTrue(tutorial.tutorialCode
				.indexOf("lblLine3Col7 = new pact.DorminWidgets.DorminLabel();") > 0);
		assertTrue(tutorial.tutorialCode
				.indexOf("private pact.DorminWidgets.DorminLabel lblLine3Col7;") > 0);
		assertTrue(tutorial.tutorialCode.indexOf("add(lblLine3Col7);") > 0);
	}

	private void ThenNumberofParanthesisIsBalanced() {
		System.out.println(tutorial.tutorialCode);
		ArrayList<Character> paranthesisStack = new ArrayList<Character>();
		char[] tutorialCodeArr = tutorial.tutorialCode.toCharArray();
		for (char currCharacter : tutorialCodeArr) {
			if (currCharacter == '{') {
				paranthesisStack.add(currCharacter);
			} else if (currCharacter == '}') {
				paranthesisStack.remove(paranthesisStack.size() - 1);
			}
		}
		assertEquals(0, paranthesisStack.size());
	}

	private void ThenReturnedTutorialIsNotNull() {
		assertNotNull(tutorial);
	}

	private void WhenGetTutorialIsCalled() {
		tutorial = tutorialPresenter.GetTutorial("System", linesOfCode,
				wordInfoList, "blank source");
	}

	private void GivenValidWordInfoList() {
		wordInfo.wordToBeBlanked = "out";
		wordInfo.lineNumber = 3;
		wordInfo.columnNumber = 7;
		wordInfo.blankType = BlankType.Text;
		wordInfoList.add(wordInfo);
	}

	private void GivenValidLinesOfCode() {
		linesOfCode.add("class myfirstjavaprog {");
		linesOfCode.add("public static void main(String args[]) {");
		linesOfCode.add("System.out.println(\"Hello World!\");");
		linesOfCode.add("}");
		linesOfCode.add("}");
	}

}
