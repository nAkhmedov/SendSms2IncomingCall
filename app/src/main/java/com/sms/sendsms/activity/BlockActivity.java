package com.sms.sendsms.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sms.sendsms.R;
import com.sms.sendsms.fragment.BlockListFragment;
import com.sms.sendsms.fragment.ContactsFragment;
import com.sms.sendsms.fragment.HistoryFragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 01.04.2016.
 */
public class BlockActivity extends AppCompatActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        LOGGER.info("BlockActivity started");

        BlockListFragment fragment = new BlockListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameView, fragment)
                .commit();

    }

    public void showContactList(View view) {
        ContactsFragment contactsFragment = new ContactsFragment();
        contactsFragment.setHasOptionsMenu(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameView, contactsFragment)
                .addToBackStack(null)
                .commit();
    }
    public void showHistoryList(View view) {
        HistoryFragment historyFragment = new HistoryFragment();
        historyFragment.setHasOptionsMenu(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameView, historyFragment)
                .addToBackStack(null)
                .commit();
    }

}
