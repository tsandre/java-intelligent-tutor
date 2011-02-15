/**
 * 
 */
package itjava.model;

import itjava.data.LocalMachine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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
		CompileTutorial(rt);
		BRDStore.GenerateBRD(_tutorial);
		ResetBuildXMLFile();
		CreateBuildPropertiesFile();
		BuildXMLCreate.GenerateBuildXML(_tutorial);
		ExecuteBatchScript(rt);
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
