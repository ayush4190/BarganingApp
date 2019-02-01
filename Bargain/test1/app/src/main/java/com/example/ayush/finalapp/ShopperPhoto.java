package com.example.ayush.finalapp;

public class ShopperPhoto {
    String phone , dob ;

    public ShopperPhoto(String phone, String dob) {
        this.phone = phone;
        this.dob = dob;
    }

    public ShopperPhoto() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
