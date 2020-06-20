package com.example.ymautowheel.model;

import java.util.List;

public class ResponseModelVelg {
    String status, message;
    List<KategoriVelgModel> velg_originals;
    List<KategoriVelgModel> velg_replikas;
    List<VelgModel> velgs;

    public ResponseModelVelg(String status, String message, List<KategoriVelgModel> velg_originals, List<KategoriVelgModel> velg_replikas, List<VelgModel> velgs) {
        this.status = status;
        this.message = message;
        this.velg_originals = velg_originals;
        this.velg_replikas = velg_replikas;
        this.velgs = velgs;
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

    public List<KategoriVelgModel> getVelg_originals() {
        return velg_originals;
    }

    public void setVelg_originals(List<KategoriVelgModel> velg_originals) {
        this.velg_originals = velg_originals;
    }

    public List<KategoriVelgModel> getVelg_replikas() {
        return velg_replikas;
    }

    public void setVelg_replikas(List<KategoriVelgModel> velg_replikas) {
        this.velg_replikas = velg_replikas;
    }

    public List<VelgModel> getVelgs() {
        return velgs;
    }

    public void setVelgs(List<VelgModel> velgs) {
        this.velgs = velgs;
    }
}
