package com.example.coolrack.generalClass.SQLiteControll;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coolrack.generalClass.pojos.Libro;

@Database(entities = {Libro.class}, version = 2)
public abstract class DataBaseManager extends RoomDatabase {

    public abstract LibroDao getLibroDao();


}
