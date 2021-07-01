package util;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ademirestudo.AlteraProd;
import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;
import com.example.ademirestudo.database.DadosOpenHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;




public class AlterarProdTrib extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Object instance;
    AlteraProd m = new AlteraProd();
    MainActivity  mainActivity = new MainActivity();

    private SQLiteDatabase conexao;
    public DadosOpenHelper dadosOpenHelper;
    private EditText codNcm;
    private Spinner csosn;
    private Spinner origemProd;
    private EditText aliqIcms;
    private EditText cstpis;
    private EditText aliqpis;
    private EditText cstcofins;
    private EditText aliqicofins;
    private EditText codcontribsocial;
    private EditText cest;
    private EditText cfop;
    private CheckBox cbtribut;
    Boolean bool = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
                }
            }
        });
        instance =this;
        setContentView( R.layout.criarprodtribut);
        DisplayMetrics dm = new DisplayMetrics();
        conectarBanco();
        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.RELATIVE_LAYOUT_DIRECTION );
        getWindow().setLayout( (int)(width*.7),(int)(height*.7) );
        csosn = (Spinner) findViewById(R.id.spCsosn);
        origemProd = (Spinner) findViewById(R.id.spOrigem);
        aliqIcms = (EditText) findViewById(R.id.tvAliqicms);
        cstpis = (EditText) findViewById(R.id.tvCstPis);
        aliqpis = (EditText) findViewById(R.id.tvAliqPis);
        cstcofins = (EditText) findViewById(R.id.tvCstCofins );
        aliqicofins = (EditText) findViewById(R.id.tvAliqCofins );
        codcontribsocial = (EditText) findViewById(R.id.tvCodContribSocial);
        cest = (EditText) findViewById(R.id.tvCest);
        cfop = (EditText) findViewById(R.id.tvCfop);
        codNcm = (EditText) findViewById(R.id.tvEan);
        cbtribut = (CheckBox) findViewById(R.id.cbTribut );
        ArrayAdapter<CharSequence> adapterstatus = ArrayAdapter.createFromResource(this, R.array.spcsons, android.R.layout.simple_spinner_item);
        adapterstatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        csosn.setAdapter(adapterstatus);
        csosn.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterorigem = ArrayAdapter.createFromResource(this, R.array.sporigem, android.R.layout.simple_dropdown_item_1line);
        adapterorigem.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        origemProd.setAdapter(adapterorigem);
        origemProd.setOnItemSelectedListener(this);
        if (m.objProdutos.getCsosn() == 500){

            csosn.setSelection( 3 );}
        else{
            if (m.objProdutos.getCsosn() == 400){

                csosn.setSelection( 2 );}
            if (m.objProdutos.getCsosn() == 300){

                csosn.setSelection(  1);}
            if (m.objProdutos.getCsosn() == 102){

                csosn.setSelection( 0 );}
        }

        aliqIcms.setText(String.valueOf(converte.format(m.objProdutos.getAliqicms())));
        cstpis.setText(String.valueOf(m.objProdutos.getCstpis()));
        aliqpis.setText(String.valueOf(converte.format(m.objProdutos.getAliqpis())));
        cstcofins.setText(String.valueOf(m.objProdutos.getCstcofins()));
        aliqicofins.setText(String.valueOf(converte.format(m.objProdutos.getAliqicofins())));
        codcontribsocial.setText(String.valueOf(m.objProdutos.getCodcontribsocial()));
        cfop.setText(String.valueOf(m.objProdutos.getCfop()));
        codNcm.setText(String.valueOf(m.objProdutos.getCodigoNcm()));
        cest.setText(String.valueOf(m.objProdutos.getCest()));
        origemProd.setSelection(m.objProdutos.getOrigem());
        cbtribut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor tributacao = (dadosOpenHelper.selectribcateg(m.objProdutos.getDescricaocateg()));
                String codigoncm = (dadosOpenHelper.selecionarCodNCM(tributacao.getString(10)));
                if (cbtribut.isChecked())
                aliqIcms.setText(String.valueOf(converte.format(tributacao.getDouble(0))));
                cstpis.setText(String.valueOf(tributacao.getString(1)));
                aliqpis.setText(String.valueOf(converte.format(tributacao.getDouble(2))));
                cstcofins.setText(String.valueOf(tributacao.getString(3)));
                aliqicofins.setText(String.valueOf(converte.format(tributacao.getDouble(4))));
                codcontribsocial.setText(String.valueOf(tributacao.getString(5)));
                cfop.setText(String.valueOf(tributacao.getString(8)));
                codNcm.setText(String.valueOf(codigoncm));
                if (tributacao.getInt(6) == 500){

                    csosn.setSelection( 3 );}
                else{
                    if (tributacao.getInt(6) == 400){

                        csosn.setSelection( 2 );}
                    if (tributacao.getInt(6) == 300){

                        csosn.setSelection(  1);}
                    if (tributacao.getInt(6) == 102){

                        csosn.setSelection( 0 );}
                }
                origemProd.setSelection(tributacao.getInt(7));

            }
        });

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

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
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

    public void BotaoC(View view){
        if (bool == false){
            String str = codNcm.getText().toString();

            codNcm.setText("");
        }
    }

    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            String teste =  data.getStringExtra("KEY");
            this.cfop.setText(teste);

        }

        if (resultCode == RESULT_OK && requestCode == 2) {

            String teste =  data.getStringExtra("KEY");

            this.aliqIcms.setText(teste);        }

        if (resultCode == RESULT_OK && requestCode == 3) {

            String teste =  data.getStringExtra("KEY");

            this.cstpis.setText(teste);        }

        if (resultCode == RESULT_OK && requestCode == 4) {

            String teste =  data.getStringExtra("KEY");

            this.aliqpis.setText(teste);        }

        if (resultCode == RESULT_OK && requestCode == 5) {

            String teste =  data.getStringExtra("KEY");

            this.cstcofins.setText(teste);        }

        if (resultCode == RESULT_OK && requestCode == 6) {

            String teste =  data.getStringExtra("KEY");

            this.aliqicofins.setText(teste);        }
        if (resultCode == RESULT_OK && requestCode == 7) {

            String teste =  data.getStringExtra("KEY");

            this.codcontribsocial.setText(teste);        }
        if (resultCode == RESULT_OK && requestCode == 8) {

            String teste =  data.getStringExtra("KEY");

            this.cest.setText(teste);        }

        if (resultCode == RESULT_OK && requestCode == 9) {

            String teste =  data.getStringExtra("KEY");
           // codNcm.addTextChangedListener(MascaraNCM.insert(codNcm));
            this.codNcm.setText(teste);        }



    }
    public  static String formatPriceSave(String price) {
        //Ex - price = $ 5555555
        //return = 55555.55 para salvar no banco de dados
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = price.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));

    }
    public void BotaoA(View view){



            m.objProdutos.setCsosn(Integer.parseInt(csosn.getSelectedItem().toString()));
            m.objProdutos.setOrigem(origemProd.getSelectedItemPosition());
            m.objProdutos.setCfop(cfop.getText().toString());
            m.objProdutos.setCstcofins(cstcofins.getText().toString());
            m.objProdutos.setCfop(cfop.getText().toString());
            m.objProdutos.setCodigoNcm(codNcm.getText().toString());
            m.objProdutos.setCest(cest.getText().toString());
            m.objProdutos.setCstpis(cstpis.getText().toString());
            m.objProdutos.setCodcontribsocial(codcontribsocial.getText().toString());
            m.objProdutos.setAliqicms(Double.parseDouble(formatPriceSave(aliqIcms.getText().toString())));
            m.objProdutos.setAliqpis(Double.parseDouble(formatPriceSave(aliqpis.getText().toString())));
            m.objProdutos.setAliqicofins(Double.parseDouble(formatPriceSave(aliqicofins.getText().toString())));
            try {
                m.objProdutos.setIdNcm(dadosOpenHelper.selecionarNCM(codNcm.getText().toString()));
            }
            catch (Exception e){

            }
        instance =null;
            this.finish();

    }

    public void botaoX(View v){
        instance =null;
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
