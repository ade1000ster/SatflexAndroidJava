package model;

import java.util.Calendar;

public class modelDocumento {

    private int iddocumento;
    private double totaldocumento;
    private String nomecliente = "nao informado";
    private String cpfcnpj = "nao informado";
    private String operacao = "CU";
    private int numerodeprodutos = 0;
    private double totalquantidade = 0;
    private double totaltroco = 0;
    private double totaldesconto = 0;
    private double totaldositens = 0;
    private double totalrestante =0;
    private double totalacrescimo = 0;
    private  double totaldocumentocdesc=0;
    private String xml = "";
    private int numero = 000000;
    private String chaveCfe = "00000000000000000000000000000000000000000000";
    Calendar calendar = Calendar.getInstance();
    final int ano = calendar.get(Calendar.YEAR);
    final int mes = calendar.get(Calendar.MONTH)+1;
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int hora = calendar.get(Calendar.HOUR_OF_DAY);
    final int minuto = calendar.get(Calendar.MINUTE);
    final int segundo = calendar.get(Calendar.SECOND);
    //final int hora = calendar.get(Calendar.)
    private String dthrcriacao = ano+"-"+mes+"-"+dia+" "+hora+":"+minuto+":"+segundo;

    public int getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(int iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getChaveCfe() {
        return chaveCfe;
    }

    public void setChaveCfe(String chaveCfe) {
        this.chaveCfe = chaveCfe;
    }

    public double getTotaldocumentocdesc() {
        return totaldocumentocdesc;
    }

    public void setTotaldocumentocdesc(double totaldocumentocdesc) {
        this.totaldocumentocdesc = totaldocumentocdesc;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getTotaldocumento() {
        return totaldocumento;
    }

    public void setTotaldocumento(double totaldocumento) {
        this.totaldocumento = totaldocumento;
    }

    public double getTotalquantidade() {
        return totalquantidade;
    }

    public void setTotalquantidade(double totalquantidade) {
        this.totalquantidade = totalquantidade;
    }

    public int getNumerodeprodutos() {
        return numerodeprodutos;
    }

    public void setNumerodeprodutos(int numerodeprodutos) {
        this.numerodeprodutos = numerodeprodutos;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }


    public String getNomecliente() {
        return nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public double getTotaltroco() {
        return totaltroco;
    }

    public void setTotaltroco(double totaltroco) {
        this.totaltroco = totaltroco;
    }

    public double getTotaldesconto() {
        return totaldesconto;
    }

    public void setTotaldesconto(double totaldesconto) {
        this.totaldesconto = totaldesconto;
    }

    public double getTotaldositens() {
        return totaldositens;
    }

    public void setTotaldositens(double totaldositens) {
        this.totaldositens = totaldositens;
    }

    public double getTotalrestante() {
        return totalrestante;
    }

    public void setTotalrestante(double totalrestante) {
        this.totalrestante = totalrestante;
    }

    public double getTotalacrescimo() {
        return totalacrescimo;
    }

    public void setTotalacrescimo(double totalacrescimo) {
        this.totalacrescimo = totalacrescimo;
    }

    public String getDthrcriacao() {
        return dthrcriacao;
    }

    public void setDthrcriacao(String dthrcriacao) {
        this.dthrcriacao = dthrcriacao;
    }
}
