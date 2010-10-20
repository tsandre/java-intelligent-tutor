package itjava.model;

import itjava.presenter.WordInfoPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class CompilationUnitStore {
	private static CompilationUnit _compilationUnit;
	private static List<Statement> _statements;
	
	private static CompilationUnitFacade _facade;
	private static List<CompilationUnitFacade> _facadeList;
	
	public static CompilationUnitFacade createCompilationUnitFacade(
			CompilationUnit compilationUnit, String sourceCode) {
				
		_facade = new CompilationUnitFacade();
		_facade.setLinesOfCode(sourceCode);

		//	TODO	Is it possible to convert VariableDeclaration, FieldDeclaration &
		// 		VariableDeclarationStatement into one Type for facade
		_compilationUnit = compilationUnit;
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
				Block block = methodDeclaration.getBody();
				List<Statement> statements = block.statements();
				for (Statement statement : statements) {
					_facade.addStatement(statement);
				}
			}
		}
		return _facade;
	}
	
	public static CompilationUnitFacade createCompilationUnitFacade(Block block, String sourceCode) {
		_facade = new CompilationUnitFacade();
		_facade.setLinesOfCode(sourceCode);

		//	TODO	Is it possible to convert VariableDeclaration, FieldDeclaration &
		// 		VariableDeclarationStatement into one Type for facade
		
		_statements = block.statements();
		for (Statement statement : _statements) {
			_facade.addStatement(statement);
		}
		return _facade;
	}

	public static boolean FindCommonDeclaration(WordInfoPresenter wordInfoPresenter){
		_facadeList = wordInfoPresenter.compilationUnitFacadeList;
		HashMap<ArrayList<String>, ArrayList<WordInfo>> codeToWordInfoMap = wordInfoPresenter.getCodeToWordInfoMap();
		HashSet<String> commonImports = new HashSet<String> ();
		HashSet<String> commonVariableDeclarationStatements = new HashSet<String> ();
		HashSet<String> commonClassInstances = new HashSet<String>();
		HashSet<String> commonMethodInvocations = new HashSet<String>();
		
		boolean commonFound = false;
		HashMap<CompilationUnitFacade, ArrayList<WordInfo>> totalHashMap = wordInfoPresenter.totalHashMap;
		// TODO	Combine all the common stuff into 2 for loops
		//Find common imports
		HashSet<String> allImports = new HashSet<String>();
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
					ArrayList<WordInfo> previouslyFoundCommonImportWords = totalHashMap.get(currFacade);
					if(previouslyFoundCommonImportWords == null){
						previouslyFoundCommonImportWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonImportWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(),currImport));
					totalHashMap.put(currFacade, previouslyFoundCommonImportWords);
				}
			}
		}
		
		//imports section END
		
		//Find common Variable Declaration Types
		HashSet<String> allVariableDeclarations = new HashSet<String>();
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (Statement currVariableDeclarationStatement : currFacade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT)) {
				if (allVariableDeclarations.contains(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString())) {
					commonVariableDeclarationStatements.add(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString());
					commonFound = true;
				}
				allVariableDeclarations.add(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString());
			}
			for (FieldDeclaration currFieldDeclaration : currFacade.getFieldDeclarations()) {
				if (allVariableDeclarations.contains(((FieldDeclaration)currFieldDeclaration).getType().toString())) {
					commonVariableDeclarationStatements.add(((FieldDeclaration)currFieldDeclaration).getType().toString());
					commonFound = true;
				}
				allVariableDeclarations.add(((FieldDeclaration)currFieldDeclaration).getType().toString());
			}
			
		}
		
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (Statement currVariableDeclarationStatement : currFacade.getStatements(Statement.VARIABLE_DECLARATION_STATEMENT)) {
				if (commonVariableDeclarationStatements.contains(((VariableDeclarationStatement)currVariableDeclarationStatement).getType().toString())) {
					ArrayList<WordInfo> previouslyFoundCommonVariableDeclarationStatementWords = totalHashMap.get(currFacade);
					if(previouslyFoundCommonVariableDeclarationStatementWords == null){
						previouslyFoundCommonVariableDeclarationStatementWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonVariableDeclarationStatementWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(),currVariableDeclarationStatement));
					totalHashMap.put(currFacade, previouslyFoundCommonVariableDeclarationStatementWords);
				}
			}
			for (FieldDeclaration currFieldDeclaration : currFacade.getFieldDeclarations()) {
				if (commonVariableDeclarationStatements.contains(((FieldDeclaration)currFieldDeclaration).getType().toString())) {
					ArrayList<WordInfo> previouslyFoundCommonVariableDeclarationStatementWords = totalHashMap.get(currFacade);
					if(previouslyFoundCommonVariableDeclarationStatementWords == null){
						previouslyFoundCommonVariableDeclarationStatementWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonVariableDeclarationStatementWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(),currFieldDeclaration));
					totalHashMap.put(currFacade, previouslyFoundCommonVariableDeclarationStatementWords);
				}
			}
		}
		
		// Variable decl END
		
		//Find class instance creations
		HashSet<String> allClassInstances = new HashSet<String>();
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (Type currClassInstance : currFacade.getClassInstances()) {
				if (allClassInstances.contains(currClassInstance.toString())) {
					commonClassInstances.add(currClassInstance.toString());
					commonFound = true;
				}
				allClassInstances.add(currClassInstance.toString());
			}
		}
		
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (Type currClassInstance : currFacade.getClassInstances()) {
				if (commonClassInstances.contains(currClassInstance.toString())) {
					ArrayList<WordInfo> previouslyFoundCommonClassInstanceWords = totalHashMap.get(currFacade);
					if(previouslyFoundCommonClassInstanceWords == null){
						previouslyFoundCommonClassInstanceWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonClassInstanceWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(),currClassInstance));
					totalHashMap.put(currFacade, previouslyFoundCommonClassInstanceWords);
				}
			}
		}
		//class instance creations END
		
		//Find common method invocations
		HashSet<String> allMethodInvocations = new HashSet<String>();
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (SimpleName currMethodInvocation : currFacade.getMethodInvocations()) {
				System.out.println("Method.parent" + currMethodInvocation.getParent().toString());
				if (allMethodInvocations.contains(currMethodInvocation.toString())) {
					commonMethodInvocations.add(currMethodInvocation.toString());
					commonFound = true;
				}
				allMethodInvocations.add(currMethodInvocation.toString());
			}
		}
		
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (SimpleName currMethodInvocation : currFacade.getMethodInvocations()) {
				if (commonMethodInvocations.contains(currMethodInvocation.toString())) {
					ArrayList<WordInfo> previouslyFoundCommonMethodInvocationWords = totalHashMap.get(currFacade);
					if(previouslyFoundCommonMethodInvocationWords == null){
						previouslyFoundCommonMethodInvocationWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonMethodInvocationWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(), currMethodInvocation));
					totalHashMap.put(currFacade, previouslyFoundCommonMethodInvocationWords);
				}
			}
		}
		//Method Invocation END
		
		
		for (Entry<CompilationUnitFacade, ArrayList<WordInfo>> entrySet :  totalHashMap.entrySet()) {
			codeToWordInfoMap.put(entrySet.getKey().getLinesOfCode(), entrySet.getValue());
		}
		wordInfoPresenter.setCodeToWordInfoMap(codeToWordInfoMap);
		
		return commonFound;
	}
}
