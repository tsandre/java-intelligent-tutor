package itjava.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class CompilationUnitFacade {
	private List<ImportDeclaration> _importDeclarations;
	private List<SimpleType> _superInterfaces;
	private List<Type> _superTypes;
	private List<Modifier> _modifiers;
	private List<VariableDeclaration> _variableDeclarations;
	private List<MethodDeclaration> _methodDeclarations;
	private List<FieldDeclaration> _fieldDeclarations;
	private List<String> _linesOfCode;

	public void setLinesOfCode(String linesOfCode) {
		ArrayList<Character> newLineCandidates = new ArrayList<Character>();
		newLineCandidates.add('{');
		newLineCandidates.add('}');
		newLineCandidates.add(')');
		newLineCandidates.add(';');
		
		// TODO Perform indentation of file
		_linesOfCode = new ArrayList<String>();
		
		char[] charsOfCode = linesOfCode.trim().toCharArray();
		int length = charsOfCode.length;
		int cursorPosition = 0;
		int lastNewLinePosition = -1;
		while (cursorPosition < length) {
			if (newLineCandidates.contains(charsOfCode[cursorPosition])) {
				_linesOfCode.add(linesOfCode.substring(1 + lastNewLinePosition, 1 + cursorPosition));
				lastNewLinePosition = cursorPosition;
			}
			cursorPosition++;
		}
	}

	public List<String> getLinesOfCode() {
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

	
	//MethodDeclarations
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

	//FieldDeclarations
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

	

}
