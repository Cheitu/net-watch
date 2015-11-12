package com.cheitu.watch;

public class Utils {
	
	/**
	 * byte to string
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

	
	public static boolean isEmpty(String st){
		if(st == null )
			return true;
		if(st.trim().length()==0)
			return true;
		return false;
	}
}
