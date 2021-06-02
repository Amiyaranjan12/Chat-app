package com.example.meetup;

public class userModel {

    String name;
    String email;
    String hobby;
    String userId;
    String image;


    public userModel(){
    }

    public userModel(String userId,String name,String email,String hobby,String image){

        this.email=email;
        this.hobby=hobby;
        this.name=name;
        this.userId=userId;
        this.image=image;


    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
