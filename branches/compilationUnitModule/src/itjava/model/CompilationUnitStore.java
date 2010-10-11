package itjava.model;

import itjava.presenter.WordInfoPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class CompilationUnitStore {
	private static CompilationUnit _compilationUnit;
	private static CompilationUnitFacade _facade;
	private static List<CompilationUnitFacade> _facadeList;
	
	public static CompilationUnitFacade createCompilationUnitFacade(
			CompilationUnit compilationUnit, String sourceCode) {
				
		_facade = new CompilationUnitFacade();
		_facade.setLinesOfCode(sourceCode);

		//	TODO	Is it possible to convert VariableDeclaration, FieldDeclaration &
		// 		VariableDeclarationStatement into one Type for facade
		_compilationUnit = compilationUnit;
		_compilationUnit.getCommentList();
		_facade.setImportDeclarations(_compilationUnit.imports());
		List<AbstractTypeDeclaration> types = _compilationUnit.types();
		for (AbstractTypeDeclaration abstractTypeDeclaration : types) {
			TypeDeclaration currTypeDeclaration = (TypeDeclaration) abstractTypeDeclaration;
			_facade.addAllFields(currTypeDeclaration.getFields());
			_facade.addAllMethods(currTypeDeclaration.getMethods());
			_facade.addAllSuperInterfaces(currTypeDeclaration.superInterfaceTypes());
			_facade.addAllClassModifiers(currTypeDeclaration.modifiers());
			_facade.addSuperTypes(currTypeDeclaration.getSuperclassType());
			_facade.addAllSuperInterfaces(currTypeDeclaration.superInterfaceTypes());
			
			for (MethodDeclaration methodDeclaration : currTypeDeclaration.getMethods()) {
				List<Statement> statements = methodDeclaration.getBody().statements();
				for (Statement statement : statements) {
					_facade.addStatement(statement);
				}
			}
		}
		return _facade;
	}

	public static boolean FindCommonDeclaration(WordInfoPresenter wordInfoPresenter){
		_facadeList = wordInfoPresenter.compilationUnitFacadeList;
		ArrayList<String> commonImports = new ArrayList<String> ();
		ArrayList<String> commonVariableDeclarationStatements = new ArrayList<String> ();
		boolean commonFound = false;
		
		// TODO	Combine all the common stuff into 2 for loops
		//Find common imports
		HashSet<String> allImports = new HashSet<String>();
		HashMap<CompilationUnitFacade, List<String>> compilationUnitToImportDeclarationsMap = wordInfoPresenter.compilationUnitToImportDeclarationsMap; 
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (ImportDeclaration currImport : currFacade.getImportDeclarations()) {
				if (allImports.contains(currImport.getName().getFullyQualifiedName())) {
					commonImports.add(currImport.getName().getFullyQualifiedName());
					commonFound = true;
				}
				allImports.add(currImport.getName().getFullyQualifiedName());
			}
		}
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (ImportDeclaration currImport : currFacade.getImportDeclarations()) {
				if (commonImports.contains(currImport.getName().getFullyQualifiedName())) {
					List<String> previouslyFoundCommonImportWords = compilationUnitToImportDeclarationsMap.get(currFacade);
					if(previouslyFoundCommonImportWords == null){
						previouslyFoundCommonImportWords = new ArrayList<String>();
					}
					previouslyFoundCommonImportWords.add(currImport.getName().getFullyQualifiedName());
					compilationUnitToImportDeclarationsMap.put(currFacade, previouslyFoundCommonImportWords);
				}
			}
		}
		
		//imports section END
		
		//Find common Variable Declarations
		HashSet<String> allVariableDeclarations = new HashSet<String>();
		HashMap<CompilationUnitFacade, List<WordInfo>> compilationUnitToVariableDeclarationsMap = wordInfoPresenter.compilationUnitToVariableDeclarationsMap; 
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (Statement currVariableDeclarationStatement : currFacade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT)) {
				if (allVariableDeclarations.contains(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString())) {
					commonVariableDeclarationStatements.add(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString());
					commonFound = true;
				}
				allVariableDeclarations.add(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString());
			}
		}
		
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (Statement currVariableDeclarationStatement : currFacade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT)) {
				if (commonVariableDeclarationStatements.contains(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString())) {
					List<WordInfo> previouslyFoundCommonVariableDeclarationStatementWords = compilationUnitToVariableDeclarationsMap.get(currFacade);
					if(previouslyFoundCommonVariableDeclarationStatementWords == null){
						previouslyFoundCommonVariableDeclarationStatementWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonVariableDeclarationStatementWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(),currVariableDeclarationStatement));
					compilationUnitToVariableDeclarationsMap.put(currFacade, previouslyFoundCommonVariableDeclarationStatementWords);
				}
			}
		}
		
		// Variable decl END
		
		return commonFound;
	}
}
