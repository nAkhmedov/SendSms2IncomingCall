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
public class AboutCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(AboutCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference aboutAddress;
    private ColorPreference aboutLabelColor;
    private ColorPreference aboutIconColor;
    private ColorPreference aboutTextColor;
    private EditTextPreference aboutLabel;
    private CheckBoxPreference ifAbout;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //About item
        aboutAddress = (EditTextPreference) findPreference("about_address");
        aboutLabelColor = (ColorPreference) findPreference("about_label_color");
        aboutIconColor = (ColorPreference) findPreference("about_icon_color");
        aboutTextColor = (ColorPreference) findPreference("about_text_color");
        aboutLabel = (EditTextPreference) findPreference("about_label");
        ifAbout = (CheckBoxPreference) findPreference("ifabout");

        aboutAddress.setText(business.getAboutAddress());
        aboutAddress.setSummary(business.getAboutAddress());
        aboutLabelColor.setColor(Color.parseColor(business.getAboutLabelColor()));
        aboutIconColor.setColor(Color.parseColor(business.getAboutIconColor()));
//        aboutTextColor.setColor(Color.parseColor(business.getAboutTextColor()));
        aboutLabel.setText(business.getAboutLabel());
        aboutLabel.setSummary(business.getAboutLabel());
        ifAbout.setChecked(business.getIfAbout());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "about_address": {
                String addressValue = sharedPreferences.getString(key, "");
                aboutAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "about_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "about_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "about_text_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "about_label": {
                String labelValue = sharedPreferences.getString(key, "");
                aboutLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifabout": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifAbout.setChecked(ifValue);
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
