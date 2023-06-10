package com.example.coolrack.generalClass.pojos;

public class LibroTienda {
    private String autor;
    private String autosense;
    private String coleccion;
    private String colsense;
    private String enlaces;
    private String nodoName;
    private String estado;
    private Long fecha_publi;
    private String genero;
    private String idioma;
    private String img;
    private String sinopsis;
    private Long paginas;
    private String titulo;

    // constructor completo
    public LibroTienda() {}

    // constructor para el cardView
    public LibroTienda(String nodoName, String img) {
       this.nodoName = nodoName;
        this.img = img;
    }

    public String getNodoName() {
        return nodoName;
    }

    public void setNodoName(String nodoName) {
        this.nodoName = nodoName;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getFecha_publi() {
        return fecha_publi;
    }

    public void setFecha_publi(Long fecha_publi) {
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

    public Long getPaginas() {
        return paginas;
    }

    public void setPaginas(Long paginas) {
        this.paginas = paginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
