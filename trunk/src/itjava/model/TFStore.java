/**
 * 
 */
package itjava.model;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import itjava.data.NodeToCompare;
import itjava.data.TFVector;

/**
 * @author Aniket
 *
 */
public class TFStore {
	static TFVector _tfVector;

	public static TFVector GetTF(CompilationUnitFacade facade) {
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
	private static HashMap<String, Float> GetTF(CompilationUnitFacade facade, NodeToCompare nodeToCompare) {
		HashMap<String, Float> tfMap = new HashMap<String, Float>();
		switch (nodeToCompare) {
		case ImportDeclaration :
			List<ImportDeclaration> importDeclarations = facade.getImportDeclarations();
			for (ImportDeclaration importDeclaration : importDeclarations) {
				tfMap.put(importDeclaration.getName().getFullyQualifiedName(), (float) (1/importDeclarations.size()));
			}
			break;
			
		case SuperType :
			
			break;
			
		case VariableDeclaration : 
			List<Statement> statements = facade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT);
			float totalVariableDeclarations = statements.size();
			for (Statement statement : statements) {
				VariableDeclarationStatement variableDeclarationStatement = (VariableDeclarationStatement) statement;
				String variableType = variableDeclarationStatement.getType().toString();
				float variableCount = 1 + ((tfMap.get(variableType) == null) ? 0 : 1);
				tfMap.put(variableType, variableCount/totalVariableDeclarations);
			}
			break;
			
		case ClassInstanceCreator : 
			List<Type> classInstances = facade.getClassInstances();
			float totalClassInstances = classInstances.size();
			for (Type classInstance: classInstances) {
				float variableCount = 1 + ((tfMap.get(classInstance.toString()) == null) ? 0 : 1);
				tfMap.put(classInstance.toString(), variableCount/totalClassInstances);
			}
			break;
			
		case MethodInvocation : 
			List<SimpleName> methodInvocations = facade.getMethodInvocations();
			float totalMethodInvocations = methodInvocations.size();
			for (SimpleName methodInvocation : methodInvocations) {
				float variableCount = 1 + ((tfMap.get(methodInvocation.toString()) == null) ? 0 : 1);
				tfMap.put(methodInvocation.toString(), variableCount/totalMethodInvocations);
			}
			break;
			
		case PropertyAssignment : 
			
			break;
		}
		return tfMap;
	}
}
