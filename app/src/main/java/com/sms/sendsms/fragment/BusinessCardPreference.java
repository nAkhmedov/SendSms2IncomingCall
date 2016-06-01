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
public class BusinessCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference businessName;
    private ColorPreference businessNameColor;
    private CheckBoxPreference ifBusinessName;
    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.business_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Business item
        businessName = (EditTextPreference) findPreference("businessname");
        businessNameColor = (ColorPreference) findPreference("businessname_color");
        ifBusinessName = (CheckBoxPreference) findPreference("ifbusinessname");

        businessName.setText(business.getBusinessName());
        businessName.setSummary(business.getBusinessName());
        businessNameColor.setColor(Color.parseColor(business.getBusinessnameColor()));
        ifBusinessName.setChecked(business.getIfBusinessname());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "businessname": {
                String businessText = sharedPreferences.getString(key, " ");
                businessName.setSummary(businessText);
                ((EditCardActivity) getActivity()).updateCard(key, businessText);
                break;
            }
            case "businessname_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "ifbusinessname": {
                boolean ifBusiness = sharedPreferences.getBoolean(key, false);
                ifBusinessName.setChecked(ifBusiness);
                ((EditCardActivity) getActivity()).updateCard(key, ifBusiness);
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
