package com.example.billetblaze;


import android.media.Image;

import androidx.annotation.NonNull;

public class Billet {

    private String HostName;
    private String HostAddress;
    private String CheckIn;
    private String CheckOut;


    public Billet(){

    }

    public Billet(String HostName, String HostAddress, String CheckIn, String CheckOut){
        this.HostName = HostName;
        this.HostAddress = HostAddress;
        this.CheckIn = CheckIn;
        this.CheckOut = CheckOut;
    }

    @NonNull
    public String toString(){
        return HostName + "\n" + HostAddress + "\n" + CheckIn + " to " + CheckOut;
    }
}
