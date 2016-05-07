package com.sms.sendsms.execution;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Navruz on 22.08.2015.
 */
public interface CustomHTTPService {

    @GET("/login.asp")
    void sendAuthRequest(@Query("username") String username, @Query("password") String password, Callback<String> response);

    @GET("/login.asp")
    void sendMessageBodyRequest(@Query("action") String action, @Query("code") String code, Callback<String> response);

    @GET("/read.asp")
    void sendBusinessDetailRequest(@Query("code") String code, Callback<JsonObject> response);
}
