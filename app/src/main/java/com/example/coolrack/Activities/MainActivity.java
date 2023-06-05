package com.example.coolrack.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.coolrack.BuildConfig;
import com.example.coolrack.R;
import com.example.coolrack.Activities.ui.main.MainFragments.FatherMainFragment;
import com.example.coolrack.Activities.ui.main.MainFragments.Favoritos;
import com.example.coolrack.Activities.ui.main.MainFragments.Leidos;
import com.example.coolrack.Activities.ui.main.MainFragments.Leyendo;
import com.example.coolrack.Activities.ui.main.MainFragments.Papelera;
import com.example.coolrack.Activities.ui.main.MainFragments.ParaLeer;
import com.example.coolrack.generalClass.MenuOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //valor que indica si el usuario acepto el permiso
    int REQUEST_CODE = 200;

    //menu
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Menu fragmentsOptionsMenu;

    //declaracion de fragmentos y transacion
    //despues se definiran con su respectiva clase para la navegacion
    //fragmentLeyendo == Fragmento inicial
    FragmentTransaction transactioni;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //verifica si la aplicacion tiene permisos, si no los tiene los pide y asigna
        verificarPermisos();

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        //Instancia de diseño de cajón para alternar el icono de menú para abrir
        //Cajón y botón de retroceso para cerrar el cajón
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        //Pase el botón de alternancia Abrir y Cerrar para el detector de diseño de cajón
        //Para alternar el botón
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // para que el icono del cajón de navegación aparezca siempre en la barra de acciones
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView=(NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,new Leyendo()).commit();

    }

//---- Comportamiento de los menus ---------------------------------------------------------------------------------------------------------

    // se ejecuta cuando el usuario selecciona un item del menu de navegacion entre ventanas(fragments)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        gestorTransiciones(item.getItemId());
        return false;
    }

    //realiza la transicion entre fragmentos
    public void gestorTransiciones(int id){
        transactioni = getSupportFragmentManager().beginTransaction();

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
                startActivity(new Intent(this, com.example.coolrack.Activities.Tienda.class));
                break;
            // ventana de opciones de configuracio de la aplicacion
            case R.id.nav_opciones:
                startActivity(new Intent(this, com.example.coolrack.Activities.SettingsActivity.class));
                break;
            // ventana de inforrmacion de la aplicacion
            case R.id.nav_info:
                startActivity(new Intent(this, com.example.coolrack.Activities.InformacionActivity.class));
                break;
        }
        // cierra el menu despues de seleccionar una opcion con una animaciuon a la izquierda
        this.drawerLayout.closeDrawer(Gravity.LEFT);
    }

    // Crea el menu de opciones de los activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        fragmentsOptionsMenu = menu;
        getMenuInflater().inflate(R.menu.fragments_menu, menu);
        fragmentsOptionsMenu.findItem(R.id.optionDelate).setVisible(false);
        return true;
    }

    //  Dicta como actuan las opciones del ActivityBar al ser ejecutadas,
    //  en concreto las opciones situadas a la derecha.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Abre el menu de de navegacion entre fragments, de caso contrario  ejecuta una de las otras opciones del "Bar"
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;

        }else{
            switch (item.getItemId()) {
                case R.id.optionDelate:
                    new MenuOptions(this).buttonDelate(getLayoutInflater());
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }}
    }

//-------------------------------------------------------------------------------------------------------------

    // Pide los permisos necesarios para el comodo funcionamiento del programa al usuario.
    // Los permisos necesarios para el completo funcionamiento del programa es para
    // buscar y guardar los nuevos libros descargados y no tener que cargarlo uno a uno
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void verificarPermisos(){

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