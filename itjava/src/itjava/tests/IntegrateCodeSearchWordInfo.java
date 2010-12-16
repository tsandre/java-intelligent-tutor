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
	ArrayList<Tutorial> _tutorialList;

	@Test
	public void IntegrateCodeSearchWordInfoTest() {
		GivenQuery();
		WhenRepositoryIsGenerated();
		WhenWordInfoIsGeneratedFromCodeSearchResults();
//		WhenTutorialIsGenerated();
	}
	
	private void WhenRepositoryIsGenerated() {
		_wordInfoPresenter.SetCompilationUnitListAndAccessRepository(_query, _codeSearchPresenter.SearchNext());
	}

	private void GivenQuery() {
		_query = "ProcessBuilder java example";
		_codeSearchPresenter = new CodeSearchPresenter(_query);
		_wordInfoPresenter = new WordInfoPresenter();
	}
	
	private void WhenWordInfoIsGeneratedFromCodeSearchResults() {
		_tutorialList = _wordInfoPresenter.GenerateWordInfoMap();
	}
	
	/*private void WhenTutorialIsGenerated() {
		int i = 0; 
		Tutorial tutorial = null;
		tutorialList = new ArrayList<Tutorial>();
		for (Entry<ArrayList<String>, ArrayList<WordInfo>> entrySet : _codeToWordInfoMap.entrySet()) {
			_tutorialPresenter = new TutorialPresenter();
			try {
				tutorial = _tutorialPresenter
						.GetTutorial("SampleGUI" + (i), entrySet.getKey(), entrySet.getValue(), "sourceUrl");
				System.out.println(tutorial.tutorialCode);
				System.out.println("----------------------------------");
				tutorialList.add(tutorial);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error creating tutorial GUI");
			}
			i++;
		}

	}*/
}
