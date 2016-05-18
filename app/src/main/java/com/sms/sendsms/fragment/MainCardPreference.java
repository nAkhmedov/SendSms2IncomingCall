package com.sms.sendsms.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.google.gson.JsonObject;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;
import com.sms.sendsms.execution.CustomHTTPService;
import com.sms.sendsms.util.ImageUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Navruz on 10.05.2016.
 */
public class MainCardPreference extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainCardPreference.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.main_pref_content);
        Preference businessPref = findPreference("a_bus_name");
        Preference logoPref = findPreference("a_logo");
        Preference bgImgPref = findPreference("a_bg_img");
        Preference bgColorPref = findPreference("a_bg_color");
        Preference headerPref = findPreference("a_header");
        Preference footerPref = findPreference("a_footer");
        Preference mainLabelTxtPref = findPreference("a_main_label_txt");
        Preference generalPref = findPreference("a_general");
        Preference mailPref = findPreference("a_mail");
        Preference facebookPref = findPreference("a_facebook");
        Preference twitterPref = findPreference("a_twitter");
        Preference linkedInPref = findPreference("a_linked_in");
        Preference googlePlusPref = findPreference("a_google_plus");
        Preference youtubePref = findPreference("a_youtube");
        Preference phonePref = findPreference("a_phone");
        Preference galleryPref = findPreference("a_gallery");
        Preference aboutPref = findPreference("a_about");
        Preference websitePref = findPreference("a_website");
        Preference mapPref = findPreference("a_map");
        Preference pinterestPref = findPreference("a_pinterest");
        Preference chatPref = findPreference("a_chat");
        Preference androidAppPref = findPreference("a_android_app");
        Preference userPlusPref = findPreference("a_user_plus");
        businessPref.setOnPreferenceClickListener(this);
        logoPref.setOnPreferenceClickListener(this);
        bgImgPref.setOnPreferenceClickListener(this);
        bgColorPref.setOnPreferenceClickListener(this);
        headerPref.setOnPreferenceClickListener(this);
        footerPref.setOnPreferenceClickListener(this);
        mainLabelTxtPref.setOnPreferenceClickListener(this);
        generalPref.setOnPreferenceClickListener(this);
        mailPref.setOnPreferenceClickListener(this);
        facebookPref.setOnPreferenceClickListener(this);
        twitterPref.setOnPreferenceClickListener(this);
        linkedInPref.setOnPreferenceClickListener(this);
        googlePlusPref.setOnPreferenceClickListener(this);
        youtubePref.setOnPreferenceClickListener(this);
        phonePref.setOnPreferenceClickListener(this);
        galleryPref.setOnPreferenceClickListener(this);
        aboutPref.setOnPreferenceClickListener(this);
        websitePref.setOnPreferenceClickListener(this);
        mapPref.setOnPreferenceClickListener(this);
        pinterestPref.setOnPreferenceClickListener(this);
        chatPref.setOnPreferenceClickListener(this);
        androidAppPref.setOnPreferenceClickListener(this);
        userPlusPref.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "a_bus_name": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new BusinessCardPreference(), ContextConstants.TAG_FRAGMENT_BUS_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_logo": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new LogoCardPreference(), ContextConstants.TAG_FRAGMENT_LOGO_CARD)
                        .addToBackStack(null)
                        .commit();

                break;
            }
            case "a_bg_img": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new BgImgCardPreference(), ContextConstants.TAG_FRAGMENT_BG_IMG_CARD)
                        .addToBackStack(null)
                        .commit();

                break;
            }
            case "a_bg_color": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new BgColorCardPreference(), ContextConstants.TAG_FRAGMENT_BG_COLOR_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_header": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new HeaderColorCardPreference(), ContextConstants.TAG_FRAGMENT_HEADER_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_footer": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new FooterColorCardPreference(), ContextConstants.TAG_FRAGMENT_FOOTER_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_main_label_txt": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new MainTextCardPreference(), ContextConstants.TAG_FRAGMENT_MAIN_TXT_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_general": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new GeneralCardPreference(), ContextConstants.TAG_FRAGMENT_GENERAL_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_mail": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new MailCardPreference(), ContextConstants.TAG_FRAGMENT_MAIL_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_facebook": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new FBCardPreference(), ContextConstants.TAG_FRAGMENT_FB_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_twitter": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new TwitCardPreference(), ContextConstants.TAG_FRAGMENT_TW_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_linked_in": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new LinkedInCardPreference(), ContextConstants.TAG_FRAGMENT_LIN_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_google_plus": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new GoogleCardPreference(), ContextConstants.TAG_FRAGMENT_GOOGLE_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_youtube": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new YoutubeCardPreference(), ContextConstants.TAG_FRAGMENT_YOUTUBE_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_phone": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new PhoneCardPreference(), ContextConstants.TAG_FRAGMENT_PHONE_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_gallery": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new GalleryCardPreference(), ContextConstants.TAG_FRAGMENT_GALLERY_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_about": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new AboutCardPreference(), ContextConstants.TAG_FRAGMENT_ABOUT_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_website": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new WebsiteCardPreference(), ContextConstants.TAG_FRAGMENT_WEB_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_map": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new MapCardPreference(), ContextConstants.TAG_FRAGMENT_MAP_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_pinterest": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new PinterestCardPreference(), ContextConstants.TAG_FRAGMENT_PIN_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_chat": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new ChatCardPreference(), ContextConstants.TAG_FRAGMENT_CHAT_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_android_app": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new AndroidCardPreference(), ContextConstants.TAG_FRAGMENT_ANDR_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_user_plus": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new UserPlusCardPreference(), ContextConstants.TAG_FRAGMENT_USER_PLUS_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }

        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
