package com.example.ademirestudo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import model.modelDocumentoProduto;
import util.DescontoItem;



 class Adapter extends  ArrayAdapter<modelDocumentoProduto>    {

    MainActivity m = new MainActivity();
    //private DescontoItem desc;
    private AlertDialog alerta;
    private Context context;
    private ArrayList<modelDocumentoProduto> venda = null;
    private DecimalFormat converte = new DecimalFormat("0.00");

    public Adapter(Context context, ArrayList<modelDocumentoProduto> pessoas ) {
        super(context, 0, pessoas);

        this.venda = pessoas;
        this.context = context;
    }//fecha construtor



   //   23 @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        NumberFormat dff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dff).applyPattern("0.000");
        NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0.00");
        NumberFormat dfff = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)dfff).applyPattern("0");

        final modelDocumentoProduto objCupom = venda.get(position);

     //   if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
     //   }

        TextView tvDescCupom = (TextView) convertView.findViewById(R.id.tvDescCupom);
        tvDescCupom.setText(objCupom.getDescricao());

        TextView tvValorItem = (TextView) convertView.findViewById(R.id.tvValorItem);
        String valoritemS = converte.format( objCupom.getPreco() );

        double descontounitario = (objCupom.getDescontounitario());
        Intent intent;

        objCupom.setTotaldesconto(objCupom.getDescontounitario() * objCupom.getQuantidade() );
        objCupom.setTotalacrescimo(objCupom.getAcrescimounitario()* objCupom.getQuantidade());
        String subTotItem = converte.format( objCupom.getTotalprodutocdesc());
        //objCupom.setTotalprodutocdesc(Double.parseDouble(subTotItem));
        if (objCupom.getDescontounitario()>0 && objCupom.getAcrescimounitario()>0) {
            tvValorItem.setText(valoritemS + " X " + objCupom.getQuantidade() + " - R$ " + converte.format(objCupom.getTotaldesconto()) + " + R$ " + converte.format(objCupom.getTotalacrescimo()) + " = R$ " + subTotItem);
        }
        else
        if (objCupom.getDescontounitario()>0) {
            tvValorItem.setText(valoritemS + " X " + objCupom.getQuantidade() + " - R$ " + converte.format(objCupom.getTotaldesconto()) + " = R$ " + subTotItem);
        }
        else
        if (objCupom.getAcrescimounitario()>0) {
            tvValorItem.setText(valoritemS + " X " + objCupom.getQuantidade() + " + R$ " + converte.format(objCupom.getTotalacrescimo()) +  " = R$ " + subTotItem);
        }
        else {
            if (objCupom.getIdunidade()==2) {
                tvValorItem.setText(valoritemS + " X " + dff.format(objCupom.getQuantidade()) + " = R$ " + subTotItem);
            }
            else
                if (objCupom.getIdunidade()==3) {
                tvValorItem.setText(valoritemS + " X " + df.format(objCupom.getQuantidade()) + " = R$ " + subTotItem);
            }
            else  {
                    if (objCupom.getIdunidade()==1) {
                tvValorItem.setText(valoritemS + " X " +dfff.format(objCupom.getQuantidade())  + " = R$ " + subTotItem);
            }
        }}
        int i =1;
        tvDescCupom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (DescontoItem.instance == null) {

                    Intent intent = new Intent(context, DescontoItem.class);
                    startActivity(intent);
                    ((Activity) context).startActivity(intent);
                    m.descontouniario = position;
                    m.atualizarlista();
                }
            }
        });
       tvValorItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (DescontoItem.instance == null) {
                    Intent intent = new Intent(context, DescontoItem.class);
                    startActivity(intent);
                    ((Activity) context).startActivity(intent);
                    m.descontouniario = position;
                    m.atualizarlista();
                }
       }
        });

        TextView addProd = (TextView) convertView.findViewById( R.id.bAddProd );
        addProd.setOnClickListener( new View.OnClickListener() {


            public void onClick(View v) {
                objCupom.setQuantidade( objCupom.getQuantidade()+1 );
                objCupom.setTotalproduto(objCupom.getTotalproduto()+objCupom.getPreco());
                objCupom.setTotaldesconto(objCupom.getTotaldesconto() + objCupom.getDescontounitario());
                objCupom.setTotalacrescimo(objCupom.getTotalacrescimo() + objCupom.getAcrescimounitario());
                objCupom.setTotalprodutocdesc(( objCupom.getTotalprodutocdesc() + objCupom.getPreco() ) - objCupom.getDescontounitario() + objCupom.getAcrescimounitario() );
                m.objdocumento.setTotalquantidade(m.objdocumento.getTotalquantidade()+1);
                m.objdocumento.setTotaldocumento( m.objdocumento.getTotaldocumento() + objCupom.getPreco());
                m.objdocumento.setTotaldesconto(Double.parseDouble(String.valueOf(m.objdocumento.getTotaldesconto() + objCupom.getDescontounitario())));
                m.objdocumento.setTotalacrescimo(Double.parseDouble(String.valueOf(m.objdocumento.getTotalacrescimo() + objCupom.getAcrescimounitario())));
                m.objdocumento.setTotaldocumentocdesc(m.objdocumento.getTotaldocumentocdesc() + objCupom.getPreco()-objCupom.getDescontounitario()+objCupom.getAcrescimounitario());

                m.atualizarlista();
            }
        } );



        TextView subProd = (TextView) convertView.findViewById( R.id.bSubProd );

        subProd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if ( objCupom.getQuantidade()>1) {


                    objCupom.setQuantidade( objCupom.getQuantidade()-1 );
                    objCupom.setTotalproduto(objCupom.getTotalproduto()-objCupom.getPreco());
                    objCupom.setTotaldesconto(objCupom.getTotaldesconto() - objCupom.getDescontounitario());
                    objCupom.setTotalacrescimo(objCupom.getTotalacrescimo() - objCupom.getAcrescimounitario());
                    objCupom.setTotalprodutocdesc(( objCupom.getTotalprodutocdesc() - objCupom.getPreco() ) + objCupom.getDescontounitario() - objCupom.getAcrescimounitario() );
                    m.objdocumento.setTotalquantidade(m.objdocumento.getTotalquantidade()-1);
                    m.objdocumento.setTotaldocumento( m.objdocumento.getTotaldocumento() - objCupom.getPreco());
                    m.objdocumento.setTotaldesconto(Double.parseDouble(String.valueOf(m.objdocumento.getTotaldesconto() - objCupom.getDescontounitario())));
                    m.objdocumento.setTotalacrescimo(Double.parseDouble(String.valueOf(m.objdocumento.getTotalacrescimo() - objCupom.getAcrescimounitario())));
                    m.objdocumento.setTotaldocumentocdesc(m.objdocumento.getTotaldocumentocdesc() - objCupom.getPreco());
                    m.objdocumento.setTotaldocumentocdesc(m.objdocumento.getTotaldocumentocdesc() + objCupom.getDescontounitario());
                    m.objdocumento.setTotaldocumentocdesc(m.objdocumento.getTotaldocumentocdesc() - objCupom.getAcrescimounitario());

                    m.atualizarlista();
                }
            }
        } );

        TextView excProd = (TextView) convertView.findViewById( R.id.bExcluirProd );


        excProd.setOnClickListener( new View.OnClickListener (){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //define o titulo
                builder.setTitle("Excluir produto");

                //define a mensagem
                builder.setMessage("Tem certeza que deseja remover o produto? \n" + objCupom.getDescricao()+"         " +
                        "                              R$  "+ converte.format(objCupom.getPreco() * objCupom.getQuantidade()));

                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton( "SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        m.listadeItens.remove( objCupom );
                        m.objdocumento.setNumerodeprodutos( m.objdocumento.getNumerodeprodutos() -1 );
                        m.objdocumento.setTotalquantidade(m.objdocumento.getTotalquantidade() - objCupom.getQuantidade() );
                        m.objdocumento.setTotaldocumento( m.objdocumento.getTotaldocumento() - (objCupom.getTotalproduto()) );
                        m.objdocumento.setTotaldesconto(m.objdocumento.getTotaldesconto()- objCupom.getTotaldesconto());
                        m.objdocumento.setTotalacrescimo(m.objdocumento.getTotalacrescimo()- objCupom.getTotalacrescimo());
                        m.objdocumento.setTotaldocumentocdesc((m.objdocumento.getTotaldocumentocdesc()- objCupom.getTotalprodutocdesc()));

                        m.atualizarlista();

                    }
                } );
                //define um botão como negativo.
                builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        arg0.cancel();
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();
                Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor( Color.RED);
                nbutton.setTextSize(  20 );
                nbutton.setScaleY( 1 );
                nbutton.setScaleX( 1 );
                nbutton.setX( 60 );
                nbutton.setTextColor( Color.WHITE );

                Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setBackgroundColor(Color.GREEN);
                pbutton.setTextSize( 20 );
                pbutton.setScaleY( 1 );
                pbutton.setScaleX( 1 );
                pbutton.setX( -140 );

                pbutton.setTextColor( Color.WHITE );
            }


        });


        return convertView;
    }//fecha getView

    private void startActivity(Intent intent) {


    }



}//fecha classe



