package com.jihuiweb.common.db;

import java.util.HashMap;

public class ValueKeyHashMap<K,V> extends HashMap<K,V> {
    private HashMap<V,K> VALUE_KEY_MAP = new HashMap<>();
    @Override
    public V put(K key, V value) {
        VALUE_KEY_MAP.put(value,key);
        return super.put(key, value);
    }

    public V reverseGet(Object value){
        return get(VALUE_KEY_MAP.get(value));
    }
}
