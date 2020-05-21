package com.example.ademirestudo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.modelRelatorioOrc;

class AdapterRelatorioOrc extends ArrayAdapter<modelRelatorioOrc>{
    MainActivity mainActivity = new MainActivity();
    private Context context;
    private ArrayList<modelRelatorioOrc> lista;

    DecimalFormat converte = new DecimalFormat("0.00");

    public AdapterRelatorioOrc(Context context, ArrayList<modelRelatorioOrc> lista){
        super(context, 0, lista);

        this.lista = lista;
        this.context = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final modelRelatorioOrc itemposicao = lista.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.list_relatorio_orc, null);

        TextView Numero = (TextView) convertView.findViewById(R.id.tvNumerolist );
        Numero.setText(String.valueOf( itemposicao.getNumero() ));

        TextView Cliente = (TextView) convertView.findViewById(R.id.tvClientelist );
        Cliente.setText(itemposicao.getCliente());
        TextView Quantidade = (TextView) convertView.findViewById(R.id.tvQuantidadelist );
        if (itemposicao.getQuantidade() == Math.rint (itemposicao.getQuantidade())) {
            int quantidade = (int)itemposicao.getQuantidade();
            Quantidade.setText( String.valueOf( quantidade ) );
        }else{
            double quantidade = itemposicao.getQuantidade();
            Quantidade.setText( String.valueOf( quantidade ) );
        }
      //  Quantidade.setText(String.valueOf(converte.format( itemposicao.getQuantidade() )));

        TextView Total = (TextView) convertView.findViewById(R.id.tvTotallist);
        Total.setText("R$ " + converte.format(itemposicao.getTotal()).toString());
        Total.setX(-200);
        if (mainActivity.auxtipoRelatorio != 0){
            Cliente.setVisibility(View.INVISIBLE);
            Quantidade.setX(-200);

           // TextView Resumo = (TextView) convertView.findViewById(R.id.tvResumo );

        }
        if (itemposicao.getIdproduto() == -1){
            Cliente.setVisibility(View.INVISIBLE);
            Quantidade.setVisibility(View.VISIBLE);
            Total.setVisibility(View.VISIBLE);
            Numero.setVisibility(View.INVISIBLE);

        }
        if (itemposicao.getTotalquantidade() == -2){
            Cliente.setVisibility(View.INVISIBLE);
            Quantidade.setVisibility(View.INVISIBLE);
            Total.setVisibility(View.INVISIBLE);
            Numero.setX(480);
            Numero.setTextSize(22);
        }
        if (itemposicao.getTotalquantidade() <=-3){
            Cliente.setVisibility(View.INVISIBLE);
            Quantidade.setVisibility(View.INVISIBLE);
            Total.setVisibility(View.VISIBLE);
        }
        if (itemposicao.getTotalquantidade() == -5 || itemposicao.getTotalquantidade() == -7 ){
            Cliente.setVisibility(View.INVISIBLE);
            Quantidade.setVisibility(View.INVISIBLE);
            Total.setVisibility(View.VISIBLE);
            Total.setText(  String.valueOf(itemposicao.getTotal().intValue()));
        }
        if (itemposicao.getTotalquantidade() == -10){
            Cliente.setVisibility(View.INVISIBLE);
            Quantidade.setVisibility(View.VISIBLE);
            Total.setVisibility(View.VISIBLE);
            Quantidade.setText("Quantidade");
            Total.setText("Total");
            Quantidade.setTextSize(20);
            Numero.setTextSize(20);
            Total.setTextSize(20);
            Quantidade.setX(-300);
            Total.setX(-200);

        }
        if (mainActivity.auxtipoRelatorio ==0){
            Cliente.setVisibility(View.VISIBLE);
            Quantidade.setVisibility(View.VISIBLE);
            Total.setVisibility(View.VISIBLE);
            if (itemposicao.getTotalquantidade()== -11) {
                Quantidade.setText("Quantidade");
                Total.setText("Total");
                Quantidade.setTextSize(20);
                Cliente.setTextSize(20);
                Numero.setTextSize(20);
                Total.setTextSize(20);
            }
            if (itemposicao.getIdproduto() == -1){
                Cliente.setVisibility(View.INVISIBLE);
                Quantidade.setVisibility(View.VISIBLE);
                Total.setVisibility(View.VISIBLE);
                Numero.setVisibility(View.INVISIBLE);

            }
            Cliente.setX(-300);
            Quantidade.setX(-200);
            Total.setX(-150);
        }

       // m.totValRelOrc.setText( String.valueOf  ( itemposicao.getValorTotalRel()) );
       // m.totQuantRelOrc.setText( itemposicao.getTotal().toString() );


        return convertView;


    }


}

