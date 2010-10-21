/**
 * 
 */
package itjava.model;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import itjava.data.NodeToCompare;
import itjava.data.TFIDF;
import itjava.data.TFVector;

/**
 * @author Aniket
 *
 */
public class TFStore {
	static TFVector _tfVector;
	static Repository _repository;

	public static TFVector GetTF(CompilationUnitFacade facade, Repository repository) {
		_repository = repository;
		if (facade.getTFVector() == null) {
			_tfVector = new TFVector();
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
		switch (nodeToCompare) {
		case ImportDeclaration :
			for(String keyTerm: _repository.importTerms.keySet()) {
				tfMap.put(keyTerm, new TFIDF(0,0,0,0));
			}
			List<ImportDeclaration> importDeclarations = facade.getImportDeclarations();
			for (ImportDeclaration importDeclaration : importDeclarations) {
				String importTerm = importDeclaration.getName().getFullyQualifiedName();
				tfMap.put(importTerm, new TFIDF(1, importDeclarations.size(), _repository.importTerms.get(importTerm), _repository.allDocuments.size()));
			}
			break;
			
		case SuperType :
			
			break;
			
		case VariableDeclaration : 
			for(String keyTerm: _repository.variableDeclarationTerms.keySet()) {
				tfMap.put(keyTerm, new TFIDF(0,0,0,0));
			}
			List<Statement> statements = facade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT);
			float totalVariableDeclarations = statements.size();
			for (Statement statement : statements) {
				VariableDeclarationStatement variableDeclarationStatement = (VariableDeclarationStatement) statement;
				String variableType = variableDeclarationStatement.getType().toString();
				float variableCount = 1 + ((tfMap.get(variableType) == null) ? 0 : tfMap.get(variableType).GetNumOfOccurrences());
				tfMap.put(variableType, new TFIDF(variableCount, totalVariableDeclarations, _repository.variableDeclarationTerms.get(variableType), _repository.allDocuments.size()));
			}
			break;
			
		case ClassInstanceCreator : 
			for(String keyTerm: _repository.classInstanceTerms.keySet()) {
				tfMap.put(keyTerm, new TFIDF(0,0,0,0));
			}
			List<Type> classInstances = facade.getClassInstances();
			float totalClassInstances = classInstances.size();
			for (Type classInstance: classInstances) {
				float variableCount = 1 + ((tfMap.get(classInstance.toString()) == null) ? 0 : tfMap.get(classInstance.toString()).GetNumOfOccurrences());
				tfMap.put(classInstance.toString(), new TFIDF(variableCount, totalClassInstances, _repository.classInstanceTerms.get(classInstance.toString()), _repository.allDocuments.size()));
			}
			break;
			
		case MethodInvocation : 
			for(String keyTerm: _repository.methodInvocationTerms.keySet()) {
				tfMap.put(keyTerm, new TFIDF(0,0,0,0));
			}
			List<SimpleName> methodInvocations = facade.getMethodInvocations();
			float totalMethodInvocations = methodInvocations.size();
			for (SimpleName methodInvocation : methodInvocations) {
				float variableCount = 1 + ((tfMap.get(methodInvocation.toString()) == null) ? 0 : tfMap.get(methodInvocation.toString()).GetNumOfOccurrences());
				tfMap.put(methodInvocation.toString(), new TFIDF(variableCount, totalMethodInvocations, _repository.methodInvocationTerms.get(methodInvocation.toString()), _repository.allDocuments.size()));
			}
			break;
			
		case PropertyAssignment : 
			
			break;
		}
		return tfMap;
	}

}
