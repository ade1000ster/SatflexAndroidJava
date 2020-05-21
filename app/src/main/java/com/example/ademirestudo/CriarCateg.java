package com.example.ademirestudo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import model.modelCategorias;
import util.NovaCor;
import util.TecladoCEST;
import util.TecladoCFOP;
import util.TecladoNCMProduto;
import util.TecladoQuantInteiro;
import util.Tecladonumerico;

public class CriarCateg extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    MainActivity  mainActivity = new MainActivity();
    public static Object instance;
    private TextView nomeTela;
    private EditText codNcm;
    private EditText descricao;
    private EditText dthrcriacao;
    private Spinner origemCateg;
    private EditText cfop;
    private Spinner csosn;
    private EditText aliqicms;
    private EditText cstpis;
    private EditText aliqpis;
    private EditText cstcofins;
    private EditText aliqcofins;
    private EditText codcontribsocial;
    private EditText cest;
    private SQLiteDatabase conexao;
    public DadosOpenHelper dadosOpenHelper;
    public EditText cor;
    public Button altera;
    public String codigo;
    public String produtos;
    public ConstraintLayout layoutVendas;
    private CheckBox cbtribut;
    Boolean bool = false;
    private AlertDialog alerta;
    View toastLayout;
    LayoutInflater layoutInflater;


    @SuppressLint("WrongViewCast")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final DecimalFormat converte = new DecimalFormat("0.00");
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
          setContentView( R.layout.criarcategoria );
          instance=this;
        DisplayMetrics dm = new DisplayMetrics();
        nomeTela = (TextView) findViewById( R.id.textViewcriarcateg);
        descricao = (EditText) findViewById( R.id.textViewDescrCateg);
        cor = (EditText) findViewById( R.id.txtviewcorcateg);
        altera = (Button) findViewById( R.id.alterarcor);
        dthrcriacao = (EditText) findViewById(R.id.tvCriadoem);
        origemCateg = (Spinner) findViewById(R.id.spOrigem);
        cfop = (EditText) findViewById(R.id.tvCfop);
        csosn = (Spinner) findViewById(R.id.spCsosn);
        aliqicms = (EditText) findViewById(R.id.tvAliqicms);
        cstpis = (EditText) findViewById(R.id.tvCstpis);
        aliqpis = (EditText) findViewById(R.id.tvAliqpis);
        cstcofins = (EditText) findViewById(R.id.tvCstcofins);
        aliqcofins = (EditText) findViewById(R.id.tvAliqcofins);
        codcontribsocial = (EditText) findViewById(R.id.tvContribsocial);
        cest = (EditText) findViewById(R.id.tvCest);
        codNcm = (EditText) findViewById(R.id.tvNcm );
        cbtribut = (CheckBox) findViewById(R.id.cbTribut );
        conectarBanco();
        getWindowManager().getDefaultDisplay().getMetrics( dm );

        ArrayAdapter<CharSequence> adapterorigem = ArrayAdapter.createFromResource(this, R.array.sporigem, android.R.layout.simple_spinner_item);
        adapterorigem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origemCateg.setAdapter(adapterorigem);
        origemCateg.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterstatus = ArrayAdapter.createFromResource(this, R.array.spcsons, android.R.layout.simple_spinner_item);
        adapterstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        csosn.setAdapter(adapterstatus);
        csosn.setOnItemSelectedListener(this);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.TOP);
        getWindow().setLayout( (int)(width*.7),(int)(height*.9) );
        layoutVendas = (ConstraintLayout)findViewById( R.id.layoutContentMainVendas);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        cbtribut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbtribut.isChecked())
                try {
                    Cursor tributacao = (dadosOpenHelper.selecTribNcm(codNcm.getText().toString()));
                    // String codigoncm = (dadosOpenHelper.selecionarCodNCM(tributacao.getString(10)));

                     aliqicms.setText(String.valueOf(converte.format(tributacao.getDouble(0))));
                    cstpis.setText(String.valueOf(tributacao.getString(1)));
                    aliqpis.setText(String.valueOf(converte.format(tributacao.getDouble(2))));
                    cstcofins.setText(String.valueOf(tributacao.getString(3)));
                    aliqcofins.setText(String.valueOf(converte.format(tributacao.getDouble(4))));
                    codcontribsocial.setText(String.valueOf(tributacao.getString(5)));
                    cfop.setText(String.valueOf(tributacao.getString(8)));
                   // cest.setText(String.valueOf(tributacao.getString(9)));
                    //montecodNcm.setText(String.valueOf(codigoncm));
                    if (tributacao.getInt(6) == 500) {

                        csosn.setSelection(3);
                    } else {
                        if (tributacao.getInt(6) == 400) {

                            csosn.setSelection(2);
                        }
                        if (tributacao.getInt(6) == 300) {

                            csosn.setSelection(1);
                        }
                        if (tributacao.getInt(6) == 102) {

                            csosn.setSelection(0);
                        }
                    }
                    origemCateg.setSelection(tributacao.getInt(7));
                }
                catch (Exception ex) {

                    // AlertDialog.Builder builder = new AlertDialog.Builder();
                    //define o titulo
                    builder.setTitle("NCM não encontrado");

                    //define a mensagem
                    builder.setMessage("o NCM informado na categoria não pôde ser encontrado");

                    //define um botão como positivo

                    //define um botão como negativo.
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.cancel();
                        }
                    });
                    //cria o AlertDialog
                    alerta = builder.create();
                    //Exibe

                    alerta.show();
                    Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setBackgroundColor(Color.BLUE);
                    nbutton.setTextSize(20);
                    nbutton.setScaleY(1);
                    nbutton.setScaleX(1);
                    nbutton.setX(-60);
                    nbutton.setTextColor(Color.WHITE);

                }

            }
        });
        LayoutInflater layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(R.layout.toastdadosgravado, (ViewGroup) findViewById(R.id.toastdadosgravado));
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


    public void tecladoCFOP (View view){
        if (TecladoCFOP.instance == null) {
            Intent intent = new Intent(getApplication(), TecladoCFOP.class);
            startActivityForResult(intent, 1);
        }
    }

    public void tecladoICMS (View view){
        if (Tecladonumerico.instance==null) {
            mainActivity.controleteclado=18;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);
            startActivityForResult(intent, 2);
        }
    }

    public void tecladoCstPis (View view){
        if (TecladoQuantInteiro.instance==null) {
            mainActivity.controleteclado=2;
            Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);
            startActivityForResult(intent, 3);
        }
    }

    public void tecladoAliqPis (View view){
        if (Tecladonumerico.instance==null) {
            mainActivity.controleteclado=17;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);
            startActivityForResult(intent, 4);
        }
    }

    public void tecladoCstCofins (View view){
        if (TecladoQuantInteiro.instance == null) {
            mainActivity.controleteclado=1;
            Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);
            startActivityForResult(intent, 5);
        }
    }

    public void tecladoAliqCofins (View view){
        if (Tecladonumerico.instance == null) {
            mainActivity.controleteclado=16;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 6);
        }
    }


    public void tecladoContribSocial (View view){
        if (TecladoQuantInteiro.instance == null) {
            mainActivity.controleteclado=3;
            Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);

            startActivityForResult(intent, 7);
        }
    }

    public void tecladoCest (View view){
        if (TecladoCEST.instance == null) {
            Intent intent = new Intent(getApplication(), TecladoCEST.class);

            startActivityForResult(intent, 8);
        }
    }

    public void tecladoNcm (View view){
        if (TecladoNCMProduto.instance == null) {
            Intent intent = new Intent(getApplication(), TecladoNCMProduto.class);

            startActivityForResult(intent, 9);
        }
    }



    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

    }
    public void esconderTeclado(View view){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(descricao.getWindowToken(), 0);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            String teste = data.getStringExtra("KEY");
            this.cfop.setText(teste);

        }

        if (resultCode == RESULT_OK && requestCode == 2) {

            String teste = data.getStringExtra("KEY");

            this.aliqicms.setText(teste);
        }

        if (resultCode == RESULT_OK && requestCode == 3) {

            String teste = data.getStringExtra("KEY");

            this.cstpis.setText(teste);
        }

        if (resultCode == RESULT_OK && requestCode == 4) {

            String teste = data.getStringExtra("KEY");

            this.aliqpis.setText(teste);
        }

        if (resultCode == RESULT_OK && requestCode == 5) {

            String teste = data.getStringExtra("KEY");

            this.cstcofins.setText(teste);
        }

        if (resultCode == RESULT_OK && requestCode == 6) {

            String teste = data.getStringExtra("KEY");

            this.aliqcofins.setText(teste);
        }
        if (resultCode == RESULT_OK && requestCode == 7) {

            String teste = data.getStringExtra("KEY");

            this.codcontribsocial.setText(teste);
        }
        if (resultCode == RESULT_OK && requestCode == 8) {

            String teste = data.getStringExtra("KEY");

            this.cest.setText(teste);
        }

        if (resultCode == RESULT_OK && requestCode == 9) {

            String teste = data.getStringExtra("KEY");

            this.codNcm.setText(teste);
        }
        if (resultCode == RESULT_OK && requestCode == 10) {

            String teste =  data.getStringExtra("KEY");
            this.cor.setText("#" + teste);
            altera.setBackgroundColor(Color.parseColor("#"+teste));
        }

    }
    public  static String formatPriceSave(String price) {
        //Ex - price = $ 5555555
        //return = 55555.55 para salvar no banco de dados
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = price.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));

    }
    // *****************************************************************************************************
    /*Objetivo = Gravar nova categoria, atraves da chamada de metodo addCategoria da classe dadosOpenHelper*
    passando o objeto categ como parametro                                                                 */
    // *****************************************************************************************************

    public void gravarcategoria (View view)
    {
        long retorno =0;
        try {
            modelCategorias categ = new modelCategorias();
            categ.setDescricao( descricao.getText().toString() );
            categ.setCor( cor.getText().toString() );
            categ.setOrigem(origemCateg.getSelectedItemPosition());
            categ.setCfop(cfop.getText().toString());
            categ.setCsosn(Integer.parseInt(csosn.getSelectedItem().toString()));
            categ.setAliqicms(Double.parseDouble(formatPriceSave(aliqicms.getText().toString())));
            categ.setCstpis(cstpis.getText().toString());
            categ.setAliqpis(Double.parseDouble(formatPriceSave(aliqpis.getText().toString())));
            categ.setCstcofins(cstcofins.getText().toString());
            categ.setAliqcofins(Double.parseDouble(formatPriceSave(aliqcofins.getText().toString())));
            categ.setCodcontribsocial(codcontribsocial.getText().toString());
           // categ.setCest(cest.getText().toString());
            categ.setIdNcm(dadosOpenHelper.selecionarNCM(codNcm.getText().toString()));
           dadosOpenHelper.addCategoria(categ);
            Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(toastLayout);
            toast.show();
            instance=null;
            if (retorno == 0) {
                Toast.makeText(getApplicationContext(), +retorno + " Categoria já existente", Toast.LENGTH_SHORT).show();
            }
            this.setResult(RESULT_OK);
            this.finish();
            Toast.makeText(getApplicationContext(), "Cadastrado Categoria", Toast.LENGTH_SHORT).show();
        }catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Não foi possível cadastrar" +ex.getMessage() +ex.getCause(), Toast.LENGTH_SHORT).show();

        }
    }


    // ******************************************************************************************
    // Objetivo = Abrir a tela com a opção de selecionar cor para a categoria a ser cadastrada   *
    // ******************************************************************************************
    public void novacor (View view){
      if(NovaCor.instance==null) {
          Intent intent = new Intent(getApplication(), NovaCor.class);
          //inicia a activity desejada, enquanto está continua aberta esperando
          // um resultado
          startActivityForResult(intent, 10);
      }
    }

    // ******************************************************************************************
   // Objetivo = Cancelar o cadastro de nova categoria                                                *
   // *******************************************************************************************
   public void cancelarcategoria(View view)
    {
        instance=null;
        this.finish();
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
