package com.br.imobiliaria.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TratamentoImagem {

    public static String converterBitMapToArrayBytes(Bitmap bitmap) {
       if(bitmap != null){
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
           byte[] byteArray = byteArrayOutputStream .toByteArray();
           return Base64.encodeToString(byteArray, Base64.DEFAULT);
       }
        return null;
    }


    public static Bitmap convertArrayBytesToBitMapImage(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
