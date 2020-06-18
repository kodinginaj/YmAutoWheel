package com.example.ymautowheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterNotif;
import com.example.ymautowheel.adapter.AdapterStokBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.NotifikasiModel;
import com.example.ymautowheel.model.ResponseModelNotifikasi;

import java.util.List;

public class Notification extends AppCompatActivity {
    private RecyclerView tampilNotif;
    private RecyclerView.LayoutManager layoutNotif;
    private RecyclerView.Adapter adapterNotif;
    private List<NotifikasiModel> listNotif;
    private ImageView iBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));

        iBack = findViewById(R.id.ivNotifBack);
        tampilNotif = findViewById(R.id.rvNotifikasi);
        layoutNotif = new LinearLayoutManager(Notification.this, RecyclerView.VERTICAL, false);
        tampilNotif.setLayoutManager(layoutNotif);

        iBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ApiRequest apiRequest = Retroserver.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelNotifikasi> getNotif = apiRequest.getNotifikasi();
        getNotif.enqueue(new Callback<ResponseModelNotifikasi>() {
            @Override
            public void onResponse(Call<ResponseModelNotifikasi> call, Response<ResponseModelNotifikasi> response) {
                if (response.body()!= null){
                    listNotif = response.body().getNotifications();
                    adapterNotif = new AdapterNotif(Notification.this, listNotif);
                    tampilNotif.setAdapter(adapterNotif);
                    adapterNotif.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelNotifikasi> call, Throwable t) {
                Toast.makeText(Notification.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

    }
}