package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ubah_stocksuspensi extends AppCompatActivity {

    EditText etSpesifikasi,etHarga;
    Button btnUbah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_stocksuspensi);

        Intent data = getIntent();
        String spesifikasi = data.getStringExtra("Spesifikasi");
        String harga = data.getStringExtra("Harga");
        String id = data.getStringExtra("Id");
        String merekId = data.getStringExtra("MerekId");
        String kategoriId = data.getStringExtra("KategoriId");

        btnUbah = findViewById(R.id.btnUbah);

        etSpesifikasi = findViewById(R.id.etSpek);
        etHarga = findViewById(R.id.etHarga);

        etSpesifikasi.setText(spesifikasi);
        etHarga.setText(harga);

//        btnUbah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String spekbaru = etSpesifikasi.getText().toString();
//                String hargaBaru = etHarga.getText().toString();
//
//                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
//                Call<ResponseModel> updateSuspensi = api.updateS(id,ukuranBaru,hargaBaru);
//                updateBan.enqueue(new Callback<ResponseModel>() {
//                    @Override
//                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
////                        Log.d("CEK",""+expired);
//
//                        Toast.makeText(Ubah_stockban.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        Intent pindah = new Intent(Ubah_stockban.this, Informasi_stokban.class);
//                        pindah.putExtra("idMerek", idMerek);
//                        pindah.putExtra("idTipe", idTipe);
//                        startActivity(pindah);
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseModel> call, Throwable t) {
//                        Toast.makeText(Ubah_stockban.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }
}