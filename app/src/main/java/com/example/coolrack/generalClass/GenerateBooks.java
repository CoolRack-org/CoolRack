package com.example.coolrack.generalClass;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
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

    public ArrayList<Book> getLibros() {
        ArrayList<Book> listBook = null;

        try {
            listBook = new ArrayList<>();
            //saca la ruta de Descargas y lo usa en el objeto dir
            String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            File dir = new File(path);

            EpubReader er = new EpubReader();
            for (File f :dir.listFiles()){
                try {
                    Book b = er.readEpub(new FileInputStream(f.getAbsolutePath()));
                    listBook.add(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return listBook;
        }

    }
}
