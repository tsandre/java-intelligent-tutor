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
	private void CompileTutorial(Runtime rt) throws Exception {
		String sourcepath = LocalMachine.home + "generated/";
		File classFile = new File(LocalMachine.home + "generated/" + _tutorial.getTutorialName() + ".class");
		File batFile = new File(LocalMachine.home + "generated/compiler.bat");
		if (batFile.exists()) {
			batFile.delete();
		}
		FileWriter compilerBat = new FileWriter(LocalMachine.home + "generated/compiler.bat");
		if ( classFile.exists()) {
			classFile.delete();
		}
		String classpath = "E:\\web\\itjava\\WebContent\\WEB-INF\\lib\\AbsoluteLayout.jar;E:\\web\\itjava\\WebContent\\WEB-INF\\lib\\DorminWidgets.jar;E:\\web\\itjava\\WebContent\\WEB-INF\\lib\\grant.jar;E:\\web\\itjava\\WebContent\\WEB-INF\\lib\\jess.jar;E:\\web\\itjava\\WebContent\\WEB-INF\\lib\\runcc.jar;\n";
		String command = "e:\ncd " + sourcepath + "\njavac " + _tutorial.getTutorialName() + ".java";
		compilerBat.write("@echo OFF\nset CLASSPATH=%CLASSPATH%" + classpath);
		compilerBat.append(command);
		compilerBat.close();
		String execBat = "cmd /C start " + LocalMachine.home + "generated/compiler.bat";
		System.out.println("Compiling .java");
		Process pr = rt.exec(execBat);
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
	 * and copies the template to the location instead.
	 */
	private void ResetBuildXMLFile() {
		String XMLpath = LocalMachine.home + "automate/deploy-tutor/build.xml";
		String BasicXML = LocalMachine.home + "generated/build.xml";
		File toDel = new File(XMLpath);
		if (toDel.exists()) {
			toDel.delete();
		}
		File sourceFile = new File(BasicXML);
		File destFile = new File(XMLpath);
		try {
			copyFile(sourceFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void CreateBuildPropertiesFile() {
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
		
	private void ExecuteBatchScript(Runtime rt) {
		try {
			File batFile = new File(LocalMachine.home + "automate/autoBuild.bat");
			if (!batFile.exists()) {
				FileWriter compilerBat = new FileWriter(LocalMachine.home + "automate/autoBuild.bat");
				compilerBat.write("CD " + LocalMachine.home + "automate/deploy-tutor \n");
				compilerBat.append("ant build-only");
			}
			
			System.out.println("Running the batch script for Building Webserver Files");
			String command = "e:\ncmd /C start " + LocalMachine.home
					+ "automate/autoBuild.bat";
			rt.exec(command);
			Process pr = rt.exec(command);
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
	private void copyFile(File sourceFile, File destFile) throws IOException {
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
