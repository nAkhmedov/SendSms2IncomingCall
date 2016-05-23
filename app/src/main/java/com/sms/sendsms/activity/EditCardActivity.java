package com.sms.sendsms.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;
import com.sms.sendsms.database.User;
import com.sms.sendsms.execution.CustomHTTPService;
import com.sms.sendsms.fragment.BusinessCardPreference;
import com.sms.sendsms.fragment.MainCardPreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class EditCardActivity extends AppCompatActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCardPreference.class);

    private final static String TAG_FRAGMENT_MAIN_CARD = "TAG_FRAGMENT_MAIN_CARD";

    public static Business business;
    private User user;

    @Override
    protected  void onCreate ( Bundle savedInstanceState )  {
        super.onCreate (savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainCardPreference(), TAG_FRAGMENT_MAIN_CARD)
                .commit();

        user = ApplicationLoader.getApplication(this)
                .getDaoSession()
                .getUserDao()
                .queryBuilder()
                .unique();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendRequest2Data();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void updateCard(String property, boolean ifValue) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", ifValue);
        sendRequest2Api(property, jsonObject);
    }

    public void updateCard(String property, String value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", value);
        sendRequest2Api(property, jsonObject);
    }

    private void sendRequest2Api(String property, JsonObject jsonObject) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//If need to logging, just uncomment
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContextConstants.APP_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        CustomHTTPService http = retrofit.create(CustomHTTPService.class);

        http.sendCardData(user.getGuid(), property, jsonObject).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LOGGER.info("CHECKING response = " + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable error) {
                LOGGER.info("CHECKING error = " + error);
            }
        });
    }

    private void sendRequest2Data() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//If need to logging, just uncomment
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContextConstants.APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        CustomHTTPService http = retrofit.create(CustomHTTPService.class);
        http.sendBusinessDetailRequest(user.getMessageCode()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                if (jsonObject != null && !jsonObject.entrySet().isEmpty()) {
                    parseJson(jsonObject);
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable error) {
                LOGGER.error("Exception = " + error.getMessage());
                error.printStackTrace();
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
                business.setHeaderColor(jsonObject.get("headercolor").getAsString());
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

                long id = ApplicationLoader.getApplication(EditCardActivity.this)
                        .getDaoSession()
                        .getBusinessDao()
                        .insertOrReplace(business);
                EditCardActivity.business = business;
            }
        }).start();
    }

    public void uploadFile2Api(String property, MultipartBody.Part multipartBody) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//If need to logging, just uncomment
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContextConstants.APP_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        CustomHTTPService http = retrofit.create(CustomHTTPService.class);

        http.uploadFile(user.getGuid(), property, multipartBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LOGGER.info("CHECKING response2 = " + response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable error) {
                LOGGER.info("CHECKING error2 = " + error);
            }
        });
    }
}