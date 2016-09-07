package com.sms.sendsms.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.constants.NotificationConstants;
import com.sms.sendsms.database.BlackListDao;
import com.sms.sendsms.database.SmsLog;
import com.sms.sendsms.database.SmsLogDao;
import com.sms.sendsms.database.User;
import com.sms.sendsms.receiver.KeepAliveAlarmReceiver;
import com.sms.sendsms.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Navruz on 30.03.2016.
 */
public class SendSmsService extends Service {

    private static Logger LOGGER = LoggerFactory.getLogger(SendSmsService.class);

    private static final String SMS_SENT = "com.sms.sendsms.SMS_SENT";
    private static final String SMS_DELIVERED = "com.sms.sendsms.SMS_DELIVERED";
    private static final int MAX_SMS_MESSAGE_LENGTH = 160;
    private static final int SMS_PORT = 16001;
    private int mLastState = -1;
    private static String recipientNumber;

    private NotificationManager mNotificationManager;
    private User user;
    private SharedPreferences sharedPref;
    private AlarmManager alarmManager;
    private TelephonyManager telephony;
    private PendingIntent keepAlivePendingIntent;
    private SharedPreferences prefs;


    @Override
    public void onCreate() {
        super.onCreate();
        LOGGER.info("Service is onCreate");
        mNotificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        prefs = PreferenceManager.getDefaultSharedPreferences(SendSmsService.this);

        LOGGER.info("registering incomingCallReceiver receiver");
        LOGGER.info("registering receiver: SMS_SEND");
        registerReceiver(sendSmsReceiver, new IntentFilter(SMS_SENT));
        registerReceiver(incomingCallReceiver, new IntentFilter(
                TelephonyManager.ACTION_PHONE_STATE_CHANGED));

        alarmManager = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);
        telephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        Intent intent = new Intent(this, KeepAliveAlarmReceiver.class);
        keepAlivePendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HALF_DAY,
                keepAlivePendingIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LOGGER.info("Service is onStartCommand");
        sharedPref.edit().putBoolean(getString(R.string.is_service_running), true).apply();
        user = ApplicationLoader.getApplication(SendSmsService.this)
                .getDaoSession()
                .getUserDao()
                .queryBuilder()
                .unique();
        int iconId = R.drawable.icon;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(SendSmsService.this)
                .setContentTitle(getResources().getText(R.string.app_name))
                .setSmallIcon(iconId)
                .setWhen(System.currentTimeMillis());

        Intent nIntent = getPackageManager().
                getLaunchIntentForPackage(ContextConstants.PACKAGE_NAME);
        PendingIntent pendingIntent = PendingIntent.getActivity(SendSmsService.this, 0, nIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        startForeground(NotificationConstants.LAUNCHER_SERVICE_NOTE_ID,
                notificationBuilder.build());
        return START_STICKY;

    }

    private BroadcastReceiver sendSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOGGER.info("sendSmsReceiver is received");
            String message;

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    message = context.getResources().getString(R.string.msg_sent) + " " + recipientNumber;
                    SmsLog smsLog = new SmsLog();
                    String dateString = (String) DateFormat.format("dd/MM/yyyy h:mm a", new java.util.Date());
                    smsLog.setSentDate(dateString);
                    smsLog.setSentNumber(recipientNumber);
                    ApplicationLoader.getApplication(SendSmsService.this)
                            .getDaoSession()
                            .getSmsLogDao()
                            .insert(smsLog);
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    message = context.getResources().getString(R.string.error_msg);
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    message = context.getResources().getString(R.string.no_service);
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    message = context.getResources().getString(R.string.error_pdu);
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    message = context.getResources().getString(R.string.radio_off);
                    break;
                default: {
                    message = context.getResources().getString(R.string.unknown_error_sent_msg);
                    break;
                }
            }

            showSmsSendNotification(message);
            LOGGER.info("sendSmsReceiver message = " + message);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGGER.info("Service is onDestroy");
        sharedPref.edit().putBoolean(getString(R.string.is_service_running), false).apply();
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
        unregisterReceiver(incomingCallReceiver);
        unregisterReceiver(sendSmsReceiver);
        alarmManager.cancel(keepAlivePendingIntent);
        mNotificationManager.cancel(NotificationConstants.SEND_SMS_MSG);
        mNotificationManager.cancel(NotificationConstants.LAUNCHER_SERVICE_NOTE_ID);
        LOGGER.info("Unregistered all receiver and stopped successfully.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver incomingCallReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOGGER.info("IncomingCallReceiver is received.");
            telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    };

    private PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            LOGGER.info("phoneListener state : " + state);
            if (state == mLastState) {
                return;
            }
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                mLastState = state;
                sendSms(incomingNumber, user.getMessageBody(), false);
            }
                    /*after finishing call state android returns CALL_STATE_IDLE*/
            if (state == TelephonyManager.CALL_STATE_IDLE) {
                LOGGER.info("incomingCall finished state = " + state);
                mLastState = -1;
            }
        }
    };

    private void showSmsSendNotification(String message) {
        boolean isEnabledNty = sharedPref.getBoolean(getResources().getString(R.string.is_enable_notifying), true);
        LOGGER.info("Enable notification = " + isEnabledNty);
        if (isEnabledNty) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(SendSmsService.this)
                    .setContentTitle(getResources().getText(R.string.app_name))
                    .setContentText(message)
                    .setSmallIcon(R.drawable.icon)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL);
            Notification notification = notificationBuilder.build();
            notification.defaults = Notification.DEFAULT_ALL;
            notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
            mNotificationManager.notify(NotificationConstants.SEND_SMS_MSG, notification);
        }
    }

    private void sendSms(String phoneNumber,String messageText, boolean isBinary) {
        LOGGER.info("Sending sms to phoneNumber = " + phoneNumber);
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            LOGGER.info("Failed: Couldn't sent message to number = " + phoneNumber);
            mLastState = -1;
            return;
        }
        if (ApplicationLoader.getApplication(this)
                .getDaoSession()
                .getBlackListDao()
                .queryBuilder()
                .where(BlackListDao.Properties.Number.eq(phoneNumber)).buildCount().count() != 0) {
            LOGGER.info("Failed: Number in blockList = " + phoneNumber);
            mLastState = -1;
            return;
        }
        if (!phoneNumber.startsWith(ContextConstants.PHONE_NUMBER_FORMAT_F) &&
                !phoneNumber.startsWith(ContextConstants.PHONE_NUMBER_FORMAT_S)) {
            LOGGER.info("Failed: Number is not started 05 or +9725; phoneNumber = " + phoneNumber);
            mLastState = -1;
            return;
        }

        if (messageText == null || messageText.isEmpty()) {
            LOGGER.info("Error: Sending msg content empty or null message = " + messageText);
            mLastState = -1;
            return;
        }

        List<SmsLog> smsList = ApplicationLoader.getApplication(this)
                .getDaoSession()
                .getSmsLogDao()
                .queryBuilder()
                .where(SmsLogDao.Properties.SentNumber.eq(phoneNumber))
                .build()
                .list();
        int smsListSize = smsList.size();
        if (smsListSize > 0) {
            Resources res = getResources();
            String period = prefs.getString("send_sms_period", res.getString(R.string.everytime));
            String everytime = res.getString(R.string.everytime);
            String onceADay = res.getString(R.string.once_day);
            String onceAWeek = res.getString(R.string.once_week);
            String onceAMonth = res.getString(R.string.once_month);
            long settingsPeriod = 0;
            if (period.equals(everytime)) {
                settingsPeriod = ContextConstants.EVERYTIME_PERIOD;
            } else if (period.equals(onceADay)) {
                settingsPeriod = ContextConstants.ONE_DAY_PERIOD;
            } else if (period.equals(onceAWeek)) {
                settingsPeriod = ContextConstants.WEEK_DAY_PERIOD;
            } else {
                settingsPeriod = ContextConstants.MONTH_DAY_PERIOD;
            }

            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy h:mm a");
                Date date = format.parse(smsList.get(smsListSize - 1).getSentDate());
                Calendar thatDay = Calendar.getInstance();
                thatDay.setTime(date);
                if (settingsPeriod != ContextConstants.EVERYTIME_PERIOD &&
                        !DateUtil.isMoreThanSelectedDays(thatDay, settingsPeriod)) {
                    LOGGER.info("SEND SMS PERIOD = " + settingsPeriod +  " LAST SENT SMS date = " + date.toString());
                    return;

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("It is the first time to send sms size = " + smsList.size());
        }

        LOGGER.info("Sending probable success ");
        recipientNumber = phoneNumber;
        SmsManager smsManager = SmsManager.getDefault();

        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

        ArrayList<String> parts = smsManager.divideMessage(messageText);

        int partsCount = parts.size();
        ArrayList<PendingIntent> sentIntents = new ArrayList<>(partsCount);
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<>(partsCount);

        for (int i = 0; i < partsCount; i++) {
            sentIntents.add(i, piSend);
            deliveryIntents.add(i, piDelivered);
        }

        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents);
    }
}
