package com.sms.sendsms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.sms.sendsms.R;
import com.sms.sendsms.service.SendSmsService;
import com.sms.sendsms.util.ServiceDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Sateesh Kumar on 9/12/2016.
 */
public class IncomingCallReceiver extends BroadcastReceiver {

    private static Logger LOGGER = LoggerFactory.getLogger(IncomingCallReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.getBoolean(context.getString(R.string.is_service_running), false) && ServiceDetector.isSmsServiceRunning(context)){
            String newPhoneState = intent.hasExtra(TelephonyManager.EXTRA_STATE)?
                    intent.getStringExtra(TelephonyManager.EXTRA_STATE):null;
            if(newPhoneState!=null && newPhoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                LOGGER.info("Incoming Call: Rigning: "+number);
                Intent i = new Intent(context, SendSmsService.class);
                i.putExtra(SendSmsService.EXTRA_PHONE_NUMBER, number);
                context.startService(i);
            }
        }
    }
}
