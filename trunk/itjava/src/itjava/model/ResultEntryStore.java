/**
 * 
 */
package itjava.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Aniket, Matt
 *
 */
public class ResultEntryStore {
	private static ArrayList<URL> _setOfLinks;
	private static ArrayList<ResultEntry> _resultEntries;
	private static int _totalTasks;

	
	public static ArrayList<ResultEntry> createResultEntryList(HashMap<String, String> fileContentsToUrlMap) {
		_resultEntries = new ArrayList<ResultEntry>();
		for (Entry<String, String> entry : fileContentsToUrlMap.entrySet()) {
			ResultEntry newEntry = new ResultEntry(entry.getKey(), entry.getValue(), 0);
			_resultEntries.add(newEntry);
		}
		return _resultEntries;
	}
	
	
	
	/**
	 * Creates a List of {@link ResultEntry} objects each corresponding to each snippet 
	 * found on the list of {@link URL}s. <br />
	 * <b>Note:</b> This method is multi-threaded.
	 * @param setOfLinks
	 * @return
	 */
	public static ArrayList<ResultEntry> createResultEntryList(ArrayList<URL> setOfLinks) {
		_setOfLinks = setOfLinks;
		_resultEntries = new ArrayList<ResultEntry>();
		ArrayList<Thread> mythreads = new ArrayList<Thread>();
		_totalTasks = 0;
		_totalTasks = _setOfLinks.size();
		for(int i=0;i<_setOfLinks.size();i++){
			URL url = _setOfLinks.get(i);
			System.out.println(url);
			SearchThread task = new SearchThread(url);
			ResultSetter setter = new ResultSetter() {  
				public void setResult(ArrayList<ResultEntry> result) {  
					for(int j=0; j<result.size(); j++){
						_resultEntries.add(result.get(j));  
					}
					_totalTasks = _totalTasks-1;
					System.out.println("Total Tasks: " + _totalTasks);
				}  
			};
			task.setResultSetter(setter);
			Thread worker = new Thread(task);
			//pool.execute(task);
			worker.start();
			mythreads.add(worker);
		}
		for (int i=0; i < mythreads.size(); i++) {
			try {
				mythreads.get(i).join();
	        }catch (InterruptedException e) {
		            System.out.print("Join interrupted\n");
	        }
		}
		


		
		return _resultEntries;
		
	} 
	
	public ArrayList<ResultEntry> getResults(){
		return _resultEntries;
	}
	
}
