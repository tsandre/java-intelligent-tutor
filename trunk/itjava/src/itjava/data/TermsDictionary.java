/**
 * 
 */
package itjava.data;

import itjava.util.Concordance;

/**
 * @author Aniket
 *
 */
public class TermsDictionary {

	private Concordance<String> _importDict;
	private Concordance<String> _methodsDict;
	private Concordance<String> _instancesDict;
	private Concordance<String> _variablesDict;
	
	public TermsDictionary(){
		_importDict = new Concordance<String>();
		_methodsDict = new Concordance<String>();
		_instancesDict = new Concordance<String>();
		_variablesDict = new Concordance<String>();
	}
	

	/**
	 * @return the importDict
	 */
	public Concordance<String> getImportDict() {
		return _importDict;
	}

	/**
	 * @param importDict the importDict to set
	 */
	public void setImportDict(Concordance<String> importDict) {
		_importDict = importDict;
	}

	/**
	 * @return the methodsDict
	 */
	public Concordance<String> getMethodsDict() {
		return _methodsDict;
	}

	/**
	 * @param methodsDict the methodsDict to set
	 */
	public void setMethodsDict(Concordance<String> methodsDict) {
		_methodsDict = methodsDict;
	}

	/**
	 * @return the instancesDict
	 */
	public Concordance<String> getInstancesDict() {
		return _instancesDict;
	}

	/**
	 * @param instancesDict the instancesDict to set
	 */
	public void setInstancesDict(Concordance<String> instancesDict) {
		_instancesDict = instancesDict;
	}

	/**
	 * @return the variablesDict
	 */
	public Concordance<String> getVariablesDict() {
		return _variablesDict;
	}

	/**
	 * @param variablesDict the variablesDict to set
	 */
	public void setVariablesDict(Concordance<String> variablesDict) {
		_variablesDict = variablesDict;
	}


	public void putMethods(String key) {
		_methodsDict.put(key);
	}


	public void putInstances(String key) {
		_instancesDict.put(key);
	}


	public void putImports(String key) {
		_importDict.put(key);
	}


	public void putVariables(String key) {
		_variablesDict.put(key);
	}

}
