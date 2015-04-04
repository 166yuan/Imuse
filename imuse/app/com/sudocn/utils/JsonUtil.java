package com.sudocn.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Json工具
 * 
 * @author chao
 * 
 */
public class JsonUtil {

	/**
	 * 将json转成指定类型的对象，优先使用setter来赋值
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T convertTo(String jsonStr, Class<T> clazz) {
		JsonObject json = new JsonParser().parse(jsonStr).getAsJsonObject();
		return convertTo(json, clazz);
	}

	/**
	 * 将json转成指定类型的对象，优先使用setter来赋值
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T convertTo(JsonObject json, Class<T> clazz) {
		if (json == null) {
			return null;
		}

		Field[] fields = clazz.getFields();
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (Exception e) { // 必须有无参构造函数
			throw new RuntimeException(e);
		}

		for (Field f : fields) {
			String fieldName = f.getName();

			JsonElement el = json.get(f.getName());
			if (el == null) { // 如果json没有这个数据，则不设置该值
				continue;
			}

			String capFieldName = StringUtils.capitalize(fieldName);

			String setter = "set" + capFieldName;

			Method setterMethod = null;

			try {
				setterMethod = clazz.getDeclaredMethod(setter, f.getType());
			} catch (Exception e) {

			}

			if (setterMethod == null) {
				setFieldWithJson(instance, f, el);
			} else {
				setMethodWithJson(instance, setterMethod, f, el);
			}

		}
		return instance;
	}

	static void setFieldWithJson(Object instance, Field f, JsonElement el){
		Class type = f.getType();
		Object val = getObject(type, el);
		if(val == null){
			return ;
		}
		try {
			if(val instanceof JsonObject){
				val = convertTo((JsonObject)val , type);
			}
			f.set(instance, val);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static void setMethodWithJson(Object instance, Method m, Field f,
			JsonElement el) {
		Class type = f.getType();
		Object val = getObject(type, el);
		if(val == null){
			return ;
		}
		try {
			if(val instanceof JsonObject){
				val = convertTo((JsonObject)val , type);
			}
			m.invoke(instance, val);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Object getObject(Class type, JsonElement el) {
		if(el.isJsonNull()){
			return null;
		}
		 if(type.isArray()){
			JsonArray jsonArr = el.getAsJsonArray();
			Class componentType = type.getComponentType();
			Object javaArr = Array.newInstance(componentType, jsonArr.size());
			int size = jsonArr.size();
			for(int i = 0; i < size; i ++){
				Object val = getObject(componentType, jsonArr.get(i));
				if(val instanceof JsonObject){
					val = convertTo((JsonObject)val , componentType);
				}
				Array.set(javaArr, i, val);
			}
			return javaArr;
		}else if (type.equals(Integer.class) || type.getName().equals("int")) {
			return el.getAsInt();
		} else if (type.equals(String.class)) {
			return el.getAsString();
		} else if (type.equals(Double.class) 
				|| type.getName().equals("float")) {
			return el.getAsFloat();
		} else if (type.equals(Double.class) 
				|| type.getName().equals("double")) {
			return el.getAsDouble();
		} else if (type.equals(Boolean.class)
				|| type.getName().equals("boolean")) {
			return el.getAsBoolean();
		} else if (type.equals(Byte.class)
				|| type.getName().equals("byte")) {
			return el.getAsByte();
		} else if (type.equals(Long.class)
				|| type.getName().equals("long")) {
			return el.getAsLong();
		} else {
			if(el.isJsonNull()){
				return null;
			}else if(el.isJsonArray()){
				return el.getAsJsonArray();
			}else if(el.isJsonPrimitive()){
				return el.getAsJsonPrimitive();
			}else if(el.isJsonObject()){
				return el.getAsJsonObject();
			}else{
				return null;
			}
		}
	}

	// 以下是测试代码

//	public static void main(String[] args) {
//		TestObject obj = convertTo(
//				"{name:'张子超', age: 23, _age: 20, sex: true, _sex: false, " +
//				"contents: ['fuck', 'you'], " +
//				"data: {dataMsg: 'This is not for you!'}, " +
//				"datas: [{dataMsg: 'Hello!'}, {dataMsg: 'World!'}]}",
//				TestObject.class);
//		System.out.println(XJavaExtension.toJson(obj));
//	}
//	
//	static class TestObject {
//	
//		public String name;
//	
//		public int age;
//	
//		public Integer _age;
//	
//		public boolean sex;
//	
//		public Boolean _sex;
//	
//		public TestData data;
//	
//		public String[] contents;
//	
//		public TestData[] datas;
//	
//		public void setName(String n) {
//			name = n + "!!!!!";
//		}
//	}
//	
//	static class TestData {
//	
//		public String dataMsg;
//	}

}
