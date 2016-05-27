package com.sms.sendsms.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.fragment.SettingsPreferenceFragment;

/**
 * Created by Navruz on 27.05.2016.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsPreferenceFragment(), ContextConstants.TAG_FRAGMENT_SETTINGS)
                .commit();
    }
}
