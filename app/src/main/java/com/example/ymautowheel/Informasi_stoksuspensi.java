package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterSuspensi;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModelSuspensi;
import com.example.ymautowheel.model.SuspensiModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Informasi_stoksuspensi extends AppCompatActivity {

    TextView tvNamaSuspensi;

    private RecyclerView tampilSuspensi;
    private RecyclerView.LayoutManager layoutSuspensi;
    private RecyclerView.Adapter adapterSuspensi;

    List<SuspensiModel> listSuspensi;
    Button btnTambahSuspensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_stoksuspensi);

        Intent data = getIntent();
        String kategoriId = data.getStringExtra("kategoriId");
        String merekId = data.getStringExtra("merekId");
        String nama = data.getStringExtra("nama");

        Log.d("CACA",""+kategoriId);
        Log.d("CACA",""+merekId);

        tvNamaSuspensi = findViewById(R.id.tvNamaSuspensi);
        tvNamaSuspensi.setText(nama);

        tampilSuspensi = findViewById(R.id.rvSuspensi);
        layoutSuspensi = new LinearLayoutManager(Informasi_stoksuspensi.this, RecyclerView.VERTICAL, false);
        tampilSuspensi.setLayoutManager(layoutSuspensi);

        btnTambahSuspensi = findViewById(R.id.btnTambahSuspensi);
        btnTambahSuspensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Informasi_stoksuspensi.this, Tambah_stocksuspensi.class );
                pindah.putExtra("kategoriId",kategoriId);
                pindah.putExtra("merekId",merekId);
                pindah.putExtra("nama",nama);
                startActivity(pindah);
            }
        });

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Informasi_stoksuspensi.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Session session = new Session(Informasi_stoksuspensi.this);
        if(session.getRole().equals("0")){
            btnTambahSuspensi.setVisibility(View.GONE);
        }

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelSuspensi> getSuspensi = api.getSuspensi(kategoriId,merekId);
        getSuspensi.enqueue(new Callback<ResponseModelSuspensi>() {
            @Override
            public void onResponse(Call<ResponseModelSuspensi> call, Response<ResponseModelSuspensi> response) {
                progressDialog.dismiss();
                listSuspensi = response.body().getSuspensis();

                adapterSuspensi = new AdapterSuspensi(Informasi_stoksuspensi.this, listSuspensi, nama);
                tampilSuspensi.setAdapter(adapterSuspensi);
                adapterSuspensi.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModelSuspensi> call, Throwable t) {
                Toast.makeText(Informasi_stoksuspensi.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
    }



