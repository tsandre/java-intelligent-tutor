package itjava.presenter;

import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.Repository;
import itjava.model.RepositoryStore;
import itjava.model.ResultEntry;
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
	public CompilationUnitStore compilationUnitStore;
	public ArrayList<CompilationUnitFacade> compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
	public boolean hasCommonNodes;
	private Repository _repository;
	
	public Repository getRepository() {
		return _repository;
	}

	public void setRepository(Repository repository) {
		_repository = repository;
	}

	public HashMap<ArrayList<String>, ArrayList<WordInfo>> getCodeToWordInfoMap() {
		return ( _codeToWordInfoMap == null ) ? (new HashMap<ArrayList<String>, ArrayList<WordInfo>>()) : _codeToWordInfoMap;
	}
	
	public void setCodeToWordInfoMap(
			HashMap<ArrayList<String>, ArrayList<WordInfo>> codeToWordInfoMap) {
		_codeToWordInfoMap = codeToWordInfoMap;
	}

	public WordInfoPresenter() {
		compilationUnitStore = new CompilationUnitStore();
	}

	public HashMap<ArrayList<String>, ArrayList<WordInfo>> GenerateWordInfoMap(
			String query, ArrayList<ResultEntry> resultEntryList) {
		compilationUnitFacadeList = compilationUnitStore.createCompilationUnitFacadeList(query, resultEntryList);
//		_codeToWordInfoMap = compilationUnitStore.FindCommonDeclaration(compilationUnitFacadeList);

		_repository = RepositoryStore.UpdateRepository(compilationUnitFacadeList);
		compilationUnitStore.FindSimilarCompilationUnits(compilationUnitFacadeList, _repository);
		
		return _codeToWordInfoMap;
	}

}
