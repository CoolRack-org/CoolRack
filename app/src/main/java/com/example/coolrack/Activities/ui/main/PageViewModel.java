package com.example.coolrack.Activities.ui.main;

import android.content.Context;
import android.widget.TextView;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.coolrack.R;
import com.example.coolrack.fragments.InfoFragments.DataInformacion;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            DataInformacion data = new DataInformacion();
            switch (input){

                case 0: return data.DATA_NOVEDADES;
                case 1: return data.DATA_NOSOTROS;
                case 2: return data.DATA_MAS_INFO;
                case 3: return data.DATA_LICENCIA;
            }
            return null;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
