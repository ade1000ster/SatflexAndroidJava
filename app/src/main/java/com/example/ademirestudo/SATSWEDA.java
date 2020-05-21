package com.example.ademirestudo;
import android.content.Context;

import  br.com.sweda.satserial.Java_DLLSAT;
public class SATSWEDA {
    Java_DLLSAT sat;
    Context ctx;

    public SATSWEDA(Context ctx){
        this.ctx = ctx;
        sat = new Java_DLLSAT(ctx);
    }

    public Java_DLLSAT getSat() {
        return sat;
    }

    public void setSat(Java_DLLSAT sat) {
        this.sat = sat;
    }

    public String ConsultaSAT(int aa)
    {
        String a = sat.ConsultarSAT(653871);
        return  a;

    }
}
