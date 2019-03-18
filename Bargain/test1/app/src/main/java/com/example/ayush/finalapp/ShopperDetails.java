package com.example.ayush.finalapp;


import java.io.Serializable;

public class ShopperDetails implements Serializable {

    String fname, lname, email, dob, phone, amount,username;

    public ShopperDetails(String fname, String lname, String email, String dob, String phone, String amount,String username) {
        this.fname = fname;
        this.lname = lname;
        this.username=username;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.amount = amount;

    }

    public ShopperDetails() {
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public String getPhno() {
        return phone;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPhno(String phno) {
        this.phone = phno;
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
