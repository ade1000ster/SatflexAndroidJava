package com.example.ademirestudo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.modelDocumento;
 class AdapterReimpCupom extends ArrayAdapter<modelDocumento> {
        MainActivity m = new MainActivity();
        // AlteraProd alteraProd = new AlteraProd();

        private Context context;

        private ArrayList<modelDocumento> lista = null;
        private int valida = 1;



        public AdapterReimpCupom(Context context, ArrayList<modelDocumento> listap){
            super(context, 0, listap);

            this.lista = listap;
            this.context = context;

        }




    @Override
        public View getView(final int position, View convertVieww, ViewGroup parent) {
            final modelDocumento itemposicao = (modelDocumento) lista.get(position);
            DecimalFormat converte = new DecimalFormat("0.00");
            convertVieww = LayoutInflater.from(context).inflate(R.layout.list_reimprimircupom, null);
            TextView datadocumento = (TextView) convertVieww.findViewById(R.id.tvdatadoc);
            datadocumento.setText( itemposicao.getDthrcriacao());

            TextView totalDocumento = (TextView) convertVieww.findViewById(R.id.tvtotal);
            totalDocumento.setText( totalDocumento.getText() + converte.format(itemposicao.getTotaldocumentocdesc()));

            TextView totalItensDocumento = (TextView) convertVieww.findViewById(R.id.tvQtde);
            totalItensDocumento.setText(  String.valueOf(itemposicao.getTotalquantidade()));

            TextView cpfcnpjDocumento = (TextView) convertVieww.findViewById(R.id.tvCpfCnpj);
            cpfcnpjDocumento.setText( itemposicao.getCpfcnpj());

            TextView cooDocumento = (TextView) convertVieww.findViewById(R.id.tvcoo);
            cooDocumento.setText(cooDocumento.getText() + String.valueOf(itemposicao.getNumero()));






        return convertVieww;


        }


    }



