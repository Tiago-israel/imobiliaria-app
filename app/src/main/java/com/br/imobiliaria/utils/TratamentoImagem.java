package com.br.imobiliaria.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TratamentoImagem {

    public static byte[] converterBitMapToArrayBytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static Bitmap convertArrayBytesToBitMapImage(byte[] bytes) {

        InputStream inputStream = new ByteArrayInputStream(bytes);


        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,1024);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
       // options.
       /* Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;*/

        Bitmap bitMapImage = BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        return bitMapImage;
    }

}
