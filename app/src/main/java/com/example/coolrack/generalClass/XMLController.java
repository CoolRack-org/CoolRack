package com.example.coolrack.generalClass;

import java.io.File;
import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;

public class XMLController {
    // if tipo == 1 create xml of Biblioteca
    // else tipo == 2 create xml of Leyendo
    private int tipo;
    private ArrayList<Book> lista;
    private String dir = "/data/";

    public XMLController(){}
    public XMLController(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public ArrayList<Book> getLista() {
        return lista;
    }
    public void setLista(ArrayList<Book> lista) {
        this.lista = lista;
    }

    public void createXML( ArrayList<Book> l){
        String nameFile = "XMLRackBooks.xml";

        File file = new File(this.dir);
        if (!file.exists()){
            file.mkdirs();
        }


    }

    public ArrayList<Book> getBooks(){

        return lista;
    }

    public void editBook(Book book){

    }
}
