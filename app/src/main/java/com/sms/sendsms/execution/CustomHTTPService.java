package com.sms.sendsms.execution;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Navruz on 22.08.2015.
 */
public interface CustomHTTPService {

    @GET("login.asp")
    Call<String> sendAuthRequest(@Query("username") String username, @Query("password") String password);

    @GET("login.asp")
    Call<String> sendMessageBodyRequest(@Query("action") String action, @Query("code") String code);

    @GET("read.asp")
    Call<JsonObject> sendBusinessDetailRequest(@Query("code") String code);

    @FormUrlEncoded
    @POST("write.asp")
    Call<String> sendCardData(@Query("code") String code, @Field("businessname") String businessname);
}