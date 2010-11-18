package itjava.model;
//import java.util.ArrayList;

public class EdgeData {
	private String _edgeVal;
	public String getEdgeVal() {
		return _edgeVal;
	}
	public void setEdgeVal(String edgeVal) {
		_edgeVal = edgeVal;
	}
	private String _edgeName;
	public String getEdgeName() {
		return _edgeName;
	}
	public void setEdgeName(String edgeName) {
		_edgeName = edgeName;
	}
	public String[] _edgeBuggyValues;
	public EdgeData(String edgeVal, String edgeName) {
		_edgeVal = edgeVal;
		_edgeName = edgeName;
		_edgeBuggyValues = new String[0];
}

}
	/*public ArrayList<String> edgeName = new ArrayList<String>();
	public ArrayList<String> edgeSelection = new ArrayList<String>();
	public ArrayList<String> edgeAction = new ArrayList<String>();
	public ArrayList<String> edgeCorrectval = new ArrayList<String>();
	public ArrayList<String> edgeWrongval = new ArrayList<String>();
}*/
