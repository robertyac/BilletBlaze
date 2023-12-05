package com.example.billetblaze.ui.manage;

import androidx.annotation.NonNull;

public class Review {

    private String HostName;
    private String HostAddress;
    private String CheckIn;
    private String CheckOut;
    private String review;
    int Image;

    public Review(){

    }

    public Review(String HostName, String HostAddress, String CheckIn, String CheckOut, int Image, String review){
        this.HostName = HostName;
        this.HostAddress = HostAddress;
        this.CheckIn = CheckIn;
        this.CheckOut = CheckOut;
        this.Image = Image;
        this.review = review;
    }

    public String getReview(){return review;}
    public void setReview(String review){review = review;}

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    public String getHostAddress() {
        return HostAddress;
    }

    public void setHostAddress(String hostAddress) {
        HostAddress = hostAddress;
    }

    public String getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(String checkIn) {
        CheckIn = checkIn;
    }

    public String getCheckOut() {
        return CheckOut;
    }

    public void setCheckOut(String checkOut) {
        CheckOut = checkOut;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    @NonNull
    public String toString(){
        return HostName + "\n" + HostAddress + "\n" + CheckIn + " to " + CheckOut;
    }
}
