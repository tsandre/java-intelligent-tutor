package itjava.presenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import itjava.data.LocalMachine;
import itjava.model.BRDStore;
import itjava.model.Convertor;
import itjava.model.Tutorial;
import itjava.model.TutorialDeployer;
import itjava.model.TutorialStore;
import itjava.model.WordInfo;

public class TutorialPresenter {
private TutorialStore _tutorialStore;

	public Tutorial GetTutorial(String tutorialName, String readableName, ArrayList<String> exampleCode, ArrayList<WordInfo> wordInfoList, String sourceUrl) throws Exception {
		Tutorial tutorial = new Tutorial(tutorialName, readableName, ArrangeWordsAccordingToLineNumber(wordInfoList), Convertor.TrimArrayListOfString(exampleCode), sourceUrl);
		tutorial = new TutorialStore().GenerateTutorial(tutorial);
		return tutorial;
	}
	
	/**
	 * Accepts a List<> of {@link Tutorial} approved by the user and creates the CTAT-ready GUI file (i.e. tutorialName.java)
	 * Then compiles the .java file to create a .class file. Calls required routines to generate .brd file for each tutorial.
	 * And finally, deploys the CTAT tutor as web-start ready files.
	 * @param approvedTutorialList
	 * @return List of CTAT ready tutorials.
	 */
	public ArrayList<Tutorial> GetFinalTutorialList(ArrayList<Tutorial> approvedTutorialList) {
		ArrayList<Tutorial> finalTutorialList = new ArrayList<Tutorial>();
		TutorialDeployer deployer = null;
		Runtime rt = Runtime.getRuntime();
		for (Tutorial approvedTutorial : approvedTutorialList) {
			try {
				_tutorialStore = new TutorialStore();
				Tutorial finalTutorial = _tutorialStore.GenerateTutorial(approvedTutorial);
				deployer = new TutorialDeployer(finalTutorial);
				CreateDeliveryFolder(approvedTutorial.getReadableName());
				deployer.Deploy(rt);
				finalTutorialList.add(finalTutorial);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return finalTutorialList;
	}

	private void CreateDeliveryFolder(String folderName) {
		String folderPath = LocalMachine.home + LocalMachine.webcontent + "delivery/" + folderName;
		File deliveryFolder = new File(folderPath);
		if (deliveryFolder.exists()) {
			deliveryFolder.delete();
		}
		if(deliveryFolder.mkdir()) {
			System.out.println("Delivery folder : " + folderPath + " created.");
		}
		else {
			System.out.println("Problems creating folder " + folderPath);
		}
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
