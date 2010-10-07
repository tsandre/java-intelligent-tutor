package itjava.tests;

import static org.junit.Assert.assertTrue;
import itjava.model.CompilationUnitStore;
import itjava.model.WordInfo;
import itjava.presenter.WordInfoPresenter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class CompilationUnitFacadeTest {

	ArrayList<String> resultEntryList;
	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	private WordInfoPresenter _wordInfoPresenter;
	String query;
	
	@Before
	public final void setUp() {
		resultEntryList = new ArrayList<String>();
		_wordInfoPresenter = new WordInfoPresenter();
	}
	
	@Test
	public final void FacadeFindsCommonImports() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		ThenFacadeFindsCommonImportDeclarations();
	}
	
	private void ThenFacadeFindsCommonImportDeclarations() {
		assertTrue(_wordInfoPresenter.hasCommonNodes);
	}

	private void GivenFiles() {
		BufferedReader bReader;
		try {
			bReader = new BufferedReader(new FileReader(
					"samples/UseThisForTestingFacade_1.java"));
			String currLine = bReader.readLine();
			String fileContent = "";
			while (currLine != null) {
				fileContent += currLine;
				currLine = bReader.readLine();
			}
			resultEntryList.add(fileContent);

			bReader = new BufferedReader(new FileReader(
					"samples/UseThisForTestingFacade_2.java"));
			currLine = bReader.readLine();
			fileContent = "";
			while (currLine != null) {
				fileContent += currLine;
				currLine = bReader.readLine();
			}
			resultEntryList.add(fileContent);
			
			bReader = new BufferedReader(new FileReader(
					"samples/UseThisForTestingFacade_3.java"));
			currLine = bReader.readLine();
			fileContent = "";
			while (currLine != null) {
				fileContent += currLine;
				currLine = bReader.readLine();
			}
			resultEntryList.add(fileContent);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void WhenGetCodeInfoIsCalled() {
		_codeToWordInfoMap = _wordInfoPresenter.GetCodeToWordInfoMap(query,
				resultEntryList);
	}
}
