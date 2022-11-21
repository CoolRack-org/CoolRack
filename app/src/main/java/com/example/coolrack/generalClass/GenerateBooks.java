package com.example.coolrack.generalClass;

import android.os.Environment;

import com.example.coolrack.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

public class GenerateBooks {
    private String url;
    private String filtro;

    public GenerateBooks(){}
    public GenerateBooks(String url) {
        this.url = url;
    }
    public GenerateBooks(String url, String filtro) {
        this.url = url;
        this.filtro = filtro;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public ArrayList<Libro> getLibros() {
        ArrayList<Libro> listBook = null;

        //try {
        listBook = new ArrayList<>();
        //saca la ruta de Descargas y lo usa en el objeto dir
        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        File dir = new File(path);

        EpubReader er = new EpubReader();
        for (File f :dir.listFiles()){
            try {
                Book b = er.readEpub(new FileInputStream(f.getAbsolutePath()));
                Libro l = new Libro();

                l.setTitle(b.getTitle());
                l.setAuthor(b.getMetadata().getAuthors()+"");
                l.setFormat(b.getMetadata().getFormat());
                l.setImg(R.drawable.ic_launcher_background);
                l.setSerie("testSerie"); //TEMPORAL
                l.setUrl(f.getAbsolutePath());

                listBook.add(l);
                System.out.println(f.getAbsolutePath());


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //}catch (Exception e){
        //    e.printStackTrace();
        //}finally {
        return listBook;
        // }

    }
}
