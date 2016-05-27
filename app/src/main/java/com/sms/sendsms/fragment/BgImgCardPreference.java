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
import android.support.design.widget.Snackbar;
import android.view.MenuItem;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.activity.EditCardActivity;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.Business;
import com.sms.sendsms.database.User;
import com.sms.sendsms.execution.CustomHTTPService;
import com.sms.sendsms.util.ImageUtil;
import com.sms.sendsms.util.ImageViewPreference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Navruz on 17.05.2016.
 */
public class BgImgCardPreference extends BaseCEFragment implements Preference.OnPreferenceClickListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCardPreference.class);

    //    private EditTextPreference bgText;
    private ImageViewPreference bgImgdRes;
    private Preference bgFile;
    private Preference removeBgPref;
    private Business business;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.bg_img_pref_content);


        user = ApplicationLoader.getApplication(getActivity())
                .getDaoSession()
                .getUserDao()
                .queryBuilder()
                .unique();

        business = EditCardActivity.business;

        downloadCurrentBgImg();

        //Background item
//        bgText = (EditTextPreference) findPreference("bg_text");
        bgImgdRes = (ImageViewPreference) findPreference("backgroundimage");
        bgFile = findPreference("backgroundimage_base64");
        removeBgPref = findPreference("remove_bg");
        bgFile.setOnPreferenceClickListener(this);
        removeBgPref.setOnPreferenceClickListener(this);

//        Bitmap bitmapImgData;
//        bgImgdRes.setImage(bitmapImgData);
//        bgText.setSummary(business.getBackgroundImage());
    }

    private void downloadCurrentBgImg() {
        showDialog(getActivity());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//If need to logging, just uncomment
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContextConstants.CARD_URL)
                .client(client)
                .build();
        CustomHTTPService downloadService = retrofit.create(CustomHTTPService.class);
        String fileUrl = "images/" + business.getBackgroundImage();
        Call<ResponseBody> call = downloadService.getBgImg(fileUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    LOGGER.debug("server contacted and has file");

                    File file = ImageUtil.writeResponseBodyToDisk(response.body());

                    if (file != null) {
                        Bitmap bitmap = ImageUtil.convertFile2Bitmap(file);
                        setBgImg2Ui(bitmap);
                    } else {
                        LOGGER.error("Couldn't write body to file");
                    }
                } else {
                    LOGGER.debug("server contact failed");
                }
                dismissDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dismissDialog();
                t.printStackTrace();
                LOGGER.error("error = " + t.getMessage());
            }
        });
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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), ContextConstants.SELECT_BG_IMG);
                break;
            }
            case "remove_bg": {
                bgImgdRes.removeImg();
                ((EditCardActivity) getActivity()).updateCard("backgroundimage", "");
                ((EditCardActivity) getActivity()).resendRequestData();
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case ContextConstants.SELECT_BG_IMG:
                if(resultCode == getActivity().RESULT_OK){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Uri selectedImage = intent.getData();
                            try {
                                InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
                                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] bytes = baos.toByteArray();
                                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), bytes);
                                MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file1", "fileName", requestFile);

//                        String base64String = ImageUtil.encodeToBase64(imageBitmap);
//                        LOGGER.info("JSON = " + base64String);
                                ((EditCardActivity) getActivity()).uploadFile2Api("backgroundimage_base64", multipartBody);
                                setBgImg2Ui(imageBitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
        }
    }

    private void setBgImg2Ui(final Bitmap imageBitmap) {
        if (getActivity() == null || imageBitmap == null) {
            Snackbar.make(getView(), R.string.cant_download_img,
                    Snackbar.LENGTH_LONG).show();
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bgImgdRes.setImage(imageBitmap);
            }
        });
    }
}
