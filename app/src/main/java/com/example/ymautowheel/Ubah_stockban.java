package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ubah_stockban extends AppCompatActivity {

    private EditText etHarga,etUkuran;
    private Button btnUbah;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_stockban);

        Intent data = getIntent();
        String idTipe = data.getStringExtra("idTipe");
        String idMerek = data.getStringExtra("idMerek");
        String id = data.getStringExtra("Id");
        String ukuran = data.getStringExtra("Ukuran");
        String harga = data.getStringExtra("Harga");
        String expired = data.getStringExtra("Expired");
        String nama = data.getStringExtra("nama");

        btnUbah = findViewById(R.id.btnUbah);

        etHarga = findViewById(R.id.etHargaF);
        etUkuran = findViewById(R.id.etUkuranF);

        etUkuran.setText(ukuran);
        etHarga.setText(harga);

        ivBack = findViewById(R.id.ivBackUbahStockBan);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ukuranBaru = etUkuran.getText().toString();
                String hargaBaru = etHarga.getText().toString();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModel> updateBan = api.updateBan(id,ukuranBaru,hargaBaru);

                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(Ubah_stockban.this);
                progressDialog.setMessage("Mohon tunggu....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();

                updateBan.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                        Log.d("CEK",""+expired);

                        Toast.makeText(Ubah_stockban.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent pindah = new Intent(Ubah_stockban.this, Informasi_stokban.class);
                        pindah.putExtra("idMerek", idMerek);
                        pindah.putExtra("idTipe", idTipe);
                        pindah.putExtra("nama", nama);
                        pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        progressDialog.dismiss();
                        startActivity(pindah);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(Ubah_stockban.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}