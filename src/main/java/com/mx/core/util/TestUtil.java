package com.mx.core.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestUtil {

	public static void main( String[] args ){
		TestUtil test = new TestUtil();
		
		test.printJsonArray();
	}
	
	private void printJsonArray(){
		JSONArray jsonList = new JSONArray();
		for( int i = 0; i < 5; i++ ){
			JSONObject obj = new JSONObject();
			obj.put("id", i);
			jsonList.add( obj );
		}
		
		JSONObject res = new JSONObject();
		res.put("res", 1);
		res.put("list", jsonList);
		System.out.println("=====");
		System.out.println( res.toString() );
	}
}
