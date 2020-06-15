package com.example.ymautowheel.ui.suspensi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuspensiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuspensiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow suspensi");
    }

    public LiveData<String> getText() {
        return mText;
    }
}