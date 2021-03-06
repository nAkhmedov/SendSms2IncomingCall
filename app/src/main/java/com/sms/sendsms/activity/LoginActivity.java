package com.sms.sendsms.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sms.sendsms.ApplicationLoader;
import com.sms.sendsms.R;
import com.sms.sendsms.constants.ContextConstants;
import com.sms.sendsms.database.User;
import com.sms.sendsms.execution.CustomHTTPService;
import com.sms.sendsms.service.SendSmsService;
import com.sms.sendsms.util.NullOnEmptyConverterFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginActivity.class);

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    public static CustomHTTPService http;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);

            try {
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();//If need to logging, just uncomment
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(interceptor)
                        .build();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ContextConstants.APP_URL)
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();

                http = retrofit.create(CustomHTTPService.class);

                LOGGER.info("Send login request");
                http.sendAuthRequest(email, password, "newlogin").enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String body = response.body();
                        if (!response.isSuccessful() || body == null) {
                            showProgress(false);
                            LOGGER.error("ERROR: resultValue = " + response);
                            showMessage(getString(R.string.no_response));
                            return;
                        }
                        final String[] result = body.trim().split("\\|");
                        final String resultCode = result[0];
                        final String guid = result[1];
                        http.sendMessageBodyRequest("getsms", resultCode).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    byte[] messageBytes = response.body().bytes();
                                    String messageBody = new String(messageBytes);
                                    if (messageBody.isEmpty()) {
                                        messageBody = "Empty string by server";
                                    }
                                    User user = new User();
                                    user.setEmail(email);
                                    user.setPassword(password);
                                    user.setMessageCode(resultCode);
                                    user.setMessageBody(messageBody);
                                    user.setGuid(guid);
                                    ApplicationLoader.getApplication(LoginActivity.this)
                                            .getDaoSession()
                                            .getUserDao()
                                            .insert(user);
                                    startMainActivity();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable error) {
                                showProgress(false);
                                LOGGER.error("ERROR: Auth getMessageBody content = " + error + " messageError = " + error.getMessage());
                                showMessage(error.getMessage());
                                error.printStackTrace();
                            }
                        });
//                        http.sendMessageBodyRequest("getsms", resultCode).enqueue(new Callback<String>() {
//                            @Override
//                            public void onResponse(Call<String> call, Response<String> response) {
//                                String messageBody = response.body();
//                                if (messageBody == null || messageBody.isEmpty()) {
//                                    messageBody = "Empty string by server";
//                                }
//                                User user = new User();
//                                user.setEmail(email);
//                                user.setPassword(password);
//                                user.setMessageCode(resultCode);
//                                user.setMessageBody(messageBody);
//                                user.setGuid(guid);
//                                ApplicationLoader.getApplication(LoginActivity.this)
//                                        .getDaoSession()
//                                        .getUserDao()
//                                        .insert(user);
//                                startMainActivity();
//                            }
//
//                            @Override
//                            public void onFailure(Call<String> call, Throwable error) {
//                                showProgress(false);
//                                LOGGER.error("ERROR: Auth getMessageBody content = " + error + " messageError = " + error.getMessage());
//                                showMessage(error.getMessage());
//                                error.printStackTrace();
//                            }
//                        });
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable err) {
                        showProgress(false);
                        showMessage(getString(R.string.no_response));
                        LOGGER.error("ERROR: auth user = " + err.getMessage());
                        err.printStackTrace();
                    }
                });
            } catch (Exception err) {
                showProgress(false);
                showMessage(err.getMessage());
                LOGGER.error("Error " + err.getMessage());
                err.printStackTrace();
            }
        }
    }

    private void startMainActivity() {
        startSmsService();
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }

    private void startSmsService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("starting: {}", SendSmsService.class.getSimpleName());
                Intent homeBridgeServiceIntent = new Intent(LoginActivity.this, SendSmsService.class);
                startService(homeBridgeServiceIntent);
            }
        }).start();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showMessage(String text) {
        Snackbar.make(mEmailView, text, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }
}

