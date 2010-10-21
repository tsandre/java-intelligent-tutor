package itjava.tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itjava.model.CompilationUnitFacade;
import itjava.model.Convertor;
import itjava.model.WordInfo;
import itjava.presenter.WordInfoPresenter;

import org.eclipse.jdt.core.dom.ASTNode;
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
	
	@Test
	public final void GivenFacadesHaveCommonInitializerInFirstFacade() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		ThenFacade0HasCommonInitializers();
	}
	
	@Test
	public final void GivenFacadesHaveCommonMethodInvocationsFacade() {
		GivenFiles();
		WhenGetCodeInfoIsCalled();
		ThenFacade0And1HasCommonMethodInvocations();
	}
	
	@Test
	public final void DoWhileExpressionTest() {
		GivenFile("samples/UseThisForTestingFacade_5.java");
		WhenGetCodeInfoIsCalled();
		ThenStatementsInDoLoopAreNoted();
	}
	
	@Test
	public final void SwitchCaseTest() {
		GivenFile("samples/UseThisForTestingFacade_6.java");
		WhenGetCodeInfoIsCalled();
		ThenStatementsInSwitchCasesAreNoted();
	}
	

	private void ThenStatementsInSwitchCasesAreNoted() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getMethodInvocations().size() == 10);
	}

	private void ThenStatementsInDoLoopAreNoted() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getMethodInvocations().size() == 1);
	}

	private void GivenFile(String fileName) {
		sourceCodes.add(Convertor.FileToString(fileName));
	}

	private void ThenFacade0And1HasCommonMethodInvocations() {
		assertTrue(_wordInfoPresenter.totalHashMap.containsKey(_wordInfoPresenter.compilationUnitFacadeList.get(0)));
		assertTrue(_wordInfoPresenter.totalHashMap.containsKey(_wordInfoPresenter.compilationUnitFacadeList.get(1)));
		
		// TODO Need to be changed to find method common method declaration and then compare 
		//assertTrue(_wordInfoPresenter.totalHashMap.get(_wordInfoPresenter.compilationUnitFacadeList.get(0)).get(0).wordToBeBlanked.equals(_wordInfoPresenter.totalHashMap.get(_wordInfoPresenter.compilationUnitFacadeList.get(1)).get(0).wordToBeBlanked));
	}

	private void ThenFacade0HasCommonInitializers() {
		// TODO Need to be changed to find method common Initializer declaration and then compare 
		//assertTrue(_wordInfoPresenter.compilationUnitToInitializersMap.get(_wordInfoPresenter.compilationUnitFacadeList.get(0)).size() >= 2);
	}

	private void GivenFiles() {
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_1.java"));
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_2.java"));
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_3.java"));
	}

	private void GivenInvalidFile() {
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_4.java"));
	}

	private void GivenSingleFile() {
		sourceCodes.add(Convertor
				.FileToString("samples/UseThisForTestingFacade_1.java"));
	}

	private void Then2FacadesHaveCommonSocketVariables() {
		// TODO Need to be changed to find method common variable declaration and then compare 
		/*assertTrue(_wordInfoPresenter.compilationUnitToVariableDeclarationsMap
				.size() > 1);*/
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
		// TODO Need to be changed to find method common Import declaration and then compare 
		/*assertTrue(_wordInfoPresenter.compilationUnitToImportDeclarationsMap
				.size() > 1);*/
	}

	private void ThenFacadeHasClassModifierAsPublic() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0)
				.getClassModifiers().get(0).toString()
				.equalsIgnoreCase("public"));
	}

	private void ThenFacadeHasMethods() {
		assertTrue(_wordInfoPresenter.compilationUnitFacadeList.get(0).getStatements(ASTNode.VARIABLE_DECLARATION_STATEMENT).size() > 0);
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
