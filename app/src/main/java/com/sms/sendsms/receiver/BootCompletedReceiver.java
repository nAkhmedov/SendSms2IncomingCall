package com.sms.sendsms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;

import com.sms.sendsms.R;
import com.sms.sendsms.service.SendSmsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 07.04.2016.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    private Logger LOGGER = LoggerFactory.getLogger(BootCompletedReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("CHECKING: BOOT_COMPLETE EVENT RECEIVED 1 ");

        if ((intent.getAction() != null) && (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))) {

            LOGGER.info("CHECKING: BOOT_COMPLETE EVENT RECEIVED 2");
            String state = Environment.getExternalStorageState();
            LOGGER.info("CHECKING: STATE = " + state.equals(Environment.MEDIA_MOUNTED));

            if (state.equals(Environment.MEDIA_MOUNTED)) {
                LOGGER.info("BOOT_COMPLETED event received 3.");
                SharedPreferences sharedPref = context.getSharedPreferences(context.getString(
                        R.string.preference_file_key), Context.MODE_PRIVATE);
                boolean isServiceEnabled = sharedPref.getBoolean(context.getString(R.string.is_service_running), false);
                LOGGER.info("BOOT_COMPLETED isServiceEnabled = " + isServiceEnabled);
                if(isServiceEnabled) {
                    LOGGER.info("Starting SendSmsService service.");
                    Intent sendSmsServiceIntent = new Intent(context, SendSmsService.class);
                    context.startService(sendSmsServiceIntent);
                }
            }
        }
    }
}

