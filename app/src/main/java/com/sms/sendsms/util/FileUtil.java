package com.sms.sendsms.util;

import com.sms.sendsms.constants.ContextConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	public static void copyFromInputStream(InputStream originalInputStream, File copy) {		
		try {
			if (!copy.exists()) {
				copy.createNewFile();
			}

			OutputStream out = new FileOutputStream(copy);

			byte[] buf = new byte[1024];
			int len;
			while ((len = originalInputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			originalInputStream.close();
			out.close();
		} catch (IOException e) {
            LOGGER.error("FileUtil", e.getMessage());
		}
	}

    public static File createEncryptMsgFile() {
        File encryptsDir = new File(ContextConstants.DATA_PATH + "encrypt");
        if (!encryptsDir.exists()) {
            LOGGER.info("making encrypt dir");
            if (!encryptsDir.mkdir())
                LOGGER.error("cant create dir " + encryptsDir.getAbsolutePath());
        }
        String encryptFilePath = encryptsDir.getAbsolutePath() +
                File.separator +
                new Date().getTime() +
                ".txt";
        File file = new File(encryptFilePath);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File createMsgFile() {
        File encryptsDir = new File(ContextConstants.DATA_PATH + File.separator + "emails");
        if (!encryptsDir.exists()) {
            LOGGER.info("making encrypt dir");
            if (!encryptsDir.mkdir())
                LOGGER.error("cant create dir " + encryptsDir.getAbsolutePath());
        }
        String encryptFilePath = encryptsDir.getAbsolutePath() +
                File.separator +
                new Date().getTime() +
                ".txt";
        File file = new File(encryptFilePath);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        return fileOrDirectory.delete();
    }
}
