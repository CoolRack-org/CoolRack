package com.example.coolrack.fragments;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.generalClass.AdaptadorItemBook;
import com.example.coolrack.generalClass.AdaptadorItemPapelera;
import com.example.coolrack.generalClass.Libro;
import com.example.coolrack.generalClass.TransitionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Papelera extends FatherMainFragment{

    protected AdaptadorItemPapelera adapterPapelera;

    public Papelera(){}

    @Override
    protected void personalizeFragment() {
        getActivity().setTitle("Papelera");
    }

    @Override
    protected void cargarLista() {
        this.listBook = (ArrayList<Libro>) this.queryRecord.getPapelera();
    }

    @Override
    protected void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPapelera = new AdaptadorItemPapelera(getContext(),this.listBook);
        recyclerView.setAdapter(adapterPapelera);

        //Te redirecciona al perfil del usuario (Activity)
        adapterPapelera.setOnclickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Libro libro = listBook.get(recyclerView.getChildAdapterPosition(view));

                // Le pasa a la actividad del perfil del libro el POJO con los datos del libro correspondiente
                new TransitionManager(getContext()).goToPerfilLibro(libro.getIdentifier());
            }
        });
    }
}
