package com.example.ademirestudo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.modelProdutos;

class AdapterProd extends ArrayAdapter<modelProdutos>{
    MainActivity m = new MainActivity();
   // AlteraProd alteraProd = new AlteraProd();

    private Context context;

    private ArrayList<modelProdutos> lista = null;
    private int valida = 1;



    public AdapterProd(Context context, ArrayList<modelProdutos> listap){
        super(context, 0, listap);

        this.lista = listap;
        this.context = context;

    }

    @Override
    public View getView(final int position, View convertVieww, ViewGroup parent) {
        final modelProdutos itemposicao = lista.get(position);
        DecimalFormat converte = new DecimalFormat("0.00");
        convertVieww = LayoutInflater.from(context).inflate(R.layout.list_prod, null);





        TextView descricao = (TextView) convertVieww.findViewById(R.id.tvdescricao);
        descricao.setText(itemposicao.getDescricao());

        TextView categoria = (TextView) convertVieww.findViewById(R.id.tvcategoria);
        categoria.setText(itemposicao.getDescricaocateg());

        TextView preco = (TextView) convertVieww.findViewById(R.id.tvpreco);
        preco.setText( converte.format (itemposicao.getPreco()));

        TextView ean = (TextView) convertVieww.findViewById(R.id.tvean);
        ean.setText(itemposicao.getCodigoean());

        TextView dataCriacao = (TextView) convertVieww.findViewById(R.id.tvdatacriacao   );
        dataCriacao.setText(itemposicao.getDthrcriacao());



        return convertVieww;


    }


}

