package com.example.coolrack.generalClass.XMLControll;

import android.content.Context;

import android.graphics.Bitmap;
import android.util.Xml;

import com.example.coolrack.generalClass.Libro;

import java.io.File;
import java.io.FileInputStream;

import java.io.OutputStreamWriter;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class XMLController {
    // if tipo == 1 create xml of Biblioteca
    // else tipo == 2 create xml of Leyendo
    private int tipo;
    private String nameFile = "XMLBooks.xml";

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

    public void createXML(ArrayList<Libro> lista, Context context){
        try {
            //TEMPORAL TEST ALMACEN PRIVADO
            File dir= context.getFilesDir();
            File fileXML = new File(dir,this.nameFile);
            if (fileXML.exists() != true){
                fileXML.createNewFile();
            }
            else{
                fileXML.delete();
                fileXML.createNewFile();
            }

            //Inicializo el serializador de XML, esto almacenara los datos para
            //posteriormente pasarlos a un formato XML
            XmlSerializer xmlSerializer = Xml.newSerializer();
            OutputStreamWriter osWriter = new OutputStreamWriter(context.openFileOutput(fileXML.getName().toString(),Context.MODE_APPEND));
            xmlSerializer.setOutput(osWriter);
            xmlSerializer.startDocument("UTF-8",false);

//            System.out.println(fileXML.getAbsolutePath());

            //Creacion de tag root
            xmlSerializer.startTag("","root");
            for (Libro l : lista){

                String titulo = l.getTitle();
                String autor = l.getAuthor();
                int imagen = l.getImg();
                //String serie = l.getSerie();
                String lenguaje = l.getLanguage();
                String identifier = l.getIdentifier();
                String format = l.getFormat();
                String url = l.getUrl();
                String leyendo = String.valueOf(l.getLeyendo());

                xmlSerializer.startTag("","book");

                xmlSerializer.startTag("","titulo");
                xmlSerializer.text(titulo);
                xmlSerializer.endTag("","titulo");

                xmlSerializer.startTag("","autor");
                xmlSerializer.text(autor);
                xmlSerializer.endTag("","autor");

/*                xmlSerializer.startTag("","imagen");
                xmlSerializer.text(String.valueOf(imagen)); //Falta Implementar
                xmlSerializer.endTag("","imagen");

                xmlSerializer.startTag("","serie");
                xmlSerializer.text(serie); //Falta Implementar
                xmlSerializer.endTag("","serie");*/

                xmlSerializer.startTag("","lenguaje");
                xmlSerializer.text(lenguaje);
                xmlSerializer.endTag("","lenguaje");

                xmlSerializer.startTag("","identifier");
                xmlSerializer.text(identifier);
                xmlSerializer.endTag("","identifier");

                xmlSerializer.startTag("","format");
                xmlSerializer.text(format);
                xmlSerializer.endTag("","format");

                xmlSerializer.startTag("","url");
                xmlSerializer.text(url);
                xmlSerializer.endTag("","url");

                //TAG LEYENDO TEMPORAL
                xmlSerializer.startTag("","leyendo");
                xmlSerializer.text(leyendo);
                xmlSerializer.endTag("","leyendo");

                xmlSerializer.endTag("","book");

            }
            //Cierre tag root
            xmlSerializer.endTag("","root");

            xmlSerializer.endDocument();
            osWriter.close();


        } catch (Exception ex){
            ex.printStackTrace();
        }


    }

    //TIPO 1 Listado general
    //TIPO 2 Listado leyendo
    public ArrayList<Libro> getBooks(Context context,int tipo){
        ArrayList<Libro> lista = new ArrayList<>();
        try {
            //TEMPORAL TEST ALMACEN PRIVADO
            File dir= context.getFilesDir();
            File  fileXML= new File(dir,this.nameFile);

            FileInputStream fis = context.openFileInput(this.nameFile);

            ParserDOM parser = new ParserDOM();
            Document document = parser.getDocument(fis);

            NodeList nodeList = document.getElementsByTagName("book");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) node;
                    boolean leyendo = Boolean.parseBoolean(parser.getValue(eElement,"leyendo"));

                    if (tipo==2 && leyendo){
                        lista.add(this.getValues(parser,eElement));
                    }
                    else if (tipo == 1){
                        lista.add(this.getValues(parser,eElement));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return lista;
    }

    private Libro getValues(ParserDOM parser, Element eElement){
        Libro l = new Libro();
        l.setTitle(parser.getValue(eElement,"titulo"));
        l.setAuthor(parser.getValue(eElement,"autor"));
        //l.setSerie();
        l.setLanguage(parser.getValue(eElement,"lenguaje"));
        l.setIdentifier(parser.getValue(eElement,"identifier"));
        l.setUrl(parser.getValue(eElement,"url"));
        l.setFormat(parser.getValue(eElement,"format"));
        l.setLeyendo(Boolean.valueOf(parser.getValue(eElement, "leyendo")));
        //l.setImg(Bitmap.(parser.getValue(eElement, "imagen")));

        return l;
    }



    public void addBook(Libro l, Context context){
        try {
            ArrayList<Libro> lista = this.getBooks(context,1);
            lista.add(l);
            this.createXML(lista,context);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editBook(Libro editeBook, Context context){
        try {
            ArrayList<Libro> lista = this.getBooks(context,1);

            for (int i=0; i<lista.size(); i++){
                String id = lista.get(i).getIdentifier();
                if (id.equals(editeBook.getIdentifier()) ){
                    lista.set(i,editeBook);
                    this.createXML(lista, context);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
