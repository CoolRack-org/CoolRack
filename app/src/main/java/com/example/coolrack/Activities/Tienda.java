package com.example.coolrack.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coolrack.R;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;
import com.example.coolrack.generalClass.adaptadores.AdaptadorItemTienda;
import com.example.coolrack.generalClass.pojos.Libro;

import java.util.List;

public class Tienda extends AppCompatActivity implements AdaptadorItemTienda.OnItemClickListener {

    private RecyclerView recyclerView;
    private AdaptadorItemTienda bookAdapter;
    private List<Libro> books;

    private QueryRecord queryRecord = QueryRecord.get(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        setTitle("La gran biblioteca");

        recyclerView = findViewById(R.id.recyclerViewTienda);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // crea 2 columnas para que en la lista de cardViews se vean de dos en dos
        GridLayoutManager gridLayoutManager = new
                GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        books = queryRecord.getAll();
        // Agregar los libros a la lista de books

        bookAdapter = new AdaptadorItemTienda(books);
        recyclerView.setAdapter(bookAdapter);

        // Crea un boton con forma de flecha el la toolbar
        // esta se usara para retroceder
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //opciones de bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}