package com.example.ymautowheel.model;

public class TipeBanModel {
    String id,merek_ban_id,nama,created_at;

    public TipeBanModel(String id, String merek_ban_id, String nama, String created_at) {
        this.id = id;
        this.merek_ban_id = merek_ban_id;
        this.nama = nama;
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
