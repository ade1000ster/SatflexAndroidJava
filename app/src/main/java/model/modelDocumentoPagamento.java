package model;

import java.util.Calendar;

public class modelDocumentoPagamento {
    private int iddocumento =1;
    private int idformapagamento =1;
    private String especieformapagaento ="01";
    private double totalpagamento = 1;
    private String descricao = "aa";
    Calendar calendar = Calendar.getInstance();
    final int ano = calendar.get(Calendar.YEAR);
    final int mes = calendar.get(Calendar.MONTH)+1;
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int hora = calendar.get(Calendar.HOUR_OF_DAY);
    final int minuto = calendar.get(Calendar.MINUTE);
    final int segundo = calendar.get(Calendar.SECOND);
    //final int hora = calendar.get(Calendar.)
    private String dthrcriacao = ano+"-"+mes+"-"+dia+" "+hora+":"+minuto+":"+segundo;

    public String getDthrcriacao() {
        return dthrcriacao;
    }

    public void setDthrcriacao(String dthrcriacao) {
        this.dthrcriacao = dthrcriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEspecieformapagaento() {
        return especieformapagaento;
    }

    public void setEspecieformapagaento(String especieformapagaento) {
        this.especieformapagaento = especieformapagaento;
    }

    public int getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(int iddocumento) {
        this.iddocumento = iddocumento;
    }

    public int getIdformapagamento() {
        return idformapagamento;
    }

    public void setIdformapagamento(int idformapagamento) {
        this.idformapagamento = idformapagamento;
    }

    public double getTotalpagamento() {
        return totalpagamento;
    }

    public void setTotalpagamento(double totalpagamento) {
        this.totalpagamento = totalpagamento;
    }
}
