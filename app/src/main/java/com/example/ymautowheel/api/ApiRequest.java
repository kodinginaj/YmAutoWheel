package com.example.ymautowheel.api;

import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;
import com.example.ymautowheel.model.ResponseModelNotifikasi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @GET("ban/getmerektipe.php")
    Call<ResponseModelBan> getMerekTipe();

    @FormUrlEncoded
    @POST("ban/insertNerek.php")
    Call<ResponseModel> insertMerek(
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("ban/insertTipe.php")
    Call<ResponseModel> insertTipe(
            @Field("merekId") String merekId,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("ban/updateMerek.php")
    Call<ResponseModel> updateMerek(
            @Field("id") String id,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("ban/updateTipe.php")
    Call<ResponseModel> updateTipe(
            @Field("id") String id,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("ban/deleteMerek.php")
    Call<ResponseModel> deleteMerek(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("ban/deleteTipe.php")
    Call<ResponseModel> deleteTipe(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("ban/getBan.php")
    Call<ResponseModelBan> getBan(
            @Field("merekId") String merekId,
            @Field("tipeId") String tipeId
    );

    @GET("getNotifikasi.php")
    Call<ResponseModelNotifikasi> getNotifikasi();
}
