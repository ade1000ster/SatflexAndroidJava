package com.example.ademirestudo.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;
import com.example.ademirestudo.database.DadosOpenHelper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class InsertNcmThread extends AsyncTask<Void,Void,Void> {
    MainActivity mainActivity = new MainActivity();
    private Context context;
    private ProgressDialog progressDialog;
    private String message;
    private AlertDialog alerta;
    StringBuffer myData;
    private static File diretorioTemp;
    private   DadosOpenHelper dadosOpenHelper;
    //dadosOpenHelper = new DadosOpenHelper(this);
   // private ProgressDialog progressDialog;
    public InsertNcmThread(Context context){
        //Initializing variables
        this.context = context;

    }
    public void conectarBanco(){
        try {
            dadosOpenHelper = new DadosOpenHelper(mainActivity.getApplicationContext());
        }catch (SQLException ex){

        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Atualização da tabela NCM","Aguarde por favor...",true,true);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ncm);
        builder.setTitle("Atualizado com sucesso");
        builder.setMessage("A tabela NCM foi atualizada com sucesso");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        alerta = builder.create();

        alerta.show();
        Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.BLUE);

        nbutton.setTextSize(20);
        nbutton.setScaleY(1);
        nbutton.setScaleX(1);
        nbutton.setX(-60);
        nbutton.setTextColor(Color.WHITE);
    }

    @Override
    protected Void doInBackground(Void... voids) {


        diretorioTemp = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/");

        try {
                FileInputStream fis = new FileInputStream( diretorioTemp + "/" + "ncm" + ".txt");
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(in));
                String strLine;

                while ((strLine = br.readLine()) != null) {
                mainActivity.addNcm(strLine);

                }
                in.close();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
