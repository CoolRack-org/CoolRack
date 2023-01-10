package com.example.coolrack.Activities;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coolrack.R;
import com.example.coolrack.controlBook.EpubReaderView;
import com.example.coolrack.generalClass.GenerateBooks;
import com.example.coolrack.generalClass.Libro;
import com.example.coolrack.generalClass.SQLiteControll.QueryRecord;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

public class LecturaActivity extends AppCompatActivity {
    EpubReaderView ePubReader;
    ImageView select_copy;
    ImageView select_highlight;
    ImageView select_underline;
    ImageView select_strikethrough;
    ImageView select_search;

    ImageView select_exit;
    ImageView show_toc;
    ImageView change_theme;
    LinearLayout bottom_contextual_bar;
    Context context;

    //determina si se entro en la activity desde otra parte del mismo programa o si por lo contrario se abrio por un sitio externo
    boolean openInProgram;

    QueryRecord queryRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this.getApplicationContext();

        String epub_location = null;

        try {
            openInProgram = this.getIntent().getExtras().getBoolean("openInProgram");
        } catch (Exception e){
            openInProgram = false;
        }

        try {
            epub_location = this.getIntent().getExtras().getString("epub_location");
        } catch (Exception e) {
            epub_location = getRealPath();
        }

        controllerReader(epub_location);

    }

    // Genera la ruta necesaria para acceder al EPUB a leer
    // y modifica y/o lo añade a la base de datos en el grupo de LEYENDO
    private String getRealPath(){
        QueryRecord queryRecord =  QueryRecord.get(this);
        File file = null;
        Libro libro = null;
        String displayName = null;

        String downloadPath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));

        Cursor cursor = this.getContentResolver()
                .query(getIntent().getData(), null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            file = new File(downloadPath, displayName);
            Log.i(TAG,displayName);
            Log.i(TAG,file.getAbsolutePath());
            cursor.close();
        }


        // Si el fichero existe en la base de datos significa que tambien existe en el directorio descargas y retorna un inputStream legible por la libreria
        // en el caso contrario copiara el contenido del inputStrean en el directorio descargas y realizara el proceso de creacion de un nuevo libro
        if (file.exists()){
            libro = queryRecord.getLibroForPath(file.getAbsolutePath());

            // si el libro esta en la base de datos modifica los valores necesarios
            // y devuelve la ruta a utilizar.
            // Si por lo contrario no se encuentra entonces se inicia el proceso de añadir el libro.
            if (libro != null){
                libro.setLeyendo(true);
                queryRecord.updateBook(libro);
                Log.i(TAG, "EXISTE EN LA BASE DE DATOS Y EN EL DIRECTORIO DESCARGAS");
                return libro.getCopyBookUrl();
            }
            else {
                Log.i(TAG, "NO EXISTE EN LA BASE DE DATOS PERO SI EN EL DIRECTORIO DESCARGAS");
                return new GenerateBooks(context).addLibroInDB(file);
            }
        }
        else{
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(getIntent().getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            GenerateBooks generateBooks = new GenerateBooks(context);

            try {
                file = generateBooks.inputStreamToFile(inputStream, displayName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i(TAG,"NO EXISTE NI EN LA BASE DE DATOS NI EN EL DIRECTORIO DESCARGAS");

            return generateBooks.addLibroInDB(file);
        }
    }

    public void controllerReader(String epub_location){
        ePubReader = (EpubReaderView) findViewById(R.id.epub_reader);
        show_toc = (ImageView) findViewById(R.id.show_toc);
        change_theme = (ImageView) findViewById(R.id.change_theme);
        bottom_contextual_bar = (LinearLayout) findViewById(R.id.bottom_contextual_bar);
        select_copy = (ImageView) findViewById(R.id.select_copy);
        select_highlight = (ImageView) findViewById(R.id.select_highlight);
        select_underline = (ImageView) findViewById(R.id.select_underline);
        select_strikethrough = (ImageView) findViewById(R.id.select_strikethrough);
        select_exit = (ImageView) findViewById(R.id.select_exit);
        select_search = (ImageView) findViewById(R.id.select_search);
        select_exit = (ImageView) findViewById(R.id.select_exit);

        ePubReader.OpenEpubFile(epub_location);
        ePubReader.GotoPosition(0,(float)0);

        ePubReader.setEpubReaderListener(new EpubReaderView.EpubReaderListener() {
            @Override
            public void OnTextSelectionModeChangeListner(Boolean mode) {
                Log.d("EpubReader","TextSelectionMode"+mode+" ");
                if(mode){
                    bottom_contextual_bar.setVisibility(View.VISIBLE);
                }else{
                    bottom_contextual_bar.setVisibility(View.GONE);
                }
            }
            @Override
            public void OnPageChangeListener(int ChapterNumber,int PageNumber, float ProgressStart,float ProgressEnd) {
                Log.d("EpubReader","PageChange: Chapter:"+ChapterNumber+" PageNumber:"+PageNumber);
            }
            @Override
            public void OnChapterChangeListener(int ChapterNumber) {
                Log.d("EpubReader","ChapterChange"+ChapterNumber+" ");
            }
            @Override
            public void OnLinkClicked(String url) {
                Log.d("EpubReader","LinkClicked:"+url+" ");
            }
            @Override
            public void OnBookStartReached() {
                //Use this method to go to previous book
                //When user slides previous when opened the first page of the book
                Log.d("EpubReader","StartReached");
            }
            @Override
            public void OnBookEndReached() {
                //Use this method to go to next book
                //When user slides next when opened the last page of the book
                Log.d("EpubReader","EndReached");
            }

            @Override
            public void OnSingleTap() {
                Log.d("EpubReader","PageTapped");
            }
        });
        show_toc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ListChaptersDialog(ePubReader.GetTheme());
            }
        });
        change_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ePubReader.GetTheme()==ePubReader.THEME_LIGHT) {
                    ePubReader.SetTheme(ePubReader.THEME_DARK);
                }else{
                    ePubReader.SetTheme(ePubReader.THEME_LIGHT);
                }
            }
        });

        select_highlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ProcessTextSelection();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String SelectedText = "";
                        int ChapterNumber = -1;
                        String DataString = "";
                        try {
                            JSONObject reponse = new JSONObject(ePubReader.getSelectedText());
                            SelectedText = reponse.getString("SelectedText");
                            ChapterNumber = reponse.getInt("ChapterNumber");
                            DataString = reponse.getString("DataString");
                        }catch(Exception e){e.printStackTrace();}
                        if(ChapterNumber>=0&&!SelectedText.equals("")&&!DataString.equals("")) {
                            //Save ChapterNumber,DataString,Color,AnnotateMethod,BookLocation etc in database/Server to recreate highlight
                            if(ChapterNumber==ePubReader.GetChapterNumber())//Verify ChanpterNumber and BookLocation before suing highlight
                                ePubReader.Annotate(DataString,ePubReader.METHOD_HIGHLIGHT,"#ef9a9a");
                        }
                        ePubReader.ExitSelectionMode();
                    }
                }, 100);
            }
        });
        select_underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ProcessTextSelection();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String SelectedText = "";
                        int ChapterNumber = -1;
                        String DataString = "";
                        try {
                            JSONObject reponse = new JSONObject(ePubReader.getSelectedText());
                            SelectedText = reponse.getString("SelectedText");
                            ChapterNumber = reponse.getInt("ChapterNumber");
                            DataString = reponse.getString("DataString");
                        }catch(Exception e){e.printStackTrace();}
                        if(ChapterNumber>=0&&!SelectedText.equals("")&&!DataString.equals("")) {
                            //Save ChapterNumber,DataString,Color,BookLocation etc in database/Server to recreate highlight
                            //Toast.makeText(context, "TODO:Selected highlight:" + SelectedText, Toast.LENGTH_LONG).show();
                            if(ChapterNumber==ePubReader.GetChapterNumber())//Verify ChanpterNumber and BookLocation before suing highlight
                                ePubReader.Annotate(DataString,ePubReader.METHOD_UNDERLINE,"#ef9a9a");
                        }
                        ePubReader.ExitSelectionMode();
                    }
                }, 100);
            }
        });
        select_strikethrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ProcessTextSelection();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String SelectedText = "";
                        int ChapterNumber = -1;
                        String DataString = "";
                        try {
                            JSONObject reponse = new JSONObject(ePubReader.getSelectedText());
                            SelectedText = reponse.getString("SelectedText");
                            ChapterNumber = reponse.getInt("ChapterNumber");
                            DataString = reponse.getString("DataString");
                        }catch(Exception e){e.printStackTrace();}
                        if(ChapterNumber>=0&&!SelectedText.equals("")&&!DataString.equals("")) {
                            //Save ChapterNumber,DataString,Color,BookLocation etc in database/Server to recreate highlight
                            //Toast.makeText(context, "TODO:Selected highlight:" + SelectedText, Toast.LENGTH_LONG).show();
                            if(ChapterNumber==ePubReader.GetChapterNumber())//Verify ChanpterNumber and BookLocation before suing highlight
                                ePubReader.Annotate(DataString,ePubReader.METHOD_STRIKETHROUGH,"#ef9a9a");
                        }
                        ePubReader.ExitSelectionMode();
                    }
                }, 100);
            }
        });
        select_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ProcessTextSelection();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String SelectedText = "";
                        int ChapterNumber = -1;
                        String DataString = "";
                        try {
                            JSONObject reponse = new JSONObject(ePubReader.getSelectedText());
                            SelectedText = reponse.getString("SelectedText");
                            ChapterNumber = reponse.getInt("ChapterNumber");
                            DataString = reponse.getString("DataString");
                        }catch(Exception e){e.printStackTrace();}
                        if(ChapterNumber>=0&&!SelectedText.equals("")&&!DataString.equals("")) {
                            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                clipboard.setText(SelectedText);
                            } else {
                                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", SelectedText);
                                clipboard.setPrimaryClip(clip);
                            }
                            Toast.makeText(context,"Text Copied",Toast.LENGTH_LONG).show();
                        }
                        ePubReader.ExitSelectionMode();
                    }
                }, 100);
            }
        });
        select_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ProcessTextSelection();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String SelectedText = "";
                        int ChapterNumber = -1;
                        String DataString = "";
                        try {
                            JSONObject reponse = new JSONObject(ePubReader.getSelectedText());
                            SelectedText = reponse.getString("SelectedText");
                            ChapterNumber = reponse.getInt("ChapterNumber");
                            DataString = reponse.getString("DataString");
                        }catch(Exception e){e.printStackTrace();}
                        if(ChapterNumber>=0&&!SelectedText.equals("")&&!DataString.equals("")) {
                            if(SelectedText.length()<120) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://duckduckgo.com/"+SelectedText));
                                startActivity(browserIntent);
                            }else{
                                Toast.makeText(context,"Selected Text is too big to search",Toast.LENGTH_LONG).show();
                            }
                        }
                        ePubReader.ExitSelectionMode();
                    }
                }, 100);
            }
        });

        select_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ePubReader.ExitSelectionMode();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (openInProgram){
            finish();
        } else{
            startActivity(new Intent(this, com.example.coolrack.Activities.MainActivity.class));
        }

        return true;
    }
}