package com.example.ademirestudo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import model.modelDocumentoPagamento;
import model.modelDocumentoProduto;
import util.AcrescimoCupom;
import util.DescontoCupom;
import util.Tecladonumerico;

import static com.example.ademirestudo.AlteraCateg.getCurrencySymbol;
import static com.example.ademirestudo.MainActivity.objparam;
import static com.example.ademirestudo.R.color.semfoco;

public   class FinalizarVendas  extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();
    final DecimalFormat converte = new DecimalFormat("0.00");
    DadosOpenHelper db = new DadosOpenHelper(this);
    private static GridView containerGridViewFormPgto;;
    public static ArrayList<modelDocumentoPagamento> listadeFormPgto;
   // private static AdapterFormPgto itensFormPgto;
    private SQLiteDatabase conexao;
    public DadosOpenHelper dadosOpenHelper;
    private EditText valortotal;
    private EditText totalpago;
    private EditText totalrestante;
    private EditText dinheirorec;
    private Button dinheiro;
    private Button cheque;
    private Button cCredito;
    private Button cDebito;
    private static SATsweda sweda;
    private Button valeAlim;
    private Button outros;
    private Button desconto;
    private Button acrescimo;
    private Button incluir10;
    private Button cancelar;
    public EfetivarVenda efetuarvenda;
    public static int controle = 0;
    public double totrest = 0;
    private double totrec=0;
    public modelDocumentoProduto objCupom2;
    public modelDocumentoPagamento objFormPgto;
    public modelDocumentoPagamento formPag;
   private BigDecimal bd;
    View toastLayout;
    LayoutInflater layoutInflater;
    public static FinalizarVendas instance;
    public void onCreate(@Nullable Bundle savedInstanceState) {

        mainActivity.statusSat();
        super.onCreate(savedInstanceState);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    //        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });

        setContentView(R.layout.layoutfinalizarvendas);
       instance =this;
        DisplayMetrics dm = new DisplayMetrics();
        conectarBanco();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity(Gravity.TOP);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 70;
        params.y = 40;
        getWindow().setAttributes(params);
        getWindow().setLayout((int) (width * .704), (int) (height * .785));
        containerGridViewFormPgto =  findViewById(R.id.GridFormPag);
        listadeFormPgto = new ArrayList<>();
        //itensFormPgto = new AdapterFormPgto(this, listadeFormPgto);
        valortotal = (EditText) findViewById(R.id.etValorTot);
        totalrestante = findViewById(R.id.tvTotalRestante);
        dinheiro = (Button) findViewById(R.id.btnDinheiro);
        cheque = (Button) findViewById(R.id.btnCheque);
        cCredito = (Button) findViewById(R.id.btnCartaoC);
        cDebito = (Button) findViewById(R.id.btnCartaoD);
        valeAlim = (Button) findViewById(R.id.btnValeA);
        outros = (Button) findViewById(R.id.btnOutros);
        desconto = (Button) findViewById(R.id.btnDesconto);
        acrescimo = (Button) findViewById(R.id.btnAcrescimo);
        incluir10 = (Button) findViewById(R.id.btnIncluir10);
        cancelar =(Button) findViewById(R.id.btnCancelar);
        dinheirorec = (EditText) findViewById(R.id.tvDinheiro);
        totalpago = (EditText) findViewById(R.id.tvTotalPago);
        mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotaldocumentocdesc());
        totrest = mainActivity.objdocumento.getTotaldocumentocdesc();
        totalrestante.setText(String.valueOf(converte.format(totrest)));
        valortotal.setText(valortotal.getText() + String.valueOf(converte.format(mainActivity.objdocumento.getTotaldocumentocdesc())));
        dinheiro.setOnClickListener(selecDinheiro);
        cheque.setOnClickListener(selecCheque);
        cCredito.setOnClickListener(selecCartaoCred);
        cDebito.setOnClickListener(selecCartaoDeb);
        valeAlim.setOnClickListener(selecValeAlim);
        outros.setOnClickListener(selecOutros);
        desconto.setOnClickListener(selecDesconto);
        acrescimo.setOnClickListener(selecAcrescimo);
        incluir10.setOnClickListener(selecInclui10);
        incluir10.setVisibility(View.INVISIBLE);
        layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
    }

    public static String formatPriceSave(String price) {
        //Ex - price = $ 5555555
        //return = 55555.55 para salvar no banco de dados
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = price.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        efetuarvenda = new EfetivarVenda();

        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0.00");
        if (resultCode == RESULT_OK && requestCode == 1) {
            desconto.setBackgroundResource(semfoco);
            desconto.setOnClickListener(null);
            String retornoTeclado = data.getStringExtra("KEY");

            double valorrecDin = 0;
            valorrecDin = Double.parseDouble(retornoTeclado);

            totrec = totrec + Double.parseDouble(retornoTeclado);
            mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotalrestante() - Double.parseDouble(retornoTeclado));

            if ( Double.parseDouble(df.format(mainActivity.objdocumento.getTotaldocumentocdesc())) > totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecDin);
                formPag.setIdformapagamento(1);
                formPag.setEspecieformapagaento("01");
                formPag.setDescricao("Dinheiro");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                totrest = (mainActivity.objdocumento.getTotalrestante() - totrec);

            } else if ( Double.parseDouble(df.format(mainActivity.objdocumento.getTotaldocumentocdesc())) < totrec)  {
                double troco = (totrec - mainActivity.objdocumento.getTotaldocumentocdesc());

                formPag = new modelDocumentoPagamento();

                formPag.setTotalpagamento(valorrecDin - troco);
                formPag.setIdformapagamento(1);
                formPag.setEspecieformapagaento("01");
                formPag.setDescricao("Dinheiro");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                mainActivity.objdocumento.setTotaltroco(troco);
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                if(mainActivity.efetuarVenda( )==true){

                excluirOrc(conexao);
                for (int i = 1; i <= listadeFormPgto.size(); i++) {
                    int ultimoDoc = 0;
                    objFormPgto = new modelDocumentoPagamento();
                    objFormPgto = listadeFormPgto.get(i - 1);
                    ultimoDoc = db.selecionariddocumento();
                    objFormPgto.setIddocumento(ultimoDoc);
                    objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                    db.adddocpagamento(objFormPgto);


                }
                for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {

                    int ultimoDoc = 0;
                    objCupom2 = new modelDocumentoProduto();
                    objCupom2 = mainActivity.listadeItens.get(i - 1);
                    objCupom2.setSequencial(i);
                    ultimoDoc = db.selecionariddocumento();
                    objCupom2.setIddocumento(ultimoDoc);
                    objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                    db.adddocumentoproduto(objCupom2);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                 builder.setCancelable(false);
                builder.setTitle("              Venda Finalizada com sucesso!");
                builder.setMessage("            Total de troco \n" + "\n" + "              R$ " + converte.format(troco));
                //  formPag.setTotalpagamento(valorrec);
                //define um botão como positivo
                    if(objparam.getSatModelo().equalsIgnoreCase("2")){
                        sweda = new SATsweda(this);
                    }
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        mainActivity.inicianovodocumento();

                        finish();

                    }
                });
                AlertDialog alerta = builder.create();

                alerta.show();

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                pbutton.setTextSize(20);
                pbutton.setScaleY(1);
                pbutton.setScaleX(1);
                pbutton.setX(-40);
                pbutton.setTextColor(Color.WHITE);}
                else {

                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setCancelable(false);
                        builder.setTitle("              Venda Finalizada com sucesso!");
                        builder.setMessage("            Total de troco \n" + "\n" + "              R$ " + converte.format(troco));
                        //  formPag.setTotalpagamento(valorrec);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                mainActivity.inicianovodocumento();

                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();

                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });
                        AlertDialog alerta = builder.create();

                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            } else if ( Double.parseDouble(df.format(mainActivity.objdocumento.getTotaldocumentocdesc())) == totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecDin );
                formPag.setIdformapagamento(1);
                formPag.setEspecieformapagaento("01");
                formPag.setDescricao("Dinheiro");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);

                if(mainActivity.efetuarVenda()== true) {
                    excluirOrc(conexao);
                    for (int i = 1; i <= listadeFormPgto.size(); i++) {
                        int ultimoDoc = 0;
                        objFormPgto = new modelDocumentoPagamento();
                        objFormPgto = listadeFormPgto.get(i - 1);
                        ultimoDoc = db.selecionariddocumento();
                        objFormPgto.setIddocumento(ultimoDoc);
                        objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        db.adddocpagamento(objFormPgto);

                    }
                    for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
                        int ultimoDoc = 0;
                        objCupom2 = new modelDocumentoProduto();
                        objCupom2 = mainActivity.listadeItens.get(i - 1);
                        objCupom2.setSequencial(i);
                        ultimoDoc = db.selecionariddocumento();
                        objCupom2.setIddocumento(ultimoDoc);
                        objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        db.adddocumentoproduto(objCupom2);
                    }



                    mainActivity.inicianovodocumento();
                    if(objparam.getSatModelo().equalsIgnoreCase("2")){
                        //sweda = new SAT(this);
                        //sweda.desativarEthSat();
                    }
                    finish();
                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                }
                else {
                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        layoutInflater = getLayoutInflater();
                        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
                        Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(toastLayout);
                        toast.show();
                        mainActivity.inicianovodocumento();
                        finish();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //  formPag.setTotalpagamento(valorrec);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                //  mainActivity.inicianovodocumento();
                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();
                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 2) {
            bd =  BigDecimal.valueOf(mainActivity.objdocumento.getTotaldocumentocdesc()).setScale(2, RoundingMode.HALF_EVEN);
            desconto.setBackgroundResource(semfoco);
            desconto.setOnClickListener(null);
            String retornoTeclado = data.getStringExtra("KEY");
            double valorrecCheq = 0;
            valorrecCheq = Double.parseDouble(retornoTeclado);
            totrec = totrec + Double.parseDouble(retornoTeclado);

            if (bd.doubleValue() > totrec) {
                mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotalrestante() - Double.parseDouble(retornoTeclado));
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecCheq);
                formPag.setIdformapagamento(2);
                formPag.setEspecieformapagaento("02");
                formPag.setDescricao("Cheque");
                formPag.setIddocumento(db.selecionariddocumento());
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                listadeFormPgto.add(formPag);
                totrest = (mainActivity.objdocumento.getTotalrestante() - totrec);
            }
            else if ( bd.doubleValue() < totrec) {
                valorrecCheq = 0;
                totrec = totrec - Double.parseDouble(retornoTeclado);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(false);
                builder.setTitle("Atenção!");
                builder.setMessage("Essa forma de pagamento não permite troco, informe um valor menor ou igual ao total restante");
                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                       // finish();

                    }
                });
                AlertDialog alerta = builder.create();

                alerta.show();

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                pbutton.setTextSize(20);
                pbutton.setScaleY(1);
                pbutton.setScaleX(1);
                pbutton.setX(-40);
                pbutton.setTextColor(Color.WHITE);
            } else if ( bd.doubleValue() == totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecCheq);
                formPag.setIdformapagamento(2);
                formPag.setEspecieformapagaento("02");
                formPag.setDescricao("Cheque");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                if(mainActivity.efetuarVenda( )==true) {
                excluirOrc(conexao);
                for (int i = 1; i <= listadeFormPgto.size(); i++) {
                    int ultimoDoc = 0;
                    objFormPgto = new modelDocumentoPagamento();
                    objFormPgto = listadeFormPgto.get(i - 1);
                    ultimoDoc = db.selecionariddocumento();
                    objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                    objFormPgto.setIddocumento(ultimoDoc);
                    db.adddocpagamento(objFormPgto);

                }
                for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
                    int ultimoDoc = 0;
                    objCupom2 = new modelDocumentoProduto();
                    objCupom2 = mainActivity.listadeItens.get(i - 1);
                    objCupom2.setSequencial(i);
                    ultimoDoc = db.selecionariddocumento();
                    objCupom2.setIddocumento(ultimoDoc);
                    objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                    db.adddocumentoproduto(objCupom2);
                }
                mainActivity.inicianovodocumento();
                finish();

                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                }
                else {
                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        layoutInflater = getLayoutInflater();
                        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
                        Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(toastLayout);
                        toast.show();
                        mainActivity.inicianovodocumento();
                        finish();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();

                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 3) {
            bd =  BigDecimal.valueOf(mainActivity.objdocumento.getTotaldocumentocdesc()).setScale(2, RoundingMode.HALF_EVEN);
            desconto.setBackgroundResource(semfoco);
            desconto.setOnClickListener(null);
            String retornoTeclado = data.getStringExtra("KEY");
            double valorrecCc = 0;
            valorrecCc = Double.parseDouble(retornoTeclado);
            totrec = totrec + Double.parseDouble(retornoTeclado);
            if ( bd.doubleValue() > totrec) {
                mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotalrestante() - Double.parseDouble(retornoTeclado));
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecCc);
                formPag.setIdformapagamento(3);
                formPag.setEspecieformapagaento("03");
                formPag.setDescricao("Cartao de credito");
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);

                totrest = (mainActivity.objdocumento.getTotalrestante() - totrec);

            } else if ( bd.doubleValue() < totrec) {
                valorrecCc = 0;
                totrec = totrec - Double.parseDouble(retornoTeclado);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(false);
                builder.setTitle("Atenção!");
                builder.setMessage("Essa forma de pagamento não permite troco, informe um valor menor ou igual ao total restante");
                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  mainActivity.inicianovodocumento();
                        // finish();

                    }
                });
                AlertDialog alerta = builder.create();

                alerta.show();

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                pbutton.setTextSize(20);
                pbutton.setScaleY(1);
                pbutton.setScaleX(1);
                pbutton.setX(-40);
                pbutton.setTextColor(Color.WHITE);

            } else if (bd.doubleValue() == totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecCc);
                formPag.setIdformapagamento(3);
                formPag.setEspecieformapagaento("03");
                formPag.setDescricao("Cartao de credito");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                if(mainActivity.efetuarVenda( )==true) {
                    excluirOrc(conexao);
                    for (int i = 1; i <= listadeFormPgto.size(); i++) {
                        int ultimoDoc = 0;
                        objFormPgto = new modelDocumentoPagamento();
                        objFormPgto = listadeFormPgto.get(i - 1);
                        ultimoDoc = db.selecionariddocumento();
                        objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objFormPgto.setIddocumento(ultimoDoc);
                        db.adddocpagamento(objFormPgto);

                    }
                    for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
                        int ultimoDoc = 0;
                        objCupom2 = new modelDocumentoProduto();
                        objCupom2 = mainActivity.listadeItens.get(i - 1);
                        objCupom2.setSequencial(i);
                        ultimoDoc = db.selecionariddocumento();
                        objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objCupom2.setIddocumento(ultimoDoc);
                        db.adddocumentoproduto(objCupom2);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                    mainActivity.inicianovodocumento();
                    finish();
                }
                else {
                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        layoutInflater = getLayoutInflater();
                        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
                        Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(toastLayout);
                        toast.show();
                        mainActivity.inicianovodocumento();
                        finish();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                //  mainActivity.inicianovodocumento();
                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();

                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 4) {
            bd =  BigDecimal.valueOf(mainActivity.objdocumento.getTotaldocumentocdesc()).setScale(2, RoundingMode.HALF_EVEN);
            desconto.setBackgroundResource(semfoco);
            desconto.setOnClickListener(null);
            String retornoTeclado = data.getStringExtra("KEY");
            double valorrecCd = 0;
            valorrecCd = Double.parseDouble(retornoTeclado);
            totrec = totrec + Double.parseDouble(retornoTeclado);


            if ( bd.doubleValue() > totrec) {
                mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotalrestante() - Double.parseDouble(retornoTeclado));
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecCd);
                formPag.setIdformapagamento(4);
                formPag.setEspecieformapagaento("04");
                formPag.setDescricao("Cartao de debito");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                totrest = (mainActivity.objdocumento.getTotalrestante() - totrec);

            } else if ( bd.doubleValue() < totrec) {
                valorrecCd = 0;
                totrec = totrec - Double.parseDouble(retornoTeclado);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(false);
                builder.setTitle("Atenção!");
                builder.setMessage("Essa forma de pagamento não permite troco, informe um valor menor ou igual ao total restante");
                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  mainActivity.inicianovodocumento();
                        // finish();

                    }
                });
                AlertDialog alerta = builder.create();

                alerta.show();

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                pbutton.setTextSize(20);
                pbutton.setScaleY(1);
                pbutton.setScaleX(1);
                pbutton.setX(-40);
                pbutton.setTextColor(Color.WHITE);
            }
            else if ( bd.doubleValue() == totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecCd);
                formPag.setIdformapagamento(4);
                formPag.setEspecieformapagaento("04");
                formPag.setDescricao("Cartao de debito");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                if(mainActivity.efetuarVenda( )==true) {
                    excluirOrc(conexao);
                    for (int i = 1; i <= listadeFormPgto.size(); i++) {
                        int ultimoDoc = 0;
                        objFormPgto = new modelDocumentoPagamento();
                        objFormPgto = listadeFormPgto.get(i - 1);
                        ultimoDoc = db.selecionariddocumento();
                        objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objFormPgto.setIddocumento(ultimoDoc);
                        db.adddocpagamento(objFormPgto);

                    }
                    for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
                        int ultimoDoc = 0;
                        objCupom2 = new modelDocumentoProduto();
                        objCupom2 = mainActivity.listadeItens.get(i - 1);
                        objCupom2.setSequencial(i);
                        ultimoDoc = db.selecionariddocumento();
                        objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objCupom2.setIddocumento(ultimoDoc);
                        db.adddocumentoproduto(objCupom2);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                    mainActivity.inicianovodocumento();
                    finish();
                }
                else {
                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        layoutInflater = getLayoutInflater();
                        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
                        Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(toastLayout);
                        toast.show();
                        mainActivity.inicianovodocumento();
                        finish();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                //mainActivity.inicianovodocumento();
                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();

                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 5) {
            bd =  BigDecimal.valueOf(mainActivity.objdocumento.getTotaldocumentocdesc()).setScale(2, RoundingMode.HALF_EVEN);
            desconto.setBackgroundResource(semfoco);
            desconto.setOnClickListener(null);
            String retornoTeclado = data.getStringExtra("KEY");
            double valorrecVal = 0;
            valorrecVal = Double.parseDouble(retornoTeclado);
            totrec = totrec + Double.parseDouble(retornoTeclado);



            if ( bd.doubleValue() > totrec) {
                mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotalrestante() - Double.parseDouble(retornoTeclado));
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecVal);
                formPag.setIdformapagamento(6);
                formPag.setEspecieformapagaento("10");
                formPag.setDescricao("Vale alimentacao");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                totrest = (mainActivity.objdocumento.getTotalrestante() - totrec);

            } else if ( bd.doubleValue() < totrec) {
                valorrecVal = 0;
                totrec = totrec - Double.parseDouble(retornoTeclado);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(false);
                builder.setTitle("Atenção!");
                builder.setMessage("Essa forma de pagamento não permite troco, informe um valor menor ou igual ao total restante");
                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  mainActivity.inicianovodocumento();
                        // finish();

                    }
                });
                AlertDialog alerta = builder.create();

                alerta.show();

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                pbutton.setTextSize(20);
                pbutton.setScaleY(1);
                pbutton.setScaleX(1);
                pbutton.setX(-40);
                pbutton.setTextColor(Color.WHITE);
            } else if ( bd.doubleValue() == totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecVal);
                formPag.setIdformapagamento(6);
                formPag.setEspecieformapagaento("10");
                formPag.setDescricao("Vale alimentacao");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                if(mainActivity.efetuarVenda( )==true) {
                    excluirOrc(conexao);
                    for (int i = 1; i <= listadeFormPgto.size(); i++) {
                        int ultimoDoc = 0;
                        objFormPgto = new modelDocumentoPagamento();
                        objFormPgto = listadeFormPgto.get(i - 1);
                        ultimoDoc = db.selecionariddocumento();
                        objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objFormPgto.setIddocumento(ultimoDoc);
                        db.adddocpagamento(objFormPgto);

                    }
                    for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
                        int ultimoDoc = 0;
                        objCupom2 = new modelDocumentoProduto();
                        objCupom2 = mainActivity.listadeItens.get(i - 1);
                        objCupom2.setSequencial(i);
                        ultimoDoc = db.selecionariddocumento();
                        objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objCupom2.setIddocumento(ultimoDoc);
                        db.adddocumentoproduto(objCupom2);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                    mainActivity.inicianovodocumento();
                    finish();
                }
                else {
                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        layoutInflater = getLayoutInflater();
                        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
                        Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(toastLayout);
                        toast.show();
                        mainActivity.inicianovodocumento();
                        finish();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                //  mainActivity.inicianovodocumento();
                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();


                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 6) {
            bd =  BigDecimal.valueOf(mainActivity.objdocumento.getTotaldocumentocdesc()).setScale(2, RoundingMode.HALF_EVEN);
            desconto.setBackgroundResource(semfoco);
            desconto.setOnClickListener(null);
            String retornoTeclado = data.getStringExtra("KEY");
            double valorrecOut = 0;
            valorrecOut = Double.parseDouble(retornoTeclado);
            totrec = totrec + Double.parseDouble(retornoTeclado);



            if ( bd.doubleValue() > totrec) {
                formPag = new modelDocumentoPagamento();
                formPag.setTotalpagamento(valorrecOut);
                formPag.setIdformapagamento(10);
                formPag.setEspecieformapagaento("99");
                formPag.setDescricao("Outros");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                totrest = (mainActivity.objdocumento.getTotalrestante() - totrec);

            } else if ( bd.doubleValue() < totrec) {
                valorrecOut = 0;
                totrec = totrec - Double.parseDouble(retornoTeclado);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(false);
                builder.setTitle("Atenção!");
                builder.setMessage("Essa forma de pagamento não permite troco, informe um valor menor ou igual ao total restante");
                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //  mainActivity.inicianovodocumento();
                        // finish();

                    }
                });
                AlertDialog alerta = builder.create();

                alerta.show();

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.BLUE);
                pbutton.setTextSize(20);
                pbutton.setScaleY(1);
                pbutton.setScaleX(1);
                pbutton.setX(-40);
                pbutton.setTextColor(Color.WHITE);
            } else if ( bd.doubleValue() == totrec) {
                formPag = new modelDocumentoPagamento();

                formPag.setTotalpagamento(valorrecOut);
                formPag.setIdformapagamento(10);
                formPag.setEspecieformapagaento("99");
                formPag.setDescricao("Outros");
                formPag.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                formPag.setIddocumento(db.selecionariddocumento());
                listadeFormPgto.add(formPag);
                if(mainActivity.efetuarVenda( )==true) {
                    excluirOrc(conexao);
                    for (int i = 1; i <= listadeFormPgto.size(); i++) {
                        int ultimoDoc = 0;
                        objFormPgto = new modelDocumentoPagamento();
                        objFormPgto = listadeFormPgto.get(i - 1);
                        //  objCupom2.setSequencial(i);

                        ultimoDoc = db.selecionariddocumento();
                        objFormPgto.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        objFormPgto.setIddocumento(ultimoDoc);
                        db.adddocpagamento(objFormPgto);

                    }
                    for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
                        int ultimoDoc = 0;
                        objCupom2 = new modelDocumentoProduto();
                        objCupom2 = mainActivity.listadeItens.get(i - 1);
                        objCupom2.setSequencial(i);
                        objCupom2.setDthrcriacao(mainActivity.objdocumento.getDthrcriacao());
                        ultimoDoc = db.selecionariddocumento();
                        objCupom2.setIddocumento(ultimoDoc);
                        db.adddocumentoproduto(objCupom2);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                    mainActivity.inicianovodocumento();
                    finish();
                }
                else {
                    if (objparam.getSatModelo().equalsIgnoreCase("1")) {
                        layoutInflater = getLayoutInflater();
                        toastLayout = layoutInflater.inflate(R.layout.layouttoast, (ViewGroup) findViewById(R.id.layouttoast));
                        Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setView(toastLayout);
                        toast.show();
                        mainActivity.inicianovodocumento();
                        finish();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);

                        builder.setCancelable(false);
                        builder.setTitle("Houve uma falha");
                        builder.setMessage(mainActivity.erroconexãosat);
                        //define um botão como positivo
                        final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                //  mainActivity.inicianovodocumento();
                                finish();

                            }
                        });
                        AlertDialog alerta = builder.create();

                        alerta.show();

                        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.BLUE);
                        pbutton.setTextSize(20);
                        pbutton.setScaleY(1);
                        pbutton.setScaleX(1);
                        pbutton.setX(-40);
                        pbutton.setTextColor(Color.WHITE);
                    }
                }
            }}
        if (resultCode == RESULT_OK  && requestCode == 7){
            valortotal.setText(String.valueOf(converte.format( mainActivity.objdocumento.getTotaldocumentocdesc())));
            mainActivity.objdocumento.setTotalrestante(mainActivity.objdocumento.getTotaldocumentocdesc());

        }
        totalpago.setText(String.valueOf(converte.format(totrec)));
        dinheirorec.setText("   R$ " + totrec);
        totalrestante.setText(converte.format( mainActivity.objdocumento.getTotalrestante()));
        instance = null;
        }

    private View.OnClickListener selecDinheiro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Tecladonumerico.instance==null){
            mainActivity.controleteclado = 1;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 1);
        }}

    };


    private View.OnClickListener selecCheque = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Tecladonumerico.instance==null) {
                mainActivity.controleteclado = 2;
                Intent intent = new Intent(getApplication(), Tecladonumerico.class);
                startActivityForResult(intent, 2);
            }
        }

    };

    private View.OnClickListener selecCartaoCred = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Tecladonumerico.instance==null) {
                mainActivity.controleteclado = 3;
                Intent intent = new Intent(getApplication(), Tecladonumerico.class);

                startActivityForResult(intent, 3);
            }
        }

    };
    private View.OnClickListener selecCartaoDeb = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Tecladonumerico.instance==null) {
                mainActivity.controleteclado = 4;
                Intent intent = new Intent(getApplication(), Tecladonumerico.class);
                startActivityForResult(intent, 4);
            }
        }

    };
    private View.OnClickListener selecValeAlim = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Tecladonumerico.instance==null) {
                mainActivity.controleteclado = 5;
                Intent intent = new Intent(getApplication(), Tecladonumerico.class);
                startActivityForResult(intent, 5);
            }
        }

    };
    private View.OnClickListener selecOutros = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Tecladonumerico.instance==null) {
                mainActivity.controleteclado = 6;
                Intent intent = new Intent(getApplication(), Tecladonumerico.class);
                startActivityForResult(intent, 6);
            }
        }

    };
    private View.OnClickListener selecDesconto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(getApplication(), DescontoCupom.class);


            startActivityForResult(intent, 7);
        }

    };
    private View.OnClickListener selecAcrescimo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controle = 1;
            Intent intent = new Intent(getApplication(), AcrescimoCupom.class);

            startActivityForResult(intent, 7);
        }

    };
    private View.OnClickListener selecInclui10 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controle = 1;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 9);
        }

    };

    public SATsweda getSAT() {
        if (sweda == null) {
            sweda = new SATsweda(getApplicationContext());
        }

        return sweda;
    }

    public void cancelarFinalizarVenda(View view)
    {
        if(objparam.getSatModelo().equalsIgnoreCase("2")){
            sweda = new SATsweda(this);
            //sweda.desativarEthSat();
        }
        instance =null;
        this.finish();
    }
    private void excluirOrc(SQLiteDatabase db) {

        String[] numOrcExcluir = {String.valueOf( mainActivity.numOrcamento )};

        db.delete( "documento", "iddocumento=?", numOrcExcluir );
        db.delete("documentoproduto", "iddocumento=?", numOrcExcluir  );
    }
    @Override
    public void onBackPressed(){
        instance = null;
    }

    public void conectarBanco(){
        try {
            dadosOpenHelper = new DadosOpenHelper( this );
            conexao = dadosOpenHelper.getReadableDatabase();
        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder (this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

}