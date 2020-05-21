package model;

public class modelDocumentoPagamento {
    private int iddocumento =1;
    private int idformapagamento =1;
    private String especieformapagaento ="01";
    private double totalpagamento = 1;
    private String descricao = "aa";


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
