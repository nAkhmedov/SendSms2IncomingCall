package com.sms.sendsms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.User;
import com.sms.sendsms.service.SendSmsService;
import com.sms.sendsms.util.ServiceDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Navruz on 30.03.2016.
 */
public class LaunchActivity extends Activity {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            init();
        } catch (Exception e) {
            LOGGER.info("ERROR on init " + e.getMessage());
        } finally {
            startIndeedActivity();
        }
    }

    private void init() throws RuntimeException {
        File clientDataDir = getExternalFilesDir(null);
        File appDataDir = new File(ContextConstants.DATA_PATH);
        if (clientDataDir != null && !clientDataDir.exists() && !clientDataDir.mkdirs()) {
            LOGGER.error("Cannot create data path");
            throw new RuntimeException("Failed to init application");
        }
        LOGGER.info("created data path");
        if (!appDataDir.exists() && !appDataDir.mkdirs()) {
            LOGGER.error("Cannot create data path");
            throw new RuntimeException("Failed to init application");
        }
        LOGGER.info("init successful");
    }

    private void startIndeedActivity() {
        QueryBuilder<User> accountQueryBuilder = ApplicationLoader.getApplication(this)
                .getDaoSession()
                .getUserDao()
                .queryBuilder();

        User registeredAccount = accountQueryBuilder.unique();

        long registeredAccountsCount = accountQueryBuilder
                .buildCount()
                .count();

        if (registeredAccountsCount == 0  || registeredAccount == null) {
            Intent loginIntent = new Intent(LaunchActivity.this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
            finish();
            return;
        }

        LOGGER.info("continue starting");


        startSmsService();
        Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();

    }

    private void startSmsService() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.getBoolean(getString(R.string.is_service_running), false) && !ServiceDetector.isSmsServiceRunning(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("starting: ", SendSmsService.class.getSimpleName());
                    Intent homeBridgeServiceIntent = new Intent(LaunchActivity.this, SendSmsService.class);
                    startService(homeBridgeServiceIntent);
                }
            }).start();
        } else {
            LOGGER.info("service is already running or stopped");
        }
    }
}