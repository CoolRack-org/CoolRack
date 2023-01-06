package com.example.coolrack.generalClass.SQLiteControll;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.coolrack.generalClass.Libro;

@Database(entities = {Libro.class}, version = 1)
public abstract class DataBaseManager extends RoomDatabase {

    public abstract LibroDao getLibroDao();


}
