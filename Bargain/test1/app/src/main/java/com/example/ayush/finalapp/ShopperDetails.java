package com.example.ayush.finalapp;


import java.io.Serializable;

public class ShopperDetails implements Serializable {

    String fname, lname, email, dob, phno, amount;

    public ShopperDetails(String fname, String lname, String email, String dob, String phno, String amount) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
        this.phno = phno;
        this.amount = amount;

    }

    public ShopperDetails() {
    }

    public String getDob() {
        return dob;
    }

    public String getPhno() {
        return phno;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPhno(String phno) {
        this.phno = phno;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
