package com.sudocn.utils;

import java.util.HashMap;
import java.util.Map;

public class ChainedHashMap<K,V> extends HashMap<K,V>{

	public ChainedHashMap() {
		super();
	}

	public ChainedHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ChainedHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ChainedHashMap(Map<? extends K, ? extends V> m) {
		super(m);
	}
	
	public ChainedHashMap<K,V> set(K key, V value){
		super.put(key, value);
		return this;
	}

}
