package com.sms.sendsms.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.constants.ActionNames;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.User;
import com.sms.sendsms.service.SendSmsService;
import com.sms.sendsms.util.ReportHelper;
import com.sms.sendsms.util.ServiceDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by Navruz on 29.03.2016.
 */
public class MainActivity extends BaseCEActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);
    public static boolean IS_ACTIVITY_ALIVE = false;

    private SharedPreferences sharedPref;
    private RelativeLayout mainLayout;
    private ReportHelper reportHelper;
    private User user;

    private final static String LOG_EXT = "log";
    private final static String ERROR_LOG_PREFIX = "error";
    private final static String MAIN_LOG_PREFIX = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        user = ApplicationLoader.getApplication(MainActivity.this)
                .getDaoSession()
                .getUserDao()
                .queryBuilder()
                .unique();

        sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        Switch toggleNtf = (Switch) findViewById(R.id.toggle_ntf);
        Switch toggleService = (Switch) findViewById(R.id.toggle_service);
        Button logOutBtn = (Button) findViewById(R.id.log_out);
        TextView preview = (TextView) findViewById(R.id.preview);
        toggleNtf.setChecked(sharedPref.getBoolean(getString(R.string.is_enable_notifying), true));
        toggleService.setChecked(ServiceDetector.isSmsServiceRunning(this));

        toggleNtf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LOGGER.info("toggleNtf onCheckedChanged = " + isChecked);
                sharedPref.edit().putBoolean(getString(R.string.is_enable_notifying), isChecked).apply();
            }
        });

        toggleService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LOGGER.info("toggleService onCheckedChanged = " + isChecked);
                sharedPref.edit().putBoolean(getString(R.string.is_service_running), isChecked).apply();
                toggleService(isChecked);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        Resources res = getResources();
        String link = "<a href=\"" + ContextConstants.CARD_URL + user.getMessageCode() + "\">" +  res.getString(R.string.preview)+ "</a>";
        CharSequence text = Html.fromHtml(String.format(res.getString(R.string.preview_link), link));
        preview.setText(text);
        preview.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_send_report: {
                sendReport();
                break;
            }
            case R.id.item_settings: {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            }
            case R.id.share: {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, user.getMessageBody());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_via)));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleService(boolean isEnabled) {
        if (isEnabled) {
            if (!ServiceDetector.isSmsServiceRunning(this)) {
                startService(new Intent(MainActivity.this, SendSmsService.class));
            } else {
                LOGGER.info("Service is enabled and already running");
            }
        } else {
            LOGGER.info("Service is stopping");
            stopService(new Intent(MainActivity.this, SendSmsService.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IS_ACTIVITY_ALIVE = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(logoutReceiver,
                new IntentFilter(ActionNames.ACTION_LOG_OUT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IS_ACTIVITY_ALIVE = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(logoutReceiver);
    }

    public void showSmsReports(View view) {
        startActivity(new Intent(MainActivity.this, SmsHistoryActivity.class));
    }

    public void startBlockActivity(View view) {
        startActivity(new Intent(MainActivity.this, BlockActivity.class));
    }

    public void logout() {
        LOGGER.info("Logging out...");
        new Thread(new Runnable() {
            @Override
            public void run() {//                List<SmsLog> smsLogList = ApplicationLoader.getApplication(MainActivity.this)
//                        .getDaoSession()
//                        .getSmsLogDao()
//                        .queryBuilder()
//                        .where(SmsLogDao.Properties.AccountId.eq(user.getId()))
//                        .list();

                LOGGER.info("Service is stopping");
                stopService(new Intent(MainActivity.this, SendSmsService.class));

                ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getBlackListDao()
                        .deleteAll();
                ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getSmsLogDao()
                        .deleteAll();
                ApplicationLoader.getApplication(MainActivity.this)
                        .getDaoSession()
                        .getUserDao()
                        .delete(user);
                sharedPref.edit().putBoolean(getString(R.string.is_enable_notifying), true).apply();
                sharedPref.edit().putBoolean(getString(R.string.is_service_running), true).apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }).start();
    }

    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOGGER.info("logoutReceiver is received!!!");
            logout();
        }
    };

    public void showEditCard(View view) {
        startActivity(new Intent(MainActivity.this, EditCardActivity.class));
    }

    private void sendReport() {
//        showCustomDialog(getResources().getString(R.string.please_wait));
        reportHelper = new ReportHelper(ApplicationLoader.getAppContext());
        try {
            final File reportFile = reportHelper.createReport();
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//If need to logging, just uncomment
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder()
////                    .addInterceptor(interceptor)
//                    .build();
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(ContextConstants.APP_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(client)
//                    .build();
//            CustomHTTPService http = retrofit.create(CustomHTTPService.class);
//
////            RequestBody requestFile = RequestBody.create(MediaType.parse("application/zip"), reportFile);
////            MultipartBody.Part multipartBody = MultipartBody.Part
////                    .createFormData("archive", reportFile.getName(), requestFile);
//
//            File mainLogFile = new File(ContextConstants.DATA_PATH + File.separator + "logs" + File.separator + MAIN_LOG_PREFIX + "." + LOG_EXT);
////            File errorLogFile = new File(ContextConstants.DATA_PATH + File.separator + "logs" + File.separator + ERROR_LOG_PREFIX + "." + LOG_EXT);
//            String logcatReport = LogCatDumpReader.read();
//            JsonObject logObject = new JsonObject();
//            logObject.addProperty("main", FileUtils.readFileToString(mainLogFile));
//            logObject.addProperty("error", logcatReport);
//            Call<ResponseBody> call = http.sendReport(logObject);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    dismissDialog();
//                    if (response.isSuccessful()) {
//                        Snackbar.make(mainLayout, getString(R.string.report_send_successfully), Snackbar.LENGTH_LONG).show();
//                        reportHelper.deleteReportDir();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable error) {
//                    dismissDialog();
//                    error.printStackTrace();
//                    LOGGER.error("SEnding report error = " + error.getMessage());
//                    Snackbar.make(mainLayout, getString(R.string.report_send_failed), Snackbar.LENGTH_LONG).show();
//                }
//            });

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{ContextConstants.REPORT_ADDRESS});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hi there");
            intent.putExtra(Intent.EXTRA_TEXT   , "Feedback");
            intent.putExtra(Intent.EXTRA_STREAM   , Uri.parse("file://" + reportFile));
            startActivityForResult(Intent.createChooser(intent, "Send report..."), ContextConstants.REPORT_REQUEST_CODE);
        } catch (IOException e) {
            dismissDialog();
            LOGGER.error("Sending report error = " + e.getMessage());
            e.printStackTrace();
            Snackbar.make(mainLayout, getString(R.string.report_send_failed), Snackbar.LENGTH_LONG).show();
        }
//        catch (android.content.ActivityNotFoundException ex) {
//            Snackbar.make(mainLayout, getString(R.string.no_mail_client), Snackbar.LENGTH_LONG).show();
//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ContextConstants.REPORT_REQUEST_CODE) {
//            LOGGER.info("Deleted created report file");
//            reportHelper.deleteReportDir();
//        }
//    }
}
