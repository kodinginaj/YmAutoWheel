package com.example.ymautowheel.model;

import java.util.List;

public class ResponseModelNotifikasi {
    private String status, message;
    private List<NotifikasiModel> notifications;

    public ResponseModelNotifikasi() {
    }

    public ResponseModelNotifikasi(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModelNotifikasi(String status, String message, List<NotifikasiModel> notifications) {
        this.status = status;
        this.message = message;
        this.notifications = notifications;
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

    public List<NotifikasiModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotifikasiModel> notifications) {
        this.notifications = notifications;
    }
}
