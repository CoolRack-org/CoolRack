package com.example.coolrack;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //menu
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    //declaracion de fragmentos y transacion
    //fragmentLeyendo == Fragmento inicial
    FragmentTransaction transactioni;
    Fragment fragmentLeyendo, fragmentInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView=(NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        //Creacion de objetos fragment y transaction
        fragmentLeyendo = new Leyendo();
        fragmentInformacion = new Informacion();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,fragmentLeyendo).commit();

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        transactioni = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()){
            case R.id.nav_leyendo:
                transactioni.replace(R.id.frame_layout,fragmentLeyendo).commit();
                Toast.makeText(this,"Leyendo",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_biblioteca:
                Toast.makeText(this,"Biblioteca",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_info:
                transactioni.replace(R.id.frame_layout,fragmentInformacion).commit();
                Toast.makeText(this,"informacion",Toast.LENGTH_LONG).show();
                break;
        }

        return false;
    }
}