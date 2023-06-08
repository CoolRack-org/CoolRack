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
    private DatabaseReference databaseReference;
    private static final int PAGE_SIZE = 10;
    private int currentPage = 1;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    /*private int PAGE_SIZE = 10;
    private Query query; // FireBase Query class
    private int currentPage = 0;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private int totalCount = 0; // número total de resultados en la consulta*/

    //private QueryRecord queryRecord = QueryRecord.get(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        setTitle("La gran biblioteca");

        databaseReference = FirebaseDatabase.getInstance().getReference("libros");
        recyclerView = findViewById(R.id.recyclerViewTienda);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // crea 2 columnas para que en la lista de cardViews se vean de dos en dos
        GridLayoutManager gridLayoutManager = new
                GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        librosList = new ArrayList<>();
        adapter = new AdaptadorItemTienda(librosList);
        recyclerView.setAdapter(adapter);

// Agregamos un listener al RecyclerView para detectar cuando se llega al final de la lista
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Si se llegó al final de la lista y no se están cargando más datos, cargamos la siguiente página
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading && !isLastPage) {
                    loadMoreItems();
                    //loadNextPage();
                }
            }
        });

        // Cargamos la primera página de datos
        //loadNextPage();
        loadMoreItems();

        //bookAdapter = new AdaptadorItemTienda(books);
        //recyclerView.setAdapter(bookAdapter);

        // crear la consulta de Firebase
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("libros");
        int pageNumber = 1; // el número de página actual (por ejemplo, 1 para la primera página)
        int pageSize = 50; // el tamaño de la página (por ejemplo, 50 objetos por página)
        int startIndex = (pageNumber - 1) * pageSize; // el índice de inicio para la página actual

        query = ref.orderByKey().startAt(String.valueOf(startIndex)).limitToFirst(pageSize); // la consulta para la página actual
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LibroTienda> libros = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    int epl_id = childSnapshot.child("epl_id").getValue(Integer.class); // obtener la clave del objeto (la ID)
                    String imgDir = childSnapshot.child("imgDir").getValue(String.class); // obtener el valor del campo "titulo" como un String

                    LibroTienda libro = new LibroTienda(epl_id, imgDir); // crear una instancia de Libro con los datos obtenidos
                    libros.add(libro); // agregar el libro a la lista de libros
                }

                // aquí puedes hacer lo que quieras con la lista de libros
                bookAdapter = new AdaptadorItemTienda(libros); // actualizar el adaptador con la lista de libros
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // manejar errores
            }
        });

        // agregar un listener para la paginación
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == itemCount - 1) {
                    // el usuario ha llegado al final de la lista, cargar más datos
                    // aquí puedes llamar a tu método de carga de datos para cargar más datos desde Firebase Realtime Database
                    loadMoreItems();
                }
            }
        });

        // cargar los primeros resultados
        loadMoreItems();*/

        //books = queryRecord.getAll();
        // Agregar los libros a la lista de books

       // recyclerView.setAdapter(bookAdapter);

        // Crea un boton con forma de flecha el la toolbar
        // esta se usara para retroceder
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    /*private void loadNextPage() {
        isLoading = true;

        // Realizamos la consulta a Firebase para obtener la siguiente página de datos
        Query query = databaseReference.orderByChild("epl_id").startAt(String.valueOf(currentPage)).limitToFirst(PAGE_SIZE);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Si existen datos en la página, agregamos los nuevos datos a la lista y actualizamos el adaptador
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        int epl_id = dataSnapshot.child("epl_id").getValue(Integer.class); // obtener la clave del objeto (la ID)
                        String titulo = dataSnapshot.child("titulo").getValue(String.class); // obtener el valor del campo "titulo" como un String
                        LibroTienda libro = new LibroTienda(epl_id, titulo); // crear una instancia de Libro con los datos obtenidos
                        librosList.add(libro); // agregar el libro a la lista de libros
                        Log.i(TAG, titulo);
                    }
                    adapter.notifyDataSetChanged();

                    currentPage++;
                } else {
                    // Si no existen datos en la página, se llegó al final de la lista
                    isLastPage = true;
                }

                isLoading = false;
            }

            *//*public Query startQueryPagination(){
                int startIndex = librosList.size(); // el índice de inicio para la página siguiente

                Query query = databaseReference.orderByKey().startAt(String.valueOf(startIndex)).limitToFirst(PAGE_SIZE); // la consulta para la página siguiente
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<LibroTienda> nuevosLibros = new ArrayList<>();

                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            int epl_id = childSnapshot.child("epl_id").getValue(Integer.class); // obtener la clave del objeto (la ID)
                            String titulo = childSnapshot.child("titulo").getValue(String.class); // obtener el valor del campo "titulo" como un String
                            LibroTienda libro = new LibroTienda(epl_id, titulo); // crear una instancia de Libro con los datos obtenidos
                            nuevosLibros.add(libro); // agregar el libro a la lista de libros
                            Log.i(TAG, titulo);
                        }

                        librosList.addAll(nuevosLibros); // agregar los nuevos libros a la lista de libros existente
                        adapter.setLibros(librosList); // actualizar el adaptador con la lista de libros
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // manejar errores
                    }
                });
            }*//*

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("LibrosActivity", "Error al obtener los datos de Firebase");
                isLoading = false;
            }
        });
    }*/

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
                    int epl_id = childSnapshot.child("epl_id").getValue(Integer.class); // obtener la clave del objeto (la ID)
                    String imgDir = childSnapshot.child("imgDir").getValue(String.class); // obtener el valor del campo "titulo" como un String
                    LibroTienda libro = new LibroTienda(epl_id, imgDir); // crear una instancia de Libro con los datos obtenidos
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