package com.sms.sendsms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.database.BlackList;

import java.util.List;

/**
 * Created by Navruz on 01.04.2016.
 */
public class BlockContactsAdapter extends ArrayAdapter<BlackList> {
    private Context context;
    private List<BlackList> blockContactList;
    public BlockContactsAdapter(Context context, List<BlackList> blockContactList) {
        super(context, 0, blockContactList);
        this.context = context;
        this.blockContactList = blockContactList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BlackList blockContact = blockContactList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.block_list_item, parent, false);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.block_contact_name);
        TextView numberView = (TextView) convertView.findViewById(R.id.block_contact_number);
        ImageButton deleteView = (ImageButton) convertView.findViewById(R.id.deleteBlockButton);
        // Populate the data into the template view using the data object
        nameView.setText(blockContact.getContactName());
        numberView.setText(blockContact.getNumber());

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlackList blockUser = getItem(position);
                ApplicationLoader.getApplication(context)
                        .getDaoSession()
                        .getBlackListDao()
                        .delete(blockUser);
                blockContactList.remove(blockUser);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
