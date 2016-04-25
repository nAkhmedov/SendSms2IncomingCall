package com.sms.sendsms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sms.sendsms.R;
import com.sms.sendsms.database.SmsLog;

import java.util.List;

/**
 * Created by Navruz on 02.04.2016.
 */
public class SmsLogsAdapter extends ArrayAdapter<SmsLog> {

    private Context context;
    private List<SmsLog> smsLogList;

    public SmsLogsAdapter(Context context, List<SmsLog> smsLogList) {
        super(context, 0, smsLogList);
        this.context = context;
        this.smsLogList = smsLogList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SmsLog smsLog = smsLogList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        final TextView numberView = (TextView) convertView.findViewById(R.id.sms_number);
        TextView dateView = (TextView) convertView.findViewById(R.id.sms_date);
        // Populate the data into the template view using the data object
        numberView.setText(smsLog.getSentNumber());
        dateView.setText(context.getResources().getString(R.string.sent_on) + "  " + smsLog.getSentDate());

        return convertView;
    }
}
