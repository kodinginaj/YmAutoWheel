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

import com.example.ymautowheel.Informasi_stoksuspensi;
import com.example.ymautowheel.Informasi_stokvelg;
import com.example.ymautowheel.R;
import com.example.ymautowheel.Ubah_stocksuspensi;
import com.example.ymautowheel.Ubah_stockvelg;
import com.example.ymautowheel.VelgActivity;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.SuspensiModel;
import com.example.ymautowheel.model.VelgModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterVelg extends RecyclerView.Adapter<AdapterVelg.TampungData> {

    private Context ctx;
    private List<VelgModel> listVelg;
    String nama;

    public AdapterVelg(Context ctx, List<VelgModel> listVelg, String nama) {
        this.ctx = ctx;
        this.listVelg = listVelg;
        this.nama = nama;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.stokvelg_item, parent, false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        VelgModel dataVelg = listVelg.get(position);

        holder.etSpek.setText(dataVelg.getSpesifikasi());
        holder.etJumlah.setText(dataVelg.getJumlah());
        holder.etHarga.setText(dataVelg.getHarga());

        holder.dataVelg = dataVelg;

    }

    @Override
    public int getItemCount() {
        return listVelg.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        TextView etTipe, etSpek, etJumlah, etHarga;
        ImageView btnDelete, btnEdit, btnUbah;
        VelgModel dataVelg;
        EditText etUbah;

        RadioButton cbTambah, cbKurang;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        private TampungData(View v) {
            super(v);

//            etTipe = v.findViewById(R.id.etTipe);
            etJumlah = v.findViewById(R.id.etJumlah);
            etHarga = v.findViewById(R.id.etHarga);
            etSpek = v.findViewById(R.id.etSpek);

            btnEdit = v.findViewById(R.id.btnEdit);

            btnUbah = v.findViewById(R.id.btnUbah);
            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, Ubah_stockvelg.class);
                    pindah.putExtra("Id", dataVelg.getId());
                    pindah.putExtra("Spesifikasi", dataVelg.getSpesifikasi());
                    pindah.putExtra("Harga", dataVelg.getHarga());
                    pindah.putExtra("merekId", dataVelg.getMerek_velg_id());
                    pindah.putExtra("nama",nama);
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

            Session session = new Session(ctx);
            if(session.getRole().equals("0")){
                btnDelete.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                btnUbah.setVisibility(View.GONE);
            }

        }

        private void DialogForm2() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_stockvelg, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            etUbah = dialogView.findViewById(R.id.stokBaru);
//            etUbah.setText(dataBan.getJumlah());

            cbTambah = dialogView.findViewById(R.id.cbTambah);
            cbKurang = dialogView.findViewById(R.id.cbKurang);

            dialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String id = dataVelg.getId();
                    String jumlahTotal = dataVelg.getJumlah();
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
                            Call<ResponseModel> tambahStockVelg = api.tambahStockVelg(id, jumlahTotal, jumlahUbah);
                            tambahStockVelg.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent pindah = new Intent(ctx, Informasi_stokvelg.class);
                                    pindah.putExtra("idKategori", dataVelg.getMerek_velg_id());
                                    pindah.putExtra("nama",nama);
                                    pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    ctx.startActivity(pindah);
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        } else if (cbKurang.isChecked()) {
                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                            Call<ResponseModel> kurangStockVelg = api.kurangStockVelg(id, jumlahTotal, jumlahUbah);
                            kurangStockVelg.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent pindah = new Intent(ctx, Informasi_stokvelg.class);
                                    pindah.putExtra("idKategori", dataVelg.getMerek_velg_id());
                                    pindah.putExtra("nama",nama);
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
            builder.setMessage("Apakah anda yakin untuk menghapus velg?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = dataVelg.getId();

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(ctx);
                    progressDialog.setMessage("Mohon tunggu...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> deleteVelg = api.deleteVelg(id);
                    deleteVelg.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Log.d("ANJ",""+dataVelg.getMerek_velg_id());
                            Intent pindah = new Intent(ctx, Informasi_stokvelg.class);
                            pindah.putExtra("idKategori", dataVelg.getMerek_velg_id());
                            pindah.putExtra("nama",nama);
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