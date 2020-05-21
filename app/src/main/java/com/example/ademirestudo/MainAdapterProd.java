package com.example.ademirestudo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.modelProdutos;
import util.TecladoContribSocial;
import util.Tecladonumerico;

public class MainAdapterProd  extends ArrayAdapter<modelProdutos> {
    MainActivity m = new MainActivity();

    private Context contextt;
    private ArrayList<modelProdutos> lista = null;
    private int requestCode;
    private int resultCode;
    private Intent data;
 private String numero = "5";

    public MainAdapterProd(Context context, ArrayList<modelProdutos> listac){
            super(context, 0, listac);

            this.lista = listac;
            this.contextt = context;

    }


    @Override
    public View getView(final int position , View convertVieww, ViewGroup parent) {
        m.atualizarlista();
       final modelProdutos itemposicao = lista.get(position );

        convertVieww = LayoutInflater.from(contextt).inflate(R.layout.row_item, null);

         TextView descricao = (TextView) convertVieww.findViewById(R.id.button);

        m.atualizarlista();
        if (position == 0 ) {
            descricao.setText(itemposicao.getDescricao());
                     descricao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  m.containerGridViewCateg.setVisibility(View.VISIBLE);
                    m.containerGridViewprod.setVisibility(View.INVISIBLE);;
                }
            });

        } else {
            descricao.setText(lista.get(position).getDescricao());
            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setColor(Color.parseColor("#fffff0"));
            gdDefault.setCornerRadius(10);
            gdDefault.setStroke(6, Color.parseColor(itemposicao.getCor()));
            descricao.setText(itemposicao.getDescricao());
            descricao.setBackground(gdDefault);
           // descricao.setBackgroundColor(Color.parseColor(itemposicao.getCor()));

           // descricao.setText(String.valueOf(itemposicao.getDescricao()));
            if (itemposicao.getPrecovariavel().equalsIgnoreCase("N")  ) {
                   descricao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.addprodvenda(itemposicao);

                    }

                });
            }


            if (itemposicao.getPrecovariavel().equalsIgnoreCase("S")  ) {
                descricao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Tecladonumerico.instance==null) {
                            m.controleteclado = 7;
                            m.itemposicaovariavel = itemposicao;
                            Intent intent = new Intent(contextt, Tecladonumerico.class);
                            ((Activity) contextt).startActivityForResult(intent, 2);
                        }

                    }

                });
            }
else
            if (itemposicao.getIdunidade() == 3  ) {
                descricao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Tecladonumerico.instance==null) {
                            m.itemposicaovariavel = itemposicao;
                            m.controleteclado = 8;
                            Intent intents = new Intent(contextt, Tecladonumerico.class);
                            ((Activity) contextt).startActivityForResult(intents, 3);
                        }

                    }

                });
            }
            else
            if (itemposicao.getIdunidade() == 2  ) {
                descricao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TecladoContribSocial.instance==null) {
                            m.itemposicaovariavel = itemposicao;
                            m.controleteclado = 8;
                            Intent intents = new Intent(contextt, TecladoContribSocial.class);
                            ((Activity) contextt).startActivityForResult(intents, 3);
                        }

                    }

                });
            }




        }
            return convertVieww;
        }



    }