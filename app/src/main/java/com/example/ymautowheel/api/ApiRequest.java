package com.example.ymautowheel.api;

import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;

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

    @FormUrlEncoded
    @POST("ban/insertBan.php")
    Call<ResponseModel> insertBan(
            @Field("merekId") String merekId,
            @Field("tipeId") String tipeId,
            @Field("ukuran") String ukuran,
            @Field("kadaluarsa") String kadaluarsa,
            @Field("jumlah") String jumlah,
            @Field("harga") String harga
    );

    @FormUrlEncoded
    @POST("ban/updateBan.php")
    Call<ResponseModel> updateBan(
            @Field("id") String id,
            @Field("ukuran") String ukuran,
            @Field("harga") String harga
    );

    @FormUrlEncoded
    @POST("ban/deleteBan.php")
    Call<ResponseModel> deleteBan(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("ban/tambahStockBan.php")
    Call<ResponseModel> tambahBan(
            @Field("id") String id,
            @Field("jumlahTotal") String jumlahTotal,
            @Field("jumlahTambah") String jumlahTambah
    );

    @FormUrlEncoded
    @POST("ban/kurangStockBan.php")
    Call<ResponseModel> kurangBan(
            @Field("id") String id,
            @Field("jumlahTotal") String jumlahTotal,
            @Field("jumlahKurang") String jumlahKurang
    );

    @FormUrlEncoded
    @POST("ban/searchMerek.php")
    Call<ResponseModelBan> searchMerek(
            @Field("keyword") String query
    );

}
