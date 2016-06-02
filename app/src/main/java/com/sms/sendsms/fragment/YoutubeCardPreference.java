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
public class YoutubeCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference youtubeAddress;
    private ColorPreference youtubeLabelColor;
    private ColorPreference youtubeIconColor;
    private EditTextPreference youtubeLabel;
    private CheckBoxPreference ifYoutube;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.youtube_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Youtube item
        youtubeAddress = (EditTextPreference) findPreference("youtube_address");
        youtubeLabelColor = (ColorPreference) findPreference("youtube_label_color");
        youtubeIconColor = (ColorPreference) findPreference("youtube_icon_color");
        youtubeLabel = (EditTextPreference) findPreference("youtube_label");
        ifYoutube = (CheckBoxPreference) findPreference("ifyoutube");

        youtubeAddress.setText(business.getYoutubeAddress());
        youtubeAddress.setSummary(business.getYoutubeAddress());
        youtubeLabelColor.setColor(Color.parseColor(business.getYoutubeLabelColor()));
        youtubeIconColor.setColor(Color.parseColor(business.getYoutubeIconColor()));
        youtubeLabel.setText(business.getYoutubeLabel());
        youtubeLabel.setSummary(business.getYoutubeLabel());
        ifYoutube.setChecked(business.getIfYoutube());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "youtube_address": {
                String addressValue = sharedPreferences.getString(key, "");
                youtubeAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "youtube_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "youtube_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "youtube_label": {
                String labelValue = sharedPreferences.getString(key, "");
                youtubeLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifyoutube": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifYoutube.setChecked(ifValue);
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
