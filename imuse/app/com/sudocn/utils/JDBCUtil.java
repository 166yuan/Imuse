package com.sudocn.utils;

import java.lang.reflect.Method;

/**
 * JDBC工具
 * 
 * @author chao
 */
public class JDBCUtil {

	/**
	 * 释放资源（通过反射来调用close方法）
	 * 
	 * @param objects
	 */
	public static void close(Object... objects) {
		for (Object o : objects) {
			if (o != null) {
				try {
					Method m = o.getClass().getMethod("close");
					m.invoke(o);
				} catch (Exception e) {

				}
			}
		}
	}

}
