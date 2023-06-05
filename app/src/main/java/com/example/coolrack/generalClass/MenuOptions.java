package com.example.coolrack.generalClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.example.coolrack.Activities.ui.main.MainFragments.FatherMainFragment;
import com.example.coolrack.Activities.ui.main.MainFragments.Favoritos;
import com.example.coolrack.Activities.ui.main.MainFragments.Leidos;
import com.example.coolrack.Activities.ui.main.MainFragments.Leyendo;
import com.example.coolrack.Activities.ui.main.MainFragments.Papelera;
import com.example.coolrack.Activities.ui.main.MainFragments.ParaLeer;
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

    public void mainMenuSwitch(int id, FragmentTransaction transactioni, Menu fragmentsOptionsMenu){


        switch (id){
            // ventana de "leyendo" donde se muestran los ultimos libros abiertos
            case R.id.nav_leyendo:
                fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(false);
                transactioni.replace(R.id.frame_layout,new Leyendo()).commit();
                break;
            // ventana de la biblioteca donde se muestran todos los libros
            case R.id.nav_biblioteca:
                fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(false);
                transactioni.replace(R.id.frame_layout,new FatherMainFragment()).commit();
                break;
            // ventana de libros marcados como "Para Leer"
            case R.id.nav_para_leer:
                fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(false);
                transactioni.replace(R.id.frame_layout,new ParaLeer()).commit();
                break;
            // ventana de libros marcados como "Favoritos"
            case R.id.nav_favorito:
                fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(false);
                transactioni.replace(R.id.frame_layout,new Favoritos()).commit();
                break;
            // ventana de libros marcados como "Leidos"
            case R.id.nav_leidos:
                fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(false);
                transactioni.replace(R.id.frame_layout,new Leidos()).commit();
                break;
            // ventana de papelera de reciclaje
            case R.id.nav_papelera:
                fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(true);
                transactioni.replace(R.id.frame_layout,new Papelera()).commit();
                break;
            // ventana de la tienda de libros
            case R.id.nav_tienda:
                context.startActivity(new Intent(context, com.example.coolrack.Activities.Tienda.class));
                break;
            // ventana de opciones de configuracio de la aplicacion
            case R.id.nav_opciones:
                context.startActivity(new Intent(context, com.example.coolrack.Activities.SettingsActivity.class));
                break;
            // ventana de inforrmacion de la aplicacion
            case R.id.nav_info:
                context.startActivity(new Intent(context, com.example.coolrack.Activities.InformacionActivity.class));
                break;
        }
    }

    public void buttonDelate(LayoutInflater inflater, FragmentTransaction transactioni){
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
