package itjava.industry;

import itjava.data.LocalMachine;
import itjava.model.CompilationUnitStore;
import itjava.model.Convertor;
import itjava.model.ResultEntry;
import itjava.model.ResultEntryIndustry;
import itjava.presenter.WordInfoPresenter;
import itjava.presenter.WordInfoPresenterIndustry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class code2String {
public static void main (String args[]){	
	final File folder = new File(LocalMachine.home+"files/");
	ArrayList<String> filePaths = listFilesForFolder(folder);
	System.out.println(filePaths.size()+ " java files in directory");
	ArrayList<String> fileCodes = convert2String(filePaths);
	System.out.println("Total files processed: "+fileCodes.size());
	ArrayList<String> resultEntries = convert2Object(fileCodes);
	System.out.println("Total ResultEntryIndustry processed: "+resultEntries.size());
	WordInfoPresenterIndustry wordInfoPresenterIndustry = new WordInfoPresenterIndustry();
	wordInfoPresenterIndustry.SetCompilationUnitListAndAccessRepository(resultEntries);
	System.out.println("Completed");
}
private static ArrayList<String> convert2Object(ArrayList<String> fileCodes) {
	ArrayList<String> resultEntries = new ArrayList<String>();;
	ResultEntryIndustry newEntry;
	for(int j=0; j < fileCodes.size(); j++){
		if(fileCodes.get(j).length() >= 40){ 
			String codeSample = fileCodes.get(j);
//			Pattern pattern = Pattern.compile("/\\*.*\\*/", Pattern.DOTALL);
//		    Matcher matcher = pattern.matcher(fileCodes.get(j));
//		    Pattern pattern2 = Pattern.compile("^ *//.*");
//		    Matcher matcher2 = pattern2.matcher(matcher.replaceAll(""));
//		    Pattern pattern3 = Pattern.compile("[0-9]+: *");
//		    Matcher matcher3 = pattern3.matcher(matcher2.replaceAll(""));
		    int NumOpenBraces = codeSample.replaceAll("[^{]","").length();
		    int NumClosedBraces = codeSample.replaceAll("[^}]","").length();
		    String partialFiltered = codeSample.replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1 ");
		    String fullFiltered = partialFiltered.replaceAll("//.*(?=\\n)", "");
		    String finalString = fullFiltered.replace("\u00A0", " ");
		    if(NumOpenBraces == NumClosedBraces){
		    	resultEntries.add(finalString);
		    }
		}
	}
	return resultEntries;
}


private static ArrayList<String> convert2String(ArrayList<String> filePaths) {
	Iterator<String> fileLocation = filePaths.iterator();
	ArrayList<String> fileCodes = new ArrayList<String>();
	while (fileLocation.hasNext()) {
		try {
			String fileCode = Files.toString(new File(fileLocation.next()), Charsets.UTF_8);
			fileCodes.add(fileCode);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	return fileCodes;
}

public static ArrayList<String> listFilesForFolder(final File folder) {
	ArrayList<String> filePaths = new ArrayList<String>();
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            System.out.println(fileEntry.getAbsolutePath());
            filePaths.add(fileEntry.getAbsolutePath());
        }
    }
	return filePaths;
}


}