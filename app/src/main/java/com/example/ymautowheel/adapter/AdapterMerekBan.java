package com.example.ymautowheel.adapter;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.R;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.TipeBanModel;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMerekBan extends RecyclerView.Adapter<AdapterMerekBan.TampungData>{

    private Context ctx;
    private List<MerekBanModel> listMerek;

    public AdapterMerekBan(Context ctx, List<MerekBanModel> listMerek) {
        this.ctx = ctx;
        this.listMerek = listMerek;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.merekban_item,parent,false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        MerekBanModel datamerek = listMerek.get(position);
        List<TipeBanModel> listTipeBan = datamerek.getTipe_bans();

        holder.adapterTipeBan = new AdapterTipeBan(ctx, listTipeBan);
        holder.tampilTipeBan.setAdapter(holder.adapterTipeBan);
        holder.adapterTipeBan.notifyDataSetChanged();

        holder.tv_merekban.setText(datamerek.getNama());
        holder.merekBan = datamerek;

    }

    @Override
    public int getItemCount() {
        return listMerek.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        private TextView tv_merekban;

        private RecyclerView tampilTipeBan;
        private RecyclerView.LayoutManager layoutTipeBan;
        private RecyclerView.Adapter adapterTipeBan;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        TextView txtdia;

        EditText etTipe;
        MerekBanModel merekBan;
        ImageView btnTambahTipe, btnUbahMerek, btnDelete;

        private TampungData(View v) {
            super(v);

            tv_merekban = v.findViewById(R.id.tv_merekban);
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


            tampilTipeBan = v.findViewById(R.id.rvTipeBan);
            layoutTipeBan = new LinearLayoutManager(ctx, RecyclerView.VERTICAL, false);
            tampilTipeBan.setLayoutManager(layoutTipeBan);
        }

        private void DialogForm() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_tipeban, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            EditText etTipe = dialogView.findViewById(R.id.etTipeBan);

            dialog.setPositiveButton("TAMBAH", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String tipe = etTipe.getText().toString();

                    if (!etTipe.getText().toString().isEmpty()){
                        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                        Call<ResponseModel> tambahTipe = api.insertTipe(merekBan.getId(),tipe);
                        tambahTipe.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent pindah = new Intent(ctx,BanActivity.class);
                                ctx.startActivity(pindah);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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

            txtdia.setText("Ubah Merek Ban");
            etTipe.setText(merekBan.getNama());

            EditText komplain = dialogView.findViewById(R.id.etTipeBan);

            dialog.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nama = komplain.getText().toString();

                    if (!komplain.getText().toString().isEmpty()){
                        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                        Call<ResponseModel> ubahMerek = api.updateMerek(merekBan.getId(),nama);
                        ubahMerek.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent pindah = new Intent(ctx,BanActivity.class);
                                ctx.startActivity(pindah);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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
                    String id = merekBan.getId();

                   ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                   Call<ResponseModel> hapusMerek = api.deleteMerek(id);
                   hapusMerek.enqueue(new Callback<ResponseModel>() {
                       @Override
                       public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                           Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                           Intent pindah = new Intent(ctx,BanActivity.class);
                           ctx.startActivity(pindah);
                       }

                       @Override
                       public void onFailure(Call<ResponseModel> call, Throwable t) {
                           Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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
