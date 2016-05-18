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
public class GalleryCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference galleryAddress;
    private ColorPreference galleryLabelColor;
    private ColorPreference galleryIconColor;
    private EditTextPreference galleryLabel;
    private CheckBoxPreference ifGallery;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.gallery_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Gallery item
        galleryAddress = (EditTextPreference) findPreference("gallery_address");
        galleryLabelColor = (ColorPreference) findPreference("gallery_label_color");
        galleryIconColor = (ColorPreference) findPreference("gallery_icon_color");
        galleryLabel = (EditTextPreference) findPreference("gallery_label");
        ifGallery = (CheckBoxPreference) findPreference("ifgallery");

        galleryAddress.setText(business.getGalleryAddress());
        galleryAddress.setSummary(business.getGalleryAddress());
        galleryLabelColor.setColor(Color.parseColor(business.getGalleryLabelColor()));
        galleryIconColor.setColor(Color.parseColor(business.getGalleryIconColor()));
        galleryLabel.setText(business.getGalleryLabel());
        galleryLabel.setSummary(business.getGalleryLabel());
        ifGallery.setChecked(business.getIfGallery());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "gallery_address": {
                String addressValue = sharedPreferences.getString(key, "");
                galleryAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "gallery_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "gallery_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "gallery_label": {
                String labelValue = sharedPreferences.getString(key, "");
                galleryLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifgallery": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifGallery.setChecked(ifValue);
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
