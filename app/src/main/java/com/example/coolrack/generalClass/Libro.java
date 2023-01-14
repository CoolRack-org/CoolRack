package com.example.coolrack.generalClass;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "book")
public class Libro  implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "identifier")
    private String identifier;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    //@ColumnInfo(name = "anotation")
    //private String anotation;

    @ColumnInfo(name = "serie")
    private String serie;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "urlOriginal")
    private String originalBookUrl;

    @ColumnInfo(name = "urlCopy")
    private String copyBookUrl;

    @ColumnInfo(name = "format")
    private String format;

    @ColumnInfo(name = "leyendo")
    private boolean leyendo;

    @ColumnInfo(name = "papelera")
    private boolean papelera;

    @ColumnInfo(name = "favorito")
    private boolean favorito;

    @ColumnInfo(name = "leido")
    private boolean leido;

    @ColumnInfo(name = "paraLeer")
    private boolean paraLeer;

    @ColumnInfo(name = "img")
    private byte[] img;

    @Ignore
    public Libro() {}

    public Libro(@NonNull String identifier, String title, String author, String serie, String language, String originalBookUrl,
                 String copyBookUrl, String format, boolean leyendo, boolean papelera, boolean favorito, boolean leido, boolean paraLeer, byte[] img) {
        this.identifier = identifier;
        this.title = title;
        this.author = author;
        this.serie = serie;
        this.language = language;
        this.originalBookUrl = originalBookUrl;
        this.copyBookUrl = copyBookUrl;
        this.format = format;
        this.leyendo = leyendo;
        this.papelera = papelera;
        this.favorito = favorito;
        this.leido = leido;
        this.paraLeer = paraLeer;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    public String getAnotation() {
//        return anotation;
//    }
//
//    public void setAnotation(String anotation) {
//        this.anotation = anotation;
//    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOriginalBookUrl() {
        return originalBookUrl;
    }

    public void setOriginalBookUrl(String originalBookUrl) {
        this.originalBookUrl = originalBookUrl;
    }

    public String getCopyBookUrl() {
        return copyBookUrl;
    }

    public void setCopyBookUrl(String copyBookUrl) {
        this.copyBookUrl = copyBookUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean getLeyendo() {
        return leyendo;
    }

    public void setLeyendo(boolean leyendo) {
        this.leyendo = leyendo;
    }

    public boolean getPapelera() {
        return papelera;
    }

    public void setPapelera(boolean papelera) {
        this.papelera = papelera;
    }

    public boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean getLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public boolean getParaLeer() {
        return paraLeer;
    }

    public void setParaLeer(boolean paraLeer) {
        this.paraLeer = paraLeer;
    }
}
