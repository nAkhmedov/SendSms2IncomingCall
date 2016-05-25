package com.sms.sendsms.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.fragment.BusinessCardPreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import okhttp3.ResponseBody;

/**
 * Created by Navruz on 18.05.2016.
 */
public class ImageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCardPreference.class);

    public static String encodeToBase64(Bitmap image) {
        Bitmap img = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        LOGGER.info("Image Log:" + imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static File writeResponseBodyToDisk(ResponseBody body) {
        try {
            File imgDirectory = new File(ContextConstants.APP_IMG_PATH);
            imgDirectory.mkdirs();
            File imgFile = new File(imgDirectory + File.separator + generateUniqueFileName() + ".png");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(imgFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    LOGGER.debug("file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();

                return imgFile;
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("writeResponseBodyToDisk error = " + e.getMessage());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("writeResponseBodyToDisk error2 = " + e.getMessage());
            return null;
        }
    }

    public static Bitmap convertFile2Bitmap(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, blob);
        return bitmap;
    }

    private static String generateUniqueFileName() {
        return UUID.randomUUID().toString();
    }
}
