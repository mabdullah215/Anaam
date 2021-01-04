package com.app.anaamapp.model;

public class User
{
    String title;
    String userid;
    String details;
    String imgsource;
    String number;
    String email;
    String address;
    String location;


    public User()
    {
        this.email="";
        this.address="";
        this.location="";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImgsource() {
        return imgsource;
    }

    public void setImgsource(String imgsource) {
        this.imgsource = imgsource;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
