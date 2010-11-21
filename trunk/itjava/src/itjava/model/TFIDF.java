/**
 * 
 */
package itjava.model;

/**
 * Wrapper object that consists all necessary information for calculating tfidf value for each term.
 * Use method getValue() to get the actual tfidf value.
 * @author Aniket
 *
 */
public class TFIDF implements Comparable<TFIDF>{
	private Number numOfOccurrences;
	private Number totalTermsInDocument;
	private Number totalDocuments;
	private Number numOfDocumentsWithTerm;
	
	public TFIDF(Number occ, Number totTerms, Number totDocs, Number docsWithTerms) {
		numOfOccurrences = occ;
		totalTermsInDocument = totTerms;
		totalDocuments = totDocs;
		numOfDocumentsWithTerm = docsWithTerms;
	}
	
	public Float getValue(){
		float tf = numOfOccurrences.floatValue() / (Float.MIN_VALUE + totalTermsInDocument.floatValue());
		float idf = (float) Math.log10(totalDocuments.floatValue() / (Float.MIN_VALUE + numOfDocumentsWithTerm.floatValue()));
		return (tf * idf);
	}
	
	public int getNumOfOccurrences() {
		return this.numOfOccurrences.intValue();
	}
	
	public String toString() {
		return this.getValue().toString();
//		return "numOfOccurrences : " + this.numOfOccurrences.intValue() + "\n"
//			+ "totalTermsInDocument : " + this.totalTermsInDocument.intValue() + "\n"
//			+ "numOfDocumentsWithTerm : " + this.numOfDocumentsWithTerm.intValue() + "\n"
//			+ "totalDocuments : " + this.totalDocuments.intValue() + "\n"
//			+ "TFIDF : " + this.Value();
			
	}
	
	@Override
	public int compareTo(TFIDF o) {
		return (int) ((this.getValue() - o.getValue()) * 100);
	}
	
}
