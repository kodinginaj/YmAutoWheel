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

public class Ubah_stockvelg extends AppCompatActivity {

    EditText etSpesifikasi,etHarga;
    Button btnUbah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_stockvelg);

        Intent data = getIntent();
        String spesifikasi = data.getStringExtra("Spesifikasi");
        String harga = data.getStringExtra("Harga");
        String id = data.getStringExtra("Id");
        String merekId = data.getStringExtra("merekId");
        String nama = data.getStringExtra("nama");

        btnUbah = findViewById(R.id.btnUbah);

        etSpesifikasi = findViewById(R.id.etSpesifikasi);
        etHarga = findViewById(R.id.etHarga);

        etSpesifikasi.setText(spesifikasi);
        etHarga.setText(harga);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spekbaru = etSpesifikasi.getText().toString();
                String hargaBaru = etHarga.getText().toString();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModel> updateVelg = api.updateVelg(id,spekbaru,hargaBaru);
                updateVelg.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                        Log.d("CEK",""+expired);

                        Toast.makeText(Ubah_stockvelg.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent pindah = new Intent(Ubah_stockvelg.this, Informasi_stokvelg.class);
                        pindah.putExtra("idKategori", merekId);
                        pindah.putExtra("nama",nama);
                        startActivity(pindah);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(Ubah_stockvelg.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}