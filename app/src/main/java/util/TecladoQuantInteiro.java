
package util;

        import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;

import java.text.NumberFormat;
import java.util.Locale;

public class TecladoQuantInteiro extends AppCompatActivity {
    public static Object instance;
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
    private EditText valor;
    private TextView tvTitulo;
    private TextView R$;
    private int limitedigitos;
   private View toastLayout;
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
        layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(R.layout.toastvalorinvalido, (ViewGroup) findViewById(R.id.toastvalorinvalido));
        tvTitulo = (TextView) findViewById( R.id.tvTitulo);
        R$ = (TextView) findViewById( R.id.textView26);
        tvTitulo.setText( "CST do Pis" );
        R$.setText("");
        valor.setText("");

        limitedigitos =4;

        if(MainActivity.controleteclado== 1) {
            limitedigitos =2;
            tvTitulo.setText("CST do Cofins");
        }

        if(MainActivity.controleteclado== 2) {
            limitedigitos =2;
            tvTitulo.setText("CST do PIS");
        }
        if(MainActivity.controleteclado== 3) {
            limitedigitos =2;
            tvTitulo.setText("Contribuição social");
        }

        if(MainActivity.controleteclado== 8) {
            limitedigitos =3;
            tvTitulo.setText("Quantidade");
        }
        if(MainActivity.controleteclado== 9) {
            limitedigitos =2;
            tvTitulo.setText("Informe o valor");

        }
        if(MainActivity.controleteclado== 11) {
            limitedigitos =2;
            tvTitulo.setText("Desconto percentual");

        }
        if(MainActivity.controleteclado== 14) {
            limitedigitos =2;
            tvTitulo.setText("Acrésc percentual");

        }
        if(MainActivity.controleteclado== 20) {
            limitedigitos =6;
            tvTitulo.setText("Informe o número do cupom");

        }
        if(MainActivity.controleteclado== 21) {
            limitedigitos =6;
            tvTitulo.setText("Número do Orçamento");

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

            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "1");
            }
    }

    public void Botao2(View view){
            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "2");
            }
    }

    public void Botao3(View view){
            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "3");
            }
    }

    public void Botao4(View view){
            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "4");
            }
    }

    public void Botao5(View view){
        if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "5");
            }
    }

    public void Botao6(View view){
        if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "6");
            }
    }

    public void Botao7(View view){
            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "7");
            }
    }

    public void Botao8(View view){
            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "8");
            }
    }

    public void Botao9(View view){
            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "9");
            }
    }

    public void Botao0(View view){

            if (valor.length() < limitedigitos) {
                String str = valor.getText().toString();
                valor.setText(str + "0");
            }
    }

    public void BotaoC(View view){

            String str = valor.getText().toString();
            if (str.length()>0) {
                int indice = str.length()-1;
                valor.setText(str.substring(0,indice));

        }
    }

    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

    }


    public void BotaoA(View view){
        String str = (valor.getText().toString());
        if (valor.length()== 0){
            Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.setView(toastLayout);
            toast.show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("KEY", str);
            this.setResult(RESULT_OK, intent);
            instance = null;
            this.finish();
        }
    }

    public void botaoX(View v){
        instance=null;
        this.finish();
    }}


