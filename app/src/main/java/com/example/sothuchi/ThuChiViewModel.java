package com.example.sothuchi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ThuChiViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<ThuChi>> thuChiList = new MutableLiveData<>();

    public LiveData<ArrayList<ThuChi>> getThuChiList() {
        return thuChiList;
    }

    public void setThuChiList(ArrayList<ThuChi> list) {
        thuChiList.setValue(list);
    }
}