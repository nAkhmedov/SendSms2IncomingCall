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
public class PinterestCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinterestCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference pinterestAddress;
    private ColorPreference pinterestLabelColor;
    private ColorPreference pinterestIconColor;
    private EditTextPreference pinterestLabel;
    private CheckBoxPreference ifPinterest;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pinterest_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Pinterest item
        pinterestAddress = (EditTextPreference) findPreference("pinterest_address");
        pinterestLabelColor = (ColorPreference) findPreference("pinterest_label_color");
        pinterestIconColor = (ColorPreference) findPreference("pinterest_icon_color");
        pinterestLabel = (EditTextPreference) findPreference("pinterest_label");
        ifPinterest = (CheckBoxPreference) findPreference("ifpinterest");

        pinterestAddress.setText(business.getPinterestAddress());
        pinterestAddress.setSummary(business.getPinterestAddress());
        pinterestLabelColor.setColor(Color.parseColor(business.getPinterestLabelColor()));
        pinterestIconColor.setColor(Color.parseColor(business.getPinterestIconColor()));
        pinterestLabel.setText(business.getPinterestLabel());
        pinterestLabel.setSummary(business.getPinterestLabel());
        ifPinterest.setChecked(business.getIfPinterest());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "pinterest_address": {
                String addressValue = sharedPreferences.getString(key, "");
                pinterestAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "pinterest_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "pinterest_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "pinterest_label": {
                String labelValue = sharedPreferences.getString(key, "");
                pinterestLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifpinterest": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifPinterest.setChecked(ifValue);
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
