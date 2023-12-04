package com.example.billetblaze;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class BilletDetail implements Serializable {
    private String location;
    private String startDate;
    private String endDate;
    private int maxGuests;
    private List<String> amenities;
    private int price;

    public BilletDetail(String location, String startDate, String endDate, int maxGuests, List<String> amenities, int price) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.amenities = amenities;
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public int getPrice() {
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return location + "\n" + startDate + " to " + endDate + "\n" + maxGuests + " guests\n" + "Amenities: " + amenities.toString() + "\n" + "Price: " + price;
    }
}
