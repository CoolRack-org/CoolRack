package com.example.coolrack.generalClass;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.pojos.Libro;

import java.util.ArrayList;

/** La clase MenuOptions se utiliza para almacenar las funciones que se ejecutan
 * al presionar una opcion del menu "fragments_menu.xml".
 * Se decidio hacerse de esa manera para poder tener un MainActivity mas limpio.
 *
 * @author Jibuza
 */
public class MenuOptions {
    private Context context;

    public MenuOptions(Context context) {
        this.context = context;
    }

    public void buttonDelate(LayoutInflater inflater){
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);


        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View view = inflater.inflate(R.layout.delete_popup, null);

        // Boton para confirmar la eliminacion de los libros de la aplicacion y/o del sistema
        Button btnDelate = (Button) view.findViewById(R.id.btnConfirmDelete);

        // CheckBox que determina si los libros en la papelera seran eliminados de la aplicacion y del sistema
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBorrarDelDispositivo);

        builder.setView(view);
        alertDialog = builder.create();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView texto = view.findViewById(R.id.textoAlertPapelera);

                if (b){
                    texto.setText("Los documentos serán borrados del sistema");
                    texto.setTextColor(Color.parseColor("#FF6200EE"));
                }else {
                    texto.setText("Los documentos serán borrados de la aplicación");
                    texto.setTextColor(Color.parseColor("#FF7E7E7E"));
                }
            }
        });

        btnDelate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QueryRecord queryRecord = QueryRecord.get(context);
                        GenerateBooks generateBooks = new GenerateBooks(context);
                        ArrayList<Libro> libros = (ArrayList<Libro>) queryRecord.getPapelera();

                        // Si es verdadero se borran los libros de la DB y del sistema
                        // Si es falso solo se borran de la DB
                        if (checkBox.isChecked()){
                            for (Libro l : libros){
                                queryRecord.deleteBook(l);
                                generateBooks.removeBook(l.getCopyBookUrl());
                                generateBooks.removeBook(l.getOriginalBookUrl());
                            }
                        }else {
                            for (Libro l : libros){
                                queryRecord.deleteBook(l);
                                generateBooks.removeBook(l.getCopyBookUrl());
                            }
                        }

                        alertDialog.dismiss();
                    }
                }
        );

        alertDialog.show();
    }
}
