package itjava.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class CompilationUnitFacade {
	private List<ImportDeclaration> _importDeclarations;
	private List<SimpleType> _superInterfaces;
	private List<Type> _superTypes;
	private List<Modifier> _modifiers;
	private List<VariableDeclaration> _variableDeclarations;
	private List<MethodDeclaration> _methodDeclarations;
	private List<FieldDeclaration> _fieldDeclarations;
	private ArrayList<String> _linesOfCode;
	private List<Statement> _statements;
	private List<Expression> _expressions;
	private List<Statement> _variableDeclarationStatements;
	private List<Statement> _typeDeclarationStatements;
	private List<Statement> _superConstructorInvocations;
	private List<Statement> _constructorInvocations;
	private List<Expression> _assignments;
	private List<SimpleName> _methodInvocations;
	private List<SingleVariableDeclaration> _caughtExceptions;
	private List<Type> _classInstances;

	public void setLinesOfCode(String linesOfCode) {
		_linesOfCode = Convertor.StringToArrayListOfStrings(linesOfCode);
	}

	public ArrayList<String> getLinesOfCode() {
		return _linesOfCode;
	}

	public CompilationUnitFacade() {
		_facadeList = wordInfoPresenter.compilationUnitFacadeList;
		_variableDeclarations = new ArrayList<VariableDeclaration>();
		_modifiers = new ArrayList<Modifier>();
		_superTypes = new ArrayList<Type>();
		_superInterfaces = new ArrayList<SimpleType>();
		_importDeclarations = new ArrayList<ImportDeclaration>();
		_methodDeclarations = new ArrayList<MethodDeclaration>();
		_fieldDeclarations = new ArrayList<FieldDeclaration>();
		_variableDeclarationStatements = new ArrayList<Statement>();
		_typeDeclarationStatements = new ArrayList<Statement>();
		_superConstructorInvocations = new ArrayList<Statement>();
		_constructorInvocations = new ArrayList<Statement>();
		_expressions = new ArrayList<Expression>();
		_assignments = new ArrayList<Expression>();
		_methodInvocations = new ArrayList<SimpleName>();
		_caughtExceptions = new ArrayList<SingleVariableDeclaration>();
		setClassInstances(new ArrayList<Type>());
	}

	public void addExpression(Expression expression) {
		if (expression != null) {
			switch (expression.getNodeType()) {    
//			Name
//		    IntegerLiteral (includes decimal, hex, and octal forms; and long)
//		    FloatingPointLiteral (includes both float and double)
//		    CharacterLiteral
//		    NullLiteral
//		    BooleanLiteral
//		    StringLiteral
//		    TypeLiteral
//		    ThisExpression
//		    SuperFieldAccess
//		    FieldAccess
//		    Assignment
			case (Expression.ASSIGNMENT) :
				addExpression(((Assignment)expression).getRightHandSide());
				break;
//		    ParenthesizedExpression
//		    ClassInstanceCreation
			case (Expression.CLASS_INSTANCE_CREATION):
				ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) expression;
				addClassInstances(classInstanceCreation.getType());
				break;
//		    ArrayCreation
//		    ArrayInitializer
//		    MethodInvocation
			case (Expression.METHOD_INVOCATION) :
				addMethodInvocation(((MethodInvocation)expression).getName());
				break;
//		    SuperMethodInvocation
//		    ArrayAccess
//		    InfixExpression
			case (Expression.INFIX_EXPRESSION) :
				//expression like: (a==b) :Do nothing
				break;
//		    InstanceofExpression
//		    ConditionalExpression
//		    PostfixExpression
//		    PrefixExpression
//		    CastExpression
//		    VariableDeclarationExpression
			default: 
				_expressions.add(expression);
				break;
			}
		}
	}
	
}
