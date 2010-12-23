package itjava.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class Convertor {

	public static String FileToString(String fileSource) throws Exception {
		String fileContent = "";
			BufferedReader bReader = new BufferedReader(new FileReader(fileSource));
			String currLine = bReader.readLine();
			while (currLine != null) {
				if (!currLine.trim().startsWith("//")) {
					fileContent += currLine;
				}
				currLine = bReader.readLine();
				
			}
			bReader.close();
		return fileContent;
	}
	
	public static String FormatCode(String rawCode) {
		String formattedCode = "";
		try {
			BufferedReader bReader = new BufferedReader(new StringReader(rawCode));
			String currLine = bReader.readLine();
			while (currLine != null) {
				if (currLine.trim().length() > 0) {
					if (!currLine.trim().startsWith("//")) {
						formattedCode += currLine.trim().replaceAll("\\s+", " "); // + "\n"; // Removed \n and also removed all possible whitespace
					}
				}
				currLine = bReader.readLine();
			}
			bReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formattedCode;
	}
	
	public static ArrayList<String> StringToArrayListOfStrings(String linesOfCode) throws Exception {
		ArrayList<String> _linesOfCode = new ArrayList<String>();
		try {
			BufferedReader bReader = new BufferedReader(new StringReader(linesOfCode));
			String currLine = bReader.readLine();
			while (currLine != null) {
				if (currLine.trim().length() > 0) {
					if (!currLine.trim().startsWith("//")) {
						_linesOfCode.add(currLine.trim().replaceAll("\\s+", " ")); // + "\n"; // Removed \n and also removed all possible whitespace
					}
				}
				currLine = bReader.readLine();
			}
		}
		catch(Exception e) {
			System.err.println(e.getMessage() + "Problem in converting String to ArrayList of Strings");
		}
		/*String codeInSingleLine = linesOfCode.replace("\n", "");
		String tempLinesOfCode = linesOfCode;
		while (tempLinesOfCode.indexOf("\n") != -1) {
			_linesOfCode.add(tempLinesOfCode.substring(0, tempLinesOfCode.indexOf("\n")).trim().replaceAll("\\s+", " "));
			tempLinesOfCode = tempLinesOfCode.substring(tempLinesOfCode.indexOf("\n") + 1);
		}*/
		
		/*ArrayList<Character> newLineCandidates = new ArrayList<Character>();
		newLineCandidates.add('{');
		newLineCandidates.add('}');
		newLineCandidates.add(';');
		

		char[] charsOfCode = codeInSingleLine.trim().toCharArray();
		int length = charsOfCode.length;
		int cursorPosition = 0;
		int lastNewLinePosition = -1;
		while (cursorPosition < length) {
			if (newLineCandidates.contains(charsOfCode[cursorPosition])) {
				_linesOfCode.add(codeInSingleLine.substring(1 + lastNewLinePosition,
						1 + cursorPosition));
				lastNewLinePosition = cursorPosition;
			}
			cursorPosition++;
		}*/
		return _linesOfCode;
	}

	public static ArrayList<String> TrimArrayListOfString(
			ArrayList<String> linesOfCode) {
		ArrayList<String> _linesOfCode = new ArrayList<String>();
		for (String line : linesOfCode) {
			_linesOfCode.add(line.trim().replace("\n", ""));
		}
		return _linesOfCode;
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!sourceFile.exists()) {
			return;
		}
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}

	}

}
