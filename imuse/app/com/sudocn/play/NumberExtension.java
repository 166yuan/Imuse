package com.sudocn.play;

import play.templates.JavaExtensions;

public class NumberExtension extends JavaExtensions {

	public static String asRMB(Long balance) {
		Long orginal = balance;
		balance = Math.abs(balance);
		Long end = (balance % 100);
		return (orginal < 0 ? "-" : "") + (balance / 100) + "."
				+ (end < 10 ? "0" + end : end);
	}

}
