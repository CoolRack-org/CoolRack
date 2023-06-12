package com.example.coolrack.Activities.ui.main.MainFragments;

import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.pojos.Libro;

import java.util.ArrayList;

public class ParaLeer extends FatherMainFragment{

    public ParaLeer() {}

    @Override
    protected void personalizeFragment() {
        this.seccion = "Para Leer";
        getActivity().setTitle(R.string.mainMenu_para_leer);
        this.textoCallback = String.valueOf(R.string.item_book_drop_paraLeer);
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