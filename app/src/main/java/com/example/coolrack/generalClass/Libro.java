package com.example.coolrack.generalClass;

import nl.siegmann.epublib.domain.Book;

public class Libro {
    private String title;
    private String author;
    //private String anotation;
    private String serie;
    private String language;
    private String identifier;
    private String url;
    private String format;
    private boolean leyendo;
    private int img;



    public Libro(String title, String author, String anotation, String language, String serie,String format, String identifier,boolean leyendo, int img) {
        this.title = title;
        this.author = author;
        //this.anotation = anotation;
        this.language = language;
        this.serie = serie;
        this.format = format;
        this.identifier = identifier;
        this.leyendo = leyendo;
        this.img = img;
    }
    public Libro(String title, String author, String serie,String format, int img) {
        this.title = title;
        this.author = author;
        this.serie = serie;
        this.format = format;
        this.img = img;
    }
    public Libro() {}


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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
