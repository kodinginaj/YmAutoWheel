package com.example.ymautowheel.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.Informasi_stokban;
import com.example.ymautowheel.Informasi_stoksuspensi;
import com.example.ymautowheel.R;
import com.example.ymautowheel.Ubah_stockban;
import com.example.ymautowheel.Ubah_stocksuspensi;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.SuspensiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSuspensi extends RecyclerView.Adapter<AdapterSuspensi.TampungData> {

    private Context ctx;
    private List<SuspensiModel> listSuspensi;

    public AdapterSuspensi(Context ctx, List<SuspensiModel> listSuspensi) {
        this.ctx = ctx;
        this.listSuspensi = listSuspensi;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.stoksuspensi_item, parent, false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        SuspensiModel dataSuspensi = listSuspensi.get(position);

        holder.etSpesifikasi.setText(dataSuspensi.getSpesifikasi());
        holder.etJumlah.setText(dataSuspensi.getJumlah());
        holder.etHarga.setText(dataSuspensi.getHarga());

        holder.dataSuspensi = dataSuspensi;

    }

    @Override
    public int getItemCount() {
        return listSuspensi.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        TextView etSpesifikasi,etJumlah,etHarga;
        ImageView btnDelete, btnEdit, btnUbahBan;
        SuspensiModel dataSuspensi;
        EditText etUbah;

        RadioButton cbTambah, cbKurang;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        private TampungData(View v) {
            super(v);

            etSpesifikasi = v.findViewById(R.id.etSpesifikasi);
            etJumlah = v.findViewById(R.id.etJumlah);
            etHarga = v.findViewById(R.id.etHarga);
            btnEdit = v.findViewById(R.id.btnEdit);

            btnUbahBan = v.findViewById(R.id.btnUbahSuspensi);
            btnUbahBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, Ubah_stocksuspensi.class);
                    pindah.putExtra("Id",dataSuspensi.getId());
                    pindah.putExtra("Spesifikasi",dataSuspensi.getSpesifikasi());
                    pindah.putExtra("Harga",dataSuspensi.getHarga());
                    pindah.putExtra("MerekId", dataSuspensi.getMerek_suspensi_id());
                    pindah.putExtra("KategoriId", dataSuspensi.getKategori_suspensi_id());
                    ctx.startActivity(pindah);
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm2();
                }
            });

            btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm();
                }
            });
        }

        private void DialogForm2() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.model_stocksuspensi, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            etUbah = dialogView.findViewById(R.id.stokBaru);
//            etUbah.setText(dataBan.getJumlah());

            cbTambah = dialogView.findViewById(R.id.cbTambah);
            cbKurang = dialogView.findViewById(R.id.cbKurang);

            dialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String id = dataSuspensi.getId();
                    String jumlahTotal = dataSuspensi.getJumlah();
                    String jumlahUbah = etUbah.getText().toString();

                    if (!etUbah.getText().toString().isEmpty()) {

                        if (cbTambah.isChecked()) {

                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                            Call<ResponseModel> tambahSuspensi = api.tambahSuspensi(id, jumlahTotal, jumlahUbah);
                            tambahSuspensi.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent pindah = new Intent(ctx, Informasi_stoksuspensi.class);
                                    pindah.putExtra("kategoriId", dataSuspensi.getKategori_suspensi_id());
                                    pindah.putExtra("merekId", dataSuspensi.getMerek_suspensi_id());

                                    pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    ctx.startActivity(pindah);
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }else if(cbKurang.isChecked()){
                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                            Call<ResponseModel> kurangSuspensi = api.kurangSuspensi(id, jumlahTotal, jumlahUbah);
                            kurangSuspensi.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent pindah = new Intent(ctx, Informasi_stoksuspensi.class);
                                    pindah.putExtra("kategoriId", dataSuspensi.getKategori_suspensi_id());
                                    pindah.putExtra("merekId", dataSuspensi.getMerek_suspensi_id());
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

        private void DialogForm() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(false);
            builder.setMessage("Apakah anda yakin untuk menghapus suspensi?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = dataSuspensi.getId();

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(ctx);
                    progressDialog.setMessage("Mohon tunggu...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> deleteSuspensi = api.deleteSuspensi(id);
                    deleteSuspensi.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent pindah = new Intent(ctx, Informasi_stoksuspensi.class);
                            pindah.putExtra("kategoriId", dataSuspensi.getKategori_suspensi_id());
                            pindah.putExtra("merekId", dataSuspensi.getId());
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
