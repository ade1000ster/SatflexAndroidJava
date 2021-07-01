package com.example.ademirestudo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class EfetivarVenda extends AsyncTask<Void,Void,Void> {
    MainActivity mainActivity = new MainActivity();
    private Context context;
    public EfetivarVenda(){
        //Initializing variables
        this.context = context;

    }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //Codigo
        }
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Void doInBackground(Void... params) {
        //Creating properties
       // mainActivity.efetuarVenda( );
        return null;
    }

        @Override
        protected void onPostExecute(Void numero){
            super.onPostExecute(numero);
            //Codigo
        }


    }

