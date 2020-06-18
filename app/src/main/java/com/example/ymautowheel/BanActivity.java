package com.example.ymautowheel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterMerekBan;
import com.example.ymautowheel.adapter.AdapterStokBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanActivity extends AppCompatActivity {

    private RecyclerView tampilBan;
    private RecyclerView.LayoutManager layoutBan;
    private RecyclerView.Adapter adapterBan;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    SearchView search;

    ImageView ivBack, ivNotif;

    List<MerekBanModel> listBan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(BanActivity.this, MainActivity.class);
                startActivity(pindah);
            }
        });

        search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModelBan> getBan = api.searchMerek(query);
                getBan.enqueue(new Callback<ResponseModelBan>() {
                    @Override
                    public void onResponse(Call<ResponseModelBan> call, Response<ResponseModelBan> response) {
                        listBan = response.body().getMerek_bans();

                        adapterBan = new AdapterMerekBan(BanActivity.this, listBan);
                        tampilBan.setAdapter(adapterBan);
                        adapterBan.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseModelBan> call, Throwable t) {
                        Toast.makeText(BanActivity.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModelBan> getMerekTipe = api.getMerekTipe();
                    getMerekTipe.enqueue(new Callback<ResponseModelBan>() {
                        @Override
                        public void onResponse(Call<ResponseModelBan> call, Response<ResponseModelBan> response) {
                            listBan = response.body().getMerek_bans();

                            adapterBan = new AdapterMerekBan(BanActivity.this, listBan);
                            tampilBan.setAdapter(adapterBan);
                            adapterBan.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ResponseModelBan> call, Throwable t) {
                            Toast.makeText(BanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return true;
            }
        });

        ivNotif = findViewById(R.id.ivAddMerekBan);
        ivNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
            }
        });

        tampilBan = findViewById(R.id.tampilMerekBan);
        layoutBan = new LinearLayoutManager(BanActivity.this, RecyclerView.VERTICAL, false);
        tampilBan.setLayoutManager(layoutBan);

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelBan> getMerekTipe = api.getMerekTipe();
        getMerekTipe.enqueue(new Callback<ResponseModelBan>() {
            @Override
            public void onResponse(Call<ResponseModelBan> call, Response<ResponseModelBan> response) {
                listBan = response.body().getMerek_bans();

                adapterBan = new AdapterMerekBan(BanActivity.this, listBan);
                tampilBan.setAdapter(adapterBan);
                adapterBan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModelBan> call, Throwable t) {
                Toast.makeText(BanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(BanActivity.this);
        inflater = LayoutInflater.from(BanActivity.this);
        dialogView = inflater.inflate(R.layout.modal_merekban, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        EditText komplain = dialogView.findViewById(R.id.etTipeBan);

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = komplain.getText().toString();

                if (!komplain.getText().toString().isEmpty()){
                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> insertMerek = api.insertMerek(nama);

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(BanActivity.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    insertMerek.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(BanActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = getIntent();
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            progressDialog.dismiss();
                            startActivity(pindah);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(BanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
