package com.example.ymautowheel.ui.ban;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ban fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}