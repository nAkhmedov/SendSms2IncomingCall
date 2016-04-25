package com.sms.sendsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.sms.sendsms.R;

/**
 * Created by Navruz on 01.04.2016.
 */
public class CustomSimpleCursorAdapter extends SimpleCursorAdapter {
    private final String[] from;
    private final int[] to;
//    private final ListView contactListView;
    private Context context;
    private int layout;

    public CustomSimpleCursorAdapter(Context context, ListView contactListView, int layout, Cursor c, String[] from, int[] to, int i) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        this.from = from;
        this.to = to;
//        this.contactListView = contactListView;
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {

        Cursor mCursor = getCursor();
        View convertView = LayoutInflater.from(context).inflate(layout, parent, false);

        String contactName = mCursor.getString(mCursor.getColumnIndex(from[0]));
        String phoneNumber = mCursor.getString(mCursor.getColumnIndex(from[1]));
        TextView nameView = (TextView) convertView.findViewById(R.id.contact_name);
        TextView numberView = (TextView) convertView.findViewById(R.id.contact_number);
        nameView.setText(contactName);
        numberView.setText(phoneNumber);

        return convertView;
    }

    @Override
    public void bindView(View convertView, final Context context, Cursor c) {

        String contactName = mCursor.getString(mCursor.getColumnIndex(from[0]));
        String phoneNumber = mCursor.getString(mCursor.getColumnIndex(from[1]));

        TextView nameView = (TextView) convertView.findViewById(R.id.contact_name);
        TextView numberView = (TextView) convertView.findViewById(R.id.contact_number);
        nameView.setText(contactName);
        numberView.setText(phoneNumber);

        ImageButton addContactBtn = (ImageButton) convertView.findViewById(R.id.addContactButton);
        addContactBtn.setTag(mCursor.getPosition());
    }
}
