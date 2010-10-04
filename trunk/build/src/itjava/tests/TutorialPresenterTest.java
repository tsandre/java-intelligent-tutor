package itjava.tests;


import static org.junit.Assert.*;

import java.util.ArrayList;

import itjava.data.BlankType;
import itjava.model.Tutorial;
import itjava.model.WordInfo;
import itjava.presenter.TutorialPresenter;
import org.junit.Before;
import org.junit.Test;

public class TutorialPresenterTest{

	private WordInfo wordInfo;
	private ArrayList<WordInfo> wordInfoList;
	private ArrayList<String> linesOfCode;
	private TutorialPresenter tutorialPresenter;
	private Tutorial tutorial;
	private String[] _bounds;
	
	@Before
	public final void SetUp() {
		wordInfo = new WordInfo();
		
		wordInfoList = new ArrayList<WordInfo> ();
		
		linesOfCode = new ArrayList<String>();
		
		tutorialPresenter = new TutorialPresenter();
	}
	
	@Test
	public final void GetTutorialReturnsNullOnPassingInvalidParams() {
		wordInfo.wordToBeBlanked = "new";
		wordInfo.lineNumber = 2;
		wordInfo.blankType = BlankType.Text;
		wordInfo.columnNumber = 10;		
		wordInfoList.add(wordInfo);
		
		tutorial = tutorialPresenter.GetTutorial("SampleGUI", linesOfCode, wordInfoList);
		assertEquals(null, tutorial);
	}
	
	@Test
	public final void GetTutorialReturnsNotNullTutorial () {
		GivenValidWordInfoList();
		GivenValidLinesOfCode();
		WhenGetTutorialIsCalled();
		ThenReturnedTutorialIsNotNull();
	}
	
	@Test
	public final void GetTutorialWithMultipleWordsReturnsNotNullTutorial () {
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
		String subStringCode = tutorial.tutorialCode.substring(tutorial.tutorialCode.indexOf("doneButton.setBounds(")+ ("doneButton.setBounds(").length());
		subStringCode = subStringCode.substring(0, subStringCode.indexOf(')') - 1);
		_bounds = subStringCode.split(",");
		assertTrue(Integer.parseInt(_bounds[1]) > 20);
	}

	private void ThenDoneButtonSyntaxHasPositiveIndices() {
		assertTrue(tutorial.tutorialCode.indexOf("private pact.DorminWidgets.DorminButton doneButton;") > 0);
		assertTrue(tutorial.tutorialCode.indexOf("doneButton = new pact.DorminWidgets.DorminButton();") > 0);
		assertTrue(tutorial.tutorialCode.indexOf("add(doneButton);") > 0);
	}

	private void ThenXCoordIsHigherThanPriorComponent() {
		assertTrue(Integer.parseInt(_bounds[1]) > 20);
	}

	private void ThenWidthIsPositive() {
		String subStringCode = tutorial.tutorialCode.substring(tutorial.tutorialCode.indexOf("txtLine3Col7_out.setBounds(")+ ("txtLine3Col7_out.setBounds(").length());
		subStringCode = subStringCode.substring(0, subStringCode.indexOf(')') - 1);
		_bounds = subStringCode.split(",");
		assertTrue(Integer.parseInt(_bounds[2]) > 0);
	}

	private void ThenLabelSyntaxHasPositiveIndices() {
		assertTrue(tutorial.tutorialCode.indexOf("lblLine3Col7_out = new pact.DorminWidgets.DorminLabel();") > 0);
		assertTrue(tutorial.tutorialCode.indexOf("private pact.DorminWidgets.DorminLabel lblLine3Col7_out;") > 0);
		assertTrue(tutorial.tutorialCode.indexOf("add(lblLine3Col7_out);") > 0);
	}

	private void ThenNumberofParanthesisIsBalanced() {
		System.out.println(tutorial.tutorialCode);
		ArrayList<Character> paranthesisStack = new ArrayList<Character> ();
		char[] tutorialCodeArr = tutorial.tutorialCode.toCharArray();
		for (char currCharacter:tutorialCodeArr) {
			if(currCharacter == '{') {
				paranthesisStack.add(currCharacter);
			}
			else if(currCharacter == '}') {
				paranthesisStack.remove(paranthesisStack.size() - 1);
			}
		}
		assertEquals(0, paranthesisStack.size());
	}

	private void ThenReturnedTutorialIsNotNull() {
		assertNotNull(tutorial);
	}

	private void WhenGetTutorialIsCalled() {
		tutorial = tutorialPresenter.GetTutorial("SystemClass", linesOfCode, wordInfoList);
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
