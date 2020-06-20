package com.example.ymautowheel.api;

import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;
import com.example.ymautowheel.model.ResponseModelNotifikasi;
import com.example.ymautowheel.model.ResponseModelSuspensi;
import com.example.ymautowheel.model.ResponseModelVelg;

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
    @POST("ban/insertMerek.php")
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

    @GET("suspensi/getKategoriMerek.php")
    Call<ResponseModelSuspensi> getKategoriMerekSuspensi();

    @FormUrlEncoded
    @POST("suspensi/searchKategori.php")
    Call<ResponseModelSuspensi> searchSuspensi(
            @Field("keyword") String keyword
    );

    @FormUrlEncoded
    @POST("suspensi/insertKategori.php")
    Call<ResponseModel> insertKategoriSuspensi(
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("suspensi/updateKategori.php")
    Call<ResponseModel> updateKategoriSuspensi(
            @Field("id") String id,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("suspensi/deleteKategori.php")
    Call<ResponseModel> deleteKategoriSuspensi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("suspensi/insertMerek.php")
    Call<ResponseModel> insertMerekSuspensi(
            @Field("kategoriId") String kategoriId,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("suspensi/updateMerek.php")
    Call<ResponseModel> updateMerekSuspensi(
            @Field("id") String id,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("suspensi/deleteMerek.php")
    Call<ResponseModel> deleteMerekSuspensi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("suspensi/getSuspensi.php")
    Call<ResponseModelSuspensi> getSuspensi(
            @Field("kategoriId") String kategoriId,
            @Field("merekId") String merekId
    );

    @FormUrlEncoded
    @POST("suspensi/insertSuspensi.php")
    Call<ResponseModel> insertSuspensi(
            @Field("kategoriId") String kategoriId,
            @Field("merekId") String merekId,
            @Field("spesifikasi") String Spesifikasi,
            @Field("jumlah") String jumlah,
            @Field("harga") String harga
    );

    @FormUrlEncoded
    @POST("suspensi/kurangStockSuspensi.php")
    Call<ResponseModel> kurangSuspensi(
            @Field("id") String kategoriId,
            @Field("jumlahTotal") String jumlahTotal,
            @Field("jumlahKurang") String jumlahKurang
    );

    @FormUrlEncoded
    @POST("suspensi/tambahStockSuspensi.php")
    Call<ResponseModel> tambahSuspensi(
            @Field("id") String id,
            @Field("jumlahTotal") String jumlahTotal,
            @Field("jumlahTambah") String jumlahTambah
    );

    @FormUrlEncoded
    @POST("suspensi/deleteSuspensi.php")
    Call<ResponseModel> deleteSuspensi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("suspensi/updateSuspensi.php")
    Call<ResponseModel> updateSuspensi(
            @Field("id") String id,
            @Field("spesifikasi") String Spesifikasi,
            @Field("harga") String harga
    );

    @GET("velg/getMerek.php")
    Call<ResponseModelVelg> getMerekVelg();

    @FormUrlEncoded
    @POST("velg/searchMerek.php")
    Call<ResponseModelVelg> searchMerekVelg(
            @Field("keyword") String Query
    );

    @FormUrlEncoded
    @POST("velg/insertMerekOriginal.php")
    Call<ResponseModel> insertMerekOriginal(
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("velg/insertMerekReplika.php")
    Call<ResponseModel> insertMerekReplika(
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("velg/updateMerek.php")
    Call<ResponseModel> updateMerekVelg(
            @Field("id") String id,
            @Field("nama") String nama
    );

    @FormUrlEncoded
    @POST("velg/deleteMerek.php")
    Call<ResponseModel> deleteMerkVelg(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("velg/getVelg.php")
    Call<ResponseModelVelg> getVelg(
            @Field("merekId") String merekId
    );

    @FormUrlEncoded
    @POST("velg/insertVelg.php")
    Call<ResponseModel> insertVelg(
            @Field("kategoriId") String kategoriId,
            @Field("merekId") String merekId,
            @Field("spesifikasi") String spesifikasi,
            @Field("jumlah") String jumlah,
            @Field("harga") String harga
    );

    @FormUrlEncoded
    @POST("velg/updateVelg.php")
    Call<ResponseModel> updateVelg(
            @Field("id") String id,
            @Field("spesifikasi") String spesifikasi,
            @Field("harga") String harga
    );

    @FormUrlEncoded
    @POST("velg/kurangStockVelg.php")
    Call<ResponseModel> kurangStockVelg(
            @Field("id") String id,
            @Field("jumlahTotal") String jumlahTotal,
            @Field("jumlahKurang") String jumlahKurang
    );

    @FormUrlEncoded
    @POST("velg/tambahStockVelg.php")
    Call<ResponseModel> tambahStockVelg(
            @Field("id") String id,
            @Field("jumlahTotal") String jumlahTotal,
            @Field("jumlahTambah") String jumlahTambah
    );

    @FormUrlEncoded
    @POST("velg/deleteVelg.php")
    Call<ResponseModel> deleteVelg(
            @Field("id") String id
    );
}


