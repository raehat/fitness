package com.example.fitnessaf.ui.third;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThirdViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ThirdViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("third");
    }

    public LiveData<String> getText() {
        return mText;
    }
}