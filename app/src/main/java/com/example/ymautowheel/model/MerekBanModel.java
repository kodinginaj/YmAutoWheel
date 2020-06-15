package com.example.ymautowheel.model;

import java.util.List;

public class MerekBanModel {
    String id,nama,created_at;
    List<TipeBanModel> tipe_bans;

    public MerekBanModel(String id, String nama, String created_at,  List<TipeBanModel> tipe_bans) {
        this.id = id;
        this.nama = nama;
        this.created_at = created_at;
        this.tipe_bans = tipe_bans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public  List<TipeBanModel> getTipe_bans() {
        return tipe_bans;
    }

    public void setTipe_bans( List<TipeBanModel> tipe_bans) {
        this.tipe_bans = tipe_bans;
    }
}
