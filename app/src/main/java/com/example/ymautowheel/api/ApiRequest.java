package com.example.ymautowheel.api;

import com.example.ymautowheel.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("auth/login.php")
    Call<ResponseModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/register.php")
    Call<ResponseModel> register(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("password") String password
    );
}
