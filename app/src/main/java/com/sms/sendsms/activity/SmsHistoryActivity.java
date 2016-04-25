package com.sms.sendsms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.adapter.SmsLogsAdapter;
import com.sms.sendsms.database.BlackList;
import com.sms.sendsms.database.BlackListDao;
import com.sms.sendsms.database.SmsLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Navruz on 02.04.2016.
 */
public class SmsHistoryActivity extends Activity {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsHistoryActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        LOGGER.info("SmsHistoryActivity started");

        final ListView smsListView = (ListView) findViewById(R.id.contact_list);
        TextView noContactView = (TextView) findViewById(R.id.empty_contact);
        smsListView.setEmptyView(noContactView);

        final List<SmsLog> smsLogsList = ApplicationLoader.getApplication(this)
                .getDaoSession()
                .getSmsLogDao()
                .loadAll();

        LOGGER.info("smsLogsList size = " + smsLogsList.size());

        SmsLogsAdapter blockAdapter = new SmsLogsAdapter(this, smsLogsList);

        smsListView.setAdapter(blockAdapter);

        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView numberView = (TextView) view.findViewById(R.id.sms_number);
                BlackList blockUser = new BlackList();
                blockUser.setNumber(numberView.getText().toString());
                BlackListDao blockUserDao = ApplicationLoader.getApplication(SmsHistoryActivity.this)
                        .getDaoSession()
                        .getBlackListDao();
                String messageText = "";
                if (blockUserDao.queryBuilder().where(BlackListDao.Properties.Number.
                        eq(blockUser.getNumber())).buildCount().count() == 0) {
                    blockUserDao.insert(blockUser);
                    messageText = blockUser.getNumber() + " "+ getResources().getString(R.string.added_block);
                } else {
                    messageText = blockUser.getNumber() + " "+ getResources().getString(R.string.already_was_block);
                }
                Snackbar.make(smsListView, messageText, Snackbar.LENGTH_INDEFINITE).
                        setAction(R.string.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }
                        ).show();
            }
        });
    }

}
