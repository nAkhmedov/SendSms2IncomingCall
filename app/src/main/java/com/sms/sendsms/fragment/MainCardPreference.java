package com.sms.sendsms.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;
import com.sms.sendsms.execution.CustomHTTPService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Navruz on 10.05.2016.
 */
public class MainCardPreference extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private final static String TAG_FRAGMENT_BUS_CARD = "TAG_FRAGMENT_BUS_CARD";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.main_pref_content);
        Preference businessPref = findPreference("a_bus_name");
        businessPref.setOnPreferenceClickListener(this);
        sendRequest2Data();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_update: {
                updateBusinessData();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequest2Data() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ContextConstants.APP_URL)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
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

                long id = ApplicationLoader.getApplication(getActivity())
                        .getDaoSession()
                        .getBusinessDao()
                        .insert(business);
                EditCardActivity.businessId = id;
            }
        }).start();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "a_bus_name": {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new BusinessCardPreference(), TAG_FRAGMENT_BUS_CARD)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case "a_bg_img": {

                break;
            }
            case "a_bg_color": {

                break;
            }
            case "a_header": {

                break;
            }
            case "a_footer": {

                break;
            }
            case "a_main_label_txt": {

                break;
            }
            case "a_general": {

                break;
            }
            case "a_mail": {

                break;
            }
            case "a_facebook": {

                break;
            }
            case "a_twitter": {

                break;
            }
            case "a_linked_in": {

                break;
            }
            case "a_google_plus": {

                break;
            }
            case "a_youtube": {

                break;
            }
            case "a_phone": {

                break;
            }
            case "a_gallery": {

                break;
            }
            case "a_about": {

                break;
            }
            case "a_website": {

                break;
            }
            case "a_map": {

                break;
            }
            case "a_pinterest": {

                break;
            }
            case "a_chat": {

                break;
            }
            case "a_android_app": {

                break;
            }
            case "a_user_plus": {

                break;
            }

        }
        return true;
    }

    private void updateBusinessData() {

    }
}
