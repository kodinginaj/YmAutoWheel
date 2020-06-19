package com.example.ymautowheel.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.Informasi_stokban;
import com.example.ymautowheel.R;
import com.example.ymautowheel.Ubah_stockban;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.BanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.TipeBanModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterStokBan extends RecyclerView.Adapter<AdapterStokBan.TampungData> {

    private Context ctx;
    private List<BanModel> listBan;
    private String nama ;

    public AdapterStokBan(Context ctx, List<BanModel> listBan, String nama) {
        this.ctx = ctx;
        this.listBan = listBan;
        this.nama = nama;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.stokban_item, parent, false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        BanModel dataBan = listBan.get(position);

        holder.etExpired.setText("Exp : "+dataBan.getKadaluarsa());
        holder.etJumlah.setText("Stock : "+dataBan.getJumlah());
        holder.etUkuran.setText("UK : "+dataBan.getUkuran());
        holder.etHarga.setText(Rupiah(Double.parseDouble(dataBan.getHarga())));

        holder.dataBan = dataBan;

    }

    @Override
    public int getItemCount() {
        return listBan.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        TextView etExpired,etUkuran,etJumlah,etHarga;
        ImageView btnDelete, btnEdit, btnUbahBan;
        BanModel dataBan;
        EditText etUbah;

        RadioButton cbTambah, cbKurang;

        AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        private TampungData(View v) {
            super(v);

            etExpired = v.findViewById(R.id.etExpired);
            etUkuran = v.findViewById(R.id.etUkuran);
            etJumlah = v.findViewById(R.id.etJumlah);
            etHarga = v.findViewById(R.id.etHarga);
            btnEdit = v.findViewById(R.id.btnEdit);

            btnUbahBan = v.findViewById(R.id.btnUbahBan);
            btnUbahBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, Ubah_stockban.class);
                    pindah.putExtra("Id",dataBan.getId());
                    pindah.putExtra("Ukuran",dataBan.getUkuran());
                    pindah.putExtra("Harga",dataBan.getHarga());
                    pindah.putExtra("Expired",dataBan.getKadaluarsa());
                    pindah.putExtra("idMerek", dataBan.getMerek_ban_id());
                    pindah.putExtra("idTipe", dataBan.getTipe_ban_id());
                    pindah.putExtra("nama", nama);
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
            dialogView = inflater.inflate(R.layout.modal_stokban, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            etUbah = dialogView.findViewById(R.id.stokBaru);
//            etUbah.setText(dataBan.getJumlah());

            cbTambah = dialogView.findViewById(R.id.cbTambah);
            cbKurang = dialogView.findViewById(R.id.cbKurang);

            dialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String id = dataBan.getId();
                    String jumlahTotal = dataBan.getJumlah();
                    String jumlahUbah = etUbah.getText().toString();

                    if (!etUbah.getText().toString().isEmpty()) {

                        if (cbTambah.isChecked()) {
                            Log.d("AJ",""+id+jumlahTotal+jumlahUbah);

                            ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                            Call<ResponseModel> tambahBan = api.tambahBan(id, jumlahTotal, jumlahUbah);

                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu....");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            tambahBan.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent pindah = new Intent(ctx, Informasi_stokban.class);
                                    pindah.putExtra("idMerek", dataBan.getMerek_ban_id());
                                    pindah.putExtra("idTipe", dataBan.getTipe_ban_id());
                                    pindah.putExtra("nama", nama);
                                    pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    progressDialog.dismiss();
                                    ctx.startActivity(pindah);
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if(cbKurang.isChecked()){
                            ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                            Call<ResponseModel> kurangBan = api.kurangBan(id, jumlahTotal, jumlahUbah);

                            final ProgressDialog progressDialog;
                            progressDialog = new ProgressDialog(ctx);
                            progressDialog.setMessage("Mohon tunggu....");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            kurangBan.enqueue(new Callback<ResponseModel>() {
                                @Override
                                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                    Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent pindah = new Intent(ctx, Informasi_stokban.class);
                                    pindah.putExtra("idMerek", dataBan.getMerek_ban_id());
                                    pindah.putExtra("idTipe", dataBan.getTipe_ban_id());
                                    pindah.putExtra("nama", nama);
                                    pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    progressDialog.dismiss();
                                    ctx.startActivity(pindah);
                                }

                                @Override
                                public void onFailure(Call<ResponseModel> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
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
            builder.setMessage("Apakah anda yakin untuk menghapus ban?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = dataBan.getId();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> deleteBan = api.deleteBan(id);

                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(ctx);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    deleteBan.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(ctx, Informasi_stokban.class);
                            pindah.putExtra("idMerek",dataBan.getMerek_ban_id());
                            pindah.putExtra("idTipe",dataBan.getTipe_ban_id());
                            pindah.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            progressDialog.dismiss();
                            ctx.startActivity(pindah);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progressDialog.dismiss();
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

    public String Rupiah(double rupiah){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        String rp = kursIndonesia.format(rupiah);

        return rp;
    }
}
