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
public class UserPlusCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPlusCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private ColorPreference userPlusLabelColor;
    private ColorPreference userPlusIconColor;
    private EditTextPreference userPlusLabel;
    private CheckBoxPreference ifUserPlus;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.save_contact_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Save Contact item
        userPlusLabelColor = (ColorPreference) findPreference("userplus_label_color");
        userPlusIconColor = (ColorPreference) findPreference("userplus_icon_color");
        userPlusLabel = (EditTextPreference) findPreference("userplus_label");
        ifUserPlus = (CheckBoxPreference) findPreference("ifuserplus");

        userPlusLabelColor.setColor(Color.parseColor(business.getUserplusLabelColor()));
        userPlusIconColor.setColor(Color.parseColor(business.getUserplusIconColor()));
        userPlusLabel.setText(business.getUserplusLabel());
        userPlusLabel.setSummary(business.getUserplusLabel());
        ifUserPlus.setChecked(business.getIfUserplus());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "userplus_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "userplus_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "userplus_label": {
                String labelValue = sharedPreferences.getString(key, "");
                userPlusLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifuserplus": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifUserPlus.setChecked(ifValue);
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
