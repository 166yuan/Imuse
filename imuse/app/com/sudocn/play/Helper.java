package com.sudocn.play;

import java.lang.reflect.Array;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import play.Play;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * 用于为模板提供便捷地调用方法
 * @author chao
 *
 */
public class Helper extends Controller{
	
	@Before
	static void addHelper(){
		renderArgs.put("helper", new Helper());
	}
	
	public boolean isEmpty(Object obj){
		if(obj == null){
			return true;
		}
		
		if(obj instanceof String){
			return StringUtils.isEmpty((String)obj);
		}
		
		if(obj instanceof Collection){
			return ((Collection)obj).isEmpty();
		}
		
		if(obj.getClass().isArray()){
			return Array.getLength(obj) <= 0;
		}
		
		return true;
		//throw new RuntimeException("Helper.isEmpty() only support java.lang.String, java.util.Collection, Array!");
	}
	
	public Object get(Object value, Object defaultValue){
		if(isEmpty(value)){
			return defaultValue;
		}else{
			return value;
		}
	}
	
	public boolean isDev(){
		return Play.mode == Play.Mode.DEV;
	}
	
	public boolean isProd(){
		return Play.mode == Play.Mode.PROD;
	}

}
