package com.example.ayush.finalapp;

import java.io.Serializable;

public class ShopperProfile implements Serializable {

    String fname , lname , email,password , username;

    public ShopperProfile(String fname, String lname, String email, String password , String username) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.username = username;

    }

    public ShopperProfile() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
