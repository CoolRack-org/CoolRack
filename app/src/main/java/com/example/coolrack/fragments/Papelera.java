package com.example.coolrack.fragments;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.generalClass.Libro;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Papelera extends FatherMainFragment{
    public Papelera(){}

    @Override
    protected void personalizeFragment() {
        getActivity().setTitle("Papelera");

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void cargarLista() {
        this.listBook = (ArrayList<Libro>) this.queryRecord.getPapelera();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Libro libro = listBook.get(viewHolder.getAdapterPosition());

            Snackbar.make(linearLayout,libro.getTitle()+" eliminado de la papelera",Snackbar.LENGTH_LONG).show();

            libro.setPapelera(false);
            queryRecord.updateBook(libro);

            listBook.remove(viewHolder.getAdapterPosition());
            adapterItem.notifyDataSetChanged();
        }
    };
}
