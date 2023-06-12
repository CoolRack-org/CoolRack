package com.example.coolrack.Activities.ui.main.MainFragments;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.pojos.Libro;

import java.util.ArrayList;

public class Leyendo extends FatherMainFragment{

    public Leyendo() {}

    @Override
    protected void personalizeFragment() {
        this.seccion = "Leyendo";
        getActivity().setTitle(R.string.mainMenu_leyendo);
        this.textoCallback = String.valueOf(R.string.expulsion_lista);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void cargarLista(){
        this.listBook = (ArrayList<Libro>) this.queryRecord.getLeyendo();
    }


    @Override
    protected void personalizeCallback(Libro libro) {
        libro.setLeyendo(false);
        queryRecord.updateBook(libro);
    }
}