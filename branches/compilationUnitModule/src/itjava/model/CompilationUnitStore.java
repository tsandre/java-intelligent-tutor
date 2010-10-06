package itjava.model;

import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class CompilationUnitStore {
	private static CompilationUnit _compilationUnit;
	private static CompilationUnitFacade facade;
	
	@SuppressWarnings("unchecked")
	public static CompilationUnitFacade createCompilationUnitFacade(
			CompilationUnit compilationUnit) {
		
		facade = new CompilationUnitFacade();
		_compilationUnit = compilationUnit;
		facade.setImportDeclarations(_compilationUnit.imports());
		List<AbstractTypeDeclaration> types = _compilationUnit.types();
		for (AbstractTypeDeclaration abstractTypeDeclaration : types) {
			TypeDeclaration currTypeDeclaration = (TypeDeclaration) abstractTypeDeclaration;
			facade.addAllFields(currTypeDeclaration.getFields());
			facade.addAllMethods(currTypeDeclaration.getMethods());
			facade.addAllSuperInterfaces(currTypeDeclaration.superInterfaceTypes());
			facade.addAllClassModifiers(currTypeDeclaration.modifiers());
			facade.addSuperTypes(currTypeDeclaration.getSuperclassType());
			facade.addAllSuperInterfaces(currTypeDeclaration.superInterfaceTypes());
			
			for (MethodDeclaration methodDeclaration : currTypeDeclaration.getMethods()) {
				facade.addMethodDeclaration(methodDeclaration);
				List<Statement> statements = methodDeclaration.getBody().statements();
				for(Statement statement : statements) {
//					    IfStatement
//					    ForStatement
//					    EnhancedForStatement
//					    WhileStatement
//					    DoStatement
//					    TryStatement
//					    SwitchStatement
//					    SynchronizedStatement
//					    ReturnStatement
//					    ThrowStatement
//					    BreakStatement
//					    ContinueStatement
//					    EmptyStatement
//					    ExpressionStatement
//					    LabeledStatement
//					    AssertStatement
//					    VariableDeclarationStatement
//					    TypeDeclarationStatement
//					    ConstructorInvocation
//					    SuperConstructorInvocation
				}
			}
		}
		return facade;
	}

}
