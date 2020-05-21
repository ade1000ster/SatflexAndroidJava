package com.example.ademirestudo;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.regex.Pattern;

import model.modelRelatorioOrc;

public class CupomRelatorios {
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    DecimalFormat converte = new DecimalFormat("0.00");
    public static StringBuilder cupomRelatorios;
    MainActivity mainActivity = new MainActivity();
    String diainicial  = (mainActivity.dataInicialAux.substring(8,10));
    String mesinicial  = (mainActivity.dataInicialAux.substring(4,8));
    String anoinicial  = (mainActivity.dataInicialAux.substring(0,4));
    int diafinalAux = Integer.parseInt(mainActivity.dataFinalAux.substring(8,10));

    String diafinal  = (String.valueOf(diafinalAux-1));

    String mesfinal  = (mainActivity.dataFinalAux.substring(4,8));
    String anofinal  = (mainActivity.dataFinalAux.substring(0,4));
    //Remover acentos amtes de enviar a impressora
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public void cupomOrcamento(){
        cupomRelatorios = new StringBuilder();
        modelRelatorioOrc objCupomRelOrc = new modelRelatorioOrc();
        if(diafinalAux <10){
            diafinal = "0" +diafinal;
        }
        cupomRelatorios.append( "       "+ deAccent(mainActivity.objparam.getEmitenteRazaoSocial())+ "\n") ;
        cupomRelatorios.append("ORCAMENTOS: "+ diainicial+mesinicial +anoinicial+ "  "+diafinal+mesfinal+anofinal  + "\n");
        cupomRelatorios.append( "\n" );
        cupomRelatorios.append("Numero   Cliente             Qtde        TOTAL\n");



        for (int i = 0; i < mainActivity.listadeorcamento.size(); i++) {

            objCupomRelOrc = mainActivity.listadeorcamento.get(i);

            if ((objCupomRelOrc.getTotalquantidade() != -11) && (objCupomRelOrc.getIdproduto() != -1)) {
                if (objCupomRelOrc.getQuantidade() == Math.rint (objCupomRelOrc.getQuantidade())) {
                    int quantidade = (int)objCupomRelOrc.getQuantidade();
                    cupomRelatorios.append(String.format("%-5.5s",objCupomRelOrc.getNumero())+ "   " + String.format("%-14.14s", objCupomRelOrc.getCliente()) + "        " + String.format("%-5.5s", quantidade) + "    " + String.format("%9.2f", objCupomRelOrc.getTotal()) + "\n");
                    objCupomRelOrc.setQuantidade(quantidade);
                }
                else{
                    double quantidade = objCupomRelOrc.getQuantidade();
                    objCupomRelOrc.setQuantidade(quantidade);
                    cupomRelatorios.append(String.format("%-5.5s", objCupomRelOrc.getNumero()) + "   " + String.format("%-14.14s", objCupomRelOrc.getCliente()) + "        " + String.format("%-5.2f", objCupomRelOrc.getQuantidade()) + "    " + String.format("%9.2f", objCupomRelOrc.getTotal()) + "\n");
                }

            }
        }
        cupomRelatorios.append("Total.......................  "+String.format("%-7.7s",mainActivity.quantTotalRelOrc) + "  " + String.format("%9.2f",mainActivity.valorTotalRelOrc )+ "\n");

    }
    public void cupomFechamento(){
        cupomRelatorios = new StringBuilder();
        String tipoFechamento="";
        modelRelatorioOrc objCupomRelOrc = new modelRelatorioOrc();
        if(diafinalAux <10){
            diafinal = "0" +diafinal;
        }
        if (mainActivity.auxtipoRelatorio==1){
            tipoFechamento = "DATA";
        }
        else
        if (mainActivity.auxtipoRelatorio==2){
            tipoFechamento = "CATEGORIA";
        }
        else
        if (mainActivity.auxtipoRelatorio==3){
            tipoFechamento = "Forma DE PAGAMENTO";
        }
        else
        if (mainActivity.auxtipoRelatorio==4){
            tipoFechamento = "PRODUTO";
        }


        cupomRelatorios.append( "       "+ mainActivity.objparam.getEmitenteRazaoSocial()+ "\n") ;
        cupomRelatorios.append("     "+"FECHAMENTO: "+ diainicial+mesinicial +anoinicial+ " a "+diafinal+mesfinal+anofinal  + "\n");
        cupomRelatorios.append( "\n" );

int tamanhoListOrc = mainActivity.listadeorcamento.size();
//mainActivity.VENDER.setText(String.valueOf(mainActivity.listadeorcamento.size()));

        for (int i = 1; i < mainActivity.listadeorcamento.size(); i++) {

            objCupomRelOrc = mainActivity.listadeorcamento.get(i);

            if ((objCupomRelOrc.getTotalquantidade() >= 0)&& (objCupomRelOrc.getIdproduto()!=-1) && i != tamanhoListOrc-9)  {
                if (objCupomRelOrc.getQuantidade() == Math.rint (objCupomRelOrc.getQuantidade())) {
                    int quantidade = (int)objCupomRelOrc.getQuantidade();
                    cupomRelatorios.append( String.format("%-30.30s",tipoFechamento) + String.format("%-7.7s","Qtde")    +   String.format("%-7.7s", "TOTAL") + "\n");
                    cupomRelatorios.append( String.format("%-30.30s", deAccent( objCupomRelOrc.getNumero()))+ String.format("%-7.7s", quantidade) + String.format("%-7.2f", objCupomRelOrc.getTotal()) + "\n");
                    cupomRelatorios.append("\n");
                  //  cupomRelatorios.append("\n");
                    objCupomRelOrc.setQuantidade(quantidade);
                }
                else{
                    double quantidade = objCupomRelOrc.getQuantidade();
                    objCupomRelOrc.setQuantidade(quantidade);
                    cupomRelatorios.append( String.format("%-30.30s",tipoFechamento) + String.format("%-7.7s","Qtde")    +   String.format("%-7.7s", "TOTAL") + "\n");
                    cupomRelatorios.append( String.format("%-30.30s", deAccent( objCupomRelOrc.getNumero())) + String.format("%-7.7s", objCupomRelOrc.getQuantidade()) + String.format("%-7.2f", objCupomRelOrc.getTotal()) + "\n");

                }

            }
            else {
                    if (objCupomRelOrc.getTotalquantidade() == -2) {
                        cupomRelatorios.append(String.format("%25.25s", objCupomRelOrc.getNumero()) + "\n");
                    } else {
                        if (objCupomRelOrc.getTotalquantidade() == -5 || objCupomRelOrc.getTotalquantidade() == -7) {
                            cupomRelatorios.append(" " + String.format("%-31.31s", deAccent(objCupomRelOrc.getNumero())) + "      " + String.format("%10.3s",objCupomRelOrc.getTotal().intValue()) + "\n");
                        } else {
                            if (objCupomRelOrc.getTotalquantidade() == -3 || objCupomRelOrc.getTotalquantidade() == -4 || objCupomRelOrc.getTotalquantidade() == -6||objCupomRelOrc.getTotalquantidade() == -8||objCupomRelOrc.getTotalquantidade() == -9)
                            cupomRelatorios.append(" " + String.format("%-31.31s", deAccent(objCupomRelOrc.getNumero())) + "      " + String.format("%10.2f", objCupomRelOrc.getTotal()) + "\n");
                        }
                    }

            }
        }

    }
}
