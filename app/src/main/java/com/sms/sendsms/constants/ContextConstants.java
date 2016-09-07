package com.sms.sendsms.constants;

import com.sms.sendsms.ApplicationLoader;

public class ContextConstants {

    public static final String PACKAGE_NAME = ApplicationLoader.getAppContext().getPackageName();

    public static final String APP_URL = "http://www.yesplease.co.il/app/";
    public static final String CARD_URL = "http://www.yes-please.co.il/card/";
    public static final String DATA_PATH = ApplicationLoader.getAppContext().getExternalFilesDir(null) + "/data";
    public static final String APP_IMG_PATH = DATA_PATH + "/images";
    public static final String PHONE_NUMBER_FORMAT_F = "05";
    public static final String PHONE_NUMBER_FORMAT_S = "+9725";
    public static final long EVERYTIME_PERIOD = 0;
    public static final long ONE_DAY_PERIOD = 1;
    public static final long THREE_DAY_PERIOD = 3;
    public static final long WEEK_DAY_PERIOD = 7;
    public static final long MONTH_DAY_PERIOD = 30;
    public static final int REPORT_REQUEST_CODE = 527;
    public static final int SELECT_BG_IMG = 528;
    public static final int SELECT_LOGO_IMG = 529;

    public final static String TAG_FRAGMENT_BUS_CARD = "TAG_FRAGMENT_BUS_CARD";
    public final static String TAG_FRAGMENT_LOGO_CARD = "TAG_FRAGMENT_LOGO_CARD";
    public final static String TAG_FRAGMENT_BG_IMG_CARD = "TAG_FRAGMENT_BG_IMG_CARD";
    public final static String TAG_FRAGMENT_BG_COLOR_CARD = "TAG_FRAGMENT_BG_COLOR_CARD";
    public final static String TAG_FRAGMENT_HEADER_CARD = "TAG_FRAGMENT_HEADER_CARD";
    public final static String TAG_FRAGMENT_FOOTER_CARD = "TAG_FRAGMENT_FOOTER_CARD";
    public final static String TAG_FRAGMENT_MAIN_TXT_CARD = "TAG_FRAGMENT_MAIN_TXT_CARD";
    public final static String TAG_FRAGMENT_GENERAL_CARD = "TAG_FRAGMENT_GENERAL_CARD";
    public final static String TAG_FRAGMENT_MAIL_CARD = "TAG_FRAGMENT_MAIL_CARD";
    public final static String TAG_FRAGMENT_FB_CARD = "TAG_FRAGMENT_FB_CARD";
    public final static String TAG_FRAGMENT_TW_CARD = "TAG_FRAGMENT_TW_CARD";
    public final static String TAG_FRAGMENT_LIN_CARD = "TAG_FRAGMENT_LIN_CARD";
    public final static String TAG_FRAGMENT_GOOGLE_CARD = "TAG_FRAGMENT_GOOGLE_CARD";
    public final static String TAG_FRAGMENT_YOUTUBE_CARD = "TAG_FRAGMENT_YOUTUBE_CARD";
    public final static String TAG_FRAGMENT_PHONE_CARD = "TAG_FRAGMENT_PHONE_CARD";
    public final static String TAG_FRAGMENT_GALLERY_CARD = "TAG_FRAGMENT_GALLERY_CARD";
    public final static String TAG_FRAGMENT_ABOUT_CARD = "TAG_FRAGMENT_ABOUT_CARD";
    public final static String TAG_FRAGMENT_WEB_CARD = "TAG_FRAGMENT_WEB_CARD";
    public final static String TAG_FRAGMENT_MAP_CARD = "TAG_FRAGMENT_MAP_CARD";
    public final static String TAG_FRAGMENT_PIN_CARD = "TAG_FRAGMENT_PIN_CARD";
    public final static String TAG_FRAGMENT_CHAT_CARD = "TAG_FRAGMENT_CHAT_CARD";
    public final static String TAG_FRAGMENT_ANDR_CARD = "TAG_FRAGMENT_ANDR_CARD";
    public final static String TAG_FRAGMENT_USER_PLUS_CARD = "TAG_FRAGMENT_USER_PLUS_CARD";
    public final static String TAG_FRAGMENT_SETTINGS = "TAG_FRAGMENT_SETTINGS";
}
