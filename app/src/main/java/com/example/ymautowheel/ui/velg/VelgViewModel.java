package com.example.ymautowheel.ui.velg;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VelgViewModel extends ViewModel {
    private MutableLiveData<String> mText;



    public VelgViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow velg");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
