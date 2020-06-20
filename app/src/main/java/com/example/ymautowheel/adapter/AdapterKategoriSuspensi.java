package com.example.ymautowheel.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.R;
import com.example.ymautowheel.SuspensiActivity;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.KategoriSuspensiModel;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.MerekSuspensiModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.TipeBanModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKategoriSuspensi extends RecyclerView.Adapter<AdapterKategoriSuspensi.TampungData>{

    private Context ctx;
    private List<KategoriSuspensiModel> listKategori;

    public AdapterKategoriSuspensi(Context ctx, List<KategoriSuspensiModel> listKategori) {
        this.ctx = ctx;
        this.listKategori = listKategori;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipesuspensi_item,parent,false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        KategoriSuspensiModel dataKategori = listKategori.get(position);
        List<MerekSuspensiModel> listMerek = dataKategori.getMerek_suspensis();

        holder.adapterMerekSuspensi = new AdapterMerekSuspensi(ctx, listMerek);
        holder.tampilMerekSuspensi.setAdapter(holder.adapterMerekSuspensi);
        holder.adapterMerekSuspensi.notifyDataSetChanged();

        holder.tv_tipesuspensi.setText(dataKategori.getNama());
        holder.kategoriSuspensi = dataKategori;

    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        private TextView tv_tipesuspensi;

        private RecyclerView tampilMerekSuspensi;
        private RecyclerView.LayoutManager layoutMerekSuspensi;
        private RecyclerView.Adapter adapterMerekSuspensi;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        TextView txtdia;

        EditText etTipe;
        KategoriSuspensiModel kategoriSuspensi;
        ImageView btnTambahTipe, btnUbahMerek, btnDelete;

        private TampungData(View v) {
            super(v);

            tv_tipesuspensi = v.findViewById(R.id.tv_tipesuspensi);
            btnDelete = v.findViewById(R.id.btnDelete);
            btnTambahTipe = v.findViewById(R.id.btnTambahTipe);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm3();
                }
            });

            btnTambahTipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm();
                }
            });

            btnUbahMerek = v.findViewById(R.id.btnUbahMerek);
            btnUbahMerek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm2();
                }
            });

            Session session = new Session(ctx);
            if(session.getRole().equals("0")){
                btnDelete.setVisibility(View.GONE);
                btnTambahTipe.setVisibility(View.GONE);
                btnUbahMerek.setVisibility(View.GONE);
            }



            tampilMerekSuspensi = v.findViewById(R.id.merekSuspensi);
            layoutMerekSuspensi = new LinearLayoutManager(ctx, RecyclerView.VERTICAL, false);
            tampilMerekSuspensi.setLayoutManager(layoutMerekSuspensi);
        }

        private void DialogForm() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_suspensi, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            txtdia = dialogView.findViewById(R.id.tvJudul);
            txtdia.setText("Masukan Merek Suspensi");

            EditText etTipe = dialogView.findViewById(R.id.stokBaru);

            dialog.setPositiveButton("TAMBAH", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String tipe = etTipe.getText().toString();

                    if (!etTipe.getText().toString().isEmpty()){
                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(ctx);
                        progressDialog.setMessage("Mohon tunggu...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                        Call<ResponseModel> tambahMerekSuspensi = api.insertMerekSuspensi(kategoriSuspensi.getId(),tipe);
                        tambahMerekSuspensi.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                progressDialog.dismiss();
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent pindah = new Intent(ctx, SuspensiActivity.class);
                                pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ctx.startActivity(pindah);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_merekban, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            txtdia = dialogView.findViewById(R.id.tvJudul);
            etTipe = dialogView.findViewById(R.id.etTipeBan);

            txtdia.setText("Ubah Kategori Suspensi");
            etTipe.setText(kategoriSuspensi.getNama());

            EditText komplain = dialogView.findViewById(R.id.etTipeBan);

            dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nama = komplain.getText().toString();

                    if (!komplain.getText().toString().isEmpty()){
                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(ctx);
                        progressDialog.setMessage("Mohon tunggu...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                        Call<ResponseModel> updateMerekSuspensi = api.updateKategoriSuspensi(kategoriSuspensi.getId(),nama);
                        updateMerekSuspensi.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent pindah = new Intent(ctx,SuspensiActivity.class);
                                pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ctx.startActivity(pindah);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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
        private void DialogForm3() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(false);
            builder.setMessage("Apakah anda yakin untuk menghapus kategori?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(ctx);
                    progressDialog.setMessage("Mohon tunggu...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    String id = kategoriSuspensi.getId();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> deleteMerekSuspensi = api.deleteKategoriSuspensi(id);
                    deleteMerekSuspensi.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            progressDialog.dismiss();
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(ctx,SuspensiActivity.class);
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            ctx.startActivity(pindah);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            });
            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}