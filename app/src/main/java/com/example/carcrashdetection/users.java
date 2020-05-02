package com.example.carcrashdetection;

public class users {
    private String email;
    private String Uid;



    public users() {
    }

    public users(String email,  String uid) {
        this.email = email;
        Uid = uid;


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

}
