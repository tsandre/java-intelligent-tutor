/**
 * 
 */
package itjava.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import itjava.data.NodeToCompare;

/**
 * @author Aniket
 *
 */
public class TFIDFStore {
	static TFIDFVector _tfVector;
	static Repository _repository;

	public static TFIDFVector GetTF(CompilationUnitFacade facade, Repository repository) {
		_repository = repository;
		if (facade.getTFVector() == null) {
			_tfVector = new TFIDFVector();
		}
		else {
			_tfVector = facade.getTFVector();
		}
		_tfVector.importDeclarationsTF = GetTF(facade, NodeToCompare.ImportDeclaration);
		_tfVector.superTypeTF = GetTF(facade, NodeToCompare.SuperType);
		_tfVector.variableDeclarationsTF = GetTF(facade, NodeToCompare.VariableDeclaration);
		_tfVector.classInstancesTF = GetTF(facade, NodeToCompare.ClassInstanceCreator);
		_tfVector.methodInvoationsTF = GetTF(facade, NodeToCompare.MethodInvocation);
		_tfVector.propertyAssignmentsTF = GetTF(facade, NodeToCompare.PropertyAssignment);
		return _tfVector;
	}

	/**
	 * Returns term-frequency map for a specified typeOfNode.
	 * The map can be read as <String "actualWord", Float "term frequency">
	 * @param CompilationUnitFacade facade
	 * @param NodeToCompare typeOfNode
	 */
	private static TreeMap<String,TFIDF> GetTF(CompilationUnitFacade facade, NodeToCompare nodeToCompare) {
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
				tfMap.put(entry.getKey(), new TFIDF(numOfOccurrences, totInstanceTermsInDoc,totDocs,entry.getValue()));
			}
			break;
			
		case MethodInvocation : 
			List<SimpleName> methodInvocations = facade.getMethodInvocations();
			ArrayList<String> methodTerms = new ArrayList<String>();
			for (SimpleName methodInvocation : methodInvocations) {
				methodTerms.add(methodInvocation.toString());
			}
			Collections.sort(methodTerms);
			int totMethodTermsInDoc = methodInvocations.size();
			for(Entry<String, Integer> entry: _repository.methodInvocationTerms.entrySet()) {
				String term = entry.getKey();
				int numOfOccurrences = (methodTerms.contains(term)) ? (methodTerms.lastIndexOf(term) - methodTerms.indexOf(term) + 1): 0;
				tfMap.put(entry.getKey(), new TFIDF(numOfOccurrences, totMethodTermsInDoc,totDocs,entry.getValue()));
			}
			break;
			
		case PropertyAssignment : 
			
			break;
		}
		return tfMap;
	}
	
}
