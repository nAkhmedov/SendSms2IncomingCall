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
public class MailCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference mailAddress;
    private ColorPreference mailColor;
    private ColorPreference mailIconColor;
    private EditTextPreference mailLabel;
    private CheckBoxPreference ifMail;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.mail_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Mail item
        mailAddress = (EditTextPreference) findPreference("mail_address");
        mailColor = (ColorPreference) findPreference("mail_label_color");
        mailIconColor = (ColorPreference) findPreference("mail_icon_color");
        mailLabel = (EditTextPreference) findPreference("mail_label");
        ifMail = (CheckBoxPreference) findPreference("ifmail");

        mailAddress.setText(business.getMailAddress());
        mailAddress.setSummary(business.getMailAddress());
        mailColor.setColor(Color.parseColor(business.getMailLabelColor()));
        mailIconColor.setColor(Color.parseColor(business.getMailIconColor()));
        mailLabel.setText(business.getMailLabel());
        mailLabel.setSummary(business.getMailLabel());
        ifMail.setChecked(business.getIfMail());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "mail_address": {
                String addressValue = sharedPreferences.getString(key, "");
                mailAddress.setSummary(addressValue);
                ((EditCardActivity) getActivity()).updateCard(key, addressValue);
                break;
            }
            case "mail_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "mail_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "mail_label": {
                String mailLabelValue = sharedPreferences.getString(key, "");
                mailLabel.setSummary(mailLabelValue);
                ((EditCardActivity) getActivity()).updateCard(key, mailLabelValue);
                break;
            }
            case "ifmail": {
                boolean ifMailValue = sharedPreferences.getBoolean(key, false);
                ifMail.setChecked(ifMailValue);
                ((EditCardActivity) getActivity()).updateCard(key, ifMailValue);
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
