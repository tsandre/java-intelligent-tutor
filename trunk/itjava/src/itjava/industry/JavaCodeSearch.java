package itjava.industry;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.apache.lucene.store.*;
import org.apache.lucene.document.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.queryParser.*;

import itjava.data.LocalMachine;
import itjava.industry.KeywordAnalyzer;
import itjava.industry.JavaSourceCodeAnalyzer;
import com.google.common.io.*;


public class JavaCodeSearch  {

    /** Main for running test case by itself. */
    public static ArrayList<String> industrySearch(String methodName) {
    	ArrayList<String> resultLocations = new ArrayList<String>();
		try {
			File indexDir = new File(LocalMachine.home + "index/");
			String q = "code:"+methodName;
			PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(
					new JavaSourceCodeAnalyzer());
			analyzer.addAnalyzer("import", new KeywordAnalyzer());
			Directory fsDir;
			fsDir = FSDirectory.getDirectory(indexDir, false);
			IndexSearcher is = new IndexSearcher(fsDir);
			Query query = QueryParser.parse(q, "code", analyzer);
			long start = System.currentTimeMillis();

			Hits hits = is.search(query);
			long end = System.currentTimeMillis();

			System.err.println("Found " + hits.length() + " docs in "
					+ (end - start) + " millisec");
			for (int i = 0; i < hits.length(); i++) {
				Document doc = hits.doc(i);
				System.out.println(doc.get("filename") + " with a score of " + hits.score(i));
				resultLocations.add(LocalMachine.home+"/files/"+doc.get("filename"));
			}
			is.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultLocations;
	}
    
}
