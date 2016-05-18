package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.rarepebble.colorpicker.ColorPreference;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.database.Business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 10.05.2016.
 */
public class AndroidCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference androidAddress;
    private ColorPreference androidLabelColor;
    private ColorPreference androidIconColor;
    private EditTextPreference androidLabel;
    private CheckBoxPreference ifAndroid;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.android_app_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Android App item
        androidAddress = (EditTextPreference) findPreference("android_address");
        androidLabelColor = (ColorPreference) findPreference("android_label_color");
        androidIconColor = (ColorPreference) findPreference("android_icon_color");
        androidLabel = (EditTextPreference) findPreference("android_label");
        ifAndroid = (CheckBoxPreference) findPreference("ifandroid");

        androidAddress.setText(business.getAndroidAddress());
        androidAddress.setSummary(business.getAndroidAddress());
        androidLabelColor.setColor(Color.parseColor(business.getAndroidLabelColor()));
        androidIconColor.setColor(Color.parseColor(business.getAndroidIconColor()));
        androidLabel.setText(business.getAndroidLabel());
        androidLabel.setSummary(business.getAndroidLabel());
        ifAndroid.setChecked(business.getIfAndroid());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "android_address": {
                String addressValue = sharedPreferences.getString(key, "");
                androidAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "android_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "android_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "android_label": {
                String labelValue = sharedPreferences.getString(key, "");
                androidLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifandroid": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifAndroid.setChecked(ifValue);
                ((EditCardActivity) getActivity()).updateCard(key, ifValue);
                break;
            }
        }
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
