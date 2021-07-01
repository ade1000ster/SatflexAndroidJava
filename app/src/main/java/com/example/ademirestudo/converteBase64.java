package com.example.ademirestudo;

import android.os.Build;
import android.support.annotation.RequiresApi;

import android.util.Base64;

public class converteBase64 {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String base64(String texto){
        byte[] arrayBytesDecodificado = Base64.decode(texto,16);/// linha alterada em 14/06/2020
        String textoStringDecodificado = new String(arrayBytesDecodificado);
        return textoStringDecodificado;
    }


}
