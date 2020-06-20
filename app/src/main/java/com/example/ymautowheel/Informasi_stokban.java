package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterMerekBan;
import com.example.ymautowheel.adapter.AdapterStokBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.BanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Informasi_stokban extends AppCompatActivity {

    private TextView tvBan;

    private RecyclerView tampilBan;
    private RecyclerView.LayoutManager layoutBan;
    private RecyclerView.Adapter adapterBan;

    private List<BanModel> listBan;
    private Button btnTambah;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_stokban);

        Intent data = getIntent();
        String idTipe = data.getStringExtra("idTipe");
        String idMerek = data.getStringExtra("idMerek");
        String nama = data.getStringExtra("nama");


        tvBan = findViewById(R.id.tvBan);
        tvBan.setText(nama);

        tampilBan = findViewById(R.id.tampilStokBan);
        layoutBan = new LinearLayoutManager(Informasi_stokban.this, RecyclerView.VERTICAL, false);
        tampilBan.setLayoutManager(layoutBan);

        ivBack = findViewById(R.id.ivBackStockBan);
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
                Intent pindah = new Intent(Informasi_stokban.this, Tambah_stockban.class );
                pindah.putExtra("idTipe",idTipe);
                pindah.putExtra("idMerek",idMerek);
                pindah.putExtra("nama",nama);
                startActivity(pindah);
            }
        });

        Session session = new Session(Informasi_stokban.this);
        if(session.getRole().equals("0")){
            btnTambah.setVisibility(View.GONE);
        }


        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelBan> getBan = api.getBan(idMerek,idTipe);
        getBan.enqueue(new Callback<ResponseModelBan>() {
            @Override
            public void onResponse(Call<ResponseModelBan> call, Response<ResponseModelBan> response) {
                listBan = response.body().getBans();

                adapterBan = new AdapterStokBan(Informasi_stokban.this, listBan,nama);
                tampilBan.setAdapter(adapterBan);
                adapterBan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModelBan> call, Throwable t) {
                Toast.makeText(Informasi_stokban.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

    }
}