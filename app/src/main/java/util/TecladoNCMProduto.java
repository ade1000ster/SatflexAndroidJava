package util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.example.ademirestudo.R;
import java.text.NumberFormat;
import java.util.Locale;

public class TecladoNCMProduto  extends AppCompatActivity

    {
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
        private AlertDialog alerta;

        LayoutInflater layoutInflater;

        View toastLayout;

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
        getWindow().setGravity( 17 );

        getWindow().setLayout( (int) (width * .34), (int) (height * .6) );


            layoutInflater = getLayoutInflater();
            toastLayout = layoutInflater.inflate(R.layout.toastvalorinvalido, (ViewGroup) findViewById(R.id.toastvalorinvalido));
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
        R$ = (TextView) findViewById( R.id.textView26);
        tvTitulo = (TextView) findViewById( R.id.tvTitulo);
        tvTitulo.setText( "NCM do Produto" );
        R$.setText("");
        valor.setText("");
        valor.addTextChangedListener(MascaraNCM.insert(valor));

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
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "1");
            }
        }
    }

        public void Botao2(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "2");
            }
        }
    }

        public void Botao3(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "3");
            }
        }
    }

        public void Botao4(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "4");
            }
        }
    }

        public void Botao5(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "5");
            }
        }
    }

        public void Botao6(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "6");
            }
        }
    }

        public void Botao7(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "7");
            }
        }
    }

        public void Botao8(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "8");
            }
        }
    }

        public void Botao9(View view){
        if (bool == false){


            if (valor.length() < 10) {
                String str = (valor.getText().toString());
                valor.setText (str + "9");
            }
        }
    }

        public void Botao0(View view){
        if (bool == false){
            if (valor.length() < 10) {
                String str = valor.getText().toString();
                valor.setText(str + "0");
            }
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

        public final  static String formatPriceSave(String price) {
        //Ex - price = $ 5555555
        //return = 55555.55 para salvar no banco de dados
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = price.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 3, '.'));

    }
        public void BotaoA(View view){
            instance=null;
            if (valor.length()==10) {
                valor.addTextChangedListener(MascaraNCM.insert(valor));
                String str = (valor.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("KEY", str);
                this.setResult(RESULT_OK, intent);
                this.finish();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.setView(toastLayout);
                toast.show();
            }
    }

        public void botaoX(View v){
            instance=null;
            this.finish();
    }
}
