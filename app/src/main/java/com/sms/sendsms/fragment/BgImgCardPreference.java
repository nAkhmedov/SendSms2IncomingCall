package com.sms.sendsms.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Navruz on 17.05.2016.
 */
public class BgImgCardPreference extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCardPreference.class);

    public static SharedPreferences.OnSharedPreferenceChangeListener listener;
    private SharedPreferences prefs = null;

    //    private EditTextPreference bgText;
    private Preference bgFileName;
    private Preference bgFile;
    private Business business;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.bg_img_pref_content);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        business = EditCardActivity.business;

        //Background item
//        bgText = (EditTextPreference) findPreference("bg_text");
        bgFileName = findPreference("backgroundimage");
        bgFile = findPreference("backgroundimage_base64");
        bgFile.setOnPreferenceClickListener(this);

//        bgText.setSummary(business.getBackgroundImage());
        bgFile.setSummary(business.getBackgroundImage());


        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setNewValues(sharedPreferences, key);
            }
        };
    }

    private void setNewValues(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "": {
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (prefs != null)
            prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (prefs != null)
            prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "backgroundimage_base64": {
//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, ContextConstants.SELECT_BG_PHOTO);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), ContextConstants.SELECT_BG_PHOTO);
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case ContextConstants.SELECT_BG_PHOTO:
                if(resultCode == getActivity().RESULT_OK){
                    Uri selectedImage = intent.getData();
                    try {
                        InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);
                        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file1", "file1", requestFile);

//                        String base64String = ImageUtil.encodeToBase64(imageBitmap);
//                        LOGGER.info("JSON = " + base64String);
                        ((EditCardActivity) getActivity()).uploadFile2Api("backgroundimage_base64", multipartBody);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
