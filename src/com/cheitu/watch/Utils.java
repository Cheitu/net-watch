package com.cheitu.watch;

public class Utils {

	/**
	 * byte to string
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			if (i < src.length - 1) {
				stringBuilder.append("-");
			}
		}
		return stringBuilder.toString().toUpperCase();
	}

	public static boolean isEmpty(String st) {
		if (st == null)
			return true;
		if (st.trim().length() == 0)
			return true;
		return false;
	}

	public static boolean isResponse(String s) {

		boolean result = false;

		if (s.startsWith("HTTP/1.0") || s.startsWith("HTTP/1.1")) {
			result = true;
		 
		}

		return result;
	}

	public static boolean isRequest(String s) {
		boolean result = false;

		if (s.startsWith("GET") || s.startsWith("POST") || s.startsWith("HEAD")
				|| s.startsWith("OPTIONS") || s.startsWith("PUT") 
				|| s.startsWith("DELETE") || s.startsWith("TARCE")) {
			result = true;
			 
		}

		return result;
	}

}
