/**
 * 
 */
package itjava.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import itjava.data.NodeToCompare;
import itjava.data.TermsDictionary;
import itjava.util.Concordance;

/**
 * @author Aniket
 *
 */
public class TFIDFStore {
	
	public static TFIDFVector GetTF(CompilationUnitFacade facade, 
			Repository repository,
			TermsDictionary newtermsDict) {
		
		TFIDFVector _tfVector = new TFIDFVector();

		_tfVector.importDeclarationsTF = GetTF(facade, NodeToCompare.ImportDeclaration, repository, newtermsDict);
		_tfVector.variableDeclarationsTF = GetTF(facade, NodeToCompare.VariableDeclaration, repository, newtermsDict);
		_tfVector.classInstancesTF = GetTF(facade, NodeToCompare.ClassInstanceCreator, repository, newtermsDict);
		_tfVector.methodInvoationsTF = GetTF(facade, NodeToCompare.MethodInvocation, repository, newtermsDict);
		return _tfVector;
	}

	/**
	 * Accepts a facade and repository object. Calls an internal method GetTF() for each type of Node.
	 * @param facade
	 * @param repository
	 * @return
	 */
	public static TFIDFVector GetTF(CompilationUnitFacade facade, Repository repository) {
		TFIDFVector _tfVector = new TFIDFVector();

		_tfVector.importDeclarationsTF = GetTF(facade, NodeToCompare.ImportDeclaration, repository);
		_tfVector.superTypeTF = GetTF(facade, NodeToCompare.SuperType, repository);
		_tfVector.variableDeclarationsTF = GetTF(facade, NodeToCompare.VariableDeclaration, repository);
		_tfVector.classInstancesTF = GetTF(facade, NodeToCompare.ClassInstanceCreator, repository);
		_tfVector.methodInvoationsTF = GetTF(facade, NodeToCompare.MethodInvocation, repository);
		_tfVector.propertyAssignmentsTF = GetTF(facade, NodeToCompare.PropertyAssignment, repository);
		return _tfVector;
	}
	
	private static TreeMap<String, TFIDF> GetTF(CompilationUnitFacade facade,
			NodeToCompare nodeToCompare, Repository repository,
			TermsDictionary newtermsDict) {
		
		TreeMap<String, TFIDF> tfMap = new TreeMap<String, TFIDF>();
		int totDocs = repository.allDocuments.size();
		TreeMap<String, Integer> terms = null;
		Concordance<String> dict = null;
		HashSet<String> facadeTerms = new HashSet<String>();
		switch (nodeToCompare) {
		case ImportDeclaration :
			terms = repository.importTerms;
			dict = newtermsDict.getImportDict();
			for (ImportDeclaration importTerm : facade.getImportDeclarations()) {
				facadeTerms.add(importTerm.getName().getFullyQualifiedName());
			}
			break;
		case MethodInvocation : 
			terms = repository.methodInvocationTerms;
			dict = newtermsDict.getMethodsDict();
			for (SimpleName methodInvocation : facade.getMethodInvocations()) {
				facadeTerms.add(methodInvocation.toString());
			}
			break;
		case VariableDeclaration : 
			terms = repository.variableDeclarationTerms;
			dict = newtermsDict.getVariablesDict();
			for (Statement statement : facade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT)) {
				facadeTerms.add(statement.toString());
			}
			break;
		case ClassInstanceCreator :
			terms = repository.classInstanceTerms;
			dict = newtermsDict.getInstancesDict();
			for (Type classInstance: facade.getClassInstances()) {
				facadeTerms.add(classInstance.toString());
			}
		}

		int totTermsInSearch = 0;
		for (int val : dict.values()) totTermsInSearch += val;
		
		for(Entry<String, Integer> repositoryEntry: terms.entrySet()) {
			String term = repositoryEntry.getKey();
			int numOfDocsWithTerm = repositoryEntry.getValue();
			int numOfOccurrences = (facadeTerms.contains(term)) ? dict.get(term) : 0;
			tfMap.put(term, new  TFIDF( numOfOccurrences, totTermsInSearch, totDocs, numOfDocsWithTerm));
		}
		
		return tfMap;
	}

	/**
	 * Returns term-frequency map for a specified typeOfNode.
	 * The map can be read as <String "actualWord", Float "term frequency">
	 * @param _repository 
	 * @param CompilationUnitFacade facade
	 * @param NodeToCompare typeOfNode
	 */
	private static TreeMap<String,TFIDF> GetTF(CompilationUnitFacade facade, NodeToCompare nodeToCompare, Repository _repository) {
		TreeMap<String, TFIDF> tfMap = new TreeMap<String, TFIDF>();
		int totDocs = _repository.allDocuments.size();
		switch (nodeToCompare) {
		case ImportDeclaration :
			List<ImportDeclaration> importDeclarations = facade.getImportDeclarations();
			ArrayList<String> importTerms = new ArrayList<String>();
			for (ImportDeclaration importDeclaration : facade.getImportDeclarations()) {
				importTerms.add(importDeclaration.getName().getFullyQualifiedName());
			}
			Collections.sort(importTerms);
			int totImportTermsInDoc = importDeclarations.size();
			for(Entry<String, Integer> entry: _repository.importTerms.entrySet()) {
				String term = entry.getKey();
				int numOfOccurrences = (importTerms.contains(term)) ? (importTerms.lastIndexOf(term) - importTerms.indexOf(term) + 1): 0;
				tfMap.put(entry.getKey(), new TFIDF(numOfOccurrences, totImportTermsInDoc,totDocs,entry.getValue()));
			}
			
			break;
			
		case SuperType :
			
			break;
			
		case VariableDeclaration : 
			List<Statement> statements = facade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT);
			ArrayList<String> variableTerms = new ArrayList<String>();
			for (Statement statement : statements) {
				variableTerms.add(((VariableDeclarationStatement) statement).getType().toString());
			}
			Collections.sort(variableTerms);
			int totVarTermsInDoc = statements.size();
			for(Entry<String, Integer> entry: _repository.variableDeclarationTerms.entrySet()) {
				String term = entry.getKey();
				int numOfOccurrences = (variableTerms.contains(term)) ? (variableTerms.lastIndexOf(term) - variableTerms.indexOf(term) + 1): 0;
				tfMap.put(entry.getKey(), new TFIDF(numOfOccurrences, totVarTermsInDoc,totDocs,entry.getValue()));
			}
			break;
			
		case ClassInstanceCreator : 
			List<Type> classInstances = facade.getClassInstances();
			ArrayList<String> classInstanceTerms = new ArrayList<String>();
			for (Type classInstance: classInstances) {
				classInstanceTerms.add(classInstance.toString());
			}
			Collections.sort(classInstanceTerms);
			int totInstanceTermsInDoc = classInstances.size();
			for(Entry<String, Integer> entry: _repository.classInstanceTerms.entrySet()) {
				String term = entry.getKey();
				int numOfOccurrences = (classInstanceTerms.contains(term)) ? (classInstanceTerms.lastIndexOf(term) - classInstanceTerms.indexOf(term) + 1): 0;
				tfMap.put(term, new TFIDF(numOfOccurrences, totInstanceTermsInDoc,totDocs,entry.getValue()));
			}
			break;
			
		case MethodInvocation : 
			ArrayList<String> methodTerms = new ArrayList<String>();
			for (SimpleName methodInvocation : facade.getMethodInvocations()) {
				methodTerms.add(methodInvocation.toString());
			}
			Collections.sort(methodTerms);
			int totMethodTermsInDoc = facade.getMethodInvocations().size();
			for(Entry<String, Integer> entry: _repository.methodInvocationTerms.entrySet()) {
				String term = entry.getKey();
				int numOfOccurrences = (methodTerms.contains(term)) ? (methodTerms.lastIndexOf(term) - methodTerms.indexOf(term) + 1): 0;
				tfMap.put(term, new TFIDF(numOfOccurrences, totMethodTermsInDoc,totDocs,entry.getValue()));
			}
			break;
			
		case PropertyAssignment : 
			
			break;
		}
		return tfMap;
	}
	
/*	public static TermsDictionary CreateDictionaries(ArrayList<CompilationUnitFacade> facadeList){
		TermsDictionary dict = new TermsDictionary();
		for (CompilationUnitFacade facade: facadeList) {
			if (facade.getMethodInvocations() != null || facade.getMethodInvocations().size() > 0) {
				for (SimpleName method : facade.getMethodInvocations()) {
					dict.putMethods(method.toString());
				}
			}
			if (facade.getClassInstances() != null || facade.getClassInstances().size() > 0) {
				for (Type classInstance : facade.getClassInstances()) {
					dict.putInstances(classInstance.toString());					
				}
			}
			if (facade.getImportDeclarations() != null || facade.getImportDeclarations().size() > 0) {
				for (ImportDeclaration importDeclaration : facade.getImportDeclarations()) {
					dict.putImports(importDeclaration.getName().getFullyQualifiedName());
				}
			}
			List<Statement> stmts = facade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT);
			if (stmts != null || stmts.size() > 0) {				
				for (Statement statement : stmts) {
					dict.putVariables(((VariableDeclarationStatement) statement).getType().toString());
				}
			}
		}
		return dict;
	}*/
	
}
