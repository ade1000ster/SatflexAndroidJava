package model;

import java.io.Serializable;

public class modelProdutos implements Serializable {
    private int idproduto = 0;
    private String dthrcriacao = "";
    private String descricao = "jj";
    private String descricaocateg = "jj";
    private int idcategoria = 0;
    private double preco = 0;
    private double quantidade=1;
    private String codigoean = "";
    private String precovariavel ="N";
    private String codigoNcm = "0101.21.00";
    private int idNcm =1;
    private int origem =1;
    private int csosn = 0;
    private double aliqicms = 0;
    private String cstpis = "";
    private double aliqpis = 0;
    private String cstcofins = "";
    private double aliqicofins = 0;
    private String codcontribsocial = "";
    private String cest = "99";
    private String cfop = "5.102";
    private String status = "A";
    private int idunidade = 1;
    private String undSigla= "UN";
    private String cor = "ffffff";

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public String getUndSigla() {
        return undSigla;
    }

    public void setUndSigla(String undSigla) {
        this.undSigla = undSigla;
    }

    public String getDescricaocateg() {
        return descricaocateg;
    }

    public void setDescricaocateg(String descricaocateg) {
        this.descricaocateg = descricaocateg;
    }

    public String getDthrcriacao() {
        return dthrcriacao;
    }

    public void setDthrcriacao(String dthrcriacao) {
        this.dthrcriacao = dthrcriacao;
    }

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

     public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCodigoean() {
        return codigoean;
    }

    public void setCodigoean(String codigoean) {
        this.codigoean = codigoean;
    }

    public String getPrecovariavel() {
        return precovariavel;
    }

    public void setPrecovariavel(String precovariavel) {
        this.precovariavel = precovariavel;
    }

    public String getCodigoNcm() {
        return codigoNcm;
    }

    public void setCodigoNcm(String codigoNcm) {
        this.codigoNcm = codigoNcm;
    }

    public int getIdNcm() {
        return idNcm;
    }

    public void setIdNcm(int idNcm) {
        this.idNcm = idNcm;
    }

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public int getCsosn() {
        return csosn;
    }

    public void setCsosn(int csosn) {
        this.csosn = csosn;
    }


    public double getAliqicms() {
        return aliqicms;
    }

    public void setAliqicms(double aliqicms) {
        this.aliqicms = aliqicms;
    }

    public String getCstpis() {
        return cstpis;
    }

    public void setCstpis(String cstpis) {
        this.cstpis = cstpis;
    }

    public double getAliqpis() {
        return aliqpis;
    }

    public void setAliqpis(double aliqpis) {
        this.aliqpis = aliqpis;
    }

    public String getCstcofins() {
        return cstcofins;
    }

    public void setCstcofins(String cstcofins) {
        this.cstcofins = cstcofins;
    }

    public double getAliqicofins() {
        return aliqicofins;
    }

    public void setAliqicofins(double aliqicofins) {
        this.aliqicofins = aliqicofins;
    }

    public String getCodcontribsocial() {
        return codcontribsocial;
    }

    public void setCodcontribsocial(String codcontribsocial) {
        this.codcontribsocial = codcontribsocial;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int  getIdunidade() {
        return idunidade;
    }

    public void setIdunidade(int idunidade) {
        this.idunidade = idunidade;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
