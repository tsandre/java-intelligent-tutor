/**
 * 
 */
package itjava.model;

import itjava.data.TFIDF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Aniket
 *
 */
public class Matrix {
	private ArrayList<CompilationUnitFacade> facadeList;
	private float[] importSimilarity;
	private Set<Entry<Float, Integer[]>> sortedImport;
	
	private float[] similarity;
	private float UNTOUCHED = -999;
	
	public Matrix(ArrayList<CompilationUnitFacade> compilationUnitFacadeList) {
		facadeList = compilationUnitFacadeList;
		for (int i = 0; i < (facadeList.size() * facadeList.size()); i++) {
			similarity[i] = UNTOUCHED;
		}
		importSimilarity = similarity.clone();
		sortedImport = new TreeSet<Entry<Float, Integer[]>> (new SetComparator());
	}

	public boolean contains(CompilationUnitFacade y, CompilationUnitFacade x) {
		return (similarity[x.getTFVector().importDeclarationsTF.size()*facadeList.indexOf(x) + facadeList.indexOf(y)] != UNTOUCHED || 
				similarity[x.getTFVector().importDeclarationsTF.size()*facadeList.indexOf(y) + facadeList.indexOf(x)] != UNTOUCHED);
	}

	public void setValues(CompilationUnitFacade x, CompilationUnitFacade y) {
		int indexOfX = facadeList.indexOf(x);
		int indexOfY = facadeList.indexOf(y);
		float importVal = CalculateSimilarity(x.getTFVector().importDeclarationsTF.values().toArray(new TFIDF[0]), y.getTFVector().importDeclarationsTF.values().toArray(new TFIDF[0]));
		importSimilarity[x.getTFVector().importDeclarationsTF.size()*indexOfX + indexOfY] = importVal;
		importSimilarity[x.getTFVector().importDeclarationsTF.size()*indexOfY + indexOfX] = importVal;
		
	}

	public ArrayList<CompilationUnitFacade> GetTopSimilar(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private float CalculateSimilarity(TFIDF[] a, TFIDF[] b) {
		float dotProduct = 0;
		float magProduct;
		for (int i = 0; i < a.length; i ++) {
			dotProduct += a[i].Value()*b[i].Value();
		}
		float magA = 0;
		float magB = 0;
		for (int j = 0; j < a.length; j++){
			magA += a[j].Value() * a[j].Value();
			magB += b[j].Value() * b[j].Value();
		}
		magProduct = (float) ((Math.sqrt(magA)) * (Math.sqrt(magB)));
		return dotProduct / magProduct;
	}
	
	static class SetComparator implements Comparator<Entry<Float, Integer[]>>{

		@Override
		public int compare(Entry<Float, Integer[]> arg0,
				Entry<Float, Integer[]> arg1) {
			return (arg0.getKey() > arg1.getKey()) ? 1 : 0;
		}
	}

}
