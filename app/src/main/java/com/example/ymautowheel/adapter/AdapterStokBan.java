package com.example.ymautowheel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ymautowheel.R;
import com.example.ymautowheel.model.BanModel;
import com.example.ymautowheel.model.TipeBanModel;

import java.util.List;

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

    }

    @Override
    public int getItemCount() {
        return listBan.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        TextView etExpired,etUkuran,etJumlah,etHarga;

        private TampungData(View v) {
            super(v);

            etExpired = v.findViewById(R.id.etExpired);
            etUkuran = v.findViewById(R.id.etUkuran);
            etJumlah = v.findViewById(R.id.etJumlah);
            etHarga = v.findViewById(R.id.etHarga);
        }
    }

}
