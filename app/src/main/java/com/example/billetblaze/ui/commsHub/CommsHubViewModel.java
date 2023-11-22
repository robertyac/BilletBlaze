package com.example.billetblaze.ui.commsHub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommsHubViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CommsHubViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}