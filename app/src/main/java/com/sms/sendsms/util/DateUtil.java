package com.sms.sendsms.util;

import com.sms.sendsms.constants.ContextConstants;

import java.util.Calendar;

/**
 * Created by Navruz on 18.04.2016.
 */
public class DateUtil {

    public static boolean isMoreThan3Days(Calendar thatDay) {
        Calendar today = Calendar.getInstance();
        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        return days > ContextConstants.DAY_PERIOD;
    }
}
