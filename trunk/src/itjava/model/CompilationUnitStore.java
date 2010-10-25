package itjava.model;

import itjava.data.TFVector;
import itjava.presenter.WordInfoPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class CompilationUnitStore {
	private ASTParser _astParser;
	private static CompilationUnit _compilationUnit;
	private static List<Statement> _statements;
	private static CompilationUnitFacade _facade;
	private static List<CompilationUnitFacade> _facadeList;

	public ArrayList<CompilationUnitFacade> createCompilationUnitFacadeList(
			String query, ArrayList<ResultEntry> resultEntryList) {
		ArrayList<CompilationUnitFacade> compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
		for (ResultEntry resultEntry : resultEntryList) {
			
			do {
				_astParser = InitParser(ASTParser.K_COMPILATION_UNIT, resultEntry.text.toCharArray());
				CompilationUnit cUnit = (CompilationUnit)_astParser.createAST(null);
				if (cUnit.toString() != null && cUnit.toString().length() > 0 && cUnit.types().size() > 0) {
					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(cUnit, cUnit.toString());
//					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(cUnit, resultEntry.text);
					compilationUnitFacade.setUrl(resultEntry.url);
					compilationUnitFacadeList.add(compilationUnitFacade);
					break;
				}
				_astParser = InitParser(ASTParser.K_STATEMENTS, resultEntry.text.toCharArray());
				Block block = (Block)_astParser.createAST(null);
				if (block != null
						&& block.toString().trim().length() > 0
						&& block.statements().size() > 1) {
					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(block, resultEntry.text);
					compilationUnitFacade.setUrl(resultEntry.url);
					compilationUnitFacadeList.add(compilationUnitFacade);
					break;
				}
			}
			while (false);
		}
		return compilationUnitFacadeList;
	}
	
	private CompilationUnitFacade createCompilationUnitFacade(
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
	
	private CompilationUnitFacade createCompilationUnitFacade(Block block, String sourceCode) {
		_facade = new CompilationUnitFacade();
		_facade.setLinesOfCode(sourceCode);

		_statements = block.statements();
		for (Statement statement : _statements) {
			_facade.addStatement(statement);
		}
		return _facade;
	}
	
	public void FindSimilarCompilationUnits(ArrayList<CompilationUnitFacade> compilationUnitFacadeList, Repository repository) {
		for (CompilationUnitFacade facade : compilationUnitFacadeList) {
			facade.setTFVector(repository);
			System.out.println("Source Example: " + facade.getUrl());
			System.out.println(facade.getLinesOfCode());
			System.out.println("Class Instances: " + facade.getTFVector().classInstancesTF);
			System.out.println("Method Invocations: " + facade.getTFVector().methodInvoationsTF);
			System.out.println("Variable Declarations: " + facade.getTFVector().variableDeclarationsTF);
			System.out.println("---------------");
		}
		Matrix matrix = new Matrix(compilationUnitFacadeList);
		for (CompilationUnitFacade x : compilationUnitFacadeList) {
			for (CompilationUnitFacade y : compilationUnitFacadeList) {
				if (!matrix.contains(y, x)) {
					matrix.setValues(x, y);
				}
			}	
		}
		ArrayList<CompilationUnitFacade> tutorialReadyList = matrix.GetTopSimilar(10);
		
		//TODO HashMap<CompilationUnitFacade, ArrayList<WordInfo>> tutorialReadyMap = FindCosineSimilar(tfidfVectorList, 10);
	}

	public HashMap<ArrayList<String>,ArrayList<WordInfo>> FindCommonDeclaration(ArrayList<CompilationUnitFacade> compilationUnitFacadeList){
		_facadeList = compilationUnitFacadeList;
		HashMap<ArrayList<String>, ArrayList<WordInfo>> codeToWordInfoMap = new HashMap<ArrayList<String>, ArrayList<WordInfo>>();
		HashSet<String> commonImports = new HashSet<String> ();
		HashSet<String> commonVariableDeclarationStatements = new HashSet<String> ();
		HashSet<String> commonClassInstances = new HashSet<String>();
		HashSet<String> commonMethodInvocations = new HashSet<String>();
		HashSet<String> commonPropertyAssignments = new HashSet<String>();
		
		boolean commonFound = false;
		HashMap<CompilationUnitFacade, ArrayList<WordInfo>> totalHashMap = new HashMap<CompilationUnitFacade, ArrayList<WordInfo>>();
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
		
		/*
		 * Find common qualified Names : eg. char arr[] abc; abc.length;
		 * Here length is the one you are looking for.
		*/
		HashSet<String> allPropertyAssignments = new HashSet<String>();
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (QualifiedName currPropertyAssignment : currFacade.getQualifiedNames()) {
				if (allPropertyAssignments.contains(currPropertyAssignment.getName().toString())) {
					commonPropertyAssignments.add(currPropertyAssignment.getName().toString());
					commonFound = true;
				}
				allPropertyAssignments.add(currPropertyAssignment.getName().toString());
			}
		}
		
		for (CompilationUnitFacade currFacade : _facadeList) {
			for (QualifiedName currPropertyAssignment : currFacade.getQualifiedNames()) {
				if (commonPropertyAssignments.contains(currPropertyAssignment.toString())) {
					ArrayList<WordInfo> previouslyFoundCommonPropertyAssignmentWords = totalHashMap.get(currFacade);
					if(previouslyFoundCommonPropertyAssignmentWords == null){
						previouslyFoundCommonPropertyAssignmentWords = new ArrayList<WordInfo>();
					}
					previouslyFoundCommonPropertyAssignmentWords.add(WordInfoStore.createWordInfo(currFacade.getLinesOfCode(), currPropertyAssignment));
					totalHashMap.put(currFacade, previouslyFoundCommonPropertyAssignmentWords);
				}
			}
		}
		//qualified Names END
		
		
		for (Entry<CompilationUnitFacade, ArrayList<WordInfo>> entrySet :  totalHashMap.entrySet()) {
			codeToWordInfoMap.put(entrySet.getKey().getLinesOfCode(), entrySet.getValue());
		}
		
		return codeToWordInfoMap;
	}

	private static ASTParser InitParser(int kind, char[] source) {
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(source);
		astParser.setStatementsRecovery(true);
		astParser.setKind(kind);
		return astParser;
	}
}
