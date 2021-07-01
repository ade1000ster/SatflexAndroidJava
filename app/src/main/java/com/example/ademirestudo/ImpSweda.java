package com.example.ademirestudo;

import android.content.Context;
import com.sweda2.SWSIM;
import com.sweda2.si300dll.Java_SI300;
public class ImpSweda {


  private Java_SI300 javaSi300;
  private SWSIM swsim;
    private Context context;
    private static final int MAX_LENGHT = 30;

    public ImpSweda(final Context context) {
        this.context = context;
        javaSi300 = new Java_SI300();
        swsim = new SWSIM();
    }

    public void SelecionarAlinhamento (int var1){
        swsim.SelecionarAlinhamento(var1);
    }
    public void selecionarTamanhoCaractere(int var1 , int var2){
        swsim.SelecionarTamanhoCaracter(var1, var2);
    }
    public void SelecionarFonteCaracteres (int var1){
        swsim.SelecionarFonteCaracteres(var1);
    }
    public void SelecionarEspacamentoEntreCaracteres (int var1){
        swsim.SelecionarEspacamentoEntreCaracteres(var1);
    }
    public void imprimirTextos(final String texto) {

        swsim.ImprimirTexto(texto, texto.length());

    }
    public void ImprimirCodigoBarras(int var0, String var1){
        swsim.SelecionarAlturaCodigoBarras(60);
        swsim.ImprimirCodigoBarras( var0,  var1,0);
    }
    public  void iImprimeQRCode_TextoDireita(String var0){
        String var1 ="";
        javaSi300.setSize_Qrcode(4);
        javaSi300.iImprimeQRCode_TextoDireita(var0,var1);
    }
    public void setInterfaceComunicacao(int tipo) {
       javaSi300.Java_SI300_SetInterfaceComunicacao(tipo);
    }
    public void acionarCortadorPapel(){
        swsim.AcionarCortadorPapel();
    }
    public void setSize_Qrcode(int i) {
        javaSi300.setSize_Qrcode(i);
    }
}
