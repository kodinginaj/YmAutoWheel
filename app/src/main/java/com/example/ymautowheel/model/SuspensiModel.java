package com.example.ymautowheel.model;

public class SuspensiModel {
    String id,kategori_suspensi_id,merek_suspensi_id,spesifikasi,jumlah,harga,created_at;

    public SuspensiModel(String id, String kategori_suspensi_id, String merek_suspensi_id, String spesifikasi, String jumlah, String harga, String created_at) {
        this.id = id;
        this.kategori_suspensi_id = kategori_suspensi_id;
        this.merek_suspensi_id = merek_suspensi_id;
        this.spesifikasi = spesifikasi;
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

    public String getKategori_suspensi_id() {
        return kategori_suspensi_id;
    }

    public void setKategori_suspensi_id(String kategori_suspensi_id) {
        this.kategori_suspensi_id = kategori_suspensi_id;
    }

    public String getMerek_suspensi_id() {
        return merek_suspensi_id;
    }

    public void setMerek_suspensi_id(String merek_suspensi_id) {
        this.merek_suspensi_id = merek_suspensi_id;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
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
