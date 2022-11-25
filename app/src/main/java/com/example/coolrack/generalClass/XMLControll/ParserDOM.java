package com.example.coolrack.generalClass.XMLControll;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ParserDOM {
    //private Context context;
    private Document document = null;

    //public ParserDOM (Context context){this.context=context;}
    public ParserDOM(){}

    public Document getDocument (InputStream inputStream){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(inputStream);
            document = documentBuilder.parse(inputSource);

        }catch (ParserConfigurationException e){
            Log.e("ERROR",e.getMessage());
            return null;
        }catch (SAXException e){
            Log.e("ERROR",e.getMessage());
            return null;
        }finally {
            return document;
        }
    }

    public String getValue(Element item, String name){
        NodeList nodes =item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }

    private final String getTextNodeValue(Node node){
        Node hijo;

        if (node != null){
            if (node.hasChildNodes()){
                hijo= node.getFirstChild();

                while (hijo != null){
                    if (hijo.getNodeType() ==Node.TEXT_NODE){
                        return hijo.getNodeValue();
                    }
                    hijo =hijo.getNextSibling();
                }
            }
        }
        return "";
    }
}
