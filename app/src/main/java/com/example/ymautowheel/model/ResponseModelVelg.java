package com.example.ymautowheel.model;

import java.util.List;

public class ResponseModelVelg {
    String status,message;
    List<VelgModel> velg_originals;
    List<VelgModel> velg_replikas;

    public ResponseModelVelg(String status, String message, List<VelgModel> velg_originals, List<VelgModel> velg_replikas) {
        this.status = status;
        this.message = message;
        this.velg_originals = velg_originals;
        this.velg_replikas = velg_replikas;
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

    public List<VelgModel> getVelg_originals() {
        return velg_originals;
    }

    public void setVelg_originals(List<VelgModel> velg_originals) {
        this.velg_originals = velg_originals;
    }

    public List<VelgModel> getVelg_replikas() {
        return velg_replikas;
    }

    public void setVelg_replikas(List<VelgModel> velg_replikas) {
        this.velg_replikas = velg_replikas;
    }
}
