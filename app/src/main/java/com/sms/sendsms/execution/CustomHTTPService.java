package com.sms.sendsms.execution;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Navruz on 22.08.2015.
 */
public interface CustomHTTPService {

    @GET("login.asp")
    Call<String> sendAuthRequest(@Query("username") String username, @Query("password") String password, @Query("action") String newLogin);

    @GET("login.asp")
    Call<ResponseBody> sendMessageBodyRequest(@Query("action") String action, @Query("code") String code);

    @GET("read.asp")
    Call<JsonObject> sendBusinessDetailRequest(@Query("code") String code);

    @POST("write.asp")
    Call<String> sendCardData(@Query("code") String code, @Query("name") String name, @Body JsonObject businessname);

    @Multipart
    @POST("upload.asp")
    Call<ResponseBody> uploadFile(@Query("code") String code, @Query("name") String name,
                                  @Part MultipartBody.Part multipartBody);

    @GET
    Call<ResponseBody> getBgImg(@Url String fileUrl);

    @POST("sendlog.asp")
    Call<ResponseBody> sendReport(@Query("main") String mainLog, @Query("error") String errorLog);
}