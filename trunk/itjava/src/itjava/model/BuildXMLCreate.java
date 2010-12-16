package itjava.model;
import itjava.data.LocalMachine;

import java.io.*;
public class BuildXMLCreate {
  public static void GenerateBuildXML(Tutorial tutorial) {
     try {        
    	 FileWriter fw = new FileWriter(LocalMachine.home+"automate/deploy-tutor/build.xml",true);
    	 BufferedWriter out = new BufferedWriter(fw);
       	 out.write("<property name=\"tutorial-name\" value=\""+tutorial.getTutorialName()+"\"/>\n");
       	out.write("<property name=\"tmp_jar_location\" value=\""+LocalMachine.home+ LocalMachine.webcontent +"delivery/"+tutorial.getReadableName()+"\"/>\n");
       	out.write("<property name=\"folder-name\" value=\""+tutorial.getReadableName()+"\"/>\n");
       	out.write("<property name=\"projects-dir\" value=\""+LocalMachine.projectsDir+"\"/>\n");
    	 out.write("</project>\n");
    	 out.flush();
    	 out.close();
    	 fw.close();
      }
      catch (UnsupportedEncodingException e) {
        System.out.println("This VM does not support the Latin-1 character set.");
      }
      catch (IOException e) {
        System.out.println(e.getMessage());        
      }
  }

} 
  
