package com.example.coolrack.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.Libro;

public class PerfilLibro extends AppCompatActivity {
    private Libro libro = null;
    private int DIRECCION_ANTERIOR = 0;
    private  String direccion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_libro);

        // Crea un boton con forma de flecha el la toolbar
        // esta se usara para retroceder
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // carga el pojo enviado por  la Actividad/fragmento que cargo esta Activity anteriormente
        Bundle bundle = getIntent().getExtras();
        this.libro = (Libro) bundle.getSerializable("objetoLibro");
        this.setTitle(libro.getTitle());

        // Carga los datos del pojo para para poder verse
        cargarDatos(libro);
        this.DIRECCION_ANTERIOR = (int) bundle.getSerializable("direccionAnterior");

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

    // Redirecciona al usuario dependiendo de la opcion selecionada en la toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            startActivity(new Intent(this,MainActivity.class).putExtra("direccionAnterior",this.DIRECCION_ANTERIOR));
            //NavUtils.navigateUpFromSameTask(this);
            return true;
    }
        return super.onOptionsItemSelected(item);
    }
}