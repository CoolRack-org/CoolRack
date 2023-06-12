package com.example.coolrack.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coolrack.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilTienda extends AppCompatActivity {

    private String enlaceDescarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_tienda);

        // Crea un boton con forma de flecha el la toolbar
        // esta se usara para retroceder
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // LLama a la funcion encargada re realizar al consulta y setear los datos en el pojo
        String idBook = getIntent().getExtras().getString("idBook");
        query(idBook);


    }

    private void query(String nodeId) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("libros").child(nodeId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot nodo) {
                if (nodo.exists()) {
                    String autorDB = (String) nodo.child("autor").getValue();
                    String coleccionDB = (String) nodo.child("coleccion").getValue();
                    String enlaceDB = (String) nodo.child("enlaces").getValue();
                    Long fechaPubliDB = (Long) nodo.child("fecha_publi").getValue();
                    String generoDB = (String) nodo.child("generos").getValue();
                    String idiomaDB = (String) nodo.child("idioma").getValue();
                    String imgDirDB = (String) nodo.child("imgDir").getValue();
                    Long paginasDB = (Long) nodo.child("paginas").getValue();
                    String sinopsisDB = (String) nodo.child("sinopsis").getValue();
                    String tituloDB = (String) nodo.child("titulo").getValue();
                    Long idDB = (Long) nodo.child("epl_id").getValue();

                    // Rellenar los views de la interfaz con los datos obtenidos
                    TextView titulo = (TextView) findViewById(R.id.titulo_perfil_tienda);
                    TextView magnetLink = (TextView) findViewById(R.id.magnetLink_perfil_tienda);
                    TextView autor = (TextView) findViewById(R.id.autor_perfil_tienda);
                    TextView coleccion = (TextView) findViewById(R.id.coleccion_perfil_tienda);
                    TextView sinopsis = (TextView) findViewById(R.id.sinopsis_perfil_tienda);
                    TextView idioma = (TextView) findViewById(R.id.idioma_perfil_tienda);
                    TextView dataPublicacion = (TextView) findViewById(R.id.publicacion_data_perfil_tienda);
                    TextView genero = (TextView) findViewById(R.id.genero_perfil_tienda);
                    TextView paginas = (TextView) findViewById(R.id.paginas_perfil_tienda);
                    ImageView imagen = (ImageView) findViewById(R.id.imagen_perfil_tienda);

                    enlaceDescarga = enlaceDB;

                    titulo.setText(tituloDB);
                    setTitle(tituloDB);
                    magnetLink.setText(enlaceDB);
                    autor.setText(autorDB);
                    coleccion.setText(coleccionDB);
                    sinopsis.setText(sinopsisDB);
                    idioma.setText(idiomaDB);
                    dataPublicacion.setText(Long.toString(fechaPubliDB));
                    genero.setText(generoDB);
                    paginas.setText(Long.toString(paginasDB));
                    Glide.with(getBaseContext())
                            .load(imgDirDB)
                            .into(imagen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //opciones de bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}