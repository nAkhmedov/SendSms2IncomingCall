package com.sms.sendsms.constants;

import com.sms.sendsms.ApplicationLoader;

public class ContextConstants {

    public static final String APP_URL = "http://www.yesplease.co.il/app";
    public static final String DATA_PATH = ApplicationLoader.getAppContext().getExternalFilesDir(null) + "/data";
    public static final String PHONE_NUMBER_FORMAT_F = "05";
    public static final String PHONE_NUMBER_FORMAT_S = "+9725";
    public static final long DAY_PERIOD = 3;
}
