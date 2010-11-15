/**
 * 
 */
package itjava.presenter;

import itjava.model.LinkStore;
import itjava.model.ResultEntry;
import itjava.model.ResultEntryStore;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Aniket, Matt
 *
 */
public class CodeSearchPresenter {
	private String _query;
	private String[] _cacheLinks;
	private int _startPosition = 0;
	private int _endPosition = 49;
	
	public CodeSearchPresenter(String query) {
		_query = query;
		_cacheLinks = LinkStore.CreateLinks(_query).toArray(new String[0]);
	}
	
	public String[] ShowLinks(){
		return _cacheLinks;
	}
	
	public ArrayList<ResultEntry> SearchNext() {
		ArrayList<URL> currLinks = new ArrayList<URL>();
		for(int index = _startPosition; index <= _endPosition; index++) {
			try {
				currLinks.add(new URL(_cacheLinks[index]));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		_startPosition = _endPosition + 1;
		_endPosition = _endPosition + 10;
		return ResultEntryStore.createResultEntryList(currLinks);
	}
	
	
}
