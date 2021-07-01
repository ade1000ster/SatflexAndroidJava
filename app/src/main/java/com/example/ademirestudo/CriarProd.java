package com.example.ademirestudo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        descricaoProd.setPaintFlags(descricaoProd.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        precovenda = (EditText) findViewById( R.id.editTextPrecoVenda);
        ean = (EditText) findViewById(R.id.tvEan);

        listCategoria();
        listUnidade();
        status = (Spinner) findViewById(R.id.spStatus);
        PrecoVariavel = (Spinner) findViewById(R.id.spprecovariavel);
        TextView criadoem = (EditText) findViewById(R.id.tvCriadoEm);
        criadoem.setEnabled(false);
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        criadoem.setText(dataFormatada);

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
        PrecoVariavel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (PrecoVariavel.getSelectedItemPosition()==1){
                    precovenda.setEnabled(true);
                    if(objProdutos.getPreco()==0) {
                        if (Tecladonumerico.instance==null) {
                            mainActivity.controleteclado=19;
                            Intent intent = new Intent(getApplication(), Tecladonumerico.class);
                            startActivityForResult(intent, 1);
                        }
                    }
                }
                else{
                    precovenda.setText("0,00");
                    objProdutos.setPreco(0);
                    precovenda.setEnabled(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        if(descricaoProd.getText().length()>0){
            if (PrecoVariavel.getSelectedItemPosition()==1){
                objProdutos.setPreco(  Double.parseDouble(formatPriceSave( precovenda.getText().toString() )));
                if(objProdutos.getPreco()!=0) {

                    instance = null;
                    inserirprod(conexao);
                    this.setResult(RESULT_OK);
                    this.finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setCancelable(false);
                    builder.setTitle("Atenção");
                    builder.setIcon(R.drawable.reimrimircupom);
                    builder.setMessage(" O Campo Preço Não Pode Ser Igual a Zero!");


                    final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            GradientDrawable gdDefault = new GradientDrawable();
                            gdDefault.setColor(Color.parseColor("#fffff0"));
                            gdDefault.setCornerRadius(10);
                            gdDefault.setStroke(6, Color.parseColor("#ff3333"));
                            precovenda.setBackground(gdDefault);

                        }
                    });
                    AlertDialog alerta = builder.create();

                    alerta.show();

                    Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                    pbutton.setBackgroundColor(Color.BLUE);
                    pbutton.setTextSize(20);
                    pbutton.setScaleY(1);
                    pbutton.setScaleX(1);
                    pbutton.setX(-40);
                    pbutton.setTextColor(Color.WHITE);
                }

            }
            else{
                instance = null;
                inserirprod(conexao);
                this.setResult(RESULT_OK);
                this.finish();
            }

        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setCancelable(false);
            builder.setTitle("Atenção");
            builder.setMessage(" O Campo Descrição Não Pode Ser Vazio!");

            final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    GradientDrawable gdDefault = new GradientDrawable();
                    gdDefault.setColor(Color.parseColor("#fffff0"));
                    gdDefault.setCornerRadius(10);
                    gdDefault.setStroke(6, Color.parseColor("#ff3333"));
                    descricaoProd.setBackground(gdDefault);

                }
            });
            AlertDialog alerta = builder.create();

            alerta.show();

            Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setBackgroundColor(Color.BLUE);
            pbutton.setTextSize(20);
            pbutton.setScaleY(1);
            pbutton.setScaleX(1);
            pbutton.setX(-40);
            pbutton.setTextColor(Color.WHITE);
        }

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
            int codCateg = 0;
            String descrCateg = "";
            long statusSelec = 0;
            String descrUnid = "";
            int codUnidade = 0;

            categoria = (Spinner) findViewById( R.id.spCsosn );
            Unidade = (Spinner) findViewById(R.id.spUnidade);
            descrCateg = categoria.getSelectedItem().toString();
            descrUnid = Unidade.getSelectedItem().toString();
            statusSelec = status.getSelectedItemId();
            precoVariavelSelec = PrecoVariavel.getSelectedItemId();
            codCateg = dadosOpenHelper.selecionarcateg( descrCateg );
            codUnidade = dadosOpenHelper.selecionarUnidade( descrUnid );
            objProdutos.setIdcategoria( codCateg );
            if (PrecoVariavel.getSelectedItemPosition()==0){
                objProdutos.setPreco(0);
            }
            else {
                objProdutos.setPreco(  Double.parseDouble(formatPriceSave( precovenda.getText().toString() )));
            }
            objProdutos.setDescricao( descricaoProd.getText().toString().toUpperCase() );
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
                objProdutos.setPrecovariavel( "S" );

            }
            else {
                if (precoVariavelSelec == 1)
                    objProdutos.setPrecovariavel( "N" );



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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String texto = String.valueOf(adapterView.getItemIdAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
