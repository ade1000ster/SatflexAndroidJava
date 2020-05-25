package com.example.ademirestudo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ademirestudo.database.DadosOpenHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sweda2.si300dll.Java_SI300;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import br.com.controlid.printid.PrintID;
import br.com.controlid.printid.communication.IDDeviceListeners;
import br.com.controlid.printid.enums.EnumPosicaoCaracteresBarras;
import br.com.controlid.printid.enums.EnumQRCorrecaoErro;
import br.com.controlid.printid.enums.EnumQRModelo;
import br.com.controlid.printid.enums.EnumStatus;
import br.com.controlid.printid.enums.EnumTipoCodigoBarras;
import br.com.controlid.printid.enums.EnumTipoCorte;
import br.com.controlid.satid.SATiD;
import br.com.controlid.satid.communication.ISATiDListeners;
import br.com.controlid.satid.enums.EnumSatStatus;
import model.modelCategorias;
import model.modelCupomFiscal;
import model.modelDocumento;
import model.modelDocumentoProduto;
import model.modelParametros;
import model.modelProdutos;
import model.modelRelatorioOrc;
import util.IncertNcm;
import util.TecladoContribSocial;
import util.TecladoQuantInteiro;
import util.Tecladocpfcnpj;
import util.Tecladonumerico;

import static android.widget.Button.INVISIBLE;
import static android.widget.Button.VISIBLE;
import static com.example.ademirestudo.R.color.corparametroselect;
import static com.example.ademirestudo.R.color.corparametrosemfoco;
import static com.example.ademirestudo.R.color.finalizarorc;
import static com.example.ademirestudo.R.color.selecionado;
import static com.example.ademirestudo.R.color.semfoco;
import static com.example.ademirestudo.R.id;
import static com.example.ademirestudo.R.layout;
import static com.example.ademirestudo.R.menu;
public class MainActivity extends AppCompatActivity implements OnClickListener, TextToSpeech.OnInitListener, AdapterView.OnItemSelectedListener {
    private StringBuilder statusSat;
    private AlertDialog alertDialog;
    StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference();
    private static DatabaseReference firebaseDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference statusFirebaseDatabase;
    private static DatabaseReference alertaFirebaseDatabase;
    private static DatabaseReference alertaMsgFirebaseDatabase;
    public static  int controleteclado = 0;
    public static ConstraintLayout layoutVendas;
    private ConstraintLayout layoutSuporte;
    public static ConstraintLayout layoutCupom;
    private ConstraintLayout layoutContador;
    public static ConstraintLayout layoutRelatorio;
    private ConstraintLayout layoutConfigura;
    private ConstraintLayout layoutConfiguraparam;
    private ConstraintLayout layoutStatusSistema;
    private ConstraintLayout layoutCadastrarprod;
    private ConstraintLayout layoutCadastrarCat;
    public static ConstraintLayout layoutOrcamento;
    private static DadosOpenHelper dadosOpenHelper;
    public static Button VENDER;
    private Button CADASTRO;
    public static Button RELATORIO;
    private Button CONTADOR;
    private Button CONFIGURACAO;
    private String senhaConfiguracao = "";
    private Button SUPORTE;
    private Button SAIR;
    private Button bcateg;
    private Button bprod;
    private Button btnAlertaUsb;
    public static Button binformarCPF;
    public static Button bFinalizaVenda;
    public static Button bFinalizaOrc;
    public static int auxtipoRelatorio; //0 relatorio orcamento e 1,2,3,4 para relatorio fechamento
    private Spinner sptipofechamento;
    private TextView tvfechamentoPor;
    private Button fechamento;
    private AnalogClock relogioRelatorio;
    private Button estatisticas;
    private Button orcamento;
    private Button imprimeorcamento;
    private Button geraorcamento;
    private TextView textRelatorio;
    private ListView listorcamento;

    public static ListView cupomOrcamento;
    private EditText dtinicialorcamento;
    private EditText dtfinalormaneto;
    private EditText dtinicialcontador;
    private EditText dtfinalcontador;
    private TextView RelatorioOrcamento;
    private TextView textdtnInicial;
    private TextView textdtnFinal;
    public static int numOrcamento = 0;
    private EditText barraproduto = null;
    private EditText barracategoria = null;
    private static EditText buscarProd = null;
    public static modelProdutos itemposicaovariavel = new modelProdutos();
    public static int descontouniario = 0;

    public static TextView valorTotalVendas;
    public static TextView valorTotalOrc;
    public static TextView qtditensVenda;
    public static TextView qtditensOrc;
    public static TextView valorTotSemDescV;
    public static TextView valorTotSemDescO;
    public static TextView totDescontoV;
    public static TextView totDescontoOcr;
    public static modelParametros objparam = new modelParametros();
    public static modelDocumentoProduto objCupom2;
    public static modelDocumentoProduto objCupom;
    public static modelDocumento objdocumento;
    public static modelCupomFiscal objCupomFiscal = new modelCupomFiscal();
    public static ArrayList<modelDocumentoProduto> listadeItens;
    private ArrayList<modelCategorias> listadecateg;

    public static ArrayList<modelRelatorioOrc> listadeorcamento;

    public static Adapter itensAdapter;
    private static AdapterCateg itenscateg;
    private static AdapterRelatorioOrc itensorcamento;
    public static double quantTotalRelOrc = 0;
    public static double valorTotalRelOrc = 0;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListenerr;
    public static GridView containerGridViewprod;
    public static GridView containerGridViewCateg;
    public static ArrayList<modelProdutos> listadeItensVendaProd;
    private static MainAdapterProd itensVendaProd;
    private EditText tvInfo;
    public static TextView tvInfoDescr;
    public static TextView tvDescricao;
    public static Button btnGravarAlt;
    private Button btncontador;
    private Button btndesenvolvedora;
    private Button btndiversos;
    private Button btnemitente;
    private Button btnimpressora;
    private Button btnrevenda;
    private Button btnsat;
    private Button btnParametros;
    private Button btnStatusSistema;
    View toastLayout;
    public static ArrayList<modelParametros> listadeItensParam = new ArrayList<>();
    public static GridView containerGridViewParam;
    LayoutInflater layoutInflater;
    private int controle = 0;
    public static String dataInicialAux;
    public static String dataFinalAux;
    private static File diretorioBanco;
    private static File diretorioXmlContador;
    private static File diretorioXmlTemp;
    private static File diretorioTemp;
    private static File diretorioAtualiza;
    String myData = "";
    public static String[] retornosat;
    public static String erroconexãosat;
    public static String qrCode = "";
    private static String qrCodeCanc = "";
    private static String chave = "";
    private static int coo = 000000;
    public EnviarEmail enviaemail;
    public IncertNcm incertNcm;
    FinalizarVendas finalizarVendas = null;
    AlteraProd alterarProd = null;
    private static SAT sweda;
    private static Java_SI300 impr ;
   // public PowerManager p;
   private ProgressBar Progressbar;
    private int i = 0;
    public static int statuSat =0; //1 sat conectado 0 não consectado
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    // Hide the nav bar and status bar
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    );

                    //        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });
        setContentView(layout.activity_main);
        conectarBanc();

     //   ProgressBar.setMax(100); // 100 maximum value for the progress barff
        containerGridViewCateg = findViewById(id.GridCategorias);
        containerGridViewprod = findViewById(id.GridProdutos);
        listadeItensVendaProd = new ArrayList<>();
        itensVendaProd = new MainAdapterProd(this, listadeItensVendaProd);
        consultarCateg();
        objdocumento = new modelDocumento();
        //listadeprod = new ArrayList<>();
        listadeItens = new ArrayList<>();
       // adapterProd = new AdapterProd(this, listadeprod);
        itensAdapter = new Adapter(this, listadeItens);
        itenscateg = new AdapterCateg(this, listadecateg);

        buscarProd = findViewById(id.tvPesquisarProd);
        listadeorcamento = new ArrayList<>();
        itensorcamento = new AdapterRelatorioOrc(this, listadeorcamento);

        VENDER = findViewById(id.Bvender);
        CADASTRO = findViewById(id.Bcadastro);
        RELATORIO = findViewById(id.Brelatorios);
        CONTADOR = findViewById(id.Bcontador);
        CONFIGURACAO = findViewById(id.Bconfigurar);
        SUPORTE = findViewById(id.Bsuporte);
        SAIR = findViewById(id.Bsair);

        layoutVendas = findViewById(id.layoutContentMainVendas);
        layoutCadastrarprod = findViewById(id.layoutCadProd);
        layoutRelatorio = findViewById(id.layoutRelatorio);
        layoutContador = findViewById(id.layoutContador);
        layoutConfigura = findViewById(id.layoutConfigura);
        layoutSuporte = findViewById(id.layoutSuporte);
        layoutCupom = findViewById(id.layoutCupom);
        layoutConfiguraparam = findViewById(id.Parametros);
        layoutStatusSistema = findViewById(id.StatusSistema);
        layoutCadastrarCat = findViewById(id.layoutCadastrarCategoria);
        layoutOrcamento = findViewById(id.layoutorcamento);
        btnAlertaUsb = findViewById(id.btnalertausb);
        bcateg = findViewById(id.bCategoria);
        bprod = findViewById(id.bProd);
        binformarCPF = findViewById(id.btninformcpf);
        bFinalizaVenda = findViewById(id.bFinalizar);
        bFinalizaOrc = findViewById(id.bFinalizarOrc);

        orcamento = findViewById(id.Borcamento);
        geraorcamento = findViewById(id.btngerarorcamento);
        imprimeorcamento = findViewById(id.btnimprimirorcamento);
        fechamento = findViewById(id.Bfechamento);
        relogioRelatorio = findViewById(id.AnlClockRelatorio);
        estatisticas = findViewById(id.Bestatisticas);
        tvfechamentoPor = findViewById(id.tvfechamentoPor);
        sptipofechamento = findViewById(id.sptipofechamento);
        textRelatorio = findViewById(id.textRelatorio);
        RelatorioOrcamento = findViewById(id.textRelatorioOrcamento);
        textdtnFinal = findViewById(id.textdtnFinal);
        textdtnInicial = findViewById(id.textdtnInicial);
        listorcamento = findViewById(id.listorcamento);

        Toolbar menuCupomVendas = findViewById(id.toolbar);
        setSupportActionBar(menuCupomVendas);
        menuCupomVendas.inflateMenu(menu.menu_venda);
        Toolbar menuCupomOrc = findViewById(id.toolbarOrc);
        setSupportActionBar(menuCupomOrc);
        menuCupomOrc.inflateMenu(menu.menu_orcamento);
        ListView cupomVendas = findViewById(id.textViewcupom);
        barraproduto = findViewById(id.txtpesquisa);
        barracategoria = findViewById(id.textviewinserirCat);
        dtinicialorcamento = findViewById(id.editDatainicial);
        dtfinalormaneto = findViewById(id.editDatafinal);

        cupomOrcamento = findViewById(id.tvOrcamento);
        valorTotalVendas = findViewById(id.tvValorTotal);
        valorTotalOrc = findViewById(id.tvValorTotalOrc);
        valorTotSemDescV = findViewById(id.tvTotSemDesc);
        qtditensVenda = findViewById(id.tvQtdItens);
        valorTotSemDescO = findViewById(id.tvTotSemDescOrc);
        qtditensOrc = findViewById(id.tvQtdItensOrc);
        totDescontoV = findViewById(id.tvDesconto);
        totDescontoOcr = findViewById(id.tvDescontoOrc);
        versaoApp();
        cupomVendas.setAdapter(itensAdapter);
        cupomOrcamento.setAdapter(itensAdapter);
        listorcamento.setAdapter(itensorcamento);
        dtinicialcontador = findViewById(id.etdatainicial);
        dtfinalcontador = findViewById(id.etdatafinal);

        //consultarStatusOperacional();


        //layoutparametro
        btnParametros = (Button) findViewById(id.btnparametros);
        btnStatusSistema = (Button) findViewById(id.btnstatusistema);
        btncontador = (Button) findViewById(id.btncontador);
        btndesenvolvedora = (Button) findViewById(id.btndesenvolvedora);
        btndiversos = (Button) findViewById(id.btndiversos);
        btnemitente = (Button) findViewById(id.btnemitente);
        btnimpressora = (Button) findViewById(id.btnimpressora);
        btnrevenda = (Button) findViewById(id.btnrevenda);
        btnsat = (Button) findViewById(id.btnsat);
        layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(layout.layouttoastparametro, (ViewGroup) findViewById(id.layouttoastparametro));
        btnGravarAlt = (Button) findViewById(id.btGravarAlt);
        tvInfo = (EditText) findViewById(id.tvInfo);
        tvInfoDescr = (TextView) findViewById(id.tvInfoDescr);
        tvDescricao = (TextView) findViewById(id.tvDescricao);
        containerGridViewParam = findViewById(id.gridParametros);
        layoutVendas.setVisibility(VISIBLE);
        layoutCupom.setVisibility(VISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        binformarCPF.setOnClickListener(this);
        //CADASTRO.setOnClickListener(this);
        bcateg.setOnClickListener(this);
        bprod.setOnClickListener(this);
        dtinicialorcamento.setOnClickListener(this);
        dtfinalormaneto.setOnClickListener(this);
        dtinicialcontador.setOnClickListener(this);
        dtfinalcontador.setOnClickListener(this);
        btncontador.setOnClickListener(this);
        btndesenvolvedora.setOnClickListener(this);
        btndiversos.setOnClickListener(this);
        btnemitente.setOnClickListener(this);
        btnimpressora.setOnClickListener(this);
        btnrevenda.setOnClickListener(this);
        btnsat.setOnClickListener(this);
        btnParametros.setOnClickListener(this);
        btnStatusSistema.setOnClickListener(this);
        btnGravarAlt.setOnClickListener(this);
        btnAlertaUsb.setOnClickListener(this);
        parametros();
        firebaseStorageRef = FirebaseStorage.getInstance().getReference();
        criarDiretorios();
        // avisar();
      //  bloqueio();
       compactarBanco();
        conectSatControlid();
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        getSAT().identificarEth();
        impSweda();

        downloadAtualizacao();
       // backupBanco();
        //statusSat =new StringBuilder();
        //testeSatSweda();

    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void impSweda() {

//VENDER.setText(String.valueOf(getimp().Java_SI300_eBuscarPortaVelocidade(this)));
        getimp().Java_SI300_Criar_NovoDispositivo();
       //getimp().Java_SI300_setIP_Porta("6" );
       // getimp().Java_SI300_Set_Dispositivo(2);
       // getimp().Java_SI300_SetInterfaceComunicacao(0);

        getimp().Java_SI300_iImprimirTexto("aaadasdaasdaa",1);

     //   ConnectPrinter connectprinter = new ConnectPrinter(this);
       /// connectprinter.openPrinter();
        //getimp().Java_SI300_iImprimirTexto("texto", 3);
    }

    public void conectSatControlid() {

        try {

            SATiD.config(getApplicationContext());
            onNewIntentt(getIntent());
            SATiD.getInstance().startConnection();

        } catch (Exception e) {


        }
        SATiD.getInstance().setOnChangeConnection(new ISATiDListeners.OnChangeConnectionListener() {
            @Override
            public void status(EnumSatStatus enumSatStatus) {

            }
        });
    }

    public void conectImpControlid() {

        try {
            PrintID.config(getApplicationContext());
            onNewIntent(getIntent());

            PrintID.getInstance().startConnection();

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), " Não foi possivel estabelecer conexão com a impressora ", Toast.LENGTH_LONG);
        }
        PrintID.getInstance().setOnChangeConnection(new IDDeviceListeners.OnChangeConnectionListener() {
            @Override
            public void status(EnumStatus enumStatus) {
                if (enumStatus.toString().equalsIgnoreCase("CONNECTED")) {
                    btnAlertaUsb.setVisibility(INVISIBLE);

                } else {
                    btnAlertaUsb.setVisibility(VISIBLE);

                }

            }
        });
    }

    protected void onNewIntentt(Intent intentt) {
        super.onNewIntent(intentt);
        intentt.setAction(UsbManager.EXTRA_PERMISSION_GRANTED);
        if (intentt.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED))
            SATiD.getInstance().startConnection();
        //  PrintID.getInstance().startConnection();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED))
            PrintID.getInstance().startConnection();
    }
    public  void statusSat(){
    if(objparam.getSatModelo().equalsIgnoreCase("sweda")) {
        sweda = new SAT(this);
        sweda.execute();


    }
    if(objparam.getSatModelo().equalsIgnoreCase("controlid")) {
        if (SATiD.getInstance().isConnected()){
            statuSat =1;
        }
        else{
            statuSat =0;
        }


    }
}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean venderSatControlId() {
       //pausa();
       //statusSat();
       pausa();
        if (statuSat == 1) {
            Random random = new Random();
            int number = 0;
            String numeroSessao = "123456";
            try {
                number = random.nextInt(999999);
                numeroSessao = (String.format("%06d", number));
                //   String consultarstatusat = SATiD.getInstance().ConsultarStatusOperacional(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao());
                DadosVenda dadosVenda = new DadosVenda();
                number = random.nextInt(999999);
                numeroSessao = (String.format("%06d", number));
                String retornosatt = " ";
                if (objparam.getSatModelo().equalsIgnoreCase("sweda")) {

                    try {
                      //  pausa();
                        retornosatt = getSAT().enviarDadosVenda(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao(), dadosVenda.criarDadosVenda().toString());
                    } catch (Exception E) {


                        {
                        }
                    }
                }
                if (objparam.getSatModelo().equalsIgnoreCase("controlid") && SATiD.getInstance().isConnected()) {
                    retornosatt = SATiD.getInstance().EnviarDadosVenda(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao(), dadosVenda.criarDadosVenda().toString());
                }
                String dadosvenda = dadosVenda.criarDadosVenda().toString();
                FileOutputStream savedadosVenda = new FileOutputStream(diretorioTemp + "/" + "dadosVenda" + ".xml");
                savedadosVenda.write(dadosvenda.getBytes());
                savedadosVenda.close();
                retornosat = retornosatt.split("\\|");

                if (retornosat[1].equalsIgnoreCase("06000")) {

                    chave = retornosat[8];
                    String vendas = converteBase64.base64(retornosat[6]);
                    String[] hora = vendas.split("hEmi>");
                    String[] data = vendas.split("dEmi>");
                    qrCode = retornosat[11];
                    coo = Integer.parseInt(retornosat[8].substring(34, 40));
                    objCupomFiscal.setCoo(coo);
                    objdocumento.setChaveCfe(chave.substring(3));
                    objCupomFiscal.setChaveCfe(objdocumento.getChaveCfe());
                    objdocumento.setXml(vendas);
                    objdocumento.setNumero(coo);
                    objdocumento.setDthrcriacao(data[1].substring(0, 4) + "-" + data[1].substring(4, 6) + "-" + data[1].substring(6, 8) + " " + hora[1].substring(0, 2) + ":" + hora[1].substring(2, 4) + ":" + hora[1].substring(4, 6));
                    objCupomFiscal.setData(data[1].substring(6, 8) + "/" + data[1].substring(4, 6) + "/" + data[1].substring(0, 4));
                    objCupomFiscal.setHora(hora[1].substring(0, 2) + ":" + hora[1].substring(2, 4) + ":" + hora[1].substring(4, 6));
                    dadosOpenHelper.adddocumento(objdocumento);

                    if (objparam.getImpressoraConfirm().equalsIgnoreCase("N")) {
                        impCupomFiscal();
                    } else {


                    }


                    return true;
                } else {

                    erroconexãosat = retornosat[3];
                    sweda = new SAT(this);
                    sweda.desativarEthSat();
                    return false;
                }


            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
else{
    erroconexãosat = "sem conexão Sat";
            sweda = new SAT(this);
            sweda.desativarEthSat();
    return false;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public SAT getSAT() {
        if (sweda == null) {
            sweda = new SAT(getApplicationContext());
        }

        return sweda;
    }
    public Java_SI300 getimp() {
        if (impr == null) {
            impr = new Java_SI300();
        }

        return impr;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cancelarVendaSatControlId(Cursor cursor) {
        String consultarsttusat = "";
        String retornosatCanc ="";
        pausa();
        if (statuSat == 1) {

            try {
                cursor.moveToLast();
                String dia = cursor.getString(1).substring(8, 10);
                String mes = cursor.getString(1).substring(5, 7);
                String ano = cursor.getString(1).substring(0, 4);
                String hora = cursor.getString(1).substring(11, 13);
                String min = cursor.getString(1).substring(13, 15);
                String seg = cursor.getString(1).substring(15, 19);
                String data = dia + "/" + mes + "/" + ano;
                String horario = hora + min + seg;
                final String iddocumento = cursor.getString(0);
                final String cfeCanc = "CFe" + cursor.getString(6);
                if (objparam.getSatModelo().equalsIgnoreCase("sweda")) {

                    try {
                        //  pausa();
                        consultarsttusat = getSAT().consultarStatusOperacional(3, objparam.getSatCodAtivacao());
                    } catch (Exception E) {


                        {
                        }
                    }
                }
                if (objparam.getSatModelo().equalsIgnoreCase("controlid") && SATiD.getInstance().isConnected()) {
                     consultarsttusat = SATiD.getInstance().ConsultarStatusOperacional(3, objparam.getSatCodAtivacao());
                }

                String[] separa = consultarsttusat.split("\\|");
                objCupomFiscal.setData(data);
                objCupomFiscal.setHora(horario);
                objCupomFiscal.setSatSerie(separa[5]);
                objdocumento.setTotaldocumentocdesc(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29)));
                String xmlvenda = cursor.getString(18);
                String[] qrcode = xmlvenda.split("assinaturaQRCODE>");
                qrCode = qrcode[1].toString();
                DadosVenda d = new DadosVenda();
                if (objparam.getSatModelo().equalsIgnoreCase("sweda")) {

                    try {
                        //  pausa();
                        retornosatCanc = getSAT().cancelarUltimaVenda(111111, objparam.getSatCodAtivacao(), cfeCanc, d.criarDadosVendaCanc(cfeCanc).toString());
                    } catch (Exception E) {


                        {
                        }
                    }
                }
                if (objparam.getSatModelo().equalsIgnoreCase("controlid") && SATiD.getInstance().isConnected()) {
                    retornosatCanc = SATiD.getInstance().CancelarUltimaVenda(111111, objparam.getSatCodAtivacao(), cfeCanc, d.criarDadosVendaCanc(cfeCanc).toString());
                }
                String[] retornosatCan = retornosatCanc.split("\\|");
                if (retornosatCan[1].equalsIgnoreCase("07000")) {
                    String[] alterarDocCanc = new String[7];
                    alterarDocCanc[0] = iddocumento;
                    alterarDocCanc[1] = retornosatCan[8].substring(3);
                    alterarDocCanc[2] = converteBase64.base64(retornosatCan[6]);

                    String[] dataCanc = alterarDocCanc[2].split("nCFe><dEmi>");
                    String[] horaCanc = dataCanc[1].split("hEmi>");
                    alterarDocCanc[3] = dataCanc[1].substring(0, 4) + "-" + dataCanc[1].substring(4, 6) + "-" + dataCanc[1].substring(6, 8) + " " + horaCanc[1].substring(0, 2) + ":" + horaCanc[1].substring(2, 4) + ":" + horaCanc[1].substring(4, 6);
                    objCupomFiscal.setDataCanc(dataCanc[1].substring(6, 8) + "/" + dataCanc[1].substring(4, 6) + "/" + dataCanc[1].substring(0, 4));
                    objCupomFiscal.setHoraCanc(horaCanc[1].substring(0, 2) + ":" + horaCanc[1].substring(2, 4) + ":" + horaCanc[1].substring(4, 6));
                    objCupomFiscal.setChaveCfeCanc(retornosatCan[8].substring(3));
                    qrCodeCanc = retornosatCan[11];
                    dadosOpenHelper.alterdocCancela(alterarDocCanc);
                    impCupomFiscalCanc();
                    if(objparam.getSatModelo().equalsIgnoreCase("sweda")){
                        sweda = new SAT(this);
                        sweda.desativarEthSat();
                    }
                } else {
                    String[] alterarDocCanc = new String[3];
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Houve uma falha");
                    if(objparam.getSatModelo().equalsIgnoreCase("sweda")){
                        sweda = new SAT(this);
                        sweda.desativarEthSat();
                    }
                    builder.setMessage(retornosatCan[3]);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.cancel();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setBackgroundColor(Color.BLUE);
                    nbutton.setTextSize(20);
                    nbutton.setScaleY(1);
                    nbutton.setScaleX(1);
                    nbutton.setX(-60);
                    nbutton.setTextColor(Color.WHITE);

                }
                // FileOutputStream fos = new FileOutputStream(diretorioTemp+ "/" + "dadosvenda" +".xml" );
                // fos.write(dadosvenda.getBytes());
                //  fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(objparam.getSatModelo().equalsIgnoreCase("sweda")){
                sweda = new SAT(this);
                sweda.desativarEthSat();
            }
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Houve uma falha");
                builder.setMessage("Verifique a conexão do sat");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.BLUE);
                nbutton.setTextSize(20);
                nbutton.setScaleY(1);
                nbutton.setScaleX(1);
                nbutton.setX(-60);
                nbutton.setTextColor(Color.WHITE);
            }
        }


    public static void imprimirCupomOrc(StringBuilder cumpomorc) {
        if (PrintID.getInstance().isConnected()) {
            PrintID.getInstance().ImprimirFormatado(cumpomorc.toString(), false, false, false, false);

           PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
            // Toast.makeText(getApplicationContext(), SATiD.getInstance().ConsultarSAT(3), Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(getApplicationContext(), "sat_id desconectado", Toast.LENGTH_LONG).show();
        }
    }

    public static void reimprimirCupom(StringBuilder cumpomreimprimir) {
        if (PrintID.getInstance().isConnected()) {
            //CupomFiscal.montarCupom();
            PrintID.getInstance().ImprimirFormatado(cumpomreimprimir.toString(), false, false, false, false);
            PrintID.getInstance().ConfigurarCodigoDeBarras(60, 2, EnumPosicaoCaracteresBarras.SEM_CARACTERES);
            String completachave1 = "  " + objCupomFiscal.getChaveCfe().substring(0, 22);
            String completachave2 = "  " + objCupomFiscal.getChaveCfe().substring(22, 44);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachave1, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachave2, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoQR(qrCode.substring(0, 344), 4, EnumQRCorrecaoErro.MEDIO_BAIXO, EnumQRModelo.MODELO_2);
            PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
            // Toast.makeText(getApplicationContext(), SATiD.getInstance().ConsultarSAT(3), Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(getApplicationContext(), "sat_id desconectado", Toast.LENGTH_LONG).show();
        }
    }

    public void impCupomFiscal() {

        CupomFiscalSat CupomFiscal = new CupomFiscalSat();

        if (PrintID.getInstance().isConnected()) {
            CupomFiscal.montarCupom();
            DadosVenda d = new DadosVenda();
            //PrintID.getInstance().ImprimirFormatado(d.criarDadosVendaCanc("a").toString(),false,false,false,true);
            PrintID.getInstance().ImprimirFormatado(CupomFiscal.cupomFiscalSat.toString(), false, false, false, true);

            PrintID.getInstance().ConfigurarCodigoDeBarras(60, 2, EnumPosicaoCaracteresBarras.SEM_CARACTERES);
            String completachave1 = "  " + objCupomFiscal.getChaveCfe().substring(0, 22);
            String completachave2 = "  " + objCupomFiscal.getChaveCfe().substring(22, 44);

            //   PrintID.getInstance().ImprimirCodigoDeBarras(completachave1, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachave1, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachave2, EnumTipoCodigoBarras.CODE128);

            PrintID.getInstance().ImprimirCodigoQR(qrCode, 4, EnumQRCorrecaoErro.MEDIO_BAIXO, EnumQRModelo.MODELO_2);

            PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
            // Toast.makeText(getApplicationContext(), SATiD.getInstance().ConsultarSAT(3), Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(getApplicationContext(), "sat_id desconectado", Toast.LENGTH_LONG).show();
        }
    }

    public void impCupomFiscalCanc() {

        CupomFiscalSat CupomFiscal = new CupomFiscalSat();
        if (PrintID.getInstance().isConnected()) {
            CupomFiscal.montarCupomCanc();
            //PrintID.getInstance().ImprimirFormatado(String.valueOf( qrCode.substring(0,344)),false,false,false,true);
            PrintID.getInstance().ImprimirFormatado(CupomFiscal.cupomFiscalCancelado.toString(), false, false, false, true);
            PrintID.getInstance().ConfigurarCodigoDeBarras(60, 2, EnumPosicaoCaracteresBarras.SEM_CARACTERES);
            String completachave1 = "  " + objCupomFiscal.getChaveCfe().substring(0, 22);
            String completachave2 = "  " + objCupomFiscal.getChaveCfe().substring(22, 44);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachave1, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachave2, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoQR(qrCode.substring(0, 344), 4, EnumQRCorrecaoErro.MEDIO_BAIXO, EnumQRModelo.MODELO_2);
            PrintID.getInstance().ImprimirFormatado(CupomFiscal.cupomFiscalCancelamento.toString(), false, false, false, true);
            PrintID.getInstance().ConfigurarCodigoDeBarras(60, 2, EnumPosicaoCaracteresBarras.SEM_CARACTERES);
            String completachaveCanc1 = "  " + objCupomFiscal.getChaveCfeCanc().substring(0, 22);
            String completachaveCanc2 = "  " + objCupomFiscal.getChaveCfeCanc().substring(22, 44);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachaveCanc1, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoDeBarras(completachaveCanc2, EnumTipoCodigoBarras.CODE128);
            PrintID.getInstance().ImprimirCodigoQR(qrCodeCanc, 4, EnumQRCorrecaoErro.MEDIO_BAIXO, EnumQRModelo.MODELO_2);
           PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
            inicianovodocumento();
            // Toast.makeText(getApplicationContext(), SATiD.getInstance().ConsultarSAT(3), Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(getApplicationContext(), "sat_id desconectado", Toast.LENGTH_LONG).show();
        }
    }

    public void backupBanco() {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        String nomecliente = objparam.getEmitenteRazaoSocial();
        // Uri file = Uri.parse("file:///data/data/com.example.ademirestudo/databases/satflex");
        Uri file = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + "/satflex/banco/satflex.zip");
        StorageReference bkpBanco = firebaseStorageRef.child(nomecliente + "/Bkbanco" + "/Bkp" + "_" + objparam.getEmitenteCNPJ() + " Dt-" + dataFormatada);

        bkpBanco.putFile(file);
    }
    public void versaoApp(){
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;


        int verCode = pInfo.versionCode;
        SAIR.setText(String.valueOf( verCode));
    }
    public void downloadAtualizacao() {

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        String nomecliente = objparam.getEmitenteRazaoSocial();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
       // storageRef.getBucket();
        StorageReference  islandRef = storageRef.child("atualizacao" + "/flex-remarca.apk");


        final File localFile = new File(diretorioAtualiza,islandRef.getName());

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Toast toast = Toast.makeText(getApplicationContext(), " download ok ", Toast.LENGTH_LONG);toast.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast toast = Toast.makeText(getApplicationContext(), " download erro ", Toast.LENGTH_LONG);toast.show();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void criarDiretorios() {
        diretorioAtualiza = new File(Environment.getExternalStorageDirectory() + "/satflex/atualizacao");
        diretorioBanco = new File(Environment.getExternalStorageDirectory() + "/satflex/banco");
        diretorioXmlContador = new File(Environment.getExternalStorageDirectory() + "/satflex/xmlcontador");
        diretorioTemp = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/");
       diretorioXmlTemp = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/xmltempcontador");
        if (!diretorioAtualiza.exists()) {
            diretorioAtualiza.mkdirs();
        }
        if (!diretorioBanco.exists()) {
            diretorioBanco.mkdirs();
        }
        if (!diretorioXmlContador.exists()) {
            diretorioXmlContador.mkdirs();
        }
        if (!diretorioTemp.exists()) {
            diretorioTemp.mkdirs();
        }
        if (!diretorioXmlTemp.exists()) {
            diretorioXmlTemp.mkdirs();
        }
    }

    public void compactarBanco() {
        try {
            compactararq compactarArq = new compactararq();
            compactarArq.compactarParaZip(Environment.getExternalStorageDirectory() + "/satflex/banco/satflex.zip", "/data/data/com.example.ademirestudo/databases/satflex");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void compactarDiretorios() {
        try {
            compactararq compactarArq = new compactararq();
            //   compactarArq.zipDirectory(new File("/data/data/com.example.ademirestudo/databases"),Environment.getExternalStorageDirectory() + "/banco/satflexx.zip");
            compactarArq.zipDirectory(new File(String.valueOf(diretorioBanco)), Environment.getExternalStorageDirectory() + "/" + "09122019/xml.zip");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void avisar() {
        // super.onStart();
        alertaFirebaseDatabase = firebaseDatabaseRef.child(objparam.getEmitenteCNPJ()).child("alerta");
        alertaMsgFirebaseDatabase = firebaseDatabaseRef.child(objparam.getEmitenteCNPJ()).child("alertamsg");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] msgmAlerta = {""};
        alertaMsgFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                msgmAlerta[0] = value;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        alertaFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                if (value.equalsIgnoreCase("1")) {
                    builder.setCancelable(false);
                    builder.setTitle("              Aviso!");

                    builder.setMessage(msgmAlerta[0] + "\n");
                    //  formPag.setTotalpagamento(valorrec);
                    //define um botão como positivo

                    final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            //finish();

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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void bloqueio() {
        // super.onStart();
        statusFirebaseDatabase = firebaseDatabaseRef.child(objparam.getEmitenteCNPJ()).child("status");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        statusFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                if (value.equalsIgnoreCase("bloqueado")) {
                    builder.setCancelable(false);
                    builder.setTitle("              TERMINAL BLOQUEADO!");

                    builder.setMessage("           Entre em contato com o suporte\n" + "pelo número " + objparam.getRevendaTelefone() + "\n");
                    //  formPag.setTotalpagamento(valorrec);
                    //define um botão como positivo

                    final AlertDialog.Builder sim = builder.setPositiveButton("Sair do SATFLEX", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();

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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            ValidarCPF_CNPJ valida = new ValidarCPF_CNPJ();
            String retornoteclado = data.getStringExtra("KEY");
            if (retornoteclado.length() == 13) {
                String parametrovalidador = retornoteclado.substring(0, 3) + retornoteclado.substring(3, 6) + retornoteclado.substring(6, 9) + retornoteclado.substring(11, 13);

                if (valida.validarCPF(parametrovalidador) == true) {
                    this.binformarCPF.setText("CPF:" + retornoteclado);
                    objdocumento.setCpfcnpj(parametrovalidador);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("CPF ou CNPJ inválido");
                    builder.setMessage("O CPF ou CNPJ informado é inválido\n" +
                            " Por favor, digite novamente.");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.cancel();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setBackgroundColor(Color.BLUE);
                    nbutton.setTextSize(20);
                    nbutton.setScaleY(1);
                    nbutton.setScaleX(1);
                    nbutton.setX(-60);
                    nbutton.setTextColor(Color.WHITE);
                }

            } else {
                if (retornoteclado.length() == 17) {
                    String parametrovalidador = retornoteclado.substring(0, 2) + retornoteclado.substring(2, 5) + retornoteclado.substring(5, 8) + retornoteclado.substring(9, 13) + retornoteclado.substring(15, 17);

                    if (valida.validarCNPJ(parametrovalidador) == true) {

                        this.binformarCPF.setText("CNPJ:" + retornoteclado);
                        objdocumento.setCpfcnpj(parametrovalidador);
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("CPF ou CNPJ inválido");
                        builder.setMessage("O CPF ou CNPJ informado é inválido\n" +
                                " Por favor, digite novamente.");
                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.cancel();
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        nbutton.setBackgroundColor(Color.BLUE);
                        nbutton.setTextSize(20);
                        nbutton.setScaleY(1);
                        nbutton.setScaleX(1);
                        nbutton.setX(-60);
                        nbutton.setTextColor(Color.WHITE);
                    }

                }

            }
        }
        if (resultCode == RESULT_OK && requestCode == 2) {
            Double retornoteclado = (Double.parseDouble(data.getStringExtra("KEY")));

            if (retornoteclado == 0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Produto sem preço");
                builder.setMessage("O preço do produto precisa ser maior que zero.");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.BLUE);
                nbutton.setTextSize(20);
                nbutton.setScaleY(1);
                nbutton.setScaleX(1);
                nbutton.setX(-60);
                nbutton.setTextColor(Color.WHITE);
            } else {

                itemposicaovariavel.setPreco(retornoteclado);
                if (itemposicaovariavel.getIdunidade() == 3) {
                    if (Tecladonumerico.instance == null) {
                        controleteclado = 8;
                        Intent intents = new Intent(this, Tecladonumerico.class);
                        ((Activity) this).startActivityForResult(intents, 3);
                    }
                    // addprodvenda(itemposicaovariavel);
                } else {
                    if (itemposicaovariavel.getIdunidade() == 2) {

                        if (TecladoContribSocial.instance == null) {
                            Intent intents = new Intent(this, TecladoContribSocial.class);
                            ((Activity) this).startActivityForResult(intents, 3);
                        }

                    } else {
                        addprodvenda(itemposicaovariavel);
                    }
                }

            }


        }
        if (resultCode == RESULT_OK && requestCode == 3) {
            Double retornoteclado = (Double.parseDouble(data.getStringExtra("KEY")));

            if (retornoteclado == 0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Produto sem preço");
                builder.setMessage("O preço do produto precisa ser maior que zero.");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.BLUE);
                nbutton.setTextSize(20);
                nbutton.setScaleY(1);
                nbutton.setScaleX(1);
                nbutton.setX(-60);
                nbutton.setTextColor(Color.WHITE);
            } else {

                itemposicaovariavel.setQuantidade((retornoteclado));
                addprodvenda(itemposicaovariavel);
            }


        }
        if (resultCode == RESULT_OK && requestCode == 4) {
            Integer retornoteclado = (Integer.parseInt(data.getStringExtra("KEY")));
            DecimalFormat converte = new DecimalFormat("0.00");
            final Cursor cursor = dadosOpenHelper.selecDocCancbyCoo(retornoteclado);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    cursor.moveToLast();
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyOwnDialogTitle));
                    //define o titulo
                    builder.setTitle("Cancelamento de venda");
                    //define a mensagem
                    final String cfeCanc = "CFe" + cursor.getString(6);
                    builder.setMessage("Tem certeza que deseja cancelar a venda?" + "\n" + "\n" + "Cupom: " + String.format("%06d", cursor.getInt(22)) + "\n" + "Total: R$ " + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + "\n" + "CPF: " + cfeCanc + "\n");

                    //define um botão como positivo
                    final AlertDialog.Builder sim = builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.O)
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                objCupomFiscal.setChaveCfe(cursor.getString(6));
                                objCupomFiscal.setCoo(Integer.parseInt(cursor.getString(22)));
                                cancelarVendaSatControlId(cursor);
                            }
                            DadosVenda d = new DadosVenda();

                        }
                    });
                    builder.setNeutralButton("Outro cupom", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            chamarteclado();


                        }
                    }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            SAIR.setBackgroundResource(semfoco);
                            arg0.cancel();
                        }
                    });

                    AlertDialog alerta = builder.create();
                    //Exibe

                    alerta.show();
                    Button neubutton = alerta.getButton(DialogInterface.BUTTON_NEUTRAL);
                    neubutton.setBackgroundResource(corparametroselect);
                    neubutton.setTextSize(18);
                    neubutton.setScaleY(1);
                    neubutton.setScaleX(1);
                    neubutton.setX(50);
                    neubutton.setTextColor(Color.WHITE);

                    Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setBackgroundColor(Color.RED);
                    nbutton.setTextSize(20);
                    nbutton.setScaleY(1);
                    nbutton.setScaleX(1);
                    nbutton.setX(60);
                    nbutton.setTextColor(Color.WHITE);

                    Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
                    pbutton.setBackgroundColor(Color.GREEN);
                    pbutton.setTextSize(20);
                    pbutton.setScaleY(1);
                    pbutton.setScaleX(1);
                    pbutton.setX(-140);
                    // return true;
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Houve uma falha");
                    builder.setMessage("Nenhum documento foi encontrado com o numero " + retornoteclado);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.cancel();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setBackgroundColor(Color.BLUE);
                    nbutton.setTextSize(20);
                    nbutton.setScaleY(1);
                    nbutton.setScaleX(1);
                    nbutton.setX(-60);
                    nbutton.setTextColor(Color.WHITE);

                }
            }

        }
        if (resultCode == RESULT_OK && requestCode == 5) {
            //infoOrcamento = new InfoOrcamento();
            Integer retornoteclado = (Integer.parseInt(data.getStringExtra("KEY")));
            numOrcamento = (retornoteclado);
            efetivarOrcc();
        }
        if (resultCode == RESULT_OK && requestCode == 6) {
            //infoOrcamento = new InfoOrcamento();
            lprod();
        }
        if (resultCode == RESULT_OK && requestCode == 7) {
            //infoOrcamento = new InfoOrcamento();
            lcateg();
        }
    }

    public void efetivarOrcc( ) {
        modelDocumentoProduto objProdOrc;
        VENDER.setBackgroundResource(selecionado);
        layoutCupom.setVisibility(VISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutVendas.setVisibility(VISIBLE);
        RELATORIO.setBackgroundResource(semfoco);
        listadeItens.clear();
        objdocumento = new modelDocumento();
        Cursor tributacao = (dadosOpenHelper.selecItensOrcamento(String.valueOf(numOrcamento)));
        if (tributacao.getCount() > 0) {
            for (int i = 0; i < tributacao.getCount(); i++) {
                tributacao.moveToPosition(i);

                objProdOrc = new modelDocumentoProduto();
                objProdOrc.setIddoproduto(tributacao.getInt(0));
                objProdOrc.setDescricao(String.valueOf(tributacao.getString(3)));
                objProdOrc.setPreco(tributacao.getDouble(2));
                objProdOrc.setQuantidade(tributacao.getInt(1));
                objProdOrc.setTotalproduto(tributacao.getDouble(2) * tributacao.getInt(1));
                objProdOrc.setTotaldesconto(tributacao.getDouble(5));
                objProdOrc.setDescontounitario(tributacao.getDouble(4));
                objProdOrc.setAcrescimounitario(tributacao.getDouble(6));
                objProdOrc.setTotalacrescimo(tributacao.getDouble(7));
                objProdOrc.setTotalprodutocdesc((tributacao.getDouble(2) * tributacao.getInt(1)) - tributacao.getDouble(5) + objProdOrc.getTotalacrescimo());
                objProdOrc.setCfop(tributacao.getString(8));
                objProdOrc.setCsosn(tributacao.getInt(9));
                objProdOrc.setCstcofins(tributacao.getString(10));
                objProdOrc.setCstpis(tributacao.getString(11));
                objProdOrc.setOrigem(tributacao.getInt(12));
                objProdOrc.setCodigoNcm(tributacao.getString(13));
                objProdOrc.setIdunidade(selectIdunidadeProd(objProdOrc.getIddoproduto()));

                listadeItens.add(objProdOrc);


                objdocumento.setTotalquantidade(objdocumento.getTotalquantidade() + objProdOrc.getQuantidade());
                objdocumento.setTotaldocumento(objdocumento.getTotaldocumento() + objProdOrc.getTotalproduto());
                objdocumento.setTotaldesconto(Double.parseDouble(String.valueOf(objdocumento.getTotaldesconto() + objProdOrc.getTotaldesconto())));
                objdocumento.setTotalacrescimo(Double.parseDouble(String.valueOf(objdocumento.getTotalacrescimo() + objProdOrc.getTotalacrescimo())));
                objdocumento.setTotaldocumentocdesc(objdocumento.getTotaldocumentocdesc() + objProdOrc.getTotalprodutocdesc());
                objdocumento.setNumerodeprodutos(tributacao.getCount());
                //binformarCPF.setText("CPF:" + String.valueOf(objRelOrc.getCpfcnpj()));
                bFinalizaVenda.setBackgroundResource(selecionado);
                bFinalizaOrc.setBackgroundResource(finalizarorc);

                atualizarlista();

            }
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Houve uma falha");
            builder.setMessage("Orcamento de número " + numOrcamento + " não pôde ser encontrado");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.cancel();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setBackgroundColor(Color.BLUE);
            nbutton.setTextSize(20);
            nbutton.setScaleY(1);
            nbutton.setScaleX(1);
            nbutton.setX(-60);
            nbutton.setTextColor(Color.WHITE);
        }

    }

    public int selectIdunidadeProd(int idproduto) {

        String rawQuery = "SELECT produto.idunidade FROM produto  WHERE produto.idproduto = " + idproduto;
        SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);
        int idUnidadeprod = 0;

        if (cursor.moveToFirst()) {
            idUnidadeprod = cursor.getInt(0);
        }

        return idUnidadeprod;
    }

    public void modificar() {
        String mensaje = String.valueOf("desbloqueado");
        statusFirebaseDatabase.setValue(mensaje);

    }

   // @SuppressLint("SetTextI18n")
    public void inicianovodocumento() {
        objdocumento = new modelDocumento();
        listadeItens.clear();
        binformarCPF.setText("Informar CPF");

        atualizarlista();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater ingflaterr = getMenuInflater();
        Menu teste = null;
        ingflaterr.inflate(R.menu.menu_orcamento, menu);
        // cancelarVendaSatControlId = (MenuItem) findViewById(R.id.cancelarVendaOrc);

        return super.onCreateOptionsMenu(menu);
    }

    public void onClick(View view) {
        final ArrayList<modelParametros> listadeItensParam = new ArrayList<>();

        final AdapterParametros itensparam = new AdapterParametros(this, listadeItensParam);
        containerGridViewParam.setAdapter(itensparam);
        switch (view.getId()) {
            case R.id.btnalertausb:
                btnAlertaUsb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conectSatControlid();
                        conectImpControlid();

                    }
                });

            case R.id.btncontador:
                btncontador.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       btnGravarAlt.setBackgroundResource(semfoco);
                                                       tvInfo.setText("");
                                                       btncontador.setBackgroundResource(corparametroselect);
                                                       btndesenvolvedora.setBackgroundResource(corparametrosemfoco);
                                                       btndiversos.setBackgroundResource(corparametrosemfoco);
                                                       btnemitente.setBackgroundResource(corparametrosemfoco);
                                                       btnimpressora.setBackgroundResource(corparametrosemfoco);
                                                       btnrevenda.setBackgroundResource(corparametrosemfoco);
                                                       btnsat.setBackgroundResource(corparametrosemfoco);
                                                       listadeItensParam.clear();
                                                       modelParametros objParam = new modelParametros();
                                                       objParam.setGrupo("CONTADOR");
                                                       objParam.setNome("EMAIL");
                                                       objParam.setIdparametro(32);
                                                       objParam.setObservacao("E-mail de contato do contador.");
                                                       tvDescricao.setVisibility(View.INVISIBLE);
                                                       tvInfoDescr.setVisibility(View.INVISIBLE);
                                                       listadeItensParam.add(objParam);
                                                       itensparam.notifyDataSetChanged();
//
                                                   }
                                               }

                );
            case id.btnparametros:
                btnParametros.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         layoutConfiguraparam.setVisibility(VISIBLE);
                                                         layoutStatusSistema.setVisibility(INVISIBLE);
                                                         btnParametros.setVisibility(INVISIBLE);
                                                         btnStatusSistema.setVisibility(INVISIBLE);
                                                     }
                                                 }

                );
            case id.btnstatusistema:
                btnStatusSistema.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Button btnConsultarStatusOper = findViewById(R.id.btnConsultarStatusOper);
                                                            btnConsultarStatusOper.setVisibility(VISIBLE);
                                                            layoutConfiguraparam.setVisibility(INVISIBLE);
                                                            layoutStatusSistema.setVisibility(VISIBLE);
                                                            btnParametros.setVisibility(INVISIBLE);
                                                            btnStatusSistema.setVisibility(INVISIBLE);
                                                        }
                                                    }

                );
            case R.id.btndesenvolvedora:
                btndesenvolvedora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btncontador.setBackgroundResource(corparametrosemfoco);
                        btndesenvolvedora.setBackgroundResource(corparametroselect);
                        btndiversos.setBackgroundResource(corparametrosemfoco);
                        btnemitente.setBackgroundResource(corparametrosemfoco);
                        btnimpressora.setBackgroundResource(corparametrosemfoco);
                        btnrevenda.setBackgroundResource(corparametrosemfoco);
                        btnsat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 3; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("ASSINATURA");
                                objParam.setIdparametro(5);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            } else {

                                if (i == 1) {
                                    modelParametros objParam = new modelParametros();
                                    objParam.setNome("CNPJ");
                                    objParam.setIdparametro(4);
                                    tvDescricao.setVisibility(View.INVISIBLE);
                                    tvInfoDescr.setVisibility(View.INVISIBLE);
                                    listadeItensParam.add(objParam);
                                    itensparam.notifyDataSetChanged();
//
                                } else {

                                    if (i == 2) {
                                        modelParametros objParam = new modelParametros();
                                        objParam.setNome("UF");
                                        objParam.setIdparametro(3);
                                        tvDescricao.setVisibility(View.INVISIBLE);
                                        tvInfoDescr.setVisibility(View.INVISIBLE);
                                        listadeItensParam.add(objParam);
                                        itensparam.notifyDataSetChanged();
//
                                    }
                                }
                            }
                        }
                    }
                });

            case R.id.btndiversos:
                btndiversos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btncontador.setBackgroundResource(corparametrosemfoco);
                        btndesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btndiversos.setBackgroundResource(corparametroselect);
                        btnemitente.setBackgroundResource(corparametrosemfoco);
                        btnimpressora.setBackgroundResource(corparametrosemfoco);
                        btnrevenda.setBackgroundResource(corparametrosemfoco);
                        btnsat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 1; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("EM DESENVOLVIMENTO");
                                // objParam.setIdparametro(5);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                        }
                    }
                });


            case R.id.btnemitente:
                btnemitente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvInfo.setText("");
                        btnGravarAlt.setBackgroundResource(semfoco);
                        btnemitente.setBackgroundResource(corparametroselect);
                        btncontador.setBackgroundResource(corparametrosemfoco);
                        btndesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btndiversos.setBackgroundResource(corparametrosemfoco);
                        btnimpressora.setBackgroundResource(corparametrosemfoco);
                        btnrevenda.setBackgroundResource(corparametrosemfoco);
                        btnsat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 12; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NOMEFANTASIA");
                                objParam.setIdparametro(33);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            } else {

                                if (i == 1) {
                                    modelParametros objParam = new modelParametros();
                                    objParam.setNome("RAZÃO SOCIAL");
                                    objParam.setIdparametro(2);
                                    tvDescricao.setVisibility(View.INVISIBLE);
                                    tvInfoDescr.setVisibility(View.INVISIBLE);
                                    listadeItensParam.add(objParam);
                                    itensparam.notifyDataSetChanged();

                                } else {

                                    if (i == 2) {
                                        modelParametros objParam = new modelParametros();
                                        objParam.setNome("CNPJ");
                                        objParam.setIdparametro(1);
                                        tvDescricao.setVisibility(View.INVISIBLE);
                                        tvInfoDescr.setVisibility(View.INVISIBLE);
                                        listadeItensParam.add(objParam);
                                        itensparam.notifyDataSetChanged();

                                    } else {

                                        if (i == 3) {
                                            modelParametros objParam = new modelParametros();
                                            objParam.setNome("IE");
                                            objParam.setIdparametro(7);
                                            tvDescricao.setVisibility(View.INVISIBLE);
                                            tvInfoDescr.setVisibility(View.INVISIBLE);
                                            listadeItensParam.add(objParam);
                                            itensparam.notifyDataSetChanged();


                                        } else {

                                            if (i == 4) {
                                                modelParametros objParam = new modelParametros();
                                                objParam.setNome("CEP");
                                                objParam.setIdparametro(13);
                                                tvDescricao.setVisibility(View.INVISIBLE);
                                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                                listadeItensParam.add(objParam);
                                                itensparam.notifyDataSetChanged();


                                            } else {

                                                if (i == 5) {
                                                    modelParametros objParam = new modelParametros();
                                                    objParam.setNome("UF");
                                                    objParam.setIdparametro(16);
                                                    tvDescricao.setVisibility(View.INVISIBLE);
                                                    tvInfoDescr.setVisibility(View.INVISIBLE);
                                                    listadeItensParam.add(objParam);
                                                    itensparam.notifyDataSetChanged();


                                                } else {

                                                    if (i == 6) {
                                                        modelParametros objParam = new modelParametros();
                                                        objParam.setNome("MUNICIPIO");
                                                        objParam.setIdparametro(12);
                                                        tvDescricao.setVisibility(View.INVISIBLE);
                                                        tvInfoDescr.setVisibility(View.INVISIBLE);
                                                        listadeItensParam.add(objParam);
                                                        itensparam.notifyDataSetChanged();


                                                    } else {

                                                        if (i == 7) {
                                                            modelParametros objParam = new modelParametros();
                                                            objParam.setNome("BAIRRO");
                                                            objParam.setIdparametro(11);
                                                            tvDescricao.setVisibility(View.INVISIBLE);
                                                            tvInfoDescr.setVisibility(View.INVISIBLE);
                                                            listadeItensParam.add(objParam);
                                                            itensparam.notifyDataSetChanged();


                                                        } else {

                                                            if (i == 8) {
                                                                modelParametros objParam = new modelParametros();
                                                                objParam.setNome("ENDEREÇO");
                                                                objParam.setGrupo("EMITENTE");
                                                                objParam.setIdparametro(8);
                                                                tvDescricao.setVisibility(View.INVISIBLE);
                                                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                                                listadeItensParam.add(objParam);
                                                                itensparam.notifyDataSetChanged();


                                                            } else {

                                                                if (i == 9) {
                                                                    modelParametros objParam = new modelParametros();
                                                                    objParam.setNome("NÚMERO");
                                                                    objParam.setGrupo("EMITENTE");
                                                                    objParam.setIdparametro(9);
                                                                    tvDescricao.setVisibility(View.INVISIBLE);
                                                                    tvInfoDescr.setVisibility(View.INVISIBLE);
                                                                    listadeItensParam.add(objParam);
                                                                    itensparam.notifyDataSetChanged();


                                                                } else {

                                                                    if (i == 10) {
                                                                        modelParametros objParam = new modelParametros();
                                                                        objParam.setNome("COMPLEMENTO");
                                                                        objParam.setGrupo("EMITENTE");
                                                                        objParam.setIdparametro(10);
                                                                        tvDescricao.setVisibility(View.INVISIBLE);
                                                                        tvInfoDescr.setVisibility(View.INVISIBLE);
                                                                        listadeItensParam.add(objParam);
                                                                        itensparam.notifyDataSetChanged();


                                                                    } else {

                                                                        if (i == 11) {
                                                                            modelParametros objParam = new modelParametros();
                                                                            objParam.setNome("IMPOSTO ESTADUAL");
                                                                            objParam.setGrupo("EMITENTE");
                                                                            objParam.setIdparametro(20);
                                                                            tvDescricao.setVisibility(View.INVISIBLE);
                                                                            tvInfoDescr.setVisibility(View.INVISIBLE);
                                                                            listadeItensParam.add(objParam);
                                                                            itensparam.notifyDataSetChanged();


                                                                        } else {

                                                                            if (i == 12) {
                                                                                modelParametros objParam = new modelParametros();
                                                                                objParam.setNome("IMPOSTO FEDERAL");
                                                                                objParam.setGrupo("EMITENTE");
                                                                                objParam.setIdparametro(19);
                                                                                tvDescricao.setVisibility(View.INVISIBLE);
                                                                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                                                                listadeItensParam.add(objParam);
                                                                                itensparam.notifyDataSetChanged();


                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }


                            }
                        }
                    }
                });
            case R.id.btnimpressora:
                btnimpressora.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btncontador.setBackgroundResource(corparametrosemfoco);
                        btndesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btndiversos.setBackgroundResource(corparametrosemfoco);
                        btnemitente.setBackgroundResource(corparametrosemfoco);
                        btnimpressora.setBackgroundResource(corparametroselect);
                        btnrevenda.setBackgroundResource(corparametrosemfoco);
                        btnsat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 5; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("COLUNAS");
                                objParam.setIdparametro(28);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 1) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("CONFIRMAÇAO");
                                objParam.setIdparametro(41);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 2) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("GUILHOTINA");
                                objParam.setIdparametro(52);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 3) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("MODELO");
                                objParam.setIdparametro(27);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 4) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NOME");
                                objParam.setIdparametro(29);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 5) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NUMEROVIAS");
                                objParam.setIdparametro(37);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                        }
                    }
                });
            case R.id.btnrevenda:
                btnrevenda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btncontador.setBackgroundResource(corparametrosemfoco);
                        btndesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btndiversos.setBackgroundResource(corparametrosemfoco);
                        btnemitente.setBackgroundResource(corparametrosemfoco);
                        btnimpressora.setBackgroundResource(corparametrosemfoco);
                        btnrevenda.setBackgroundResource(corparametroselect);
                        btnsat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 5; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("EMAIL");
                                objParam.setIdparametro(25);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 1) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("ENDEREÇO");
                                objParam.setIdparametro(23);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 2) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("INFORMATIVO");
                                objParam.setIdparametro(22);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 3) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("LOGOTIPO");
                                objParam.setIdparametro(21);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 4) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NOME");
                                objParam.setIdparametro(26);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 5) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("TELEFONE");
                                objParam.setIdparametro(24);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                        }
                    }
                });

            case R.id.btnsat:
                btnsat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btncontador.setBackgroundResource(corparametrosemfoco);
                        btndesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btndiversos.setBackgroundResource(corparametrosemfoco);
                        btnemitente.setBackgroundResource(corparametrosemfoco);
                        btnimpressora.setBackgroundResource(corparametrosemfoco);
                        btnrevenda.setBackgroundResource(corparametrosemfoco);
                        btnsat.setBackgroundResource(corparametroselect);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 5; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("AMBIENTE");
                                objParam.setIdparametro(15);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 1) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("ATIVAÇÃO");
                                objParam.setIdparametro(17);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 2) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("CAIXA");
                                objParam.setIdparametro(14);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 3) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("MODELO");
                                objParam.setIdparametro(18);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 4) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("SERIE");
                                objParam.setIdparametro(6);
                                tvDescricao.setVisibility(View.INVISIBLE);
                                tvInfoDescr.setVisibility(View.INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }

                        }
                    }
                });


            case id.btninformcpf:
                binformarCPF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Tecladocpfcnpj.instance == null) {
                            Intent intent = new Intent(getApplication(), Tecladocpfcnpj.class);
                            //ini cia a activity desejada, enquanto está continua aberta esperando
                            // um resultado
                            startActivityForResult(intent, 1);
                        }
                    }
                });
            case id.bCategoria:
                bcateg.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelcCadastrarCat(v);
                        lcateg();
                    }
                });
                break;
            case id.bProd:
                bprod.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelcCadastrarProd(v);
                        lprod();
                    }
                });
                break;
            case R.id.etdatainicial:
                dtinicialcontador.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar calendar = new GregorianCalendar();
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                        //calendar.set(2018, 0, 1);
                        final int ano = calendar.get(Calendar.YEAR);
                        final int mes = calendar.get(Calendar.MONTH);
                        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog date = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListener,
                                ano, mes, dia);
                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
                        //  Log.d(TAG, "onDataSet: yyyy-mm-dd: " + ano + "-"  + mes + "-"  + dia);
                        String diaString = Integer.toString(dia);
                        String mesString = Integer.toString(mes);

                        if (diaString.length() < 2) diaString = "0" + dia;
                        if (mesString.length() < 2) mesString = "0" + mes;
                        dataInicialAux = ano + "-" + mesString + "-" + diaString;
                        String data = diaString + "/" + mesString + "/" + ano;
                        dtinicialcontador.setText(data);
                    }
                };
                break;
            case R.id.etdatafinal:
                dtfinalcontador.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        final int ano = calendar.get(Calendar.YEAR);
                        final int mes = calendar.get(Calendar.MONTH);
                        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        int diaaux = dia + 1;
                        String diaString = Integer.toString(dia);
                        String diaauxString = Integer.toString(diaaux);
                        String mesString = Integer.toString(mes);

                        if (diaString.length() < 2 & !diaString.equalsIgnoreCase("9")) {
                            diaauxString = "0" + diaaux;
                            diaString = "0" + dia;
                        }
                        if (mesString.length() < 2) mesString = "0" + mes;
                        dataFinalAux = ano + "-" + mesString + "-" + diaauxString;
                        DatePickerDialog date = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListenerr,
                                ano, mes, 12);


                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListenerr = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
                        //  Log.d(TAG, "onDataSet: yyyy-mm-dd: " + ano + "-"  + mes + "-" + dia);
                        int diaaux = dia + 1;
                        String diaString = Integer.toString(dia);
                        String diaauxString = Integer.toString(diaaux);
                        String mesString = Integer.toString(mes);


                        if (diaString.length() < 2 & !diaString.equalsIgnoreCase("9")) {
                            diaauxString = "0" + diaaux;
                            diaString = "0" + dia;
                        }
                        if (mesString.length() < 2) mesString = "0" + mes;
                        dataFinalAux = ano + "-" + mesString + "-" + diaauxString;
                        String data = diaString + "/" + mesString + "/" + ano;
                        dtfinalcontador.setText(data);
                    }
                };
                break;

            case R.id.editDatainicial:
                dtinicialorcamento.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar calendar = new GregorianCalendar();
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                        //calendar.set(2018, 0, 1);
                        final int ano = calendar.get(Calendar.YEAR);
                        final int mes = calendar.get(Calendar.MONTH);
                        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog date = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListener,
                                ano, mes, dia);
                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
                        //  Log.d(TAG, "onDataSet: yyyy-mm-dd: " + ano + "-"  + mes + "-"  + dia);
                        String diaString = Integer.toString(dia);
                        String mesString = Integer.toString(mes);

                        if (diaString.length() < 2) diaString = "0" + dia;
                        if (mesString.length() < 2) mesString = "0" + mes;
                        dataInicialAux = ano + "-" + mesString + "-" + diaString;
                        String data = diaString + "/" + mesString + "/" + ano;
                        dtinicialorcamento.setText(data);
                    }
                };
                break;
            case R.id.editDatafinal:
                dtfinalormaneto.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        final int ano = calendar.get(Calendar.YEAR);
                        final int mes = calendar.get(Calendar.MONTH);
                        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        int diaaux = dia + 1;
                        String diaString = Integer.toString(dia);
                        String diaauxString = Integer.toString(diaaux);
                        String mesString = Integer.toString(mes);

                        if (diaString.length() < 2 & !diaString.equalsIgnoreCase("9")) {
                            diaauxString = "0" + diaaux;
                            diaString = "0" + dia;
                        }
                        if (mesString.length() < 2) mesString = "0" + mes;
                        dataFinalAux = ano + "-" + mesString + "-" + diaauxString;
                        DatePickerDialog date = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListenerr,
                                ano, mes, 12);


                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListenerr = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
                        //  Log.d(TAG, "onDataSet: yyyy-mm-dd: " + ano + "-"  + mes + "-" + dia);
                        int diaaux = dia + 1;
                        String diaString = Integer.toString(dia);
                        String diaauxString = Integer.toString(diaaux);
                        String mesString = Integer.toString(mes);


                        if (diaString.length() < 2 & !diaString.equalsIgnoreCase("9")) {
                            diaauxString = "0" + diaaux;
                            diaString = "0" + dia;
                        }
                        if (mesString.length() < 2) mesString = "0" + mes;
                        dataFinalAux = ano + "-" + mesString + "-" + diaauxString;
                        String data = diaString + "/" + mesString + "/" + ano;
                        dtfinalormaneto.setText(data);
                    }
                };
                break;
        }
        containerGridViewParam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvDescricao.setVisibility(View.VISIBLE);
                tvInfoDescr.setVisibility(View.INVISIBLE);
                tvInfoDescr.setVisibility(View.VISIBLE);
                btnGravarAlt.setBackgroundResource(selecionado);
                if (controle <= (listadeItensParam.size() - 1)) {
                    modelParametros c1 = (modelParametros) adapterView.getItemAtPosition(controle);
                    c1.setSelect("N");
                }

                final modelParametros c = (modelParametros) adapterView.getItemAtPosition(i);
                Cursor parametro = (dadosOpenHelper.selecValorParam(String.valueOf(c.getIdparametro())));

                for (int ei = 0; ei < parametro.getCount(); ei++) {
                    parametro.moveToPosition(ei);
                    tvInfo.setText(String.valueOf(parametro.getString(0)));
                    tvInfoDescr.setText(String.valueOf(parametro.getString(1)));

                }
                c.setSelect("S");
                btnGravarAlt.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {


                                                        c.setValor(String.valueOf(tvInfo.getText()));
                                                        dadosOpenHelper.addalterparametro(c);
                                                        parametros();
                                                        Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                                        toast.setView(toastLayout);
                                                        toast.show();
                                                    }
                                                }

                );
                controle = i;
                itensparam.notifyDataSetChanged();
            }
        });


    }

    public void parametros() {

        Cursor revenda = (dadosOpenHelper.selecParametros());
        if (revenda.moveToFirst()) {

            for (int i = 0; i < revenda.getCount(); i++) {
                revenda.moveToPosition(i);
                if (revenda.getInt(0) == 32) {
                    objparam.setContadorEmail(revenda.getString(1));
                }
                if (revenda.getInt(0) == 2) {
                    objparam.setEmitenteRazaoSocial(deAccent(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 1) {
                    objparam.setEmitenteCNPJ(revenda.getString(1));
                }
                if (revenda.getInt(0) == 4) {
                    objparam.setDesenvolvedoraCNPJ(revenda.getString(1));
                }
                if (revenda.getInt(0) == 5) {
                    objparam.setDesenvolvedoraAssinatura(revenda.getString(1));
                }
                if (revenda.getInt(0) == 6) {
                    objparam.setSatSerie(revenda.getString(1));
                }
                if (revenda.getInt(0) == 7) {
                    objparam.setEmitenteIe(revenda.getString(1));
                }
                if (revenda.getInt(0) == 8) {
                    objparam.setEmitenteEndereco(deAccent(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 9) {
                    objparam.setEmitenteNumero(revenda.getString(1));
                }
                if (revenda.getInt(0) == 10) {
                    objparam.setEmitenteComplemento(deAccent(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 11) {
                    objparam.setEmitenteBairro(deAccent(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 12) {
                    objparam.setEmitenteMunicipio(deAccent(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 13) {
                    objparam.setEmitenteCep(revenda.getString(1));
                }
                if (revenda.getInt(0) == 14) {
                    objparam.setSatCaixa(revenda.getString(1));
                }
                if (revenda.getInt(0) == 16) {
                    objparam.setEmitenteUf(revenda.getString(1));
                }
                if (revenda.getInt(0) == 17) {
                    objparam.setSatCodAtivacao(revenda.getString(1));
                }
                if (revenda.getInt(0) == 18) {
                    objparam.setSatModelo(revenda.getString(1));
                }
                if (revenda.getInt(0) == 19) {
                    objparam.setImpostofederal(revenda.getDouble(1));

                }
                if (revenda.getInt(0) == 20) {
                    objparam.setImpostoestadual(revenda.getDouble(1));
                }
                if (revenda.getInt(0) == 24) {
                    objparam.setRevendaTelefone(revenda.getString(1));
                }
                if (revenda.getInt(0) == 41) {
                    objparam.setImpressoraConfirm(revenda.getString(1));
                }
                if (revenda.getInt(0) == 49) {
                    objparam.setSenhaConfiguracao(revenda.getString(1));
                }

            }
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_orçamento) {
            //  cancelarVendaSatControlId.setTitle("ademir");
            layoutCupom.setVisibility(INVISIBLE);
            layoutOrcamento.setVisibility(VISIBLE);
            //item.setTitle("aa");

            return true;
        }
        if (id == R.id.action_venda) {

            layoutCupom.setVisibility(VISIBLE);
            layoutOrcamento.setVisibility(INVISIBLE);
            return true;
        }
        if ((id == R.id.cancelarVendaOrc) || (id == R.id.cancelarVenda)) {
            DecimalFormat converte = new DecimalFormat("0.00");
            final Cursor cursor = dadosOpenHelper.selecionariddocCanc();
            String cfeCancel = "";
            statusSat();
            if (cursor != null) {
                cursor.moveToLast();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyOwnDialogTitle));
            //define o titulo
            builder.setTitle("Cancelamento de venda");
            //define a mensagem
            if (cursor.getCount() != 0){
                cfeCancel ="Tem certeza que deseja cancelar a venda?" + "\n" + "\n" + "Cupom: " + String.format("%06d", cursor.getInt(22)) + "\n" + "Total: R$ " + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + "\n" + "CPF: " + cursor.getString(6) + "\n";
                     //   "\"Tem certeza que deseja cancelar a venda?\" + \"\\n\" + \"\\n\" + \"Cupom: \" + String.format(\"%06d\", cursor.getInt(22)) + \"\\n\" + \"Total: R$ \" + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + \"\\n\" + CFe" + cursor.getString(6);
            }
            else{
                cfeCancel = "Nenhuma Venda Realizada neste equipamento";
            }
            final String cfeCanc = cfeCancel;
           // builder.setMessage("Tem certeza que deseja cancelar a venda?" + "\n" + "\n" + "Cupom: " + String.format("%06d", cursor.getInt(22)) + "\n" + "Total: R$ " + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + "\n" + "CPF: " + cfeCanc + "\n");
            builder.setMessage(cfeCanc);
            //define um botão como positivo
            final AlertDialog.Builder sim = builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.O)
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onClick(DialogInterface arg0, int arg1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        objCupomFiscal.setChaveCfeCanc(cfeCanc);
                        objCupomFiscal.setCoo(Integer.parseInt(cursor.getString(22)));
                        cancelarVendaSatControlId(cursor);


                    }
                    DadosVenda d = new DadosVenda();
                    //   cancelarVendaSatControlId(cursor);
                    // StringBuilder teste = d.criarDadosVendaCanc(cfeCanc);
                    // String  retornosatt =   SATiD.getInstance().CancelarUltimaVenda(123456,"senha12345",cfeCanc,d.criarDadosVendaCanc(cfeCanc).toString());
                    //String[]  retornosat = retornosatt.split("\\|");
                    // VENDER.setText(retornosat[3]);
                    //Intent intent = new Intent(Intent.ACTION_MAIN);
                    //intent.addCategory(Intent.CATEGORY_HOME);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // startActivity(intent);
                }
            });
            builder.setNeutralButton("Outro cupom", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    chamarteclado();


                }
            }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    SAIR.setBackgroundResource(semfoco);
                    arg0.cancel();
                }
            });
            //define um botão como negativo.
            //cria o AlertDialog
            AlertDialog alerta = builder.create();
            //Exibe

            alerta.show();
            Button neubutton = alerta.getButton(DialogInterface.BUTTON_NEUTRAL);
            neubutton.setBackgroundResource(corparametroselect);
            neubutton.setTextSize(18);
            neubutton.setScaleY(1);
            neubutton.setScaleX(1);
            neubutton.setX(50);
            neubutton.setTextColor(Color.WHITE);

            Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setBackgroundColor(Color.RED);
            nbutton.setTextSize(20);
            nbutton.setScaleY(1);
            nbutton.setScaleX(1);
            nbutton.setX(60);
            nbutton.setTextColor(Color.WHITE);

            Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setBackgroundColor(Color.GREEN);
            pbutton.setTextSize(20);
            pbutton.setScaleY(1);
            pbutton.setScaleX(1);
            pbutton.setX(-140);
            if (cursor.getCount()==0) {
                pbutton.setVisibility(INVISIBLE);
                nbutton.setText("Voltar");
            }
            else{
                pbutton.setVisibility(VISIBLE);
            }
            return true;
        }
        if ((id == R.id.reimprimircupomorc) || (id == R.id.reimprimircupom)) {
            Intent intent = new Intent(getApplicationContext(), ReimprimirCupom.class);

            // intent.putExtra("Dados", c);
            startActivity(intent);
            return true;
        }
        if ((id == R.id.efetivarOrc) || (id == R.id.efetivorcvend)) {
            if (TecladoQuantInteiro.instance == null) {
                controleteclado = 21;
                Intent intents = new Intent(this, TecladoQuantInteiro.class);
                ((Activity) this).startActivityForResult(intents, 5);
            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void consultarCateg() {

        containerGridViewprod.setVisibility(INVISIBLE);
        containerGridViewCateg.setVisibility(VISIBLE);
        ArrayList<modelCategorias> listadeItensVendaCateg = new ArrayList<>();
        MainAdapterCateg itensVendaCateg = new MainAdapterCateg(this, listadeItensVendaCateg);
        containerGridViewCateg.setAdapter(itensVendaCateg);
        Cursor cursor = dadosOpenHelper.selecionarVendCateg();

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                modelCategorias objCateg = new modelCategorias();
                cursor.moveToPosition(i);
                objCateg.setIdcategoria(cursor.getInt(0));
                objCateg.setDescricao(cursor.getString(1));
                objCateg.setCor(cursor.getString(2));
                listadeItensVendaCateg.add(objCateg);
            }

            itensVendaCateg.notifyDataSetChanged();
            cursor.close();
        }
    }

    public void limpaPesqProd(View view) {
        Button btnClearPesqProd;
        btnClearPesqProd = findViewById(id.btnClearPesqProd);
        // EditText buscarProd = findViewById(id.tvPesquisarProd);
        buscarProd.setText(null);
        btnClearPesqProd.setVisibility(view.INVISIBLE);
        // itensdofiltroprod.clear();

    }

    public void buscarProduto(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button btnClearPesqProd;
        btnClearPesqProd = findViewById(id.btnClearPesqProd);

        String texto;
        texto = buscarProd.getText().toString();
        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        itensVendaProd.clear();
        containerGridViewprod.setAdapter(itensVendaProd);
        SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();

        String rawQuery = "SELECT produto.idproduto, produto.descricao, categoria.cor, produto.preco, ncm.codigoncm, produto.origem, produto.csosn, " +
                "produto.aliqicms, produto.cstpis, produto.aliqpis, produto.cstcofins, produto.aliqcofins, produto.codcontribsocial, produto.cest, produto.cfop, " +
                " produto.precovariavel, produto.idunidade FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.status = 'A' and produto.descricao LIKE '%" + texto + "%'";
        Cursor cursor = db.rawQuery(rawQuery, null);


        if (cursor.getCount() == 0) {

            modelProdutos objProdd = new modelProdutos();

            objProdd.setDescricao("VOLTAR");
            listadeItensVendaProd.add(objProdd);

        }

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount() + 1; i++) {
                modelProdutos objProd = new modelProdutos();
                if (i == 0) {
                    objProd.setDescricao("VOLTAR");
                    listadeItensVendaProd.add(objProd);

                } else {
                    cursor.moveToPosition(i - 1);


                    objProd.setIdproduto(cursor.getInt(0));
                    objProd.setDescricao(cursor.getString(1));
                    objProd.setCor(cursor.getString(2));
                    objProd.setPreco(cursor.getDouble(3));
                    objProd.setCodigoNcm(cursor.getString(4));
                    objProd.setOrigem(cursor.getInt(5));
                    objProd.setCsosn(cursor.getInt(6));
                    objProd.setAliqicms(cursor.getDouble(7));
                    objProd.setCstpis(cursor.getString(8));
                    objProd.setAliqpis(cursor.getDouble(9));
                    objProd.setCstcofins(cursor.getString(10));
                    objProd.setAliqicofins(cursor.getDouble(11));
                    objProd.setCodcontribsocial(cursor.getString(12));
                    objProd.setCest(String.valueOf(13));
                    objProd.setCfop(cursor.getString(14));
                    objProd.setPrecovariavel(cursor.getString(15));
                    objProd.setIdunidade(cursor.getInt(16));
                    if (objProd.getIdunidade() == 1) {
                        objProd.setUndSigla("UN");
                    }
                    if (objProd.getIdunidade() == 2) {
                        objProd.setUndSigla("KG");
                    }
                    if (objProd.getIdunidade() == 3) {
                        objProd.setUndSigla("M");
                    }


                    listadeItensVendaProd.add(objProd);

                }
            }
            cursor.close();
        }

        containerGridViewprod.setVisibility(VISIBLE);
        btnClearPesqProd.setVisibility(view.VISIBLE);
        containerGridViewCateg.setVisibility(INVISIBLE);
    }

    public void consultarProd(int idcateg) {

        itensVendaProd.clear();
        containerGridViewprod.setAdapter(itensVendaProd);
        SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();
        Cursor cursor = dadosOpenHelper.selecionarVendProd(idcateg);


        if (cursor.getCount() == 0) {

            modelProdutos objProdd = new modelProdutos();

            objProdd.setDescricao("VOLTAR");
            listadeItensVendaProd.add(objProdd);

        }

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount() + 1; i++) {
                modelProdutos objProd = new modelProdutos();
                if (i == 0) {
                    objProd.setDescricao("VOLTAR");
                    listadeItensVendaProd.add(objProd);

                } else {
                    cursor.moveToPosition(i - 1);


                    objProd.setIdproduto(cursor.getInt(0));
                    objProd.setDescricao(cursor.getString(1));
                    objProd.setCor(cursor.getString(2));
                    objProd.setPreco(cursor.getDouble(3));
                    objProd.setCodigoNcm(cursor.getString(4));
                    objProd.setOrigem(cursor.getInt(5));
                    objProd.setCsosn(cursor.getInt(6));
                    objProd.setAliqicms(cursor.getDouble(7));
                    objProd.setCstpis(cursor.getString(8));
                    objProd.setAliqpis(cursor.getDouble(9));
                    objProd.setCstcofins(cursor.getString(10));
                    objProd.setAliqicofins(cursor.getDouble(11));
                    objProd.setCodcontribsocial(cursor.getString(12));
                    objProd.setCest(String.valueOf(13));
                    objProd.setCfop(cursor.getString(14));
                    objProd.setPrecovariavel(cursor.getString(15));
                    objProd.setIdunidade(cursor.getInt(16));
                    if (objProd.getIdunidade() == 1) {
                        objProd.setUndSigla("UN");
                    }
                    if (objProd.getIdunidade() == 2) {
                        objProd.setUndSigla("KG");
                    }
                    if (objProd.getIdunidade() == 3) {
                        objProd.setUndSigla("M");
                    }


                    listadeItensVendaProd.add(objProd);

                }
            }
            cursor.close();
        }

        containerGridViewprod.setVisibility(VISIBLE);
        containerGridViewCateg.setVisibility(INVISIBLE);
    }

    public void chamarteclado() {
        if (TecladoQuantInteiro.instance == null) {
            controleteclado = 20;
            Intent intents = new Intent(this, TecladoQuantInteiro.class);
            ((Activity) this).startActivityForResult(intents, 4);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public void addprodvenda(modelProdutos model) {
        buscarProd.setText("");
        int quantt = 1;
          double subtotalproduto = 0;
        itensAdapter.notifyDataSetChanged();
        boolean existe = false;
        DecimalFormat converte = new DecimalFormat("0.00");
        subtotalproduto = (model.getPreco()) * quantt;
        itensAdapter.notifyDataSetChanged();

        for (int i = 1; i <= listadeItens.size(); i++) {
            objCupom2 = new modelDocumentoProduto();
            objCupom2 = listadeItens.get(i - 1);
            if ((model.getIdproduto() == objCupom2.getIddoproduto()) && (model.getPreco() == objCupom2.getPreco())) {
                double quant = 0;
                quant = model.getQuantidade();
                objCupom = objCupom2;
                existe = true;
                // double quant = 1;
                // quant = objCupom.getQuantidade() + 1;
                objCupom.setQuantidade(objCupom.getQuantidade() + quant);
                objCupom.setTotalproduto(Double.valueOf(model.getPreco()) * objCupom.getQuantidade());
                objCupom.setTotaldesconto(objCupom.getTotaldesconto() + objCupom.getDescontounitario());
                objCupom.setTotalacrescimo(objCupom.getTotalacrescimo() + objCupom.getAcrescimounitario());
                objCupom.setTotalprodutocdesc((objCupom.getTotalprodutocdesc() + (objCupom.getPreco() * quant)) - objCupom.getDescontounitario() + objCupom.getAcrescimounitario());
                //objCupom.setTotaldesconto(objCupom.getDescontounitario() * quant);
                //objCupom.setTotalprodutocdesc(objCupom.getTotalproduto()-objCupom.getTotaldesconto());
                objdocumento.setTotalquantidade(objdocumento.getTotalquantidade() + quant);
                objdocumento.setTotaldocumento(objdocumento.getTotaldocumento() + (objCupom.getPreco() * quant));
                objdocumento.setTotaldocumentocdesc(Double.valueOf(objdocumento.getTotaldocumentocdesc() + (objCupom.getPreco() * quant) - objCupom.getDescontounitario() + objCupom.getAcrescimounitario()));
                objdocumento.setTotaldesconto(objdocumento.getTotaldesconto() + objCupom.getDescontounitario());
                objdocumento.setTotalacrescimo(objdocumento.getTotalacrescimo() + objCupom.getAcrescimounitario());
            }
        }

        itensAdapter.notifyDataSetChanged();


        if (existe == false) {
            objCupom = new modelDocumentoProduto();
            objCupom.setIddoproduto(model.getIdproduto());
            objCupom.setDescricao(deAccent(model.getDescricao()));
            objCupom.setPreco(Double.valueOf(model.getPreco()));
            objCupom.setQuantidade(model.getQuantidade());
            objCupom.setTotalproduto(Double.valueOf(model.getPreco()) * objCupom.getQuantidade());
            objCupom.setTotaldesconto(objCupom.getTotaldesconto() + objCupom.getDescontounitario());
            objCupom.setTotalacrescimo(objCupom.getTotalacrescimo() + objCupom.getAcrescimounitario());
            objCupom.setTotalprodutocdesc(objCupom.getTotalproduto());
            objCupom.setCfop((model.getCfop()));
            objCupom.setCsosn(model.getCsosn());
            objCupom.setCstcofins(model.getCstcofins());
            objCupom.setCstpis(model.getCstpis());
            objCupom.setOrigem(model.getOrigem());
            objCupom.setCodigoNcm(model.getCodigoNcm());
            objCupom.setIdunidade(model.getIdunidade());
            objCupom.setUndSigla(model.getUndSigla());
            objdocumento.setNumerodeprodutos(objdocumento.getNumerodeprodutos() + 1);
            objdocumento.setTotalquantidade(objdocumento.getTotalquantidade() + objCupom.getQuantidade());
            objdocumento.setTotaldocumento(objdocumento.getTotaldocumento() + (objCupom.getPreco() * objCupom.getQuantidade()));
            objdocumento.setTotaldocumentocdesc((objdocumento.getTotaldocumentocdesc() + (objCupom.getPreco() * objCupom.getQuantidade()) - objCupom.getDescontounitario() + objCupom.getAcrescimounitario()));
            objdocumento.setTotaldesconto(objdocumento.getTotaldesconto() + objCupom.getDescontounitario());
            objdocumento.setTotalacrescimo(objdocumento.getTotalacrescimo() + objCupom.getAcrescimounitario());
            listadeItens.add(objCupom);
            bFinalizaVenda.setBackgroundResource(selecionado);
            bFinalizaOrc.setBackgroundResource(finalizarorc);


        }


        atualizarlista();

    }

    public void atualizarlista() {
        NumberFormat dfff = NumberFormat.getCurrencyInstance(Locale.US);
        ((DecimalFormat) dfff).applyPattern("0");
        DecimalFormat converte = new DecimalFormat("0.00");
       // lprod();
        itensAdapter.notifyDataSetChanged();
        qtditensVenda.setText(String.valueOf(dfff.format(objdocumento.getTotalquantidade())));
        qtditensOrc.setText(String.valueOf(objdocumento.getTotalquantidade()));
        valorTotSemDescV.setText("R$ " + converte.format(objdocumento.getTotaldocumento()));
        valorTotSemDescO.setText("R$ " + converte.format(objdocumento.getTotaldocumento()));
        valorTotalOrc.setText("R$ " + converte.format(objdocumento.getTotaldocumentocdesc()));
        valorTotalVendas.setText("R$ " + converte.format(objdocumento.getTotaldocumentocdesc()));
        totDescontoOcr.setText("R$ " + converte.format(objdocumento.getTotaldesconto()));
        totDescontoV.setText("R$ " + converte.format(objdocumento.getTotaldesconto()));
        // binformarCPF.setText("Informar CPF");
        if (itensAdapter.getCount() == 0) {
            bFinalizaVenda.setBackgroundResource(semfoco);
            bFinalizaOrc.setBackgroundResource(semfoco);

        }


        //   itenscateg.notifyDataSetChanged();
    }

    private void conectarBanc() {
        try {
           dadosOpenHelper = new DadosOpenHelper(this);
           // conexao = dadosOpenHelper.getReadableDatabase();

        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    public void novoproduto(View view) {

        if (CriarProd.instance == null) {
            Intent intent = new Intent(this, CriarProd.class);
            startActivityForResult(intent,6);

        }
    }

    public void finalizaorcamento(View view) {
        if (CriarOrcamento.instance == null) {
            if (itensAdapter.getCount() > 0) {
                Intent intent = new Intent(this, CriarOrcamento.class);
                startActivity(intent);
            }
        }

    }

    public void finalizaVenda(View view) {
        conectImpControlid();

        if (itensAdapter.getCount() > 0) {
            if (objdocumento.getTotaldocumentocdesc() <= 10000) {
                if (finalizarVendas.instance == null) {
                    Intent intent = new Intent(getApplication(), FinalizarVendas.class);
                    startActivity(intent);
                }
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(false);
                builder.setTitle("              Valor máximo permitido!");
                builder.setMessage("            Total de troco \n" + "\n" + "              R$ ");
                //  formPag.setTotalpagamento(valorrec);
                //define um botão como positivo
                final AlertDialog.Builder sim = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {


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

    }



    public   void lprod() {
        ListView listproduto = findViewById(id.listproduto);
        ArrayList<modelProdutos> listadeprod;
        listadeprod = new ArrayList<>();
        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        String rawQuery = "SELECT produto.descricao, produto.codigoean, produto.status, produto.preco, categoria.descricao, produto.idproduto,strftime('%d/%m/%Y %H:%M',produto.dthrcriacao) , produto.csosn,produto.aliqicms,produto.cstpis,produto.aliqpis,produto.cstcofins,produto.aliqcofins,produto.codcontribsocial,produto.cest,produto.cfop, produto.idcategoria, ncm.codigoncm, produto.precovariavel, produto.idunidade, produto.origem FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade";
        Cursor cursor = dadosOpenHelper.selecionarCadProd();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    modelProdutos modelprod = new modelProdutos();
                    modelprod.setDescricao(cursor.getString(0));
                    modelprod.setCodigoean(cursor.getString(1));
                    modelprod.setStatus(cursor.getString(2));
                    modelprod.setPreco(cursor.getDouble(3));
                    modelprod.setDescricaocateg(cursor.getString(4));
                    modelprod.setDthrcriacao(cursor.getString(6));
                    modelprod.setIdproduto(cursor.getInt(5));
                    modelprod.setCsosn(cursor.getInt(7));
                    modelprod.setAliqicms(cursor.getDouble(8));
                    modelprod.setCstpis(cursor.getString(9));
                    modelprod.setAliqpis(cursor.getDouble(10));
                    modelprod.setCstcofins(cursor.getString(11));
                    modelprod.setAliqicofins(cursor.getDouble(12));
                    modelprod.setCodcontribsocial(cursor.getString(13));
                    modelprod.setCest(cursor.getString(14));
                    modelprod.setCfop(cursor.getString(15));
                    modelprod.setIdcategoria(cursor.getInt(16));
                    modelprod.setCodigoNcm(cursor.getString(17));
                    modelprod.setPrecovariavel(cursor.getString(18));
                    modelprod.setIdunidade(cursor.getInt(19));
                    modelprod.setOrigem(cursor.getInt(20));
                    listadeprod.add(modelprod);
                } while (cursor.moveToNext());
            }
            cursor.close();
            listproduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (alterarProd.instance == null) {
                        modelProdutos c = (modelProdutos) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getApplicationContext(), AlteraProd.class);

                        intent.putExtra("Dados", c);
                        startActivityForResult(intent,6);
                    }
                }
            });
        }
        AdapterProd adapterProd;
        adapterProd = new AdapterProd(this, listadeprod);
        listproduto.setAdapter(adapterProd);

    }

    private void lprodfiltra() {
        ListView listproduto = findViewById(id.listproduto);
        ArrayList<modelProdutos> listadeprod;
        listadeprod = new ArrayList<>();
        String texto;
        texto = barraproduto.getText().toString();
        Cursor cursor = dadosOpenHelper.selecionarCadProd(texto);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    modelProdutos modelprod = new modelProdutos();
                    modelprod.setDescricao(cursor.getString(0));
                    modelprod.setCodigoean(cursor.getString(1));
                    modelprod.setStatus(cursor.getString(2));
                    modelprod.setPreco(cursor.getDouble(3));
                    modelprod.setDescricaocateg(cursor.getString(4));
                    modelprod.setIdproduto(cursor.getInt(5));
                    modelprod.setDthrcriacao(cursor.getString(6));
                    modelprod.setCsosn(cursor.getInt(7));
                    modelprod.setAliqicms(cursor.getDouble(8));
                    modelprod.setCstpis(cursor.getString(9));
                    modelprod.setAliqpis(cursor.getDouble(10));
                    modelprod.setCstcofins(cursor.getString(11));
                    modelprod.setAliqicofins(cursor.getDouble(12));
                    modelprod.setCodcontribsocial(cursor.getString(13));
                    modelprod.setCest(cursor.getString(14));
                    modelprod.setCfop(cursor.getString(15));
                    modelprod.setIdcategoria(cursor.getInt(16));
                    modelprod.setCodigoNcm(cursor.getString(17));
                    modelprod.setPrecovariavel(cursor.getString(18));
                    modelprod.setIdunidade(cursor.getInt(19));
                    modelprod.setOrigem(cursor.getInt(20));
                    listadeprod.add(modelprod);
                } while (cursor.moveToNext());
            }
            cursor.close();
            listproduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    modelProdutos c = (modelProdutos) parent.getItemAtPosition(position);
                    if (AlteraProd.instance == null) {
                        Intent intent = new Intent(getApplicationContext(), AlteraProd.class);
                        intent.putExtra("Dados", c);
                        startActivityForResult(intent,6);
                    }
                }
            });
        }

        AdapterProd adapterProd;
        adapterProd = new AdapterProd(this, listadeprod);
        listproduto.setAdapter(adapterProd);
    }

    private void lcateg() {
        ListView listcateg = findViewById(id.listcategoria);
        ArrayList<modelCategorias> listadecateg;
        listadecateg = new ArrayList<>();
        Cursor cursor = dadosOpenHelper.selecionarCadCateg();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    modelCategorias modelcateg = new modelCategorias();
                    modelcateg.setDescricao(cursor.getString(0));
                    modelcateg.setCodigoNcm(cursor.getString(1));
                    modelcateg.setQuantidade(cursor.getString(2));
                    modelcateg.setCor(cursor.getString(3));
                    modelcateg.setIdcategoria(cursor.getInt(4));
                    modelcateg.setDthrcriacao(cursor.getString(5));
                    modelcateg.setOrigem(cursor.getInt(6));
                    modelcateg.setCfop(cursor.getString(7));
                    modelcateg.setCsosn(cursor.getInt(8));
                    modelcateg.setAliqicms(cursor.getDouble(9));
                    modelcateg.setCstpis(cursor.getString(10));
                    modelcateg.setAliqpis(cursor.getDouble(11));
                    modelcateg.setCstcofins(cursor.getString(12));
                    modelcateg.setAliqcofins(cursor.getDouble(13));
                    modelcateg.setCodcontribsocial(cursor.getString(14));
                    modelcateg.setCest(cursor.getString(15));
                    listadecateg.add(modelcateg);
                } while (cursor.moveToNext());
            }
            cursor.close();
            listcateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (AlteraCateg.instance == null) {
                        modelCategorias c = (modelCategorias) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getApplicationContext(), AlteraCateg.class);
                        intent.putExtra("Dados", c);
                        startActivityForResult(intent,7);
                    }
                }
            });
        }

        AdapterCateg adapterCateg = new AdapterCateg(this, listadecateg);
        listcateg.setAdapter(adapterCateg);
    }

    private void lcategfiltra() {
        ListView listcateg = findViewById(id.listcategoria);
        ArrayList<modelCategorias> listadecateg;
        listadecateg = new ArrayList<>();
        String filtro;
        filtro = barracategoria.getText().toString();
        Cursor cursor = dadosOpenHelper.selecionarCadCateg(filtro);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    modelCategorias modelcateg = new modelCategorias();
                    modelcateg.setDescricao(cursor.getString(0));
                    modelcateg.setCodigoNcm(cursor.getString(1));
                    modelcateg.setQuantidade(cursor.getString(2));
                    modelcateg.setCor(cursor.getString(3));
                    modelcateg.setIdcategoria(cursor.getInt(4));
                    modelcateg.setDthrcriacao(cursor.getString(5));
                    modelcateg.setOrigem(cursor.getInt(6));
                    modelcateg.setCfop(cursor.getString(7));
                    modelcateg.setCsosn(cursor.getInt(8));
                    modelcateg.setAliqicms(cursor.getDouble(9));
                    modelcateg.setCstpis(cursor.getString(10));
                    modelcateg.setAliqpis(cursor.getDouble(11));
                    modelcateg.setCstcofins(cursor.getString(12));
                    modelcateg.setAliqcofins(cursor.getDouble(13));
                    modelcateg.setCodcontribsocial(cursor.getString(14));
                    modelcateg.setCest(cursor.getString(15));
                    listadecateg.add(modelcateg);
                } while (cursor.moveToNext());
            }
            listcateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    modelCategorias c = (modelCategorias) parent.getItemAtPosition(position);
                    if (AlteraCateg.instance == null) {
                        Intent intent = new Intent(getApplicationContext(), AlteraCateg.class);
                        intent.putExtra("Dados", c);
                        startActivityForResult(intent,7);
                    }
                }
            });
        }
        AdapterCateg adapterCateg = new AdapterCateg(this, listadecateg);
        listcateg.setAdapter(adapterCateg);
    }

    public void filtrandoprod(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button clearprod;
        clearprod = findViewById(id.clearprod);
        String texto2;
        texto2 = barraproduto.getText().toString();
        if (texto2 != "") {
            //   itensdofiltroprod.clear();
            lprodfiltra();
            // itensprod.clear();
            clearprod.setVisibility(VISIBLE);

        }
        //
    }

    public void limpaprod(View view) {
        Button clearprod;
        clearprod = findViewById(id.clearprod);
        barraproduto.setText(null);
        lprod();
        clearprod.setVisibility(view.INVISIBLE);
        // itensdofiltroprod.clear();

    }

    public void filtrandocateg(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button clearcateg = findViewById(id.clearcateg);
        String texto2;
        texto2 = barracategoria.getText().toString();
        if (texto2 != "") {
            // itensdofiltrocateg.clear();
            lcategfiltra();
//            itenscateg.clear();
            clearcateg.setVisibility(VISIBLE);
        }
    }

    public void limpacateg(View view) {
        Button clearcateg = findViewById(id.clearcateg);
        barracategoria.setText(null);
        lcateg();
        clearcateg.setVisibility(INVISIBLE);

    }

    public void relatorioorcamento(View view) {
        auxtipoRelatorio = 0;
        relogioRelatorio.setVisibility(INVISIBLE);
        fechamento.setVisibility(INVISIBLE);
        estatisticas.setVisibility(INVISIBLE);
        orcamento.setVisibility(INVISIBLE);
        textRelatorio.setVisibility(INVISIBLE);
        tvfechamentoPor.setVisibility(INVISIBLE);
        sptipofechamento.setVisibility(INVISIBLE);
        RelatorioOrcamento.setVisibility(VISIBLE);
        RelatorioOrcamento.setText("Relatório de Orçamento");
        textdtnFinal.setVisibility(VISIBLE);
        textdtnInicial.setVisibility(VISIBLE);
        dtfinalormaneto.setVisibility(VISIBLE);
        dtinicialorcamento.setVisibility(VISIBLE);
        geraorcamento.setVisibility(VISIBLE);
        imprimeorcamento.setVisibility(VISIBLE);
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        //Log.d(TAG, "onDataSet: yyyy-mm-dd: " + ano + "-"  + mes + "-" + dia);
        int diaaux = dia + 1;
        String diaString = Integer.toString(dia);
        String diaauxString = Integer.toString(diaaux);
        String mesString = Integer.toString(mes + 1);

        if (diaString.length() < 2 && dia != 9) {
            diaString = "0" + dia;
            diaauxString = "0" + diaaux;
        } else {
            if (dia == 9) {
                diaString = "0" + dia;

            }
        }
        if (mesString.length() < 2) mesString = "0" + mesString;
        dataFinalAux = ano + "/" + mesString + "/" + diaauxString;
        //dataInicialAux = ano + "-"  + mes + "-"  + diaString;
        dataInicialAux = ano + "/" + mesString + "/" + diaString;
        // Date data = new Date();
        Date datafinal = new Date();
        //  String dataformatada = formataData.format(data);
        String dataformatadafinal = formataData.format(datafinal);
        dataInicialAux = ano + "-" + mesString + "-" + diaString;
        String data = diaString + "/" + mesString + "/" + ano;
        dtinicialorcamento.setText(data);
        dtfinalormaneto.setText(dataformatadafinal);


    }

    public void relatoriofechamento(View view) {
        auxtipoRelatorio = 1;
        relogioRelatorio.setVisibility(INVISIBLE);
        fechamento.setVisibility(INVISIBLE);
        estatisticas.setVisibility(INVISIBLE);
        orcamento.setVisibility(INVISIBLE);
        textRelatorio.setVisibility(INVISIBLE);
        RelatorioOrcamento.setText("FECHAMENTO");
        RelatorioOrcamento.setVisibility(VISIBLE);
        tvfechamentoPor.setVisibility(VISIBLE);
        sptipofechamento.setVisibility(VISIBLE);
        textdtnFinal.setVisibility(VISIBLE);
        textdtnInicial.setVisibility(VISIBLE);
        dtfinalormaneto.setVisibility(VISIBLE);
        dtinicialorcamento.setVisibility(VISIBLE);
        geraorcamento.setVisibility(VISIBLE);
        imprimeorcamento.setVisibility(VISIBLE);
        ArrayAdapter<CharSequence> adapterfechamento = ArrayAdapter.createFromResource(this, R.array.sptipofechamento, android.R.layout.simple_spinner_item);
        adapterfechamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptipofechamento.setAdapter(adapterfechamento);
        sptipofechamento.setOnItemSelectedListener(this);
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        //Log.d(TAG, "onDataSet: yyyy-mm-dd: " + ano + "-"  + mes + "-" + dia);
        int diaaux = dia + 1;
        String diaString = Integer.toString(dia);
        String diaauxString = Integer.toString(diaaux);
        String mesString = Integer.toString(mes + 1);

        if (diaString.length() < 2 && dia != 9) {
            diaString = "0" + dia;
            diaauxString = "0" + diaaux;
        } else {
            if (dia == 9) {
                diaString = "0" + dia;

            }
        }
        if (mesString.length() < 2) mesString = "0" + mesString;
        dataFinalAux = ano + "/" + mesString + "/" + diaauxString;
        //dataInicialAux = ano + "-"  + mes + "-"  + diaString;
        dataInicialAux = ano + "/" + mesString + "/" + diaString;
        // Date data = new Date();
        Date datafinal = new Date();
        //  String dataformatada = formataData.format(data);
        String dataformatadafinal = formataData.format(datafinal);
        dataInicialAux = ano + "-" + mesString + "-" + diaString;
        String data = diaString + "/" + mesString + "/" + ano;
        dtinicialorcamento.setText(data);
        dtfinalormaneto.setText(dataformatadafinal);
    }

    public void gerarelatofecham() {
        DecimalFormat converte = new DecimalFormat("0.00");
        SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        listorcamento.setVisibility(VISIBLE);
        boolean existe;
        String cpf;
        double quantTotalRel = 0;
        double valorTotalRel = 0;
        double valorTotalVendCanc = 0;
        listadeorcamento.clear();
        Cursor cursor = null;
        Cursor cursorQtdVendas = null;
        Cursor cursorTotOrc = null;
        Cursor cursorQtdVendasCanc = null;
        modelRelatorioOrc modelOrcamento1 = new modelRelatorioOrc();
        modelOrcamento1.setTotalquantidade(-10);
        if (dataInicialAux != null && dataFinalAux != null) {
            if (sptipofechamento.getSelectedItemPosition() == 0) {
                modelOrcamento1.setNumero("Data");
                listadeorcamento.add(modelOrcamento1);
                cursor = d5.rawQuery("SELECT  documento.iddocumento,strftime('%d/%m/%Y',documento.dthrcriacao), documento.numeroprodutos,documento.totaldocumento,documento.totaldesconto,documento.totalacrescimo FROM documento  WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'CU' AND documento.status = 'A'", null);
                auxtipoRelatorio = 1;

            }

            if (sptipofechamento.getSelectedItemPosition() == 1) {
                modelOrcamento1.setNumero("Categoria");
                listadeorcamento.add(modelOrcamento1);
                cursor = d5.rawQuery("SELECT  categoria.idcategoria,categoria.descricao, documentoproduto.quantidade,documentoproduto.totalproduto FROM documentoproduto INNER JOIN produto ON produto.idproduto  = documentoproduto.idproduto INNER JOIN categoria  ON categoria.idcategoria = produto.idcategoria LEFT JOIN documento  ON documento.iddocumento = documentoproduto.iddocumento WHERE documentoproduto.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'CU' AND documento.status = 'A'", null);
                auxtipoRelatorio = 2;
            }
            if (sptipofechamento.getSelectedItemPosition() == 2) {
                modelOrcamento1.setNumero("Forma de pagamento");
                listadeorcamento.add(modelOrcamento1);
                cursor = d5.rawQuery("SELECT documentopagamento.idformapagamento, formapagamento.descricao,documentopagamento.iddocumento, documentopagamento.totalpagamento  from documentopagamento inner join formapagamento on formapagamento.idformapagamento = documentopagamento.idformapagamento inner join documento on documentopagamento.iddocumento = documento.iddocumento WHERE documentopagamento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.status  ='A'", null);
                auxtipoRelatorio = 3;
            }

            if (sptipofechamento.getSelectedItemPosition() == 3) {
                auxtipoRelatorio = 4;
                modelOrcamento1.setNumero("Produto");
                listadeorcamento.add(modelOrcamento1);
                cursor = d5.rawQuery("SELECT  documentoproduto.idproduto,produto.descricao, documentoproduto.quantidade,documentoproduto.totalproduto FROM documentoproduto  LEFT JOIN produto  ON produto.idproduto  = documentoproduto.idproduto LEFT JOIN documento  ON documento.iddocumento = documentoproduto.iddocumento WHERE documentoproduto.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'CU' AND documento.status = 'A'", null);
            }
            cursorTotOrc = d5.rawQuery("SELECT SUM (documento.totaldocumento) FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'OR'", null);
            cursorQtdVendas = d5.rawQuery("SELECT  documento.iddocumento FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'CU' AND documento.status = 'A'", null);
            cursorQtdVendasCanc = d5.rawQuery("SELECT  documento.iddocumento,documento.totaldocumento,documento.totaldesconto,documento.totalacrescimo FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'CU' AND documento.status = 'C'", null);
            if (cursorQtdVendasCanc != null) {
                if (cursorQtdVendasCanc.moveToFirst()) {
                    do {
                        valorTotalVendCanc += cursorQtdVendasCanc.getDouble(1)-cursorQtdVendasCanc.getDouble(2)+cursorQtdVendasCanc.getDouble(3);
                    } while (cursorQtdVendasCanc.moveToNext());
                }
            }
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        existe = false;
                        modelRelatorioOrc modelOrcamento = new modelRelatorioOrc();
                        modelOrcamento.setIdproduto(cursor.getInt(0));
                        modelOrcamento.setNumero(cursor.getString(1));
                        //modelOrcamento.setQuantidade(cursor.getDouble(2));
                        if (auxtipoRelatorio == 3 || auxtipoRelatorio == 1) {
                            modelOrcamento.setQuantidade(1);
                        } else {
                            modelOrcamento.setQuantidade(cursor.getInt(2));
                        }
                        if (auxtipoRelatorio == 1) {
                            modelOrcamento.setTotal(cursor.getDouble(3) - cursor.getDouble(4) + cursor.getDouble(5));
                        } else {
                            modelOrcamento.setTotal(cursor.getDouble(3));
                        }
                        valorTotalRel = valorTotalRel + modelOrcamento.getTotal();
                        quantTotalRel = quantTotalRel + modelOrcamento.getQuantidade();

                        for (int i = 1; i <= listadeorcamento.size(); i++) {
                            modelRelatorioOrc modelRelatorioOrc11;
                            modelRelatorioOrc11 = listadeorcamento.get(i - 1);
                            if (auxtipoRelatorio != 1) {
                                if (modelOrcamento.getIdproduto() == modelRelatorioOrc11.getIdproduto()) {
                                    existe = true;
                                    if (auxtipoRelatorio != 3) {


                                        modelRelatorioOrc11.setQuantidade(modelRelatorioOrc11.getQuantidade() + cursor.getInt(2));
                                    }
                                    if (auxtipoRelatorio == 3) {
                                        modelRelatorioOrc11.setQuantidade(modelRelatorioOrc11.getQuantidade() + 1);
                                    }
                                    modelRelatorioOrc11.setTotal(modelRelatorioOrc11.getTotal() + cursor.getDouble(3));
                                }
                            } else {
                                if (modelOrcamento.getNumero().equalsIgnoreCase(modelRelatorioOrc11.getNumero())) {
                                    existe = true;
                                    if (auxtipoRelatorio != 3) {


                                        modelRelatorioOrc11.setQuantidade(modelRelatorioOrc11.getQuantidade() + 1);
                                    }
                                    if (auxtipoRelatorio == 3) {
                                        modelRelatorioOrc11.setQuantidade(modelRelatorioOrc11.getQuantidade() + 1);
                                    }
                                    if (auxtipoRelatorio == 1) {
                                        modelRelatorioOrc11.setTotal(modelRelatorioOrc11.getTotal() + cursor.getDouble(3) - cursor.getDouble(4) + cursor.getDouble(5));
                                    } else {
                                        modelRelatorioOrc11.setTotal(modelRelatorioOrc11.getTotal() + cursor.getDouble(3));
                                    }
                                }
                            }

                        }

                        if (existe == false) {
                            listadeorcamento.add(modelOrcamento);
                        }

                    } while (cursor.moveToNext());

                }
                for (int i = 1; i <= 9; i++) {

                    modelRelatorioOrc m = new modelRelatorioOrc();
                    if (i == 1) {
                        m.setIdproduto(-1);
                        m.setQuantidade(quantTotalRel);
                        m.setTotal(valorTotalRel);
                        listadeorcamento.add(m);
                    }
                    if (i == 2) {
                        m.setNumero("RESUMO");
                        m.setTotalquantidade(-2);
                        listadeorcamento.add(m);
                    }
                    if (i == 3) {
                        m.setNumero("Total bruto das vendas");
                        m.setTotalquantidade(-3);
                        m.setTotal(valorTotalRel);
                        listadeorcamento.add(m);
                    }
                    if (i == 4) {
                        m.setNumero("Total de vendas canceladas");
                        m.setTotalquantidade(-4);
                        cursorQtdVendasCanc.moveToFirst();
                        m.setTotal(valorTotalVendCanc);
                        listadeorcamento.add(m);
                    }
                    if (i == 5) {
                        m.setNumero("Quantidade de vendas canceladas");
                        m.setTotalquantidade(-5);
                        cursorQtdVendasCanc.moveToFirst();
                        m.setTotal(Double.parseDouble(String.valueOf(cursorQtdVendasCanc.getCount())));
                        listadeorcamento.add(m);
                    }
                    if (i == 6) {
                        m.setNumero("Total liquido de vendas");
                        m.setTotalquantidade(-6);
                        m.setTotal(valorTotalRel);
                        listadeorcamento.add(m);
                    }
                    if (i == 7) {//
                        m.setNumero("Quantidade de vendas");
                        m.setTotalquantidade(-7);
                        cursorQtdVendas.moveToFirst();
                        cursorTotOrc.moveToFirst();
                        m.setTotal(Double.parseDouble(String.valueOf(cursorQtdVendas.getCount())));
                        listadeorcamento.add(m);
                    }
                    if (i == 8) {
                        m.setNumero("Ticket médio das vendas");
                        m.setTotalquantidade(-8);
                        if (valorTotalRel > 0 && cursorQtdVendas.getCount() > 0) {
                            m.setTotal(Double.parseDouble(String.valueOf(valorTotalRel / cursorQtdVendas.getCount())));
                        } else
                            m.setTotal(Double.parseDouble(String.valueOf(0.00)));
                        listadeorcamento.add(m);
                    }
                    if (i == 9) {
                        m.setNumero("Total dos orçamentos");
                        m.setTotalquantidade(-9);
                        m.setTotal(cursorTotOrc.getDouble(0));
                        listadeorcamento.add(m);
                    }
                    listorcamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });


                }
            } else {

                Toast.makeText(getApplicationContext(), "Sem relatórios para mostrar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Informe uma data", Toast.LENGTH_SHORT).show();
        }

        AdapterRelatorioOrc relatorioOrc = new AdapterRelatorioOrc(this, listadeorcamento);
        listorcamento.setAdapter(relatorioOrc);


    }

    public void gerarRelatorio(View v) {
        conectImpControlid();
        if (auxtipoRelatorio == 0)
            gerarelatorioorc();
        if (auxtipoRelatorio != 0)
            gerarelatofecham();
    }

    public void gerarelatorioorc() {

        DecimalFormat converte = new DecimalFormat("0.00");
        modelRelatorioOrc modelOrcamento1 = new modelRelatorioOrc();
        listorcamento.setVisibility(VISIBLE);
        String numero;
        String cliente;
        int quantidade;
        Double valor;
        String cpf;
        quantTotalRelOrc = 0;
        valorTotalRelOrc = 0;
        listadeorcamento.clear();
        modelOrcamento1.setNumero("NUMERO");
        modelOrcamento1.setCliente("ClIENTE");
        modelOrcamento1.setTotalquantidade(-11);
        listadeorcamento.add(modelOrcamento1);


        if (dataInicialAux != null && dataFinalAux != null) {
            final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();

            Cursor cursor = d5.rawQuery("SELECT documento.iddocumento, documento.nomeorcamento, documento.totalquantidade, documento.totaldocumento,documento.cpfcnpj,documento.totaldesconto,documento.totalacrescimo FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' AND documento.operacao = 'OR'", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //uma nova instância por cada registo
                        modelRelatorioOrc modelOrcamento = new modelRelatorioOrc();
                        numero = cursor.getString(0);
                        cliente = cursor.getString(1);
                        //quantidade = cursor.getDouble(2);
                        valor = cursor.getDouble(3) - cursor.getDouble(5) + cursor.getDouble(6);
                        cpf = cursor.getString(4);
                        modelOrcamento.setNumero(numero);
                        modelOrcamento.setCliente(cliente);
                        modelOrcamento.setQuantidade(cursor.getDouble(2));
                        modelOrcamento.setCpfcnpj(cpf);
                        // modelOrcamento1.setQuantTotalRel(modelOrcamento1.getQuantTotalRel() + quantidade);
                        modelOrcamento.setTotal(valor);
                        // modelOrcamento1.setValorTotalRel(modelOrcamento1.getValorTotalRel() + valor);
                        //Adiciona ao array
                        listadeorcamento.add(modelOrcamento);
                        quantTotalRelOrc = quantTotalRelOrc + modelOrcamento.getQuantidade();
                        valorTotalRelOrc = valorTotalRelOrc + valor;

                    } while (cursor.moveToNext());
                    modelRelatorioOrc objRelOrc = new modelRelatorioOrc();
                    objRelOrc.setIdproduto(-1);
                    objRelOrc.setQuantidade(quantTotalRelOrc);
                    objRelOrc.setTotalquantidade(quantTotalRelOrc);
                    objRelOrc.setTotal(valorTotalRelOrc);
                    listadeorcamento.add(objRelOrc);

                }

                listorcamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        modelRelatorioOrc c = (modelRelatorioOrc) parent.getItemAtPosition(position);

                        if ((InfoOrcamento.instance == null) && (c.getTotalquantidade() != -11.0) && c.getCliente() != "a") {
                            Intent intent = new Intent(getApplicationContext(), InfoOrcamento.class);
                            intent.putExtra("Dados", c);
                            startActivity(intent);
                        }
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "Informe uma data", Toast.LENGTH_SHORT).show();
        }

        AdapterRelatorioOrc relatorioOrc = new AdapterRelatorioOrc(this, listadeorcamento);
        listorcamento.setAdapter(relatorioOrc);

    }

    public void novacategoria(View view) {


        if (CriarCateg.instance == null) {
            Intent intent = new Intent(this, CriarCateg.class);
            startActivityForResult(intent,7);
            CriarCateg criar = new CriarCateg();
        }
    }

    public void SelcVender(View view) {
        impSweda();
        onStart();
       // conectImpControlid();
        esconderTeclado(view);
        //   getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//sat.ConsultarSAT(123456);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        barracategoria.setText(null);
        barraproduto.setText(null);
        consultarCateg();
        VENDER.setBackgroundResource(selecionado);
        CADASTRO.setBackgroundResource(semfoco);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        CONFIGURACAO.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(semfoco);
        SAIR.setBackgroundResource(semfoco);
        layoutVendas.setVisibility(VISIBLE);
        layoutCupom.setVisibility(VISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutContador.setVisibility(INVISIBLE);
        layoutSuporte.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutConfigura.setVisibility(INVISIBLE);
        layoutCadastrarprod.setVisibility(INVISIBLE);
        layoutCadastrarCat.setVisibility(INVISIBLE);

    }

    public void SelcCadastrarProd(View view) {
        esconderTeclado(view);
        itensorcamento.clear();
        barracategoria.setText(null);
        barraproduto.setText(null);
        lprod();
        VENDER.setBackgroundResource(semfoco);
        CADASTRO.setBackgroundResource(selecionado);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        CONFIGURACAO.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(semfoco);
        SAIR.setBackgroundResource(semfoco);
        layoutVendas.setVisibility(INVISIBLE);
        layoutContador.setVisibility(INVISIBLE);
        layoutSuporte.setVisibility(INVISIBLE);
        layoutCupom.setVisibility(INVISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutConfigura.setVisibility(INVISIBLE);
        layoutCadastrarprod.setVisibility(VISIBLE);
        layoutCadastrarCat.setVisibility(INVISIBLE);
    }

    private void SelcCadastrarCat(View view) {
        itensorcamento.clear();
        barracategoria.setText(null);
        barraproduto.setText(null);
        VENDER.setClickable(true);
        VENDER.setBackgroundResource(semfoco);
        CADASTRO.setBackgroundResource(selecionado);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        CONFIGURACAO.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(semfoco);
        SAIR.setBackgroundResource(semfoco);
        layoutVendas.setVisibility(INVISIBLE);
        layoutContador.setVisibility(INVISIBLE);
        layoutSuporte.setVisibility(INVISIBLE);
        layoutCupom.setVisibility(INVISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutConfigura.setVisibility(INVISIBLE);
        layoutCadastrarprod.setVisibility(INVISIBLE);
        layoutCadastrarCat.setVisibility(VISIBLE);
    }

    public void SelcRelatorio(View view) {
        //modificar();
        esconderTeclado(view);
        itensorcamento.clear();
        barracategoria.setText(null);
        barraproduto.setText(null);
        relogioRelatorio.setVisibility(VISIBLE);
        tvfechamentoPor.setVisibility(INVISIBLE);
        sptipofechamento.setVisibility(INVISIBLE);
        VENDER.setClickable(true);
        VENDER.setBackgroundResource(semfoco);
        CADASTRO.setBackgroundResource(semfoco);
        RELATORIO.setBackgroundResource(selecionado);
        CONFIGURACAO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(semfoco);
        SAIR.setBackgroundResource(semfoco);
        layoutVendas.setVisibility(INVISIBLE);
        layoutContador.setVisibility(INVISIBLE);
        layoutSuporte.setVisibility(INVISIBLE);
        layoutCupom.setVisibility(INVISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(VISIBLE);
        layoutConfigura.setVisibility(INVISIBLE);
        layoutCadastrarprod.setVisibility(INVISIBLE);
        layoutCadastrarCat.setVisibility(INVISIBLE);
        fechamento.setVisibility(VISIBLE);
        estatisticas.setVisibility(VISIBLE);
        orcamento.setVisibility(VISIBLE);
        textRelatorio.setVisibility(VISIBLE);
        RelatorioOrcamento.setVisibility(INVISIBLE);
        textdtnFinal.setVisibility(INVISIBLE);
        textdtnInicial.setVisibility(INVISIBLE);
        dtfinalormaneto.setVisibility(INVISIBLE);
        dtinicialorcamento.setVisibility(INVISIBLE);
        geraorcamento.setVisibility(INVISIBLE);
        imprimeorcamento.setVisibility(INVISIBLE);
        listorcamento.setVisibility(INVISIBLE);


    }

    public void emailContador(View view) {
        VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
        esconderTeclado(view);

        if (verificarInternetStatus.executeCommand() == true) {
            final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
            //String rawQuery = "SELECT documento.iddocumento, documento.nomeorcamento, documento.totalquantidade, documento.totaldocumento,documento.cpfcnpj FROM documento WHERE documento.dthrcriacao between '"  + DataInicial + "' and '" + DataInicial+'";
            // and documento.operacao = 'OR'";

            Cursor cursor = d5.rawQuery("SELECT documento.chave, documento.xml FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "'", null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        try {
                            String conteudoArq = cursor.getString(1);
                            String nomeArq = cursor.getString(0);
                            FileOutputStream fos = new FileOutputStream(diretorioXmlTemp + "/Cfe" + nomeArq + ".xml");

                            fos.write(conteudoArq.getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } while (cursor.moveToNext());
                }
            }
            Cursor cancelados = d5.rawQuery("SELECT documento.chavecanc, documento.xmlcanc FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' and documento.status ='C'", null);
            if (cancelados != null) {
                if (cancelados.moveToFirst()) {
                    do {
                        try {
                            String conteudoArq = cancelados.getString(1);
                            String nomeArq = cancelados.getString(0);
                            FileOutputStream fos = new FileOutputStream(diretorioXmlTemp + "/Cfe" + nomeArq + ".xml");

                            fos.write(conteudoArq.getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } while (cancelados.moveToNext());
                }
            }
            if (cursor.getCount() > 0) {
                try {
                    compactararq compactarArq = new compactararq();
                    // compactarArq.zipDirectory(new File("/data/data/com.example.ademirestudo/databases"),Environment.getExternalStorageDirectory() + "/banco/satflexx.zip");
                    compactarArq.zipDirectory(new File(String.valueOf(diretorioXmlTemp)), diretorioXmlContador + "/xml.zip");

                    DeletaPasta(diretorioXmlTemp);
                    if (!diretorioXmlTemp.exists()) {
                        diretorioXmlTemp.mkdir();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String diainicial = (dataInicialAux.substring(8, 10));
                String mesinicial = (dataInicialAux.substring(4, 8));
                String anoinicial = (dataInicialAux.substring(0, 4));
                int diafinalAux = Integer.parseInt(dataFinalAux.substring(8, 10));
                String diafinal = (String.valueOf(diafinalAux - 1));
                String mesfinal = (dataFinalAux.substring(4, 8));
                String anofinal = (dataFinalAux.substring(0, 4));
                if (diafinalAux < 10) {
                    diafinal = "0" + diafinal;
                }
                enviaemail = new EnviarEmail(this, objparam.getContadorEmail(), "XMLs: " + objparam.getEmitenteRazaoSocial(), "Seguem os XMLs do período de: " +
                        diainicial + mesinicial + anoinicial + " até " + diafinal + mesfinal + anofinal + " anexados ao e-mail.");

                enviaemail.execute();
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("");
                builder.setMessage("Nenhum documento fiscal foi encontrado no perìodo\n especificado.");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.BLUE);
                nbutton.setTextSize(20);
                nbutton.setScaleY(1);
                nbutton.setScaleX(1);
                nbutton.setX(-60);
                nbutton.setTextColor(Color.WHITE);

            }
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Falha ao enviar e-mail");
            builder.setMessage("Verifique sua conexão com a internet.");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.cancel();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
            Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setBackgroundColor(Color.BLUE);
            nbutton.setTextSize(20);
            nbutton.setScaleY(1);
            nbutton.setScaleX(1);
            nbutton.setX(-60);
            nbutton.setTextColor(Color.WHITE);
        }
    }

    public void addNcm(String ncm){
        try {
            dadosOpenHelper.inserirNcm(ncm);
        }
        catch (Exception e){

        }
    }
public void inserNcm(View view){
        incertNcm = new IncertNcm(this);
   // addNcm("a+");
    incertNcm.execute();

}
    public void DeletaPasta(File file) {
        if (file.isDirectory()) {
            for (File fila : file.listFiles()) {
                DeletaPasta(fila);
            }
        }
        file.delete();
    }

    public void SelcContador(View view) {
        esconderTeclado(view);
        itensorcamento.clear();
        barracategoria.setText(null);
        barraproduto.setText(null);
        TextView tvemailcontador = (TextView) findViewById(R.id.tvemailcontador);
        tvemailcontador.setText(objparam.getContadorEmail());
        VENDER.setClickable(true);
        VENDER.setBackgroundResource(semfoco);
        CADASTRO.setBackgroundResource(semfoco);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(selecionado);
        CONFIGURACAO.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(semfoco);
        SAIR.setBackgroundResource(semfoco);
        layoutVendas.setVisibility(INVISIBLE);
        layoutContador.setVisibility(VISIBLE);
        layoutSuporte.setVisibility(INVISIBLE);
        layoutCupom.setVisibility(INVISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutConfigura.setVisibility(INVISIBLE);
        layoutCadastrarprod.setVisibility(INVISIBLE);
        layoutCadastrarCat.setVisibility(INVISIBLE);
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        final int ano = calendar.get(Calendar.YEAR);
        final int mes = calendar.get(Calendar.MONTH) + 1;
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int diaaux = dia + 1;
        String diaString = Integer.toString(dia);
        String diaauxString = Integer.toString(diaaux);
        String mesString = Integer.toString(mes);

        if (diaString.length() < 2 && dia != 9) {
            diaString = "0" + dia;
            diaauxString = "0" + diaaux;
        } else {
            if (dia == 9) {
                diaString = "0" + dia;

            }
        }
        if (mesString.length() < 2) mesString = "0" + mes;
        dataFinalAux = ano + "-" + mesString + "-" + diaauxString;
        dataInicialAux = ano + "-" + mesString + "-" + diaString;
        Date data = new Date();
        Date datafinal = new Date();
        String dataformatada = formataData.format(data);
        String dataformatadafinal = formataData.format(datafinal);
        dtinicialcontador.setText(dataformatada);
        dtfinalcontador.setText(dataformatadafinal);
    }

    public void SelcConfigurar(View view) {

        layoutConfiguraparam.setVisibility(INVISIBLE);
        layoutStatusSistema.setVisibility(INVISIBLE);
        btnParametros.setVisibility(INVISIBLE);
        btnStatusSistema.setVisibility(INVISIBLE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informe a Senha de Acesso");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setMaxLines(1);
        // input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(new PasswordTransformationMethod());
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               // senhaConfiguracao = input.getText().toString();
                senhaConfiguracao = "automacao";
                if (senhaConfiguracao.equalsIgnoreCase(objparam.getSenhaConfiguracao())) {
                    ConstraintLayout layoutConsultarStatusOper;
                    layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
                    layoutConsultarStatusOper.setVisibility(INVISIBLE);
                    itensorcamento.clear();
                    barracategoria.setText(null);
                    barraproduto.setText(null);
                    Button btncontador = (Button) findViewById(R.id.btncontador);
                    VENDER.setClickable(true);
                    VENDER.setBackgroundResource(semfoco);
                    CADASTRO.setBackgroundResource(semfoco);
                    RELATORIO.setBackgroundResource(semfoco);
                    CONTADOR.setBackgroundResource(semfoco);
                    CONFIGURACAO.setBackgroundResource(selecionado);
                    SUPORTE.setBackgroundResource(semfoco);
                    SAIR.setBackgroundResource(semfoco);
                    layoutStatusSistema.setVisibility(INVISIBLE);
                    layoutVendas.setVisibility(INVISIBLE);
                    layoutContador.setVisibility(INVISIBLE);
                    layoutSuporte.setVisibility(INVISIBLE);
                    layoutCupom.setVisibility(INVISIBLE);
                    layoutOrcamento.setVisibility(INVISIBLE);
                    layoutRelatorio.setVisibility(INVISIBLE);
                    layoutConfigura.setVisibility(VISIBLE);
                    layoutCadastrarprod.setVisibility(INVISIBLE);
                    layoutCadastrarCat.setVisibility(INVISIBLE);
                    layoutConfiguraparam.setVisibility(INVISIBLE);
                    btnParametros.setVisibility(VISIBLE);
                    btnStatusSistema.setVisibility(VISIBLE);
                } else {
                    layoutInflater = getLayoutInflater();
                    toastLayout = layoutInflater.inflate(layout.toastsenhainvalida, (ViewGroup) findViewById(R.id.toastsenhainvalida));
                    Toast toast = Toast.makeText(getApplicationContext(), "  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();
                }

            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alerta = builder.create();
        //Exibe

        alerta.show();
        Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.RED);
        nbutton.setTextSize(20);
        nbutton.setScaleY(1);
        nbutton.setScaleX(1);
        nbutton.setX(60);
        nbutton.setTextColor(Color.WHITE);

        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.GREEN);
        pbutton.setTextSize(20);
        pbutton.setScaleY(1);
        pbutton.setScaleX(1);
        pbutton.setX(-180);
        pbutton.setTextColor(Color.WHITE);
    }

    public void ConfigMenuAnterior(View view) {

        layoutConfiguraparam.setVisibility(INVISIBLE);
        layoutStatusSistema.setVisibility(INVISIBLE);
        btnParametros.setVisibility(INVISIBLE);
        btnStatusSistema.setVisibility(INVISIBLE);
        ConstraintLayout layoutConsultarStatusOper;
        layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
        layoutConsultarStatusOper.setVisibility(INVISIBLE);
        itensorcamento.clear();
        barracategoria.setText(null);
        barraproduto.setText(null);
        Button btncontador = (Button) findViewById(R.id.btncontador);
        VENDER.setClickable(true);
        VENDER.setBackgroundResource(semfoco);
        CADASTRO.setBackgroundResource(semfoco);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        CONFIGURACAO.setBackgroundResource(selecionado);
        SUPORTE.setBackgroundResource(semfoco);
        SAIR.setBackgroundResource(semfoco);
        layoutStatusSistema.setVisibility(INVISIBLE);
        layoutVendas.setVisibility(INVISIBLE);
        layoutContador.setVisibility(INVISIBLE);
        layoutSuporte.setVisibility(INVISIBLE);
        layoutCupom.setVisibility(INVISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutConfigura.setVisibility(VISIBLE);
        layoutCadastrarprod.setVisibility(INVISIBLE);
        layoutCadastrarCat.setVisibility(INVISIBLE);
        layoutConfiguraparam.setVisibility(INVISIBLE);
        btnParametros.setVisibility(VISIBLE);
        btnStatusSistema.setVisibility(VISIBLE);
    }


    public void SelcSuporte(View view) {


        esconderTeclado(view);
        itensorcamento.clear();
        barracategoria.setText(null);
        barraproduto.setText(null);
        TextView tvinformativo = (TextView) findViewById(R.id.tvinformativo);
        TextView tvemail = (TextView) findViewById(R.id.tvemail);
        TextView tvendereco = (TextView) findViewById(R.id.tvendereco);
        TextView tvtelefone = (TextView) findViewById(R.id.tvtelefone);
        VENDER.setClickable(true);
        VENDER.setBackgroundResource(semfoco);
        CADASTRO.setBackgroundResource(semfoco);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        CONFIGURACAO.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(selecionado);
        SAIR.setBackgroundResource(semfoco);
        layoutVendas.setVisibility(INVISIBLE);
        layoutContador.setVisibility(INVISIBLE);
        layoutSuporte.setVisibility(VISIBLE);
        layoutCupom.setVisibility(INVISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        layoutRelatorio.setVisibility(INVISIBLE);
        layoutConfigura.setVisibility(INVISIBLE);
        layoutCadastrarprod.setVisibility(INVISIBLE);
        layoutCadastrarCat.setVisibility(INVISIBLE);
        Cursor revenda = (dadosOpenHelper.selecValorSuporte());

        revenda.moveToPosition(0);
        tvinformativo.setText(String.valueOf(revenda.getString(0)));
        revenda.moveToPosition(2);
        tvendereco.setText(String.valueOf(revenda.getString(0)));
        revenda.moveToPosition(4);
        tvemail.setText(String.valueOf(revenda.getString(0)));
        revenda.moveToPosition(5);
        tvtelefone.setText(String.valueOf(revenda.getString(0)));

    }

    public void sair(View view) {
        // final char[] CORTAR = new char[] { 0x1B, 'm' };
        // VENDER.setText(CORTAR.length);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Sair do SAT-FLEX");


        //define a mensagem
        builder.setMessage("Você tem certeza que deseja encerrar o SAt-Flex agora");
        SAIR.setBackgroundResource(selecionado);
        //define um botão como positivo
        final AlertDialog.Builder sim = builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @SuppressLint("MissingPermission")
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(DialogInterface arg0, int arg1) {
                Runtime runtime = Runtime.getRuntime();
                try {

                    Process process =  runtime.exec(new String[]{"/system/bin/su", "-c","/system/bin/ifconfig"});
                    VENDER.setText("content.toStringf()");
                  //  Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "poweroff"});
                    process.waitFor();
                    VENDER.setText("content.toString()");
                    InputStream inputStream = process.getInputStream();
                    byte[] content = new byte[inputStream.available()];
                    inputStream.read(content);
                    System.out.println(new String(content));

                    String teste = new String(content);
                    int retorno = teste.indexOf("eth1");

                    VENDER.setText("");
                    FileOutputStream savedadosVenda = new FileOutputStream(diretorioTemp + "/" + "ipp" + ".xml");
                    savedadosVenda.write(teste.getBytes());
                    savedadosVenda.close();
                 //  VENDER.setText(content.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                SAIR.setBackgroundResource(semfoco);


                arg0.cancel();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
        Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.RED);
        nbutton.setTextSize(20);
        nbutton.setScaleY(1);
        nbutton.setScaleX(1);
        nbutton.setX(60);
        nbutton.setTextColor(Color.WHITE);

        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.GREEN);
        pbutton.setTextSize(20);
        pbutton.setScaleY(1);
        pbutton.setScaleX(1);
        pbutton.setX(-140);

        pbutton.setTextColor(Color.WHITE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void extrairLogs(View view) {
        ConstraintLayout layoutConsultarStatusOper;
        layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
        layoutConsultarStatusOper.setVisibility(INVISIBLE);
        Random random = new Random();
        int number = 0;
        String numeroSessao = "123456";
        number = random.nextInt(999999);
        numeroSessao = (String.format("%06d", number));
        if (SATiD.getInstance().isConnected()) {
            String consultarsttusat = SATiD.getInstance().ExtrairLogs(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao());
            String[] separa = consultarsttusat.split("\\|");
            String logsSat = converteBase64.base64(separa[5]);
            statusSat = new StringBuilder();
            statusSat.append(logsSat);
            try {

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Extrair Logs");
                builder.setMessage("Extração do LOG do equipamento Sat concluida com sucesso!\n -Arquivo criado em:\n" + diretorioTemp + "/" + "LogSat" + ".txt");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.BLUE);
                nbutton.setTextSize(20);
                nbutton.setScaleY(1);
                nbutton.setScaleX(1);
                nbutton.setX(-60);
                nbutton.setTextColor(Color.WHITE);
                FileOutputStream fos = new FileOutputStream(diretorioTemp + "/" + "LogSat" + ".txt");
                fos.write(logsSat.getBytes());
                fos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void pausa(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
           // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

@RequiresApi(api = Build.VERSION_CODES.O)
public void consultarStatusOperacional(View view){
 //   consultarStatusOperacionall();
    //conectImpControlid();
    String consultarsttusat ="";
    int cooIni=0;
    int cooFim=0;
    int cooPendente=0;
    statusSat();
    pausa();
    Random random = new Random();
    int number = 0;
    String numeroSessao = "123456";
    number = random.nextInt(999999);
    numeroSessao = (String.format("%06d", number));
    statusSat = new StringBuilder();
    ConstraintLayout layoutConsultarStatusOper;
    layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
    VerificarInternetStatus v = new VerificarInternetStatus();
    TextView tvConsultarStatusOperac = findViewById(R.id.tvconsultarstatusoperac);
    v.isOnline();
    layoutConsultarStatusOper.setVisibility(VISIBLE);




    if (statuSat == 1) {
        try {
            if (objparam.getSatModelo().equalsIgnoreCase("sweda")) {
                pausa();
                pausa();

                consultarsttusat = getSAT().consultarStatusOperacional(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao());
            }
            if (objparam.getSatModelo().equalsIgnoreCase("controlid") && SATiD.getInstance().isConnected()) {
                consultarsttusat = SATiD.getInstance().ConsultarStatusOperacional(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao());
            }
            String[] separa = consultarsttusat.split("\\|");
            cooIni= Integer.parseInt(separa[21].substring(31,37));
            cooFim= Integer.parseInt(separa[22].substring(31,37));

            if (cooFim != 0){
                cooPendente = cooFim - cooIni + 1;

            }
            else {
                cooPendente =0;
            }


            statusSat.append("          CONSULTAR STATUS OPERACIONAL"+"\n\n");
            statusSat.append(separa[2]);
            statusSat.append("\nNumero de Série: "+separa[5]+"\nTipo da LAN: "+separa[6]+"\nIP: "+separa[7]+"\nMac Adreass: "+separa[8]);
            statusSat.append("\nMascara de Rede: "+separa[9]+"\nGateway: "+separa[10]+"\nDNS1: "+separa[11]+"\nDNS2: "+separa[12]+"\nStatus Rede: "+separa[13]);
            statusSat.append("\nNivel da Bateria: "+separa[14]+"\nMemória Total: "+separa[15]+"\nMemória Usada: "+separa[16]+"\nData e Hora Atual: "+separa[17].

                    substring(6,8) +"/"+separa[17].

                    substring(4,6) +"/"+separa[17].

                    substring(0,4) +" "+separa[17].

                    substring(8,10) +":"+separa[17].

                    substring(10,12) +":"+separa[17].

                    substring(12,14) +"\nVersão Software Básico: "+separa[18]);
            statusSat.append("\nVersão Layout: "+separa[19]+"\nUltimo CFE Emitido: "+separa[20].

                    substring(0,28) +"\n"+separa[20].

                    substring(28,44) +"\nCFE Inicial: "+separa[21].

                    substring(0,28) +"\n"+separa[21].

                    substring(28,44) +"\nCfe Final: "+separa[22].

                    substring(0,28) +"\n"+separa[22].

                    substring(28,44));
            statusSat.append("\nCupons Pendentes: "+ cooPendente);
            statusSat.append("\nUltima Comunicação Sefaz: "+separa[23].

                    substring(6,8) +"/"+separa[23].

                    substring(4,6) +"/"+separa[23].

                    substring(0,4) +" "+separa[23].

                    substring(8,10) +":"+separa[23].

                    substring(10,12) +":"+separa[23].

                    substring(12,14) +"\n");
            tvConsultarStatusOperac.setText(statusSat);
            sweda = new SAT(getApplicationContext());
            sweda.desativarEthSat();

        }
        catch (Exception e){
            statusSat.append("Não foi possivel localizar o sat");
            tvConsultarStatusOperac.clearComposingText();
            tvConsultarStatusOperac.setText(statusSat);
            sweda = new SAT(getApplicationContext());
            sweda.desativarEthSat();
        }}
}
    public void consultarStatusOperacionall() {
        final ProgressBar Progressbar =(ProgressBar) findViewById(R.id.progressBar); // initiate the progress bar
        i = 0;
        new Thread(new Runnable() {
            public void run() {
                while (i < 100) {
                    i += 1;
                    // Update the progress bar and display the current value in text view
                    Progressbar.post(new Runnable() {
                        public void run() {
                            Progressbar.setProgress(i);
                           // txtView.setText(i+"/"+pgsBar.getMax());
                        }
                    });
                    try {

                        // Sleep for 100 milliseconds to show the progress slowly.

                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();



    }

    public void FecharAplicacao (View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Fechar Aplicação / Reiniciar equipamento");


        //define a mensagem
        builder.setMessage("Escolha uma das opções abaixo: ");
        SAIR.setBackgroundResource(selecionado);
        //define um botão como positivo
        final AlertDialog.Builder sim = builder.setPositiveButton("Reiniciar Equipamento", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                try {
                   // Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c","reboot now"});

                    Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c","pm install -r sdcard/satflex/atualizacao/flex-rem"+"a"+"rca.apk"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Fechar Aplicação", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                SAIR.setBackgroundResource(semfoco);

                finish();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe

        alerta.show();
        Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.RED);
        nbutton.setTextSize(14);
        nbutton.setScaleY(1);
        nbutton.setScaleX(1);
        nbutton.setX(100);
        nbutton.setTextColor(Color.WHITE);

        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.GREEN);
        pbutton.setTextSize(14);
        pbutton.setScaleY(1);
        pbutton.setScaleX(1);
        pbutton.setX(-440);

        pbutton.setTextColor(Color.WHITE);
    }
    public void imprimirStatusSat(View view) {

        if (PrintID.getInstance().isConnected()==true) {

            PrintID.getInstance().ImprimirFormatado(statusSat.toString(), false, true, false, false);

            PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
        }

        // VENDER.setText(separa[5]);
//            objCupomFiscal.setSatSerie(separa[5]);

        else {

            Toast.makeText(getApplicationContext(), "Impressora Desconectada", Toast.LENGTH_LONG).show();
        }
    }
    public void imprimirRelOrc(View view ) {
       // conectImpControlid();

        CupomRelatorios  CupomRelatotio = new CupomRelatorios();
        if (auxtipoRelatorio==0){
        if (PrintID.getInstance().isConnected()) {
            CupomRelatotio.cupomOrcamento();
            PrintID.getInstance().ImprimirFormatado(CupomRelatotio.cupomRelatorios.toString(),false,false,false,false);

         PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
        }
        else
        {
            // Toast.makeText(getApplicationContext(), "sat_id desconectado", Toast.LENGTH_LONG).show();
        }}
        if (auxtipoRelatorio!=0){
            if (PrintID.getInstance().isConnected()) {
                CupomRelatotio.cupomFechamento();
                PrintID.getInstance().ImprimirFormatado(CupomRelatotio.cupomRelatorios.toString(),false,false,false,false);

                PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
            }
            else
            {
                // Toast.makeText(getApplicationContext(), "sat_id desconectado", Toast.LENGTH_LONG).show();
            }}
    }

    public void excluirOrcamento(View view){
    }

    public void esconderTeclado(View view){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);

    }
    @Override

    public void onAttachedToWindow() {
       // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);


        //    super.onAttachedToWindow();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.i("Home Button","Clicked");
        }
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }
    @Override
    public void onInit(int i) {


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}





















