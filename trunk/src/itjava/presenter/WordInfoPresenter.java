package itjava.presenter;

import itjava.data.NodeToCompare;
import itjava.data.TFVector;
import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.Repository;
import itjava.model.RepositoryStore;
import itjava.model.ResultEntry;
import itjava.model.WordInfo;
import itjava.model.WordInfoStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;

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
		_codeToWordInfoMap = new HashMap<ArrayList<String>, ArrayList<WordInfo>>();

		compilationUnitFacadeList = compilationUnitStore.createCompilationUnitFacadeList(query, resultEntryList);
		_repository = RepositoryStore.UpdateRepository(compilationUnitFacadeList);
		
		HashSet<CompilationUnitFacade> similarFacades = compilationUnitStore.FindSimilarCompilationUnits(compilationUnitFacadeList, _repository);
		for (CompilationUnitFacade facade : similarFacades) {
			ArrayList<WordInfo> wordInfoList = new ArrayList<WordInfo>();
			
			ArrayList<String> topImports = facade.getTFVector().getSortedTerms(NodeToCompare.ImportDeclaration, 1);
			for (ImportDeclaration importDeclaration : facade.getImportDeclarations()) {
				if(topImports.contains(importDeclaration.getName().getFullyQualifiedName())) {
					wordInfoList.add(WordInfoStore.createWordInfo(facade.getLinesOfCode(), importDeclaration));
				}
			}
			
			ArrayList<String> topMethods = facade.getTFVector().getSortedTerms(NodeToCompare.MethodInvocation, 2);
			for (SimpleName methodInvocation : facade.getMethodInvocations()) {
				if (topMethods.contains(methodInvocation.getFullyQualifiedName())) {
					wordInfoList.add(WordInfoStore.createWordInfo(facade.getLinesOfCode(), methodInvocation));
				}
			}
			
			ArrayList<String> topClassInstances = facade.getTFVector().getSortedTerms(NodeToCompare.ClassInstanceCreator, 2);
			for (Type classInstance : facade.getClassInstances()) {
				if (topClassInstances.contains(classInstance.toString())) {
					wordInfoList.add(WordInfoStore.createWordInfo(facade.getLinesOfCode(), classInstance));
				}
			}
			_codeToWordInfoMap.put(facade.getLinesOfCode(), wordInfoList);
		}
		return _codeToWordInfoMap;
	}

}
