package com.example.ymautowheel.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ymautowheel.BanActivity;
import com.example.ymautowheel.R;
import com.example.ymautowheel.model.MerekBanModel;
import com.example.ymautowheel.model.NotifikasiModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterNotif extends RecyclerView.Adapter<AdapterNotif.TampungData> {

    private Context ctx;
    private List<NotifikasiModel> mList;

    public AdapterNotif(Context ctx, List<NotifikasiModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public AdapterNotif.TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifikasiban_item,parent,false);
        AdapterNotif.TampungData tampungData = new AdapterNotif.TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {
        NotifikasiModel notifikasiModel = mList.get(position);
        int jumlah = Integer.parseInt(notifikasiModel.getStock());
        if (jumlah <= 4){
            holder.ivFoto.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_warning));
        }else{
            holder.ivFoto.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_plusnotif));
        }
        holder.tJudul.setText(notifikasiModel.getJenis());
        holder.tKeterangan.setText(notifikasiModel.getJenis()+"/"+notifikasiModel.getMerek()+"/"+notifikasiModel.getKeterangan());
        holder.tStock.setText(notifikasiModel.getStock()+" Stock");


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TampungData extends RecyclerView.ViewHolder {

        private ImageView ivFoto;
        private TextView tJudul, tKeterangan, tStock;

        public TampungData(@NonNull View itemView) {
            super(itemView);

            ivFoto = itemView.findViewById(R.id.ivLogoFoto);
            tJudul = itemView.findViewById(R.id.tv_judul);
            tKeterangan = itemView.findViewById(R.id.tv_keterangan);
            tStock = itemView.findViewById(R.id.tv_stock);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotifikasiModel notifikasiModel = mList.get(getAdapterPosition());

                    if (notifikasiModel.getJenis().equals("Ban")){
                        Intent pindah = new Intent(ctx, BanActivity.class);
                        ctx.startActivity(pindah);
                    }

                    if(notifikasiModel.getJenis().equals("Suspensi")){

                    }

                    if(notifikasiModel.getJenis().equals("Velg")){

                    }



                }
            });



        }
    }
}
