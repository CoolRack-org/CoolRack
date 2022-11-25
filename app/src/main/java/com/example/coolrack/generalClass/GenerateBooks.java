package com.example.coolrack.generalClass;

import android.os.Environment;

import com.example.coolrack.R;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

public class GenerateBooks {

    public GenerateBooks(){}

    public ArrayList<Libro> getLibros() {
        ArrayList<Libro> listBook = null;

        try {
        listBook = new ArrayList<>();
        //saca la ruta de Descargas y lo usa en el objeto dir
        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        File dir = new File(path);

        EpubReader er = new EpubReader();
        for (File f :dir.listFiles()/*dir.listFiles(new FileFilter() {
            //Filtro que solo deja pasar los file que tengam formato epub
            //los otros los desecha y no son tomados en cuenta para el programa;
            @Override
            public boolean accept(File file) {
                StringBuilder sb = new StringBuilder(file.getAbsolutePath());
                String format = String.valueOf(sb.reverse());
                format = format.substring(0,5);
                sb = new StringBuilder(format);
                format = String.valueOf(sb.reverse());

                if(format == ".epub")
                    return false;
                else
                    return true;
            }
        })*/){

            try {
                Book b = er.readEpub(new FileInputStream(f.getAbsolutePath()));
                Libro l = new Libro();
                if (b.getTitle().equals("Canallas"))
                    l.setLeyendo(true);

                l.setTitle(b.getTitle());
                l.setAuthor(b.getMetadata().getAuthors().get(0).getFirstname()+" "+b.getMetadata().getAuthors().get(0).getLastname());
                //l.setSerie();
                l.setLanguage(b.getMetadata().getLanguage());
                l.setIdentifier(b.getMetadata().getIdentifiers().get(0).getValue());
                l.setUrl(f.getAbsolutePath());
                l.setFormat(b.getMetadata().getFormat());

                listBook.add(l);
                System.out.println(f.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
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
