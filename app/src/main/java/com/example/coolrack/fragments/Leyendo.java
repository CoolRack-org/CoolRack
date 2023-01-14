package com.example.coolrack.fragments;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.AdaptadorItemBook;
import com.example.coolrack.generalClass.Libro;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.TransitionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Leyendo extends FatherMainFragment{

    public Leyendo() {}

    @Override
    protected void personalizeFragment() {
        getActivity().setTitle("Leyendo");
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void cargarLista(){
        this.listBook = (ArrayList<Libro>) this.queryRecord.getLeyendo();
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
            libro.setLeyendo(false);
            queryRecord.updateBook(libro);

            Snackbar.make(linearLayout,libro.getTitle()+" eliminado de LEYENDO",Snackbar.LENGTH_LONG).show();

            listBook.remove(viewHolder.getAdapterPosition());
            adapterItem.notifyDataSetChanged();

        }
    };
}