package com.sms.sendsms.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import com.sms.sendsms.service.SendSmsService;

import java.util.List;


public class ServiceDetector {
	public static boolean isSmsServiceRunning(Context context) {
	    return isServiceRunning(context, SendSmsService.class);
	}

	private static boolean isServiceRunning(Context context, Class<?> serviceClass) {
		final ActivityManager activityManager = (ActivityManager)context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
	    final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

	    for (RunningServiceInfo runningServiceInfo : services) {
	        if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())){
	            return true;
	        }
	    }
	    return false;
	}
}
