package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterMerekBan;
import com.example.ymautowheel.adapter.AdapterStokBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.BanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Informasi_stokban extends AppCompatActivity {

    TextView tvBan;

    private RecyclerView tampilBan;
    private RecyclerView.LayoutManager layoutBan;
    private RecyclerView.Adapter adapterBan;

    List<BanModel> listBan;

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

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelBan> getBan = api.getBan(idMerek,idTipe);
        getBan.enqueue(new Callback<ResponseModelBan>() {
            @Override
            public void onResponse(Call<ResponseModelBan> call, Response<ResponseModelBan> response) {
                listBan = response.body().getBans();

                adapterBan = new AdapterStokBan(Informasi_stokban.this, listBan);
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