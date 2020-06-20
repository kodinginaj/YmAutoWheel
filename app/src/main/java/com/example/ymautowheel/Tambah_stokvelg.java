package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Tambah_stokvelg extends AppCompatActivity {

    EditText etSpesifikasi,etHarga,etJumlah;
    Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stokvelg);

        Intent data = getIntent();
        String idMerek = data.getStringExtra("idMerek");
        String idKategori = data.getStringExtra("idKategori");
        String nama = data.getStringExtra("nama");

        btnTambah = findViewById(R.id.btnTambah);

        etSpesifikasi = findViewById(R.id.etSpesifikasi);
        etHarga = findViewById(R.id.etHarga);
        etJumlah = findViewById(R.id.etJumlah);
        btnTambah = findViewById(R.id.btnTambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spesifikasi = etSpesifikasi.getText().toString();
                String harga = etHarga.getText().toString();
                String jumlah = etJumlah.getText().toString();

                Log.d("TESS",""+idKategori);
                Log.d("TESS",""+idMerek);
                Log.d("TESS",""+spesifikasi);
                Log.d("TESS",""+jumlah);
                Log.d("TESS",""+harga);

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModel> insertVelg = api.insertVelg(idKategori,idMerek,spesifikasi,jumlah,harga);
                insertVelg.enqueue(new Callback<ResponseModel>() {

                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Toast.makeText(Tambah_stokvelg.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent pindah = new Intent(Tambah_stokvelg.this, Informasi_stokvelg.class );
                        Log.d("HARUSNYA 12",""+idKategori);
                        pindah.putExtra("idKategori",idMerek);
                        pindah.putExtra("nama",nama);
                        pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(pindah);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(Tambah_stokvelg.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    }