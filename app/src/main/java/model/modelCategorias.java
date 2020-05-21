package model;

import java.io.Serializable;

public class modelCategorias  implements Serializable {
    private int idcategoria = 0;
    private String dthrcriacao = "";
    private String descricao = "";
    private String cor = "ff";
    private String codigoNcm = " ";
    private int idNcm =1;
    private String sinctributacao ="N";
    private String quantidade;
    private int origem =1;
    private int csosn = 0;
    private Double aliqicms;
    private String cstpis;
    private Double aliqpis;
    private String cstcofins;
    private Double aliqcofins;
    private String codcontribsocial;
    private String cest = "0";
    private String cfop;

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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

    public String getSinctributacao() {
        return sinctributacao;
    }

    public void setSinctributacao(String sinctributacao) {
        this.sinctributacao = sinctributacao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
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

    public Double getAliqicms() {
        return aliqicms;
    }

    public void setAliqicms(Double aliqicms) {
        this.aliqicms = aliqicms;
    }

    public String getCstpis() {
        return cstpis;
    }

    public void setCstpis(String cstpis) {
        this.cstpis = cstpis;
    }

    public Double getAliqpis() {
        return aliqpis;
    }

    public void setAliqpis(Double aliqpis) {
        this.aliqpis = aliqpis;
    }

    public String getCstcofins() {
        return cstcofins;
    }

    public void setCstcofins(String cstcofins) {
        this.cstcofins = cstcofins;
    }

    public Double getAliqcofins() {
        return aliqcofins;
    }

    public void setAliqcofins(Double aliqcofins) {
        this.aliqcofins = aliqcofins;
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
}
