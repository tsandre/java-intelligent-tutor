package itjava.industry;
import itjava.data.LocalMachine;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FileUtils.*;

public class searchMethod {
public static void main(String args[]){
	final File folder = new File(LocalMachine.home+"IndustryFiles/");
	Iterator iter =  FileUtils.iterateFiles(folder, new String[]{"java"}, true);
	while(iter.hasNext()) {
	    File file = (File) iter.next();
	    searchInFile(file);
	}
}
private static void searchInFile(File f) {
	String text = f.toString();
	String word = "getProperty";
	int totalCount = 0;
	int wordCount = 0;
	Scanner s = new Scanner(text);
	while (s.hasNext()) {
		totalCount++;
	if (s.next().equals(word)) wordCount++;
	}
	System.out.println("Word count:  " + wordCount);
	System.out.println("Total count: " + totalCount);
	System.out.printf("Frequency:   %.2f", (double) wordCount / totalCount);
	}
}
