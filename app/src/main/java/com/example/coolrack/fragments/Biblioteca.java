package com.example.coolrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.AdaptadorItemBook;
import com.example.coolrack.generalClass.Libro;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.TransitionManager;


import java.util.ArrayList;

public class Biblioteca extends Fragment {

    AdaptadorItemBook adapterItem;
    RecyclerView recyclerView;
    ArrayList<Libro> listBook;

    private QueryRecord queryRecord;

    public Biblioteca() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biblioteca, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        listBook = new ArrayList<>();
        queryRecord = QueryRecord.get(this.getContext());

        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();

        this.getActivity().setTitle("Biblioteca");

        return view;
    }

    public void cargarLista(){
        this.listBook = (ArrayList<Libro>) queryRecord.getAll();
    }

    //Muestra el contenido de los Libros y dicta su comportamiento al hacer click en el
    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterItem = new AdaptadorItemBook(getContext(),this.listBook);
        recyclerView.setAdapter(adapterItem);

        //Te redirecciona al perfil del usuario (Activity)
        adapterItem.setOnclickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Libro libro = listBook.get(recyclerView.getChildAdapterPosition(view));

                // Le pasa a la actividad del perfil del libro el POJO con los datos del libro correspondiente
                new TransitionManager(getContext()).goToPerfilLibro(libro.getIdentifier());
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
}