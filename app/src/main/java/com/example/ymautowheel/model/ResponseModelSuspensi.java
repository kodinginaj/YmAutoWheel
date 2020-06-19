package com.example.ymautowheel.model;

import java.util.List;

public class ResponseModelSuspensi {
    String status, message;
    List<KategoriSuspensiModel> kategori_suspensis;
    List<SuspensiModel> suspensis;

    public ResponseModelSuspensi(String status, String message, List<KategoriSuspensiModel> kategori_suspensis, List<SuspensiModel> suspensis) {
        this.status = status;
        this.message = message;
        this.kategori_suspensis = kategori_suspensis;
        this.suspensis = suspensis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KategoriSuspensiModel> getKategori_suspensis() {
        return kategori_suspensis;
    }

    public void setKategori_suspensis(List<KategoriSuspensiModel> kategori_suspensis) {
        this.kategori_suspensis = kategori_suspensis;
    }

    public List<SuspensiModel> getSuspensis() {
        return suspensis;
    }

    public void setSuspensis(List<SuspensiModel> suspensis) {
        this.suspensis = suspensis;
    }
}