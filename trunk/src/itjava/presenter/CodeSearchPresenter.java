/**
 * 
 */
package itjava.presenter;

import itjava.model.LinkStore;
import itjava.model.ResultEntry;
import itjava.model.ResultEntryStore;

import java.util.ArrayList;

/**
 * @author Aniket, Matt
 *
 */
public class CodeSearchPresenter {
	private String _query;
	private String[] _cacheLinks;
	private int _startPosition = 0;
	private int _endPosition = 19;
	
	public CodeSearchPresenter(String query) {
		_query = query;
		_cacheLinks = LinkStore.CreateLinks(_query).toArray(new String[0]);
	}
	public ArrayList<ResultEntry> SearchNext() {
		ArrayList<String> currLinks = new ArrayList<String>();
		for(int index = _startPosition; index <= _endPosition; index++) {
			currLinks.add(_cacheLinks[index]);
		}
		_startPosition = _endPosition + 1;
		_endPosition = _endPosition + 20;
		return ResultEntryStore.createResultEntryList(currLinks);
	}
	
	
}
