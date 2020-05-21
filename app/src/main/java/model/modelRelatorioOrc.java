package model;

import java.io.Serializable;

public class modelRelatorioOrc implements Serializable {
    private int idproduto;
    String numero = "Produto";
    String cliente ="a";
    double quantidade;
    double totalquantidade;
    int iddocumento;
    Double total=0.0;
    String cpfcnpj;

    public int getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(int idproduto) {
        this.idproduto = idproduto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotalquantidade() {
        return totalquantidade;
    }

    public void setTotalquantidade(double totalquantidade) {
        this.totalquantidade = totalquantidade;
    }

    public int getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(int iddocumento) {
        this.iddocumento = iddocumento;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }



    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }
}
