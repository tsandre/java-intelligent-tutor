/**
 * 
 */
package itjava.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import itjava.model.Tutorial;
import itjava.model.WordInfo;
import itjava.presenter.CodeSearchPresenter;
import itjava.presenter.TutorialPresenter;
import itjava.presenter.WordInfoPresenter;

import org.junit.Test;

/**
 * @author Aniket
 *
 */
public class IntegrateCodeSearchWordInfo {
	
	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	private WordInfoPresenter _wordInfoPresenter;
	private CodeSearchPresenter _codeSearchPresenter;
	private String _query;
	private TutorialPresenter _tutorialPresenter;
	ArrayList<Tutorial> tutorialList;

	@Test
	public void IntegrateCodeSearchWordInfoTest() {
		GivenQuery();
		WhenWordInfoIsGeneratedFromCodeSearchResults();
	//	WhenTutorialIsGenerated();
	}
	
	private void GivenQuery() {
		_codeToWordInfoMap = new HashMap<ArrayList<String>, ArrayList<WordInfo>>();
		_query = "Connect MySQL database java example";
		_codeSearchPresenter = new CodeSearchPresenter(_query);
		_wordInfoPresenter = new WordInfoPresenter();
	}
	
	private void WhenWordInfoIsGeneratedFromCodeSearchResults() {
		while (_codeToWordInfoMap.size() <= 10) {
			_codeToWordInfoMap.putAll(_wordInfoPresenter.GenerateWordInfoMap(_query, _codeSearchPresenter.SearchNext()));
		}
	}
	
	private void WhenTutorialIsGenerated() {
		int i = 0;
		for (Entry<ArrayList<String>, ArrayList<WordInfo>> entrySet : _codeToWordInfoMap
				.entrySet()) {
			_tutorialPresenter = new TutorialPresenter();
			Tutorial tutorial = _tutorialPresenter.GetTutorial(
					"SampleGUI" + (i), entrySet.getKey(), entrySet.getValue(), "sourceUrl");
			tutorialList.add(tutorial);
			i++;
		}

	}
}
