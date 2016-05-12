package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rarepebble.colorpicker.ColorPreference;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;
import com.sms.sendsms.execution.CustomHTTPService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class BusinessCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference businessName;
    private ColorPreference businessNameColor;
    private CheckBoxPreference ifBusinessName;
    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.business_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = ApplicationLoader.getApplication(getActivity())
                .getDaoSession()
                .getBusinessDao()
                .load(EditCardActivity.businessId);

        if (business == null)
            return;
        //Business item
        businessName = (EditTextPreference) findPreference("business_name");
        businessNameColor = (ColorPreference) findPreference("business_color_code");
        ifBusinessName = (CheckBoxPreference) findPreference("if_business_name");

        businessName.setText(business.getBusinessName());
        businessName.setSummary(business.getBusinessName());
//        businessNameColor.setColor(Color.parseColor(business.getBusinessnameColor()));
        ifBusinessName.setChecked(business.getIfBusinessname());

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
                updateCard(businessText);
                break;
            }
        }
    }

    private void updateCard(String itemValue) {
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
        http.sendCardData("13933", itemValue).enqueue(new Callback<String>() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack();
                break;
            }
        }
        return true;
    }
}
