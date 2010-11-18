package itjava.model;
import itjava.data.LocalMachine;

import java.io.*;
import java.util.ArrayList;
public class BRDStore {
  public static void GenerateBRD(Tutorial tutorial) {
    ArrayList<EdgeData> edgeDataList = tutorial.getEdgeDataList();
    ArrayList<LabelData> labelDataList = tutorial.getLabelDataList();
    int edgeIndex = edgeDataList.size();
    int labelIndex = labelDataList.size();
    String[] edgeValue = new String[edgeIndex];
    String[] edgeSelection = new String[edgeIndex];
    String[] codeContent = new String[labelIndex];
    String[] dorminName = new String[labelIndex];
    String[] edgeactionLabel = new String[edgeIndex];
    String[] nodeText = new String[edgeIndex + 1];
    String[] edgeAction = new String[edgeIndex];
    String[] buggyMessage = new String[edgeIndex];
    String[] hintMessage = new String[edgeIndex];
    int[] edgeUniqueID = new int[edgeIndex];
    int[] sourceID = new int[edgeIndex];
    int[] destID = new int[edgeIndex];
    int[] node_x = new int[edgeIndex + 1];
    int[] node_y = new int[edgeIndex + 1];
    int[] node_width = new int[edgeIndex + 1];
    int[] node_height = new int[edgeIndex + 1];
    int notepropertysetCount = 0;
    int edgeID = -1;
    int _sourceID = 0;
    int _destID = 1;
 //   int x_val = 200;
 //   int y_val = 0;
    	for (int i = 0; i < edgeDataList.size(); i++)
    	{
    		edgeValue[i] = edgeDataList.get(i).getEdgeVal();
    		edgeSelection[i] = edgeDataList.get(i).getEdgeName();
    		edgeactionLabel[i] = "true";
    		buggyMessage[i] = "No, this is not correct.";
    		
    		// To calculate Buggy Message
    		if(edgeValue[i].contains("Done")){
    			hintMessage[i] = "Please click on the highlighted button.";
    		}
    		else{
    			hintMessage[i] = "Please enter '"+edgeValue[i]+"' in the highlighted field.";
    		}
    		
    		// To calculate Edge Action
    		if(edgeValue[i].contains("Done"))
    		{
    			edgeAction[i] = "ButtonPressed";
    		}
    		else if(edgeValue[i].contains("Help"))
    		{
    			edgeAction[i] = "ButtonPressed";
    		}
    		else{
    			edgeAction[i] = "UpdateTextField";
    		}
    	}
    	for (int i = 0; i < labelDataList.size(); i++)
    	{
    		codeContent[i] = labelDataList.get(i)._labelName;
    		dorminName[i] = labelDataList.get(i)._labelValue;
    	}
    	nodeText[0]=tutorial.tutorialName;
    	for(int j=1; j <= edgeDataList.size(); j++ )
    	{
    		nodeText[j]="state"+j;
    	}
    	for(int k=0; k < edgeDataList.size(); k++)
    	{
    		edgeID = edgeID + 2;
    		edgeUniqueID[k] = edgeID;
    	}
    	for(int l=0; l < edgeDataList.size(); l++)
    	{
    		// Include few more conditions for buggy messages when needed
    		_sourceID = _sourceID + 1;
    		_destID = _destID + 1;
    		sourceID[l] = _sourceID;
    		destID[l] = _destID;
    	}
    	for(int m=0; m <= edgeDataList.size(); m++)
    	{
    	//	x_val = x_val + 100;
    	//	y_val = y_val + 50;
    		node_x[m] = (m+1)*100;
    		node_y[m] = (m+1)*50;
    		node_width[m] = 40;
    		node_height[m] = 22;
    	}
    	
//    String[] dorminName = {"lblLine1","lblLine2","lblLine2Col7_static","lblLine3","lblLine3Col7_out","lblLine4","lblLine5","hint","doneButton"};
//    String[] codeContent = {"class myfirstjavaprog {","public","void main(String args[]) {","System.",".println(\"Hello World!\");","}","}","Help","Done"};
//    String[] edgeValue = {"static","out","Done","int","in"};
//    String[] edgeSelection = {"txtLine2Col7_static","txtLine3Col7_out","doneButton","txtLine2Col7_static","txtLine3Col7_out"};
//    String[] edgeactionLabel = {"true","true","true","false","false"};    	
//    String[] nodeText = {"startstate","state1","state2","state3","state4","state5"};
//    String[] edgeAction = {"UpdateTextField","UpdateTextField","ButtonPressed","UpdateTextField","UpdateTextField"};
//    String[] buggyMessage = {"No, this is not correct.","No, this is not correct.","No, this is not correct.","No, this is not correct.","No, this is not correct."};
//    String[] hintMessage = {"Please enter 'static' in the highlighted field.","Please enter 'out' in the highlighted field.","Please click on the highlighted button.","Please enter 'static' instead of 'int' in the highlighted field.","Please enter 'out' instead of 'in' in the highlighted field."};
//    int[] edgeUniqueID = new int[] {1,3,5,7,9};
//    int[] node_x = new int[] {345,508,508,508,164,246};
//    int[] node_y = new int[] {30,115,225,335,119,170};
//    int[] node_width = new int[] {69,40,40,40,40,40};
//    int[] node_height = new int[] {25,22,22,22,22,22};
//    int[] sourceID = new int[] {1,2,3,1,1 };
//    int[] destID = new int[] {2,3,4,5,6};
    	
      try {        
        OutputStream fout= new FileOutputStream(LocalMachine.home+"generated/"+tutorial.tutorialName+".brd");
    	//OutputStream fout= new FileOutputStream("generated/mainprog.brd");
        OutputStream bout= new BufferedOutputStream(fout);
        OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1");
        out.write("<?xml version=\"1.0\" ");
        out.write("standalone=\"yes\"?>\r\n");  
        out.write("<stateGraph firstCheckAllStates=\"false\" caseInsensitive=\"true\" unordered=\"false\" lockWidget=\"true\" version=\"2.1\" suppressStudentFeedback=\"false\" highlightRightSelection=\"true\" startStateNodeName=\"%(startStateNodeName)%\">\r\n");
        out.write("<startNodeMessages>\r\n");
     /*   out.write("<message>\r\n");
        out.write("<verb>NotePropertySet</verb>\r\n");
        out.write("<properties>\r\n");
        out.write("    <MessageType>StartProblem</MessageType>\r\n");
        out.write("    <ProblemName>"+problemName+"</ProblemName>\r\n");
        out.write("</properties>\r\n");
        out.write("</message>\r\n"); */
            for (int i = 0; i < codeContent.length ; i++) {
	            out.write(" <message>\r\n");
	            out.write("  <verb>NotePropertySet</verb>\r\n");
	            out.write("  <properties>\r\n");
	            out.write("   <MessageType>InterfaceAction</MessageType>\r\n");
	            out.write("   <Selection>\r\n");  
	            out.write("      <value>"+codeContent[notepropertysetCount]+"</value>\r\n"); 
	            out.write("   </Selection>\r\n");  
	            out.write("   <Action>\r\n");  
	            out.write("      <value>UpdateText</value>\r\n");          		
	            out.write("   </Action>\r\n");
	            out.write("   <Input>\r\n");  
	            out.write("      <value>"+dorminName[notepropertysetCount]+"</value>\r\n");          		
	            out.write("   </Input>\r\n");
	            notepropertysetCount = notepropertysetCount + 1;
	            out.write("  </properties>\r\n");
	            out.write(" </message>\r\n");
            }
            out.write("</startNodeMessages>\r\n"); 
            for (int j = 0; j < nodeText.length ; j++) {
            	out.write("  <node locked=\"false\" doneState=\"false\">\r\n");	
            	out.write("   <text>"+nodeText[j]+"</text>\r\n");
            	out.write("   <uniqueID>"+(j+1)+"</uniqueID>\r\n");
            	out.write("   <dimension>\r\n");
            	out.write("    <x>"+node_x[j]+"</x>\r\n");
            	out.write("    <y>"+node_y[j]+"</y>\r\n");
            	out.write("    <width>"+node_width[j]+"</width>\r\n");
            	out.write("    <height>"+node_height[j]+"</height>\r\n");
            	out.write("   </dimension>\r\n");
            	out.write("  </node>\r\n");
            }
            for (int k = 0; k < edgeValue.length ; k++) {
            	out.write("<edge>\r\n");
            	out.write(" <actionLabel preferPathMark=\""+edgeactionLabel[k]+"\" minTraversals=\"1\" maxTraversals=\"1\">\r\n");
            	out.write(" <studentHintRequest></studentHintRequest>\r\n");
            	out.write(" <stepSuccessfulCompletion></stepSuccessfulCompletion>\r\n");
            	out.write(" <stepStudentError></stepStudentError>\r\n");
            	out.write("  <uniqueID>"+edgeUniqueID[k]+"</uniqueID>\r\n");
            	out.write(" <message>\r\n");
                out.write("  <verb>NotePropertySet</verb>\r\n");
                out.write("  <properties>\r\n");
                out.write("   <MessageType>InterfaceAction</MessageType>\r\n");
                out.write("   <Selection>\r\n");
                out.write("    <value>"+edgeSelection[k]+"</value>\r\n");
                out.write("   </Selection>\r\n");
                out.write("   <Action>\r\n");
                out.write("    <value>"+edgeAction[k]+"</value>\r\n");
                out.write("   </Action>\r\n");
                out.write("   <Input>\r\n");
                out.write("    <value>"+edgeValue[k]+"</value>\r\n");
                out.write("   </Input>\r\n");
                out.write("  </properties>\r\n");
                out.write(" </message>\r\n");
                out.write(" <buggyMessage>"+buggyMessage[k]+"</buggyMessage>\r\n");
                out.write(" <successMessage></successMessage>\r\n");
                out.write(" <hintMessage>"+hintMessage[k]+"</hintMessage>\r\n");
                if (edgeactionLabel[k] == "true")
                {
                	out.write(" <actionType>Correct Action</actionType>\r\n");
                }
                else
                {
                	out.write(" <actionType>Buggy Action</actionType>\r\n");
                }

                out.write("  <oldActionType>Correct Action</oldActionType>\r\n");
                out.write("  <checkedStatus>Never Checked</checkedStatus>\r\n");
                out.write("  <matcher>\r\n");
                out.write(" <matcherType>ExactMatcher</matcherType>\r\n");
                out.write("   <matcherParameter name=\"selection\">"+edgeSelection[k]+"</matcherParameter>\r\n");
                out.write("   <matcherParameter name=\"action\">"+edgeAction[k]+"</matcherParameter>\r\n");
                out.write("   <matcherParameter name=\"input\">"+edgeValue[k]+"</matcherParameter>\r\n");
                out.write("   <matcherParameter name=\"actor\">Student</matcherParameter>\r\n");
                out.write(" </matcher>\r\n");
                out.write(" </actionLabel>\r\n");
                out.write(" <preCheckedStatus>No-Applicable</preCheckedStatus>\r\n");
                out.write(" <rule>\r\n");
                out.write("   <text>unnamed</text>\r\n");
                out.write("   <indicator>-1</indicator>\r\n");
                out.write(" </rule>\r\n");
                out.write(" <sourceID>"+sourceID[k]+"</sourceID>\r\n");
                out.write(" <destID>"+destID[k]+"</destID>\r\n");  
                out.write(" <traversalCount>0</traversalCount>\r\n"); 
                out.write("</edge>\r\n");
            }
           out.write("<EdgesGroups ordered=\"true\"></EdgesGroups>\r\n");
           out.write("</stateGraph>\r\n");
        out.flush(); 
        out.close();
      }
      catch (UnsupportedEncodingException e) {
        System.out.println("This VM does not support the Latin-1 character set.");
      }
      catch (IOException e) {
        System.out.println(e.getMessage());        
      }
  }

} 
  
