package com.example.ademirestudo;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

import model.modelCupomFiscal;
import model.modelDocumento;
import model.modelDocumentoPagamento;
import model.modelDocumentoProduto;

import static com.example.ademirestudo.CupomFiscalSat.cupomFiscalSat;

public class ReimprimirCupom extends AppCompatActivity {

    MainActivity mainActivity = new MainActivity();
    private static DadosOpenHelper dadosOpenHelper;
    private static SQLiteDatabase conexao;
    private modelDocumento modeldoc = new modelDocumento();
    private modelCupomFiscal objCupomFiscal = new modelCupomFiscal();
    private  ArrayList<modelDocumentoProduto> listadeItens;
    private  ArrayList<modelDocumentoPagamento> listadeFormPgto;
    private String data, horario;
    View toastLayout;
    LayoutInflater layoutInflater;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0");
        super.onCreate(savedInstanceState);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                }
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.reimprimircupom);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity(Gravity.NO_GRAVITY);
        getWindow().setLayout((int) (width * .47), (int) (height * .8));
        data = "";
        horario = "";
        conectarBanc();
        listarDocImprimir();



    }
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    private void conectarBanc() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getReadableDatabase();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }


    public void listarDocImprimir() {
       final DecimalFormat converte = new DecimalFormat("0.00");
        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0");
        NumberFormat dff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dff).applyPattern("0.000");
        NumberFormat dfff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dfff).applyPattern("0.00");
        ListView listCupomReimprimir = findViewById(R.id.listcupomreimprimir);
        ArrayList<modelDocumento> listadedocreimprimir;
        listadedocreimprimir = new ArrayList<>();

        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        String rawQuery = "SELECT iddocumento,dthrcriacao, cpfcnpj,totalquantidade,totaldocumento, numero, totaltroco, totaldesconto, totalacrescimo, chave ,xml FROM documento WHERE operacao = 'CU' order by iddocumento desc LIMIT 10";

        Cursor cursor5 = d5.rawQuery(rawQuery, null);
        if (cursor5 != null) {
            if (cursor5.moveToFirst()) {
                do {
                    modelDocumento modeldoc = new modelDocumento();
                    modeldoc.setIddocumento(cursor5.getInt(0));
                    String dia  = cursor5.getString(1).substring(8,10);
                    String mes  = cursor5.getString(1).substring(5,7);
                    String ano  = cursor5.getString(1).substring(0,4);
                    String hora  = cursor5.getString(1).substring(11,13);
                    String min  = cursor5.getString(1).substring(13,15);
                    String seg  = cursor5.getString(1).substring(15,19);
                    String data = dia +"/"+ mes  + "/"+ ano ;
                    String  horario = hora + min  + seg ;
                    modeldoc.setDthrcriacao(data +"  "+ horario);
                    modeldoc.setCpfcnpj(cursor5.getString(2));
                    modeldoc.setTotalquantidade(cursor5.getDouble(3));
                    modeldoc.setTotaldocumentocdesc(cursor5.getDouble(4)-cursor5.getDouble(7)+cursor5.getDouble(8));
                    modeldoc.setNumero(cursor5.getInt(5));
                    modeldoc.setTotaltroco(cursor5.getDouble(6));
                    modeldoc.setTotaldesconto(cursor5.getDouble(7));
                    modeldoc.setTotalacrescimo(cursor5.getDouble(8));
                    modeldoc.setChaveCfe(cursor5.getString(9));
                    modeldoc.setXml(cursor5.getString(10));


                    listadedocreimprimir.add(modeldoc);
                } while (cursor5.moveToNext());
            }
            cursor5.close();
            listCupomReimprimir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity mainActivity = new MainActivity();
                    modeldoc = (modelDocumento) parent.getItemAtPosition(position);
                    String dia  = (modeldoc.getDthrcriacao().substring(0,2));
                    String mes  = (modeldoc.getDthrcriacao().substring(3,5));
                    String ano  = (modeldoc.getDthrcriacao().substring(6,10));
                    String hora  = (modeldoc.getDthrcriacao().substring(12,14));
                    String min  = (modeldoc.getDthrcriacao().substring(15,17));
                    String seg  = (modeldoc.getDthrcriacao().substring(18,20));
                    data = dia +"/"+ mes  + "/"+ ano ;
                    horario = hora +":"+ min  +":"+ seg ;
                    String[]  qrcode =  modeldoc.getXml().split("assinaturaQRCODE>");

                    String cpfCnpjContrib = "";
                    if (modeldoc.getXml().contains("dest><CPF>")){
                        //cpfCnpjContrib = "cpf";
                        String[]  cpfContrib =  modeldoc.getXml().split("CPF>");
                        cpfCnpjContrib = cpfContrib[1];
                    if (cpfContrib[1].length()==13){
                        cpfCnpjContrib = cpfContrib[1].substring(0,11);
                    }}
                    if (modeldoc.getXml().contains("dest><CNPJ>")){

                        String[]  CNPJContrib =  modeldoc.getXml().split("CNPJ>");
                   if (CNPJContrib[1].length()==16){
                        cpfCnpjContrib = CNPJContrib[5].substring(0,14);
                    }}

                    mainActivity.qrCode =modeldoc.getChaveCfe()+"|"  +ano+mes+dia+hora+min+seg+"|" +converte.format(modeldoc.getTotaldocumentocdesc()).replace(",",".")+"|"+cpfCnpjContrib+"|" + qrcode[1].toString();
                    mainActivity.objCupomFiscal.setChaveCfe(modeldoc.getChaveCfe());
                    listarDcoProd();
                    listarFormPag();
                    montarCupom();

                    layoutInflater = getLayoutInflater();
                    toastLayout = layoutInflater.inflate(R.layout.toastreimprimir, (ViewGroup) findViewById(R.id.toastreimprimir));
                    Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();

                   mainActivity.reimprimirCupom(cupomFiscalSat);
                   finish();
                }
            });
        }
        AdapterReimpCupom adapterReimpCupom;
        adapterReimpCupom = new AdapterReimpCupom(this, listadedocreimprimir);
        listCupomReimprimir.setAdapter(adapterReimpCupom);
    }


    public void listarDcoProd(){
        listadeItens = new ArrayList<>();
        int iddocumento = modeldoc.getIddocumento();
        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        String rawQuery = "SELECT documentoproduto.descricao, documentoproduto.quantidade, documentoproduto.preco, documentoproduto.totalproduto,unidade.sigla, produto.idunidade FROM documentoproduto INNER JOIN produto ON documentoproduto.idproduto  = produto.idproduto INNER JOIN unidade ON unidade.idunidade  = produto.idunidade WHERE iddocumento = "+ iddocumento+"";

        Cursor cursor5 = d5.rawQuery(rawQuery, null);
        if (cursor5 != null) {
            if (cursor5.moveToFirst()) {
                do {
                    modelDocumentoProduto modeldocProd = new modelDocumentoProduto();
                    modeldocProd.setDescricao(cursor5.getString(0));
                    modeldocProd.setQuantidade(cursor5.getDouble(1));
                    modeldocProd.setPreco(cursor5.getDouble(2));
                    modeldocProd.setUndSigla(cursor5.getString(4));
                    modeldocProd.setIdunidade(cursor5.getInt(5));
                    modeldocProd.setTotalproduto(cursor5.getDouble(3));
                    listadeItens.add(modeldocProd);
                } while (cursor5.moveToNext());
            }
            cursor5.close();

    }}

    public void listarFormPag(){
        listadeFormPgto = new ArrayList<>();
        int iddocumento = modeldoc.getIddocumento();
        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        String rawQuery = "SELECT formapagamento.descricao, documentopagamento.totalpagamento  FROM documentopagamento LEFT JOIN formapagamento  ON formapagamento.idformapagamento  = documentopagamento.idformapagamento  WHERE iddocumento = "+ iddocumento+"";

        Cursor cursor5 = d5.rawQuery(rawQuery, null);
        if (cursor5 != null) {
            if (cursor5.moveToFirst()) {
                do {
                    modelDocumentoPagamento modeldocPag = new modelDocumentoPagamento();
                    modeldocPag.setDescricao(cursor5.getString(0));
                    modeldocPag.setTotalpagamento(cursor5.getDouble(1));
                    listadeFormPgto.add(modeldocPag);
                } while (cursor5.moveToNext());
            }
            cursor5.close();

        }}


    public  void montarCupom() {
        cupomFiscalSat = new StringBuilder();
        DecimalFormat converte = new DecimalFormat("0.00");
        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0");
        NumberFormat dff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dff).applyPattern("0.000");
        NumberFormat dfff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dfff).applyPattern("0.00");
        objCupomFiscal.setData(data);
        objCupomFiscal.setHora(horario);
        objCupomFiscal.setChaveCfe(modeldoc.getChaveCfe());
        objCupomFiscal.setSatSerie(objCupomFiscal.getChaveCfe().substring(22,31));
        cupomFiscalSat.append( "       "+"\n"+ "       " +mainActivity.objparam.getEmitenteRazaoSocial()+ "\n") ;
        cupomFiscalSat.append("       "+  mainActivity. objparam.getEmitenteEndereco()+", " +mainActivity. objparam.getEmitenteNumero()+ "\n");
        cupomFiscalSat.append("       "+mainActivity. objparam.getEmitenteBairro()+" - "+mainActivity. objparam.getEmitenteMunicipio()+" - "+mainActivity.objparam.getEmitenteUf()+ "\n");
        cupomFiscalSat.append("    "+"CNPJ:" + mainActivity.objparam.getEmitenteCNPJ().substring(0, 2) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(2, 5) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(5, 8) + "/" + mainActivity.objparam.getEmitenteCNPJ().substring(8, 12) + "-" + mainActivity.objparam.getEmitenteCNPJ().substring(12, 14) );
        cupomFiscalSat.append(" "+"IE:" + mainActivity.objparam.getEmitenteIe().substring(0,3)+"."+ mainActivity.objparam.getEmitenteIe().substring(3,6)+"."+ mainActivity.objparam.getEmitenteIe().substring(6,9)+"."+mainActivity.objparam.getEmitenteIe().substring(9,12)+"\n");
        cupomFiscalSat.append( "----------------------------------------------"+"\n");
        cupomFiscalSat.append("       "+ "CUPOM FISCAL ELETRONICO - SAT"+"\n");
        cupomFiscalSat.append("COO:"+String.format("%06d",  modeldoc.getNumero()) +" Data: "+objCupomFiscal.getData()+" Hora: "+objCupomFiscal.getHora()+"\n");
        cupomFiscalSat.append("----------------------------------------------"+"\n");
        if (modeldoc.getCpfcnpj().length()==11) {
            cupomFiscalSat.append("CPF/CNPJ do consumidor: " + modeldoc.getCpfcnpj().substring(0, 3) + "." + modeldoc.getCpfcnpj().substring(3, 6) + "." + modeldoc.getCpfcnpj().substring(6, 9) + "-" + modeldoc.getCpfcnpj().substring(9, 11) + "\n");

        }
        else{
            if  (modeldoc.getCpfcnpj().length()==14) {
                cupomFiscalSat.append("CPF/CNPJ do consumidor: " + modeldoc.getCpfcnpj().substring(0, 2) + "." + modeldoc.getCpfcnpj().substring(2, 5) + "." + modeldoc.getCpfcnpj().substring(5, 8) + "/" + modeldoc.getCpfcnpj().substring(8, 12) + "-" + modeldoc.getCpfcnpj().substring(12, 14) + "\n");
            }
            else{

                cupomFiscalSat.append("CPF/CNPJ do consumidor: " + modeldoc.getCpfcnpj()+ "\n");
            }}
        cupomFiscalSat.append("----------------------------------------------"+"\n");
        cupomFiscalSat.append("#   Descricao         Qtde X Preco     TOTAL\n");
        cupomFiscalSat.append("----------------------------------------------\n");

        modelDocumentoProduto objCupom1 = new modelDocumentoProduto();
        for (int i = 0; i < listadeItens.size(); i++) {
            objCupom1 =listadeItens.get(i);

            if (objCupom1.getIdunidade() == 1) {

                cupomFiscalSat.append(String.format("%-3.3s", i + 1) + " " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s", df.format(objCupom1.getQuantidade())) + " " +  String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");
            }
            else{
                if (objCupom1.getIdunidade() == 2) {

                    cupomFiscalSat.append(String.format("%-3.3s",i + 1) + " " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s",dff.format(objCupom1.getQuantidade())) + "  " +  String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");
                }


                else{
                    if (objCupom1.getIdunidade() == 3) {
                        cupomFiscalSat.append(String.format("%-3.3s",i + 1 )+ " " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s",dfff.format(objCupom1.getQuantidade())) +  "  " + String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");

                    }

                }

            }
        }
        if (modeldoc.getTotalacrescimo()>0){
            cupomFiscalSat.append(String.format("%-38.38s","Acrescimo") +"+" +String.format("%8.8s",converte.format(modeldoc.getTotalacrescimo())) +"\n");
        }
        if (modeldoc.getTotaldesconto()>0.00){
            cupomFiscalSat.append(String.format("%-38.38s","Desconto")+"-"  +String.format("%8.8s",converte.format(modeldoc.getTotaldesconto())) +"\n");
        }
        cupomFiscalSat.append(String.format("%-39.39s","TOTAL R$")  +String.format("%8.8s",converte.format(modeldoc.getTotaldocumentocdesc())) +"\n"+"\n");

        for (int i = 1; i <= listadeFormPgto.size(); i++) {
            modelDocumentoPagamento  objFormPgto = new modelDocumentoPagamento();
            objFormPgto = listadeFormPgto.get(i - 1);
            if (listadeFormPgto.size()==i) {
                objFormPgto.setTotalpagamento(objFormPgto.getTotalpagamento() + modeldoc.getTotaltroco() );
                cupomFiscalSat.append(String.format("%-39.39s", objFormPgto.getDescricao()) + String.format("%8.8s", converte.format(objFormPgto.getTotalpagamento())) + "\n");
            }
            else{
                cupomFiscalSat.append(String.format("%-39.39s", objFormPgto.getDescricao()) + String.format("%8.8s", converte.format(objFormPgto.getTotalpagamento())) + "\n");
            }

        }
        if( modeldoc.getTotaltroco()>0){
            cupomFiscalSat.append(String.format("%-39.39s", "Troco R$") + String.format("%8.8s", converte.format(modeldoc.getTotaltroco())) + "\n");
        }

        cupomFiscalSat.append("----------------------------------------------\n");
        cupomFiscalSat.append("           OBSERVAÇOES DO CONTRIBUINTE\n");
        cupomFiscalSat.append("ICMS recolhido conforme LC 123/2006-Simples Na\n");
        cupomFiscalSat.append("Valor aprox. imposto: R$ ");
        cupomFiscalSat.append(String.format("%2.2f",modeldoc.getTotaldocumentocdesc()* (mainActivity.objparam.getImpostofederal()/100)));
        cupomFiscalSat.append(" Fed / R$ ");
        cupomFiscalSat.append(String.format("%2.2f",modeldoc.getTotaldocumentocdesc()* (mainActivity.objparam.getImpostoestadual()/100)));
        cupomFiscalSat.append(" Est\n");
        cupomFiscalSat.append("----------------------------------------------\n");
        cupomFiscalSat.append("      SAT No. "+objCupomFiscal.getSatSerie()+"\n");
        cupomFiscalSat.append("      "+objCupomFiscal.getData()+" - "+objCupomFiscal.getHora()+"\n\n");

            cupomFiscalSat.append(objCupomFiscal.getChaveCfe().substring(0,  4) + " "+objCupomFiscal.getChaveCfe().substring(4,  8) +" "+objCupomFiscal.getChaveCfe().substring(8,  12) + " "+
                    objCupomFiscal.getChaveCfe().substring(12,  16) + " "+objCupomFiscal.getChaveCfe().substring(16,  20) + " "+objCupomFiscal.getChaveCfe().substring(20,  24) + " "+
                    objCupomFiscal.getChaveCfe().substring(24,  28) + " "+objCupomFiscal.getChaveCfe().substring(28,  32) + " "+objCupomFiscal.getChaveCfe().substring(32,  36) + "\n"+objCupomFiscal.getChaveCfe().substring(36,  40) + " "+objCupomFiscal.getChaveCfe().substring(40,  44) + "\n\n");

    }
    public void cancelarReimprCupom(View view)
    {

        this.finish();
    }
}