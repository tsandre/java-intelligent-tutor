package itjava.model;

import itjava.presenter.WordInfoPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CompilationUnitStore {
	private static CompilationUnit _compilationUnit;
	private static CompilationUnitFacade _facade;
	private static List<CompilationUnitFacade> _facadeList;

	@SuppressWarnings("unchecked")
	public static CompilationUnitFacade createCompilationUnitFacade(
			CompilationUnit compilationUnit, String sourceCode) {
				
		_facade = new CompilationUnitFacade();
		_facade.setLinesOfCode(sourceCode);

		_compilationUnit = compilationUnit;
		_facade.setImportDeclarations(_compilationUnit.imports());
		List<AbstractTypeDeclaration> types = _compilationUnit.types();
		for (AbstractTypeDeclaration abstractTypeDeclaration : types) {
			TypeDeclaration currTypeDeclaration = (TypeDeclaration) abstractTypeDeclaration;
			_facade.addAllFields(currTypeDeclaration.getFields());
			_facade.addAllMethods(currTypeDeclaration.getMethods());
			_facade.addAllSuperInterfaces(currTypeDeclaration
					.superInterfaceTypes());
			_facade.addAllClassModifiers(currTypeDeclaration.modifiers());
			_facade.addSuperTypes(currTypeDeclaration.getSuperclassType());
			_facade.addAllSuperInterfaces(currTypeDeclaration
					.superInterfaceTypes());
			

			for (MethodDeclaration methodDeclaration : currTypeDeclaration
					.getMethods()) {
				_facade.addMethodDeclaration(methodDeclaration);
				List<Statement> statements = methodDeclaration.getBody()
						.statements();
				for (Statement statement : statements) {
					// IfStatement
					// ForStatement
					// EnhancedForStatement
					// WhileStatement
					// DoStatement
					// TryStatement
					// SwitchStatement
					// SynchronizedStatement
					// ReturnStatement
					// ThrowStatement
					// BreakStatement
					// ContinueStatement
					// EmptyStatement
					// ExpressionStatement
					// LabeledStatement
					// AssertStatement
					// VariableDeclarationStatement
					// TypeDeclarationStatement
					// ConstructorInvocation
					// SuperConstructorInvocation
				}
			}
		}
		return _facade;
	}

	public static boolean FindCommonDeclaration(WordInfoPresenter wordInfoPresenter){
		_facadeList = wordInfoPresenter.compilationUnitFacadeList;
		ArrayList<String> commonImports = new ArrayList<String> ();
		
		//TODO	Find common imports
		HashSet<Name> allImports = new HashSet<Name>();
		HashMap<CompilationUnitFacade, List<String>> compilationUnitToImportDeclarationsMap = null; 
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (ImportDeclaration currImport : currFacade.getImportDeclarations()) {
				allImports.add(currImport.getName());
				if (allImports.contains(currImport.getName())) {
					commonImports.add(currImport.getName().getFullyQualifiedName());
				}
			}
		}
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (ImportDeclaration currImport : currFacade.getImportDeclarations()) {
				if (commonImports.contains(currImport.getName().getFullyQualifiedName())) {
					//TODO	Create List<WordInfo> and add it along with code of current facade to a HashMap
				}
			}
		}
		
		//imports section END
		
		return true;
	}
}
