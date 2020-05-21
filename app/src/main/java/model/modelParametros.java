package model;

public class modelParametros {
    private String emitenteCNPJ = "";
    private String emitenteEndereco = "";
    private String emitenteNumero = "";
    private String emitenteComplemento = "";
    private String emitenteMunicipio = "";
    private String emitenteBairro = "";
    private String emitenteUf = "SP";
    private String emitenteRazaoSocial = "";
    private String emitenteIe = "";
    private String emitenteCep = "";
    private String desenvolvedoraCNPJ="";
    private String desenvolvedoraAssinatura="";
    private String satCaixa="001";
    private String satCodAtivacao="111111";
    private String satSerie="111111";
    private String satModelo="111111";
    private String contadorEmail = "";
    private String nome="";
    private String valor="";
    private int idparametro = 1;
    private String observacao = "E-mail de contato do contador.";
    private String grupo="";
    private String select = "N";
    private double impostoestadual=0.00;
    private double impostofederal=0.00;
    private String revendaTelefone="2755-7911";
    private String impressoraConfirm ="N";
    private String senhaConfiguracao = "";
    private String ethLocal = "eth0";
    private String ethSat = "eth1";
    private String ipLocal = "127.0.0.1";

    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }

    public String getSatModelo() {
        return satModelo;
    }

    public void setSatModelo(String satModelo) {
        this.satModelo = satModelo;
    }

    public String getEthLocal() {
        return ethLocal;
    }

    public void setEthLocal(String ethLocal) {
        this.ethLocal = ethLocal;
    }

    public String getEthSat() {
        return ethSat;
    }

    public void setEthSat(String ethSat) {
        this.ethSat = ethSat;
    }

    public String getSenhaConfiguracao() {
        return senhaConfiguracao;
    }

    public void setSenhaConfiguracao(String senhaConfiguracao) {
        this.senhaConfiguracao = senhaConfiguracao;
    }

    public String getImpressoraConfirm() {
        return impressoraConfirm;
    }

    public void setImpressoraConfirm(String impressoraConfirm) {
        this.impressoraConfirm = impressoraConfirm;
    }

    public String getRevendaTelefone() {
        return revendaTelefone;
    }

    public void setRevendaTelefone(String revendaTelefone) {
        this.revendaTelefone = revendaTelefone;
    }

    public String getSatSerie() {
        return satSerie;
    }

    public void setSatSerie(String satSerie) {
        this.satSerie = satSerie;
    }

    public String getSatCodAtivacao() {
        return satCodAtivacao;
    }

    public void setSatCodAtivacao(String satCodAtivacao) {
        this.satCodAtivacao = satCodAtivacao;
    }

    public double getImpostoestadual() {
        return impostoestadual;
    }

    public void setImpostoestadual(double impostoestadual) {
        this.impostoestadual = impostoestadual;
    }

    public double getImpostofederal() {
        return impostofederal;
    }

    public void setImpostofederal(double impostofederal) {
        this.impostofederal = impostofederal;
    }

    public String getSatCaixa() {
        return satCaixa;
    }

    public void setSatCaixa(String satCaixa) {
        this.satCaixa = satCaixa;
    }

    public String getDesenvolvedoraAssinatura() {
        return desenvolvedoraAssinatura;
    }

    public void setDesenvolvedoraAssinatura(String desenvolvedoraAssinatura) {
        this.desenvolvedoraAssinatura = desenvolvedoraAssinatura;
    }

    public String getDesenvolvedoraCNPJ() {
        return desenvolvedoraCNPJ;
    }

    public void setDesenvolvedoraCNPJ(String desenvolvedoraCNPJ) {
        this.desenvolvedoraCNPJ = desenvolvedoraCNPJ;
    }

    public String getEmitenteCep() {
        return emitenteCep;
    }

    public void setEmitenteCep(String emitenteCep) {
        this.emitenteCep = emitenteCep;
    }

    public String getEmitenteBairro() {
        return emitenteBairro;
    }

    public void setEmitenteBairro(String emitenteBairro) {
        this.emitenteBairro = emitenteBairro;
    }

    public String getEmitenteIe() {
        return emitenteIe;
    }

    public void setEmitenteIe(String emitenteIe) {
        this.emitenteIe = emitenteIe;
    }

    public String getEmitenteCNPJ() {
        return emitenteCNPJ;
    }

    public void setEmitenteCNPJ(String emitenteCNPJ) {
        this.emitenteCNPJ = emitenteCNPJ;
    }

    public String getEmitenteEndereco() {
        return emitenteEndereco;
    }

    public void setEmitenteEndereco(String emitenteEndereco) {
        this.emitenteEndereco = emitenteEndereco;
    }

    public String getEmitenteNumero() {
        return emitenteNumero;
    }

    public void setEmitenteNumero(String emitenteNumero) {
        this.emitenteNumero = emitenteNumero;
    }

    public String getEmitenteComplemento() {
        return emitenteComplemento;
    }

    public void setEmitenteComplemento(String emitenteComplemento) {
        this.emitenteComplemento = emitenteComplemento;
    }

    public String getEmitenteMunicipio() {
        return emitenteMunicipio;
    }

    public void setEmitenteMunicipio(String emitenteMunicipio) {
        this.emitenteMunicipio = emitenteMunicipio;
    }

    public String getEmitenteUf() {
        return emitenteUf;
    }

    public void setEmitenteUf(String emitenteUf) {
        this.emitenteUf = emitenteUf;
    }

    public String getEmitenteRazaoSocial() {
        return emitenteRazaoSocial;
    }

    public void setEmitenteRazaoSocial(String emitenteRazaoSocial) {
        this.emitenteRazaoSocial = emitenteRazaoSocial;
    }

    public String getContadorEmail() {
        return contadorEmail;
    }

    public void setContadorEmail(String contadorEmail) {
        this.contadorEmail = contadorEmail;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getIdparametro() {
        return idparametro;
    }

    public void setIdparametro(int idparametro) {
        this.idparametro = idparametro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}
