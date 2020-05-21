package com.example.ademirestudo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epson.epos2.printer.Printer;
import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import model.modelDocumentoProduto;
import util.MascaraCNPJCPF;
import util.Tecladocpfcnpj;


public class CriarOrcamento extends AppCompatActivity implements View.OnClickListener {
    public static Object instance;
    DadosOpenHelper db = new DadosOpenHelper(this);
    private SQLiteDatabase conexao;
    public DadosOpenHelper dadosOpenHelper;
    private EditText editnomecliente;
    private EditText editcpfcnpj;
    private Button btncancelar;
    private Button btnconfimar;
    private Printer mPrinterorc = null;
    public modelDocumentoProduto objCupom2;


    MainActivity mainActivity = new MainActivity();

    @SuppressLint("WrongViewCast")
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        setContentView(R.layout.layoutorcamento);
        instance=this;
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout((int) (width * .3), (int) (height * .4));

        editnomecliente = (EditText) findViewById(R.id.editnomecliente);
        editcpfcnpj = (EditText) findViewById(R.id.editcpfcnpj);
        btncancelar = (Button) findViewById(R.id.btncancelar);
        btnconfimar = (Button) findViewById(R.id.btnconfirmar);
        conectarBanco();
        editcpfcnpj.addTextChangedListener(MascaraCNPJCPF.insert(editcpfcnpj));
        editcpfcnpj.setText( mainActivity.objdocumento.getCpfcnpj().toString() );
        editcpfcnpj.setOnClickListener( this );



    }
    public void esconderTeclado(View view){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editnomecliente.getWindowToken(), 0);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            String teste =  data.getStringExtra("KEY");
            this.editcpfcnpj.setText(teste);
        }
        else {
            Toast.makeText(getApplicationContext(),"Nenhuma cor selecionada" , Toast.LENGTH_LONG).show();
        }
    }

    public void fechaorcamento (View view){
        instance=null;
        this.finish();
    }

    public void onClick (View view) {
        editcpfcnpj.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Tecladocpfcnpj.instance==null) {
                    Intent intent = new Intent(getApplication(), Tecladocpfcnpj.class);
                    //ini cia a activity desejada, enquanto está continua aberta esperando
                    // um resultado
                    startActivityForResult(intent, 1);
                }
            }
        } );
    }

    public void excluirOrc(SQLiteDatabase db) {

        String[] numOrcExcluir = {String.valueOf( mainActivity.numOrcamento )};

        db.delete( "documento", "iddocumento=?", numOrcExcluir );
        db.delete("documentoproduto", "iddocumento=?", numOrcExcluir  );
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
    public void gravarorcamento (View view)
    {
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH)+1;
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        final int segundo = calendar.get(Calendar.SECOND);
        String diaString  = Integer.toString(dia);
        String mesString  = Integer.toString(mes );
        String horaString  = Integer.toString(hora);
        String minutoString  = Integer.toString(minuto );

        String segundoString  = Integer.toString(segundo );
        if (diaString.length()<2  && dia !=9 ) {
            diaString="0"+dia;
        }
        else {
            if (dia == 9) {
                diaString = "0" + dia;

            }
        }
        if (horaString.length()<2  && hora !=9 ) {
            horaString="0"+hora;
        }
        else {
            if (hora == 9) {
                horaString = "0" + hora;

            }
        }
        if (minutoString.length()<2  && minuto !=9 ) {
            minutoString="0"+minuto ;
        }
        else {
            if ( minuto== 9) {
                minutoString = "0" + minuto;

            }
        }
        if (segundoString.length()<2  && segundo !=9 ) {
            segundoString="0"+segundo ;
        }
        else {
            if ( segundo== 9) {
                segundoString = "0" + segundo;

            }
        }
        if (mesString.length()<2)mesString="0"+mesString;
        mainActivity.objdocumento.setDthrcriacao(ano+"-"+mesString +"-"+diaString+" "+horaString+":"+minutoString+":"+segundoString);

        try {
           // modelDocumento orc = new modelDocumento();
            if (editcpfcnpj.getText().length()>=1) {
                mainActivity.objdocumento.setCpfcnpj( editcpfcnpj.getText().toString() );
            }
            if (editnomecliente.getText().length()>=1) {
                mainActivity.objdocumento.setNomecliente( editnomecliente.getText().toString() );
            }
            mainActivity.objdocumento.setOperacao("OR");
            db.adddocumento( mainActivity.objdocumento );

            imprimirorc(  );
            //excluirOrc(conexao);
            //String[] numOrcExcluir = {String.valueOf( mainActivity.numOrcamento )};

            Toast.makeText(getApplicationContext(), "Orcamento salvo com sucesso"+mainActivity.numOrcamento , Toast.LENGTH_SHORT).show();
mainActivity.inicianovodocumento();
mainActivity.atualizarlista();

            instance=null;
            this.finish();
            Toast.makeText(getApplicationContext(), "Orcamento salvo com sucesso", Toast.LENGTH_SHORT).show();
        }catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Não foi possível salvar o orcamento" + ex.getMessage()+ex.getCause(), Toast.LENGTH_LONG).show();
        }
    }

    public void imprimirorc( ) {
        DecimalFormat converte = new DecimalFormat("0.00");
        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0");
        NumberFormat dff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dff).applyPattern("0.000");
        NumberFormat dfff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dfff).applyPattern("0.00");
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH)+1;
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        final int segundo = calendar.get(Calendar.SECOND);
        String diaString  = Integer.toString(dia);
        String mesString  = Integer.toString(mes );
        String horaString  = Integer.toString(hora);
        String minutoString  = Integer.toString(minuto );

        String segundoString  = Integer.toString(segundo );
        if (diaString.length()<2  && dia !=9 ) {
            diaString="0"+dia;
        }
        else {
            if (dia == 9) {
                diaString = "0" + dia;

            }
        }
        if (horaString.length()<2  && hora !=9 ) {
            horaString="0"+hora;
        }
        else {
            if (hora == 9) {
                horaString = "0" + hora;

            }
        }
        if (minutoString.length()<2  && minuto !=9 ) {
            minutoString="0"+minuto ;
        }
        else {
            if ( minuto== 9) {
                minutoString = "0" + minuto;

            }
        }
        if (segundoString.length()<2  && segundo !=9 ) {
            segundoString="0"+segundo ;
        }
        else {
            if ( segundo== 9) {
                segundoString = "0" + segundo;

            }
        }
        if (mesString.length()<2)mesString="0"+mesString;

        int ultimoDoc =0;
        for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {

            objCupom2 = new modelDocumentoProduto();
            objCupom2 = mainActivity.listadeItens.get(i - 1);
            objCupom2.setSequencial( i );
            ultimoDoc = db.selecionariddocumento(  );
            objCupom2.setIddocumento(ultimoDoc);
            db.adddocumentoproduto( objCupom2 );

            }
        StringBuilder cupomOrc = new StringBuilder();
        cupomOrc.append("       "+ mainActivity.objparam.getEmitenteRazaoSocial()+ "\n") ;
        cupomOrc.append("       "+  mainActivity. objparam.getEmitenteEndereco()+", " +mainActivity. objparam.getEmitenteNumero()+ "\n");
        cupomOrc.append("       "+mainActivity. objparam.getEmitenteBairro()+" - "+mainActivity. objparam.getEmitenteMunicipio()+" - "+mainActivity.objparam.getEmitenteUf()+ "\n");
        cupomOrc.append("    "+"CNPJ:" + mainActivity.objparam.getEmitenteCNPJ().substring(0, 2) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(2, 5) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(5, 8) + "/" + mainActivity.objparam.getEmitenteCNPJ().substring(8, 12) + "-" + mainActivity.objparam.getEmitenteCNPJ().substring(12, 14) );
        cupomOrc.append(" "+"IE:" + mainActivity.objparam.getEmitenteIe().substring(0,3)+"."+ mainActivity.objparam.getEmitenteIe().substring(3,6)+"."+ mainActivity.objparam.getEmitenteIe().substring(6,9)+"."+mainActivity.objparam.getEmitenteIe().substring(9,12)+"\n");
        cupomOrc.append( "----------------------------------------------");
        cupomOrc.append("       "+ "ORCAMENTO  -  SEM VALOR FISCAL\n");
        cupomOrc.append("Num:   "+String.format("%06d",ultimoDoc)+ " Data: " +diaString+"/"+mesString+"/"+ano+"  Hora: " + horaString+":"+minutoString+":"+segundoString+"\n");
        cupomOrc.append( "----------------------------------------------\n");
        cupomOrc.append("Nome do cliente:  " + mainActivity.objdocumento.getNomecliente()+ "\n");
        cupomOrc.append("CPF/CNPJ do cliente:  " + mainActivity.objdocumento.getCpfcnpj()+ "\n");
        cupomOrc.append( "----------------------------------------------\n");
        cupomOrc.append( "*   DESCRIÇÃO     QTD x  VALOR           TOTAL\n");
        cupomOrc.append( "----------------------------------------------\n");
        modelDocumentoProduto objCupom1 = new modelDocumentoProduto();
        for (int i = 0; i < mainActivity.listadeItens.size(); i++) {
            objCupom1 = mainActivity.listadeItens.get(i);

            if (objCupom1.getIdunidade() == 1) {

                cupomOrc.append(i + 1 + "  " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%6.6s", df.format(objCupom1.getQuantidade())) + "  " +  String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");
            }
            else{
                if (objCupom1.getIdunidade() == 2) {

                    cupomOrc.append(i + 1 + "  " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s",dff.format(objCupom1.getQuantidade())) + "  " +  String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");
                }


                else{
                    if (objCupom1.getIdunidade() == 3) {
                        cupomOrc.append(i + 1 + "  " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s",dfff.format(objCupom1.getQuantidade())) +  "  " + String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");

                    }

                }

            }}
        if (mainActivity.objdocumento.getTotalacrescimo()>0){
            cupomOrc.append(String.format("%-38.38s","Acrescimo") +"+" +String.format("%8.8s",converte.format(mainActivity.objdocumento.getTotalacrescimo())) +"\n");
        }
        if (mainActivity.objdocumento.getTotaldesconto()>0){
            cupomOrc.append(String.format("%-38.38s","Desconto")+"-"  +String.format("%8.8s",converte.format(mainActivity.objdocumento.getTotaldesconto())) +"\n");
        }
        cupomOrc.append(String.format("%-39.39s","TOTAL R$")  +String.format("%8.8s",converte.format(mainActivity.objdocumento.getTotaldocumentocdesc())) +"\n"+"\n");

        mainActivity.imprimirCupomOrc(cupomOrc);


    }

}

