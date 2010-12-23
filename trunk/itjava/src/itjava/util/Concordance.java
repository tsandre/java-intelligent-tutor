/**
 * 
 */
package itjava.util;

import java.util.HashMap;

/**
 * @author Aniket
 * @param <K>
 *
 */
public class Concordance<K> extends HashMap<K, Integer> {

	/* (non-Javadoc)
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public Integer get(Object key) {
		return (super.get(key) == null) ? new Integer(0): super.get(key);
	}

	/* (non-Javadoc)
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Integer put(K key, Integer value) {
		return super.put(key, value + ((super.get(key) != null) ? super.get(key) : new Integer(0)));
	}
	
	/**
	 * Increments number of occurrences of the specified key in this map by 1. 
	 * If the map previously did not contain a mapping for the key, value is set to 1.
	 * @param key key with which the specified value is to be associated
	 * @return the previous value associated with key, or null if there was no mapping for key.
	 */
	public Integer put(K key) {
		return super.put(key, new Integer(1) + ((super.get(key) != null) ? super.get(key) : new Integer(0)));
	}

}
