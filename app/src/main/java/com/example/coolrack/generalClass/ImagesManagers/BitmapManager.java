package com.example.coolrack.generalClass.ImagesManagers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapManager {

    public BitmapManager(){}

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
