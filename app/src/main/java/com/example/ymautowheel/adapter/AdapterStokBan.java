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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.Informasi_stokban;
import com.example.ymautowheel.R;
import com.example.ymautowheel.api.ApiRequest;
import com.example.ymautowheel.api.Retroserver;
import com.example.ymautowheel.model.BanModel;
import com.example.ymautowheel.model.ResponseModel;
import com.example.ymautowheel.model.TipeBanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterStokBan extends RecyclerView.Adapter<AdapterStokBan.TampungData> {

    private Context ctx;
    private List<BanModel> listBan;

    public AdapterStokBan(Context ctx, List<BanModel> listBan) {
        this.ctx = ctx;
        this.listBan = listBan;
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

        holder.etExpired.setText(dataBan.getKadaluarsa());
        holder.etJumlah.setText(dataBan.getJumlah());
        holder.etUkuran.setText(dataBan.getUkuran());
        holder.etHarga.setText(dataBan.getHarga());

        holder.dataBan = dataBan;

    }

    @Override
    public int getItemCount() {
        return listBan.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        TextView etExpired,etUkuran,etJumlah,etHarga;
        ImageView btnDelete;
        BanModel dataBan;

        private TampungData(View v) {
            super(v);

            etExpired = v.findViewById(R.id.etExpired);
            etUkuran = v.findViewById(R.id.etUkuran);
            etJumlah = v.findViewById(R.id.etJumlah);
            etHarga = v.findViewById(R.id.etHarga);

            btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm();
                }
            });
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
                    Call<ResponseModel> hapusBan = api.deleteBan(id);
                    hapusBan.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(ctx, Informasi_stokban.class);
                            pindah.putExtra("idMerek",dataBan.getMerek_ban_id());
                            pindah.putExtra("idTipe",dataBan.getTipe_ban_id());
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
