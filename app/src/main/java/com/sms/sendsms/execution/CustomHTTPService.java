package com.sms.sendsms.execution;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Navruz on 22.08.2015.
 */
public interface CustomHTTPService {

    @GET("login.asp")
    Call<String> sendAuthRequest(@Query("username") String username, @Query("password") String password, @Query("action") String newLogin);

    @GET("login.asp")
    Call<String> sendMessageBodyRequest(@Query("action") String action, @Query("code") String code);

    @GET("read.asp")
    Call<JsonObject> sendBusinessDetailRequest(@Query("code") String code);

    @POST("write.asp")
    Call<String> sendCardData(@Query("code") String code, @Query("name") String name, @Body JsonObject businessname);
}