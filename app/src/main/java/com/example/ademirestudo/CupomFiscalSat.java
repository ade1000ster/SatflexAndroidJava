package com.example.ademirestudo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import model.modelDocumentoPagamento;
import model.modelDocumentoProduto;

public class CupomFiscalSat {
    public static StringBuilder cupomFiscalSat;
    public static StringBuilder cupomFiscalCancelado;
    public static StringBuilder cupomFiscalCancelamento;
    MainActivity mainActivity = new MainActivity();
    FinalizarVendas f = new FinalizarVendas();
    public modelDocumentoPagamento objFormPgto;
    public  void montarCupom() {
          cupomFiscalSat = new StringBuilder();
          DecimalFormat converte = new DecimalFormat("0.00");
          NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0");
          NumberFormat dff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dff).applyPattern("0.000");
          NumberFormat dfff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dfff).applyPattern("0.00");
          cupomFiscalSat.append("       "+ mainActivity.objparam.getEmitenteRazaoSocial()+ "\n") ;
          cupomFiscalSat.append("       "+  mainActivity. objparam.getEmitenteEndereco()+", " +mainActivity. objparam.getEmitenteNumero()+ "\n");
          cupomFiscalSat.append("       "+mainActivity. objparam.getEmitenteBairro()+" - "+mainActivity. objparam.getEmitenteMunicipio()+" - "+mainActivity.objparam.getEmitenteUf()+ "\n");
          cupomFiscalSat.append("    "+"CNPJ:" + mainActivity.objparam.getEmitenteCNPJ().substring(0, 2) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(2, 5) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(5, 8) + "/" + mainActivity.objparam.getEmitenteCNPJ().substring(8, 12) + "-" + mainActivity.objparam.getEmitenteCNPJ().substring(12, 14) );
          cupomFiscalSat.append(" "+"IE:" + mainActivity.objparam.getEmitenteIe().substring(0,3)+"."+ mainActivity.objparam.getEmitenteIe().substring(3,6)+"."+ mainActivity.objparam.getEmitenteIe().substring(6,9)+"."+mainActivity.objparam.getEmitenteIe().substring(9,12)+"\n");
          cupomFiscalSat.append( "----------------------------------------------"+"\n");
          cupomFiscalSat.append("       "+ "CUPOM FISCAL ELETRONICO - SAT"+"\n");
          cupomFiscalSat.append("COO:"+String.format("%06d",mainActivity.objCupomFiscal.getCoo())+" Data: "+mainActivity.objCupomFiscal.getData()+" Hora: "+mainActivity.objCupomFiscal.getHora()+"\n");
          cupomFiscalSat.append("----------------------------------------------"+"\n");
       if (mainActivity.objdocumento.getCpfcnpj().length()==11) {
           cupomFiscalSat.append("CPF/CNPJ do consumidor: " + mainActivity.objdocumento.getCpfcnpj().substring(0, 3) + "." + mainActivity.objdocumento.getCpfcnpj().substring(3, 6) + "." + mainActivity.objdocumento.getCpfcnpj().substring(6, 9) + "-" + mainActivity.objdocumento.getCpfcnpj().substring(9, 11) + "\n");

       }
       else{
         if  (mainActivity.objdocumento.getCpfcnpj().length()==14) {
               cupomFiscalSat.append("CPF/CNPJ do consumidor: " + mainActivity.objdocumento.getCpfcnpj().substring(0, 2) + "." + mainActivity.objdocumento.getCpfcnpj().substring(2, 5) + "." + mainActivity.objdocumento.getCpfcnpj().substring(5, 8) + "/" + mainActivity.objdocumento.getCpfcnpj().substring(8, 12) + "-" + mainActivity.objdocumento.getCpfcnpj().substring(12, 14) + "\n");
       }
         else{

                 cupomFiscalSat.append("CPF/CNPJ do consumidor: " + mainActivity.objdocumento.getCpfcnpj()+ "\n");
             }}
       cupomFiscalSat.append("----------------------------------------------"+"\n");
       cupomFiscalSat.append("#   Descricao         Qtde X Preco     TOTAL\n");
       cupomFiscalSat.append("----------------------------------------------\n");

          modelDocumentoProduto objCupom1 = new modelDocumentoProduto();
          for (int i = 0; i < mainActivity.listadeItens.size(); i++) {
              objCupom1 = mainActivity.listadeItens.get(i);

              if (objCupom1.getIdunidade() == 1) {

                  cupomFiscalSat.append(i + 1 + "  " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%6.6s", df.format(objCupom1.getQuantidade())) + "  " +  String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");
              }
              else{
                  if (objCupom1.getIdunidade() == 2) {

                      cupomFiscalSat.append(i + 1 + "  " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s",dff.format(objCupom1.getQuantidade())) + "  " +  String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");
                  }


              else{
                  if (objCupom1.getIdunidade() == 3) {
                      cupomFiscalSat.append(i + 1 + "  " + String.format("%-13.13s", objCupom1.getDescricao()) + "  " + String.format("%-6.6s",dfff.format(objCupom1.getQuantidade())) +  "  " + String.format("%-2.2s", objCupom1.getUndSigla()) + " X " + String.format("%6.6s", converte.format(objCupom1.getPreco())) + "  " + String.format("%8.8s", converte.format(objCupom1.getTotalproduto())) + "\n");

                  }

              }

          }}
          if (mainActivity.objdocumento.getTotalacrescimo()>0){
              cupomFiscalSat.append(String.format("%-38.38s","Acrescimo") +"+" +String.format("%8.8s",converte.format(mainActivity.objdocumento.getTotalacrescimo())) +"\n");
          }
          if (mainActivity.objdocumento.getTotaldesconto()>0){
              cupomFiscalSat.append(String.format("%-38.38s","Desconto")+"-"  +String.format("%8.8s",converte.format(mainActivity.objdocumento.getTotaldesconto())) +"\n");
          }
       cupomFiscalSat.append(String.format("%-39.39s","TOTAL R$")  +String.format("%8.8s",converte.format(mainActivity.objdocumento.getTotaldocumentocdesc())) +"\n"+"\n");

          for (int i = 1; i <= f.listadeFormPgto.size(); i++) {
              objFormPgto = new modelDocumentoPagamento();
              objFormPgto = f.listadeFormPgto.get(i - 1);
              if (f.listadeFormPgto.size()==i) {
                 // objFormPgto.setTotalpagamento(objFormPgto.getTotalpagamento() + mainActivity.objdocumento.getTotaltroco() );
                  cupomFiscalSat.append(String.format("%-39.39s", objFormPgto.getDescricao()) + String.format("%8.8s", (converte.format(objFormPgto.getTotalpagamento() + mainActivity.objdocumento.getTotaltroco()))) + "\n");
              }
              else{
                  cupomFiscalSat.append(String.format("%-39.39s", objFormPgto.getDescricao()) + String.format("%8.8s", converte.format(objFormPgto.getTotalpagamento())) + "\n");
              }
          }
          if( mainActivity.objdocumento.getTotaltroco()>0){
              cupomFiscalSat.append(String.format("%-39.39s", "Troco R$") + String.format("%8.8s", converte.format(mainActivity.objdocumento.getTotaltroco())) + "\n");
          }

       cupomFiscalSat.append("----------------------------------------------\n");
       cupomFiscalSat.append("           OBSERVAÇOES DO CONTRIBUINTE\n");
       cupomFiscalSat.append("ICMS recolhido conforme LC 123/2006-Simples Na\n");
       cupomFiscalSat.append("Valor aprox. imposto: R$ ");
       cupomFiscalSat.append(String.format("%2.2f",mainActivity.objdocumento.getTotaldocumentocdesc()* (mainActivity.objparam.getImpostofederal()/100)));
       cupomFiscalSat.append(" Fed / R$ ");
       cupomFiscalSat.append(String.format("%2.2f",mainActivity.objdocumento.getTotaldocumentocdesc()* (mainActivity.objparam.getImpostoestadual()/100)));
       cupomFiscalSat.append(" Est\n");
       cupomFiscalSat.append("----------------------------------------------\n");
        mainActivity.objCupomFiscal.setSatSerie( mainActivity.objCupomFiscal.getChaveCfe().substring(22,31));
       cupomFiscalSat.append("      SAT No. "+mainActivity.objCupomFiscal.getSatSerie()+"\n");
       cupomFiscalSat.append("      "+mainActivity.objCupomFiscal.getData()+" - "+mainActivity.objCupomFiscal.getHora()+"\n");
       cupomFiscalSat.append(mainActivity.objCupomFiscal.getChaveCfe()+"\n");
      }
    public  void montarCupomCanc()  {
        cupomFiscalCancelado = new StringBuilder();
        cupomFiscalCancelamento = new StringBuilder();
        DecimalFormat converte = new DecimalFormat("0.00");

        cupomFiscalCancelado.append(String.format( "%37.37s", mainActivity.objparam.getEmitenteRazaoSocial())+ "\n") ;
        cupomFiscalCancelado.append("       "+  mainActivity. objparam.getEmitenteEndereco()+", " +mainActivity. objparam.getEmitenteNumero()+ "\n");
        cupomFiscalCancelado.append("       "+mainActivity. objparam.getEmitenteBairro()+" - "+mainActivity. objparam.getEmitenteMunicipio()+" - "+mainActivity.objparam.getEmitenteUf()+ "\n");
        cupomFiscalCancelado.append("    "+"CNPJ:" + mainActivity.objparam.getEmitenteCNPJ().substring(0, 2) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(2, 5) + "." + mainActivity.objparam.getEmitenteCNPJ().substring(5, 8) + "/" + mainActivity.objparam.getEmitenteCNPJ().substring(8, 12) + "-" + mainActivity.objparam.getEmitenteCNPJ().substring(12, 14) );
        cupomFiscalCancelado.append(" "+"IE:" + mainActivity.objparam.getEmitenteIe().substring(0,3)+"."+ mainActivity.objparam.getEmitenteIe().substring(3,6)+"."+ mainActivity.objparam.getEmitenteIe().substring(6,9)+"."+mainActivity.objparam.getEmitenteIe().substring(9,12)+"\n");
        cupomFiscalCancelado.append( "----------------------------------------------"+"\n");
        cupomFiscalCancelado.append("       "+ "CUPOM FISCAL ELETRONICO - SAT"+"\n");
        cupomFiscalCancelado.append("COO:"+String.format("%06d", mainActivity.objCupomFiscal.getCoo())+" Data: "+mainActivity.objCupomFiscal.getData()+" Hora: "+mainActivity.objCupomFiscal.getHora()+"\n");
        cupomFiscalCancelado.append("                     CANCELAMENTO"+"\n");
        cupomFiscalCancelado.append("----------------------------------------------"+"\n");
        cupomFiscalCancelado.append("Dados do cupom fiscal eletronico cancelado\n");
        cupomFiscalCancelado.append("      SAT No. "+mainActivity.objCupomFiscal.getSatSerie()+"\n");
        //modelDocumentoProduto objCupom1 = new modelDocumentoProduto();
        cupomFiscalCancelado.append(String.format("%-39.39s","TOTAL R$")  +String.format("%-8.8s",converte.format(mainActivity.objdocumento.getTotaldocumentocdesc())) +"\n"+"\n");
        cupomFiscalCancelado.append("      "+mainActivity.objCupomFiscal.getData()+" - "+mainActivity.objCupomFiscal.getHora()+"\n");
        cupomFiscalCancelado.append(mainActivity.objCupomFiscal.getChaveCfe()+"\n");


        cupomFiscalCancelamento.append("----------------------------------------------\n");
        cupomFiscalCancelamento.append("Dados do cupom fiscal eletronico de cancelamento\n");

        cupomFiscalCancelamento.append("      SAT No. "+mainActivity.objCupomFiscal.getSatSerie()+"\n");
        cupomFiscalCancelamento.append("      "+mainActivity.objCupomFiscal.getDataCanc()+" - "+mainActivity.objCupomFiscal.getHoraCanc()+"\n");
        cupomFiscalCancelamento.append(mainActivity.objCupomFiscal.getChaveCfeCanc()+"\n");
    }
}
