/**
 * 
 */
package itjava.model;

import itjava.data.LocalMachine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * @author Aniket, Vasanth
 *
 */
public class TutorialDeployer {
	private Tutorial _tutorial;
	
	public TutorialDeployer(Tutorial finalTutorial) {
		_tutorial = finalTutorial;
	}
	
	/**
	 * 1) Compiles the <b>tutorial.java</b> file. <br />
	 * 2) Generates <b>tutorial.brd</b> file. <br />
	 * 3) Replaces old <i>build.xml</i> file with original template and repopulates its necessary fields. <br />
	 * 4) Executes batch script for ant-deploy that is located in <i>automate</i> directory. Note: Make sure that this batch script has proper local machine address.
	 * @param rt 
	 * @throws Exception
	 */
	public void Deploy(Runtime rt) throws Exception {
		System.out.println("in Deploy");
		String htmlTutorialCode = CompileHTMLTutorial(rt);
		//SaveTutorialCode();
		//CompileTutorial(rt);
		//BRDStore.GenerateBRD(_tutorial);
		//ResetBuildXMLFile();
		//CreateBuildPropertiesFile();
		//BuildXMLCreate.GenerateBuildXML(_tutorial);
		//ExecuteBatchScript(rt);
	}
	
	public void SaveTutorialCode() {
		//unknown!? _tutorial.getWordInfoList().get(1)
	}
	
	public String CompileHTMLTutorial(Runtime rt) throws Exception {
		String myTutorialOutput = "";
		System.out.println("In CompileHTMLTutorial");
		//String sourcepath = LocalMachine.home + "WebContent/htmlDelivery/" + _tutorial.getReadableName() + "/";
		//new File(sourcepath).mkdir(); // If the directory already exists, this method does nothing.
		//File htmlFile = new File(sourcepath + _tutorial.getTutorialName() + ".html");
		//if (htmlFile.exists()) {
		//	htmlFile.delete();
		//}
		//OutputStream fout= new FileOutputStream(htmlFile);
		//OutputStream bout= new BufferedOutputStream(fout);
		//OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1");
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		String myString = _tutorial.getFacade().getInterpretedCode();
		//myString = itjava.model.Convertor.FormatCode(myString);
		Matcher m = CRLF.matcher(myString);
		
		if(_tutorial.getType().equals("Example")){
			ArrayList<String> myLines = Convertor.StringToArrayListOfStrings(_tutorial.getFacade().getUnformattedSource());
			if (m.find()) {
				myString = m.replaceAll("<br>");
			}
			String newString = "";
			int spacecounter = 0;
			for(int i=0; i<myLines.size(); i++){
				if(myLines.get(i).contains("{")){
					spacecounter++;
				}
				if(myLines.get(i).contains("}")){
					spacecounter--;
				}
				for(int j=0; j<spacecounter; j++){
					newString += "&nbsp;&nbsp;";
				}
				newString += myLines.get(i) + "<br>";
			}
			System.out.println("newstring: <br>" + newString);
			myString = itjava.model.Convertor.FormatCode(newString);
			
			if (m.find()) {
				myString = m.replaceAll("<br>");
			}
			myString.replace("  ", "&nbsp;&nbsp;");
			myTutorialOutput += "<pre class=\"prettyprint\">" + myString + "</pre>";
		}else{
			ArrayList<String> myLines = Convertor.StringToArrayListOfStrings(_tutorial.getFacade().getUnformattedSource());
			if (m.find()) {
				myString = m.replaceAll("<br>");
			}
			int Linenum;
			myTutorialOutput += "<script type=\"text/javascript\" language=\"javascript\">";
			myTutorialOutput += "var Edges = new Array();\n";
			myTutorialOutput += "var Hints = new Array();\n";
			myTutorialOutput += "var currhint = new Array();\n";
			myTutorialOutput += "var totalhintsleft = new Array();\n";
			myTutorialOutput += "var questionscore = new Array();\n";
			int totalhints = 0;
			int tempcurrtotalhints = 0;
			for(int i=0; i<_tutorial.getWordInfoList().size(); i++){
				myTutorialOutput += "Edges[" + i + "] = \"" + _tutorial.getWordInfoList().get(i).wordToBeBlanked.toString() + "\";\n";
				Linenum = _tutorial.getWordInfoList().get(i).lineNumber-1;
                                String s = myLines.get(Linenum);
                                int charCount = s.replaceAll(_tutorial.getWordInfoList().get(i).wordToBeBlanked.toString(), "").length();
				myLines.set(Linenum, myLines.get(Linenum).replaceFirst(_tutorial.getWordInfoList().get(i).wordToBeBlanked.toString(), "<input type=\"text\" name=\"answer_" + i + "\" id=\"answer_" + i + "\" onChange=\"checkanswer('" + i + "');\" onfocus=\"setcurrenthint('" + i + "');\" style=\"border-width:2px; border:solid; border-color:#999; padding: 2px 0 2px 0;\" />"));
				if(charCount > 1){
                                    for(int k=1; k<charCount;k++){
                                        myLines.set(Linenum, myLines.get(Linenum).replaceFirst(_tutorial.getWordInfoList().get(i).wordToBeBlanked.toString(), "___________"));    
                                    }
                                }
                                for(int j=0; j<_tutorial.getWordInfoList().get(i).hintsAvailable.size(); j++){
					myTutorialOutput += "Hints[" + totalhints + "] = \"" + _tutorial.getWordInfoList().get(i).hintsAvailable.get(j) + "\";\n";
					totalhints++;
					tempcurrtotalhints = j;
				}
				tempcurrtotalhints++;
				myTutorialOutput += "currhint[" + i + "] = \"0\"\n";
				myTutorialOutput += "totalhintsleft[" + i + "] = \"" + tempcurrtotalhints + "\"\n";
				myTutorialOutput += "questionscore[" + i + "] = \"100\"\n";
			}
			myTutorialOutput += "</script>";
			String newString = "";
			int spacecounter = 0;
			for(int i=0; i<myLines.size(); i++){
				if(myLines.get(i).contains("}")){
					spacecounter--;
				}
				for(int j=0; j<spacecounter; j++){
					newString += "&nbsp;&nbsp;";
				}
				if(myLines.get(i).contains("{")){
					spacecounter++;
				}
				newString += myLines.get(i) + "<br>";
			}
			System.out.println("newstring: <br>" + newString);
			myString = itjava.model.Convertor.FormatCode(newString);
		
			if (m.find()) {
				myString = m.replaceAll("<br>");
			}
			myString.replace("  ", "&nbsp;&nbsp;");
			myTutorialOutput += "<pre class=\"prettyprint\">" + myString + "</pre>";
		}
		return myTutorialOutput;
	}
	
	/**
	 * Compile the CTAT-readable .java file
	 * @param rt 
	 * @param tutorial
	 * @throws Exception
	 */
	public void CompileTutorial(Runtime rt) throws Exception {
		String sourcepath = LocalMachine.home + "generated/";
		File classFile = new File(LocalMachine.home + "generated/" + _tutorial.getTutorialName() + ".class");
		File batFile = new File(LocalMachine.home + "generated/compiler.bat");
		if (batFile.exists()) {
			if(!batFile.delete()){
				System.err.println("File compiler.bat was not deleted..");
			}
		}
		batFile.createNewFile();
		FileWriter compilerBat = new FileWriter(batFile);
		if ( classFile.exists()) {
			classFile.delete();
		}
		String classpath = LocalMachine.classPath.replace('/', '\\');
//		String command = "\ncd " + sourcepath.replace('/', '\\') + "\njavac " + _tutorial.getTutorialName() + ".java";
		String command = "\njavac " + _tutorial.getTutorialName() + ".java";
		compilerBat.write("set CLASSPATH=%CLASSPATH%" + classpath);
		compilerBat.append(command);
		compilerBat.close();
//		String execBat = "cmd.exe /c " + LocalMachine.home + "generated/compiler.bat";
		String execBat = "cmd.exe /c compiler.bat";
		System.out.println("Compiling .java");
		Process pr = rt.exec(execBat,null, new File(sourcepath) );
		InputStreamReader tempReader = new InputStreamReader(new BufferedInputStream(pr.getInputStream()));
        BufferedReader reader = new BufferedReader(tempReader);
        while (true){
            String line = reader.readLine();
            if (line == null)
                break;
            System.out.println(line);
        }	
		System.out.println((pr.waitFor() == 0) ? "Compiling done" : "Problem in compiling");
	}

	/**
	 * Deletes existing build.xml from automate/deploy-tutor directory 
	 * and copies the template to this location.
	 */
	public void ResetBuildXMLFile() {
		String XMLpath = LocalMachine.home + "automate/deploy-tutor/build.xml";
		String BasicXML = LocalMachine.home + "generated/build.xml";
		File toDel = new File(XMLpath);
		if (toDel.exists()) {
			toDel.delete();
		}
		File sourceFile = new File(BasicXML);
		File destFile = new File(XMLpath);
		try {
			Convertor.copyFile(sourceFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Function required to create library files. It is not necessary to call this 
	 * function, since the library files need to be created only once.
	 */
	public void CreateBuildPropertiesFile() {
		try {
			File buildPropertiesFile = new File(LocalMachine.home + "automate/deploy-tutor/build.properties");
			if (buildPropertiesFile.exists()) {
				buildPropertiesFile.delete();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(buildPropertiesFile));
			String buildProperties = "server=http://localhost:8080";
			buildProperties += ("\nserver-dir=/itjava/delivery/" + _tutorial.getReadableName());
			buildProperties += ("\nserver-login=");
			buildProperties += ("\ndefault-codebase=http://localhost:8080/itjava/delivery/" + _tutorial.getReadableName());
			writer.write(buildProperties);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Executes the ant script to archive all the necessary components of a CTAT tutor
	 * to be delivered as Java Web Start (.jnlp and .jar)
	 * @param rt
	 */
	public void ExecuteBatchScript(Runtime rt) {
		try {
			File batDirectory = new File(LocalMachine.home + "automate/deploy-tutor");
			File batFile = new File(batDirectory, "autoBuild.bat");
//			File batFile = new File(LocalMachine.home + "automate/deploy-tutor/autoBuild.bat");
			if (batFile.exists()) {
				batFile.delete();
			}
			//TODO : Check if all other required files exist in deploy-tutor folder.
			batFile.createNewFile();
			batFile.setWritable(true);
			FileWriter compilerBat = new FileWriter(batFile);
//			compilerBat.write("CD " + LocalMachine.home + "automate/deploy-tutor \n");
			compilerBat.write("echo %cd%\n");
			compilerBat.append("ant build-only");
			compilerBat.close();
			
			System.out.println("Running the batch script for Building Webserver Files");
			String command = "cmd.exe /c autoBuild.bat";
			Process pr = rt.exec(command, null, batDirectory);
			InputStreamReader tempReader = new InputStreamReader(new BufferedInputStream(pr.getInputStream()));
	            BufferedReader reader = new BufferedReader(tempReader);
	            while (true){
	                String line = reader.readLine();
	                if (line == null)
	                    break;
	                System.out.println(line);
	            }	
			System.out.println((pr.waitFor() == 0) ? "Finished autobuild" : "Problem in autobuild");
		} 
		catch (Exception e) {
			System.err.println("Error creating the FileInfo panel: " + e);
			e.printStackTrace();
		}
	}
	
}
