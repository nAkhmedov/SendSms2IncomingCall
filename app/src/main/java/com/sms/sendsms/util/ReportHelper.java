package com.sms.sendsms.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.format.DateFormat;

import com.sms.sendsms.constants.ContextConstants;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReportHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportHelper.class);
    private final static String REPORT_DIR = "report";
    private final static String LOG_EXT = "log";
    private final static String LOGCAT_FILE = "logcat" + "." + LOG_EXT;
    private final static String SYSTEMINFO_FILE = "system_info.txt";
    private final static String ERROR_LOG_PREFIX = "error";
    private final static String MAIN_LOG_PREFIX = "main";
    private final static String REPORT_FILE = "report.zis";
    private final static int    LOG_DAYS = 5;

    private Context context;

    public ReportHelper(Context context) {
        this.context = context;
    }

    public File createReport() throws IOException {
        File reportDirectory =
                new File(context.getExternalFilesDir(null)
                        + File.separator
                        + REPORT_DIR);

        if(!reportDirectory.exists()) {
            reportDirectory.mkdir();
        }

        ArrayList<File> reportFiles = new ArrayList<File>();

        //Adding system properties to the report
        try {
            StringBuilder systemPropertiesBuilder = new StringBuilder();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            systemPropertiesBuilder.append("Application Version: ");
            systemPropertiesBuilder.append(versionName);
            systemPropertiesBuilder.append("\n");
            systemPropertiesBuilder.append("OS Version: ");
            systemPropertiesBuilder.append(Build.VERSION.RELEASE);
            systemPropertiesBuilder.append("\n");
            systemPropertiesBuilder.append("Phone Model: ");
            systemPropertiesBuilder.append(getPhoneModel());
            File systemInfoFile = new File(reportDirectory.getAbsolutePath()
                    + File.separator
                    + SYSTEMINFO_FILE);
            FileUtils.copyInputStreamToFile(new ByteArrayInputStream(systemPropertiesBuilder.toString().getBytes()), systemInfoFile);
            reportFiles.add(systemInfoFile);
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            LOGGER.error("Cannot get sysinfo, NameNotFoundException: " + ex.getMessage());
        }

        String logcatReport = LogCatDumpReader.read();
        File logcatFile = new File(reportDirectory.getAbsolutePath()
                + File.separator
                + LOGCAT_FILE);

        FileUtils.copyInputStreamToFile(new ByteArrayInputStream(logcatReport.getBytes()), logcatFile);

        reportFiles.add(logcatFile);

//		// Collect homebridge logs for last LOG_DAYS
        Calendar cal = Calendar.getInstance();
        Date logDate = new Date();
        cal.setTime(logDate);
        for (int i = 0; i < LOG_DAYS; i++) {
            //add error logs file to reportFiles
            String errorLogName = "";
            if (i == 0) {
                errorLogName = ERROR_LOG_PREFIX + "." + LOG_EXT;
            } else errorLogName = ERROR_LOG_PREFIX + "_" + DateFormat.format("yyMMdd", cal.getTime()) + "." + LOG_EXT;
            File errorLogFile = new File(ContextConstants.DATA_PATH + File.separator + "logs" + File.separator + errorLogName);
            if (errorLogFile.exists()) {
                reportFiles.add(errorLogFile);
            }

            //add main logs file to reportFiles
            String mainLogName = "";
            if (i == 0) {
                mainLogName = MAIN_LOG_PREFIX + "." + LOG_EXT;
            } else mainLogName = MAIN_LOG_PREFIX + "_" + DateFormat.format("yyMMdd", cal.getTime()) + "." + LOG_EXT;

            File mainLogFile = new File(ContextConstants.DATA_PATH + File.separator + "logs" + File.separator + mainLogName);
            if (mainLogFile.exists()) {
                reportFiles.add(mainLogFile);
            }

            // Decrease day
            cal.add(Calendar.DATE, -1);
        }

        reportFiles.add(new File(ContextConstants.DATA_PATH + File.separator + "logs" + File.separator + "send_sms.log"));

        File reportFile = new File(reportDirectory.getAbsolutePath()
                + File.separator
                + REPORT_FILE);

        Zipper.zip(reportFiles.toArray(new File[]{}), reportFile);

        return reportFile;
    }


    private String getPhoneModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public boolean deleteReportDir() {
        File reportDirectory =
                new File(context.getExternalFilesDir(null)
                        + File.separator
                        + REPORT_DIR);
        return FileUtil.deleteRecursive(reportDirectory);
    }
}
