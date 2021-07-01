package com.example.ademirestudo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VerificarInternetStatus extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            String dadosvenda = netInfo.getExtraInfo();
            File diretorioXmlVenda;
            diretorioXmlVenda = new File(Environment.getExternalStorageDirectory() + "/satflexc/xmltttemp/");

            if (!diretorioXmlVenda.exists()) {
                diretorioXmlVenda.mkdirs();
            }
            FileOutputStream savedadosVenda = new FileOutputStream(diretorioXmlVenda + "/" + "aas" + ".xml");
            savedadosVenda.write(dadosvenda.getBytes());
            savedadosVenda.close();
            // return netInfo != null && netInfo.isConnectedOrConnecting();
            if (netInfo != null && netInfo.isConnected() ) {
                String dadosvendas = netInfo.getSubtypeName();
                File diretorioXmlVendas;
                diretorioXmlVendas = new File(Environment.getExternalStorageDirectory() + "/satflexc/xmltttemp/");

                if (!diretorioXmlVendas.exists()) {
                    diretorioXmlVendas.mkdirs();
                }
                FileOutputStream savedadosVendas = new FileOutputStream(diretorioXmlVendas + "/" + "a2a" + ".xml");
                savedadosVendas.write(dadosvendas.getBytes());
                savedadosVendas.close();
                return false;
            }
            else
            {
                return false;
            }
        }
        catch(Exception ex){
             return false;
        }
    }
    public boolean executeCommand(){
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 www.uol.com.br");
            int mExitValue = mIpAddrProcess.waitFor();
            if(mExitValue==0){
                return true;
            }else{
                return false;
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
        return false;
    }
}
