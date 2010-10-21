/**
 * 
 */
package itjava.data;

import java.util.TreeMap;

/**
 * @author Aniket
 *
 */
public class TFVector {
	public TreeMap<String, TFIDF> importDeclarationsTF;
	public TreeMap<String, TFIDF> superTypeTF;
	public TreeMap<String, TFIDF> variableDeclarationsTF;
	public TreeMap<String, TFIDF> classInstancesTF;
	public TreeMap<String, TFIDF> methodInvoationsTF;
	public TreeMap<String, TFIDF> propertyAssignmentsTF;
	
	public TFVector() {
		importDeclarationsTF = new TreeMap<String, TFIDF>();
		superTypeTF = new TreeMap<String, TFIDF>();
		variableDeclarationsTF = new TreeMap<String, TFIDF>();
		classInstancesTF = new TreeMap<String, TFIDF>();
		methodInvoationsTF = new TreeMap<String, TFIDF>();
		propertyAssignmentsTF = new TreeMap<String, TFIDF>();
	}
}
