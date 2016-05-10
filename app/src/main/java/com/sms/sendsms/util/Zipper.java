package com.sms.sendsms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
	static final int BUFFER = 2048;

	public static void zip(File[] inFiles, File outfile) {
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(outfile);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];
			for (File file: inFiles) {
				//System.out.println("Adding: " + inFiles[i]);
                FileInputStream fi = null;
                try {
				    fi = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                    continue;
                }
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
                entry.setTime(file.lastModified());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}