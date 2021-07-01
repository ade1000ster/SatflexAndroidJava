
package com.example.ademirestudo;

import android.content.Context;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.util.ArrayList;

import model.modelCategorias;

class MainAdapterCateg extends ArrayAdapter<modelCategorias> {
    MainActivity m = new MainActivity();
    public DadosOpenHelper dadosOpenHelper;
    private Context context;
    private ArrayList<modelCategorias> lista = null;



    public MainAdapterCateg(Context context, ArrayList<modelCategorias> listac){
        super(context, 0, listac);

        this.lista = listac;
        this.context = context;


    }
    private void conectarBanc() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this.context);

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this.context);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @Override
    public View getView(final int position, View convertVieww, ViewGroup parent) {
        m.atualizarlista();
        final modelCategorias itemposicao = lista.get(position);

        convertVieww = LayoutInflater.from(context).inflate(R.layout.row_item, null);
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.parseColor(itemposicao.getCor()));
        gdDefault.setCornerRadius(10);
        gdDefault.setStroke(3, Color.parseColor("#000301"));
        final TextView descricao = (TextView) convertVieww.findViewById(R.id.button);
        descricao.setText(itemposicao.getDescricao());
        descricao.setBackground(gdDefault);
        descricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m.consultarProd(itemposicao.getIdcategoria() );

            }



        });
        return convertVieww;
    }


}




