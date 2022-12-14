package com.example.coolrack.Activities;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coolrack.BuildConfig;
import com.example.coolrack.fragments.Biblioteca;
import com.example.coolrack.fragments.Informacion;
import com.example.coolrack.fragments.Leyendo;
import com.example.coolrack.R;
import com.example.coolrack.generalClass.GenerateBooks;
import com.example.coolrack.generalClass.XMLControll.XMLController;
import com.google.android.material.navigation.NavigationView;

import nl.siegmann.epublib.domain.ManifestItemProperties;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //valor que indica si el usuario acepto el permiso
    int REQUEST_CODE = 200;

    //menu
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    //declaracion de fragmentos y transacion
    //despues se definiran con su respectiva clase para la navegacion
    //fragmentLeyendo == Fragmento inicial
    FragmentTransaction transactioni;
    Fragment fragmentLeyendo, fragmentBiblioteca, fragmentInformacion;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //verifica si la aplicacion tiene permisos, si no los tiene los pide y asigna
        verificarPermisos();

        GenerateBooks gb = new GenerateBooks();
        XMLController pepe = new XMLController();
        pepe.createXML(gb.getLibros(this.getApplicationContext()),this.getApplicationContext());

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
        //esto despues se carga en el metedo onNavigationItemSelect para el cambio de dicho fragmento
        fragmentLeyendo = new Leyendo();
        fragmentInformacion = new Informacion();
        fragmentBiblioteca = new Biblioteca();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,fragmentLeyendo).commit();

        try {
            Bundle bundle = getIntent().getExtras();
            int ID_BACK = (int) bundle.getSerializable("direccionAnterior");

            if (ID_BACK != 0){
                gestorTransiciones(ID_BACK);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("no existe Activity anterior");
        }


    }

//-------------------------------------------------------------------------------------------------------------
    // Cierra el menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //permite la navegacion a las opciones del menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        gestorTransiciones(item.getItemId());
        return false;
    }

    //realiza la transicion entre fragmentos
    public void gestorTransiciones(int id){
        transactioni = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.nav_leyendo:
                transactioni.replace(R.id.frame_layout,fragmentLeyendo).commit();
                break;
            case R.id.nav_biblioteca:
                transactioni.replace(R.id.frame_layout,fragmentBiblioteca).commit();;
                break;
            case R.id.nav_info:
                transactioni.replace(R.id.frame_layout,fragmentInformacion).commit();;
                break;
        }
    }
//-------------------------------------------------------------------------------------------------------------

    // Pide los permisos necesarios para el comodo funcionamiento del programa al usuario.
    // Los permisos necesarios para el completo funcionamiento del programa es para
    // buscar y guardar los nuevos libros descargados y no tener que cargarlo uno a uno
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void verificarPermisos(){

               /* int permisosManagerExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
       int permisosWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisosRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
        if (*//*permisosWrite != PackageManager.PERMISSION_GRANTED && permisosRead != PackageManager.PERMISSION_GRANTED  && *//*permisosManagerExternalStorage != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE,ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION },REQUEST_CODE);
            startActivityForResult(intent, REQUEST_CODE);
        }*/

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            if(!Environment.isExternalStorageManager()){
                try {
                    Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivity(intent);
                } catch (Exception ex){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
            }
        } else {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
   }



//-------------------------------------------------------------------------------------------------------------
}