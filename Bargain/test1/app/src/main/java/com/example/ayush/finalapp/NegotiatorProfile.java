package com.example.ayush.finalapp;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class NegotiatorProfile implements Serializable {

    private  String fname, lname , username, memail, mpassword;

    public NegotiatorProfile(String fname, String lname, String username, String memail, String mpassword) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.memail = memail;
        this.mpassword = mpassword;
    }

    public NegotiatorProfile() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getMpassword() {
        return mpassword;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }
}