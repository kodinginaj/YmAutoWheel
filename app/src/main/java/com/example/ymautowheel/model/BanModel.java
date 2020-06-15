package com.example.ymautowheel.model;

public class BanModel {
    String id, merek_ban_id, tipe_ban_id, ukuran, kadaluarsa, jumlah, harga, created_at;

    public BanModel(String id, String merek_ban_id, String tipe_ban_id, String ukuran, String kadaluarsa, String jumlah, String harga, String created_at) {
        this.id = id;
        this.merek_ban_id = merek_ban_id;
        this.tipe_ban_id = tipe_ban_id;
        this.ukuran = ukuran;
        this.kadaluarsa = kadaluarsa;
        this.jumlah = jumlah;
        this.harga = harga;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerek_ban_id() {
        return merek_ban_id;
    }

    public void setMerek_ban_id(String merek_ban_id) {
        this.merek_ban_id = merek_ban_id;
    }

    public String getTipe_ban_id() {
        return tipe_ban_id;
    }

    public void setTipe_ban_id(String tipe_ban_id) {
        this.tipe_ban_id = tipe_ban_id;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getKadaluarsa() {
        return kadaluarsa;
    }

    public void setKadaluarsa(String kadaluarsa) {
        this.kadaluarsa = kadaluarsa;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
