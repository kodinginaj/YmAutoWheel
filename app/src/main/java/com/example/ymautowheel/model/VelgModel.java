package com.example.ymautowheel.model;

public class VelgModel {
    String id, kategori_id, merek_velg_id, spesifikasi, jumlah, harga, created_at;

    public VelgModel(String id, String kategori_id, String merek_velg_id, String spesifikasi, String jumlah, String harga, String created_at) {
        this.id = id;
        this.kategori_id = kategori_id;
        this.merek_velg_id = merek_velg_id;
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

    public String getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(String kategori_id) {
        this.kategori_id = kategori_id;
    }

    public String getMerek_velg_id() {
        return merek_velg_id;
    }

    public void setMerek_velg_id(String merek_velg_id) {
        this.merek_velg_id = merek_velg_id;
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
