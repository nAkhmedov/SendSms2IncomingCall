package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
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
 * Created by Navruz on 17.05.2016.
 */
public class GeneralCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private ColorPreference resetLabelClView;
    private ColorPreference resetIconClView;
    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.general_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //General item
        resetLabelClView = (ColorPreference) findPreference("reset_label_color");
        resetIconClView = (ColorPreference) findPreference("reset_icon_color");

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "reset_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                resetAllLabelColor(hexColor);
                break;
            }
            case "reset_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                resetAllIconColor(hexColor);
                break;
            }
        }
    }

    private void resetAllLabelColor(String hexColor) {
        ((EditCardActivity) getActivity()).updateCard("mail_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("facebook_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("twitter_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("linkedin_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("googleplus_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("youtube_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("phone_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("gallery_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("about_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("website_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("map_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("pinterest_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("android_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("userplus_label_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("chat_label_color", hexColor);
    }

    private void resetAllIconColor(String hexColor) {
        ((EditCardActivity) getActivity()).updateCard("mail_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("facebook_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("twitter_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("linkedin_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("googleplus_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("youtube_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("phone_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("gallery_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("about_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("website_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("map_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("pinterest_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("android_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("userplus_icon_color", hexColor);
        ((EditCardActivity) getActivity()).updateCard("chat_icon_color", hexColor);
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
