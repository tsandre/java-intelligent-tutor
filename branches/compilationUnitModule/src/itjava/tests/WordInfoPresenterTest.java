package itjava.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itjava.model.CompilationUnitFacade;
import itjava.model.WordInfo;
import itjava.presenter.WordInfoPresenter;

import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Before;
import org.junit.Test;

public class WordInfoPresenterTest {
	String query;
	ArrayList<String> resultEntryList;
	private WordInfoPresenter _wordInfoPresenter;
	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;

	@Before
	public void setUp() {
		resultEntryList = new ArrayList<String>();
		_wordInfoPresenter = new WordInfoPresenter();
	}

	@Test
	public void WordInfoPresenterGeneratesNodes() {
		GivenSingleFile();
		WhenGetCodeInfoIsCalled();
		ThenFacadeListHasOneFacade();
	}
	
	@Test
	public void SingleFacadeHasFollowingDeclarations() {
		GivenSingleFile();
		WhenGetCodeInfoIsCalled();
		ThenFacadeHasOneImportDeclaration();
		ThenFacadeHasClassModifierAsPublic();
		ThenFacadeHasSuperTypeAsSocket();
		ThenFacadeHasMethods();
	}
	
	private void ThenFacadeHasMethods() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getMethodDeclarations().size() > 0);
	}

	@Test
	public void MultipleFacadesHaveFollowingDeclarations() {
		Given2Files();
		WhenGetCodeInfoIsCalled();
		ThenBothFacadesHaveImportDeclaration();
		ThenBothFacadesHaveClassModifiersAsPublic();
		ThenSecondFacadeHasSuperInterfaceType();
		ThenBothFacadeHasMethods();
		System.out.print(_wordInfoPresenter.compilationUnitFacadeList.get(2));
	}

	private void ThenBothFacadeHasMethods() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getMethodDeclarations().size() > 0);
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(1).getMethodDeclarations().size() > 0);
	}

	private void ThenSecondFacadeHasSuperInterfaceType() {
		CompilationUnitFacade secondFacade = _wordInfoPresenter.compilationUnitFacadeList.get(1);
		List<SimpleType> interfaceList = secondFacade.getSuperInterfaces();

		assertTrue(interfaceList.get(0).toString().equalsIgnoreCase("ISocket"));
		assertTrue(interfaceList.get(1).toString().equalsIgnoreCase("ITestInterface"));
	}

	private void ThenBothFacadesHaveClassModifiersAsPublic() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getClassModifiers().get(0).toString().equalsIgnoreCase("public"));
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(1).getClassModifiers().get(0).toString().equalsIgnoreCase("public"));
	}

	private void ThenBothFacadesHaveImportDeclaration() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getImportDeclarations().size() == 1);
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(1).getImportDeclarations().size() == 2);
	}

	private void Given2Files() {
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

	private void ThenFacadeHasSuperTypeAsSocket() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getSuperTypes().get(0).toString().equalsIgnoreCase("socket"));
	}

	private void ThenFacadeHasClassModifierAsPublic() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getClassModifiers().get(0).toString().equalsIgnoreCase("public"));
	}

	private void ThenFacadeHasOneImportDeclaration() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getImportDeclarations().size() == 1);
	}

	private void ThenFacadeListHasOneFacade() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.size() == 1);
	}

	private void WhenGetCodeInfoIsCalled() {
		_codeToWordInfoMap = _wordInfoPresenter.GetCodeToWordInfoMap(query,
				resultEntryList);
	}

	private void GivenSingleFile() {
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(
					"samples/UseThisForTestingFacade_1.java"));
			String currLine = bReader.readLine();
			String fileContent = "";
			while (currLine != null) {
				fileContent += currLine;
				currLine = bReader.readLine();
			}
			resultEntryList.add(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
