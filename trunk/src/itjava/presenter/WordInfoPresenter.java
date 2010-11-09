package itjava.presenter;

import itjava.data.NodeToCompare;
import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.Repository;
import itjava.model.RepositoryStore;
import itjava.model.ResultEntry;
import itjava.model.TFIDFVector;
import itjava.model.WordInfo;
import itjava.model.WordInfoStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

	public HashMap<ArrayList<String>, ArrayList<WordInfo>> GenerateWordInfoMap() {
		_codeToWordInfoMap = new HashMap<ArrayList<String>, ArrayList<WordInfo>>();

		LinkedHashSet<CompilationUnitFacade> similarFacades = compilationUnitStore.FindSimilarCompilationUnits(compilationUnitFacadeList, this.getRepository(), 10);
		for (CompilationUnitFacade facade : similarFacades) {
			ArrayList<WordInfo> wordInfoList = new ArrayList<WordInfo>();
			
			ArrayList<String> topImports = facade.getTFVector().getSortedTerms(NodeToCompare.ImportDeclaration, 1);
			for (ImportDeclaration importDeclaration : facade.getImportDeclarations()) {
				if(topImports.contains(importDeclaration.getName().getFullyQualifiedName())) {
					try {
						wordInfoList.add(WordInfoStore.createWordInfo(facade.getLinesOfCode(), importDeclaration));
					}
					catch (Exception e) {
						System.err.println(e.getMessage() + "; Facade URL : " + facade.getUrl());
						continue;
					}
				}
			}
			
			ArrayList<String> topMethods = facade.getTFVector().getSortedTerms(NodeToCompare.MethodInvocation, 2);
			for (SimpleName methodInvocation : facade.getMethodInvocations()) {
				if (topMethods.contains(methodInvocation.getFullyQualifiedName())) {
					try {
						wordInfoList.add(WordInfoStore.createWordInfo(facade.getLinesOfCode(), methodInvocation));
					}
					catch (Exception e) {
						System.err.println(e.getMessage() + "; Facade URL : " + facade.getUrl());
						continue;
					}
				}
			}
			
			ArrayList<String> topClassInstances = facade.getTFVector().getSortedTerms(NodeToCompare.ClassInstanceCreator, 2);
			for (Type classInstance : facade.getClassInstances()) {
				if (topClassInstances.contains(classInstance.toString())) {
					try {
						wordInfoList.add(WordInfoStore.createWordInfo(facade.getLinesOfCode(), classInstance));
					}
					catch (Exception e) {
						System.err.println(e.getMessage() + "; Facade URL : " + facade.getUrl());
						continue;
					}
				}
			}
			if (wordInfoList.size() > 0) {
				_codeToWordInfoMap.put(facade.getLinesOfCode(), wordInfoList);
			}
		}
		return _codeToWordInfoMap;
	}

	public void AccessRepository(String query, ArrayList<ResultEntry> resultEntryList) {
		this.compilationUnitFacadeList = compilationUnitStore.createCompilationUnitFacadeList(query, resultEntryList);
		this.setRepository(RepositoryStore.UpdateRepository(compilationUnitFacadeList));
	}

}
