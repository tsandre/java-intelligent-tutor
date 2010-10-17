package itjava.presenter;

import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.WordInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;

public class WordInfoPresenter {

	private HashMap<ArrayList<String>, ArrayList<WordInfo>> _codeToWordInfoMap;
	
	public HashMap<ArrayList<String>, ArrayList<WordInfo>> getCodeToWordInfoMap() {
		return ( _codeToWordInfoMap == null ) ? (new HashMap<ArrayList<String>, ArrayList<WordInfo>>()) : _codeToWordInfoMap;
	}
	
	public void setCodeToWordInfoMap(
			HashMap<ArrayList<String>, ArrayList<WordInfo>> codeToWordInfoMap) {
		_codeToWordInfoMap = codeToWordInfoMap;
	}

	public ArrayList<CompilationUnitFacade> compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
	private ASTParser _astParser;
	
	public boolean hasCommonNodes;
	
	public HashMap<CompilationUnitFacade, ArrayList<WordInfo>> totalHashMap;


	public WordInfoPresenter() {
		totalHashMap = new HashMap<CompilationUnitFacade, ArrayList<WordInfo>>();

	}

	private static ASTParser InitParser(int kind, char[] source) {
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(source);
		astParser.setStatementsRecovery(true);
		astParser.setKind(kind);
		return astParser;
	}
	
	public HashMap<ArrayList<String>, ArrayList<WordInfo>> GetCodeToWordInfoMap(
			String query, ArrayList<String> resultEntryList) {

		for (String currFile : resultEntryList) {
			
			do {
				_astParser = InitParser(ASTParser.K_COMPILATION_UNIT, currFile.toCharArray());
				CompilationUnit cUnit = (CompilationUnit)_astParser.createAST(null);
				if (cUnit.toString() != null && cUnit.toString().length() > 0) {
					CompilationUnitFacade compilationUnitFacade = CompilationUnitStore
							.createCompilationUnitFacade(cUnit, currFile);
					compilationUnitFacadeList.add(compilationUnitFacade);
					break;
				}
				_astParser = InitParser(ASTParser.K_STATEMENTS, currFile.toCharArray());
				Block block = (Block)_astParser.createAST(null);
				if (block.toString() != null
						&& block.toString().length() > 0) {
					CompilationUnitFacade compilationUnitFacade = CompilationUnitStore
							.createCompilationUnitFacade(block, currFile);
					compilationUnitFacadeList.add(compilationUnitFacade);
					break;
				}
			}
			while (false);
		}
		FindCommonNodes(compilationUnitFacadeList);
		return _codeToWordInfoMap;
	}

	private void FindCommonNodes(ArrayList<CompilationUnitFacade> compilationUnitList) {
		hasCommonNodes = CompilationUnitStore.FindCommonDeclaration(this);
	}

}
