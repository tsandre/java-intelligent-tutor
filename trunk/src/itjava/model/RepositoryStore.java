/**
 * 
 */
package itjava.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @author Aniket
 *
 */
public class RepositoryStore {

	private static Repository repository;

	/**
	 * Seeks original {@link Repository} by performing necessary I/O and updates it with
	 * the argument passed, i.e. {@link ArrayList}<{@link CompilationUnitFacade}>. This method will also
	 * update the {@link Repository} object with count of terms present in the {@link ArrayList}
	 * @param compilationUnitFacadeList
	 * @return updated {@link Repository}
	 */
	public static Repository UpdateRepository(
			ArrayList<CompilationUnitFacade> compilationUnitFacadeList) {
		
		repository = ReadRepository();
		for(CompilationUnitFacade facade : compilationUnitFacadeList) {
			UpdateImportTerms(facade.getImportDeclarations());
			//TODO UpdateSuperTypeTerms(facade.getSuperTypes());
			UpdateVariableDeclarationTerms(facade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT));
			UpdateClassInstanceTerms(facade.getClassInstances());
			
		}
		
		return repository;
	}

	private static void UpdateImportTerms(List<ImportDeclaration> importDeclarations) {
		for (ImportDeclaration importDeclaration : importDeclarations) {
			String importTerm = importDeclaration.getName().getFullyQualifiedName();
			int numOfDocsContainingTerm = 1;
			if (repository.importTerms.containsKey(importTerm)) {
				numOfDocsContainingTerm += repository.importTerms.get(importTerm);
			}
			repository.importTerms.put(importTerm, numOfDocsContainingTerm);
		}
	}
	
	private static void UpdateSuperTypeTerms(List<Type> superTypes) {
		//TODO Implement this method
	}
	
	private static void UpdateVariableDeclarationTerms(List<Statement> statements) {
		for (Statement statement : statements) {
			VariableDeclarationStatement variableDeclarationStatement = (VariableDeclarationStatement) statement;
			String variableTerm = variableDeclarationStatement.getType().toString();
			int numOfDocsContainingTerm = 1;
			if (repository.variableDeclarationTerms.containsKey(variableTerm)) {
				numOfDocsContainingTerm += repository.variableDeclarationTerms.get(variableTerm);
			}
			repository.variableDeclarationTerms.put(variableTerm, numOfDocsContainingTerm);
		}
	}
	
	private static void UpdateMethodInvocationTerms(List<SimpleName> methodInvocations) {
		for (SimpleName methodInvocation: methodInvocations) {
			String methodInvocationTerm = methodInvocation.toString();
			int numOfDocsContainingTerm = 1;
			if (repository.classInstanceTerms.containsKey(methodInvocationTerm)) {
				numOfDocsContainingTerm += repository.classInstanceTerms.get(methodInvocationTerm);
			}
			repository.classInstanceTerms.put(methodInvocationTerm, numOfDocsContainingTerm);
		}
	}

	/**
	 * @return Original {@link Repository} saved in the system by performing I/O
	 * <p>{@code TODO Implement File IO or Database IO}
	 */
	public static Repository ReadRepository() {
		//TODO The repository data file or db table will contain URL of the code files stored 
		//on the system. This function will then create CompilationUnitFacade for each of the 
		Repository repository = new Repository();
		return repository;
	}

}
