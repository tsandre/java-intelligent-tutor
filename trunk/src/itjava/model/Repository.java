/**
 * 
 */
package itjava.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Aniket
 *
 */
public class Repository {
	public ArrayList<CompilationUnitFacade> allDocuments;
	
	public HashMap<String, Integer> importTerms;
	public HashMap<String, Integer> superTypeTerms;
	public HashMap<String, Integer> variableDeclarationTerms;
	public HashMap<String, Integer> classInstanceTerms;
	public HashMap<String, Integer> methodInvocationTerms;
	public HashMap<String, Integer> propertyAssignmentTerms;
	
	public Repository() {
		importTerms = new HashMap<String, Integer>();
		superTypeTerms = new HashMap<String, Integer>();
		variableDeclarationTerms = new HashMap<String, Integer>();
		classInstanceTerms = new HashMap<String, Integer>();
		methodInvocationTerms = new HashMap<String, Integer>();
		propertyAssignmentTerms = new HashMap<String, Integer>();
	}
}
