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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Leyendo extends Fragment {
    AdaptadorItemBook adapterItem;
    RecyclerView recyclerView;
    ArrayList<Libro> listBook;
    LinearLayout linearLayout;
    QueryRecord queryRecord;

    public Leyendo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leyendo, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayout = view.findViewById(R.id.leyendolayout);
        listBook = new ArrayList<>();
        queryRecord = QueryRecord.get(this.getContext());

        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();

        this.getActivity().setTitle("Leyendo");

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        return view;
    }

    //Carga los datos de los libros a mostrar en la lista de libros
    public void cargarLista(){
        this.listBook = (ArrayList<Libro>) queryRecord.getLeyendo();
    }

    //Muestra el contenido de los Libros y dicta su comportamiento al hacer click en el
    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterItem = new AdaptadorItemBook(getContext(),listBook);
        recyclerView.setAdapter(adapterItem);

        //Te redirecciona al perfil del libro (Activity)
        adapterItem.setOnclickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Libro libro = listBook.get(recyclerView.getChildAdapterPosition(view));

                startActivity(new Intent(getActivity(), com.example.coolrack.Activities.PerfilLibro.class)
                        .putExtra("objetoLibro",  libro)
                );
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();
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