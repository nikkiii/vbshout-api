package org.nikki.shoutbox.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Basic vBulletin functions like login etc
 * @author Nikki
 *
 */
public class VBulletinUtil {
	public static HashMap<String, Object> constructLogin(String username, String password) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("do", "login");
		map.put("securitytoken", "guest");
		map.put("s", "");
		map.put("cookieuser", "1");
		map.put("vb_login_username", username);
		map.put("vb_login_password", password);
		return map;
	}
	
	public static String md5Hex(String string) {
		return md5Hex(string.getBytes());
	}
	
	public static String md5Hex(byte[] data) {
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(data);
			byte messageDigest[] = algorithm.digest();
		            
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<messageDigest.length;i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HashMap<String, Object> constructShout(String string) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("do", "shout");
		try {
			map.put("message", URLEncoder.encode(string, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}
}
