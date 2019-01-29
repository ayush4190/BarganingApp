package com.example.ayush.finalapp;

import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class NegotiatorProfile implements Serializable {
    String  firstname,lastname,phno,ad1 , ad2, city, state, pincode,email,dob ;

    public NegotiatorProfile(Object profile){}

    public NegotiatorProfile(NegotiatorProfile temp)
    {
        this.firstname = temp.firstname;
        this.lastname = temp.lastname;
        this.phno = temp.phno;
        this.ad1 = temp.ad1;
        this.ad2 = temp.ad2;
        this.city = temp.city;
        this.state = temp.state;
        this.pincode = temp.pincode;
        this.email = temp.email;
        this.dob = temp.dob;
    }

    public NegotiatorProfile(String firstname, String lastname, String phno, String ad1, String ad2, String city, String state, String pincode, String email, String dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phno = phno;
        this.ad1 = ad1;
        this.ad2 = ad2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.email = email;
        this.dob = dob;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getAd1() {
        return ad1;
    }

    public void setAd1(String ad1) {
        this.ad1 = ad1;
    }

    public String getAd2() {
        return ad2;
    }

    public void setAd2(String ad2) {
        this.ad2 = ad2;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
