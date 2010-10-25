/**
 * 
 */
package itjava.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import itjava.model.Convertor;
import itjava.model.ResultEntry;
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
public class IntegrateWordInfoAndTutorial {
	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	private WordInfoPresenter _wordInfoPresenter;
	String query;
	ArrayList<ResultEntry> sourceCodes;
	ArrayList<Tutorial> tutorialList;
	TutorialPresenter tutorialPresenter;

	@Before
	public final void setUp() {
		sourceCodes = new ArrayList<ResultEntry>();
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
					"SampleGUI" + (i), entrySet.getKey(), entrySet.getValue(),
					"sourceUrl");
			tutorialList.add(tutorial);
			i++;
		}

	}

	private void GivenFiles() {
		try {
			sourceCodes.add(new ResultEntry(Convertor
					.FileToString("samples/UseThisForTestingFacade_1.java"),
					"url", 0));
			sourceCodes.add(new ResultEntry(Convertor
					.FileToString("samples/UseThisForTestingFacade_2.java"),
					"url", 0));
			sourceCodes.add(new ResultEntry(Convertor
					.FileToString("samples/UseThisForTestingFacade_3.java"),
					"url", 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void WhenWordInfoIsGenerated() {
		_codeToWordInfoMap = _wordInfoPresenter.GenerateWordInfoMap(query,
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
