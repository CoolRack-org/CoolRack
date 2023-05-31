package com.example.coolrack.generalClass.pojos;

public class LibroTienda {
    private String autor;
    private String autosense;
    private String coleccion;
    private String colsense;
    private String enlaces;
    private int epl_id;
    private String estado;
    private String fecha_publi;
    private String genero;
    private String idioma;
    private String img;
    private String sinopsis;
    private int paginas;
    private String titulo;

    // constructor completo
    public LibroTienda(String autor, String autosense, String coleccion, String colsense, String enlaces, int epl_id, String estado, String fecha_publi, String genero, String idioma, String img, String sinopsis, int paginas, String titulo) {
        this.autor = autor;
        this.autosense = autosense;
        this.coleccion = coleccion;
        this.colsense = colsense;
        this.enlaces = enlaces;
        this.epl_id = epl_id;
        this.estado = estado;
        this.fecha_publi = fecha_publi;
        this.genero = genero;
        this.idioma = idioma;
        this.img = img;
        this.sinopsis = sinopsis;
        this.paginas = paginas;
        this.titulo = titulo;
    }

    // constructor para el cardView
    public LibroTienda(int epl_id, String img, String titulo) {
        this.epl_id = epl_id;
        this.img = img;
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutosense() {
        return autosense;
    }

    public void setAutosense(String autosense) {
        this.autosense = autosense;
    }

    public String getColeccion() {
        return coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }

    public String getColsense() {
        return colsense;
    }

    public void setColsense(String colsense) {
        this.colsense = colsense;
    }

    public String getEnlaces() {
        return enlaces;
    }

    public void setEnlaces(String enlaces) {
        this.enlaces = enlaces;
    }

    public int getEpl_id() {
        return epl_id;
    }

    public void setEpl_id(int epl_id) {
        this.epl_id = epl_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_publi() {
        return fecha_publi;
    }

    public void setFecha_publi(String fecha_publi) {
        this.fecha_publi = fecha_publi;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
