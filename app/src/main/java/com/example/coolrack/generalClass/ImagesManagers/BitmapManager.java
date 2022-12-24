package com.example.coolrack.generalClass.ImagesManagers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapManager {

    public BitmapManager(){}

//---- String/Bitmap -------------------------------------------------------------------------------------------------
    // Pasa traduce el contenido del BitMap a string para poder almacenarlo en el XML
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    // Traduce el String del XML que representa el bitmap a digo tipo para poder usarlo en un ImagenView
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

//---- Compress/Uncompress -------------------------------------------------------------------------------------------------
    // Comprime el bitemap, que despues se seteara en el pojo Libro
    // Gracias a esta compresion es posible enviar el pojo completo, de lo contrario no seria posible
    // enviar todos los datos del pojo a la actividade de PerfilLibro
    public byte[] bitemapCompress(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        return bytes;
    }

    public Bitmap bitmapUncompress(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
