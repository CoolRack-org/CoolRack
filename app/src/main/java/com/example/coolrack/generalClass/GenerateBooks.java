package com.example.coolrack.generalClass;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.epub.EpubWriter;

public class GenerateBooks {

    public GenerateBooks(){}

    public ArrayList<Libro> getLibros(Context context) {
        ArrayList<Libro> listBook = null;

        try {
        listBook = new ArrayList<>();
        //saca la ruta de Descargas y lo usa en el objeto dir
        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        File dir = new File(path);

        EpubReader er = new EpubReader();
        for (File f :dir.listFiles()){

            try {
                Book b = er.readEpub(new FileInputStream(f.getAbsolutePath()));
                Libro l = new Libro();
                if (b.getTitle().equals("Las madres"))
                    l.setLeyendo(true);

                l.setTitle(b.getTitle());
                l.setAuthor(b.getMetadata().getAuthors().get(0).getFirstname()+" "+b.getMetadata().getAuthors().get(0).getLastname());
                //l.setSerie();
                l.setLanguage(b.getMetadata().getLanguage());
                l.setIdentifier(b.getMetadata().getIdentifiers().get(0).getValue());
                l.setUrl(f.getAbsolutePath());
                l.setFormat(b.getMetadata().getFormat());
                l.setImg(b.getCoverImage().getInputStream().read());

                listBook.add(l);
                createBook(b,f.getName(),context);
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

    // Copia los nuevos epubs analizados y los pega en "coleccionLibros"
    // dentro del direcctorio personal del programa
    public void createBook(Book libro,String fileName, Context context){
        File dirPrivate = context.getFilesDir();
        File coleccionLibros = new File(dirPrivate,"bookCollection");

        if (!coleccionLibros.exists()){
            coleccionLibros.mkdir();
        }

        File libroCopy =  new File(coleccionLibros, fileName);

        if (!libroCopy.exists()){
            try {
                EpubWriter epubWriter = new EpubWriter();
                epubWriter.write(libro, new FileOutputStream(libroCopy.getAbsolutePath()));
                System.out.println(libro.getTitle()+"\n\n");
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
        for (File file : coleccionLibros.listFiles()){
            System.out.println(file.getAbsolutePath());
        }
    }
}
