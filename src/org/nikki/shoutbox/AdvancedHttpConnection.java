package org.nikki.shoutbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.nikki.shoutbox.util.HttpUtil;

/**
 * A basic class which uses 1 main url to provide get/post functions with cookie
 * support
 * 
 * @author Nikki
 * 
 */
public class AdvancedHttpConnection {

	private static CookieManager manager;

	static {
		CookieHandler.setDefault(manager = new CookieManager());
	}

	private String url;

	public AdvancedHttpConnection(String url) {
		if (url.charAt(url.length() - 1) != '/') {
			url += "/";
		}
		this.url = url;
	}

	public String get(String file) throws IOException {
		StringBuilder contents = new StringBuilder();
		HttpURLConnection connection = (HttpURLConnection) new URL(url + file)
				.openConnection();
		connection
				.setRequestProperty("User-Agent", HttpUtil.getHttpUserAgent());
		connection.setInstanceFollowRedirects(false);
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		try {
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				contents.append(line).append("\n");
			}
		} finally {
			reader.close();
			connection.disconnect();
		}
		return contents.toString();
	}

	public String post(String file, HashMap<String, Object> values)
			throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url + file)
				.openConnection();
		connection.setDoOutput(true);
		connection
				.setRequestProperty("User-Agent", HttpUtil.getHttpUserAgent());
		connection.setInstanceFollowRedirects(false);
		connection.connect();

		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream());
		String constr = HttpUtil.implode(values);
		writer.write(constr);
		writer.flush();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		StringBuilder contents = new StringBuilder();
		try {
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				contents.append(line).append("\n");
			}
		} finally {
			try {
				reader.close();
				writer.close();
			} catch(Exception e) {
				//Nothing
			}
			connection.disconnect();
		}
		return contents.toString();
	}

	public void dumpCookies() {
		CookieStore cookieJar = manager.getCookieStore();
		List<HttpCookie> cookies = cookieJar.getCookies();
		for (HttpCookie cookie : cookies) {
			System.out.println(cookie);
		}
	}
}
