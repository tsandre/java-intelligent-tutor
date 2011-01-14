/**
 * 
 */
package itjava.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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
	ArrayList<Tutorial> initTutorialList;
	private WordInfoPresenter _wordInfoPresenter;
	String query = "SampleGUI";
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
	public void ResultFilesTOWordInfoTOTutorial() throws Exception {
		GivenFiles();
		WhenWordInfoIsGenerated();
		WhenTutorialIsGenerated();
		ThenNumberofParanthesisIsBalanced();

	}
	
	@Test
	public void TestAllWordsAreAccessible() throws Exception {
	//	GivenFiles();
		GivenFileWithMultipleWordsOnOneLine();
		WhenWordInfoIsGenerated();
		WhenTutorialIsGenerated();
		ThenWordsInaccesibleMessageNotShown();
	}
	
	@Test
	public void TestEscapedQuotesHandled() throws Exception {
		GivenFileWithEscapedQuotes();
		WhenWordInfoIsGenerated();
		WhenTutorialIsGenerated();
		ThenTutorialDeployerSucceeds();
	}

	private void ThenTutorialDeployerSucceeds() {
		Tutorial generated = initTutorialList.get(0);
		WhenCompiled(generated);
		
	}

	private void ThenWordsInaccesibleMessageNotShown() {
		// TODO Check the System.err Stream for presence of the error message.
		
	}

	private void WhenTutorialIsGenerated() throws Exception {
		int i = 0;
		for (Tutorial initialTutorial : initTutorialList)
			try {
				{
					tutorialPresenter = new TutorialPresenter();
					Tutorial tutorial = tutorialPresenter.GetTutorial(initialTutorial.getTutorialName(),
							initialTutorial.getReadableName(), initialTutorial.getLinesOfCode(), initialTutorial.getWordInfoList(),
							"sourceUrl", null);
					tutorialList.add(tutorial);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void GivenFiles() {
		try {
			sourceCodes.add(new ResultEntry(Convertor
					.FileToString("samples/UseThisForTestingFacade_1.java"),
					"url1", 0));
			sourceCodes.add(new ResultEntry(Convertor
					.FileToString("samples/UseThisForTestingFacade_2.java"),
					"url2", 0));
			sourceCodes.add(new ResultEntry(Convertor
					.FileToString("samples/UseThisForTestingFacade_3.java"),
					"url3", 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void GivenFileWithEscapedQuotes() throws Exception {
		sourceCodes.add(new ResultEntry(Convertor
				.FileToString("samples/UseThisForTestingFacade_10.java"),
				"url111", 0));
	}
	
	private void GivenFileWithMultipleWordsOnOneLine() throws Exception {
		sourceCodes.add(new ResultEntry(Convertor
				.FileToString("samples/UseThisForTestingFacade_9.java"),
				"url5", 0));
	}

	private void WhenWordInfoIsGenerated() {
		_wordInfoPresenter.SetCompilationUnitListAndAccessRepository(query,
				sourceCodes);
		initTutorialList = _wordInfoPresenter.GenerateWordInfoMap();
	}
	
	private void WhenCompiled(Tutorial tutorial) {
		String CompilePath = "generated/" + tutorial.getTutorialName()
				+ ".java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, null, null, CompilePath);
		System.out.println("Compile result code = " + result);
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
