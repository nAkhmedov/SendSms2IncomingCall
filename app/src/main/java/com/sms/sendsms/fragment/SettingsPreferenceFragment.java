package com.sms.sendsms.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.sms.sendsms.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 27.05.2016.
 */
public class SettingsPreferenceFragment extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsPreferenceFragment.class);

    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final ListPreference sendSmsPeriod = (ListPreference) findPreference("send_sms_period");
        final SwitchPreference dontSendSmsContacts = (SwitchPreference) findPreference("dont_send_sms_contacts");
        String period = prefs.getString("send_sms_period", getString(R.string.everytime));
        boolean dontSendContacts = prefs.getBoolean("dont_send_sms_contacts", false);
        sendSmsPeriod.setSummary(period);
        dontSendSmsContacts.setChecked(dontSendContacts);


        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                LOGGER.info("CHECKING: Preference settings changed = " + key);
                switch (key) {
                    case "send_sms_period": {
                        String period = sharedPreferences.getString(key, getString(R.string.everytime));
                        sendSmsPeriod.setSummary(period);
                        break;
                    }
                }
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
