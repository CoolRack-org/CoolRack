package com.example.coolrack.Activities.ui.main.MainFragments;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.coolrack.generalClass.pojos.Libro;

import java.util.ArrayList;

public class Leidos extends FatherMainFragment{

    public Leidos() {}

    @Override
    protected void personalizeFragment() {
        this.seccion = "Leidos";
        getActivity().setTitle("Leidos");
        this.textoCallback = "Quitado de \"Leidos\"";
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void cargarLista(){
        this.listBook = (ArrayList<Libro>) this.queryRecord.getLeido();
    }

    @Override
    protected void personalizeCallback(Libro libro) {
        libro.setLeido(false);
        queryRecord.updateBook(libro);
    }
}