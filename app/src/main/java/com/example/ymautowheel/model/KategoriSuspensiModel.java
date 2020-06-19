package com.example.ymautowheel.model;

import java.util.List;

public class KategoriSuspensiModel {
    String id,nama,created_at;
    List<MerekSuspensiModel> merek_suspensis;

    public KategoriSuspensiModel(String id, String nama, String created_at, List<MerekSuspensiModel> merek_suspensis) {
        this.id = id;
        this.nama = nama;
        this.created_at = created_at;
        this.merek_suspensis = merek_suspensis;
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

    public List<MerekSuspensiModel> getMerek_suspensis() {
        return merek_suspensis;
    }

    public void setMerek_suspensis(List<MerekSuspensiModel> merek_suspensis) {
        this.merek_suspensis = merek_suspensis;
    }
}
