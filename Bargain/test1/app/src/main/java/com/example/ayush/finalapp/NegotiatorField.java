package com.example.ayush.finalapp;

public class NegotiatorField {

    String Cat1,cat2,cat3;
    public NegotiatorField()
    {}

    public NegotiatorField(String cat1, String cat2, String cat3) {

        Cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
    }

    public String getCat1() {
        return Cat1;
    }

    public void setCat1(String cat1) {
        Cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }
}
