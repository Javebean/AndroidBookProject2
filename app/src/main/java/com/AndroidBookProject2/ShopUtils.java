package com.AndroidBookProject2;

public class ShopUtils {
	/**
	 * 转换编码
	 */
	public static String changeToUnicode(String str) {
		StringBuffer strBuff = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String temp = Integer.toHexString(str.charAt(i));
			if (temp.length() != 4) {
				temp = "00" + temp;
			}
			if (temp.equals("00d")) {
				temp = "0" + temp;
			}
			if (temp.equals("00a")) {
				temp = "0" + temp;
			}
			strBuff.append(temp.substring(0, temp.length() - 2));
			strBuff.append(temp.substring(temp.length() - 2, temp.length()));
		}
		String returnData = strBuff.toString();
		return returnData;
	}
}
