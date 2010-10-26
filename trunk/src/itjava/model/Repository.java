/**
 * 
 */
package itjava.model;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author Aniket
 *
 */
public class Repository {
	public ArrayList<CompilationUnitFacade> allDocuments;
	public ArrayList<String> allUrls;
	public TreeMap<String, Integer> importTerms;
	public TreeMap<String, Integer> superTypeTerms;
	public TreeMap<String, Integer> variableDeclarationTerms;
	public TreeMap<String, Integer> classInstanceTerms;
	public TreeMap<String, Integer> methodInvocationTerms;
	public TreeMap<String, Integer> propertyAssignmentTerms;
	
	public Repository() {
		allDocuments = new ArrayList<CompilationUnitFacade>();
		
		importTerms = new TreeMap<String, Integer>();
		superTypeTerms = new TreeMap<String, Integer>();
		variableDeclarationTerms = new TreeMap<String, Integer>();
		classInstanceTerms = new TreeMap<String, Integer>();
		methodInvocationTerms = new TreeMap<String, Integer>();
		propertyAssignmentTerms = new TreeMap<String, Integer>();
	}

	public boolean Contains(String url) {
		return allUrls.contains(url);
	}

	
	
}
