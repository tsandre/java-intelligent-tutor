package itjava.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itjava.model.CompilationUnitFacade;
import itjava.model.ResultCodeStore;
import itjava.model.WordInfo;
import itjava.presenter.WordInfoPresenter;

import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Statement;
import org.junit.Before;
import org.junit.Test;

public class WordInfoPresenterTest {
	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	private WordInfoPresenter _wordInfoPresenter;
	String query;
	ArrayList<String> sourceCodes;

	@Before
	public final void setUp() {
		sourceCodes = new ArrayList<String>();
		_wordInfoPresenter = new WordInfoPresenter();
	}

	@Test
	public final void FacadeFindsCommonImports() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		ThenFacadeFindsCommonImportDeclarations();
	}

	@Test
	public final void GivenFacadesHaveCommonVariableDeclarations() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		Then2FacadesHaveCommonSocketVariables();
	}

	@Test
	public final void GivenFacadesHaveVariableDeclarations() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		Then2FacadesHaveVariables();
	}

	@Test
	public final void InvalidFileResultsToValidFacade() {
		GivenInvalidFile();
		WhenGetCodeInfoIsCalled();
		ThenFacadeHasMethods();
	}

	@Test
	public final void MultipleFacadesHaveFollowingDeclarations() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		ThenBothFacadesHaveImportDeclaration();
		ThenBothFacadesHaveClassModifiersAsPublic();
		ThenSecondFacadeHasSuperInterfaceType();
		ThenBothFacadeHasMethods();
	}

	@Test
	public final void SingleFacadeHasFollowingDeclarations() {
		GivenSingleFile();
		WhenGetCodeInfoIsCalled();
		ThenFacadeHasOneImportDeclaration();
		ThenFacadeHasClassModifierAsPublic();
		ThenFacadeHasSuperTypeAsSocket();
		ThenFacadeHasMethods();
	}

	@Test
	public final void WordInfoPresenterGeneratesNodes() {
		GivenSingleFile();
		WhenGetCodeInfoIsCalled();
		ThenFacadeListHasOneFacade();
	}

	private void GivenFiles() {
		sourceCodes.add(ResultCodeStore
				.FileToString("samples/UseThisForTestingFacade_1.java"));
		sourceCodes.add(ResultCodeStore
				.FileToString("samples/UseThisForTestingFacade_2.java"));
		sourceCodes.add(ResultCodeStore
				.FileToString("samples/UseThisForTestingFacade_3.java"));
	}

	private void GivenInvalidFile() {
		sourceCodes.add(ResultCodeStore
				.FileToString("samples/UseThisForTestingFacade_4.java"));
	}

	private void GivenSingleFile() {
		sourceCodes.add(ResultCodeStore
				.FileToString("samples/UseThisForTestingFacade_1.java"));
	}

	private void Then2FacadesHaveCommonSocketVariables() {
		assertTrue(_wordInfoPresenter.compilationUnitToVariableDeclarationsMap
				.size() > 1);
	}

	private void Then2FacadesHaveVariables() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT).size() > 0);
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(2)
				.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT).size() > 0);
	}

	private void ThenBothFacadeHasMethods() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getMethodDeclarations().size() > 0);
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(1)
				.getMethodDeclarations().size() > 0);
	}

	private void ThenBothFacadesHaveClassModifiersAsPublic() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getClassModifiers().get(0).toString()
				.equalsIgnoreCase("public"));
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(1)
				.getClassModifiers().get(0).toString()
				.equalsIgnoreCase("public"));
	}

	private void ThenBothFacadesHaveImportDeclaration() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getImportDeclarations().size() == 1);
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(1)
				.getImportDeclarations().size() == 2);
	}

	private void ThenFacadeFindsCommonImportDeclarations() {
		assertTrue(_wordInfoPresenter.compilationUnitToImportDeclarationsMap
				.size() > 1);
	}

	private void ThenFacadeHasClassModifierAsPublic() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getClassModifiers().get(0).toString()
				.equalsIgnoreCase("public"));
	}

	private void ThenFacadeHasMethods() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getMethodDeclarations().size() > 0);
	}

	private void ThenFacadeHasOneImportDeclaration() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getImportDeclarations().size() == 1);
	}

	private void ThenFacadeHasSuperTypeAsSocket() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getSuperTypes().get(0).toString().equalsIgnoreCase("socket"));
	}

	private void ThenFacadeListHasOneFacade() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.size() == 1);
	}

	private void ThenSecondFacadeHasSuperInterfaceType() {
		CompilationUnitFacade secondFacade = _wordInfoPresenter.compilationUnitFacadeList
				.get(1);
		List<SimpleType> interfaceList = secondFacade.getSuperInterfaces();

		assertTrue(interfaceList.get(0).toString().equalsIgnoreCase("ISocket"));
		assertTrue(interfaceList.get(1).toString()
				.equalsIgnoreCase("ITestInterface"));
	}

	private void WhenGetCodeInfoIsCalled() {
		_codeToWordInfoMap = _wordInfoPresenter.GetCodeToWordInfoMap(query,
				sourceCodes);
	}
}
