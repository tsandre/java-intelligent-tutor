/**
 * 
 */
package itjava.data;

import java.util.HashMap;

/**
 * @author Aniket
 *
 */
public class TFVector {
	public HashMap<String, Float> importDeclarationsTF;
	public HashMap<String, Float> superTypeTF;
	public HashMap<String, Float> variableDeclarationsTF;
	public HashMap<String, Float> classInstancesTF;
	public HashMap<String, Float> methodInvoationsTF;
	public HashMap<String, Float> propertyAssignmentsTF;
	
	public TFVector() {
		importDeclarationsTF = new HashMap<String, Float>();
		superTypeTF = new HashMap<String, Float>();
		variableDeclarationsTF = new HashMap<String, Float>();
		classInstancesTF = new HashMap<String, Float>();
		methodInvoationsTF = new HashMap<String, Float>();
		propertyAssignmentsTF = new HashMap<String, Float>();
	}
}
