/**
 * 
 */
package itjava.model;

import itjava.data.TFIDF;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Aniket
 *
 */
public class Matrix {
	private ArrayList<CompilationUnitFacade> facadeList;
	private float[][] importSimilarity;
	private ArrayList<Similarity> sortedSimilarity;
	private float[][] similarity;
	private float UNTOUCHED = -999;
	private float ERROR = -666;
	
	public Matrix(ArrayList<CompilationUnitFacade> compilationUnitFacadeList) {
		facadeList = compilationUnitFacadeList;
		similarity = new float[facadeList.size()] [facadeList.size()];
		for (int i = 0; i < compilationUnitFacadeList.size(); i++) {
			java.util.Arrays.fill(similarity[i], UNTOUCHED);
		}
		importSimilarity = similarity.clone();
		sortedSimilarity = new ArrayList<Matrix.Similarity>();
	}

	public boolean contains(CompilationUnitFacade y, CompilationUnitFacade x) {
		return (similarity[facadeList.indexOf(x)][facadeList.indexOf(y)] != UNTOUCHED || 
				similarity[facadeList.indexOf(y)][facadeList.indexOf(x)] != UNTOUCHED);
	}

	public void setValues(CompilationUnitFacade x, CompilationUnitFacade y) {
		int indexOfX = facadeList.indexOf(x);
		int indexOfY = facadeList.indexOf(y);
		float importVal = ERROR;
		try {
			importVal = CalculateSimilarity(x.getTFVector().importDeclarationsTF.values().toArray(new TFIDF[0]), y.getTFVector().importDeclarationsTF.values().toArray(new TFIDF[0]));
		}
		catch (Exception e) {
			System.err.println("X : " + indexOfX + "\nY: " + indexOfY);
		}
		importSimilarity[indexOfX][indexOfY] = importVal;
		importSimilarity[indexOfY][indexOfX] = importVal;
		sortedSimilarity.add(new Similarity(importVal, indexOfX, indexOfY));
		
		similarity[indexOfX][indexOfY] = importVal;
		similarity[indexOfY][indexOfX] = importVal;
		
	}

	public HashSet<CompilationUnitFacade> GetTopSimilar(int numOfSimilarUnits) {
		HashSet<CompilationUnitFacade> topSimilar = new HashSet<CompilationUnitFacade>();
		Collections.sort(sortedSimilarity, new SimilarityComparator());
		Iterator<Similarity> it = sortedSimilarity.iterator();
		while (topSimilar.size() <= numOfSimilarUnits) {
			Similarity currSimilarity = it.next();
			topSimilar.add(facadeList.get(currSimilarity.x));
			topSimilar.add(facadeList.get(currSimilarity.y));
		}
		return topSimilar;
	}
	
	private float CalculateSimilarity(TFIDF[] a, TFIDF[] b) throws Exception{
		float dotProduct = 0;
		float magProduct;
		for (int i = 0; i < a.length; i ++) {
			try {
				dotProduct += a[i].Value()*b[i].Value();
			}
			catch (Exception e) {
				System.err.println("A : " + a[i].Value());
				System.err.println("B : " + b[i].Value());
				e.printStackTrace();
				dotProduct += a[i].Value()*b[i].Value();
			}
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
	
	
	static class SimilarityComparator implements Comparator<Similarity> {

		@Override
		public int compare(Similarity o1, Similarity o2) {
			return (o1.similarity > o2.similarity) ? 1 : 0;
		}

	}
	
	public class Similarity {
		public float similarity;
		public int x;
		public int y;
		
		public boolean equals(Similarity B) {
			boolean flag = true;
			if (this.similarity != B.similarity || this.x != B.x || this.y != B.y) {
				flag = false;
			}
			return flag;
		}
		public Similarity(float s, int X, int Y) {
			this.similarity = s;
			this.x = X;
			this.y = Y;
		}
		public String toString() {
			return similarity + ":" + x + "/" + y;
		}
	}

}
