package com.example.coolrack.Activities.ui.main.MainFragments;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coolrack.generalClass.adaptadores.AdaptadorItemPapelera;
import com.example.coolrack.generalClass.pojos.Libro;
import com.example.coolrack.generalClass.TransitionManager;

import java.util.ArrayList;

public class Papelera extends FatherMainFragment{

    protected AdaptadorItemPapelera adapterPapelera;

    public Papelera(){}

    @Override
    protected void personalizeFragment() {
        getActivity().setTitle("Papelera");
    }

    @Override
    protected void cargarLista() {
        this.listBook = (ArrayList<Libro>) this.queryRecord.getPapelera();
    }

    @Override
    protected void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPapelera = new AdaptadorItemPapelera(getContext(),this.listBook);
        recyclerView.setAdapter(adapterPapelera);

        //Te redirecciona al perfil del usuario (Activity)
        adapterPapelera.setOnclickLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Libro libro = listBook.get(recyclerView.getChildAdapterPosition(view));

                // Le pasa a la actividad del perfil del libro el POJO con los datos del libro correspondiente
                new TransitionManager(getContext()).goToPerfilLibro(libro.getIdentifier());
            }
        });
    }
}
