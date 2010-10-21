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
	private static ArrayList<String> _tempVariableDeclarations;
	private static ArrayList<String> _tempMethodInvocations;
	private static ArrayList<String> _tempClassInstances;

	/**
	 * Seeks original {@link Repository} by performing necessary I/O and updates
	 * it with the argument passed, i.e. {@link ArrayList}<
	 * {@link CompilationUnitFacade}>. This method will also update the
	 * {@link Repository} object with count of terms present in the
	 * {@link ArrayList}
	 * 
	 * @param compilationUnitFacadeList
	 * @return updated {@link Repository}
	 */
	public static Repository UpdateRepository(
			ArrayList<CompilationUnitFacade> compilationUnitFacadeList) {

		ReadRepository();
		for (CompilationUnitFacade facade : compilationUnitFacadeList) {
			InitRepositoryStore();
			UpdateImportTerms(facade.getImportDeclarations());
			// TODO UpdateSuperTypeTerms(facade.getSuperTypes());
			UpdateVariableDeclarationTerms(facade
					.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT));
			UpdateClassInstanceTerms(facade.getClassInstances());
			UpdateMethodInvocationTerms(facade.getMethodInvocations());
			// TODO UpdatePropertyAssignmentTerms(null);
			repository.allDocuments.add(facade);
		}
		WriteRepository(repository);
		return repository;
	}

	/**
	 * Reads original {@link Repository} saved in the system by performing I/O
	 * <p>
	 * {@code TODO Implement File IO or Database IO}
	 */
	public static void ReadRepository() {
		// TODO The repository data file or db table will contain URL of the
		// code files stored
		// on the system. This function will then create CompilationUnitFacade
		// for each of the
		repository = new Repository();
	}

	/**
	 * Saves the passed {@link Repository} into the system by necessary I/O
	 * operations.
	 * 
	 * @param repository
	 */
	public static void WriteRepository(Repository repository) {
		// TODO perform IO here.
	}

	private static void InitRepositoryStore() {
		_tempVariableDeclarations = new ArrayList<String>();
		_tempMethodInvocations = new ArrayList<String>();
		_tempClassInstances = new ArrayList<String>();
	}

	private static void UpdateImportTerms(
			List<ImportDeclaration> importDeclarations) {
		for (ImportDeclaration importDeclaration : importDeclarations) {
			String importTerm = importDeclaration.getName()
					.getFullyQualifiedName();
			int numOfDocsContainingTerm = 1;
			if (repository.importTerms.containsKey(importTerm)) {
				numOfDocsContainingTerm += repository.importTerms
						.get(importTerm);
			}
			repository.importTerms.put(importTerm, numOfDocsContainingTerm);
		}
	}

	private static void UpdateSuperTypeTerms(List<Type> superTypes) {
		// TODO Implement this method
	}

	private static void UpdateVariableDeclarationTerms(
			List<Statement> statements) {
		for (Statement statement : statements) {
			VariableDeclarationStatement variableDeclarationStatement = (VariableDeclarationStatement) statement;
			String variableTerm = variableDeclarationStatement.getType()
					.toString();
			if (!_tempVariableDeclarations.contains(variableTerm)) {
				_tempVariableDeclarations.add(variableTerm);
				int numOfDocsContainingTerm = 1;
				if (repository.variableDeclarationTerms
						.containsKey(variableTerm)) {
					numOfDocsContainingTerm += repository.variableDeclarationTerms
							.get(variableTerm);
				}
				repository.variableDeclarationTerms.put(variableTerm,
						numOfDocsContainingTerm);
			}
		}
	}

	private static void UpdateMethodInvocationTerms(
			List<SimpleName> methodInvocations) {
		for (SimpleName methodInvocation : methodInvocations) {
			String methodInvocationTerm = methodInvocation.toString();
			if (!_tempMethodInvocations.contains(methodInvocationTerm)) {
				_tempMethodInvocations.add(methodInvocationTerm);
				int numOfDocsContainingTerm = 1;
				if (repository.methodInvocationTerms
						.containsKey(methodInvocationTerm)) {
					numOfDocsContainingTerm += repository.methodInvocationTerms
							.get(methodInvocationTerm);
				}
				repository.methodInvocationTerms.put(methodInvocationTerm,
						numOfDocsContainingTerm);
			}
		}
	}

	private static void UpdateClassInstanceTerms(List<Type> classInstances) {
		for (Type classInstance : classInstances) {
			String classInstanceTerm = classInstance.toString();
			if (!_tempClassInstances.contains(classInstanceTerm)) {
				_tempClassInstances.add(classInstanceTerm);
				int numOfDocsContainingTerm = 1;
				if (repository.classInstanceTerms
						.containsKey(classInstanceTerm)) {
					numOfDocsContainingTerm += repository.classInstanceTerms
							.get(classInstanceTerm);
				}
				repository.classInstanceTerms.put(classInstanceTerm,
						numOfDocsContainingTerm);
			}
		}
	}

	private static void UpdatePropertyAssignmentTerms(
			List<Object> propertyAssignment) {
		// TODO Implement this method
	}

}
