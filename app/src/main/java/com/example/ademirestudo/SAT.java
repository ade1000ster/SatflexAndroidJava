package com.example.ademirestudo;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import br.com.sweda.satserial.Java_DLLSAT;
public class SAT  extends AsyncTask<Void,Void,Void> {
    MainActivity mainActivity = new MainActivity();
    private final Java_DLLSAT dllsat;
    protected static SAT instance = null;

    SAT(Context context) {
        dllsat = new Java_DLLSAT();
    }

    public static synchronized SAT getInstance(Context context){
        if (instance == null) {
            instance = new SAT(context);
        }
        return instance;
    }
    public void identificarEth(){
        Runtime runtime = Runtime.getRuntime();
        try {

            Process process =  runtime.exec(new String[]{"/system/bin/su", "-c","/system/bin/ifconfig"});

            process.waitFor();

            InputStream inputStream = process.getInputStream();
            byte[] content = new byte[inputStream.available()];
            inputStream.read(content);
            String teste = new String(content);
            int retornoEth0 = teste.indexOf("eth0");
            int retornoEth1 = teste.indexOf("eth1");
            int posicaoInicialEth0 = retornoEth0 + 62;
            int posicaoFinalEth0 = posicaoInicialEth0 + 4;
            int posicaoInicialEth1 = retornoEth1 + 62;
            int posicaoFinalEth1 = posicaoInicialEth1 + 4;
            int posicaoInicialIpEth1 = retornoEth1 + 91;
            int posicaofinalIpEth1 = posicaoInicialIpEth1 +12;
            int posicaoInicialIpEth0 = retornoEth0 + 91;
            int posicaofinalIpEth0 = posicaoInicialIpEth0 +12;
            if (teste.substring(posicaoInicialEth0,posicaoFinalEth0).equalsIgnoreCase("r dm") && retornoEth0 != -1){
                mainActivity.objparam.setEthLocal("eth0");
                mainActivity.objparam.setIpLocal(teste.substring(posicaoInicialIpEth0,posicaofinalIpEth0));
                mainActivity.VENDER.setText(mainActivity.objparam.getEthLocal());

            }
            else{
                if (teste.substring(posicaoInicialEth0,posicaoFinalEth0).equalsIgnoreCase("r cd") && retornoEth0 != -1){
                    mainActivity.objparam.setEthSat("eth0");
                    mainActivity.VENDER.setText(mainActivity.objparam.getEthLocal());


                }
            }
            if (teste.substring(posicaoInicialEth1,posicaoFinalEth1).equalsIgnoreCase("r dm") && retornoEth1 != -1){
                mainActivity.objparam.setEthLocal("eth1");
                mainActivity.objparam.setIpLocal(teste.substring(posicaoInicialIpEth1,posicaofinalIpEth1));
                mainActivity.VENDER.setText(mainActivity.objparam.getEthLocal());

            }
            else{
                if (teste.substring(posicaoInicialEth1,posicaoFinalEth1).equalsIgnoreCase("r cd") && retornoEth1 != -1){
                    mainActivity.objparam.setEthSat("eth1");
                    mainActivity.VENDER.setText(mainActivity.objparam.getEthLocal());

                }
            }
            //FileOutputStream savedadosVenda = new FileOutputStream(diretorioXmlVenda + "/" + "ipp" + ".xml");
            //savedadosVenda.write(teste.getBytes());
            //savedadosVenda.close();
            //  VENDER.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void ativarEthSat(){

            try {

                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 down"});
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 down"});
                //   Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});

            } catch (
                    IOException e) {
                e.printStackTrace();
            }

       // pausa();
        if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth0")) {
            try{

                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});

            } catch(
                    IOException e)

            {
                e.printStackTrace();
            }}
        else{
            if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth1")) {
                try{

                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});

                } catch(
                        IOException e)

                {
                    e.printStackTrace();
                }}
        }
    }
    public void desativarEthSat(){
        if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth0")) {
            try{
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 down"});

            } catch(
                    IOException e)

            {
                e.printStackTrace();
            }}
                  else{
        if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth1")) {
            try{
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 down"});

            } catch(
                    IOException e)

            {
                e.printStackTrace();
            }
    }}}
    public void pausa(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Função para comandar a ativação do equipamento SAT.
     * Mas informações vide manual.
     * @param numeroSessao , Numero de sessão
     * @param subComando , Tipo de processo de ativação, usar sempre 1 (Certificado AC-SEFAZ)
     * @param codigoDeAtivacao , Codigo de ativação a ser usado no equipamento, evitar trocar-lo
     * @param cnpj , cnpj do contribuinte, se for de homologação deve ser o da SWEDA.
     * @param cUf , estado em que sera utilizado, apenas SP
     * @return , String de retorno do SAT.
     */
    public String ativarSat(int numeroSessao,
                            int subComando,
                            String codigoDeAtivacao,
                            String cnpj,
                            int cUf) {
        synchronized (dllsat){
            return  dllsat.AtivarSAT(numeroSessao,
                    subComando,
                    codigoDeAtivacao,
                    cnpj,
                    cUf);
        }
    }

    /**
     * Comando para executar uma venda no equipamento SAT.
     * Essa venda é validada e assinada pelo equipamento, sendo o retorno um cupom com validade fiscal.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoAtivacao , Codigo de ativação cadastrado.
     * @param xmlEnvio , xml do SAT no padrão a ser gerado pelo AC, vide manual do SAT.
     * @return , String de retorno do SAT.
     */
    public String enviarDadosVenda(final int numeroSessao,final String codigoAtivacao,final String xmlEnvio) {
        synchronized (dllsat){
            return dllsat.EnviarDadosVenda(
                    numeroSessao,
                    codigoAtivacao,
                    xmlEnvio);
        }
    }

    /**
     * Comando para cancelar uma venda no equipamento SAT dentro do prazo de 30 minutos.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoAtivacao , Codigo de ativação cadastrado.
     * @param cfe , cfe de venda a ser cancelado.
     * @param xmlEnvio , xml do SAT no padrão a ser gerado pelo AC, vide manual do SAT.
     * @return , String de retorno do SAT.
     */
    public String cancelarUltimaVenda(final int numeroSessao,
                                      final String codigoAtivacao,
                                      final String cfe,
                                      final String xmlEnvio) {
        synchronized (dllsat){
            return dllsat.CancelarUltimaVenda(
                    numeroSessao,
                    codigoAtivacao,
                    cfe,
                    xmlEnvio);
        }
    }

    /**
     * Função simples que faz uma consulta para o SAT para detectar se o mesmo esta respondendo.
     * @param numeroSessao , numero de sessão do comando.
     * @return , String de retorno do SAT.
     */
    public String consultarSAT(final int numeroSessao) {
        synchronized (dllsat){
            return dllsat.ConsultarSAT(
                    numeroSessao);
        }
    }

    /**
     * Função para executar o comando de Teste fim a fim no equipamento SAT.
     * Este comando pode ser utilizado para detectar se alguma configuração do SAT esta errada ou se
     * a sefaz esta com problemas no servidor.
     * Pode ser utilizado para validar dados do xml de venda, entretanto é necessário estar ciente
     * que não é garantido que todas as validações da venda são executadas nesse teste.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @param dadosVenda , xml do SAT no padrão a ser gerado pelo AC, vide manual do SAT.
     * @return , String de retorno do SAT.
     */
    public String testeFimAFim(int numeroSessao,
                               String codigoDeAtivacao,
                               String dadosVenda) {
        synchronized (dllsat){
            return dllsat.TesteFimAFim(
                    numeroSessao,
                    codigoDeAtivacao,
                    dadosVenda);
        }
    }

    /**
     * Comando para recuperar alguns parâmetros de funcionamento do equipamento.
     * Pode ser utilizado para acompanhamento do equipamento ou para detectar condições anormais.
     * Altamente recomendado ver no manua o retorno desse comando.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @return , String de retorno do SAT.
     */
    public String consultarStatusOperacional(int numeroSessao,
                                             String codigoDeAtivacao) {
        synchronized (dllsat){
            return dllsat.ConsultarStatusOperacional(
                    numeroSessao,
                    codigoDeAtivacao);
        }
    }

    /**
     * Comando para configurar a rede do equipamento SAT, os parâmetros devem ser passados dentro de
     * um xml, vide manual.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @param dadosConfiguracao , xml com a configuração de rede.
     * @return , String de retorno do SAT.
     */
    public String configurarInterfaceDeRede(int numeroSessao,
                                            String codigoDeAtivacao,
                                            String dadosConfiguracao) {
        synchronized (dllsat){
            return dllsat.ConfigurarInterfaceDeRede(
                    numeroSessao,
                    codigoDeAtivacao,
                    dadosConfiguracao);
        }
    }

    /**
     * Comando para executar o processo de associação de assinatura, tal comando deve ser executado
     * após a ativação para colocar o SAT no estado de uso.
     * Sua função é criar um vinculo entre o cliente e a Software house.
     *
     * !! Cuidado com esse comando pois exige que todos os cupons sejam enviados, o SAT fica
     * inoperante durante esse processo. Evite utilizar de forma automática, recomendável vincular
     * sua execução sempre a uma ação do usuário que seja clara o longo tempo de espera.
     *
     *
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @param cnpjs , string com o cnpj do cliente e o cnpj da software house concatenados.
     * @param assinatura , assinatura gerada pela software house, vide manual.
     * @return , String de retorno do SAT.
     */
    public String associarAssinatura(int numeroSessao,
                                     String codigoDeAtivacao,
                                     String cnpjs,
                                     String assinatura) {
        synchronized (dllsat){
            return dllsat.AssociarAssinatura(numeroSessao,
                    codigoDeAtivacao,
                    cnpjs,
                    assinatura);
        }
    }

    /**
     * Comando para atualizar o equipamento SAT.
     *
     * !! Cuidado com esse comando pois exige que todos os cupons sejam enviados, o SAT fica
     * inoperante durante esse processo. Evite utilizar de forma automática, recomendável vincular
     * sua execução sempre a uma ação do usuário que seja clara o longo tempo de espera.
     *
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @return , String de retorno do SAT.
     */
    public String atualizarSoftwareSAT(int numeroSessao,
                                       String codigoDeAtivacao) {
        synchronized (dllsat){
            return dllsat.AtualizarSoftwareSAT(
                    numeroSessao,
                    codigoDeAtivacao);
        }
    }

    /**
     * Comando para executar a extração do log do equipamento.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @return , String de retorno do SAT.
     */
    public String extrairLogs(int numeroSessao,
                              String codigoDeAtivacao) {
        synchronized (dllsat){
            return dllsat.ExtrairLogs(
                    numeroSessao,
                    codigoDeAtivacao);
        }
    }

    /**
     * Comando para verificar se há permissão de bloqueio na SEFAZ e, caso tenha, executa-la.
     * O SAT NÂO BLOQUEIA SEM AUTORIZAÇÃO DA RETAGUARDA.
     *
     * !! Cuidado com esse comando pois exige que todos os cupons sejam enviados, o SAT fica
     * inoperante durante esse processo. Evite utilizar de forma automática, recomendável vincular
     * sua execução sempre a uma ação do usuário que seja clara o longo tempo de espera.
     *
     * Por esse comando não gerar efeitos colaterais no SAT, costuma-se utiliza-lo para forçar o
     * descarregamento forçado dos cupons retidos no SAT.
     *
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @return , String de retorno do SAT.
     */
    public String bloquear(int numeroSessao,
                           String codigoDeAtivacao) {
        synchronized (dllsat){
            return dllsat.BloquearSAT(
                    numeroSessao,
                    codigoDeAtivacao);
        }
    }

    /**
     * Caso o SAT esteja em bloqueio do contribuinte, este comando serve para avaliar se existe a
     * permissão de desbloqueio na retaguarda e, caso exista, executa-la.
     *
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação cadastrado.
     * @return , String de retorno do SAT.
     */
    public String desbloquear(int numeroSessao,
                              String codigoDeAtivacao) {
        synchronized (dllsat){
            return dllsat.DesbloquearSAT(
                    numeroSessao,
                    codigoDeAtivacao);
        }
    }

    /**
     * Esse comando serve para trocar o código de ativação.
     * Caso o código anteriormente usado não seja conhecido, se faz necessário o uso do código de
     * emergencial.
     * @param numeroSessao , numero de sessão do comando.
     * @param codigoDeAtivacao , Código de ativação anteriormente cadastrado.
     * @param opcao , tipo de troca,
     *              1 - usando o código atual.
     *              2 - usando o código de emergência.
     * @param novoCod , Novo código de ativação
     * @param confirmaNovoCod , Confirmação do novo código de ativação.
     * @return , String de retorno do SAT.
     */
    public String trocarCodAtivacao(int numeroSessao,
                                    String codigoDeAtivacao,
                                    int opcao,
                                    String novoCod,
                                    String confirmaNovoCod) {
        synchronized (dllsat){
            return  dllsat.TrocarCodigoDeAtivacao(numeroSessao,
                    codigoDeAtivacao,
                    opcao,
                    novoCod,
                    confirmaNovoCod);
        }
    }

    /**
     * Função para verificar se a ultima sessão executada pelo equipamento é a sessão a consultada
     * e, caso seja, retornar exatamente a resposta da sessão anterior.
     * @param numeroSessao , numero de sessão do comando, recomendamos sempre passar o mesmo que o
     *                     numero a ser consultado.
     * @param codigoDeAtivacao , Código de ativação anteriormente cadastrado.
     * @param numeroSessaoConsulta , numero a ser consultado
     * @return , String de retorno do SAT.
     */
    public String ConsultarNumeroSessao(int numeroSessao,
                                        String codigoDeAtivacao,
                                        int numeroSessaoConsulta) {
        synchronized (dllsat){
            return  dllsat.ConsultarNumeroSessao(numeroSessao,
                    codigoDeAtivacao,
                    numeroSessaoConsulta);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 down"});
            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 down"});
            //   Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});

        } catch (
                IOException e) {
            e.printStackTrace();
        }

         pausa();
        if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth0")) {
            try{

                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});

            } catch(
                    IOException e)

            {
                e.printStackTrace();
            }}
        else{
            if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth1")) {
                try{

                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});

                } catch(
                        IOException e)

                {
                    e.printStackTrace();
                }}
        }
        pausa();
        executeCommand();
        return null;
    }
    public void executeCommand(){
        mainActivity.statuSat =0;
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 172.16.0.4");
            int mExitValue = mIpAddrProcess.waitFor();
            if(mExitValue==0){
                mainActivity.statuSat =1;

            }else{
                mainActivity.statuSat =0;

            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();

        }

    }
}
