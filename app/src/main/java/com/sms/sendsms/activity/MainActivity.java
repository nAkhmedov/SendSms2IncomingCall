package com.sms.sendsms.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.constants.ActionNames;
import com.sms.sendsms.database.User;
import com.sms.sendsms.service.SendSmsService;
import com.sms.sendsms.util.ServiceDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 29.03.2016.
 */
public class MainActivity extends Activity {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);
    public static boolean IS_ACTIVITY_ALIVE = false;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Switch toggleNtf = (Switch) findViewById(R.id.toggle_ntf);
        Switch toggleService = (Switch) findViewById(R.id.toggle_service);
        Button logOutBtn = (Button) findViewById(R.id.log_out);
        toggleNtf.setChecked(sharedPref.getBoolean(getString(R.string.is_enable_notifying), true));
        toggleService.setChecked(ServiceDetector.isSmsServiceRunning(this));

        toggleNtf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LOGGER.info("toggleNtf onCheckedChanged = " + isChecked);
                sharedPref.edit().putBoolean(getString(R.string.is_enable_notifying), isChecked).apply();
            }
        });

        toggleService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LOGGER.info("toggleService onCheckedChanged = " + isChecked);
                sharedPref.edit().putBoolean(getString(R.string.is_service_running), isChecked).apply();
                toggleService(isChecked);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void toggleService(boolean isEnabled) {
        if (isEnabled) {
            if (!ServiceDetector.isSmsServiceRunning(this)) {
                startService(new Intent(MainActivity.this, SendSmsService.class));
            } else {
                LOGGER.info("Service is enabled and already running");
            }
        } else {
            LOGGER.info("Service is stopping");
            stopService(new Intent(MainActivity.this, SendSmsService.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IS_ACTIVITY_ALIVE = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(logoutReceiver,
                new IntentFilter(ActionNames.ACTION_LOG_OUT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IS_ACTIVITY_ALIVE = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(logoutReceiver);
    }

    public void showSmsReports(View view) {
        startActivity(new Intent(MainActivity.this, SmsHistoryActivity.class));
    }

    public void startBlockActivity(View view) {
        startActivity(new Intent(MainActivity.this, BlockActivity.class));
    }

    public void logout() {
        LOGGER.info("Logging out...");
        new Thread(new Runnable() {
            @Override
            public void run() {//                List<SmsLog> smsLogList = ApplicationLoader.getApplication(MainActivity.this)
//                        .getDaoSession()
//                        .getSmsLogDao()
//                        .queryBuilder()
//                        .where(SmsLogDao.Properties.AccountId.eq(user.getId()))
//                        .list();

                User user = ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getUserDao()
                        .queryBuilder()
                        .unique();
                LOGGER.info("Service is stopping");
                stopService(new Intent(MainActivity.this, SendSmsService.class));

                ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getBlackListDao()
                        .deleteAll();
                ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getSmsLogDao()
                        .deleteAll();
                ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getUserDao()
                        .delete(user);
                sharedPref.edit().putBoolean(getString(R.string.is_enable_notifying), true).apply();
                sharedPref.edit().putBoolean(getString(R.string.is_service_running), true).apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }).start();
    }

    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOGGER.info("logoutReceiver is received!!!");
            logout();
        }
    };
}
