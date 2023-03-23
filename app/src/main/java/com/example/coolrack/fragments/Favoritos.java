package com.example.coolrack.fragments;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.generalClass.Libro;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Favoritos extends FatherMainFragment{

    public Favoritos() {}

    @Override
    protected void personalizeFragment() {
        this.seccion = "Favoritos";
        getActivity().setTitle("Favoritos");
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void cargarLista(){
        this.listBook = (ArrayList<Libro>) this.queryRecord.getFavorito();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Libro libro = listBook.get(viewHolder.getAdapterPosition());
            Log.i(TAG,libro.getTitle());

            // Update estado del libro en DB
            libro.setFavorito(false);
            queryRecord.updateBook(libro);

            Snackbar.make(linearLayout,libro.getTitle()+" eliminado de \"Favoritos\"",Snackbar.LENGTH_LONG).show();

            listBook.remove(viewHolder.getAdapterPosition());
            adapterItem.notifyDataSetChanged();

        }
    };
}