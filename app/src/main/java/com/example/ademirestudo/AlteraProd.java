package com.example.ademirestudo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.modelProdutos;
import util.AlterarProdTrib;
import util.Tecladonumerico;

import static com.example.ademirestudo.R.id;

public class AlteraProd extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    MainActivity mainActivity = new MainActivity();
    public static modelProdutos objProdutos;
    private DadosOpenHelper dadosOpenHelper;
    private String codigo;
    private TextView descricaoProd;
    private EditText precovenda;
    private TextView codigoprod;
    private SQLiteDatabase conexao;
    private  Spinner categoria;
    private Spinner status;
    private Spinner Unidade;
    private Spinner PrecoVariavel;
    View toastLayout;
    LayoutInflater layoutInflater;
   private final List<String> items = new ArrayList<>();
    public static AlteraProd instance;
    private static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance( Locale.getDefault()).getCurrency().getSymbol();

    }

    private static String formatPriceSave(String price) {
        //Ex - price = $ 5555555
        //return = 55555.55 para salvar no banco de dados
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = price.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));

    }

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
                    //        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView( R.layout.criarprod );
        instance =this;
        DisplayMetrics dm = new DisplayMetrics();
        conectarBanco();
        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;
        DecimalFormat converte = new DecimalFormat("0.00");
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.TOP);
        getWindow().setLayout( (int)(width*.7),(int)(height*.6) );
        descricaoProd = findViewById( id.textViewDescrProd);
        precovenda = findViewById( id.editTextPrecoVenda);
        codigoprod = findViewById( id.tvNcm );
        TextView textViewcriarprod = findViewById(id.textViewcriarprod);
        TextView criadoem = (EditText) findViewById(id.tvCriadoEm);
        String statusProd;
        final String precovarProd;
        listCategoria();

        listUnidade();
        status = findViewById(id.spStatus);
        PrecoVariavel = findViewById(id.spprecovariavel);

        ArrayAdapter<CharSequence> adapterstatus = ArrayAdapter.createFromResource(this, R.array.spStatus, android.R.layout.simple_spinner_item);
        adapterstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapterstatus);
        status.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterprecovariavel = ArrayAdapter.createFromResource(this, R.array.spprecovariavel, android.R.layout.simple_spinner_item);
        adapterprecovariavel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PrecoVariavel.setAdapter(adapterprecovariavel);
       PrecoVariavel.setOnItemSelectedListener(this);

        objProdutos = (modelProdutos) getIntent().getExtras().getSerializable("Dados");


           for (int i =0; i< items.size();i++ ) {

               if (items.get(i).equalsIgnoreCase(objProdutos.getDescricaocateg())  ){
                   categoria.setSelection(i);}
                   }



        statusProd = String.valueOf(  objProdutos.getStatus() );
        precovarProd = String.valueOf(  objProdutos.getPrecovariavel() );


        codigo = String.valueOf(objProdutos.getIdproduto());

        descricaoProd.setText (objProdutos.getDescricao());
        precovenda.setText(String.valueOf (converte.format(objProdutos.getPreco()) ));
        codigoprod.setText(objProdutos.getCodigoean());
        criadoem.setText( objProdutos.getDthrcriacao() );
        if (precovarProd.equalsIgnoreCase( "N" )){ PrecoVariavel.setSelection(0);}
        else
        if (precovarProd.equalsIgnoreCase( "S" )) {
            PrecoVariavel.setSelection( 1 );
        }
        LayoutInflater layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(R.layout.toastdadosgravado, (ViewGroup) findViewById(id.toastdadosgravado));
        PrecoVariavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

if (PrecoVariavel.getSelectedItemPosition()==0){
    if(objProdutos.getPreco()==0) {
        if (Tecladonumerico.instance==null) {
            mainActivity.controleteclado=19;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);
            startActivityForResult(intent, 1);
        }
    }
}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (statusProd.equalsIgnoreCase( "A" )){ status.setSelection(0);}
        else
        if (statusProd.equalsIgnoreCase( "I" )) {
            status.setSelection( 1 );
        }




        Unidade.setSelection(objProdutos.getIdunidade() - 1);
       textViewcriarprod.setText( "Alterando  produto: " + objProdutos.getDescricao());
   }

   public void esconderTeclado(View view){
       InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
       imm.hideSoftInputFromWindow(descricaoProd.getWindowToken(), 0);
   }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            String teste =  data.getStringExtra("KEY");

            this.precovenda.setText(teste);
        }

    }

    public void cancelarNovoprod(View view)
    {
        instance =null;
        this.finish();

    }

    public void gravarProduto (View view)
    {
        instance = null;
        alteraprod(  );
        this.setResult(RESULT_OK);
        this.finish();
    }

    private void conectarBanco(){
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

    private void listUnidade(){
        String descricao = "descricao";
        String retorno = "";
        List<String> itemsUnidade = new ArrayList<>();
        String tabela = "unidade";
        Unidade = findViewById(id.spUnidade);
        SQLiteDatabase d = dadosOpenHelper.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = d.query(tabela , new String[]{descricao},null, null, null   ,null,null,null);
        if (cursor.moveToFirst()){

            do{

                retorno = cursor.getString(0);
                itemsUnidade.add(retorno);
            }while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, itemsUnidade);

        Unidade.setAdapter(adapter);
    }

    private void listCategoria(){
        String descricao = "descricao" ;
        String cor = "cor" ;
        String retorno = "";

        String tabela = "categoria";

        categoria = findViewById( id.spCsosn );

        SQLiteDatabase d = dadosOpenHelper.getReadableDatabase();

        Cursor cursor = d.query(tabela , new String[]{descricao, cor},null, null, null   ,null,null,null);
        if (cursor.moveToFirst()){

            do{

                retorno = cursor.getString(0);
                items.add(retorno);
            }while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        categoria.setAdapter(adapter);

    }

    public void teclado (View view){
        if (Tecladonumerico.instance==null) {
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 1);

        }

    }

    public void layouTribut (View view){

        if (AlterarProdTrib.instance==null) {
            Intent intent = new Intent(getApplication(), AlterarProdTrib.class);
            //ini cia a activity desejada, enquanto está continua aberta esperando
            // um resultado
            startActivityForResult(intent, 2);
        }
    }

    private void alteraprod() {
        try {
            long statusSelec = 0;
            long precovar =0;
            int codCateg = 0;
            String descrUnid = "";

            int codUnidade = 0;
            String descrCateg = "";
            descrCateg = categoria.getSelectedItem().toString();
            codCateg = dadosOpenHelper.selecionarcateg( descrCateg );
            descrUnid = Unidade.getSelectedItem().toString();
            codUnidade = dadosOpenHelper.selecionarUnidade( descrUnid );

            statusSelec = status.getSelectedItemId();
            precovar = PrecoVariavel.getSelectedItemId();

            objProdutos.setIdproduto(Integer.parseInt(codigo));
            objProdutos.setIdcategoria(codCateg);
            objProdutos.setDescricao( descricaoProd.getText().toString() );
            objProdutos.setPreco(  Double.parseDouble(formatPriceSave( precovenda.getText().toString() )));
            objProdutos.setCodigoean(codigoprod.getText().toString());
            objProdutos.setIdunidade( codUnidade);
            if (statusSelec == 0) {
                objProdutos.setStatus( "A" );
            }
            else
                objProdutos.setStatus("I");


            if (precovar == 0) {
                objProdutos.setPrecovariavel( "N" );

            }
            else {
                if (precovar == 1)
                    objProdutos.setPrecovariavel( "S" );

            }



            dadosOpenHelper.addalteraprod(objProdutos);

            Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(toastLayout);
            toast.show();




        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Não foi possível alterar" +ex.getMessage() +ex.getCause(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
