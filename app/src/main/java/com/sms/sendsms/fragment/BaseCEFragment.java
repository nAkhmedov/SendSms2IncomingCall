package com.sms.sendsms.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sms.sendsms.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 25.05.2016.
 */
public abstract class BaseCEFragment extends PreferenceFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCEFragment.class);
    private ProgressDialog dialog;

    public void showDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage(getResources().getString(R.string.downloading_img));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
