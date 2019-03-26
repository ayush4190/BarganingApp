package com.example.ayush.finalapp;

import java.io.Serializable;

public class NegotiatorDetails implements Serializable {
    String phone, dob, gender , bl,address,city,state,pincode,category1,category2,category3, firstname, lastname,year ,email ,amount , ratings , count,acceptno,requestno;

    public NegotiatorDetails(String phone, String dob, String gender, String address, String city, String year,String state, String pincode, String category1, String category2, String category3, String firstname, String lastname, String bl,String email ,String amount , String ratings , String count, String requestno, String acceptno) {
        this.phone = phone;
        this.dob = dob;
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
        this.year = year;
        this.email = email;
        this.amount = amount;
        this.ratings = ratings;
        this.count = count;
        this.acceptno=acceptno;
        this.requestno=requestno;


    }

    public  NegotiatorDetails(NegotiatorDetails w){
        this.lastname=w.lastname;
        this.firstname=w.firstname;
        this.category1=w.category1;
        this.category2=w.category2;
        this.category3=w.category3;
        this.address=w.address;
        this.bl=w.bl;
        this.state=w.state;
        this.city=w.city;
        this.phone=w.phone;
        this.dob=w.dob;
        this.gender=w.gender;
        this.pincode=w.pincode;
        this.year = w.year;
        this.email = w.email;
        this.amount = w.amount;
        this.ratings = w.ratings;
        this.count = w.count;
        this.acceptno=w.acceptno;
        this.requestno=w.requestno;
    }
    public NegotiatorDetails() {


    }



    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAcceptno() {
        return acceptno;
    }

    public String getRequestno() {
        return requestno;
    }

    public void setAcceptno(String acceptno) {
        this.acceptno = acceptno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }
    //    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }
}
