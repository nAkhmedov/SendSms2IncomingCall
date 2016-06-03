package com.sms.sendsms.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LogCatDumpReader {
	public static String read() {
		try {
			//reading logcat fil by 1 Mb, if it is more than 1 Mb it creates new one
			Process process = Runtime.getRuntime().exec("logcat -t 200000");
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			StringBuilder logBuilder = new StringBuilder();

			String line;
			while ((line = inputReader.readLine()) != null) {
				logBuilder.append(line);
				logBuilder.append("\n");
			}
			return logBuilder.toString();
		} catch (Exception e) {
			//this shouldn't really happen, but who knows...
			return "Exception occured while reading LogCat " + e.getMessage();
			//e.printStackTrace();
		}
	}
}
