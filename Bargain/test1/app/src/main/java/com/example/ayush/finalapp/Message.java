package com.example.ayush.finalapp;

public class Message {

    String sender,receiver,message,id;

    public Message() {
    }

    public Message(String message, String sender, String receiver, String id) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
