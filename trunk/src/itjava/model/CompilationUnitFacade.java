package itjava.model;

import itjava.data.NodeToCompare;
import itjava.data.TFVector;

import java.util.ArrayList;
import java.util.HashMap;
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
	private List<QualifiedName> _qualifiedNames;

	private TFVector _tfVector;
	private String _url;
	
	public String toString() {
		return getLinesOfCode().toString();
	}
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
		_assignments = new ArrayList<Expression>();
		_methodInvocations = new ArrayList<SimpleName>();
		_caughtExceptions = new ArrayList<SingleVariableDeclaration>();
		_classInstances = new ArrayList<Type>();
		_qualifiedNames = new ArrayList<QualifiedName>();
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
			for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
				_fieldDeclarations.add(fieldDeclaration);
				List<VariableDeclarationFragment> fragements = fieldDeclaration.fragments();
				for (VariableDeclarationFragment fragment : fragements) {
					_expressions.add(fragment.getInitializer());
				}
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
			case (Expression.QUALIFIED_NAME) :
				addQualifiedName((QualifiedName)expression);
				break;
				
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
				MethodInvocation methodInvocation = (MethodInvocation) expression;
				addMethodInvocation(methodInvocation.getName());
				for(Expression argument : (List<Expression>)methodInvocation.arguments()) {
					addExpression(argument);
				}
				
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
			case (Statement.BLOCK):
				List<Statement> blockStatements = ((Block)statement).statements();
				for(Statement childStatement : blockStatements) {
					addStatement(childStatement);
				}
				break;

			case (Statement.IF_STATEMENT):
				IfStatement ifStatement = (IfStatement)statement;
				addStatement(ifStatement.getThenStatement());
				addStatement(ifStatement.getElseStatement());
				addExpression(ifStatement.getExpression());
				break;

			case (Statement.FOR_STATEMENT):
				ForStatement forStatement = (ForStatement) statement;
				addStatement(forStatement.getBody());
				for(Expression initExpression : (List<Expression>)forStatement.initializers()) {
					addExpression(initExpression);
				}
				for(Expression updateExpression : (List<Expression>) forStatement.updaters()) {
					addExpression(updateExpression);
				}
				break;
				
			case (Statement.ENHANCED_FOR_STATEMENT):
				EnhancedForStatement enhancedForStatement = (EnhancedForStatement) statement;
				addStatement(enhancedForStatement.getBody());
				addExpression(enhancedForStatement.getExpression());
				//TODO : FormalParameter i.e in statement "for(int a : b)" the part "int a"
				break;
				
			case (Statement.WHILE_STATEMENT):
				WhileStatement whileStatement = (WhileStatement) statement;
				addStatement(whileStatement.getBody());
				addExpression(whileStatement.getExpression());
				break;

			case (Statement.DO_STATEMENT):
				DoStatement doStatement = (DoStatement)statement;
				addStatement(doStatement.getBody());
				addExpression(doStatement.getExpression());
				break;

			case (Statement.TRY_STATEMENT):
				TryStatement tryStatement = (TryStatement)statement;
				List<Statement> tryStatementBlock = tryStatement.getBody().statements();
				for (Statement childStatement : tryStatementBlock) {
					addStatement(childStatement);
				}
				List<CatchClause> catchClauses = tryStatement.catchClauses();
				for(CatchClause catchClause : catchClauses) {
					addCaughtExceptions(catchClause.getException());
				}
				break;
				
			case (Statement.SWITCH_STATEMENT):
				SwitchStatement switchStatement = (SwitchStatement) statement;
				addExpression(switchStatement.getExpression());
				for (Statement switchCase : (List<Statement>)switchStatement.statements()) {
					addStatement(switchCase);
				}
				break;
			// TODO SynchronizedStatement
				
			case (Statement.RETURN_STATEMENT):
				ReturnStatement returnStatement = (ReturnStatement) statement;
				addExpression(returnStatement.getExpression());
				break;
				
			case (Statement.THROW_STATEMENT):
				ThrowStatement throwStatement = (ThrowStatement) statement;
				addExpression(throwStatement.getExpression());
				break;
				
			// TODO BreakStatement
			// TODO ContinueStatement

			case (Statement.EMPTY_STATEMENT):
				//DO nothing here
				break;
			
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

	//Assignments
	public void setAssignments(List<Expression> assignments) {
		_assignments = assignments;
	}
	
	public void addAssignment(Expression assignment) {
		if(assignment != null) {
			_assignments.add(assignment);
		}
	}

	public List<Expression> getAssignments() {
		return _assignments;
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

	//Class Instance creations
	public void setClassInstances(List<Type> classInstances) {
		if(classInstances != null) {
			_classInstances = classInstances;
		}
	}
	
	public void addClassInstances(Type classInstance) {
		if (classInstance != null) {
			_classInstances.add(classInstance);
		}
	}

	public List<Type> getClassInstances() {
		return _classInstances;
	}

	//Qualified Names : properties of objects/classes in use
	public void setQualifiedNames(List<QualifiedName> qualifiedNames) {
		if (qualifiedNames != null) {
			_qualifiedNames = qualifiedNames;
		}
	}
	
	public void addQualifiedName(QualifiedName qualifiedName) {
		if(qualifiedName != null) {
			_qualifiedNames.add(qualifiedName);
		}
	}

	public List<QualifiedName> getQualifiedNames() {
		return _qualifiedNames;
	}
	
	//Term frequency settings
	
	/**
	 * For setting a TFVector, pass a {@link Repository} object that contains all the required
	 * dictionary terms of each type (eg. importTerms, methodInvocationTerms)
	 * @param repository
	 */
	public void setTFVector(Repository repository) {
		_tfVector = TFStore.GetTF(this, repository);
	}

	public TFVector getTFVector() {
		return _tfVector;
	}

	public void setUrl(String url) {
		_url = url;
	}
	
	public String getUrl() {
		return _url;
	}

}
