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

import com.example.ymautowheel.Informasi_stokban;
import com.example.ymautowheel.Informasi_stokvelg;
import com.example.ymautowheel.R;
import com.example.ymautowheel.SuspensiActivity;
import com.example.ymautowheel.VelgActivity;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.KategoriSuspensiModel;
import com.example.ymautowheel.model.KategoriVelgModel;
import com.example.ymautowheel.model.MerekSuspensiModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.util.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterKategoriVelg extends RecyclerView.Adapter<AdapterKategoriVelg.TampungData> {

    private Context ctx;
    private List<KategoriVelgModel> listKategori;

    public AdapterKategoriVelg(Context ctx, List<KategoriVelgModel> listKategori) {
        this.ctx = ctx;
        this.listKategori = listKategori;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.newmerekvelg_item, parent, false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        KategoriVelgModel dataKategori = listKategori.get(position);

        holder.tvNama.setText(dataKategori.getNama());
        holder.kategoriVelg = dataKategori;
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        private TextView tvNama;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        TextView txtdia;

        EditText etTipe;
        KategoriVelgModel kategoriVelg;
        ImageView btnUbah, btnDelete;

        private TampungData(View v) {
            super(v);

            tvNama = v.findViewById(R.id.tvNama);
            btnDelete = v.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm3();
                }
            });


            btnUbah = v.findViewById(R.id.btnUbah);
            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm2();
                }
            });

            Session session = new Session(ctx);
            if(session.getRole().equals("0")){
                btnDelete.setVisibility(View.GONE);
                btnUbah.setVisibility(View.GONE);
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, Informasi_stokvelg.class);
                    pindah.putExtra("idMerek",kategoriVelg.getId());
                    pindah.putExtra("idKategori",kategoriVelg.getId());
                    pindah.putExtra("nama",kategoriVelg.getNama());

                    ctx.startActivity(pindah);
                }
            });
        }

        private void DialogForm2() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_velg, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            txtdia = dialogView.findViewById(R.id.tvJudul);
            etTipe = dialogView.findViewById(R.id.komplain);

            txtdia.setText("Ubah Kategori Velg");
            etTipe.setText(kategoriVelg.getNama());

            EditText komplain = dialogView.findViewById(R.id.komplain);

            dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nama = komplain.getText().toString();

                    if (!komplain.getText().toString().isEmpty()) {
                        final ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(ctx);
                        progressDialog.setMessage("Mohon tunggu...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                        Call<ResponseModel> updateMerekVelg = api.updateMerekVelg(kategoriVelg.getId(), nama);
                        updateMerekVelg.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent pindah = new Intent(ctx, VelgActivity.class);
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

                    String id = kategoriVelg.getId();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> deleteMerkVelg = api.deleteMerkVelg(id);
                    deleteMerkVelg.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            progressDialog.dismiss();
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(ctx, VelgActivity.class);
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
