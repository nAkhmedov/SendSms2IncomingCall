package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.view.MenuItem;

import com.rarepebble.colorpicker.ColorPreference;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.database.Business;

/**
 * Created by Navruz on 10.05.2016.
 */
public class BusinessCardPreference extends PreferenceFragment {

    private EditTextPreference businessName;
    private ColorPreference businessNameColor;
    private CheckBoxPreference ifBusinessName;
    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.business_pref_content);

        business = ApplicationLoader.getApplication(getActivity())
                .getDaoSession()
                .getBusinessDao()
                .load(EditCardActivity.businessId);
        //Business item
        businessName = (EditTextPreference) findPreference("business_name");
        businessNameColor = (ColorPreference) findPreference("business_color_code");
        ifBusinessName = (CheckBoxPreference) findPreference("if_business_name");

        businessName.setText(business.getBusinessName());
        businessName.setSummary(business.getBusinessName());
//        businessNameColor.setColor(Color.parseColor(business.getBusinessnameColor()));
        ifBusinessName.setChecked(business.getIfBusinessname());

        EditCardActivity.listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "business_name": {
                String businessText = sharedPreferences.getString("business_name", " ");
                businessName.setSummary(businessText);
                break;
            }
        }
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
