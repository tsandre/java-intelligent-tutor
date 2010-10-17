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
	private List<Expression> _initializers;
	private List<SimpleName> _methodInvocations;
	private List<SingleVariableDeclaration> _caughtExceptions;

	public void setLinesOfCode(String linesOfCode) {
		_linesOfCode = Convertor.StringToArrayListOfStrings(linesOfCode);
	}

	public ArrayList<String> getLinesOfCode() {
		return _linesOfCode;
	}

	public CompilationUnitFacade() {

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
		_initializers = new ArrayList<Expression>();
		_methodInvocations = new ArrayList<SimpleName>();
		_caughtExceptions = new ArrayList<SingleVariableDeclaration>();
	}

	// VariableDeclarations
	public void setVariableDeclarations(
			List<VariableDeclaration> variableDeclarations) {
		if (variableDeclarations != null)
			_variableDeclarations = variableDeclarations;
	}

	public List<VariableDeclaration> getVariableDeclarations() {
		return _variableDeclarations;
	}

	// Class Modifiers
	public void setClassModifiers(List<Modifier> modifiers) {
		if (modifiers != null)
			_modifiers = modifiers;

	}

	public void addClassModifiers(Modifier modifier) {
		if (modifier != null)
			_modifiers.add(modifier);
	}

	public void addAllClassModifiers(List<Modifier> modifiers) {
		if (modifiers != null)
			_modifiers.addAll(modifiers);
	}

	public List<Modifier> getClassModifiers() {
		return _modifiers;
	}

	// SuperTypes
	public void setSuperTypes(List<Type> superTypes) {
		if (superTypes != null)
			_superTypes = superTypes;
	}

	public void addSuperTypes(Type type) {
		if (type != null)
			_superTypes.add(type);
	}

	public List<Type> getSuperTypes() {
		return _superTypes;
	}

	// SuperInterfaces
	public void setSuperInterfaces(List<SimpleType> superInterfaces) {
		if (superInterfaces != null)
			_superInterfaces = superInterfaces;
	}

	public void addSuperInterfaces(SimpleType superInterface) {
		if (superInterface != null)
			_superInterfaces.add(superInterface);
	}

	public void addAllSuperInterfaces(List<SimpleType> superInterfaceTypes) {
		if (superInterfaceTypes != null)
			_superInterfaces.addAll(superInterfaceTypes);
	}

	public List<SimpleType> getSuperInterfaces() {
		return _superInterfaces;
	}

	// ImportDeclarations
	public void setImportDeclarations(List<ImportDeclaration> imports) {
		if (imports != null)
			_importDeclarations = imports;
	}

	public void addImportDeclarations(ImportDeclaration imp) {
		if (imp != null)
			_importDeclarations.add(imp);
	}

	public List<ImportDeclaration> getImportDeclarations() {
		return _importDeclarations;
	}

	// MethodDeclarations
	public void setMethodDeclarations(List<MethodDeclaration> methodDeclarations) {
		if (methodDeclarations != null)
			_methodDeclarations = methodDeclarations;
	}

	public void addAllMethods(MethodDeclaration[] methods) {
		if (!methods.equals(null) && methods.length > 0) {
			for (MethodDeclaration method : methods) {
				_methodDeclarations.add(method);
			}
		}
	}

	public void addAllMethods(List<MethodDeclaration> methods) {
		if (methods != null) {
			_methodDeclarations.addAll(methods);
		}
	}

	public void addMethodDeclaration(MethodDeclaration methodDeclaration) {
		if (methodDeclaration != null) {
			_methodDeclarations.add(methodDeclaration);
		}
	}

	public List<MethodDeclaration> getMethodDeclarations() {
		return _methodDeclarations;
	}

	// FieldDeclarations
	public void setFieldDeclarations(List<FieldDeclaration> fieldDeclarations) {
		if (fieldDeclarations != null)
			_fieldDeclarations = fieldDeclarations;
	}

	public void addAllFields(FieldDeclaration[] fieldDeclarations) {
		if (fieldDeclarations != null && fieldDeclarations.length > 0) {
			for (FieldDeclaration field : fieldDeclarations) {
				_fieldDeclarations.add(field);
			}
		}
	}

	public List<FieldDeclaration> getFieldDeclarations() {
		return _fieldDeclarations;
	}
	
	//Expressions
	public void setExpressions(List<Expression> expressions) {
		if (expressions != null) {
			_expressions = expressions;
		}
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
				addInitializer(((Assignment)expression).getRightHandSide());
				break;
//		    ParenthesizedExpression
//		    ClassInstanceCreation
//		    ArrayCreation
//		    ArrayInitializer
//		    MethodInvocation
			case (Expression.METHOD_INVOCATION) :
				addMethodInvocation(((MethodInvocation)expression).getName());
				break;
//		    SuperMethodInvocation
//		    ArrayAccess
//		    InfixExpression
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
	
	public List<Expression> getExpressions() {
		return _expressions;
	}
	
	// Statements
	public void setStatements(List<Statement> statements) {
		_statements = statements;
	}

	public void addStatement(Statement statement) {
		if (statement != null) {
			switch (statement.getNodeType()) {
			// TODO IfStatement
			case (Statement.IF_STATEMENT):

				break;
			// TODO ForStatement
			case (Statement.FOR_STATEMENT):

				break;
			// TODO EnhancedForStatement
			case (Statement.ENHANCED_FOR_STATEMENT):

				break;
			// TODO WhileStatement
			case (Statement.WHILE_STATEMENT):

				break;
			// TODO DoStatement
			case (Statement.DO_STATEMENT):

				break;
			// TODO TryStatement
			case (Statement.TRY_STATEMENT):
				TryStatement tryStatement = (TryStatement)statement;
				List<Statement> statements = (tryStatement.getBody().statements());
				for (Statement childStatement : statements) {
					addStatement(childStatement);
				}
				List<CatchClause> catchClauses = tryStatement.catchClauses();
				for(CatchClause catchClause : catchClauses) {
					addCaughtExceptions(catchClause.getException());
				}
				break;
			// TODO SwitchStatement
			// TODO SynchronizedStatement
			// TODO ReturnStatement
			case (Statement.RETURN_STATEMENT):

				break;
			// TODO ThrowStatement
			// TODO BreakStatement
			// TODO ContinueStatement
			// TODO EmptyStatement
			// TODO LabeledStatement
			// TODO AssertStatement
			case (Statement.EXPRESSION_STATEMENT):
				addExpression(((ExpressionStatement)statement).getExpression());
				break;
			case (Statement.VARIABLE_DECLARATION_STATEMENT):
				_variableDeclarationStatements.add((VariableDeclarationStatement)statement);
				List<VariableDeclarationFragment> fragements = ((VariableDeclarationStatement)statement).fragments();
				for (VariableDeclarationFragment fragment : fragements) {
					_expressions.add(fragment.getInitializer());
					
				}
				break;
			case (Statement.TYPE_DECLARATION_STATEMENT):
				_typeDeclarationStatements.add((TypeDeclarationStatement)statement);
				break;
			case (Statement.CONSTRUCTOR_INVOCATION):
				_constructorInvocations.add((ConstructorInvocation)statement);
				break;
			case (Statement.SUPER_CONSTRUCTOR_INVOCATION):
				_superConstructorInvocations.add((SuperConstructorInvocation)statement);

				break;
			}
		}
	}

	public List<Statement> getStatements(int statementType) {
		switch (statementType) {
		// TODO IfStatement
		case (Statement.IF_STATEMENT):

			break;
		// TODO ForStatement
		case (Statement.FOR_STATEMENT):

			break;
		// TODO EnhancedForStatement
		case (Statement.ENHANCED_FOR_STATEMENT):

			break;
		// TODO WhileStatement
		case (Statement.WHILE_STATEMENT):

			break;
		// TODO DoStatement
		case (Statement.DO_STATEMENT):

			break;
		// TODO TryStatement
		// TODO SwitchStatement
		// TODO SynchronizedStatement
		// TODO ReturnStatement
		case (Statement.RETURN_STATEMENT):

			break;
		// TODO ThrowStatement
		// TODO BreakStatement
		// TODO ContinueStatement
		// TODO EmptyStatement
		// TODO LabeledStatement
		// TODO AssertStatement
		case (Statement.VARIABLE_DECLARATION_STATEMENT):
			return _variableDeclarationStatements;
		case (Statement.TYPE_DECLARATION_STATEMENT):
			return _typeDeclarationStatements;
		case (Statement.CONSTRUCTOR_INVOCATION):
			return _constructorInvocations;
		case (Statement.SUPER_CONSTRUCTOR_INVOCATION):
			return _superConstructorInvocations;
		}
		return null;
	}

	//Initializers
	public void setInitializers(List<Expression> initializers) {
		_initializers = initializers;
	}
	
	public void addInitializer(Expression initializer) {
		if(initializer != null) {
			_initializers.add(initializer);
		}
	}

	public List<Expression> getInitializers() {
		return _initializers;
	}

	//Method Invocations
	public void setMethodInvocations(List<SimpleName> methodInvocations) {
		_methodInvocations = methodInvocations;
	}
	
	public void addMethodInvocation(SimpleName methodInvocation) {
		if (methodInvocation != null) {
		_methodInvocations.add(methodInvocation);
		}
	}

	public List<SimpleName> getMethodInvocations() {
		return _methodInvocations;
	}

	//Caught Exceptions
	public void setCaughtExceptions(List<SingleVariableDeclaration> caughtExceptions) {
		if(caughtExceptions != null) {
		_caughtExceptions = caughtExceptions;
		}
	}
	
	public void addCaughtExceptions(SingleVariableDeclaration caughtException) {
		if(caughtException != null) {
			_caughtExceptions.add(caughtException);
		}
	}

	public List<SingleVariableDeclaration> getCaughtExceptions() {
		return _caughtExceptions;
	}

	

}
