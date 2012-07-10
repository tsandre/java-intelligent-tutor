package itjava.industry;

import java.io.*;
import java.util.Set;
import org.apache.lucene.analysis.*;

/**
 * @author Vasanth
 * 
 */

public class JavaSourceCodeAnalyzer extends Analyzer {

	private Set javaStopSet; 
	private Set englishStopSet; 
	public static final String[] JAVA_STOP_WORDS = {
			"public","private","protected","interface","abstract","implements",
			"extends","null","new" ,"switch","case", "default" ,"synchronized" ,
			"do", "if", "else","break","continue", "this",  "assert" ,
			"for","instanceof", "transient", "final", "static" ,"void",  
			"catch","try","throws","throw",	"class", "finally", "return" ,
			"const" , "native", "super", "while", "import" , "package" ,"true", "false",
			"a", "b", "c", "d", "e", "f", "g", "h", "i","j", "k", "l", "m", "n", "o", "p", "q", "r",
		    "s", "t", "u", "v", "w", "x", "y", "z"
	};
	
	private static final String[] ENGLISH_STOP_WORDS = {
	    "a", "an", "and", "are", "as", "at", "be", "but", "by",
	    "for", "if", "in", "into", "is", "it",
	    "no", "not", "of", "on", "or", "s", "such",
	    "t", "that", "the", "their", "then", "there", "these",
	    "they", "this", "to", "was", "will", "with"
	  };
	
	public JavaSourceCodeAnalyzer(){
		super();
		javaStopSet = StopFilter.makeStopSet(JAVA_STOP_WORDS);
		englishStopSet = StopFilter.makeStopSet(ENGLISH_STOP_WORDS);
	}
	
	public TokenStream tokenStream(String fieldName, Reader reader) {
		if (fieldName.equals("comment"))
			return   new PorterStemFilter(new StopFilter(new LowerCaseTokenizer(reader), englishStopSet));
		else
			return   new StopFilter( new LowerCaseTokenizer(reader),javaStopSet);
	  }
}
