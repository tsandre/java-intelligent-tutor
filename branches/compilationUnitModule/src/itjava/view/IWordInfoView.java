package itjava.view;

import itjava.model.WordInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public interface IWordInfoView {
	public String GetQuery();
	public ArrayList<File> GetResultEntries();
	public HashMap<ArrayList<String>, ArrayList<WordInfo>> GetCodeToWordInfoMap ();
	
	public void SetCodeToWordInfoMap(HashMap<ArrayList<String>, ArrayList<WordInfo>> codeToWordInfoMap);
}
