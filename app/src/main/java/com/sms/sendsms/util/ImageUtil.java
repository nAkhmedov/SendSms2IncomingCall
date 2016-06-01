package com.sms.sendsms.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.fragment.BusinessCardPreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.UUID;

import okhttp3.ResponseBody;
import static com.sms.sendsms.util.StreamHelper.close;
import static com.sms.sendsms.util.StreamHelper.flush;

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

    public static String downScaleAndSaveImage(String image, int scale) throws Exception {

        FileOutputStream stream = null;
        BufferedInputStream bstream = null;
        Bitmap bitmap;
        try {
            BitmapFactory.Options optionsForGettingDimensions = new BitmapFactory.Options();
            optionsForGettingDimensions.inJustDecodeBounds = true;
            BufferedInputStream boundsOnlyStream = new BufferedInputStream(new FileInputStream(image));
            bitmap = BitmapFactory.decodeStream(boundsOnlyStream, null, optionsForGettingDimensions);
            if (bitmap != null) {
                bitmap.recycle();
            }
            if (boundsOnlyStream != null) {
                boundsOnlyStream.close();
            }
            int w, l;
            w = optionsForGettingDimensions.outWidth;
            l = optionsForGettingDimensions.outHeight;

            ExifInterface exif = new ExifInterface(image);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            int rotate = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = -90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            int what = w > l ? w : l;

            BitmapFactory.Options options = new BitmapFactory.Options();
            if (scale != -1) {
                if (what > 3000) {
                    options.inSampleSize = scale * 6;
                } else if (what > 2000 && what <= 3000) {
                    options.inSampleSize = scale * 5;
                } else if (what > 1500 && what <= 2000) {
                    options.inSampleSize = scale * 4;
                } else if (what > 1000 && what <= 1500) {
                    options.inSampleSize = scale * 3;
                } else if (what > 400 && what <= 1000) {
                    options.inSampleSize = scale * 2;
                } else {
                    options.inSampleSize = scale;
                }
            } else { // our instep logic
                int maxWidth, maxHeight;
                if (what > 3000) {
                    maxWidth = 800;
                    maxHeight = 800;
                } else if (what > 2000 && what <= 3000) {
                    maxWidth = 750;
                    maxHeight = 750;
                } else if (what > 1500 && what <= 2000) {
                    maxWidth = 700;
                    maxHeight = 700;
                } else if (what > 1000 && what <= 1500) {
                    maxWidth = 650;
                    maxHeight = 650;
                } else if (what > 400 && what <= 1000) {
                    maxWidth = Math.min(what, 600);
                    maxHeight = Math.min(what, 600);
                } else {
                    maxWidth = w;
                    maxHeight = l;
                }
                options.inSampleSize = (int) Math.max(w / maxWidth, l / maxHeight);
//              HLog.i("Mact", "w : " + w + " l:"+l + " maxWidth : " + maxWidth + " maxHeight : " + maxHeight + " scale:" + options.inSampleSize);
                if (options.inSampleSize < 1) {
                    options.inSampleSize = 1;
                }
            }

            options.inJustDecodeBounds = false;
            // TODO: Sometime the decode File Returns null for some images
            // For such cases, thumbnails can't be created.
            // Thumbnails will link to the original file
            BufferedInputStream scaledInputStream = new BufferedInputStream(new FileInputStream(image));
            bitmap = BitmapFactory.decodeStream(scaledInputStream, null, options);
//            verifyBitmap(fileImage, bitmap);
            scaledInputStream.close();
            if (bitmap != null) {
                File original = new File(URLDecoder.decode(image, Charset.defaultCharset().name()));
                File file = new File(
                        (original.getParent() + File.separator + original.getName()
                                .replace(".", "-scale-" + scale + ".")));
                stream = new FileOutputStream(file);
                if (rotate != 0) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotate);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, false);
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
                return file.getAbsolutePath();
            }

        } catch (Exception e) {
//            throw new PickerException("Error while generating thumbnail: " + scale + " " + image);
        } finally {
            close(bstream);
            flush(stream);
            close(stream);
        }

        return null;
    }
}
