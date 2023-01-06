package com.example.coolrack.generalClass.SQLiteControll;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import com.example.coolrack.generalClass.Libro;

import java.util.List;


public class QueryRecord {
    @SuppressLint("StaticFieldLeak")
    public static QueryRecord sQueryRecord;

    private LibroDao libroDao;


// ---- metodos para hacer a la clase una clase singleton -------------------------------------------------------------
    private QueryRecord(Context context) {
        Context appContext = context.getApplicationContext();
        DataBaseManager database = Room.databaseBuilder(appContext, DataBaseManager.class, "book")
                .allowMainThreadQueries().build();
        libroDao = database.getLibroDao();
    }

    public static QueryRecord get(Context context) {
        if (sQueryRecord == null) {
            sQueryRecord = new QueryRecord(context);
        }
        return sQueryRecord;
    }

// ---- metodos Query -------------------------------------------------------------------------------------------------
    public void setNewBook(Libro libro) {
        libroDao.setNewBook(libro);
    }

    public void deleteBook(Libro libro) {
        libroDao.deleteBook(libro);
    }

    public void updateBook(Libro libro) {
        libroDao.updateBook(libro);
    }

    public List<Libro> getAll() {
        return libroDao.getAll();
    }

    public List<Libro> getLeyendo() {
        return libroDao.getLeyendo();
    }

    public Libro getLibro(String identifier) {
        return libroDao.getLibro(identifier);
    }

    public Libro getLibroForPath(String path){
        return libroDao.getLibroForPath(path);
    }
// --------------------------------------------------------------------------------------------------------------------
}
