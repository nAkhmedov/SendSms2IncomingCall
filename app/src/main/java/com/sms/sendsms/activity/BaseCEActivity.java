package com.sms.sendsms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sms.sendsms.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Navruz on 25.05.2016.
 */
public abstract class BaseCEActivity extends AppCompatActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCEActivity.class);
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void showDialog() {
        dialog = new ProgressDialog(BaseCEActivity.this);
        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void hideKeyboard(EditText view) {
        view.clearFocus();
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
