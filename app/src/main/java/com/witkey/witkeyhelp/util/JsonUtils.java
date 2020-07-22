package com.witkey.witkeyhelp.util;

import com.google.gson.Gson;

/**
 * 
 * Title: JsonUtils.java
 * @author wangshijie
 * 2016年8月5日
 * @version 1.0
 */
public class JsonUtils {
	private static Gson gson = new Gson();


	public static Gson getGson(){
		return gson;

	}
	
	/**
	 * 通用获取bean类
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T>T getBeanFromJson(String json,Class<T> clazz){
		try {
			try {
				T fromJson = gson.fromJson(json, clazz);
				return  fromJson;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} catch (RuntimeException e) {
			System.out.println("Gson______detail______"+e);
		}
		
		try {
			return clazz.newInstance();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

