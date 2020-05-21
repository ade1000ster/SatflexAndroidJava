package util;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.ademirestudo.R;

public class NovaCor extends AppCompatActivity {
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
    public Button botao10;
    public Button botao11;
    public Button botao12;
    public Button botao13;
    public Button botao14;
    public Button botao15;
    public Button botao16;
    public Button botao17;
    public Button botao18;
    Boolean bool = false;

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
        setContentView( R.layout.layoutcores );
        instance=this;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics( dm );
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity( Gravity.TOP );
        getWindow().setLayout( (int) (width * .25  ), (int) (height * .29  ) );

        botao1 = (Button) findViewById( R.id.btn1);
        botao2 = (Button) findViewById( R.id.btn2);
        botao3 = (Button) findViewById( R.id.btn3);
        botao4 = (Button) findViewById( R.id.btn4);
        botao5 = (Button) findViewById( R.id.btn5);
        botao6 = (Button) findViewById( R.id.btn6);
        botao7 = (Button) findViewById( R.id.btn7);
        botao8 = (Button) findViewById( R.id.btn8);
        botao9 = (Button) findViewById( R.id.btn9);
        botao10 = (Button) findViewById( R.id.btn10);
        botao11 = (Button) findViewById( R.id.btn11);
        botao12 = (Button) findViewById( R.id.btn12);
        botao13 = (Button) findViewById( R.id.btn13);
        botao14 = (Button) findViewById( R.id.btn14);
        botao15 = (Button) findViewById( R.id.btn15);
        botao16 = (Button) findViewById( R.id.btn16);
        botao17 = (Button) findViewById( R.id.btn17);
        botao18 = (Button) findViewById( R.id.btn18);




    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        instance=null;
        finish();
    }

    public void botao1(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao1.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }

    public void botao2(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao2.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao3(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao3.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao4(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao4.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao5(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao5.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao6(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao6.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao7(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao7.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao8(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao8.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao9(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao9.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao10(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao10.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao11(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao11.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao12(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao12.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao13(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao13.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao14(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao14.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao15(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao15.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao16(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao16.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao17(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao17.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
    public void botao18(View v){
        if (bool == false) {
            ColorDrawable buttonColor = (ColorDrawable) this.botao18.getBackground();
            int colorInt = buttonColor.getColor();
            String colorHex = Integer.toHexString(colorInt);
            Intent intent = new Intent();
            intent.putExtra("KEY", colorHex);
            this.setResult(RESULT_OK, intent);
            instance=null;
            this.finish();
        }
    }
}