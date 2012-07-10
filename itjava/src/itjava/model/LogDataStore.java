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
import java.util.Calendar;
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
 * @author Vasanth
 *
 */
public class LogDataStore {

	private final static int HINT_WEIGHT = 90;
	private final static int INCORRECT_WEIGHT = 10;

	public static LogData CreateLogData(int numOfBlanks, Concordance<String> hintsAvailable, int studentId) throws IOException, ParserConfigurationException, SAXException {
		LogData logData = new LogData(numOfBlanks);		
	/*	if (logData.getTotalActions() == 0)
		{
			System.out.println("Error code to be written here");
		}
	*/	
		if (numOfBlanks > 0) {
			logData.hintsAvailable = hintsAvailable;
			if (!VerifyLogFileAndRename(System.getProperty("user.name"))) throw new IOException("Problem locating raw log file");
			// Add code to handle terminate and next error here
			ConvertToXml();
			ProcessXmlToLogData(logData, studentId);
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
		float totalHints = 0;
		float tempScoreLostForHints = 0;
		float scoreLostForIncorrect = 0;
		int currHintsAvailable = 0;
		int currHintsUsed = 0;
		String logBlankName = "";
		for (String blankName : logData.hintsAvailable.keySet()) {
			logBlankName = blankName + "Action";
			currHintsUsed = logData.hintsUsed.get(logBlankName);
			/*String currHintsUsed1 = logData.hintsUsed.values().toString();
			int currHintsUsed = Integer.parseInt(currHintsUsed1);*/
			currHintsAvailable = logData.hintsAvailable.get(blankName);
			//float tempScoreLostForHints = (((currHintsUsed >= currHintsAvailable) ? currHintsAvailable : currHintsUsed ) * HINT_WEIGHT ) ;
			tempScoreLostForHints = tempScoreLostForHints + ( currHintsUsed * HINT_WEIGHT );
			scoreLostForIncorrect = scoreLostForIncorrect + logData.incorrectAttemptsConcordance.get(logBlankName) * INCORRECT_WEIGHT;
			totalHints = totalHints + currHintsAvailable;
		}
			float scoreLostForHints = tempScoreLostForHints / totalHints;
			//scoreLostForIncorrect = logData.incorrectAttemptsConcordance.get(logBlankName) * INCORRECT_WEIGHT;
			float scoreLost = scoreLostForHints + (scoreLostForIncorrect) * weightForEachBlank;
			//scoreLost = (scoreLost >= 100 ) ? weightForEachBlank * 100 : weightForEachBlank * scoreLost;
			totalScore = (int) (totalScore - scoreLost);
			if(totalScore < 0)
				totalScore = 0;
		logData.setScore(totalScore);
	}


	/**
	 * Process fields of XML to populate {@link LogData}
	 * @param logData
	 * @param studentId 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private static void ProcessXmlToLogData(LogData logData, int studentId) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		File logFolder = new File(LocalMachine.home + "logs");
		File finalXML = new File(logFolder, "finalLog.xml");
		Document doc = docBuilder.parse (finalXML);

		// normalize text representation
		doc.getDocumentElement ().normalize ();
		NodeList listOfLogActions = doc.getElementsByTagName("log_action");
		int totalLogActions = listOfLogActions.getLength();
		Calendar todayTime = Calendar.getInstance();
		String prevNodeText = null;
		String firstNodeText = null;
		for (int logActionIndex = 0; logActionIndex < totalLogActions; logActionIndex++) {
			NodeList childsOfLogAction = listOfLogActions.item(logActionIndex).getChildNodes();
			int index = 0;
			String nodeText = null;
			if (childsOfLogAction.item(1).getFirstChild() == null) 
			{
				if(prevNodeText == null)
				{
					int counter = 0;
					while(listOfLogActions.item(counter).getChildNodes().item(1).getTextContent().trim().length() == 0)
					{ 
						counter = counter + 1;
					}
					firstNodeText = listOfLogActions.item(counter).getChildNodes().item(1).getTextContent().trim();
					prevNodeText = firstNodeText.substring(firstNodeText.indexOf(":") + 1, firstNodeText.lastIndexOf(":")).trim();
					logData.hintsUsed.put(prevNodeText);
				}
				else{
					logData.hintsUsed.put(prevNodeText);
				}
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
		Convertor.copyFile(finalXML, new File(logFolder+"/SavedLogs", studentId +"_" +todayTime.getTimeInMillis()+"_final.xml"));
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
	 * Check presence of .log file in particular location & renames to raw.log
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
