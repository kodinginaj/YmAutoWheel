package com.example.ymautowheel.model;

public class KategoriVelgModel {
    String id,kategori,nama,created_at;

    public KategoriVelgModel(String id, String kategori, String nama, String created_at) {
        this.id = id;
        this.kategori = kategori;
        this.nama = nama;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
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
}
