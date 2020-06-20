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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterStokBan;
import com.example.ymautowheel.adapter.AdapterVelg;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.BanModel;
import com.example.ymautowheel.model.ResponseModelBan;
import com.example.ymautowheel.model.ResponseModelVelg;
import com.example.ymautowheel.model.VelgModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Informasi_stokvelg extends AppCompatActivity {

    private TextView etNama;

    private RecyclerView tampilVelg;
    private RecyclerView.LayoutManager layoutVelg;
    private RecyclerView.Adapter adapterVelg;

    private List<VelgModel> listVelg;
    private Button btnTambah;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_stokvelg);

        Intent data = getIntent();
        String idMerek = data.getStringExtra("idMerek");
        String idKategori = data.getStringExtra("idKategori");
        String nama = data.getStringExtra("nama");

        etNama = findViewById(R.id.etNama);
        etNama.setText(nama);

        tampilVelg = findViewById(R.id.rvVelg);
        layoutVelg = new LinearLayoutManager(Informasi_stokvelg.this, RecyclerView.VERTICAL, false);
        tampilVelg.setLayoutManager(layoutVelg);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Informasi_stokvelg.this, Tambah_stokvelg.class );
                pindah.putExtra("idKategori",idKategori);
                pindah.putExtra("idMerek",idMerek);
                pindah.putExtra("nama",nama);
                startActivity(pindah);
            }
        });

        Session session = new Session(Informasi_stokvelg.this);
        if(session.getRole().equals("0")){
            btnTambah.setVisibility(View.GONE);
        }


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Informasi_stokvelg.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("HARUSNYA 12",""+idKategori);

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelVelg> getVelg = api.getVelg(idKategori);
        Log.d("HEHE",""+idKategori);
        getVelg.enqueue(new Callback<ResponseModelVelg>() {
            @Override
            public void onResponse(Call<ResponseModelVelg> call, Response<ResponseModelVelg> response) {
                listVelg = response.body().getVelgs();
                progressDialog.dismiss();

                adapterVelg = new AdapterVelg(Informasi_stokvelg.this, listVelg, nama);
                tampilVelg.setAdapter(adapterVelg);
                adapterVelg.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModelVelg> call, Throwable t) {
                Toast.makeText(Informasi_stokvelg.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}