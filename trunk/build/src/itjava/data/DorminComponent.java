package itjava.data;

public class DorminComponent {
	public String componentType;
	public String componentName;
	public String componentText;
	public int x;
	public int y;
	public int height;
	public int width;
	
	public void Label(String lblText, String name, int x, int y, int height, int width) {
		this.componentType = "DorminLabel";
		this.componentName = name;
		this.componentText = lblText;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public void TextField(String name, int x, int y, int height, int width) {
		this.componentType = "DorminTextField";
		this.componentName = name;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public void Button(String btnText, String name, int x, int y, int height, int width) {
		this.componentType = "DorminButton";
		this.componentName = name;
		this.componentText = btnText;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
}
