/**
 * 
 */
package itjava.model;

import itjava.data.NodeToCompare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Aniket
 *
 */
public class TFIDFVector {
	public TreeMap<String, TFIDF> importDeclarationsTF;
	public TreeMap<String, TFIDF> superTypeTF;
	public TreeMap<String, TFIDF> variableDeclarationsTF;
	public TreeMap<String, TFIDF> classInstancesTF;
	public TreeMap<String, TFIDF> methodInvoationsTF;
	public TreeMap<String, TFIDF> propertyAssignmentsTF;
	
	public TFIDFVector() {
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
		Collections.reverse(tempCollection);
		List<TFIDF> topElements = tempCollection.subList(0, topX);
			Iterator<Entry<String, TFIDF>> mapIterator = entryMap.entrySet().iterator();
			while (sortedTerms.size() < topX) {
				try {
					Entry<String, TFIDF> currentEntry = mapIterator.next();
					if (currentEntry.getValue().getValue() > (float) 0 && topElements.contains(currentEntry.getValue())) {
							sortedTerms.add(currentEntry.getKey());
					}
					else {
						continue;
					}
				}
				catch (NoSuchElementException e) {
					System.err.println("Node Type : " + nodeType 
							+ " for current Facade has less than requested terms with TFIDF > 0");
					break;
				}
				catch (IndexOutOfBoundsException e) {
					System.err.println(e.getMessage() + ": sublist() had exception");
					break;
				}
				catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		
		return sortedTerms;
	}
	
	
}
