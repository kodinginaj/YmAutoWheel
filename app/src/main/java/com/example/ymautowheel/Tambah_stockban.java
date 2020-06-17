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

public class Tambah_stockban extends AppCompatActivity {

    EditText etKadaluarsa,etHarga,etJumlah,etUkuran;
    Button btnTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stockban);

        Intent data = getIntent();
        String idTipe = data.getStringExtra("idTipe");
        String idMerek = data.getStringExtra("idMerek");

        btnTambah = findViewById(R.id.btnTambah);

        etKadaluarsa = findViewById(R.id.etKadaluarsaF);
        etHarga = findViewById(R.id.etHargaF);
        etJumlah = findViewById(R.id.etJumlahF);
        etUkuran = findViewById(R.id.etUkuranF);

        Log.d("ANJING","TES");

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kadaluarsa = etKadaluarsa.getText().toString();
                String harga = etHarga.getText().toString();
                String jumlah = etJumlah.getText().toString();
                String ukuran = etUkuran.getText().toString();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModel> tambahBan = api.insertBan(idMerek,idTipe,ukuran,kadaluarsa,jumlah,harga);
                tambahBan.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Toast.makeText(Tambah_stockban.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent pindah = new Intent(Tambah_stockban.this, Informasi_stokban.class );
                        pindah.putExtra("idMerek",idMerek);
                        pindah.putExtra("idTipe",idTipe);
                        startActivity(pindah);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(Tambah_stockban.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}