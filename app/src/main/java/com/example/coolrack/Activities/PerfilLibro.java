package com.example.coolrack.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.Libro;

public class PerfilLibro extends AppCompatActivity {
    private  String direccion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_libro);

        Bundle bundle = getIntent().getExtras();
        Libro libro = (Libro) bundle.getSerializable("objetoLibro");

        this.setTitle(libro.getTitle());

        cargarDatos(libro);

    }

    // Carga los datos del POJO libro al XML
    public void cargarDatos(Libro l){
        // Inicializa los TextViews y el imageView
        TextView titulo = (TextView) findViewById( R.id.titulo_perfil);
        TextView autor = (TextView) findViewById( R.id.autor_perfil);
        TextView serie = (TextView) findViewById( R.id.serie_perfil);
        TextView lenguaje = (TextView) findViewById( R.id.lenguaje_perfil);
        TextView identificador = (TextView) findViewById( R.id.identificador_perfil);
        TextView url = (TextView) findViewById( R.id.url_perfil);
        ImageView imagen = (ImageView) findViewById( R.id.imagen_Perfil);

        this.direccion = l.getUrl();

        // setea los datos
        titulo.setText(l.getTitle());
        autor.setText(l.getAuthor());
        serie.setText(l.getSerie());
        lenguaje.setText(l.getLanguage());
        identificador.setText(l.getIdentifier());
        url.setText(l.getUrl());
        imagen.setImageResource(l.getImg());


    }
}