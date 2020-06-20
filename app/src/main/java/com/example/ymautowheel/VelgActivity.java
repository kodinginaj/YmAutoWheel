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

import com.example.ymautowheel.adapter.AdapterKategoriVelg;
import com.example.ymautowheel.adapter.AdapterMerekBan;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.KategoriVelgModel;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.ResponseModelBan;
import com.example.ymautowheel.model.ResponseModelVelg;
import com.example.ymautowheel.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VelgActivity extends AppCompatActivity {

    private RecyclerView tampilVelgO;
    private RecyclerView.LayoutManager layouVelgO;
    private RecyclerView.Adapter adapterVelgO;

    private RecyclerView tampilVelgR;
    private RecyclerView.LayoutManager layouVelgR;
    private RecyclerView.Adapter adapterVelgR;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    SearchView search;

    ImageView ivBack, btnTambahReplika, btnTambahOriginal;

    List<KategoriVelgModel> listKategoriO = new ArrayList<>();
    List<KategoriVelgModel> listKategoriR = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velg);

        btnTambahOriginal = findViewById(R.id.btnTambahOriginal);
        btnTambahReplika = findViewById(R.id.btnTambahReplika);

        Session session = new Session(VelgActivity.this);
        if(session.getRole().equals("0")){
            btnTambahOriginal.setVisibility(View.GONE);
            btnTambahReplika.setVisibility(View.GONE);
        }


        search = findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<ResponseModelVelg> getKategoriVelg = api.searchMerekVelg(query);
                getKategoriVelg.enqueue(new Callback<ResponseModelVelg>() {
                    @Override
                    public void onResponse(Call<ResponseModelVelg> call, Response<ResponseModelVelg> response) {
                        listKategoriO = response.body().getVelg_originals();

                        adapterVelgO = new AdapterKategoriVelg(VelgActivity.this, listKategoriO);
                        tampilVelgO.setAdapter(adapterVelgO);
                        adapterVelgO.notifyDataSetChanged();

                        listKategoriR = response.body().getVelg_replikas();

                        adapterVelgR = new AdapterKategoriVelg(VelgActivity.this, listKategoriR);
                        tampilVelgR.setAdapter(adapterVelgR);
                        adapterVelgR.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseModelVelg> call, Throwable t) {
                        Toast.makeText(VelgActivity.this,"Koneksi Gagal",Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModelVelg> getMerekVelg = api.getMerekVelg();
                    getMerekVelg.enqueue(new Callback<ResponseModelVelg>() {
                        @Override
                        public void onResponse(Call<ResponseModelVelg> call, Response<ResponseModelVelg> response) {
                            listKategoriO = response.body().getVelg_originals();
                            listKategoriR = response.body().getVelg_replikas();

                            adapterVelgO = new AdapterKategoriVelg(VelgActivity.this, listKategoriO);
                            tampilVelgO.setAdapter(adapterVelgO);
                            adapterVelgO.notifyDataSetChanged();

                            adapterVelgR = new AdapterKategoriVelg(VelgActivity.this, listKategoriR);
                            tampilVelgR.setAdapter(adapterVelgR);
                            adapterVelgR.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<ResponseModelVelg> call, Throwable t) {
                            Toast.makeText(VelgActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return true;
            }
        });

        btnTambahReplika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm2();
            }
        });

        btnTambahOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
            }
        });

        ivBack = findViewById(R.id.tvBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(VelgActivity.this, MainActivity.class);
                startActivity(pindah);
            }
        });


        tampilVelgO = findViewById(R.id.rvVelgOriginal);
        layouVelgO = new LinearLayoutManager(VelgActivity.this, RecyclerView.VERTICAL, false);
        tampilVelgO.setLayoutManager(layouVelgO);

        tampilVelgR = findViewById(R.id.rvVelgReplika);
        layouVelgR = new LinearLayoutManager(VelgActivity.this, RecyclerView.VERTICAL, false);
        tampilVelgR.setLayoutManager(layouVelgR);

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponseModelVelg> getMerekVelg = api.getMerekVelg();
        getMerekVelg.enqueue(new Callback<ResponseModelVelg>() {
            @Override
            public void onResponse(Call<ResponseModelVelg> call, Response<ResponseModelVelg> response) {
                listKategoriO = response.body().getVelg_originals();
                listKategoriR = response.body().getVelg_replikas();

                adapterVelgO = new AdapterKategoriVelg(VelgActivity.this, listKategoriO);
                tampilVelgO.setAdapter(adapterVelgO);
                adapterVelgO.notifyDataSetChanged();

                adapterVelgR = new AdapterKategoriVelg(VelgActivity.this, listKategoriR);
                tampilVelgR.setAdapter(adapterVelgR);
                adapterVelgR.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModelVelg> call, Throwable t) {
                Toast.makeText(VelgActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

//        Call<ResponseModelVelg> getMerek = api.getMerekVelg();
//        getMerek.enqueue(new Callback<ResponseModelVelg>() {
//            @Override
//            public void onResponse(Call<ResponseModelVelg> call, Response<ResponseModelVelg> response) {
//                listKategoriR = response.body().getVelg_replikas();
//
//                adapterVelgR = new AdapterKategoriVelg(VelgActivity.this, listKategoriR);
//                tampilVelgR.setAdapter(adapterVelgR);
//                adapterVelgR.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModelVelg> call, Throwable t) {
//                Toast.makeText(VelgActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(VelgActivity.this);
        inflater = LayoutInflater.from(VelgActivity.this);
        dialogView = inflater.inflate(R.layout.modal_velg, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        EditText komplain = dialogView.findViewById(R.id.komplain);

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = komplain.getText().toString();

                if (!komplain.getText().toString().isEmpty()){
                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> insertKategori = api.insertMerekOriginal(nama);

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(VelgActivity.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    insertKategori.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(VelgActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = getIntent();
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            progressDialog.dismiss();
                            startActivity(pindah);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(VelgActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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

    private void DialogForm2() {
        dialog = new AlertDialog.Builder(VelgActivity.this);
        inflater = LayoutInflater.from(VelgActivity.this);
        dialogView = inflater.inflate(R.layout.modal_velg, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        EditText komplain = dialogView.findViewById(R.id.komplain);

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = komplain.getText().toString();

                if (!komplain.getText().toString().isEmpty()){
                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> insertKategori = api.insertMerekReplika(nama);

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(VelgActivity.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    insertKategori.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(VelgActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = getIntent();
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            progressDialog.dismiss();
                            startActivity(pindah);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(VelgActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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