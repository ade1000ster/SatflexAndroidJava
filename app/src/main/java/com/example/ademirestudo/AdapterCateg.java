package com.example.ademirestudo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.modelCategorias;

class AdapterCateg extends ArrayAdapter<modelCategorias> {
    MainActivity m = new MainActivity();

    private Context context;
    private ArrayList<modelCategorias> lista = null;



    public AdapterCateg(Context context, ArrayList<modelCategorias> listac){
        super(context, 0, listac);

        this.lista = listac;
        this.context = context;


    }

    @Override
    public View getView(final int position, View convertVieww, ViewGroup parent) {
        m.atualizarlista();
        final modelCategorias itemposicao = lista.get(position);

        convertVieww = LayoutInflater.from(context).inflate(R.layout.list_categ, null);


        TextView descricao = (TextView) convertVieww.findViewById(R.id.tvdescricao2);
        descricao.setText(itemposicao.getDescricao());

        TextView ncm = (TextView) convertVieww.findViewById(R.id.tvncm2);
        ncm.setText(itemposicao.getCodigoNcm());

        TextView produtos = (TextView) convertVieww.findViewById(R.id.tvprodutos2);
        produtos.setText(itemposicao.getQuantidade());

        TextView dataCriacao = (TextView) convertVieww.findViewById(R.id.tvdatacriacao);
        dataCriacao.setText(itemposicao.getDthrcriacao());
        // cor.setBackgroundColor( Color.parseColor (itemposicao.getCor()) );
        m.atualizarlista();
        return convertVieww;
    }


}

