package com.example.ymautowheel.model;

public class NotifikasiModel {
    private String id, barang_id, jenis, merek, type, keterangan, stock, created_at;

    public NotifikasiModel(String id, String barang_id, String jenis, String merek, String type, String keterangan, String stock, String created_at) {
        this.id = id;
        this.barang_id = barang_id;
        this.jenis = jenis;
        this.merek = merek;
        this.type = type;
        this.keterangan = keterangan;
        this.stock = stock;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarang_id() {
        return barang_id;
    }

    public void setBarang_id(String barang_id) {
        this.barang_id = barang_id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}


