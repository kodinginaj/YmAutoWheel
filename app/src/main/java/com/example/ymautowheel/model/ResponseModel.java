package com.example.ymautowheel.model;

import java.util.List;

public class ResponseModel {
    String status, message;
    UserModel user;

    public ResponseModel(String status, String message, UserModel user) {
        this.status = status;
        this.message = message;
        this.user = user;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
