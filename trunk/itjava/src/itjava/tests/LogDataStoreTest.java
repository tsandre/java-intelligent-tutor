/**
 * 
 */
package itjava.tests;

import static org.junit.Assert.*;
import itjava.data.LocalMachine;
import itjava.data.LogData;
import itjava.model.Convertor;
import itjava.model.LogDataStore;
import itjava.util.Concordance;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Aniket, Vasanth
 *
 */
public class LogDataStoreTest {

	private File _logFile;
	
	@Before
	public void SetUp() {
		File logFolder = new File(LocalMachine.home + "/logs");
		String[] fileList = logFolder.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return (name.endsWith(".log")) ? true : false;
			}
		});
		for ( String fileName : fileList) {
			File currFile = new File(fileName);
			currFile.delete();
		}
	}

	@Test
	public void TestVerifyLocationOfLog() throws IOException {
		_logFile = new File(LocalMachine.home + "/logs/rawLog.log");
		FileWriter writer = new FileWriter(_logFile);
		writer.write("abababa");
		writer.close();
		assertTrue(LogDataStore.VerifyLogFileAndRename("rawLog"));
	}
	
	@Test
	public void TestVerifyRenameOfLog() throws IOException {
		_logFile = new File(LocalMachine.home + "/logs/rawLog.log");
		FileWriter writer = new FileWriter(_logFile);
		writer.write("abababa");
		writer.close();
		assertTrue(LogDataStore.VerifyLogFileAndRename("rawLog"));
		File renamedFile = new File(LocalMachine.home + "/logs/raw.log");
		assertTrue(renamedFile.exists());
	}
	
	@Test
	public void TestConversionToXML() throws IOException, ParserConfigurationException, SAXException {
		File testFile = new File(LocalMachine.home + "/logs/Testlog");
		_logFile = new File(LocalMachine.home + "/logs/" + System.getProperty("user.name")+ "1212.log");
		Convertor.copyFile(testFile, _logFile);
		Concordance<String> hintsAvailable = new Concordance<String>();
		hintsAvailable.put("txtLine3Col7Action");
		hintsAvailable.put("txtLine2Col7Action");
		LogDataStore.CreateLogData(2, hintsAvailable);
		File xml = new File(LocalMachine.home + "/logs/finalLog.xml");
		assertTrue(xml.exists());
	}
	
	@Test
	public void TestScoreCalculation() throws IOException, ParserConfigurationException, SAXException {
		File testFile = new File(LocalMachine.home + "/logs/Testlog");
		_logFile = new File(LocalMachine.home + "/logs/" + System.getProperty("user.name")+ "1212.log");
		Convertor.copyFile(testFile, _logFile);
		Concordance<String> hintsAvailable = new Concordance<String>();
		hintsAvailable.put("txtLine3Col7Action");
		hintsAvailable.put("txtLine2Col7Action");
		LogData logData = LogDataStore.CreateLogData(2, hintsAvailable);
		assertEquals(50, logData.getScore());
	}
	
	@After
	public void TearDown() {
		_logFile.delete();
	}
}
