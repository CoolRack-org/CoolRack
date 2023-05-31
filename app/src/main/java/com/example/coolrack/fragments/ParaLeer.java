package com.example.coolrack.fragments;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.coolrack.generalClass.pojos.Libro;

import java.util.ArrayList;

public class ParaLeer extends FatherMainFragment{

    public ParaLeer() {}

    @Override
    protected void personalizeFragment() {
        this.seccion = "Para Leer";
        getActivity().setTitle("Para Leer");
        this.textoCallback = "Quitado de \"Para Leer\"";
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void cargarLista(){
        this.listBook = (ArrayList<Libro>) this.queryRecord.getParaLeer();
    }

    @Override
    protected void personalizeCallback(Libro libro) {
        libro.setParaLeer(false);
        queryRecord.updateBook(libro);
    }
}