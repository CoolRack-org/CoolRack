package com.example.coolrack.Activities.ui.main.MainFragments;

import static android.content.ContentValues.TAG;

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
import com.example.coolrack.generalClass.adaptadores.AdaptadorItemBook;
import com.example.coolrack.generalClass.pojos.Libro;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.TransitionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FatherMainFragment extends Fragment {

    public FatherMainFragment() {}

    protected AdaptadorItemBook adapterItem;
    protected RecyclerView recyclerView;
    protected ArrayList<Libro> listBook;
    protected LinearLayout linearLayout;
    protected String seccion = "";
    protected String textoCallback = "";

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

    // Metodo que permite personalizar/realizar acciones nuevas cuando se ejecuta un onCreateView en un fragment hijo
    protected void personalizeFragment(){
        getActivity().setTitle(R.string.mainMenu_biblioteca);
    }

    // Introduce los libros que se tienen que mostrar en un array para procesarlos
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

    // Permite deslizar las cards de los libros, esto lo usamos para eliminar los libros de una lista/seccion mas comodamente.
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
            personalizeCallback(libro);

            Snackbar.make(linearLayout,textoCallback,Snackbar.LENGTH_LONG).show();

            listBook.remove(viewHolder.getAdapterPosition());
            adapterItem.notifyDataSetChanged();
        }
    };

    // Permite a los fragments hijos personalizar las acciones que se realizaran en el callback, concretamente el metodo onSwiped. Esto permite
    // a a los fragments hijos no tener que crear un nuevo callback y decirle que datos modificar y actualizar de la base de datos.
    protected void personalizeCallback(Libro libro){}

    @Override
    public void onResume() {
        super.onResume();
        //cargar lista
        cargarLista();
        //mostrar data
        mostrarData();
    }
}