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
public class LinkedInCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkedInCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference linkedAddress;
    private ColorPreference linkedLabelColor;
    private ColorPreference linkedIconColor;
    private EditTextPreference linkedLabel;
    private CheckBoxPreference ifLinkedIn;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.linkedin_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //LinkedIn item
        linkedAddress = (EditTextPreference) findPreference("linkedin_address");
        linkedLabelColor = (ColorPreference) findPreference("linkedin_label_color");
        linkedIconColor = (ColorPreference) findPreference("linkedin_icon_color");
        linkedLabel = (EditTextPreference) findPreference("linkedin_label");
        ifLinkedIn = (CheckBoxPreference) findPreference("iflinkedin");

        linkedAddress.setText(business.getLinkedinAddress());
        linkedAddress.setSummary(business.getLinkedinAddress());
        linkedLabelColor.setColor(Color.parseColor(business.getLinkedinLabelColor()));
        linkedIconColor.setColor(Color.parseColor(business.getLinkedinIconColor()));
        linkedLabel.setText(business.getLinkedinLabel());
        linkedLabel.setSummary(business.getLinkedinLabel());
        ifLinkedIn.setChecked(business.getIfLinkedin());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "linkedin_address": {
                String addressValue = sharedPreferences.getString(key, "");
                linkedAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "linkedin_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "linkedin_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "linkedin_label": {
                String labelValue = sharedPreferences.getString(key, "");
                linkedLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "iflinkedin": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifLinkedIn.setChecked(ifValue);
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
