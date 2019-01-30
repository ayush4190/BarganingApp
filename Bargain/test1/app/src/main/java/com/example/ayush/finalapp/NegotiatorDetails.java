package com.example.ayush.finalapp;

public class NegotiatorDetails {
    String phone, Dob, gender , Address,city,state,pincode,category1,category2,category3;

    public NegotiatorDetails(String phone, String dob, String gender, String address, String city, String state, String pincode, String category1, String category2, String category3) {
        this.phone = phone;
        Dob = dob;
        this.gender = gender;
        Address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
    }

    public NegotiatorDetails() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }
}
