package com.example.ademirestudo;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Base64;

public class converteBase64 {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String base64(String texto){
        byte[] arrayBytesDecodificado = Base64.getDecoder().decode(texto);
        String textoStringDecodificado = new String(arrayBytesDecodificado);
        return textoStringDecodificado;
    }


}
