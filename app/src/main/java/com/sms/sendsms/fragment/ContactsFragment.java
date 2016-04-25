package com.sms.sendsms.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsFragment.class);
    private final int LOADER_ID = 1;

    private final Uri URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    private final String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER };

    private String query;

    private final static String[] FROM_COLUMNS = {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private final static int[] TO_IDS = {
            android.R.id.text1
    };

    private SimpleCursorAdapter cursorAdapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LOGGER.info("ContactsFragment started");
        LOGGER.info("FROM_COLUMNS Version :" + FROM_COLUMNS.length);

        ActionBar actionBar= ((BlockActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getResources().getString(R.string.contact_list));

        final ListView contactListView = (ListView) view.findViewById(R.id.contact_list);
        TextView noContactView = (TextView) view.findViewById(R.id.empty_contact);
        contactListView.setEmptyView(noContactView);

        cursorAdapter = new CustomSimpleCursorAdapter(
                getActivity(), contactListView, R.layout.contact_list_item, null, FROM_COLUMNS, TO_IDS, 0);

        contactListView.setAdapter(cursorAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    messageText = messageText  + " " + getResources().getString(R.string.already_was_block);
                }
                Snackbar.make(contactListView, messageText, Snackbar.LENGTH_INDEFINITE).
                        setAction(R.string.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }
                        ).show();
            }
        });

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String ORDER = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                + ", "
                + ContactsContract.Contacts.HAS_PHONE_NUMBER
                + " COLLATE NOCASE";
        String FILTER = ContactsContract.Contacts.HAS_PHONE_NUMBER + " NOT LIKE ''";
        if(query == null || query.isEmpty()) {
            return new CursorLoader(getActivity(), URI, PROJECTION, null, null, ORDER);
        } else {
            String name = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
            String phoneNumber = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            return new CursorLoader(getActivity(), URI, PROJECTION,
                    FILTER + String.format(
                            " AND (%s LIKE '%%%s%%' OR %s LIKE '%%%s%%')", name, query, phoneNumber, query), null, ORDER);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        LOGGER.info("onLoadFinished cursor name length = " + cursor.getColumnNames().length);
        switch (cursorLoader.getId()) {
            case LOADER_ID:
                cursorAdapter.swapCursor(cursor);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onClose() {
        if (!TextUtils.isEmpty(searchView.getQuery())) {
            searchView.setQuery(null, true);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        // Don't do anything if the filter hasn't actually changed.
        // Prevents restarting the loader when restoring state.
        if (query == null && newFilter == null) {
            return true;
        }
        if (query != null && query.equals(newFilter)) {
            return true;
        }
        query = newFilter;

        getLoaderManager().restartLoader(LOADER_ID, null, this);

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_contacts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search: {
                searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(this);
                searchView.setOnCloseListener(this);
                searchView.setIconifiedByDefault(true);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
