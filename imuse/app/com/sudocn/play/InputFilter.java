package com.sudocn.play;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import play.mvc.Before;
import play.mvc.Controller;

/**
 * 用户输入过滤器
 * 
 * @author chao
 *
 */
public class InputFilter extends Controller {
	
	/**
	 * 对用户所有的输入进行trim操作，如果不希望某个参数被trim，请使用NoTrim标注
	 */
	@Before
	static void trimString(){
		NoTrim noTrim = getActionAnnotation(NoTrim.class);
		Map<String, String[]> data = params.all();
		for(String key: data.keySet()){
			if(noTrim != null){ 
				String[] noTrimQs = trimArray(noTrim.value());
				if(noTrimQs.length == 0){//noTrim如果不标注任何参数，那么就对全部参数不进行trim
					break;
				}else{
					if(contains(noTrimQs, key)){ //noTrim标注的请求参数不执行trim
						continue;
					}
				}
			}
			String[] qs = data.get(key);
			for(int i=0; i<qs.length;i++){
				qs[i] = StringUtils.trim(qs[i]);
			}
			data.put(key, qs);
		}
	}
	
	/**
	 * 将数组中字符串为空的元素删除，返回一个新的数组
	 * @param arr
	 * @return
	 */
	static String[] trimArray(String[] arr){
		List<String> strings = new ArrayList(arr.length);
		for(String s : arr){
			if(!StringUtils.isBlank(s)){
				strings.add(s.trim());
			}
		}
		return strings.toArray(new String[strings.size()]);
	}
	
	/**
	 * 判断字符串数组中是否有tar字符串
	 * @param arr
	 * @param tar
	 * @return
	 */
	static boolean contains(String[] arr, String tar){
		for(String a: arr){
			if(StringUtils.equals(a, tar)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 不执行trim操作
	 * @author chao
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface NoTrim {
		/**
		 * 不希望trim的参数
		 * @return
		 */
		String[] value() default "";
	}

}
