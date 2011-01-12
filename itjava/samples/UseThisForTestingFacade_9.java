import java.util.ArrayList;

public class test {
	public void testThis(ArrayList<Integer> intArr) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.addAll(intArr);
		arr.add(arr.get(0));
	}
}