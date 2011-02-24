package itjava.model;

import itjava.data.TermsDictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class CompilationUnitStore {
	private ASTParser _astParser;
	private CompilationUnit _compilationUnit;
	private List<Statement> _statements;
	private CompilationUnitFacade _facade;
	private List<CompilationUnitFacade> _facadeList;

	/**
	 * This is the method that accepts a list of {@link ResultEntry} objects.
	 * Processes the Text of each {@link ResultEntry} object in 3 steps: <br /><ol>
	 * <li>Removes the code if it is blank or does not contain a single semicolon(;)</li>
	 * <li>Initializes an AST Parser as a complete COMPILATION UNIT. This fails if the code snippet cannot be compiled 
	 * as an entire atomic unit.</li>
	 * <li>This step gets executed only if the last one failed. AST parser is initialized in form of a group of STATEMENTS.</li>
	 * </ol>
	 * @param resultEntryList
	 * @return ArrayList<{@link CompilationUnitFacade}> 
	 */
	public ArrayList<CompilationUnitFacade> createCompilationUnitFacadeList(
			ArrayList<ResultEntry> resultEntryList) {
		ArrayList<CompilationUnitFacade> compilationUnitFacadeList = new ArrayList<CompilationUnitFacade>();
		int badCode = 0;
		int rejectedByCU = 0;
		int rejectedByBlock = 0;
		for (ResultEntry resultEntry : resultEntryList) {
			if (resultEntry.text.trim().equals("") || !resultEntry.text.contains(";")) {
				System.err.println("bad code : \n" + resultEntry.text + "\n--------");
				badCode++;
				continue;
			}
			do {
				_astParser = InitParser(ASTParser.K_COMPILATION_UNIT, resultEntry.text.toCharArray());
				CompilationUnit cUnit = (CompilationUnit)_astParser.createAST(null);
				Message[] compilerMessages = cUnit.getMessages();
				if (cUnit.toString() != null && cUnit.toString().length() > 0 && cUnit.types().size() > 0) {
					try {
//					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(cUnit, cUnit.toString());
					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(cUnit, resultEntry.text);
					compilationUnitFacade.setMessages(compilerMessages);
					compilationUnitFacade.setUrl(resultEntry.url);
					compilationUnitFacade.setInterpretedCode(cUnit.toString());
					compilationUnitFacadeList.add(compilationUnitFacade);
					}
					catch(IOException e) {
						System.err.println("Problem setting lines of code.." + e.getMessage());
					}
					catch (Exception e) {
						e.printStackTrace();
						System.err.println("Problem creating valid facade..");
					}
					break;
				}
//				System.err.println("Rejected program : " + resultEntry.text);
				rejectedByCU++;
				_astParser = InitParser(ASTParser.K_STATEMENTS, resultEntry.text.toCharArray());
				Block block = (Block)_astParser.createAST(null);
				if (block != null
						&& block.toString().trim().length() > 0
						&& block.statements().size() > 1) {
					try {
					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(block, resultEntry.text);
//					CompilationUnitFacade compilationUnitFacade = createCompilationUnitFacade(block, block.toString());
					compilationUnitFacade.setUrl(resultEntry.url);
					compilationUnitFacade.setMessages(compilerMessages);
					compilationUnitFacade.setInterpretedCode(block.toString());
					compilationUnitFacadeList.add(compilationUnitFacade);
					}
					catch(IOException e) {
						System.err.println("Problem setting lines of code.." + e.getMessage());
					}
					catch (Exception e) {
						e.printStackTrace();
						System.err.println("Problem creating valid facade..");
					}
					break;
				}
				rejectedByBlock++;
			}
			while (false);
		}
		System.err.println("Total result entries : " + resultEntryList.size());
		System.err.println("Bad Code : " + badCode);
		System.err.println("Rejected by CU : " + rejectedByCU);
		System.err.println("Rejected by Block : " + rejectedByBlock);
		System.err.println("Total facades : " + compilationUnitFacadeList.size());
		return compilationUnitFacadeList;
	}
	
	private CompilationUnitFacade createCompilationUnitFacade(
			CompilationUnit compilationUnit, String sourceCode) throws IOException {
				
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
				try {
					Block block = methodDeclaration.getBody();
					List<Statement> statements = block.statements();
					for (Statement statement : statements) {
						_facade.addStatement(statement);
					}
				}
				catch (Exception e) {
					/*System.err.println("Error fetching block/statement from : " + methodDeclaration.toString());
					System.err.println(methodDeclaration.getParent().toString());*/
				}
			}
		}
		return _facade;
	}
	
	private CompilationUnitFacade createCompilationUnitFacade(Block block, String sourceCode) throws IOException  {
		_facade = new CompilationUnitFacade();
		_facade.setLinesOfCode(sourceCode);

		_statements = block.statements();
		for (Statement statement : _statements) {
			_facade.addStatement(statement);
		}
		return _facade;
	}
	
	/**
	 * Accepts the list of {@link CompilationUnitFacade} and {@link Repository} object required for 
	 * comparison. Also requires a number of results that are required. i.e. if numOfResults == 5, then 
	 * the number of results returned will be 5 (or in some cases 6).
	 * This method eliminates duplicate code snippets that are found in different URLs.
	 * @param compilationUnitFacadeList
	 * @param repository
	 * @param termsDictionary 
	 * @param numOfResults
	 * @return
	 */
	public LinkedHashSet<CompilationUnitFacade> FindSimilarCompilationUnits(ArrayList<CompilationUnitFacade> compilationUnitFacadeList, Repository repository, TermsDictionary termsDictionary, int numOfResults) {

/*		for (CompilationUnitFacade facade : compilationUnitFacadeList) {
			facade.setTFVector(repository);
			System.out.println("Source Example: " + facade.getUrl());
			System.out.println(facade.getLinesOfCode());
			System.out.println("Import Declarations: " + facade.getTFVector().importDeclarationsTF);
			System.out.println("Class Instances: " + facade.getTFVector().classInstancesTF);
			System.out.println("Method Invocations: " + facade.getTFVector().methodInvoationsTF);
			System.out.println("Variable Declarations: " + facade.getTFVector().variableDeclarationsTF);
			System.out.println("---------------");
		}*/
		for (CompilationUnitFacade facade : compilationUnitFacadeList) {
			facade.setTFVector(repository, termsDictionary);
		}
		RemoveDuplicateSnippets(compilationUnitFacadeList);
		Matrix matrix = new Matrix(compilationUnitFacadeList);
		for (CompilationUnitFacade x : compilationUnitFacadeList) {
			for (CompilationUnitFacade y : compilationUnitFacadeList) {
				if (!matrix.contains(y, x)) {
					matrix.setValues(x, y);
				}
			}	
		}
		LinkedHashSet<CompilationUnitFacade> tutorialReadyList = matrix.GetTopSimilar(numOfResults);
		return tutorialReadyList;
	}

	/**
	 * This function takes care of removing {@link CompilationUnitFacade} that have similar code but distinct URLs.
	 * @param compilationUnitFacadeList
	 * @return ArrayList of CompilationUnitFacade without very similar linesOfCode.
	 */
	private void RemoveDuplicateSnippets(ArrayList<CompilationUnitFacade> compilationUnitFacadeList) {
		System.err.println("Removing duplicates now..");
		ArrayList<String> cleanFacades = new ArrayList<String>();
		Iterator<CompilationUnitFacade> itFacades = compilationUnitFacadeList.iterator();
		while (itFacades.hasNext()) {
			CompilationUnitFacade facade = itFacades.next();
			if (facade.getLinesOfCode().size() < 4) {
				itFacades.remove();
			}
			else if (!facade.IsSimilarToOthersInList(cleanFacades)) {
				cleanFacades.add(facade.toString());
			}
			else {
				System.err.println("Duplicate code: " + facade.getUrl());
				itFacades.remove();
			}
		}
		System.err.println("Done removing duplicates..");
	}


	private static ASTParser InitParser(int kind, char[] source) {
		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(source);
		astParser.setKind(kind);
		return astParser;
	}

	/**
	 * Finds the word in importDeclarations OR methodInvocations OR classInstanceCreations
	 * for the current facade. If exists then returns the respective node. Else returns null. 
	 * @param facade
	 * @param newWord
	 * @return
	 */
	public static ASTNode findWordType(CompilationUnitFacade facade,
			String newWord) {

		List<SimpleName> methodInvocations = facade.getMethodInvocations();
		for (SimpleName methodInvocation: methodInvocations) {
			if (methodInvocation.getFullyQualifiedName().equals(newWord)){
				return methodInvocation;
			}
		}
		
		List<Type> classInstances = facade.getClassInstances();
		for (Type classInstance : classInstances) {
			if (classInstance.toString().equals(newWord)) {
				return classInstance;
			}
		}
		
		List<ImportDeclaration> imports = facade.getImportDeclarations();
		for (ImportDeclaration importDeclaration : imports) {
			if (importDeclaration.getName().getFullyQualifiedName().equals(newWord)) {
				return importDeclaration;
			}
		}
				
		return null;
	}
}
