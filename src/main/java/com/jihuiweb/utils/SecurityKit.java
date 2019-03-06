package com.jihuiweb.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SecurityKit {
	private static String MIXSTR = "@#$*@&@^!hhu^";
	private static char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; 
	public static String getMixedMd5Code(String str){
		try {
			 byte[] btInput = (str+MIXSTR).getBytes();
	         // 获得MD5摘要算法的 MessageDigest 对象
	         MessageDigest mdInst = MessageDigest.getInstance("MD5");
			 // 使用指定的字节更新摘要
	         mdInst.update(btInput);
	         // 获得密文
	         byte[] md = mdInst.digest();
	         // 把密文转换成十六进制的字符串形式
	         int j = md.length;
	         char bytes[] = new char[j * 2];
	         int k = 0;
	         for (int i = 0; i < j; i++) {
	             byte byte0 = md[i];
	             bytes[k++] = hexDigits[byte0 >>> 4 & 0xf];
	             bytes[k++] = hexDigits[byte0 & 0xf];
	         }
	         return new String(bytes);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	public static String string2MD5(String inStr){
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	static char [] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
	static int[] IA = new int[256];
	static {
		Arrays.fill(IA, -1);
		for (int i = 0, iS = CA.length; i < iS; i++) {
			IA[CA[i]] = i;
		}
		IA['='] = 0;
	}
	public static final String encode64(String s) {
		// Reuse char[] since we can't create a String incrementally anyway and
		// StringBuffer/Builder would be slower.
		try {
			return new String(encodeToChar(s.getBytes("UTF-8"), false));
		} catch (UnsupportedEncodingException e) {
			System.err.println("Base64 encoding error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private static final char[] encodeToChar(byte[] sArr, boolean lineSep) {
		// Check special case
		int sLen = sArr != null ? sArr.length : 0;
		if (sLen == 0)
			return new char[0];

		int eLen = (sLen / 3) * 3; // Length of even 24-bits.
		int cCnt = ((sLen - 1) / 3 + 1) << 2; // Returned character count
		int dLen = cCnt + (lineSep ? (cCnt - 1) / 76 << 1 : 0); // Length of
		// returned
		// array
		char[] dArr = new char[dLen];

		// Encode even 24-bits
		for (int s = 0, d = 0, cc = 0; s < eLen;) {
			// Copy next three bytes into lower 24 bits of int, paying attension
			// to sign.
			int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8
					| (sArr[s++] & 0xff);

			// Encode the int into four chars
			dArr[d++] = CA[(i >>> 18) & 0x3f];
			dArr[d++] = CA[(i >>> 12) & 0x3f];
			dArr[d++] = CA[(i >>> 6) & 0x3f];
			dArr[d++] = CA[i & 0x3f];

			// Add optional line separator
			if (lineSep && ++cc == 19 && d < dLen - 2) {
				dArr[d++] = '\r';
				dArr[d++] = '\n';
				cc = 0;
			}
		}

		// Pad and encode last bits if source isn't even 24 bits.
		int left = sLen - eLen; // 0 - 2.
		if (left > 0) {
			// Prepare the int
			int i = ((sArr[eLen] & 0xff) << 10)
					| (left == 2 ? ((sArr[sLen - 1] & 0xff) << 2) : 0);

			// Set last four chars
			dArr[dLen - 4] = CA[i >> 12];
			dArr[dLen - 3] = CA[(i >>> 6) & 0x3f];
			dArr[dLen - 2] = left == 2 ? CA[i & 0x3f] : '=';
			dArr[dLen - 1] = '=';
		}
		return dArr;
	}
}
