package util;

import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UtilidadesGerais {


    public static int gerarNumeroSessao() {

        Random random = new Random();
        int numeroSessao = random.nextInt(999999);

        return numeroSessao;
    }

    public static String converterBase64(String texto){
        byte[] arrayBytesDecodificado = Base64.decode(texto,16);/// linha alterada em 14/06/2020
        String textoStringDecodificado = new String(arrayBytesDecodificado);
        return textoStringDecodificado;
    }
    public static long calcularDiasSemComuinic(String[] separa){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = null;
        Date dataUltimaComunicSefaz = null;
        try {
            dataAtual = sdf.parse(separa[17].

                    substring(6, 8) + "/" + separa[17].

                    substring(4, 6) + "/" + separa[17].

                    substring(0, 4));
            dataUltimaComunicSefaz = sdf.parse(separa[24].

                    substring(6, 8) + "/" + separa[24].

                    substring(4, 6) + "/" + separa[24].

                    substring(0, 4));
        } catch (java.text.ParseException e) {

        }
        long diferencaMS = dataAtual.getTime() - dataUltimaComunicSefaz.getTime();
        long diferencaSegundos = diferencaMS / 1000;
        long diferencaMinutos = diferencaSegundos / 60;
        long diferencaHoras = diferencaMinutos / 60;
        long diferencaDias = diferencaHoras / 24;
        return diferencaDias;
    }
    public static StringBuilder statusOperacional(String[] separa){

        int cooIni = 0;
        int cooFim = 0;
        int cooPendente = 0;
        StringBuilder statusSat  = new StringBuilder();
        cooIni = Integer.parseInt(separa[21].substring(31, 37));
        cooFim = Integer.parseInt(separa[22].substring(31, 37));

        if (cooFim != 0) {
            cooPendente = cooFim - cooIni + 1;

        } else {
            cooPendente = 0;
        }
        statusSat.append("          CONSULTAR STATUS OPERACIONAL" + "\n\n");
        statusSat.append(separa[2]);
        statusSat.append("\nNumero de Série: " + separa[5] + "\nTipo da LAN: " + separa[6] + "\nIP: " + separa[7] + "\nMac Adreass: " + separa[8]);
        statusSat.append("\nMascara de Rede: " + separa[9] + "\nGateway: " + separa[10] + "\nDNS1: " + separa[11] + "\nDNS2: " + separa[12] + "\nStatus Rede: " + separa[13]);
        statusSat.append("\nNivel da Bateria: " + separa[14] + "\nMemória Total: " + separa[15] + "\nMemória Usada: " + separa[16] + "\nData e Hora Atual: " + separa[17].
                substring(6, 8) + "/" + separa[17].

                substring(4, 6) + "/" + separa[17].

                substring(0, 4) + " " + separa[17].

                substring(8, 10) + ":" + separa[17].

                substring(10, 12) + ":" + separa[17].

                substring(12, 14) + "\nVersão Software Básico: " + separa[18]);
        statusSat.append("\nVersão Layout: " + separa[19] + "\nUltimo CFE Emitido: " + separa[20].

                substring(0, 28) + "\n" + separa[20].

                substring(28, 44) + "\nCFE Inicial: " + separa[21].

                substring(0, 28) + "\n" + separa[21].

                substring(28, 44) + "\nCfe Final: " + separa[22].

                substring(0, 28) + "\n" + separa[22].

                substring(28, 44));
        statusSat.append("\nCupons Pendentes: " + cooPendente);
        statusSat.append("\nUltima Comunicação Sefaz: " + separa[24].

                substring(6, 8) + "/" + separa[24].

                substring(4, 6) + "/" + separa[24].

                substring(0, 4) + " " + separa[24].

                substring(8, 10) + ":" + separa[24].

                substring(10, 12) + ":" + separa[24].

                substring(12, 14) + "\n");
        return statusSat;
    }

}
