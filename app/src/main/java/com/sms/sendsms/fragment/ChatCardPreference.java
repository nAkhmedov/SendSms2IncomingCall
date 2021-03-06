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
public class ChatCardPreference extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private EditTextPreference chatLabel;
    private ColorPreference chatLabelColor;
    private ColorPreference chatIconColor;
    private CheckBoxPreference ifChat;

    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.chat_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Chat item
        chatLabel = (EditTextPreference) findPreference("chat_label");
        chatLabelColor = (ColorPreference) findPreference("chat_label_color");
        chatIconColor = (ColorPreference) findPreference("chat_icon_color");
        ifChat = (CheckBoxPreference) findPreference("ifchat");

        chatLabel.setText(business.getChatLabel());
        chatLabel.setSummary(business.getChatLabel());
        chatLabelColor.setColor(Color.parseColor(business.getChatLabelColor()));
        chatIconColor.setColor(Color.parseColor(business.getChatIconColor()));
        ifChat.setChecked(business.getIfChat());

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "chat_label": {
                String labelValue = sharedPreferences.getString(key, "");
                chatLabel.setSummary(labelValue);
                ((EditCardActivity) getActivity()).updateCard(key, labelValue);
                break;
            }
            case "chat_label_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "chat_icon_color": {
                int colorValue = sharedPreferences.getInt(key, 0);
                String hexColor = String.format("#%06X", (0xFFFFFF & colorValue));
                ((EditCardActivity) getActivity()).updateCard(key, hexColor);
                break;
            }
            case "ifchat": {
                boolean ifValue = sharedPreferences.getBoolean(key, false);
                ifChat.setChecked(ifValue);
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
