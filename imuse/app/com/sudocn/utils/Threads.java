package com.sudocn.utils;

/**
 * 线程工具
 * @author chao
 *
 */
public class Threads {

	public static void sleep(long millTime) {
		try {
			Thread.sleep(millTime);
		} catch (Exception e) {
		}
	}

}
