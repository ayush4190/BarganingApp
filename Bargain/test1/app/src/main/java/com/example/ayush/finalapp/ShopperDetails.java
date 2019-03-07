package com.example.ayush.finalapp;


public class ShopperDetails {

        String fname , lname , email, dob, phno;

        public ShopperDetails(String fname, String lname, String email, String dob, String phno) {
            this.fname = fname;
            this.lname = lname;
            this.email = email;
            this.dob=dob;
            this.phno=phno;

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



}
