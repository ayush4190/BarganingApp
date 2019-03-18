package com.example.ayush.finalapp;

public class TransactionsDetails {
    String debitedFrom, creditedTo,amount, date, status;
    String creditedToName,debitedFromName;

    TransactionsDetails(){

    }

    public TransactionsDetails(String debitedFrom, String creditedTo, String creditedToName, String date, String status,String amount, String debitedFromName) {
        this.debitedFrom = debitedFrom;

        this.creditedTo = creditedTo;
        this.creditedToName=creditedToName;
        this.debitedFromName=debitedFromName;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public String getCreditedToName() {
        return creditedToName;
    }

    public String getDebitedFromName() {
        return debitedFromName;
    }

    public void setCreditedToName(String creditedToName) {
        this.creditedToName = creditedToName;
    }

    public void setDebitedFromName(String debitedFromName) {
        this.debitedFromName = debitedFromName;
    }

    public String getAmount() {
        return amount;
    }

    public String getCreditedTo() {
        return creditedTo;
    }

    public String getDate() {
        return date;
    }

    public String getDebitedFrom() {
        return debitedFrom;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCreditedTo(String creditedTo) {
        this.creditedTo = creditedTo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDebitedFrom(String debitedFrom) {
        this.debitedFrom = debitedFrom;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
