package com.sudocn.play;

import play.cache.Cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 利用fastJson来改善缓存对象的性能，主要负责对象的缓存读写操作
 * @author chao
 *
 */
public class XCache {

    /**
     * Add an element only if it doesn't exist.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void add(String key, Object value, String expiration) {
        String json = JSON.toJSONString(value);
        Cache.add(key, json, expiration);
    }

    /**
     * Add an element only if it doesn't exist, and return only when 
     * the element is effectively cached.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeAdd(String key, Object value, String expiration) {
    	String json = JSON.toJSONString(value);
        return Cache.safeAdd(key, json, expiration);
    }

    /**
     * Add an element only if it doesn't exist and store it indefinitely.
     * @param key Element key
     * @param value Element value
     */
    public static void add(String key, Object value) {
    	String json = JSON.toJSONString(value);
        Cache.add(key, json);
    }

    /**
     * Set an element.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void set(String key, Object value, String expiration) {
//    	String json = JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    	String json = JSON.toJSONString(value);
        Cache.set(key, json, expiration);
    }

    /**
     * Set an element and return only when the element is effectively cached.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeSet(String key, Object value, String expiration) {
    	String json = JSON.toJSONString(value);
        return Cache.safeSet(key, json, expiration);
    }

    /**
     * Set an element and store it indefinitely.
     * @param key Element key
     * @param value Element value
     */
    public static void set(String key, Object value) {
//    	String json = JSON.toJSONString(value, SerializerFeature.DisableCircularReferenceDetect);
    	String json = JSON.toJSONString(value);
        Cache.set(key, json);
    }

    /**
     * Replace an element only if it already exists.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void replace(String key, Object value, String expiration) {
    	String json = JSON.toJSONString(value);
        Cache.replace(key, json, expiration);
    }

    /**
     * Replace an element only if it already exists and return only when the 
     * element is effectively cached.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeReplace(String key, Object value, String expiration) {
    	String json = JSON.toJSONString(value);
        return Cache.safeReplace(key, json, expiration);
    }

    /**
     * Replace an element only if it already exists and store it indefinitely.
     * @param key Element key
     * @param value Element value
     */
    public static void replace(String key, Object value) {
    	String json = JSON.toJSONString(value);
        Cache.replace(key, json);
    }

    /**
     * Delete an element from the cache.
     * @param key The element key
     */
    public static void delete(String key) {
        Cache.delete(key);
    }

    /**
     * Delete an element from the cache and return only when the 
     * element is effectively removed.
     * @param key The element key
     * @return If the element an eventually been deleted
     */
    public static boolean safeDelete(String key) {
        return Cache.safeDelete(key);
    }

    /**
     * Clear all data from cache.
     */
    public static void clear() {
        Cache.clear();
    }

    /**
     * Convenient clazz to get a value a class type;
     * @param <T> The needed type
     * @param key The element key
     * @param clazz The type class
     * @return The element value or null
     */
    @SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> clazz) {
    	String json = Cache.get(key, String.class);
    	T t = JSON.parseObject(json, clazz);
        return t;
    }
}
