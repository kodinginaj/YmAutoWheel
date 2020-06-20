package com.example.ymautowheel.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.Informasi_stokban;
import com.example.ymautowheel.R;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.TipeBanModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTipeBan extends RecyclerView.Adapter<AdapterTipeBan.TampungData> {

    private Context ctx;
    private List<TipeBanModel> listTipe;

    public AdapterTipeBan(Context ctx, List<TipeBanModel> listTipe) {
        this.ctx = ctx;
        this.listTipe = listTipe;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.tipeban_item, parent, false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        TipeBanModel dataTipe = listTipe.get(position);

        holder.tv_tipeban.setText(dataTipe.getNama());
        holder.dataTipe = dataTipe;
    }

    @Override
    public int getItemCount() {
        return listTipe.size();
    }

    class TampungData extends RecyclerView.ViewHolder {

        private TextView tv_tipeban;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        TextView txtdia;
        EditText etTipe;

        ImageView btnUbahTipe, btnDelete;

        TipeBanModel dataTipe;

        private TampungData(View v) {
            super(v);

            tv_tipeban = v.findViewById(R.id.tv_tipeban);
            btnUbahTipe = v.findViewById(R.id.btnUbahTipe);

            btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm3();
                }
            });

            btnUbahTipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm();
                }
            });

            Session session = new Session(ctx);
            if(session.getRole().equals("0")){
                btnDelete.setVisibility(View.GONE);
                btnUbahTipe.setVisibility(View.GONE);
            }


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, Informasi_stokban.class);
                    pindah.putExtra("idTipe",dataTipe.getId());
                    pindah.putExtra("idMerek",dataTipe.getMerek_ban_id());
                    pindah.putExtra("nama",dataTipe.getNama());
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

            txtdia.setText("Ubah Tipe Ban");
            etTipe.setText(dataTipe.getNama());

            EditText komplain = dialogView.findViewById(R.id.etTipeBan);
            komplain.setText(dataTipe.getNama());

            dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nama = komplain.getText().toString();

                    if (!komplain.getText().toString().isEmpty()){
                        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                        Call<ResponseModel> ubahTipe = api.updateTipe(dataTipe.getId(),nama);

                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(ctx);
                        progressDialog.setMessage("Mohon tunggu....");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        ubahTipe.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent pindah = new Intent(ctx,BanActivity.class);
                                pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                progressDialog.dismiss();
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
            builder.setMessage("Apakah anda yakin untuk menghapus tipe?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = dataTipe.getId();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> hapustipe = api.deleteTipe(id);

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(ctx);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    hapustipe.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(ctx,BanActivity.class);
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            progressDialog.dismiss();
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
