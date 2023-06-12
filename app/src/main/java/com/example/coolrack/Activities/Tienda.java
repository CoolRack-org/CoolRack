package com.example.coolrack.Activities;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolrack.R;
import com.example.coolrack.generalClass.adaptadores.AdaptadorItemTienda;
import com.example.coolrack.generalClass.pojos.LibroTienda;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tienda extends AppCompatActivity implements AdaptadorItemTienda.OnItemClickListener {

    private RecyclerView recyclerView;
    private AdaptadorItemTienda adapter;
    private List<LibroTienda> librosList;

    // Datos para la paginacion
    private boolean isLastPage = false;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        setTitle(R.string.mainMenu_biblioteca_publica);

        recyclerView = findViewById(R.id.recyclerViewTienda);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // crea 2 columnas para que en la lista de cardViews se vean de dos en dos
        GridLayoutManager gridLayoutManager = new
                GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        librosList = new ArrayList<>();
        adapter = new AdaptadorItemTienda(librosList, this);
        recyclerView.setAdapter(adapter);

        // Agregamos un listener al RecyclerView para detectar cuando se llega al final de la lista
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Si se llegó al final de la lista y no se están cargando más datos, cargamos la siguiente página
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading && !isLastPage) {
                    loadMoreItems();
                }
            }
        });

        // Cargamos la primera página de datos
        loadMoreItems();

        // Crea un boton con forma de flecha el la toolbar
        // esta se usara para retroceder
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadMoreItems() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("libros");
        int pageSize = 50; // el tamaño de la página (por ejemplo, 50 objetos por página)
        int startIndex = librosList.size(); // el índice de inicio para la página siguiente

        Query query = ref.orderByKey().startAt(String.valueOf(startIndex)).limitToFirst(pageSize); // la consulta para la página siguiente
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LibroTienda> nuevosLibros = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String nodoName = childSnapshot.getKey();
                    String imgDir = childSnapshot.child("imgDir").getValue(String.class); // obtener el valor del campo "titulo" como un String

                    LibroTienda libro = new LibroTienda(nodoName, imgDir); // crear una instancia de Libro con los datos obtenidos
                    nuevosLibros.add(libro); // agregar el libro a la lista de libros
                    Log.i(TAG, imgDir);
                }

                librosList.addAll(nuevosLibros); // agregar los nuevos libros a la lista de libros existente
                adapter.setLibros(librosList); // actualizar el adaptador con la lista de libros
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // manejar errores
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