package com.example.ademirestudo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.DecimalFormat;

import model.modelDocumento;
import model.modelDocumentoProduto;
import model.modelRelatorioOrc;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.ademirestudo.R.color.finalizarorc;
import static com.example.ademirestudo.R.color.selecionado;
import static com.example.ademirestudo.R.color.semfoco;

public class InfoOrcamento extends AppCompatActivity {
    public static Object instance;
    MainActivity mainActivity = new MainActivity();
    DecimalFormat converte = new DecimalFormat( "0.00" );
    private SQLiteDatabase conexao;
    public DadosOpenHelper dadosOpenHelper;
    public Button efetivar;
    public Button excluir;
    public TextView tvnumero;
    public TextView tvcliente;
    public TextView tvquantidade;
    public TextView tvtotal;

    private AlertDialog alerta;
    private Context context;
    private modelDocumentoProduto objProdOrc;
    private modelRelatorioOrc objRelOrc;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE);
                }
            }
        });
        setContentView( R.layout.infoorcamento );
        instance=this;
        this.context = context;
        tvnumero = (TextView) findViewById( R.id.tventranumero );
        tvcliente = (TextView) findViewById( R.id.tventracliente );
        tvquantidade = (TextView) findViewById( R.id.tventraquantidade );
        tvtotal = (TextView) findViewById( R.id.tventratotal );

        efetivar = (Button) findViewById( R.id.Befetivaorc );
        excluir = (Button) findViewById( R.id.BexcluirOrc );

        objRelOrc = (modelRelatorioOrc) getIntent().getExtras().getSerializable( "Dados" );

        tvnumero.setText( String.valueOf( objRelOrc.getNumero() ) );
        tvcliente.setText( objRelOrc.getCliente() );

        if (objRelOrc.getQuantidade() == Math.rint (objRelOrc.getQuantidade())) {
            int quantidade = (int)objRelOrc.getQuantidade();
            tvquantidade.setText( String.valueOf( quantidade ) );
        }else{
            double quantidade = objRelOrc.getQuantidade();
            tvquantidade.setText( String.valueOf( quantidade ) );
        }

        tvtotal.setText( "R$ " + converte.format( objRelOrc.getTotal() ).toString() );


        DisplayMetrics dm = new DisplayMetrics();
        conectarBanco();
        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;

        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.TOP );
        getWindow().setLayout( (int) (width * .7), (int) (height * .6) );

    }

    public void voltarorcamento(View view) {
        instance=null;
        this.finish();
    }

    public void excluirOrc(SQLiteDatabase db) {

        String[] numOrcExcluir = {String.valueOf( objRelOrc.getNumero() )};

        db.delete( "documento", "iddocumento=?", numOrcExcluir );
        db.delete("documentoproduto", "iddocumento=?", numOrcExcluir  );
           }

    public void excluirOrcamento (View view){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define o titulo
            builder.setTitle("Exclusão de orçamento");

            //define a mensagem
            builder.setMessage("tem certeza que deseja excluir o orçamento?" + "\n" + "Essa ação não poderá ser desfeita.");
              //define um botão como positivo
            final AlertDialog.Builder sim = builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    excluirOrc( conexao );
                    mainActivity.atualizarlista();
                    instance=null;
                   finish();

                }
            });
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
            nbutton.setBackgroundColor(Color.RED);
            nbutton.setTextSize(20);
            nbutton.setScaleY(1);
            nbutton.setScaleX(1);
            nbutton.setX(60);
            nbutton.setTextColor(Color.WHITE);

            Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setBackgroundColor(Color.GREEN);
            pbutton.setTextSize(20);
            pbutton.setScaleY(1);
            pbutton.setScaleX(1);
            pbutton.setX(-140);

            pbutton.setTextColor(Color.WHITE);
    }

    public void conectarBanco(){
        try {
            dadosOpenHelper = new DadosOpenHelper( this );
            conexao = dadosOpenHelper.getReadableDatabase();
        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder (this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }
    }

    public void efetivarOrc (View view){
        mainActivity.numOrcamento = Integer.parseInt(objRelOrc.getNumero()) ;
       efetivarOrcc(conexao);
    }

    public void efetivarOrcc (SQLiteDatabase db){
       mainActivity.VENDER.setBackgroundResource(selecionado);
       mainActivity.layoutCupom.setVisibility(VISIBLE);
       mainActivity.layoutRelatorio.setVisibility(INVISIBLE);
       mainActivity.layoutVendas.setVisibility(VISIBLE);
        mainActivity.RELATORIO.setBackgroundResource(semfoco);
       mainActivity.listadeItens.clear();
        mainActivity.objdocumento = new modelDocumento();
        Cursor tributacao = (dadosOpenHelper.selecItensOrcamento(String.valueOf(objRelOrc.getNumero())));
           for (int i = 0; i < tributacao.getCount(); i++) {
               tributacao.moveToPosition(i);
             objProdOrc = new modelDocumentoProduto();
             objProdOrc.setIddoproduto(tributacao.getInt(0));
             objProdOrc.setDescricao(String.valueOf(tributacao.getString(3)));
             objProdOrc.setPreco(tributacao.getDouble(2));
             objProdOrc.setQuantidade(tributacao.getDouble(1));
             objProdOrc.setTotalproduto(tributacao.getDouble(2)*tributacao.getDouble(1));
             objProdOrc.setTotaldesconto(tributacao.getDouble(5));
             objProdOrc.setDescontounitario(tributacao.getDouble(4));
             objProdOrc.setAcrescimounitario(tributacao.getDouble(6));
             objProdOrc.setTotalacrescimo(tributacao.getDouble(7));
             objProdOrc.setTotalprodutocdesc((tributacao.getDouble(2)*tributacao.getDouble(1))-tributacao.getDouble(5)+objProdOrc.getTotalacrescimo());
             objProdOrc.setCfop(tributacao.getString(8));
             objProdOrc.setCsosn(tributacao.getInt(9));
             objProdOrc.setCstcofins(tributacao.getString(10));
             objProdOrc.setCstpis(tributacao.getString(11));
             objProdOrc.setOrigem(tributacao.getInt(12));
             objProdOrc.setCodigoNcm(tributacao.getString(13));
             objProdOrc.setIdunidade( selectIdunidadeProd(objProdOrc.getIddoproduto()));

             mainActivity.listadeItens.add(objProdOrc);


              mainActivity.objdocumento.setTotalquantidade( mainActivity.objdocumento.getTotalquantidade() + objProdOrc.getQuantidade());
              mainActivity.objdocumento.setTotaldocumento(mainActivity.objdocumento.getTotaldocumento()+objProdOrc.getTotalproduto());
              mainActivity.objdocumento.setTotaldesconto(Double.parseDouble(String.valueOf(mainActivity.objdocumento.getTotaldesconto() + objProdOrc.getTotaldesconto())));
              mainActivity.objdocumento.setTotalacrescimo(Double.parseDouble(String.valueOf(mainActivity.objdocumento.getTotalacrescimo() + objProdOrc.getTotalacrescimo())));
              mainActivity.objdocumento.setTotaldocumentocdesc(mainActivity.objdocumento.getTotaldocumentocdesc() + objProdOrc.getTotalprodutocdesc());
              mainActivity.objdocumento.setNumerodeprodutos(tributacao.getCount());
              mainActivity.binformarCPF.setText("CPF:" + String.valueOf(objRelOrc.getCpfcnpj()));
              mainActivity.bFinalizaVenda.setBackgroundResource(selecionado);
              mainActivity.bFinalizaOrc.setBackgroundResource(finalizarorc);

               mainActivity.atualizarlista();

           }
        instance=null;
        this.finish();
    }

    public int selectIdunidadeProd(int idproduto){

        String rawQuery = "SELECT produto.idunidade FROM produto  WHERE produto.idproduto = " + idproduto;
        SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery,  null);
        int idUnidadeprod=0;

        if  (cursor.moveToFirst()) {
            idUnidadeprod =cursor.getInt(0);
        }

    return idUnidadeprod;
    }
}
