package com.example.ayush.finalapp;

public class shopperPhone_dob {
    private String dob , phone,fname , lname , email, username;

    public shopperPhone_dob(String dob, String phone, String fname, String lname, String email , String username) {
        this.dob = dob;
        this.phone = phone;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.username = username;
    }

    public shopperPhone_dob() {
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
