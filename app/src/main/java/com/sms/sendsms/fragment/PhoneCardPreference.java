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
public class PhoneCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference phoneAddress;
    private ColorPreference phoneLabelColor;
    private ColorPreference phoneIconColor;
    private EditTextPreference phoneLabel;
    private CheckBoxPreference ifPhone;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.phone_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Phone item
        phoneAddress = (EditTextPreference) findPreference("phone_address");
        phoneLabelColor = (ColorPreference) findPreference("phone_label_color");
        phoneIconColor = (ColorPreference) findPreference("phone_icon_color");
        phoneLabel = (EditTextPreference) findPreference("phone_label");
        ifPhone = (CheckBoxPreference) findPreference("ifphone");

        phoneAddress.setText(business.getPhoneAddress());
        phoneAddress.setSummary(business.getPhoneAddress());
        phoneLabelColor.setColor(Color.parseColor(business.getPhoneLabelColor()));
        phoneIconColor.setColor(Color.parseColor(business.getPhoneIconColor()));
        phoneLabel.setText(business.getPhoneLabel());
        phoneLabel.setSummary(business.getPhoneLabel());
        ifPhone.setChecked(business.getIfPhone());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "phone_address": {
                String addressValue = sharedPreferences.getString(key, "");
                phoneAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "phone_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "phone_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "phone_label": {
                String labelValue = sharedPreferences.getString(key, "");
                phoneLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "ifphone": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifPhone.setChecked(ifValue);
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
