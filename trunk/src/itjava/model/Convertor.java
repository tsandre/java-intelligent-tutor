package itjava.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Convertor {

	public static String FileToString(String fileSource) {
		String fileContent = "";
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(fileSource));
			String currLine = bReader.readLine();
			while (currLine != null) {
				if (!currLine.trim().startsWith("//")) {
					fileContent += currLine;
				}
				currLine = bReader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	
	public static ArrayList<String> StringToArrayListOfStrings(String linesOfCode) {
		ArrayList<Character> newLineCandidates = new ArrayList<Character>();
		newLineCandidates.add('{');
		newLineCandidates.add('}');
		newLineCandidates.add(';');

		// TODO Perform indentation of source code
		ArrayList<String> _linesOfCode = new ArrayList<String>();

		char[] charsOfCode = linesOfCode.trim().toCharArray();
		int length = charsOfCode.length;
		int cursorPosition = 0;
		int lastNewLinePosition = -1;
		while (cursorPosition < length) {
			if (newLineCandidates.contains(charsOfCode[cursorPosition])) {
				_linesOfCode.add(linesOfCode.substring(1 + lastNewLinePosition,
						1 + cursorPosition));
				lastNewLinePosition = cursorPosition;
			}
			cursorPosition++;
		}
		return _linesOfCode;
	}

	public static ArrayList<String> CleanArrayListOfString(
			ArrayList<String> linesOfCode) {
		ArrayList<String> _linesOfCode = new ArrayList<String>();
		for (String line : linesOfCode) {
			_linesOfCode.add(line.trim());
		}
		return _linesOfCode;
	}

}
