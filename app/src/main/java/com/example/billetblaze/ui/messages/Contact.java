package com.example.billetblaze.ui.messages;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Contact implements Parcelable {
    private String name;
    private int image;
    private List<String> convo;

    public Contact(){

    }

    public Contact(String name, int image, List<String> convo){

        this.name = name;
        this.image = image;
        this.convo = convo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        image = image;
    }

    public List<String> getConvo() {
        return convo;
    }

    public void setConvo(List<String> convo) {
        this.convo = convo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.image);
        dest.writeStringList(convo);
    }
}
