/**
 * 
 */
package itjava.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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

	public ArrayList<String> getSortedTerms(NodeToCompare nodeType, int topX) {
		ArrayList<String> sortedTerms = new ArrayList<String>();
		Map<String, TFIDF> entryMap = null;
		switch (nodeType) {
		case ImportDeclaration:
			entryMap = this.importDeclarationsTF;
			break;
		case MethodInvocation:
			entryMap = this.methodInvoationsTF;
			break;
		case ClassInstanceCreator:
			entryMap = this.classInstancesTF;
			break;
		case VariableDeclaration:
			entryMap = this.variableDeclarationsTF;
			break;
		case SuperType:
			entryMap = this.superTypeTF;
			break;
		case PropertyAssignment:
			entryMap = this.propertyAssignmentsTF;
			break;
		}

		List<TFIDF> tempCollection = new ArrayList<TFIDF> (entryMap.values());
		Collections.sort(tempCollection);
		Iterator<Entry<String, TFIDF>> mapIterator = entryMap.entrySet().iterator();
		try {
			while (sortedTerms.size() < topX) {
				Entry<String, TFIDF> currentEntry = mapIterator.next();
				if (tempCollection.subList(0, topX - 1).contains(currentEntry.getValue())) {
					sortedTerms.add(currentEntry.getKey());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sortedTerms;
	}
	
	
}
