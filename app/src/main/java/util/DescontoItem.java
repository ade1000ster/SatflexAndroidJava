package util;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.R;
import java.text.DecimalFormat;
import model.modelDocumentoProduto;

public class DescontoItem extends AppCompatActivity {
    MainActivity m = new MainActivity();
    final DecimalFormat converte = new DecimalFormat("0.00");
    public static Object instance;
    private TextView tvdescricao;
    private TextView tvquantidade;
    private TextView tvprecounitario;
    private TextView tvvaloritem;
    private TextView tvdescontounitario;
    private TextView tvdescontopercentual;
    private TextView tvtotaldesconto;
    private TextView tvacresunitario;
    private TextView tvacrescpercent;
    private TextView tvtotalacrescimo;
    private TextView tvtotalfinal;
    private Button btnconfirmar;
    private Button voltar;
    private  modelDocumentoProduto objCupom2;
    private double quantidade =0;
    private  double precoUnitario = 0;
    private double totalProdcDesc = 0;
    private double totalproduto = 0;
    private double descontounitario = 0;
    private double descontopercentual = 0;
    private double totaldesconto = 0;
    private  double acrescimounitario=0;
    private double acrescimopercentual = 0;
    private double totalacrescimo = 0;
    private AlertDialog alerta;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.descontoitem);
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout((int) (width * .41), (int) (height * .67));
        objCupom2 = new modelDocumentoProduto();
        tvdescricao = (TextView) findViewById(R.id.tvdescricao);
        tvquantidade = (TextView) findViewById(R.id.tvquantidade);
        tvprecounitario = (TextView) findViewById(R.id.tvprecounitario);
        tvvaloritem = (TextView) findViewById(R.id.tvvaloritem);
        tvdescontounitario = (TextView) findViewById(R.id.tvdescontounitario);
        tvdescontopercentual = (TextView) findViewById(R.id.tvdescontopercentual);
        tvtotaldesconto = (TextView) findViewById(R.id.tvtotaldesconto);
        tvacresunitario = (TextView) findViewById(R.id.tvacresunitario);
        tvacrescpercent = (TextView) findViewById(R.id.tvacrescpercent);
        tvtotalacrescimo = (TextView) findViewById(R.id.tvtotalacrescimo);
        tvtotalfinal = (TextView) findViewById(R.id.tvtotalfinal);
        btnconfirmar = (Button) findViewById(R.id.btnconfirmar);
        objCupom2 = m.listadeItens.get(m.descontouniario);
        quantidade = objCupom2.getQuantidade();
        precoUnitario = objCupom2.getPreco();
        totalProdcDesc = objCupom2.getTotalprodutocdesc();
        descontounitario = objCupom2.getDescontounitario();
        descontopercentual = objCupom2.getDescontopercentual();
        totaldesconto = objCupom2.getTotaldesconto();
        acrescimounitario = objCupom2.getAcrescimounitario();
        acrescimopercentual = objCupom2.getAcrescimopercentual();
        totalacrescimo = objCupom2.getTotalacrescimo();
        totalproduto = objCupom2.getTotalproduto();
        tvdescricao.setText(objCupom2.getDescricao());
        tvquantidade.setText(String.valueOf(quantidade));
        tvprecounitario.setText(String.valueOf(converte.format(objCupom2.getPreco())));
        tvvaloritem.setText(String.valueOf(converte.format(objCupom2.getTotalproduto())));
        tvdescontounitario.setText(String.valueOf(converte.format(objCupom2.getDescontounitario())));
        tvdescontopercentual.setText(String.valueOf(converte.format(objCupom2.getDescontopercentual())));
        tvtotaldesconto.setText(String.valueOf(converte.format(objCupom2.getTotaldesconto())));
        tvacresunitario.setText(String.valueOf(converte.format(objCupom2.getAcrescimounitario())));
        tvacrescpercent.setText(String.valueOf(converte.format(objCupom2.getAcrescimopercentual())));
        tvtotalacrescimo.setText(String.valueOf(converte.format(objCupom2.getTotalacrescimo())));
        totaldesconto = (objCupom2.getTotaldesconto());
        totalacrescimo = (objCupom2.getTotalacrescimo());
        totalProdcDesc = (objCupom2.getTotalprodutocdesc());
        tvtotalfinal.setText(String.valueOf(converte.format(totalProdcDesc)));
        tvquantidade.setOnClickListener(selecttvquantidade);
        tvprecounitario.setOnClickListener(selecttvprecounitario);
        tvdescontounitario.setOnClickListener(selecttvdescunitario);
        tvdescontopercentual.setOnClickListener(selecttvdescpercentual);
        tvtotaldesconto.setOnClickListener(selecttvtotaldesc);
        tvacresunitario.setOnClickListener(selecttvacresunitario);
        tvacrescpercent.setOnClickListener(selecttvtvacrescpercent);
        tvtotalacrescimo.setOnClickListener(selecttvtotalaacres);
        instance=this;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            String retornoTeclado = data.getStringExtra("KEY");
            quantidade = Integer.parseInt(retornoTeclado);
            if (quantidade >0) {
                totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
                totalproduto = precoUnitario * quantidade;
                totaldesconto = descontounitario * quantidade;
                totalacrescimo = acrescimounitario * quantidade;
                totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
                setcampos();
            }
            else
            {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Produto sem preço");
                    builder.setMessage("A quantidade do produto precisa ser maior que zero.");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.cancel();
                        }
                    });
                    alerta = builder.create();
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
        if (resultCode == RESULT_OK && requestCode == 2) {
            String retornoTeclado = data.getStringExtra("KEY");
            precoUnitario = Double.parseDouble(retornoTeclado);
            totalproduto = precoUnitario * quantidade;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();
        }
        if (resultCode == RESULT_OK && requestCode == 3) {
            String retornoTeclado = data.getStringExtra("KEY");
            descontounitario = Double.parseDouble(retornoTeclado);
            descontopercentual = (descontounitario / precoUnitario) * 100;
            totaldesconto = descontounitario * quantidade;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();

        }
        if (resultCode == RESULT_OK && requestCode == 4) {
            String retornoTeclado = data.getStringExtra("KEY");
            descontopercentual = Double.parseDouble(retornoTeclado);
            descontounitario = (descontopercentual *(precoUnitario)/100 );
            totaldesconto = descontounitario * quantidade;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();
        }
        if (resultCode == RESULT_OK && requestCode == 5) {
            String retornoTeclado = data.getStringExtra("KEY");
            totaldesconto = Double.parseDouble(retornoTeclado);
            descontounitario = (totaldesconto / quantidade  );
            descontopercentual = (descontounitario / precoUnitario) * 100;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();
        }
        if (resultCode == RESULT_OK && requestCode == 6) {
            String retornoTeclado = data.getStringExtra("KEY");
            acrescimounitario = Double.parseDouble(retornoTeclado);
            acrescimopercentual = (acrescimounitario / precoUnitario) * 100;
            totalacrescimo = acrescimounitario * quantidade;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();
        }

        if (resultCode == RESULT_OK && requestCode == 7) {
            String retornoTeclado = data.getStringExtra("KEY");
            acrescimopercentual = Double.parseDouble(retornoTeclado);
            acrescimounitario = (acrescimopercentual *(precoUnitario)/100 );
            totalacrescimo = acrescimounitario * quantidade;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();
        }
        if (resultCode == RESULT_OK && requestCode == 8 ) {
            String retornoTeclado = data.getStringExtra("KEY");
            totalacrescimo = Double.parseDouble(retornoTeclado);
            acrescimounitario = (totalacrescimo / quantidade  );
            acrescimopercentual = (acrescimounitario / precoUnitario) * 100;
            totalProdcDesc = (totalproduto - totaldesconto + totalacrescimo);
            setcampos();
        }


    }


    private View.OnClickListener selecttvquantidade = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if (TecladoQuantInteiro.instance==null) {
            m.controleteclado = 8;
            if (objCupom2.getIdunidade()==1) {
                Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);
                startActivityForResult(intent, 1);
            }
            else{

            }

        }}

    };

    private View.OnClickListener selecttvprecounitario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if (Tecladonumerico.instance==null) {
            m.controleteclado = 9;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 2);
        }}

    };

    private View.OnClickListener selecttvdescunitario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Tecladonumerico.instance==null){
            m.controleteclado = 10;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 3);
        }}

    };
    private View.OnClickListener selecttvdescpercentual = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            if (TecladoQuantInteiro.instance==null) {
            m.controleteclado = 11;
            Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);

            startActivityForResult(intent, 4);
        }}

    };
    private View.OnClickListener selecttvtotaldesc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (Tecladonumerico.instance==null) {
            m.controleteclado = 12;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 5);
        }}

    };
    private View.OnClickListener selecttvacresunitario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (Tecladonumerico.instance==null) {
            m.controleteclado = 13;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 6);
        }}

    };
    private View.OnClickListener selecttvtvacrescpercent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TecladoQuantInteiro.instance==null){
            m.controleteclado = 14;
            Intent intent = new Intent(getApplication(), TecladoQuantInteiro.class);

            startActivityForResult(intent, 7);
        }}

    };
    private View.OnClickListener selecttvtotalaacres = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (Tecladonumerico.instance==null) {
            m.controleteclado = 15;
            Intent intent = new Intent(getApplication(), Tecladonumerico.class);

            startActivityForResult(intent, 8);
        }}

    };


    public void setcampos(){
        tvquantidade.setText(String.valueOf(quantidade));
        tvprecounitario.setText(String.valueOf(converte.format(precoUnitario)));
        tvvaloritem.setText(String.valueOf(converte.format(totalproduto)));
        tvdescontounitario.setText(String.valueOf(converte.format(descontounitario)));
        tvdescontopercentual.setText(String.valueOf((int)descontopercentual)+"%");
        tvtotaldesconto.setText(String.valueOf(converte.format(totaldesconto)));
        tvacresunitario.setText(String.valueOf(converte.format(acrescimounitario)));
        tvacrescpercent.setText(String.valueOf((int)acrescimopercentual+"%"));
        tvtotalacrescimo.setText(String.valueOf(converte.format(totalacrescimo)));
        tvtotalfinal.setText(String.valueOf(converte.format(totalProdcDesc)));
    }

    public void confirmar(View v){
        m.objdocumento.setTotalquantidade(m.objdocumento.getTotalquantidade() - objCupom2.getQuantidade());
        m.objdocumento.setTotaldocumentocdesc(m.objdocumento.getTotaldocumentocdesc() - objCupom2.getTotalprodutocdesc());
        m.objdocumento.setTotaldocumento(m.objdocumento.getTotaldocumento() - objCupom2.getTotalproduto());
        m.objdocumento.setTotaldesconto(m.objdocumento.getTotaldesconto() - objCupom2.getTotaldesconto());
        objCupom2.setQuantidade(quantidade);
        objCupom2.setPreco(precoUnitario);
        objCupom2.setTotalproduto(totalproduto);
        objCupom2.setTotalprodutocdesc(totalProdcDesc);
        objCupom2.setDescontounitario(descontounitario);
        objCupom2.setDescontopercentual(descontopercentual);
        objCupom2.setTotaldesconto(totaldesconto);
        objCupom2.setAcrescimounitario(acrescimounitario);
        objCupom2.setAcrescimopercentual(acrescimopercentual);
        objCupom2.setTotalacrescimo(totalacrescimo);
        m.listadeItens.set(m.descontouniario,objCupom2);
        m.objdocumento.setTotalquantidade(m.objdocumento.getTotalquantidade() + objCupom2.getQuantidade());
        m.objdocumento.setTotaldocumentocdesc(m.objdocumento.getTotaldocumentocdesc() + objCupom2.getTotalprodutocdesc());
        m.objdocumento.setTotaldocumento(m.objdocumento.getTotaldocumento() + objCupom2.getTotalproduto());
        m.objdocumento.setTotaldesconto(m.objdocumento.getTotaldesconto() +  totaldesconto);
        m.objdocumento.setTotalacrescimo(m.objdocumento.getTotalacrescimo() + totalacrescimo );
        m.atualizarlista();
        instance=null;

        this.finish();
    }


    public void voltar(View v){
        instance=null;
        this.finish();
    }
}