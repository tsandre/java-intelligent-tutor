package itjava.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import itjava.model.Convertor;
import itjava.model.Tutorial;
import itjava.model.TutorialStore;
import itjava.model.WordInfo;

public class TutorialPresenter {
private TutorialStore _tutorialStore;

	public TutorialPresenter() {
		_tutorialStore = new TutorialStore();
	}

	public Tutorial GetTutorial(String tutorialName, String readableName, ArrayList<String> exampleCode, ArrayList<WordInfo> wordInfoList, String sourceUrl) {
		Tutorial tutorial = new Tutorial(tutorialName, readableName, ArrangeWordsAccordingToLineNumber(wordInfoList), Convertor.TrimArrayListOfString(exampleCode), sourceUrl);
		return _tutorialStore.GenerateTutorial(tutorial);
	}
	
	public ArrayList<Tutorial> GetFinalTutorialList(ArrayList<Tutorial> approvedTutorialList) {
		ArrayList<Tutorial> finalTutorialList = new ArrayList<Tutorial>();
		for (Tutorial approvedTutorial : approvedTutorialList) {
			finalTutorialList.add(_tutorialStore.GenerateTutorial(approvedTutorial));
		}
		return finalTutorialList;
	}

	private ArrayList<WordInfo> ArrangeWordsAccordingToLineNumber(
			ArrayList<WordInfo> wordInfoList) {
		Collections.sort(wordInfoList, new WordInfoComparator());
		return wordInfoList;
	}
	
	static class WordInfoComparator implements Comparator<WordInfo>{
		@Override
		public int compare(WordInfo w1, WordInfo w2) {
			return ((w1).lineNumber > (w2).lineNumber) ? 1 : 0;
		}
	}
	

}
