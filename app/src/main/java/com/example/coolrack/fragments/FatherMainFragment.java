package com.example.coolrack.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.AdaptadorItemBook;
import com.example.coolrack.generalClass.Libro;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.TransitionManager;

import java.util.ArrayList;

public class FatherMainFragment extends Fragment {

    public FatherMainFragment() {}

    protected AdaptadorItemBook adapterItem;
    protected RecyclerView recyclerView;
    protected ArrayList<Libro> listBook;
    protected LinearLayout linearLayout;
    protected String seccion = "";

    protected QueryRecord queryRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_father_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        listBook = new ArrayList<>();
        linearLayout = view.findViewById(R.id.bibliotecaLayout);
        queryRecord = QueryRecord.get(this.getContext());

        personalizeFragment();

        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();

        return view;
    }

    // metodo que permite personalizar/realizar acciones nuevas cuando se ejecuta un onCreateView en un fragment hijo
    protected void personalizeFragment(){
        getActivity().setTitle("Biblioteca");
    }

    protected void cargarLista(){
        this.listBook = (ArrayList<Libro>) queryRecord.getAll();
    }

    //Muestra el contenido de los Libros y dicta su comportamiento al hacer click en el
    protected void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterItem = new AdaptadorItemBook(getContext(),this.listBook, seccion);
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