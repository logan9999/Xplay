package com.xuan.lx.xplayer.util;

public class CodeToSting {
	public static String codeToString(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "gbk");
		} catch (Exception e) {
			return str;
		}
	}

	public static String gbkToIso8859(String str) {
		try {
			return new String(str.getBytes("gbk"), "ISO-8859-1");
		} catch (Exception e) {
			return str;
		}
	}

	public static String codeToUTF8(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return str;
		}
	}
}
