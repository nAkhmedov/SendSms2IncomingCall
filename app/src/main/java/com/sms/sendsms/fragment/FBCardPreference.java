package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.rarepebble.colorpicker.ColorPreference;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.database.Business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 10.05.2016.
 */
public class FBCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(FBCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference fbAddress;
    private ColorPreference fbLabelColor;
    private ColorPreference fbIconColor;
    private EditTextPreference fbLabel;
    private CheckBoxPreference ifFacebook;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fb_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Facebook item
        fbAddress = (EditTextPreference) findPreference("facebook_address");
        fbLabelColor = (ColorPreference) findPreference("facebook_label_color");
        fbIconColor = (ColorPreference) findPreference("facebook_icon_color");
        fbLabel = (EditTextPreference) findPreference("facebook_label");
        ifFacebook = (CheckBoxPreference) findPreference("iffacebook");

        fbAddress.setText(business.getFacebookAddress());
        fbAddress.setSummary(business.getFacebookAddress());
        fbLabelColor.setColor(Color.parseColor(business.getFacebookLabelColor()));
        fbIconColor.setColor(Color.parseColor(business.getFacebookIconColor()));
        fbLabel.setText(business.getFacebookLabel());
        fbLabel.setSummary(business.getFacebookLabel());
        ifFacebook.setChecked(business.getIfFacebook());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "facebook_address": {
                String addressValue = sharedPreferences.getString(key, "");
                fbAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "facebook_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "facebook_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "facebook_label": {
                String labelValue = sharedPreferences.getString(key, "");
                fbLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "iffacebook": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifFacebook.setChecked(ifValue);
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
