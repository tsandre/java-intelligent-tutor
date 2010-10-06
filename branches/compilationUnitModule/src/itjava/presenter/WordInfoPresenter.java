package itjava.presenter;

import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.WordInfo;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class WordInfoPresenter {

	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	public ArrayList<CompilationUnitFacade> compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
	private ASTParser _astParser;

	public WordInfoPresenter() {
		_astParser = ASTParser.newParser(AST.JLS3);
		_astParser.setKind(ASTParser.K_COMPILATION_UNIT);
	}

	public HashMap<ArrayList<String>, ArrayList<WordInfo>> GetCodeToWordInfoMap(
			String query, ArrayList<String> resultEntryList) {

		for (String currFile : resultEntryList) {
			_astParser.setSource(currFile.toCharArray());
			CompilationUnitFacade compilationUnit = CompilationUnitStore
					.createCompilationUnitFacade((CompilationUnit) _astParser
							.createAST(null));
			compilationUnitFacadeList.add(compilationUnit);
		}
		FindCommonNodes(compilationUnitFacadeList);
		return _codeToWordInfoMap;
	}

	private void FindCommonNodes(
			ArrayList<CompilationUnitFacade> compilationUnitList) {
		// TODO Find the common nodes in compilationUnitList and update the
		// hashMap _codeToWordInfoMap

	}

}
