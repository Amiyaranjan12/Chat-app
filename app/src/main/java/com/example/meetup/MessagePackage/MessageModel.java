package com.example.meetup.MessagePackage;

public class MessageModel {

    String message;
    String senderId;
    String messageTime;
    String messageDate;

    public MessageModel() {

    }
    public MessageModel(String message, String senderId, String messageTime, String messageDate) {
        this.message = message;
        this.senderId = senderId;
        this.messageTime = messageTime;
        this.messageDate= messageDate;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
