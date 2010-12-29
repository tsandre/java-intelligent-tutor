/**
 * 
 */
package itjava.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import itjava.data.LocalMachine;
import itjava.data.LogData;
import itjava.util.Concordance;

/**
 * @author Aniket, Vasanth
 *
 */
public class LogDataStore {

	private final static int HINT_WEIGHT = 90;
	private final static int INCORRECT_WEIGHT = 10;

	public static LogData CreateLogData(int numOfBlanks, Concordance<String> hintsAvailable) throws IOException, ParserConfigurationException, SAXException {
		LogData logData = new LogData(numOfBlanks);		
		if (numOfBlanks > 0) {
			logData.hintsAvailable = hintsAvailable;
			if (!VerifyLogFileAndRename(System.getProperty("user.name"))) throw new IOException("Problem locating raw log file");
			ConvertToXml();
			ProcessXmlToLogData(logData);
			CalculateScore(numOfBlanks, logData);
		}
		else
		{
			if (!VerifyLogFileAndRename(System.getProperty("user.name"))) throw new IOException("Problem locating raw log file");
		}
		return logData;
	}


	/**
	 * Calculate the score for the current quiz
	 * @param numOfBlanks 
	 * @param logData
	 */
	private static void CalculateScore(int numOfBlanks, LogData logData) {
		float weightForEachBlank = (float)1 / (float)numOfBlanks;
		int totalScore = 100;
		for (String blankName : logData.hintsAvailable.keySet()) {
			String logBlankName = blankName + "Action";
			int currHintsUsed = logData.hintsUsed.get(logBlankName);
			int currHintsAvailable = logData.hintsAvailable.get(blankName);
			//float scoreLostForHints = (((currHintsUsed >= currHintsAvailable) ? currHintsAvailable : currHintsUsed ) / currHintsAvailable) * HINT_WEIGHT; 
			float tempScoreLostForHints = (((currHintsUsed >= currHintsAvailable) ? currHintsAvailable : currHintsUsed ) * HINT_WEIGHT ) ; 
			float scoreLostForHints = tempScoreLostForHints / currHintsAvailable;
			float scoreLostForIncorrect = logData.incorrectAttemptsConcordance.get(logBlankName) * INCORRECT_WEIGHT;
			float scoreLost = scoreLostForHints + scoreLostForIncorrect;
			scoreLost = (scoreLost >= 100 ) ? weightForEachBlank * 100 : weightForEachBlank * scoreLost;
			totalScore = (int) (totalScore - scoreLost);
		}
		logData.setScore(totalScore);
	}


	/**
	 * Process fields of XML to populate {@link LogData}
	 * @param logData
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private static void ProcessXmlToLogData(LogData logData) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse (new File(LocalMachine.home + "logs/finalLog.xml"));

		// normalize text representation
		doc.getDocumentElement ().normalize ();
		NodeList listOfLogActions = doc.getElementsByTagName("log_action");
		int totalLogActions = listOfLogActions.getLength();
		String prevNodeText = null;
		for (int logActionIndex = 0; logActionIndex < totalLogActions; logActionIndex++) {
			NodeList childsOfLogAction = listOfLogActions.item(logActionIndex).getChildNodes();
			int index = 0;
			String nodeText = null;
			if (childsOfLogAction.item(1).getFirstChild() == null) {
				logData.hintsUsed.put(prevNodeText);
			}
			else{
				while (index < childsOfLogAction.getLength()) {
					Node childNode = childsOfLogAction.item(index);
					String presentNodeName = childNode.getNodeName();
					if (childNode.getNodeName().equals("action")) {
						nodeText = childNode.getFirstChild().getTextContent();
						nodeText = nodeText.substring
						(nodeText.indexOf(":") + 1, nodeText.lastIndexOf(":")).trim();
						prevNodeText = nodeText;
					}
					else if (childNode.getNodeName().equals("result")) {
						String resultNodeText = childNode.getFirstChild().getTextContent();
						if (resultNodeText.equals("Correct")) {
							logData.setCorrectAnswers(logData.getCorrectAnswers() + 1);
						}
						else if (resultNodeText.equals("InCorrect")) {
							logData.setTotalIncorrectAttempts(logData.getTotalIncorrectAttempts() + 1);
							logData.incorrectAttemptsConcordance.put(nodeText);
						}
					}
					index++;
				}
			}
		}
	}

	/**
	 * Create & Execute batch file to create finalLog.xml
	 */
	private static void ConvertToXml() throws IOException{
		File logsFolder = new File(LocalMachine.home + "logs");
		File batFile = new File(logsFolder, "CreateFinalLog.bat");
		File xmlFile = new File(logsFolder, "finalLog.xml");
		if (batFile.exists()) {
			batFile.delete();
		}
		if (xmlFile.exists()) {
			xmlFile.delete();
		}
		FileWriter out = new FileWriter(batFile);
		//			out.write("set CLASSPATH=%CLASSPATH%" + LocalMachine.classPath.replace("/", "\\"));
		out.write("java -cp \"../automate/lib/DorminWidgets.jar\" edu.cmu.pact.Log.LogFormatUtils " + "raw.log dummyLog.xml tempfile finalLog.xml");  
		out.close();
		String execBat = "cmd.exe /c CreateFinalLog.bat";
		System.out.println("Converting raw.log to finalLog.xml");

		Process pr = Runtime.getRuntime().exec(execBat,null, new File(LocalMachine.home+"logs"));
		InputStreamReader tempReader = new InputStreamReader(new BufferedInputStream(pr.getInputStream()));
		BufferedReader reader = new BufferedReader(tempReader);
		while (true){
			String line = reader.readLine();
			if (line == null)
				break;
			System.out.println(line);
		}	
		try {
			System.out.println((pr.waitFor() == 0) ? "Converted to XML" : "Problem in converting to XML");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Check presence of .log file in particular location & rename
	 * @return
	 */
	public static boolean VerifyLogFileAndRename(final String logFileNameStartsWith) {
		File logFolder = new File(LocalMachine.home + "/logs");
		String fileName = null;
		if(logFolder.exists()) {
			File oldLog = new File(logFolder, "raw.log");
			if (oldLog.exists()) oldLog.delete();
			String[] fileList = logFolder.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return (name.endsWith(".log") && name.startsWith(logFileNameStartsWith)) ? true : false;
				}
			});
			if (fileList.length == 0) {
				return false;
			}
			else {
				fileName = fileList[0];
			}
		}
		File logFile = new File(logFolder, fileName);
		return logFile.renameTo(new File(logFolder, "raw.log"));
	}

}
