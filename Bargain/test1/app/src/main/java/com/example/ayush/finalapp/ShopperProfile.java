package com.example.ayush.finalapp;

import java.io.Serializable;

public class ShopperProfile implements Serializable {

    String fname , lmane, meamil,mphone,dob;
    public ShopperProfile()
    {}

    public ShopperProfile(String fname, String lmane, String meamil, String mphone, String dob) {
        this.fname = fname;
        this.lmane = lmane;
        this.meamil = meamil;
        this.mphone = mphone;
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLmane() {
        return lmane;
    }

    public void setLmane(String lmane) {
        this.lmane = lmane;
    }

    public String getMeamil() {
        return meamil;
    }

    public void setMeamil(String meamil) {
        this.meamil = meamil;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
