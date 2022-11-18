package com.example.coolrack;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.coolrack.generalClass.AdaptadorItemBook;
import com.example.coolrack.generalClass.Libro;

import java.util.ArrayList;

public class Biblioteca extends Fragment {

    AdaptadorItemBook adapterItem;
    RecyclerView recyclerView;
    ArrayList<Libro> listBook;

    public Biblioteca() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_biblioteca, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        listBook = new ArrayList<>();

        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();

        return view;
    }

    public void cargarLista(){
        listBook.add(new Libro("Libro1","jose","Los mataSanos","Text",R.drawable.ic_launcher_background));
        listBook.add(new Libro("Libro2","jose","Los mataSanos1","Text",R.drawable.ic_launcher_foreground));
        listBook.add(new Libro("Libro3","jose","Los mataSanos2","Text",R.drawable.ic_launcher_background));
        listBook.add(new Libro("Libro1","jose","Los mataSanos","Text",R.drawable.ic_launcher_background));
        listBook.add(new Libro("Libro2","jose","Los mataSanos1","Text",R.drawable.ic_launcher_foreground));
        listBook.add(new Libro("Libro3","jose","Los mataSanos2","Text",R.drawable.ic_launcher_background));
        listBook.add(new Libro("Libro1","jose","Los mataSanos","Text",R.drawable.ic_launcher_background));
        listBook.add(new Libro("Libro2","jose","Los mataSanos1","Text",R.drawable.ic_launcher_foreground));
        listBook.add(new Libro("Libro3","jose","Los mataSanos2","Text",R.drawable.ic_launcher_background));
    }

    //Muestra el contenido de los Libros y dicta su comportamiento al hacer click en el
    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterItem = new AdaptadorItemBook(getContext(),listBook);
        recyclerView.setAdapter(adapterItem);

        adapterItem.setOnclickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"holaBiblioteca",Toast.LENGTH_LONG).show();
            }
        });
    }
}