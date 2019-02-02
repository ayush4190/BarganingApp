package com.example.ayush.finalapp;

public class NegotiatorDetails {
    String phone, dob, workhour1,workhour2,workhour3,gender , bl,address,city,state,pincode,category1,category2,category3, firstname, lastname;

    public NegotiatorDetails(String phone, String dob, String gender, String address, String city, String state, String pincode, String category1, String category2, String category3, String firstname, String lastname, String bl, String workhour1, String workhour2, String workhour3) {
        this.phone = phone;
        this.dob = dob;
        this.workhour1=workhour1;
        this.workhour2=workhour2;
        this.workhour3=workhour3;
        this.gender = gender;
        this.address = address;
        this.bl=bl;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.firstname=firstname;
        this.lastname=lastname;

    }

    public  NegotiatorDetails(NegotiatorDetails w){
        this.lastname=w.lastname;
        this.firstname=w.firstname;
        this.category1=w.category1;
        this.category2=w.category2;
        this.category3=w.category3;
        this.address=w.address;
        this.workhour1=w.workhour1;
        this.workhour2=w.workhour2;
        this.workhour3=w.workhour3;

        this.bl=w.bl;
        this.state=w.state;
        this.city=w.city;
        this.phone=w.phone;
        this.dob=w.dob;
        this.gender=w.gender;
        this.pincode=w.pincode;

    }
    public NegotiatorDetails() {

    }



    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getWorkhour1() {
        return workhour1;
    }

    public String getWorkhour2() {
        return workhour2;
    }

    public String getWorkhour3() {
        return workhour3;
    }

    public void setWorkhour1(String workhour1) {
        this.workhour1 = workhour1;
    }

    public void setWorkhour2(String workhour2) {
        this.workhour2 = workhour2;
    }

    public void setWorkhour3(String workhour3) {
        this.workhour3 = workhour3;
    }

    public String getBl() {
        return bl;
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
        dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
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

    public void setBl(String bl) {
        this.bl = bl;
    }

}
