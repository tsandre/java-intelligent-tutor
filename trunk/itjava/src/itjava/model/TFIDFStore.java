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
	
}
