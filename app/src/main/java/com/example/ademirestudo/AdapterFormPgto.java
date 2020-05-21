package com.example.ademirestudo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;

import java.util.ArrayList;

import model.modelDocumentoPagamento;
import model.modelProdutos;

public class AdapterFormPgto extends ArrayAdapter<modelDocumentoPagamento> {



        MainActivity m = new MainActivity();

        private Context context;
        private ArrayList<modelDocumentoPagamento> lista = null;



        public AdapterFormPgto(Context context, ArrayList<modelDocumentoPagamento> listac){
            super(context, 0, listac);

            this.lista = listac;
            this.context = context;

        }

        @Override
        public View getView(final int position , View convertVieww, ViewGroup parent) {
            m.atualizarlista();
            final modelDocumentoPagamento itemposicao = lista.get(position );

            convertVieww = LayoutInflater.from(context).inflate(R.layout.listformpgto, null);

            final Button excluirFormPgto = (Button) convertVieww.findViewById(R.id.bExcluirFormPgto);
            final EditText descrFormPgto = (EditText) convertVieww.findViewById(R.id.tvFormpgto);
            descrFormPgto.setText(String.valueOf(itemposicao.getTotalpagamento()) );
            m.atualizarlista();


            return convertVieww;
        }


    }



