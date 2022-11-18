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

/*
*
 * A simple {@link Fragment} subclass.
 * Use the {@link Leyendo#newInstance} factory method to
 * create an instance of this fragment.
*/
public class Leyendo extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
    public Leyendo() {
//        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment Informacion.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Informacion newInstance(String param1, String param2) {
//        Informacion fragment = new Informacion();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }



   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        *//*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*//*
    }*/

    AdaptadorItemBook adapterItem;
    RecyclerView recyclerView;
    ArrayList<Libro> listBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leyendo, container, false);
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

    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterItem = new AdaptadorItemBook(getContext(),listBook);
        recyclerView.setAdapter(adapterItem);

        adapterItem.setOnclickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"hola",Toast.LENGTH_LONG).show();
            }
        });
    }
}