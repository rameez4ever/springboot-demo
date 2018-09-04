package com.raydar.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtil {

	public static String getStringFromFile(File file) throws IOException {
	    FileInputStream fin = new FileInputStream(file);
	    String ret = convertStreamToString(fin);
	    //Make sure you close all streams.
	    fin.close();
	    return ret;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line).append("\n");
	    }
	    return sb.toString();
	}
	
	public static void writeJsonFile(File file, String json) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			bufferedWriter.write(json);
		}
	}
}
