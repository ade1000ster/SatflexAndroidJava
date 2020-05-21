package com.example.ademirestudo;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.modelProdutos;
import util.CriarProdTrib;
import util.Tecladonumerico;


public class CriarProd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    MainActivity mainActivity = new MainActivity();
    public static Object instance;
    private SQLiteDatabase conexao;
    public DadosOpenHelper dadosOpenHelper;
    public TextView descricaoProd;
    public EditText precovenda;
    public EditText eanaltera;
    public EditText ean;
    public static Spinner categoria;
    private Spinner status;
    private Spinner Unidade;
    private Spinner PrecoVariavel;
    private long precoVariavelSelec = 0;
    public static modelProdutos objProdutos;
    View toastLayout;
    LayoutInflater layoutInflater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        setContentView( R.layout.criarprod );
        instance=this;
        DisplayMetrics dm = new DisplayMetrics();
        conectarBanco();
        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;
        objProdutos = new modelProdutos();
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.TOP);
        getWindow().setLayout( (int)(width*.7),(int)(height*.6) );
        descricaoProd = (TextView) findViewById( R.id.textViewDescrProd);
        precovenda = (EditText) findViewById( R.id.editTextPrecoVenda);
        ean = (EditText) findViewById(R.id.tvNcm );
        listCategoria();
        listUnidade();
        status = (Spinner) findViewById(R.id.spStatus);
        PrecoVariavel = (Spinner) findViewById(R.id.spprecovariavel);

        ArrayAdapter<CharSequence> adapterstatus = ArrayAdapter.createFromResource(this, R.array.spStatus, android.R.layout.simple_spinner_item);
        adapterstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapterstatus);
        status.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterprecovariavel = ArrayAdapter.createFromResource(this, R.array.spprecovariavel, android.R.layout.simple_spinner_item);
        adapterprecovariavel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PrecoVariavel.setAdapter(adapterprecovariavel);
        PrecoVariavel.setOnItemSelectedListener(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(R.layout.toastdadosgravado, (ViewGroup) findViewById(R.id.toastdadosgravado));

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
       instance=null;

        this.finish();
    }


    public void gravarProduto (View view)
    {
        instance=null;
        inserirprod( conexao );
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

    public void listUnidade(){
        String descricao = "descricao";
        String retorno = "";
        List<String> itemsUnidade = new ArrayList<String>(  );
        String tabela = "unidade";
        Unidade = (Spinner) findViewById(R.id.spUnidade);
        SQLiteDatabase d = dadosOpenHelper.getReadableDatabase();

        Cursor cursor = d.query(tabela , new String[]{descricao},null, null, null   ,null,null,null);
        if (cursor.moveToFirst()){

            do{

                retorno = cursor.getString(0);
                itemsUnidade.add(retorno);
            }while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, itemsUnidade);

        Unidade.setAdapter(adapter);
    }

    public void listCategoria(){
        String descricao = "descricao" ;
        String cor = "cor" ;
        String retorno = "";

        String tabela = "categoria";
        List<String> items = new ArrayList<String>(  );
        categoria = (Spinner) findViewById( R.id.spCsosn );

        SQLiteDatabase d = dadosOpenHelper.getReadableDatabase();

        Cursor cursor = d.query(tabela , new String[]{descricao, cor},null, null, null   ,null,null,null);
        if (cursor.moveToFirst()){

            do{

                retorno = cursor.getString(0);
                items.add(retorno);
            }while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        categoria.setAdapter(adapter);

    }
    public void teclado (View view){

        if (Tecladonumerico.instance==null) {
            mainActivity.controleteclado=19;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);
            startActivityForResult(intent, 1);
        }

    }

    public void layouTribut (View view) {

        if (CriarProdTrib.instance==null){
        Intent intent = new Intent(getApplication(), CriarProdTrib.class);
        startActivityForResult(intent, 2);
    }}
    public void inserirprod (SQLiteDatabase db) {
        try {
            //db.execSQL("CREATE TABLE produto ( id INTEGER PRIMARY KEY AUTOINCREMENT, descricao TEXT , precoprod DOUBLE(6,2), codigocateg INTEGER, FOREIGN KEY (codigocateg) REFERENCES categoria (id))");
            int codCateg = 0;
            String descrCateg = "";
            long statusSelec = 0;
            String descrUnid = "";
            int codUnidade = 0;



           // List<String> items = new ArrayList<String>();
            categoria = (Spinner) findViewById( R.id.spCsosn );
            Unidade = (Spinner) findViewById(R.id.spUnidade);
            descrCateg = categoria.getSelectedItem().toString();
            descrUnid = Unidade.getSelectedItem().toString();
            statusSelec = status.getSelectedItemId();
            precoVariavelSelec = PrecoVariavel.getSelectedItemId();
            codCateg = dadosOpenHelper.selecionarcateg( descrCateg );
            codUnidade = dadosOpenHelper.selecionarUnidade( descrUnid );
           // modelProdutos prod = new modelProdutos();
            objProdutos.setIdcategoria( codCateg );
            objProdutos.setPreco( Double.parseDouble( precovenda.getText().toString() ) );
            objProdutos.setDescricao( descricaoProd.getText().toString() );
            if (ean.length() != 0 ) {
                objProdutos.setCodigoean(ean.getText().toString());
            }
            objProdutos.setIdunidade( codUnidade);

            if (statusSelec == 0) {
                objProdutos.setStatus( "A" );
            }
                else
                objProdutos.setStatus("I");


            if (precoVariavelSelec == 0) {
                objProdutos.setPrecovariavel( "N" );

            }
            else {
                if (precoVariavelSelec == 1)
                    objProdutos.setPrecovariavel( "S" );



            }

            dadosOpenHelper.addProduto( objProdutos );

            this.setResult(RESULT_OK);
            this.finish();
            Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(toastLayout);
            toast.show();
        }catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Não foi possível cadastrar" + ex.getMessage()+ ex.getCause() + ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String texto = String.valueOf(adapterView.getItemIdAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
