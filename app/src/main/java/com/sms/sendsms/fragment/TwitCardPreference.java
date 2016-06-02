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
public class TwitCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference twtAddress;
    private ColorPreference twtLabelColor;
    private ColorPreference twtIconColor;
    private EditTextPreference twtLabel;
    private CheckBoxPreference ifTwitter;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.twitter_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Twitter item
        twtAddress = (EditTextPreference) findPreference("twitter_address");
        twtLabelColor = (ColorPreference) findPreference("twitter_label_color");
        twtIconColor = (ColorPreference) findPreference("twitter_icon_color");
        twtLabel = (EditTextPreference) findPreference("twitter_label");
        ifTwitter = (CheckBoxPreference) findPreference("iftwitter");

        twtAddress.setText(business.getTwitterAddress());
        twtAddress.setSummary(business.getTwitterAddress());
        twtLabelColor.setColor(Color.parseColor(business.getTwitterLabelColor()));
        twtIconColor.setColor(Color.parseColor(business.getTwitterIconColor()));
        twtLabel.setText(business.getTwitterLabel());
        twtLabel.setSummary(business.getTwitterLabel());
        ifTwitter.setChecked(business.getIfTwitter());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "twitter_address": {
                String addressValue = sharedPreferences.getString(key, "");
                twtAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "twitter_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "twitter_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "twitter_label": {
                String labelValue = sharedPreferences.getString(key, "");
                twtLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "iftwitter": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifTwitter.setChecked(ifValue);
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
