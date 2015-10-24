package com.skholingua.android.json_data_to_listview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpURLConnection {

	public static String getData(String uri) {

		BufferedReader reader = null;

		try {
			URL url = new URL(uri);			
			HttpURLConnection con = (HttpURLConnection) url
					.openConnection();
			con.setReadTimeout(10000);
			con.setConnectTimeout(15000);
			con.setDoInput(true);
			con.connect();
			InputStream is = con.getInputStream();
			reader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));
			String data = null;
			String webPage = "";
			while ((data = reader.readLine()) != null) {
				webPage += data + "\n";
			}
			return webPage;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
}
