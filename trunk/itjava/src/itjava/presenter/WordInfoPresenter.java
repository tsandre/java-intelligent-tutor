package itjava.presenter;

import itjava.data.NodeToCompare;
import itjava.model.CompilationUnitFacade;
import itjava.model.CompilationUnitStore;
import itjava.model.Repository;
import itjava.model.RepositoryStore;
import itjava.model.ResultEntry;
import itjava.model.Tutorial;
import itjava.model.WordInfo;
import itjava.model.WordInfoStore;
import itjava.util.WordInfoComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;

public class WordInfoPresenter {

	public CompilationUnitStore compilationUnitStore;
	public ArrayList<CompilationUnitFacade> compilationUnitFacadeList;
	public boolean hasCommonNodes;
	private Repository _repository;
	private String _readableName;
	
	/**
	 * Getter method for {@link Repository} repository
	 * @return {@link Repository} repository
	 */
	public Repository getRepository() {
		return _repository;
	}

	/**
	 * Setter method for {@link Repository} repository
	 * @param repository
	 */
	public void setRepository(Repository repository) {
		_repository = repository;
	}

	/**
	 * Required method to utilize the {@link CompilationUnitStore} facilities.
	 * Only instantiates a new CompilationUnitStore.
	 */
	public WordInfoPresenter() {
		compilationUnitStore = new CompilationUnitStore();
		compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
	}

	/**
	 * Creates the {@link ArrayList} of {@link Tutorial}
	 * Seeks for top 10 similar {@link CompilationUnitFacade} out of the ones generated after the recent 
	 * Once similar CompilationUnitFacade s are found, it creates {@link WordInfo} by calling 
	 * createWordInfo method on {@link WordInfoStore}
	 * @return
	 */
	public ArrayList<Tutorial> GenerateWordInfoMap() {
		ArrayList<Tutorial> tutorialList = new ArrayList<Tutorial>();
		int tutorialNameIndex = 0;
		
		LinkedHashSet<CompilationUnitFacade> similarFacades = compilationUnitStore.FindSimilarCompilationUnits(compilationUnitFacadeList, this.getRepository(), 15);
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
			
			ArrayList<String> topMethods = facade.getTFVector().getSortedTerms(NodeToCompare.MethodInvocation, 5);
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
			
			ArrayList<String> topClassInstances = facade.getTFVector().getSortedTerms(NodeToCompare.ClassInstanceCreator, 3);
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
				Collections.sort(wordInfoList, new WordInfoComparator());
				tutorialList.add(new Tutorial("Example" + tutorialNameIndex, _readableName, wordInfoList, facade.getLinesOfCode(), facade.getUrl()));
				tutorialNameIndex++;
			}
		}
		return tutorialList;
	}

	/**
	 * Sets the {@link Repository} parameter of this object. 
	 * Calls {@link RepositoryStore} for this purpose.
	 * @param query : Sets the tutorialName.
	 * @param resultEntryList : {@link ArrayList}<{@link ResultEntry}> 
	 */
	public void SetCompilationUnitListAndAccessRepository(String query, ArrayList<ResultEntry> resultEntryList) {
		this.compilationUnitFacadeList = compilationUnitStore.createCompilationUnitFacadeList(query, resultEntryList);
		this.setRepository(RepositoryStore.UpdateRepository(compilationUnitFacadeList));
		_readableName = query;
		
	}

}
