package itjava.model;

import java.io.BufferedReader;
import java.io.FileReader;

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

}
