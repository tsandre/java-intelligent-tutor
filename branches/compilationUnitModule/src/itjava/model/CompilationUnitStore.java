package itjava.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
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
//			currTypeDeclaration.getFields();
//			currTypeDeclaration.getMethods();
			facade.addAllSuperInterfaces(currTypeDeclaration.superInterfaceTypes());
			facade.addAllClassModifiers(currTypeDeclaration.modifiers());
			facade.addSuperTypes(currTypeDeclaration.getSuperclassType());
			facade.addAllSuperInterfaces(currTypeDeclaration.superInterfaceTypes());
			
			
		}
		return facade;
	}

}
