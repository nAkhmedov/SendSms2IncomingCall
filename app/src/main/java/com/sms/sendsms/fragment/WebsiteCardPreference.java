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
public class WebsiteCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference websiteAddress;
    private ColorPreference websiteLabelColor;
    private ColorPreference websiteIconColor;
    private EditTextPreference websiteLabel;
    private CheckBoxPreference ifWebsite;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.website_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Website item
        websiteAddress = (EditTextPreference) findPreference("website_address");
        websiteLabelColor = (ColorPreference) findPreference("website_label_color");
        websiteIconColor = (ColorPreference) findPreference("website_icon_color");
        websiteLabel = (EditTextPreference) findPreference("website_label");
        ifWebsite = (CheckBoxPreference) findPreference("ifwebsite");

        websiteAddress.setText(business.getWebsiteAddress());
        websiteAddress.setSummary(business.getWebsiteAddress());
        websiteLabelColor.setColor(Color.parseColor(business.getWebsiteLabelColor()));
        websiteIconColor.setColor(Color.parseColor(business.getWebsiteIconColor()));
        websiteLabel.setText(business.getWebsiteLabel());
        websiteLabel.setSummary(business.getWebsiteLabel());
        ifWebsite.setChecked(business.getIfWebsite());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "website_address": {
                String addressValue = sharedPreferences.getString(key, "");
                websiteAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "website_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "website_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "website_label": {
                String labelValue = sharedPreferences.getString(key, "");
                websiteLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifwebsite": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifWebsite.setChecked(ifValue);
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
