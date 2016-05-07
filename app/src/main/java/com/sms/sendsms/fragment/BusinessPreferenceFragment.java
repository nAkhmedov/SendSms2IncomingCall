package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonObject;
import com.rarepebble.colorpicker.ColorPreference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.sms.sendsms.R;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;
import com.sms.sendsms.execution.CustomHTTPService;

/**
 * Created by Navruz on 25.04.2016.
 */
public class BusinessPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;


    private EditTextPreference businessName;
    private ColorPreference businessNameColor;
    private CheckBoxPreference ifBusinessName;

    private EditTextPreference logoName;
    private Preference logoFile;
    private CheckBoxPreference ifLogo;

    private EditTextPreference bgText;
    private Preference bgFile;

    private ColorPreference bgColor;

    private ColorPreference headerColor;
    private CheckBoxPreference ifHeader;

    private ColorPreference footerColor;
    private CheckBoxPreference ifFooter;
    private ColorPreference footerIconsColor;
    private ColorPreference footerIconsBg;

    private EditTextPreference mainText;
    private ColorPreference mainTextColor;

    private EditTextPreference mailAddress;
    private ColorPreference mailColor;
    private ColorPreference mailIconColor;
    private EditTextPreference mailLabel;
    private CheckBoxPreference ifMail;

    private EditTextPreference fbAddress;
    private ColorPreference fbLabelColor;
    private ColorPreference fbIconColor;
    private EditTextPreference fbLabel;
    private CheckBoxPreference ifFacebook;

    private EditTextPreference twtAddress;
    private ColorPreference twtLabelColor;
    private ColorPreference twtIconColor;
    private EditTextPreference twtLabel;
    private CheckBoxPreference ifTwitter;

    private EditTextPreference linkedAddress;
    private ColorPreference linkedLabelColor;
    private ColorPreference linkedIconColor;
    private EditTextPreference linkedLabel;
    private CheckBoxPreference ifLinkedIn;

    private EditTextPreference googlePlusAddress;
    private ColorPreference googlePlusLabelColor;
    private ColorPreference googlePlusIconColor;
    private EditTextPreference googlePlusLabel;
    private CheckBoxPreference ifGooglePlus;

    private EditTextPreference youtubeAddress;
    private ColorPreference youtubeLabelColor;
    private ColorPreference youtubeIconColor;
    private EditTextPreference youtubeLabel;
    private CheckBoxPreference ifYoutube;

    private EditTextPreference phoneAddress;
    private ColorPreference phoneLabelColor;
    private ColorPreference phoneIconColor;
    private EditTextPreference phoneLabel;
    private CheckBoxPreference ifPhone;

    private EditTextPreference galleryAddress;
    private ColorPreference galleryLabelColor;
    private ColorPreference galleryIconColor;
    private EditTextPreference galleryLabel;
    private CheckBoxPreference ifGallery;

    private EditTextPreference aboutAddress;
    private ColorPreference aboutLabelColor;
    private ColorPreference aboutIconColor;
    private ColorPreference aboutTextColor;
    private EditTextPreference aboutLabel;
    private CheckBoxPreference ifAbout;

    private EditTextPreference websiteAddress;
    private ColorPreference websiteLabelColor;
    private ColorPreference websiteIconColor;
    private EditTextPreference websiteLabel;
    private CheckBoxPreference ifWebsite;

    private EditTextPreference mapAddress;
    private ColorPreference mapLabelColor;
    private ColorPreference mapIconColor;
    private EditTextPreference mapLabel;
    private CheckBoxPreference ifMap;

    private EditTextPreference pinterestAddress;
    private ColorPreference pinterestLabelColor;
    private ColorPreference pinterestIconColor;
    private EditTextPreference pinterestLabel;
    private CheckBoxPreference ifPinterest;

    private EditTextPreference chatLabel;
    private ColorPreference chatLabelColor;
    private ColorPreference chatIconColor;
    private CheckBoxPreference ifChat;

    private EditTextPreference androidAddress;
    private ColorPreference androidLabelColor;
    private ColorPreference androidIconColor;
    private EditTextPreference androidLabel;
    private CheckBoxPreference ifAndroid;

    private ColorPreference userPlusLabelColor;
    private ColorPreference userPlusIconColor;
    private EditTextPreference userPlusLabel;
    private CheckBoxPreference ifUserPlus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        addPreferencesFromResource(R.xml.pref_content);
        addPreferencesFromResource(R.xml.general_pref_content);
        addPreferencesFromResource(R.xml.mail_pref_content);
        addPreferencesFromResource(R.xml.fb_pref_content);
        addPreferencesFromResource(R.xml.twitter_pref_content);
        addPreferencesFromResource(R.xml.linkedin_pref_content);
        addPreferencesFromResource(R.xml.googleplus_pref_content);
        addPreferencesFromResource(R.xml.youtube_pref_content);
        addPreferencesFromResource(R.xml.phone_pref_content);
        addPreferencesFromResource(R.xml.gallery_pref_content);
        addPreferencesFromResource(R.xml.about_pref_content);
        addPreferencesFromResource(R.xml.website_pref_content);
        addPreferencesFromResource(R.xml.map_pref_content);
        addPreferencesFromResource(R.xml.pinterest_pref_content);
        addPreferencesFromResource(R.xml.chat_pref_content);
        addPreferencesFromResource(R.xml.android_app_pref_content);
        addPreferencesFromResource(R.xml.save_contact_pref_content);

        //Business item
        businessName = (EditTextPreference) findPreference("business_name");
        businessNameColor = (ColorPreference) findPreference("business_color_code");
        ifBusinessName = (CheckBoxPreference) findPreference("if_business_name");

        //Logo item
        logoName = (EditTextPreference) findPreference("logo_name");
        logoFile = findPreference("logo_file");
        ifLogo = (CheckBoxPreference) findPreference("if_logo");

        //Background item
        bgText = (EditTextPreference) findPreference("bg_text");
        bgFile = findPreference("bg_img_file");

        //Bg color item
        bgColor = (ColorPreference) findPreference("bg_color_code");

        //Header color item
        headerColor = (ColorPreference) findPreference("header_color_code");
        ifHeader = (CheckBoxPreference) findPreference("show_color_header");

        //Color footer item
        footerColor = (ColorPreference) findPreference("footer_color_code");
        ifFooter = (CheckBoxPreference) findPreference("show_color_footer");
        footerIconsColor = (ColorPreference) findPreference("color_icons_code");
        footerIconsBg= (ColorPreference) findPreference("color_bg_icons_code");

        //Main text item
        mainText = (EditTextPreference) findPreference("label_text");
        mainTextColor = (ColorPreference) findPreference("color_label_txt_code");

        //Mail item
        mailAddress = (EditTextPreference) findPreference("mail_name");
        mailColor = (ColorPreference) findPreference("mail_color_code");
        mailIconColor = (ColorPreference) findPreference("mail_color_icon_code");
        mailLabel = (EditTextPreference) findPreference("mail_label");
        ifMail = (CheckBoxPreference) findPreference("if_mail");

        //Facebook item
        fbAddress = (EditTextPreference) findPreference("fb_address");
        fbLabelColor = (ColorPreference) findPreference("fb_label_color");
        fbIconColor = (ColorPreference) findPreference("fb_icon_color");
        fbLabel = (EditTextPreference) findPreference("fb_label");
        ifFacebook = (CheckBoxPreference) findPreference("if_facebook");

        //Twitter item
        twtAddress = (EditTextPreference) findPreference("twt_address");
        twtLabelColor = (ColorPreference) findPreference("twt_label_color");
        twtIconColor = (ColorPreference) findPreference("twt_icon_color");
        twtLabel = (EditTextPreference) findPreference("twt_label");
        ifTwitter = (CheckBoxPreference) findPreference("if_twitter");

        //LinkedIn item
        linkedAddress = (EditTextPreference) findPreference("linked_in_address");
        linkedLabelColor = (ColorPreference) findPreference("linked_in_label_color");
        linkedIconColor = (ColorPreference) findPreference("linked_in_icon_color");
        linkedLabel = (EditTextPreference) findPreference("linked_in_label");
        ifLinkedIn = (CheckBoxPreference) findPreference("if_linked_in");

        //Google+ item
        googlePlusAddress = (EditTextPreference) findPreference("google_plus_address");
        googlePlusLabelColor = (ColorPreference) findPreference("google_plus_label_color");
        googlePlusIconColor = (ColorPreference) findPreference("google_plus_icon_color");
        googlePlusLabel = (EditTextPreference) findPreference("google_plus_label");
        ifGooglePlus = (CheckBoxPreference) findPreference("if_google_plus");

        //Youtube item
        youtubeAddress = (EditTextPreference) findPreference("youtube_address");
        youtubeLabelColor = (ColorPreference) findPreference("youtube_label_color");
        youtubeIconColor = (ColorPreference) findPreference("youtube_icon_color");
        youtubeLabel = (EditTextPreference) findPreference("youtube_label");
        ifYoutube = (CheckBoxPreference) findPreference("if_youtube");

        //Phone item
        phoneAddress = (EditTextPreference) findPreference("phone_address");
        phoneLabelColor = (ColorPreference) findPreference("phone_label_color");
        phoneIconColor = (ColorPreference) findPreference("phone_icon_color");
        phoneLabel = (EditTextPreference) findPreference("phone_label");
        ifPhone = (CheckBoxPreference) findPreference("if_phone");

        //Gallery item
        galleryAddress = (EditTextPreference) findPreference("gallery_address");
        galleryLabelColor = (ColorPreference) findPreference("gallery_label_color");
        galleryIconColor = (ColorPreference) findPreference("gallery_icon_color");
        galleryLabel = (EditTextPreference) findPreference("gallery_label");
        ifGallery = (CheckBoxPreference) findPreference("if_gallery");

        //About item
        aboutAddress = (EditTextPreference) findPreference("about_address");
        aboutLabelColor = (ColorPreference) findPreference("about_label_color");
        aboutIconColor = (ColorPreference) findPreference("about_icon_color");
        aboutTextColor = (ColorPreference) findPreference("about_text_color");
        aboutLabel = (EditTextPreference) findPreference("about_label");
        ifAbout = (CheckBoxPreference) findPreference("if_about");

        //Website item
        websiteAddress = (EditTextPreference) findPreference("website_address");
        websiteLabelColor = (ColorPreference) findPreference("website_label_color");
        websiteIconColor = (ColorPreference) findPreference("website_icon_color");
        websiteLabel = (EditTextPreference) findPreference("website_label");
        ifWebsite = (CheckBoxPreference) findPreference("if_website");

        //Map item
        mapAddress = (EditTextPreference) findPreference("map_address");
        mapLabelColor = (ColorPreference) findPreference("map_label_color");
        mapIconColor = (ColorPreference) findPreference("map_icon_color");
        mapLabel = (EditTextPreference) findPreference("map_label");
        ifMap = (CheckBoxPreference) findPreference("if_map");

        //Pinterest item
        pinterestAddress = (EditTextPreference) findPreference("pinterest_address");
        pinterestLabelColor = (ColorPreference) findPreference("pinterest_label_color");
        pinterestIconColor = (ColorPreference) findPreference("pinterest_icon_color");
        pinterestLabel = (EditTextPreference) findPreference("pinterest_label");
        ifPinterest = (CheckBoxPreference) findPreference("if_pinterest");

        //Chat item
        chatLabel = (EditTextPreference) findPreference("chat_label");
        chatLabelColor = (ColorPreference) findPreference("chat_label_color");
        chatIconColor = (ColorPreference) findPreference("chat_icon_color");
        ifChat = (CheckBoxPreference) findPreference("if_chat");

        //Android App item
        androidAddress = (EditTextPreference) findPreference("android_address");
        androidLabelColor = (ColorPreference) findPreference("android_label_color");
        androidIconColor = (ColorPreference) findPreference("android_icon_color");
        androidLabel = (EditTextPreference) findPreference("android_label");
        ifAndroid = (CheckBoxPreference) findPreference("if_android");

        //Save Contact item
        userPlusLabelColor = (ColorPreference) findPreference("user_plus_label_color");
        userPlusIconColor = (ColorPreference) findPreference("user_plus_icon_color");
        userPlusLabel = (EditTextPreference) findPreference("user_plus_label");
        ifUserPlus = (CheckBoxPreference) findPreference("if_user_plus");

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "business_name": {
                String businessText = sharedPreferences.getString("business_name", " ");
                businessName.setSummary(businessText);
                break;
            }
            case "logo_name": {
                String logoText = sharedPreferences.getString("logo_name", "");
                logoName.setSummary(logoText);
                break;
            }
            case "bg_text": {
                String bgTextValue = sharedPreferences.getString("bg_text", "");
                bgText.setSummary(bgTextValue);
                break;
            }
            case "label_text": {
                String labelTextValue = sharedPreferences.getString("label_text", "");
                mainText.setSummary(labelTextValue);
                break;
            }
            case "mail_name": {
                String mailNameValue = sharedPreferences.getString("mail_name", "");
                mailAddress.setSummary(mailNameValue);
                break;
            }
            case "mail_label": {
                String mailLabelValue = sharedPreferences.getString("mail_label", "");
                mailLabel.setSummary(mailLabelValue);
                break;
            }
            case "fb_address": {
                String fbAddressValue = sharedPreferences.getString("fb_address", "");
                fbAddress.setSummary(fbAddressValue);
                break;
            }
            case "fb_label": {
                String fbLabelValue = sharedPreferences.getString("fb_label", "");
                fbLabel.setSummary(fbLabelValue);
                break;
            }
            case "twt_address": {
                String twtAddressValue = sharedPreferences.getString("twt_address", "");
                twtAddress.setSummary(twtAddressValue);
                break;
            }
            case "twt_label": {
                String twtLabelValue = sharedPreferences.getString("twt_label", "");
                twtLabel.setSummary(twtLabelValue);
                break;
            }
            case "linked_in_address": {
                String linkedAddressValue = sharedPreferences.getString("linked_in_address", "");
                linkedAddress.setSummary(linkedAddressValue);
                break;
            }
            case "linked_in_label": {
                String linkedLabelValue = sharedPreferences.getString("linked_in_label", "");
                linkedLabel.setSummary(linkedLabelValue);
                break;
            }
            case "google_plus_address": {
                String googleAddressValue = sharedPreferences.getString("google_plus_address", "");
                googlePlusAddress.setSummary(googleAddressValue);
                break;
            }
            case "google_plus_label": {
                String googleLabelValue = sharedPreferences.getString("google_plus_label", "");
                googlePlusLabel.setSummary(googleLabelValue);
                break;
            }
            case "youtube_address": {
                String youtubeAddressValue = sharedPreferences.getString("youtube_address", "");
                youtubeAddress.setSummary(youtubeAddressValue);
                break;
            }
            case "youtube_label": {
                String youtubeLabelValue = sharedPreferences.getString("youtube_label", "");
                youtubeLabel.setSummary(youtubeLabelValue);
                break;
            }
            case "phone_address": {
                String phoneAddressValue = sharedPreferences.getString("phone_address", "");
                phoneAddress.setSummary(phoneAddressValue);
                break;
            }
            case "phone_label": {
                String phoneLabelValue = sharedPreferences.getString("phone_label", "");
                phoneLabel.setSummary(phoneLabelValue);
                break;
            }
            case "gallery_address": {
                String galleryAddressValue = sharedPreferences.getString("gallery_address", "");
                galleryAddress.setSummary(galleryAddressValue);
                break;
            }
            case "gallery_label": {
                String galleryLabelValue = sharedPreferences.getString("gallery_label", "");
                galleryLabel.setSummary(galleryLabelValue);
                break;
            }
            case "about_address": {
                String aboutAddressValue = sharedPreferences.getString("about_address", "");
                aboutAddress.setSummary(aboutAddressValue);
                break;
            }
            case "about_label": {
                String aboutLabelValue = sharedPreferences.getString("about_label", "");
                aboutLabel.setSummary(aboutLabelValue);
                break;
            }
            case "website_address": {
                String webAddressValue = sharedPreferences.getString("website_address", "");
                websiteAddress.setSummary(webAddressValue);
                break;
            }
            case "website_label": {
                String webLabelValue = sharedPreferences.getString("website_label", "");
                websiteLabel.setSummary(webLabelValue);
                break;
            }
            case "map_address": {
                String youtubeAddressValue = sharedPreferences.getString("map_address", "");
                mapAddress.setSummary(youtubeAddressValue);
                break;
            }
            case "map_label": {
                String youtubeLabelValue = sharedPreferences.getString("map_label", "");
                mapLabel.setSummary(youtubeLabelValue);
                break;
            }
            case "pinterest_address": {
                String pinterestAddressValue = sharedPreferences.getString("pinterest_address", "");
                pinterestAddress.setSummary(pinterestAddressValue);
                break;
            }
            case "pinterest_label": {
                String pinterestLabelValue = sharedPreferences.getString("pinterest_label", "");
                pinterestLabel.setSummary(pinterestLabelValue);
                break;
            }
            case "chat_label": {
                String chatLabelValue = sharedPreferences.getString("chat_label", "");
                chatLabel.setSummary(chatLabelValue);
                break;
            }
            case "android_address": {
                String androidAddressValue = sharedPreferences.getString("android_address", "");
                androidAddress.setSummary(androidAddressValue);
                break;
            }
            case "android_label": {
                String androidLabelValue = sharedPreferences.getString("android_label", "");
                androidLabel.setSummary(androidLabelValue);
                break;
            }
            case "user_plus_label": {
                String userPlusLabelValue = sharedPreferences.getString("user_plus_label", "");
                userPlusLabel.setSummary(userPlusLabelValue);
                break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        sendRequest2Data();
    }

    private void sendRequest2Data() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ContextConstants.APP_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        CustomHTTPService http = restAdapter.create(CustomHTTPService.class);
        http.sendBusinessDetailRequest("13933", new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                if (jsonObject != null && !jsonObject.entrySet().isEmpty()) {
                    parseJson(jsonObject);
                } else {

                }

            }

            @Override
            public void failure(RetrofitError error) {
                int i = 0;
                i++;
            }
        });
    }

    private void parseJson(final JsonObject jsonObject) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Business business = new Business();
                business.setStoreCode(jsonObject.get("storecode").getAsString());
                business.setBusinessName(jsonObject.get("businessname").getAsString());
                business.setLogo(jsonObject.get("logo").getAsString());
                business.setBackgroundImage(jsonObject.get("backgroundimage").getAsString());
                business.setBackgroundColor(jsonObject.get("backgroundcolor").getAsString());
                business.setCustomHeaderColor(jsonObject.get("headercolor").getAsString());
                business.setFooterColor(jsonObject.get("footercolor").getAsString());
                business.setIfLogo(jsonObject.get("iflogo").getAsBoolean());
                business.setIfMail(jsonObject.get("ifmail").getAsBoolean());
                business.setIfFacebook(jsonObject.get("iffacebook").getAsBoolean());
                business.setIfTwitter(jsonObject.get("iftwitter").getAsBoolean());
                business.setIfLinkedin(jsonObject.get("iflinkedin").getAsBoolean());
                business.setIfGoogleplus(jsonObject.get("ifgoogleplus").getAsBoolean());
                business.setIfYoutube(jsonObject.get("ifyoutube").getAsBoolean());
                business.setIfPhone(jsonObject.get("ifphone").getAsBoolean());
                business.setIfGallery(jsonObject.get("ifgallery").getAsBoolean());
                business.setIfAbout(jsonObject.get("ifabout").getAsBoolean());
                business.setIfWebsite(jsonObject.get("ifwebsite").getAsBoolean());
                business.setIfMap(jsonObject.get("ifmap").getAsBoolean());
                business.setIfPinterest(jsonObject.get("ifpinterest").getAsBoolean());
                business.setIfAndroid(jsonObject.get("ifandroid").getAsBoolean());
                business.setMainText(jsonObject.get("maintext").getAsString());
                business.setMailIconColor(jsonObject.get("mail_icon_color").getAsString());
                business.setMailLabelColor(jsonObject.get("mail_label_color").getAsString());
                business.setMailLabel(jsonObject.get("mail_label").getAsString());
                business.setFacebookIconColor(jsonObject.get("facebook_icon_color").getAsString());
                business.setFacebookLabelColor(jsonObject.get("facebook_label_color").getAsString());
                business.setFacebookLabel(jsonObject.get("facebook_label").getAsString());
                business.setTwitterIconColor(jsonObject.get("twitter_icon_color").getAsString());
                business.setTwitterLabelColor(jsonObject.get("twitter_label_color").getAsString());
                business.setTwitterLabel(jsonObject.get("twitter_label").getAsString());
                business.setLinkedinIconColor(jsonObject.get("linkedin_icon_color").getAsString());
                business.setLinkedinLabelColor(jsonObject.get("linkedin_label_color").getAsString());
                business.setLinkedinLabel(jsonObject.get("linkedin_label").getAsString());
                business.setGoogleplusIconColor(jsonObject.get("googleplus_icon_color").getAsString());
                business.setGoogleplusLabelColor(jsonObject.get("googleplus_label_color").getAsString());
                business.setGoogleplusLabel(jsonObject.get("googleplus_label").getAsString());
                business.setMailAddress(jsonObject.get("mail_address").getAsString());
                business.setFacebookAddress(jsonObject.get("facebook_address").getAsString());
                business.setTwitterAddress(jsonObject.get("twitter_address").getAsString());
                business.setLinkedinAddress(jsonObject.get("linkedin_address").getAsString());
                business.setGoogleplusAddress(jsonObject.get("googleplus_address").getAsString());
                business.setYoutubeIconColor(jsonObject.get("youtube_icon_color").getAsString());
                business.setYoutubeLabelColor(jsonObject.get("youtube_label_color").getAsString());
                business.setYoutubeLabel(jsonObject.get("youtube_label").getAsString());
                business.setYoutubeAddress(jsonObject.get("youtube_address").getAsString());
                business.setPhoneLabelColor(jsonObject.get("phone_label_color").getAsString());
                business.setPhoneIconColor(jsonObject.get("phone_icon_color").getAsString());
                business.setPhoneLabel(jsonObject.get("phone_label").getAsString());
                business.setPhoneAddress(jsonObject.get("phone_address").getAsString());
                business.setGalleryIconColor(jsonObject.get("gallery_icon_color").getAsString());
                business.setGalleryLabelColor(jsonObject.get("gallery_label_color").getAsString());
                business.setGalleryLabel(jsonObject.get("gallery_label").getAsString());
                business.setGalleryAddress(jsonObject.get("gallery_address").getAsString());
                business.setAboutIconColor(jsonObject.get("about_icon_color").getAsString());
                business.setAboutLabelColor(jsonObject.get("about_label_color").getAsString());
                business.setAboutLabel(jsonObject.get("about_label").getAsString());
                business.setAboutAddress(jsonObject.get("about_address").getAsString());
                business.setWebsiteIconColor(jsonObject.get("website_icon_color").getAsString());
                business.setWebsiteLabelColor(jsonObject.get("website_label_color").getAsString());
                business.setWebsiteLabel(jsonObject.get("website_label").getAsString());
                business.setWebsiteAddress(jsonObject.get("website_address").getAsString());
                business.setMapIconColor(jsonObject.get("map_icon_color").getAsString());
                business.setMapLabelColor(jsonObject.get("map_label_color").getAsString());
                business.setMapLabel(jsonObject.get("map_label").getAsString());
                business.setMapAddress(jsonObject.get("map_address").getAsString());
                business.setPinterestIconColor(jsonObject.get("pinterest_icon_color").getAsString());
                business.setPinterestLabelColor(jsonObject.get("pinterest_label_color").getAsString());
                business.setPinterestLabel(jsonObject.get("pinterest_label").getAsString());
                business.setPinterestAddress(jsonObject.get("pinterest_address").getAsString());
                business.setAndroidIconColor(jsonObject.get("android_icon_color").getAsString());
                business.setAndroidLabelColor(jsonObject.get("android_label_color").getAsString());
                business.setAndroidLabel(jsonObject.get("android_label").getAsString());
                business.setAndroidAddress(jsonObject.get("android_address").getAsString());
                business.setIfBusinessname(jsonObject.get("ifbusinessname").getAsBoolean());
                business.setBusinessnameColor(jsonObject.get("businessname_color").getAsString());
                business.setMaintextColor(jsonObject.get("maintext_color").getAsString());
                business.setIfFooter(jsonObject.get("iffooter").getAsBoolean());
                business.setIfHeader(jsonObject.get("ifheader").getAsBoolean());
                business.setIfUserplus(jsonObject.get("ifuserplus").getAsBoolean());
                business.setUserplusIconColor(jsonObject.get("userplus_icon_color").getAsString());
                business.setUserplusLabelColor(jsonObject.get("userplus_label_color").getAsString());
                business.setUserplusLabel(jsonObject.get("userplus_label").getAsString());
                business.setBitly(jsonObject.get("bitly").getAsString());
                business.setMaskyoo(jsonObject.get("maskyoo").getAsString());
                business.setActive(jsonObject.get("active").getAsBoolean());
                business.setAboutTextColor(jsonObject.get("about_text_color").getAsString());
                business.setFooterIconsColor(jsonObject.get("footer_icons_color").getAsString());
                business.setFooterIconsBackground(jsonObject.get("footer_icons_background").getAsString());
                business.setRealPhone(jsonObject.get("realphone").getAsString());
                business.setMonthlySms(jsonObject.get("monthlysms").getAsInt());
                business.setChatIconColor(jsonObject.get("chat_icon_color").getAsString());
                business.setChatLabelColor(jsonObject.get("chat_label_color").getAsString());
                business.setChatLabel(jsonObject.get("chat_label").getAsString());
                business.setIfChat(jsonObject.get("ifchat").getAsBoolean());
                business.setTest(jsonObject.get("test").getAsInt());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fillUiWithData(business);
                    }
                });
            }
        }).start();
    }

    private void fillUiWithData(Business business) {
        Log.i("TAG", "business" + business.toString());
//        prefs.edit().putString("business_name", business.getBusinessName()).apply();
//        businessName.setPersistent(true);
        businessName.setText(business.getBusinessName());
        businessName.setSummary(business.getBusinessName());
//        businessNameColor.setColor(Color.parseColor(business.getBusinessnameColor()));
        ifBusinessName.setChecked(business.getIfBusinessname());

//        logoName.setSummary(business.getLogo());
        logoFile.setSummary(business.getLogo());
        ifLogo.setChecked(business.getIfLogo());

//        bgText.setSummary(business.getBackgroundImage());
        bgFile.setSummary(business.getBackgroundImage());
        bgColor.setColor(Color.parseColor(business.getBackgroundColor()));
        headerColor.setColor(Color.parseColor(business.getHeaderColor()));
        ifHeader.setChecked(business.getIfHeader());
        footerColor.setColor(Color.parseColor(business.getFooterColor()));
        ifFooter.setChecked(business.getIfFooter());
//        footerIconsColor.setColor(Color.parseColor(business.getFooterIconsColor()));
        footerIconsBg.setColor(Color.parseColor(business.getFooterIconsBackground()));
        mainText.setText(business.getMainText());
        mainText.setSummary(business.getMainText());
//        mainTextColor.setColor(Color.parseColor(business.getMaintextColor()));
        mailAddress.setText(business.getMailAddress());
        mailAddress.setSummary(business.getMailAddress());
        mailColor.setColor(Color.parseColor(business.getMailLabelColor()));
        mailIconColor.setColor(Color.parseColor(business.getMailIconColor()));
        mailLabel.setText(business.getMailLabel());
        mailLabel.setSummary(business.getMailLabel());
        ifMail.setChecked(business.getIfMail());

        fbAddress.setText(business.getFacebookAddress());
        fbAddress.setSummary(business.getFacebookAddress());
        fbLabelColor.setColor(Color.parseColor(business.getFacebookLabelColor()));
        fbIconColor.setColor(Color.parseColor(business.getFacebookIconColor()));
        fbLabel.setText(business.getFacebookLabel());
        fbLabel.setSummary(business.getFacebookLabel());
        ifFacebook.setChecked(business.getIfFacebook());

        twtAddress.setText(business.getFacebookAddress());
        twtAddress.setSummary(business.getFacebookAddress());
        twtLabelColor.setColor(Color.parseColor(business.getFacebookLabelColor()));
        twtIconColor.setColor(Color.parseColor(business.getFacebookIconColor()));
        twtLabel.setText(business.getFacebookLabel());
        twtLabel.setSummary(business.getFacebookLabel());
        ifTwitter.setChecked(business.getIfFacebook());

        linkedAddress.setText(business.getFacebookAddress());
        linkedAddress.setSummary(business.getFacebookAddress());
        linkedLabelColor.setColor(Color.parseColor(business.getFacebookLabelColor()));
        linkedIconColor.setColor(Color.parseColor(business.getFacebookIconColor()));
        linkedLabel.setText(business.getFacebookLabel());
        linkedLabel.setSummary(business.getFacebookLabel());
        ifLinkedIn.setChecked(business.getIfFacebook());

        googlePlusAddress.setText(business.getGoogleplusAddress());
        googlePlusAddress.setSummary(business.getGoogleplusAddress());
        googlePlusLabelColor.setColor(Color.parseColor(business.getGoogleplusLabelColor()));
        googlePlusIconColor.setColor(Color.parseColor(business.getGoogleplusIconColor()));
        googlePlusLabel.setText(business.getGoogleplusLabel());
        googlePlusLabel.setSummary(business.getGoogleplusLabel());
        ifGooglePlus.setChecked(business.getIfGoogleplus());

        youtubeAddress.setText(business.getYoutubeAddress());
        youtubeAddress.setSummary(business.getYoutubeAddress());
        youtubeLabelColor.setColor(Color.parseColor(business.getYoutubeLabelColor()));
        youtubeIconColor.setColor(Color.parseColor(business.getYoutubeIconColor()));
        youtubeLabel.setText(business.getYoutubeLabel());
        youtubeLabel.setSummary(business.getYoutubeLabel());
        ifYoutube.setChecked(business.getIfYoutube());

        phoneAddress.setText(business.getPhoneAddress());
        phoneAddress.setSummary(business.getPhoneAddress());
        phoneLabelColor.setColor(Color.parseColor(business.getPhoneLabelColor()));
        phoneIconColor.setColor(Color.parseColor(business.getPhoneIconColor()));
        phoneLabel.setText(business.getPhoneLabel());
        phoneLabel.setSummary(business.getPhoneLabel());
        ifPhone.setChecked(business.getIfPhone());

        galleryAddress.setText(business.getGalleryAddress());
        galleryAddress.setSummary(business.getGalleryAddress());
        galleryLabelColor.setColor(Color.parseColor(business.getGalleryLabelColor()));
        galleryIconColor.setColor(Color.parseColor(business.getGalleryIconColor()));
        galleryLabel.setText(business.getGalleryLabel());
        galleryLabel.setSummary(business.getGalleryLabel());
        ifGallery.setChecked(business.getIfGallery());

        aboutAddress.setText(business.getAboutAddress());
        aboutAddress.setSummary(business.getAboutAddress());
        aboutLabelColor.setColor(Color.parseColor(business.getAboutLabelColor()));
        aboutIconColor.setColor(Color.parseColor(business.getAboutIconColor()));
//        aboutTextColor.setColor(Color.parseColor(business.getAboutTextColor()));
        aboutLabel.setText(business.getAboutLabel());
        aboutLabel.setSummary(business.getAboutLabel());
        ifAbout.setChecked(business.getIfAbout());

        websiteAddress.setText(business.getWebsiteAddress());
        websiteAddress.setSummary(business.getWebsiteAddress());
        websiteLabelColor.setColor(Color.parseColor(business.getWebsiteLabelColor()));
        websiteIconColor.setColor(Color.parseColor(business.getWebsiteIconColor()));
        websiteLabel.setText(business.getWebsiteLabel());
        websiteLabel.setSummary(business.getWebsiteLabel());
        ifWebsite.setChecked(business.getIfWebsite());

        mapAddress.setText(business.getMapAddress());
        mapAddress.setSummary(business.getMapAddress());
        mapLabelColor.setColor(Color.parseColor(business.getMapLabelColor()));
        mapIconColor.setColor(Color.parseColor(business.getMapIconColor()));
        mapLabel.setText(business.getMapLabel());
        mapLabel.setSummary(business.getMapLabel());
        ifMap.setChecked(business.getIfMap());

        pinterestAddress.setText(business.getPinterestAddress());
        pinterestAddress.setSummary(business.getPinterestAddress());
        pinterestLabelColor.setColor(Color.parseColor(business.getPinterestLabelColor()));
        pinterestIconColor.setColor(Color.parseColor(business.getPinterestIconColor()));
        pinterestLabel.setText(business.getPinterestLabel());
        pinterestLabel.setSummary(business.getPinterestLabel());
        ifPinterest.setChecked(business.getIfPinterest());

        chatLabel.setText(business.getChatLabel());
        chatLabel.setSummary(business.getChatLabel());
        chatLabelColor.setColor(Color.parseColor(business.getChatLabelColor()));
        chatIconColor.setColor(Color.parseColor(business.getChatIconColor()));
        ifChat.setChecked(business.getIfChat());

        androidAddress.setText(business.getAndroidAddress());
        androidAddress.setSummary(business.getAndroidAddress());
        androidLabelColor.setColor(Color.parseColor(business.getAndroidLabelColor()));
        androidIconColor.setColor(Color.parseColor(business.getAndroidIconColor()));
        androidLabel.setText(business.getAndroidLabel());
        androidLabel.setSummary(business.getAndroidLabel());
        ifAndroid.setChecked(business.getIfAndroid());

        userPlusLabelColor.setColor(Color.parseColor(business.getUserplusLabelColor()));
        userPlusIconColor.setColor(Color.parseColor(business.getUserplusIconColor()));
        userPlusLabel.setText(business.getUserplusLabel());
        userPlusLabel.setSummary(business.getUserplusLabel());
        ifUserPlus.setChecked(business.getIfUserplus());
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (prefs != null)
            prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (prefs != null)
            prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
