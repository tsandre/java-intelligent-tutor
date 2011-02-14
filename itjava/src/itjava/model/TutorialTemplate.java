package itjava.model;

import itjava.data.DorminComponent;

public class TutorialTemplate {
	public static String importDeclaration = "import edu.cmu.pact.BehaviorRecorder.Controller.CTAT_Launcher; \n " +
			"import java.awt.Color;";
	
	
	/**
	 * Returns the string for class declaration and constructor of the CTAT supported 
	 * @param className Name of the tutorial
	 * @return Declaration of class name and constructor
	 */
	public static String classDeclaration(String className) {
		String classString = "public class " + className + " extends javax.swing.JPanel {\n" +
		    "public " + className + "() { //Constructor\n" +
		    "    initComponents();\n" +
		    "}";
		return classString;
	}
	
	public static String ctatOptionsDeclaration(String varName) {
		return "private edu.cmu.pact.BehaviorRecorder.Controller.CTAT_Options " + varName + ";";
	}

	public static String textFieldDeclaration(String varName) {
		return "private pact.DorminWidgets.DorminTextField " + varName + ";";
	}
	
	public static String buttonDeclaration(String varName) {
		return "private pact.DorminWidgets.DorminButton " + varName + ";";
	}
	
	public static String labelDeclaration(String varName) {
		return "private pact.DorminWidgets.DorminLabel " + varName + ";";
	}
	
	public static String horizontalLineDeclaration(String varName) {
		return "private pact.DorminWidgets.HorizontalLine " + varName + ";";
	}
	
	public static String mainFunctionDeclaration(String className) {
		String mainDeclaration = "public static void main(String[] argv) { \n" +
        		"new CTAT_Launcher(argv).launch (new " + className + "()); \n" +
    			"}\n";
		return mainDeclaration;		
	}
	
	/**
	 * This method returns the source code necessary for generating components that 
	 * have some text to be displayed, eg.. label or button
	 * @param highlight Whether the component should be highlighted or not.
	 * @param componentName Variable Name of the label or button or etc.
	 * @param textToDisplay Text to be displayed on the label or button or etc.
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 * @return Source code for component
	 */
	public static String addComponentToPanel(DorminComponent component, boolean highlight){
		String fontStyle = "0";
		if (highlight) {
			fontStyle = "java.awt.Font.BOLD + java.awt.Font.ITALIC";
		}
		
		String componentDeclaration = component.componentName + ".setBounds(" +
				component.x + "," + component.y + "," + component.width + "," + component.height
			+ "); \n" +
			component.componentName + ".setFont(new java.awt.Font(\"Courier New\", " + fontStyle + ", 12));\n";
		
		if(component.componentText != null) {
			componentDeclaration += component.componentName + ".setText(\"" + cleanseText(component.componentText) + "\"); \n";
		}			
		if (highlight){
			componentDeclaration += component.componentName + ".setForeground(Color.BLUE); \n";
		}
		
		componentDeclaration += "add(" + component.componentName + "); \n";
		return componentDeclaration;
	}
	
	private static String cleanseText(String componentText) {
		componentText = componentText.trim().replace("\"", "\\\"").replace("\'", "\\\'");
		return componentText;
	}

	public static String initComponentsBegin() {
		return "private void initComponents() {\n" +
		"cTAT_Options1 = new edu.cmu.pact.BehaviorRecorder.Controller.CTAT_Options();\n" +
		"setLayout(null);\n\n";
	}

	public static String initComponentDeclaration(String varName, String varType) {
		return varName + " = new pact.DorminWidgets." + varType + "();\n";		
	}

	public static String initComponentsEnd() {
		return "\n}\n";
	}
	
	public static String endClassDeclaration = "}\n";
	
	public static String packageDeclaration = "package generated;\n";
}
