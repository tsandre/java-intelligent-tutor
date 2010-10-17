/**
 * 
 */
package itjava.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import itjava.model.Convertor;
import itjava.model.Tutorial;
import itjava.model.WordInfo;
import itjava.presenter.TutorialPresenter;
import itjava.presenter.WordInfoPresenter;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Aniket
 * 
 */
public class IntegrationTest {
	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	private WordInfoPresenter _wordInfoPresenter;
	String query;
	ArrayList<String> sourceCodes;
	ArrayList<Tutorial> tutorialList;
	TutorialPresenter tutorialPresenter;

	@Before
	public final void setUp() {
		sourceCodes = new ArrayList<String>();
		_wordInfoPresenter = new WordInfoPresenter();
		tutorialList = new ArrayList<Tutorial>();
	}

	@Test
	public void ResultFilesTOWordInfoTOTutorial() {
		GivenFiles();
		WhenWordInfoIsGenerated();
		WhenTutorialIsGenerated();
		ThenNumberofParanthesisIsBalanced();

	}

	private void WhenTutorialIsGenerated() {
		int i = 0;
		for (Entry<ArrayList<String>, ArrayList<WordInfo>> entrySet : _codeToWordInfoMap
				.entrySet()) {
			tutorialPresenter = new TutorialPresenter();
			Tutorial tutorial = tutorialPresenter.GetTutorial(
					"SampleGUI" + (i), entrySet.getKey(), entrySet.getValue(), "sourceUrl");
			tutorialList.add(tutorial);
		}

	}

	private void GivenFiles() {
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_1.java"));
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_2.java"));
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_3.java"));
	}

	private void WhenWordInfoIsGenerated() {
		_codeToWordInfoMap = _wordInfoPresenter.GetCodeToWordInfoMap(query,
				sourceCodes);
	}

	private void ThenNumberofParanthesisIsBalanced() {
		for (int i = 0; i < tutorialList.size(); i++) {
			System.out.println(tutorialList.get(i).tutorialCode);
			ArrayList<Character> paranthesisStack = new ArrayList<Character>();
			char[] tutorialCodeArr = tutorialList.get(i).tutorialCode
					.toCharArray();
			for (char currCharacter : tutorialCodeArr) {
				if (currCharacter == '{') {
					paranthesisStack.add(currCharacter);
				} else if (currCharacter == '}') {
					paranthesisStack.remove(paranthesisStack.size() - 1);
				}
			}
			assertEquals(0, paranthesisStack.size());
		}

	}

}
