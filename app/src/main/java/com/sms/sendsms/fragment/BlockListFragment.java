package com.sms.sendsms.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.BlockActivity;
import com.sms.sendsms.adapter.BlockContactsAdapter;
import com.sms.sendsms.database.BlackList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Navruz on 01.04.2016.
 */
public class BlockListFragment extends Fragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockListFragment.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.block_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LOGGER.info("BlockListFragment started");

        ActionBar actionBar= ((BlockActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getResources().getString(R.string.block_list));

        ListView blockListView = (ListView) view.findViewById(R.id.block_list);
        TextView noContactView = (TextView) view.findViewById(R.id.empty_contact);
        blockListView.setEmptyView(noContactView);

        final List<BlackList> blockContactList = ApplicationLoader.getApplication(getActivity())
                .getDaoSession()
                .getBlackListDao()
                .loadAll();

        LOGGER.info("blockContactList size = "  + blockContactList.size());

        final ArrayAdapter blockAdapter = new BlockContactsAdapter(getActivity(), blockContactList);

        blockListView.setAdapter(blockAdapter);
    }
}
