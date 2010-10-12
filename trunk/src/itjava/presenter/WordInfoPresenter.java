package itjava.presenter;

import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.WordInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class WordInfoPresenter {

	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	public ArrayList<CompilationUnitFacade> compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
	private ASTParser _astParser;
	
	public boolean hasCommonNodes;
	
	public HashMap<CompilationUnitFacade, List<String>> compilationUnitToImportDeclarationsMap;
	public HashMap<CompilationUnitFacade, List<WordInfo>> compilationUnitToVariableDeclarationsMap;

	public WordInfoPresenter() {
		_astParser = ASTParser.newParser(AST.JLS3);
		_astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		compilationUnitToImportDeclarationsMap = new HashMap<CompilationUnitFacade, List<String>>();
		compilationUnitToVariableDeclarationsMap = new HashMap<CompilationUnitFacade, List<WordInfo>>();
		
	}

	public HashMap<ArrayList<String>, ArrayList<WordInfo>> GetCodeToWordInfoMap(
			String query, ArrayList<String> resultEntryList) {

		for (String currFile : resultEntryList) {
			_astParser.setSource(currFile.toCharArray());
			_astParser.setStatementsRecovery(true);
			CompilationUnitFacade compilationUnit = CompilationUnitStore
					.createCompilationUnitFacade((CompilationUnit) _astParser.createAST(null), currFile);
			compilationUnitFacadeList.add(compilationUnit);
		}
		FindCommonNodes(compilationUnitFacadeList);
		return _codeToWordInfoMap;
	}

	private void FindCommonNodes(ArrayList<CompilationUnitFacade> compilationUnitList) {
		hasCommonNodes = CompilationUnitStore.FindCommonDeclaration(this);
	}

}
