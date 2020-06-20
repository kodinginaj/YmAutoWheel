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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.Informasi_stokban;
import com.example.ymautowheel.Informasi_stoksuspensi;
import com.example.ymautowheel.R;
import com.example.ymautowheel.SuspensiActivity;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.MerekSuspensiModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.TipeBanModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMerekSuspensi extends RecyclerView.Adapter<AdapterMerekSuspensi.TampungData> {

    private Context ctx;
    private List<MerekSuspensiModel> listMerek;

    public AdapterMerekSuspensi(Context ctx, List<MerekSuspensiModel> listMerek) {
        this.ctx = ctx;
        this.listMerek = listMerek;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.merek_suspensi, parent, false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        MerekSuspensiModel dataMerek = listMerek.get(position);

        holder.merek_suspensi.setText(dataMerek.getNama());
        holder.dataMerek = dataMerek;
    }

    @Override
    public int getItemCount() {
        return listMerek.size();
    }

    class TampungData extends RecyclerView.ViewHolder {

        private TextView merek_suspensi;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        TextView txtdia;
        EditText etTipe;

        ImageView btnUbahMerekSuspensi, btnDelete;

        MerekSuspensiModel dataMerek;

        private TampungData(View v) {
            super(v);

            merek_suspensi = v.findViewById(R.id.nama_merek_suspensi);
            btnUbahMerekSuspensi = v.findViewById(R.id.btnUbahMerekSuspensi);

            btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm3();
                }
            });

            btnUbahMerekSuspensi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm();
                }
            });

            Session session = new Session(ctx);
            if(session.getRole().equals("0")){
                btnDelete.setVisibility(View.GONE);
                btnUbahMerekSuspensi.setVisibility(View.GONE);
            }


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, Informasi_stoksuspensi.class);
                    pindah.putExtra("kategoriId",dataMerek.getKategori_suspensi_id());
                    pindah.putExtra("merekId",dataMerek.getId());
                    pindah.putExtra("nama",dataMerek.getNama());
                    ctx.startActivity(pindah);
                }
            });

        }
        private void DialogForm() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_tipeban, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            txtdia = dialogView.findViewById(R.id.txt_dia);
            etTipe = dialogView.findViewById(R.id.etTipeBan);

            txtdia.setText("Ubah Merek Suspensi");
            etTipe.setText(dataMerek.getNama());

            EditText komplain = dialogView.findViewById(R.id.etTipeBan);
            komplain.setText(dataMerek.getNama());

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
                        Call<ResponseModel> updateMerekSuspensi = api.updateMerekSuspensi(dataMerek.getId(),nama);
                        updateMerekSuspensi.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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

        private void DialogForm3() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(false);
            builder.setMessage("Apakah anda yakin untuk menghapus merek?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = dataMerek.getId();

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(ctx);
                    progressDialog.setMessage("Mohon tunggu...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> deleteMerekSuspensi = api.deleteMerekSuspensi(id);
                    deleteMerekSuspensi.enqueue(new Callback<ResponseModel>() {
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
