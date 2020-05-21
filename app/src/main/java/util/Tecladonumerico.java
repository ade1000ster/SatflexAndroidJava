package util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class Tecladonumerico extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Object instance;
    private MainActivity mainActivity = new MainActivity();
    private Button botao1;
    private Button botao2;
    private Button botao3;
    private Button botao4;
    private Button botao5;
    private Button botao6;
    private Button botao7;
    private Button botao8;
    private Button botao9;
    private Button botao0;
    private Button botaoX;
    private Button botaoC;
    private Button botaoA;
    final DecimalFormat converte = new DecimalFormat("0.00");
    public static EditText valor;
    private int click =0;
    public TextView tvTitulo;
    private TextView R$;

    Boolean bool = false;
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

        setContentView( R.layout.tecladonum);
        instance =this;
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.TOP );
        getWindow().setLayout( (int) (width * .34), (int) (height * .55) );
        valor = (EditText) findViewById( R.id.calcula);
        botao1 = (Button) findViewById(R.id.button1);
        botao2 = (Button) findViewById(R.id.button2);
        botao3 = (Button) findViewById(R.id.button3);
        botao4 = (Button) findViewById(R.id.button4);
        botao5 = (Button) findViewById(R.id.button5);
        botao6 = (Button) findViewById(R.id.button6);
        botao7 = (Button) findViewById(R.id.button7);
        botao8 = (Button) findViewById(R.id.button8);
        botao9 = (Button) findViewById(R.id.button9);
        botao0 = (Button) findViewById(R.id.button0);
        botaoA= (Button) findViewById(R.id.buttona);
        botaoX = (Button) findViewById(R.id.buttonx);
        botaoC = (Button) findViewById(R.id.buttonc);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        R$ = (TextView) findViewById( R.id.textView26);

        valor.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});;
       valor.addTextChangedListener(new Mascara(valor));
        if(mainActivity.controleteclado== 1) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotalrestante()));
            tvTitulo.setText("Dinheiro");

        }
        if(mainActivity.controleteclado== 2) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotalrestante()));
            tvTitulo.setText("Cheque");

        }
        if(mainActivity.controleteclado== 3) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotalrestante()));
            tvTitulo.setText("Cartão de crédito");

        }
        if(mainActivity.controleteclado== 4) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotalrestante()));
            tvTitulo.setText("Cartão de débito");

        }
        if(mainActivity.controleteclado== 5) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotalrestante()));
            tvTitulo.setText("Vale alimentação");

        }
        if(mainActivity.controleteclado== 6) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotalrestante()));
            tvTitulo.setText("Outros");

        }
        if(mainActivity.controleteclado == 7) {
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Preço do produto");
        }
        if(mainActivity.controleteclado == 8) {
            R$.setText("");
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Quantidade em Metros");

        }


        if(mainActivity.controleteclado == 9) {
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Preço do unitário");
        }
        if(mainActivity.controleteclado == 10) {
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Desconto do unitário");
        }
        if(mainActivity.controleteclado == 12) {
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Total de desconto");
        }
        if(mainActivity.controleteclado == 13) {
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Acréscimo unitário");
        }
        if(mainActivity.controleteclado == 15) {
            valor.setText(converte.format(0.00));
            tvTitulo.setText("Total de acréscimo");
        }
        if(mainActivity.controleteclado== 16) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotaldocumento()));
            tvTitulo.setText("Alíquota de Cofins");

        }

        if(mainActivity.controleteclado== 17) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotaldocumento()));
            tvTitulo.setText("Alíquota de PIS");

        }
        if(mainActivity.controleteclado== 18) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotaldocumento()));
            tvTitulo.setText("Alíquota de ICMS");

        }
        if(mainActivity.controleteclado== 19) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotaldocumento()));
            tvTitulo.setText("Preço de venda");

        }
        if(mainActivity.controleteclado== 20) {

            valor.setText(converte.format(mainActivity.objdocumento.getTotaldocumento()));
            tvTitulo.setText("Informe o valor");

        }

    }



    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        instance=null;
        finish();
    }

    public void Botao1(View view){

if (click ==0) {
click = 1;
valor.setText("0,00");
    String str = valor.getText().toString();
    valor.setText(str + "1");

}
else{
    String str = valor.getText().toString();
    valor.setText(str + "1");
}
    }

    public void Botao2(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "2");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "2");
        }
    }

    public void Botao3(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "3");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "3");
        }
    }

    public void Botao4(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "4");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "4");
        }
    }

    public void Botao5(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "5");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "5");
        }
    }

    public void Botao6(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "6");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "6");
        }
    }

    public void Botao7(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "7");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "7");
        }
    }

    public void Botao8(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "8");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "8");
        }
    }

    public void Botao9(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "9");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "9");
        }
    }

    public void Botao0(View view){
        if (click ==0) {
            click = 1;
            valor.setText("0,00");
            String str = valor.getText().toString();
            valor.setText(str + "0");

        }
        else{
            String str = valor.getText().toString();
            valor.setText(str + "0");
        }
    }

    public void BotaoC(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText("0,00");
        }
    }

    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

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
        String str = formatPriceSave(valor.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("KEY", str);
        this.setResult(RESULT_OK, intent);
        instance =null;
        this.finish();

    }

    public void botaoX(View v){
        instance =null;
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}