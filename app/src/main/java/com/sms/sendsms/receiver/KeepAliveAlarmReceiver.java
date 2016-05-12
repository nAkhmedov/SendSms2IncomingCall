package com.sms.sendsms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.LoginActivity;
import com.sms.sendsms.activity.MainActivity;
import com.sms.sendsms.constants.ActionNames;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.User;
import com.sms.sendsms.execution.CustomHTTPService;
import com.sms.sendsms.service.SendSmsService;
import com.sms.sendsms.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Navruz on 19.04.2016.
 */
public class KeepAliveAlarmReceiver extends BroadcastReceiver {

    private static Logger LOGGER = LoggerFactory.getLogger(KeepAliveAlarmReceiver.class);
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.info("KeepAliveAlarmReceiver: " + new Date().getTime());
        this.context = context;
        getMessageBody();
    }

    private void getMessageBody() {
        final User user = ApplicationLoader.getApplication(context)
                .getDaoSession()
                .getUserDao()
                .queryBuilder()
                .unique();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContextConstants.APP_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LOGGER.info("Sending getsms request...");
        CustomHTTPService http = retrofit.create(CustomHTTPService.class);
        http.sendMessageBodyRequest("getsms", user.getMessageCode()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String messageBody = response.body();
                if (messageBody == null || messageBody.isEmpty()) {
                    LOGGER.info("Disabled date = " + user.getDisabledDate());
                    if (user.getDisabledDate() == null) {
                        user.setDisabledDate(new Date());
                    } else {
                        Calendar thatDay = Calendar.getInstance();
                        thatDay.setTime(user.getDisabledDate());
                        if (DateUtil.isMoreThan3Days(thatDay)) {
                            logoutUser();
                            return;
                        }
                    }
                    if (user.getMessageBody() == null) {
                        messageBody = "Empty string by server";
                    } else messageBody = user.getMessageBody();
                }
                user.setMessageBody(messageBody);
                ApplicationLoader.getApplication(context)
                        .getDaoSession()
                        .getUserDao()
                        .update(user);
            }

            @Override
            public void onFailure(Call<String> call, Throwable error) {
                LOGGER.error("ERROR: getMessageBody content = " + error + " messageError = " + error.getMessage());
                error.printStackTrace();
            }
        });
    }

    private void logoutUser() {
        if (MainActivity.IS_ACTIVITY_ALIVE) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(
                    new Intent(ActionNames.ACTION_LOG_OUT)
            );
        } else {
            logoutSelf();
        }
    }

    private void logoutSelf() {
        LOGGER.info("logoutSelf: logout by stopping Service ");
        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = ApplicationLoader.getApplication(context)
                        .getDaoSession()
                        .getUserDao()
                        .queryBuilder()
                        .unique();
                context.stopService(new Intent(context, SendSmsService.class));

                ApplicationLoader.getApplication(context)
                        .getDaoSession()
                        .getBlackListDao()
                        .deleteAll();
                ApplicationLoader.getApplication(context)
                        .getDaoSession()
                        .getSmsLogDao()
                        .deleteAll();
                ApplicationLoader.getApplication(context)
                        .getDaoSession()
                        .getUserDao()
                        .delete(user);
                SharedPreferences sharedPref = context.getSharedPreferences(
                        context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);;
                sharedPref.edit().putBoolean(context.getResources().getString(R.string.is_enable_notifying), true).apply();
                sharedPref.edit().putBoolean(context.getResources().getString(R.string.is_service_running), true).apply();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }).start();
    }
}
