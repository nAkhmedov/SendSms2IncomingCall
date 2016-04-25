package com.sms.sendsms.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.BlockActivity;
import com.sms.sendsms.adapter.CustomSimpleCursorAdapter;
import com.sms.sendsms.database.BlackList;
import com.sms.sendsms.database.BlackListDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryFragment.class);

    private CustomSimpleCursorAdapter cursorAdapter;
    private final static String[] FROM_COLUMNS = {
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LOGGER.info("HistoryFragment onViewCreated");

        ActionBar actionBar= ((BlockActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getResources().getString(R.string.history_calls));
        final ListView logListView = (ListView) view.findViewById(R.id.contact_list);
        TextView noLogView = (TextView) view.findViewById(R.id.empty_contact);
        logListView.setEmptyView(noLogView);

        cursorAdapter = new CustomSimpleCursorAdapter(
                getActivity(), logListView, R.layout.contact_list_item, null, FROM_COLUMNS, null, 0);

        logListView.setAdapter(cursorAdapter);

        logListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nameView = (TextView) view.findViewById(R.id.contact_name);
                TextView numberView = (TextView) view.findViewById(R.id.contact_number);
                BlackList blockUser = new BlackList();
                blockUser.setContactName(nameView.getText().toString());
                blockUser.setNumber(numberView.getText().toString());
                BlackListDao blockUserDao = ApplicationLoader.getApplication(getActivity())
                        .getDaoSession()
                        .getBlackListDao();
                String messageText = "";
                if (blockUserDao.queryBuilder().where(BlackListDao.Properties.Number.
                        eq(blockUser.getNumber())).buildCount().count() == 0) {
                    blockUserDao.insert(blockUser);
                    messageText = (blockUser.getContactName().isEmpty()) ? blockUser.getNumber() : blockUser.getContactName();
                    messageText = messageText + " " + getResources().getString(R.string.added_block);
                } else {
                    messageText = (blockUser.getContactName().isEmpty()) ? blockUser.getNumber() : blockUser.getContactName();
                    messageText = messageText + getResources().getString(R.string.already_was_block);
                }
                Snackbar.make(logListView, messageText, Snackbar.LENGTH_INDEFINITE).
                        setAction(R.string.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }
                        ).show();
            }
        });

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), CallLog.Calls.CONTENT_URI, null, null, null,
                CallLog.Calls.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        LOGGER.info("onLoadFinished cursor name length = " + cursor.getColumnNames().length);
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
