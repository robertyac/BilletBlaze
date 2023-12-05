package com.example.billetblaze.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BilletSharedData extends AndroidViewModel {
    private final MutableLiveData<String> startDate = new MutableLiveData<>();
    private final MutableLiveData<String> endDate = new MutableLiveData<>();
    private final MutableLiveData<String> priceString = new MutableLiveData<>();
    private final MutableLiveData<String> paymentUsed = new MutableLiveData<>();
    private final MutableLiveData<String> numGuests = new MutableLiveData<>();

    private final MutableLiveData<String> takenBID = new MutableLiveData<>();
    private final MutableLiveData<String> guestName = new MutableLiveData<>();

    // Class for storing the live data that the user selects for date values. This way the user
    // does not have to re-enter the values on another page etc.
    public BilletSharedData(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.setValue(startDate);
    }

    public LiveData<String> getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.setValue(endDate);
    }

    public LiveData<String> getPriceString() {return priceString;}

    public void setPriceString(String priceString) {this.priceString.setValue(priceString);}

    public MutableLiveData<String> getPaymentUsed() {return paymentUsed;}

    public void setPaymentUsed(String paymentUsed) {this.paymentUsed.setValue(paymentUsed);}

    public MutableLiveData<String> getNumGuests() {return numGuests;}

    public void setNumGuests(String numGuests){this.numGuests.setValue(numGuests);}

    public MutableLiveData<String> getGuestName() {return guestName;}

    public void setGuestName(String guestName){this.guestName.setValue(guestName);}

    public MutableLiveData<String> getTakenBID() {return takenBID;}

    public void setTakenBID(String takenBID){this.takenBID.setValue(takenBID);}
}

