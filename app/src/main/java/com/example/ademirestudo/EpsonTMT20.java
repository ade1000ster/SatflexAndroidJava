package com.example.ademirestudo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.ademirestudo.MainActivity;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import android.content.Context;
import static android.widget.Toast.*;

public class EpsonTMT20 extends AppCompatActivity {
    private String EMPRESA = "REMARCA AUTOMAÇÃO";
    private EditText mEditTarget = null;
    public Button Criarimp = null;
    public String teste = null;
    StringBuilder Cupom1 = new StringBuilder();

    public StringBuilder Criartmt() {
        Cupom1.append( EMPRESA + "\n" );
        Cupom1.append( "RUA PIO - XI\n" );
        Cupom1.append( "CNPJ - 000-000-0000-00 \n" );
        Cupom1.append("------------------------------\n");
        Cupom1.append("400 OHEIDA 3PK SPRINGF  9.99 R\n");
        Cupom1.append("410 3 CUP BLK TEAPOT    9.99 R\n");
        Cupom1.append("445 EMERIL GRIDDLE/PAN 17.99 R\n");
        Cupom1.append("438 CANDYMAKER ASSORT   4.99 R\n");
        Cupom1.append("474 TRIPOD              8.99 R\n");
        Cupom1.append("433 BLK LOGO PRNTED ZO  7.99 R\n");
        Cupom1.append("458 AQUA MICROTERRY SC  6.99 R\n");
        Cupom1.append("493 30L BLK FF DRESS   16.99 R\n");
        Cupom1.append("407 LEVITATING DESKTOP  7.99 R\n");
        Cupom1.append("441 **Blue Overprint P  2.99 R\n");
        Cupom1.append("476 REPOSE 4PCPM CHOC   5.49 R\n");
        Cupom1.append("461 WESTGATE BLACK 25  59.99 R\n");
        Cupom1.append("------------------------------\n");
        Cupom1.append("SUBTOTAL                160.38\n");
        Cupom1.append("TAX                      14.43\n");
        Cupom1.append("CASH                    200.00\n");
        Cupom1.append("CHANGE                   25.19\n");
        Cupom1.append("------------------------------\n");
        Cupom1.append("Obrigado pela preferência\n");
        Cupom1.append("Volte Sempre !\n");
        return Cupom1;

    }
}