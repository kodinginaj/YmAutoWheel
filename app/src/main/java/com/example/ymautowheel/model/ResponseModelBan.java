package com.example.ymautowheel.model;

import java.util.List;

public class ResponseModelBan {
    String status,message;
    List<MerekBanModel> merek_bans;
    List<BanModel> bans;

    public ResponseModelBan(String status, String message, List<MerekBanModel> merek_bans, List<BanModel> bans) {
        this.status = status;
        this.message = message;
        this.merek_bans = merek_bans;
        this.bans = bans;
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

    public List<MerekBanModel> getMerek_bans() {
        return merek_bans;
    }

    public void setMerek_bans(List<MerekBanModel> merek_bans) {
        this.merek_bans = merek_bans;
    }

    public List<BanModel> getBans() {
        return bans;
    }

    public void setBans(List<BanModel> bans) {
        this.bans = bans;
    }
}
