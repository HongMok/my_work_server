package com.mx.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtil {

	private static Map<String,Object> cache = new HashMap<String,Object>();
	
	public static boolean cacheObj(String key,Object obj){
		cache.put(key, obj);
		return true;
	}
	
	public static Object getObj(String key){
		return cache.get(key);
	}
	
	public static Object removeCache(String key){
		return cache.remove(key);
	}
	
	public static void removeSameCache(String prefix){
		List<String> rmkeys = new ArrayList<String>();
		for(String key : cache.keySet()){
			if(key.startsWith(prefix)){
				rmkeys.add(key);
			}
		}
		for(String key : rmkeys){
			cache.remove(key);
		}
	}
	
	public static void clearAll(){
		cache.clear();
	}
	
	public static String buildKey(Class<?> cls){
		return cls.getName();
	}
	
	public static String buildKey(Object obj){
		if(obj==null){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(obj.getClass().getName());
		return sb.toString();
	}
	
	public static String buildKeyCount(Object obj){
		String key = buildKey(obj);
		return key+"_count";
	}
	
	public static String buildKey(Object obj,int start,int size){
		String key = buildKey(obj);
		key+="_"+start+"_"+size;
		return key;
	}
}
