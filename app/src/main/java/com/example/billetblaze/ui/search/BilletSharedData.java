package com.example.billetblaze.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BilletSharedData extends AndroidViewModel {
    private final MutableLiveData<String> startDate = new MutableLiveData<>();
    private final MutableLiveData<String> endDate = new MutableLiveData<>();


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
}