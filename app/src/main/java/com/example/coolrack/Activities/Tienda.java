package com.example.coolrack.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coolrack.Activities.ui.tienda.TiendaFragments.TiendaFragment;
import com.example.coolrack.R;

public class Tienda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TiendaFragment.newInstance())
                    .commitNow();
        }
    }
}