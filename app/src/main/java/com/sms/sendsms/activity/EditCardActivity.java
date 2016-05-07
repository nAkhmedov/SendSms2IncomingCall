package com.sms.sendsms.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sms.sendsms.fragment.BusinessPreferenceFragment;

public  class EditCardActivity extends AppCompatActivity {

    private final static String TAG_FRAGMENT_GENERAL = "TAG_FRAGMENT_GENERAL";

    @Override
    protected  void onCreate ( Bundle savedInstanceState )  {
        super.onCreate (savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new BusinessPreferenceFragment(), TAG_FRAGMENT_GENERAL)
                .commit();
    }
}