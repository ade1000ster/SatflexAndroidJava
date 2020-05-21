package com.example.ademirestudo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import model.modelDocumentoPagamento;
import model.modelDocumentoProduto;

public class DadosVenda {
    modelDocumentoPagamento objFormPgto = new modelDocumentoPagamento();
    public  modelDocumentoProduto objCupom;
    MainActivity mainActivity = new MainActivity();
    public StringBuilder criarDadosVenda(){

        FinalizarVendas f = new FinalizarVendas();
        StringBuilder dadosVenda = new StringBuilder();
        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0.00");
        NumberFormat dff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dff).applyPattern("0.0000");

        dadosVenda.append("<CFe>" +
                "<infCFe versaoDadosEnt=\"0.07\">" +
                "<ide>" +
                "<CNPJ>"+mainActivity.objparam.getDesenvolvedoraCNPJ()+"</CNPJ>" +
                "<signAC>"+mainActivity.objparam.getDesenvolvedoraAssinatura()+"</signAC>" +
                "<numeroCaixa>"+mainActivity.objparam.getSatCaixa()+"</numeroCaixa>" +
                "</ide>" +
                "<emit>" +
                "<CNPJ>"+mainActivity.objparam.getEmitenteCNPJ()+"</CNPJ>" +
                "<IE>"+mainActivity.objparam.getEmitenteIe()+"</IE>" +
                "<cRegTribISSQN>1</cRegTribISSQN>" +
                "<indRatISSQN>N</indRatISSQN>" +
                "</emit>");

        if (mainActivity.objdocumento.getCpfcnpj().length()==11){
            dadosVenda.append("<dest>" +
                    "<CPF>"+mainActivity.objdocumento.getCpfcnpj()+"</CPF>" +
                    "</dest>");
        }
        else {
            if (mainActivity.objdocumento.getCpfcnpj().length()==14){
                dadosVenda.append("<dest>" +
                        "<CNPJ>"+mainActivity.objdocumento.getCpfcnpj()+"</CNPJ>" +
                        "</dest>");
            }
            else {

                dadosVenda.append("<dest/>");
            }
        }
        for (int i = 1; i <= mainActivity.listadeItens.size(); i++) {
            objCupom = new modelDocumentoProduto();
            objCupom = mainActivity.listadeItens.get(i - 1);
            //String j = "1";

           // objFormPgto = f.listadeFormPgto.get(0);
           //m.binformarCPF.setText(String.valueOf(objFormPgto.getTotalpagamento()));
            dadosVenda.append("<det nItem=\"" + i + "\">" +
                    "<prod>" +
                    "<cProd>" + objCupom.getIddoproduto() + "</cProd>" +
                    "<xProd>"+objCupom.getDescricao()+"</xProd>" +
                    "<NCM>"+objCupom.getCodigoNcm().substring(0,4)+objCupom.getCodigoNcm().substring(5,7)+objCupom.getCodigoNcm().substring(8,10) +"</NCM>" +
                    "<CFOP>"+((objCupom.getCfop().substring(0,1) + objCupom.getCfop().substring(2,5)) )+"</CFOP>"+
                    "<uCom>"+objCupom.getUndSigla()+"</uCom>"+
                    "<qCom>"+dff.format(objCupom.getQuantidade())+"</qCom>" +
                    "<vUnCom>"+df.format(objCupom.getPreco())+"</vUnCom>" +
                    "<indRegra>T</indRegra>" +
                    "<vDesc>"+df.format(objCupom.getTotaldesconto())+"</vDesc>"+
                    "<vOutro>"+df.format(objCupom.getTotalacrescimo())+"</vOutro>"+
                    "</prod>" +
                    "<imposto>" +
                    "<ICMS>" +
                    "<ICMSSN102>" +
                    "<Orig>"+objCupom.getOrigem()+"</Orig>" +
                    "<CSOSN>"+objCupom.getCsosn()+"</CSOSN>" +
                    "</ICMSSN102>" +
                    "</ICMS>" +
                    "<PIS>" +
                    "<PISSN>" +
                    "<CST>"+objCupom.getCstpis()+"</CST>" +
                    "</PISSN>" +
                    "</PIS>" +
                    "<COFINS>" +
                    "<COFINSSN>" +
                    "<CST>"+objCupom.getCstcofins()+"</CST>" +
                    "</COFINSSN>" +
                    "</COFINS>" +
                    "</imposto>" +
                    "</det>");
        }

        dadosVenda.append(   "<total/>" +
                "<pgto>");
        for (int i = 1; i <= f.listadeFormPgto.size(); i++) {
            objFormPgto = new modelDocumentoPagamento();
            objFormPgto = f.listadeFormPgto.get(i - 1);
            dadosVenda.append("<MP>"+
                    "<cMP>" + objFormPgto.getEspecieformapagaento() + "</cMP>" +
                    "<vMP>" + df.format(objFormPgto.getTotalpagamento()) + "</vMP>"+
                    "</MP>" );
        }
        dadosVenda.append("</pgto>" +
                        "<infAdic>"+
                        "<infCpl>"+
                        "Valor aprox. imposto: R$ "+String.format("%2.2f",mainActivity.objdocumento.getTotaldocumentocdesc()* (mainActivity.objparam.getImpostofederal()/100))+ " Fed / R$ "+String.format("%2.2f",mainActivity.objdocumento.getTotaldocumentocdesc()* (mainActivity.objparam.getImpostoestadual()/100))+ " Est / Fonte SEBRAE"+
                        "</infCpl>"+
                        "</infAdic>"+
                       "</infCFe>" +
                       "</CFe>");
       // StringBuilder dadosVenda = new StringBuilder();
        return dadosVenda;
    }

    public StringBuilder criarDadosVendaCanc(String cfecanc){
        //String chavecfe = "CFe35200108238299000129599000196690000799624790";
        StringBuilder dadosVendaCanc = new StringBuilder();
        dadosVendaCanc.append("<CFeCanc>" +
                "<infCFe chCanc=\""+cfecanc+"\">" +
                "<ide>" +
                "<CNPJ>"+mainActivity.objparam.getDesenvolvedoraCNPJ()+"</CNPJ>" +
                "<signAC>"+mainActivity.objparam.getDesenvolvedoraAssinatura()+"</signAC>" +
               "<numeroCaixa>"+mainActivity.objparam.getSatCaixa()+"</numeroCaixa>"+
                "</ide>"+
                "<emit/><dest/><total/></infCFe></CFeCanc>");
        return dadosVendaCanc;

}}
