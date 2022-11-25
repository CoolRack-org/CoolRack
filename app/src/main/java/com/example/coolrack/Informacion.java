package com.example.coolrack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolrack.generalClass.GenerateBooks;
import com.example.coolrack.generalClass.Libro;
import com.example.coolrack.generalClass.XMLControll.XMLController;

import java.util.ArrayList;

public class Informacion extends Fragment {

    public Informacion() {}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informacion, container, false);
    }
}