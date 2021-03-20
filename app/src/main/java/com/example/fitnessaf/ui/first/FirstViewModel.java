package com.example.fitnessaf.ui.first;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FirstViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FirstViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("first");
    }

    public LiveData<String> getText() {
        return mText;
    }
}