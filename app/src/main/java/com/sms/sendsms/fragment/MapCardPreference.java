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
public class MapCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference mapAddress;
    private ColorPreference mapLabelColor;
    private ColorPreference mapIconColor;
    private EditTextPreference mapLabel;
    private CheckBoxPreference ifMap;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.map_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Map item
        mapAddress = (EditTextPreference) findPreference("map_address");
        mapLabelColor = (ColorPreference) findPreference("map_label_color");
        mapIconColor = (ColorPreference) findPreference("map_icon_color");
        mapLabel = (EditTextPreference) findPreference("map_label");
        ifMap = (CheckBoxPreference) findPreference("ifmap");

        mapAddress.setText(business.getMapAddress());
        mapAddress.setSummary(business.getMapAddress());
        mapLabelColor.setColor(Color.parseColor(business.getMapLabelColor()));
        mapIconColor.setColor(Color.parseColor(business.getMapIconColor()));
        mapLabel.setText(business.getMapLabel());
        mapLabel.setSummary(business.getMapLabel());
        ifMap.setChecked(business.getIfMap());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "map_address": {
                String addressValue = sharedPreferences.getString(key, "");
                mapAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "map_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "map_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "map_label": {
                String labelValue = sharedPreferences.getString(key, "");
                mapLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifmap": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifMap.setChecked(ifValue);
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
