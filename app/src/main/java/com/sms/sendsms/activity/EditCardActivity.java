package com.sms.sendsms.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.sms.sendsms.fragment.MainCardPreference;

public  class EditCardActivity extends AppCompatActivity {

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    private final static String TAG_FRAGMENT_MAIN_CARD = "TAG_FRAGMENT_MAIN_CARD";
    public static long businessId;

    @Override
    protected  void onCreate ( Bundle savedInstanceState )  {
        super.onCreate (savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MainCardPreference(), TAG_FRAGMENT_MAIN_CARD)
                .commit();
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
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}