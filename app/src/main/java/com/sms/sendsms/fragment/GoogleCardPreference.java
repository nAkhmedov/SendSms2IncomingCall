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
public class GoogleCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference googlePlusAddress;
    private ColorPreference googlePlusLabelColor;
    private ColorPreference googlePlusIconColor;
    private EditTextPreference googlePlusLabel;
    private CheckBoxPreference ifGooglePlus;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.googleplus_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Google+ item
        googlePlusAddress = (EditTextPreference) findPreference("googleplus_address");
        googlePlusLabelColor = (ColorPreference) findPreference("googleplus_label_color");
        googlePlusIconColor = (ColorPreference) findPreference("googleplus_icon_color");
        googlePlusLabel = (EditTextPreference) findPreference("googleplus_label");
        ifGooglePlus = (CheckBoxPreference) findPreference("ifgoogleplus");

        googlePlusAddress.setText(business.getGoogleplusAddress());
        googlePlusAddress.setSummary(business.getGoogleplusAddress());
        googlePlusLabelColor.setColor(Color.parseColor(business.getGoogleplusLabelColor()));
        googlePlusIconColor.setColor(Color.parseColor(business.getGoogleplusIconColor()));
        googlePlusLabel.setText(business.getGoogleplusLabel());
        googlePlusLabel.setSummary(business.getGoogleplusLabel());
        ifGooglePlus.setChecked(business.getIfGoogleplus());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "googleplus_address": {
                String addressValue = sharedPreferences.getString(key, "");
                googlePlusAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "googleplus_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard("googleplus_label_color", hexColor);
                break;
            }
            case "googleplus_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "googleplus_label": {
                String labelValue = sharedPreferences.getString(key, "");
                googlePlusLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifgoogleplus": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifGooglePlus.setChecked(ifValue);
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
