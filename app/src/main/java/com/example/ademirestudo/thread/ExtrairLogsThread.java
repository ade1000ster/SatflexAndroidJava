package com.example.ademirestudo.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;
import com.example.ademirestudo.database.DadosOpenHelper;

    public class ExtrairLogsThread extends AsyncTask<Void,Void,Void> {
        MainActivity mainActivity = new MainActivity();
        private Context context;
        private ProgressDialog progressDialog;
        private String message;
        private AlertDialog alerta;
        StringBuffer myData;
        private DadosOpenHelper dadosOpenHelper;
        public ExtrairLogsThread(Context context){
            //Initializing variables
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Showing progress dialog while sending email
            progressDialog = ProgressDialog.show(context,"EXTRAINDO LOGS DO SAT","Aguarde por favor...",true,true);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Dismissing the progress dialog
            progressDialog.dismiss();

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.sat);
            if (mainActivity.extrairLogsSatResp ==1) {
                builder.setTitle("EXTRAIR LOGS");
                builder.setMessage("Extração do LOG do equipamento Sat concluida com sucesso!\n -Arquivo criado em:\n" + mainActivity.diretorioTemp + "/" + "LogSat" + ".txt");
            }
            else {
                builder.setTitle("ERROR");
                builder.setMessage("SEM COMUNICAÇÃO");
            }
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.cancel();
                }
            });
            alerta = builder.create();

            alerta.show();
            Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setColor(Color.parseColor("#211ca6"));
            gdDefault.setCornerRadius(4);
            gdDefault.setStroke(3, Color.parseColor("#000301"));
            nbutton.setBackground(gdDefault);
            nbutton.setTextSize(18);
            nbutton.setScaleY(3);
            nbutton.setScaleX(2);
            nbutton.setX(-60);
            nbutton.setTextColor(Color.WHITE);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {

            mainActivity.extrairLogss();
            return null;
            }
        }




