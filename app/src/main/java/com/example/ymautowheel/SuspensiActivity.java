package com.example.ymautowheel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ymautowheel.adapter.AdapterKategoriSuspensi;
import com.example.ymautowheel.adapter.AdapterMerekBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.KategoriSuspensiModel;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.MerekSuspensiModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;
import com.example.ymautowheel.model.ResponseModelSuspensi;
import com.example.ymautowheel.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuspensiActivity extends AppCompatActivity {

    private RecyclerView tampilSuspensi;
    private RecyclerView.LayoutManager layoutSuspensi;
    private RecyclerView.Adapter adapterSuspensi;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    SearchView search;

    ImageView ivBack, ivNotif;

    List<KategoriSuspensiModel> listSuspensi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspensi);

        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(SuspensiActivity.this, MainActivity.class);
                startActivity(pindah);
            }
        });

        search = findViewById(R.id.search1);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(SuspensiActivity.this);
                progressDialog.setMessage("Mohon tunggu...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModelSuspensi> getSuspensi = api.searchSuspensi(query);
                getSuspensi.enqueue(new Callback<ResponseModelSuspensi>() {
                    @Override
                    public void onResponse(Call<ResponseModelSuspensi> call, Response<ResponseModelSuspensi> response) {
                        listSuspensi = response.body().getKategori_suspensis();

                        adapterSuspensi = new AdapterKategoriSuspensi(SuspensiActivity.this, listSuspensi);
                        tampilSuspensi.setAdapter(adapterSuspensi);
                        adapterSuspensi.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseModelSuspensi> call, Throwable t) {
                        Toast.makeText(SuspensiActivity.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ivNotif = findViewById(R.id.ivNotif);
        ivNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
            }
        });

        Session session = new Session(SuspensiActivity.this);
        if(session.getRole().equals("0")){
            ivNotif.setVisibility(View.GONE);
        }


        tampilSuspensi = findViewById(R.id.kategosiSuspensi);
        layoutSuspensi = new LinearLayoutManager(SuspensiActivity.this, RecyclerView.VERTICAL, false);
        tampilSuspensi.setLayoutManager(layoutSuspensi);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(SuspensiActivity.this);
        progressDialog.setMessage("Mohon tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelSuspensi> getKategori = api.getKategoriMerekSuspensi();
        getKategori.enqueue(new Callback<ResponseModelSuspensi>() {
            @Override
            public void onResponse(Call<ResponseModelSuspensi> call, Response<ResponseModelSuspensi> response) {
                listSuspensi = response.body().getKategori_suspensis();

                adapterSuspensi = new AdapterKategoriSuspensi(SuspensiActivity.this, listSuspensi);
                tampilSuspensi.setAdapter(adapterSuspensi);
                adapterSuspensi.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseModelSuspensi> call, Throwable t) {
                Toast.makeText(SuspensiActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(SuspensiActivity.this);
        inflater = LayoutInflater.from(SuspensiActivity.this);
        dialogView = inflater.inflate(R.layout.modal_suspensi, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        EditText komplain = dialogView.findViewById(R.id.stokBaru);

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = komplain.getText().toString();

                if (!komplain.getText().toString().isEmpty()){

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(SuspensiActivity.this);
                    progressDialog.setMessage("Mohon tunggu...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> insertKategoriSuspensi = api.insertKategoriSuspensi(nama);
                    insertKategoriSuspensi.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(SuspensiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = getIntent();
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(pindah);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(SuspensiActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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