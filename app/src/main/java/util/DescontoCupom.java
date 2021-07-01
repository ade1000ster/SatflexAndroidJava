package util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import model.modelDocumentoProduto;


public class DescontoCupom extends AppCompatActivity {
    MainActivity m = new MainActivity();
    final DecimalFormat converte = new DecimalFormat("0.00");
    private TextView tvDescPerc;
    private TextView tvDescVal;
    private TextView tvTotFinal;
    private TextView tvTotalItens;
    private TextView tvTitulo;
    private Button confirmar;
    private Button voltar;
    private  modelDocumentoProduto objCupom2;
    public static int codpro =0;
    private double descontoval;
    private double descontoperc;
    private double totfinal;
    private double descontoTotalItem;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DecimalFormat converte = new DecimalFormat("0.00");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descontocupom);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity(Gravity.TOP);
        getWindow().setLayout((int) (width * .34), (int) (height * .55));
        tvTotalItens= (TextView) findViewById(R.id.tvTotalItens);
       tvDescPerc= (TextView) findViewById(R.id.tvDescPerc);
       tvDescVal= (TextView) findViewById(R.id.tvDescVal);
        tvTotFinal= (TextView) findViewById(R.id.tvTotFinal);
        tvTitulo= (TextView) findViewById(R.id.tvTitulo);
        tvTitulo.setText("Aplicando desconto...");
        confirmar= (Button) findViewById(R.id.btConfirmar);
        voltar= (Button) findViewById(R.id.btnVoltar);
        tvTotalItens.setText(String.valueOf(converte.format(m.objdocumento.getTotaldocumento())));
        tvDescPerc.setOnClickListener(selectDescPerc);
        tvDescVal.setOnClickListener(selectDescVal);
        objCupom2 = new modelDocumentoProduto();
        descontoval =0;
        descontoperc =0;
        totfinal=0;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US); ((DecimalFormat)df).applyPattern("0.00");
            String retornoTeclado = data.getStringExtra("KEY");
            descontoperc = Double.parseDouble(retornoTeclado);
            descontoval = Double.parseDouble(df.format(descontoperc *(m.objdocumento.getTotaldocumento())/100 ));
            totfinal = (m.objdocumento.getTotaldocumento() - descontoval);
            tvDescPerc.setText( retornoTeclado);
            tvDescVal.setText(String.valueOf(converte.format(descontoval)));
            tvTotFinal.setText(String.valueOf(converte.format(totfinal)));
            m.objdocumento.setTotaldocumentocdesc(Double.valueOf(totfinal + m.objdocumento.getTotalacrescimo()));
            m.objdocumento.setTotaldesconto(descontoval);

            for (int i = 1; i <= m.listadeItens.size(); i++) {
                objCupom2 = new modelDocumentoProduto();
                objCupom2 = m.listadeItens.get(i-1);
                objCupom2.setDescontounitario(Double.parseDouble(df.format(( objCupom2.getPreco() * (descontoperc)/100))));
                objCupom2.setTotaldesconto(Double.parseDouble(df.format(objCupom2.getDescontounitario()*objCupom2.getQuantidade())));
                descontoTotalItem = Double.parseDouble(df.format(descontoTotalItem + objCupom2.getTotaldesconto()));
                objCupom2.setTotalprodutocdesc(objCupom2.getTotalproduto()-objCupom2.getTotaldesconto()+objCupom2.getTotalacrescimo());
                m.listadeItens.set(i-1,objCupom2);
                m.atualizarlista();
            }

            if (Double.valueOf(descontoTotalItem) < Double.valueOf(descontoval )){
                double diferenca = Double.parseDouble(df.format(descontoval -descontoTotalItem));
                objCupom2 = new modelDocumentoProduto();
                int lastIndex = m.listadeItens.size();
                objCupom2 = m.listadeItens.get(lastIndex -1);
                objCupom2.setDescontounitario(Double.parseDouble(df.format(( objCupom2.getPreco() * (descontoperc)/100)+ diferenca)));
                objCupom2.setTotaldesconto( objCupom2.getDescontounitario() * objCupom2.getQuantidade() );
                objCupom2.setTotalprodutocdesc(objCupom2.getTotalproduto()-objCupom2.getTotaldesconto()+objCupom2.getTotalacrescimo());
                m.listadeItens.set(lastIndex-1,objCupom2);
                m.atualizarlista();
            }else
            {
            if (Double.valueOf(descontoTotalItem) > Double.valueOf(descontoval )){
                double diferenca =Double.parseDouble(df.format(descontoTotalItem - descontoval));
                objCupom2 = new modelDocumentoProduto();
                int lastIndex = m.listadeItens.size();
                objCupom2 = m.listadeItens.get(lastIndex -1);
                objCupom2.setDescontounitario(Double.parseDouble(df.format(( objCupom2.getPreco() * (descontoperc)/100)- diferenca)));
                objCupom2.setTotaldesconto(objCupom2.getDescontounitario()*objCupom2.getQuantidade());
                objCupom2.setTotalprodutocdesc(objCupom2.getTotalproduto()-objCupom2.getTotaldesconto()+objCupom2.getTotalacrescimo());
                m.listadeItens.set(lastIndex-1,objCupom2);
                m.atualizarlista();
            }}

        }


        if (resultCode == RESULT_OK && requestCode == 2) {
            NumberFormat df = NumberFormat.getCurrencyInstance(Locale.US);
            ((DecimalFormat) df).applyPattern("0.00");
            String retornoTeclado = data.getStringExtra("KEY");
            descontoperc = (Double.parseDouble(retornoTeclado) / (m.objdocumento.getTotaldocumento()) * 100);
            tvDescPerc.setText(String.format("%2.0f", (Double.parseDouble(retornoTeclado) / (m.objdocumento.getTotaldocumento()) * 100)));
            descontoval = (Double.parseDouble(retornoTeclado));
            totfinal = (m.objdocumento.getTotaldocumento() - (Double.parseDouble(retornoTeclado)));
            tvDescVal.setText(String.valueOf(converte.format(descontoval)));
            tvTotFinal.setText(String.valueOf(converte.format(totfinal)));
            m.objdocumento.setTotaldocumentocdesc(Double.valueOf(Double.valueOf(totfinal + m.objdocumento.getTotalacrescimo())));
            m.objdocumento.setTotaldesconto(descontoval);

            for (int i = 1; i <= m.listadeItens.size(); i++) {
                objCupom2 = new modelDocumentoProduto();
                objCupom2 = m.listadeItens.get(i - 1);
                objCupom2.setDescontounitario(objCupom2.getPreco() * (descontoperc) / 100);
                objCupom2.setTotaldesconto(objCupom2.getDescontounitario() * objCupom2.getQuantidade());
                descontoTotalItem += Double.parseDouble(df.format(objCupom2.getTotaldesconto()));
                objCupom2.setTotalprodutocdesc(objCupom2.getTotalproduto() - objCupom2.getTotaldesconto() + objCupom2.getTotalacrescimo());
                m.listadeItens.set(i - 1, objCupom2);
                m.atualizarlista();
            }
            if (Double.valueOf(descontoTotalItem) < Double.valueOf(descontoval)) {
                double diferenca = Double.parseDouble(df.format(descontoval - descontoTotalItem));
                objCupom2 = new modelDocumentoProduto();
                int lastIndex = m.listadeItens.size();
                objCupom2 = m.listadeItens.get(lastIndex - 1);
                objCupom2.setDescontounitario(Double.parseDouble(df.format((objCupom2.getPreco() * (descontoperc) / 100) + diferenca)));
                objCupom2.setTotaldesconto(objCupom2.getDescontounitario() * objCupom2.getQuantidade());
                objCupom2.setTotalprodutocdesc(objCupom2.getTotalproduto() - objCupom2.getTotaldesconto() + objCupom2.getTotalacrescimo());
                m.listadeItens.set(lastIndex - 1, objCupom2);
                m.atualizarlista();
            } else {
                if (Double.valueOf(descontoTotalItem) > Double.valueOf(descontoval)) {
                    double diferenca =Double.parseDouble(df.format(descontoTotalItem - descontoval));
                    objCupom2 = new modelDocumentoProduto();
                    int lastIndex = m.listadeItens.size();
                    objCupom2 = m.listadeItens.get(lastIndex - 1);
                    objCupom2.setDescontounitario(Double.parseDouble(df.format((objCupom2.getPreco() * (descontoperc) / 100) - diferenca)));
                    objCupom2.setTotaldesconto(objCupom2.getDescontounitario() * objCupom2.getQuantidade());
                    objCupom2.setTotalprodutocdesc(objCupom2.getTotalproduto() - objCupom2.getTotaldesconto() + objCupom2.getTotalacrescimo());
                    m.listadeItens.set(lastIndex - 1, objCupom2);
                    m.atualizarlista();
                }

            }
        }}
    private View.OnClickListener selectDescPerc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TecladoQuantInteiro.instance==null) {
                m.controleteclado = 9;
            Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);

            startActivityForResult(intent, 1);
        }}

    };

    private View.OnClickListener selectDescVal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Tecladonumerico.instance==null) {
                m.controleteclado = 20;
                Intent intent = new Intent(getApplication(), Tecladonumerico.class);

                startActivityForResult(intent, 2);
            }
        }

    };


    public void selecConfirmar(View view) {
        String str ="7";
        Intent intent = new Intent();
        intent.putExtra("KEY", str);
        this.setResult(RESULT_OK, intent);
        m.valorTotalOrc.setText( "R$ " + converte.format(m.objdocumento.getTotaldocumentocdesc()));
        m.valorTotalVendas.setText( "R$ " + converte.format(m.objdocumento.getTotaldocumentocdesc()));
        String tamanho= String.valueOf( m.listadeItens.size());


        Toast.makeText(getApplicationContext(), tamanho, Toast.LENGTH_LONG).show();
        finish();


    }



    public void voltar(View v){
        this.finish();
    }
}


