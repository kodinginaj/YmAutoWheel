package com.example.ymautowheel.model;

public class MerekSuspensiModel {
    String id,kategori_suspensi_id,nama,created_at;

    public MerekSuspensiModel(String id, String kategori_suspensi_id, String nama, String created_at) {
        this.id = id;
        this.kategori_suspensi_id = kategori_suspensi_id;
        this.nama = nama;
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
