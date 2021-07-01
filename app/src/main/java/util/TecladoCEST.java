package util;

        import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ademirestudo.R;

import java.text.NumberFormat;
import java.util.Locale;
public class TecladoCEST extends AppCompatActivity {
    public static Object instance;

    public Button botao1;
    public Button botao2;
    public Button botao3;
    public Button botao4;
    public Button botao5;
    public Button botao6;
    public Button botao7;
    public Button botao8;
    public Button botao9;
    public Button botao0;
    public Button botaoX;
    public Button botaoC;
    public Button botaoA;
    public EditText valor;
    public TextView tvTitulo;


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
        instance=this;
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.CENTER );
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
        tvTitulo = (TextView) findViewById( R.id.tvTitulo);
        tvTitulo.setText( "CEST" );
        Locale mLocale = new Locale("pt", "BR");
        valor.addTextChangedListener(new Mascara(valor));


    }



    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        instance=null;
        finish();
    }

    public void Botao1(View view){
        if (bool == false){

            String str = valor.getText().toString();
            valor.setText(str + "1");
        }
    }

    public void Botao2(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "2");
        }
    }

    public void Botao3(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "3");
        }
    }

    public void Botao4(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "4");
        }
    }

    public void Botao5(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "5");
        }
    }

    public void Botao6(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "6");
        }
    }

    public void Botao7(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "7");
        }
    }

    public void Botao8(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "8");
        }
    }

    public void Botao9(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "9");
        }
    }

    public void Botao0(View view){
        if (bool == false){
            String str = valor.getText().toString();
            valor.setText(str + "0");
        }
    }

    public void BotaoC(View view){
        if (bool == false){
            String str = valor.getText().toString();
            if (str.length()>0) {
                int indice = str.length()-1;
                valor.setText(str.substring(0,indice));
            }
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
        instance=null;
        this.finish();
    }

    public void botaoX(View v){
        instance=null;

        this.finish();
    }
}


