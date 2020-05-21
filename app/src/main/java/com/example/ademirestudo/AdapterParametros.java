package com.example.ademirestudo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.modelParametros;

import static com.example.ademirestudo.R.color.corparametroselect;
import static com.example.ademirestudo.R.color.corparametrosemfoco;


public class AdapterParametros extends ArrayAdapter<modelParametros> {

    private Context contextt;
    private ArrayList<modelParametros> lista = null;
    private int requestCode;
    private int resultCode;

    private Context context;




    public AdapterParametros(View.OnClickListener context, ArrayList<modelParametros> listac){
        super((Context) context, 0, listac);

        this.lista = listac;
        this.context = (Context) context;
    }

    @Override
    public View getView(final int position, View convertVieww, ViewGroup parent) {
        final modelParametros itemposicao = lista.get(position);

        convertVieww = LayoutInflater.from(context).inflate(R.layout.btnparametros, null);

        final TextView descricao = (TextView) convertVieww.findViewById(R.id.btnparam);
        descricao.setText(itemposicao.getNome());



                if (itemposicao.getSelect().equalsIgnoreCase("S")) {
                    descricao.setBackgroundResource(corparametroselect);
                }

                if (itemposicao.getSelect().equalsIgnoreCase("N")) {
                    descricao.setBackgroundResource(corparametrosemfoco);
                }

        return convertVieww;
    }
}
