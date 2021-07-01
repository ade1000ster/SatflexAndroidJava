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
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.discovery.DeviceInfo;
import com.epson.epos2.discovery.DiscoveryListener;
import com.epson.epos2.discovery.FilterOption;
import com.epson.epos2.printer.Printer;
import com.example.ademirestudo.R.menu;
import com.example.ademirestudo.database.DadosOpenHelper;
import com.example.ademirestudo.thread.EnviarEmailThread;
import com.example.ademirestudo.thread.ExtrairLogsThread;
import com.example.ademirestudo.thread.InsertNcmThread;
import com.facebook.stetho.Stetho;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
import br.com.dimep.dimepsat.DimepDSAT;
import br.com.dimep.dimepsat.driver.USBService;
import model.modelCategorias;
import model.modelCupomFiscal;
import model.modelDocumento;
import model.modelDocumentoProduto;
import model.modelParametros;
import model.modelProdutos;
import model.modelRelatorioOrc;
import util.TecladoContribSocial;
import util.TecladoQuantInteiro;
import util.Tecladocpfcnpj;
import util.Tecladonumerico;
import util.UtilidadesGerais;

import static android.widget.Button.INVISIBLE;
import static android.widget.Button.VISIBLE;
import static com.example.ademirestudo.R.color.corparametroselect;
import static com.example.ademirestudo.R.color.corparametrosemfoco;
import static com.example.ademirestudo.R.color.finalizarorc;
import static com.example.ademirestudo.R.color.selecionado;
import static com.example.ademirestudo.R.color.semfoco;
import static com.example.ademirestudo.R.id;
import static com.example.ademirestudo.R.layout;

public class MainActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener, View.OnKeyListener {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Animation animSequential;
    public static Printer mPrinter = null;
    private AlertDialog alertDialog;
    public static int extrairLogsSatResp;
    StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference();
    private static DatabaseReference firebaseDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference statusFirebaseDatabase;
    private static DatabaseReference alertaFirebaseDatabase;
    private static DatabaseReference alertaMsgFirebaseDatabase;
    public static int controleteclado = 0;
    public static ConstraintLayout layoutVendas;
    private ConstraintLayout layoutSuporte;
    public static ConstraintLayout layoutCupom;
    private ConstraintLayout layoutContador;
    private TextView tvemailcontador;
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
    private Animation animation;
    private static TextView tvSefazAlerta;
    private Button btnSatAlerta;
    private Button btnImpAlerta;
    public static Button binformarCPF;
    public static Button bFinalizaVenda;
    public static Button bFinalizaOrc;
    public static int auxtipoRelatorio; //0 relatorio orcamento e 1,2,3,4 para relatorio fechamento
    private Spinner sptipofechamento;
    private TextView tvfechamentoPor;
    private Button fechamento;
    private Button orcamento;
    private Button imprimeorcamento;
    private Button geraorcamento;
    private TextView textRelatorio;
    private ListView listorcamento;
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
    private DatePickerDialog.OnDateSetListener dateSetListenerContIni;
    private DatePickerDialog.OnDateSetListener dateSetListenerContFim;
    private DatePickerDialog.OnDateSetListener dateSetListenerRelIni;
    private DatePickerDialog.OnDateSetListener dateSetListenerRelFim;
    public static GridView containerGridViewprod;
    public static GridView containerGridViewCateg;
    public static ArrayList<modelProdutos> listadeItensVendaProd;
    private static MainAdapterProd itensVendaProd;
    private EditText tvInfo;
    public static TextView tvInfoDescr;
    public static TextView tvDescricao;
    public static Button btnGravarAlt;
    private Button btnContador;
    private Button btnDesenvolvedora;
    private Button btnDiversos;
    private Button btnEmitente;
    private Button btnImpressora;
    private Button btnRevenda;
    private Button btnSat;
    private Button btnParametros;
    private Button btnStatusSistema;
    private TextView tvTituloConfigurar;
    View toastLayout;
    public static ArrayList<modelParametros> listadeItensParam = new ArrayList<>();
    public static GridView containerGridViewParam;
    LayoutInflater layoutInflater;
    private int controle = 0;
    public static String dataInicialAux;
    public static String dataFinalAux;
    private static File diretorioBanco;
    private static File diretorioBancoConvertido;
    private static File diretorioXmlContador;
    private static File diretorioXmlTemp;
    public static File diretorioTemp;
    public static File diretorioImg;
    private static File diretorioAtualiza;
   // String myData = "";
    public static String[] retornosat;
    public static String erroconexãosat;
    public static String qrCode = "";
    private static String qrCodeCanc = "";
    private static String chave = "";
    private static int coo = 000000;
    public EnviarEmailThread enviaemail;
    public InsertNcmThread incertNcm;
    public ExtrairLogsThread extrairlogs;
    FinalizarVendas finalizarVendas = null;
    AlteraProd alterarProd = null;
    private static SATsweda sweda;
    private static ImpSweda impr;
    private Toolbar menuCupomOrc;
    private int i = 0;
    private FilterOption mFilterOption = null;
    public static int statuSat = 0; //1 sat conectado 0 não conectado
    public static int statuImp = 0;
    private String versaoAtual;
    private Button btnSuporteRemoto;
    public static int nViasImpVenda;
    public static int nViasImpCanc;
    public static int nViasImpOrc;
    public static boolean impSweda300 =false;
    private DimepDSAT dimepDSAT;
    public static TextView tvConsultarStatusOperac;
    public static ConstraintLayout layoutConsultarStatusOper;
    //private CustomTask customTask;
    private TimerTask timer;
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
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
        initView();
        containerGridViewCateg = findViewById(id.GridCategorias);
        containerGridViewprod = findViewById(id.GridProdutos);
        listadeItensVendaProd = new ArrayList<>();
        itensVendaProd = new MainAdapterProd(this, listadeItensVendaProd);
        consultarCateg();
        objdocumento = new modelDocumento();
        listadeItens = new ArrayList<>();
        itensAdapter = new Adapter(this, listadeItens);
        itenscateg = new AdapterCateg(this, listadecateg);
        listadeorcamento = new ArrayList<>();
        itensorcamento = new AdapterRelatorioOrc(this, listadeorcamento);
        Toolbar menuCupomVendas = findViewById(id.toolbar);
        setSupportActionBar(menuCupomVendas);
        menuCupomVendas.inflateMenu(menu.menu_venda);
        menuCupomOrc = findViewById(id.toolbarOrc);
        setSupportActionBar(menuCupomOrc);
        menuCupomOrc.inflateMenu(menu.menu_orcamento);
        menuCupomOrc.setNavigationOnClickListener(this);
        ListView cupomVendas = findViewById(id.textViewcupom);
        ListView cupomOrcamento = findViewById(id.tvOrcamento);
        cupomVendas.setAdapter(itensAdapter);
        cupomOrcamento.setAdapter(itensAdapter);
        listorcamento.setAdapter(itensorcamento);
        firebaseStorageRef = FirebaseStorage.getInstance().getReference();
        bloqueioFirebase();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            bloqueioFaltaComunic();
        }
        verificarBloqueio();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           // getSAT().identificarEth();
             //onPause();
            if(objparam.getSatModelo().equalsIgnoreCase("10")) {
                dadosOpenHelper.addSuporte(2);
                objparam.setSatModelo("2");
            }
            if(objparam.getSatModelo().equalsIgnoreCase("2")) {
              //  getSAT().ativarEthSat();

            }
        }

        versaoApp();
        mFilterOption = new FilterOption();
        if (objparam.getImpressoraModelo().equalsIgnoreCase("4")){
            try {
            }
            catch (Exception e) {
            }
        }
        menuCupomOrc.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Toolbar title clicked", Toast.LENGTH_SHORT).show();
            }
        });


        setListener();
        conectarSats();
        animSequential = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.sequencial);

        conectImpressoras();
        statusSat();
        forcaRebootEquip();
    }
    private void  initView(){
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
        tvemailcontador = (TextView) findViewById(R.id.tvemailcontador);
        layoutConfigura = findViewById(id.layoutConfigura);
        layoutSuporte = findViewById(id.layoutSuporte);
        layoutCupom = findViewById(id.layoutCupom);
        layoutConfiguraparam = findViewById(id.Parametros);
        layoutStatusSistema = findViewById(id.StatusSistema);
        layoutCadastrarCat = findViewById(id.layoutCadastrarCategoria);
        layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
        layoutOrcamento = findViewById(id.layoutorcamento);
        layoutVendas.setVisibility(VISIBLE);
        layoutCupom.setVisibility(VISIBLE);
        layoutOrcamento.setVisibility(INVISIBLE);
        bcateg = findViewById(id.bCategoria);
        bprod = findViewById(id.bProd);
        binformarCPF = findViewById(id.btninformcpf);
        bFinalizaVenda = findViewById(id.bFinalizar);
        bFinalizaOrc = findViewById(id.bFinalizarOrc);
        orcamento = findViewById(id.Borcamento);
        geraorcamento = findViewById(id.btngerarorcamento);
        imprimeorcamento = findViewById(id.btnimprimirorcamento);
        fechamento = findViewById(id.Bfechamento);
        tvfechamentoPor = findViewById(id.tvfechamentoPor);
        sptipofechamento = findViewById(id.sptipofechamento);
        textRelatorio = findViewById(id.textRelatorio);
        RelatorioOrcamento = findViewById(id.textRelatorioOrcamento);
        textdtnFinal = findViewById(id.textdtnFinal);
        textdtnInicial = findViewById(id.textdtnInicial);
        listorcamento = findViewById(id.listorcamento);
        btnParametros = (Button) findViewById(id.btnparametros);
        btnStatusSistema = (Button) findViewById(id.btnstatusistema);
        tvTituloConfigurar = (TextView)findViewById(id.tvtituloconfigurar);
        btnContador = (Button) findViewById(id.btncontador);
        btnDesenvolvedora = (Button) findViewById(id.btndesenvolvedora);
        btnDiversos = (Button) findViewById(id.btndiversos);
        btnEmitente = (Button) findViewById(id.btnemitente);
        btnImpressora = (Button) findViewById(id.btnimpressora);
        btnRevenda = (Button) findViewById(id.btnrevenda);
        btnSat = (Button) findViewById(id.btnsat);
        layoutInflater = getLayoutInflater();
        toastLayout = layoutInflater.inflate(layout.layouttoastparametro, (ViewGroup) findViewById(id.layouttoastparametro));
        btnGravarAlt = (Button) findViewById(id.btGravarAlt);
        tvInfo = (EditText) findViewById(id.tvInfo);
        tvInfoDescr = (TextView) findViewById(id.tvInfoDescr);
        tvDescricao = (TextView) findViewById(id.tvDescricao);
        containerGridViewParam = findViewById(id.gridParametros);
        barraproduto = findViewById(id.txtpesquisa);
        barracategoria = findViewById(id.textviewinserirCat);
        dtinicialorcamento = findViewById(id.editDatainicial);
        dtfinalormaneto = findViewById(id.editDatafinal);
        valorTotalVendas = findViewById(id.tvValorTotal);
        valorTotalOrc = findViewById(id.tvValorTotalOrc);
        valorTotSemDescV = findViewById(id.tvTotSemDesc);
        qtditensVenda = findViewById(id.tvQtdItens);
        valorTotSemDescO = findViewById(id.tvTotSemDescOrc);
        qtditensOrc = findViewById(id.tvQtdItensOrc);
        totDescontoV = findViewById(id.tvDesconto);
        totDescontoOcr = findViewById(id.tvDescontoOrc);
        dtinicialcontador = findViewById(id.etdatainicial);
        dtfinalcontador = findViewById(id.etdatafinal);
        buscarProd = findViewById(id.tvPesquisarProd);
        tvSefazAlerta = findViewById(id.tvsefazalerta);
        tvSefazAlerta.setVisibility(INVISIBLE);
        btnImpAlerta = findViewById(id.btnimpralerta);
        btnImpAlerta.setVisibility(INVISIBLE);
        btnSatAlerta = findViewById(id.btsatalerta);
        btnSuporteRemoto =findViewById(id.btnsuporteremoto);
        btnSatAlerta.setVisibility(INVISIBLE);
        animation = new AlphaAnimation(1, 0); // Altera alpha de visível a invisível
        animation.setDuration(500); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo
        tvConsultarStatusOperac = findViewById(R.id.tvconsultarstatusoperac);
    }
    private void setListener(){
        buscarProd.setOnKeyListener(this);
        barraproduto.setOnKeyListener(this);
        binformarCPF.setOnClickListener(this);
        bcateg.setOnClickListener(this);
        bprod.setOnClickListener(this);
        dtinicialorcamento.setOnClickListener(this);
        dtfinalormaneto.setOnClickListener(this);
        dtinicialcontador.setOnClickListener(this);
        dtfinalcontador.setOnClickListener(this);
        btnContador.setOnClickListener(this);
        btnDesenvolvedora.setOnClickListener(this);
        btnDiversos.setOnClickListener(this);
        btnEmitente.setOnClickListener(this);
        btnImpressora.setOnClickListener(this);
        btnRevenda.setOnClickListener(this);
        btnSat.setOnClickListener(this);
        btnGravarAlt.setOnClickListener(this);
        btnSatAlerta.setOnClickListener(this);


    }
    private DiscoveryListener mDiscoveryListener = new DiscoveryListener() {
        @Override
        public void onDiscovery(final DeviceInfo deviceInfo) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    objparam.setImpressoraNome(deviceInfo.getTarget());
                    statuImp =0;
                    conectImpressoras();
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        final View decorView = getWindow().getDecorView();
        statusSat();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void conectarSats(){

        if(objparam.getSatModelo().equalsIgnoreCase("4")) {
            conectSatDimep();
        }
        else if(objparam.getSatModelo().equalsIgnoreCase("3")) {
            conectSatControlid();
        }
    }
    private void conectSatDimep(){
        Intent intent = new Intent(this, USBService.class);
        this.startService(intent);
       this.dimepDSAT = new DimepDSAT();
    }
    public void conectSatControlid() {

        try {
            //SATiD.config(getApplicationContext());
          //  onNewIntentt(getIntent());
            SATiD.config(this);
            SATiD.getInstance().startConnection();

        } catch (Exception e) {


        }
        SATiD.getInstance().setOnChangeConnection(new ISATiDListeners.OnChangeConnectionListener() {
            @Override

            public void status(EnumSatStatus enumSatStatus) {
                if (enumSatStatus.toString().equalsIgnoreCase("CONNECTED")) {
                    btnSatAlerta.setVisibility(INVISIBLE);
                    btnSatAlerta.clearAnimation();
                }
                else{
                    animation = new AlphaAnimation(1, 0); // Altera alpha de visível a invisível
                    animation.setDuration(500); // duração - meio segundo
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
                    animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo
                    btnSatAlerta.startAnimation(animation);
                    reconectarSatControl();
                }

            }
        });
    }
    public void reconectarSatControl(){

        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[]{"/system/bin/su", "-c", "lsusb"});
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            byte[] content = new byte[inputStream.available()];
            inputStream.read(content);
            String teste = new String(content);
            if (teste.contains("1d6b:0104")){
                SATiD.getInstance().startConnection();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public boolean conectImpEpson( ) {

        boolean isBeginTransaction = false;

        if (statuImp == 0) {

            try {

                mPrinter.connect(objparam.getImpressoraNome(), Printer.PARAM_DEFAULT);
                btnImpAlerta.clearAnimation();
                Toast text = Toast.makeText(this, "TMT20 conectada", Toast.LENGTH_LONG);
                text.show();
                statuImp = 1;
            } catch (Epos2Exception e) {
                Toast text = Toast.makeText(this, e.getErrorStatus() + "TMT20 não conectada", Toast.LENGTH_LONG);
                btnImpAlerta.setAnimation(animation);
                text.show();
                statuImp = 0;
                return false;


            }
        }

        return true;
    }
    public ImpSweda conectImpSweda(){
        if (impr == null) {
            try {
                impr = new ImpSweda(getApplicationContext());
                impr.setInterfaceComunicacao(0);
                impr.SelecionarAlinhamento(0);

              if ( impSweda300 == true) {
                  impr.SelecionarFonteCaracteres(2);
                    impr.SelecionarEspacamentoEntreCaracteres(0);
                }
                else{
                  impr.SelecionarFonteCaracteres(3);
                    impr.SelecionarEspacamentoEntreCaracteres(2);
                }

            }
            catch (Exception e){

            }
        }

        return impr;
    }
    public void conectImpressoras() {
            if (objparam.getImpressoraModelo().equalsIgnoreCase("1")){
                btnImpAlerta.clearAnimation();
                statuImp = 1;
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("2")) {
               /* LIBSI.setCallback(new LIBSI.Callback() {
                    @Override
                    public int getAccess(String portName) {
                        String cmd = "chmod 666 " + portName;
                        try {

                            MainActivity.runCmd("su", new String[]{cmd});
                        } catch (Exception e) {
                            return 0;
                        }
                        // conectImpSweda();

                        return 1;


                    }
                });*/
                conectImpSweda();
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
                try {
                    PrintID.config(getApplicationContext());
                    onNewIntent(getIntent());

                    PrintID.getInstance().startConnection();

                } catch (Exception e) {
                }
                PrintID.getInstance().setOnChangeConnection(new IDDeviceListeners.OnChangeConnectionListener() {
                    @Override
                    public void status(EnumStatus enumStatus) {
                        if (enumStatus.toString().equalsIgnoreCase("CONNECTED")) {
                            btnImpAlerta.clearAnimation();
                            statuImp = 1;
                        } else {
                            statuImp = 0;
                            btnImpAlerta.setAnimation(animation);
                        }
                    }
                });
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
                if (statuImp == 0) {
                    try {
                        mPrinter = new Printer(Printer.TM_T20, Printer.MODEL_ANK, this);
                    } catch (Epos2Exception e) {
                        e.printStackTrace();
                    }
                    conectImpEpson();
                }
            }

    }
    protected void onNewIntentt(Intent intentt) {
        //super.onNewIntent(intentt);
       // intentt.setAction(UsbManager.EXTRA_PERMISSION_GRANTED);
      // intentt.setAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);


      //  SATiD.getInstance().startConnection();
       // if (intentt.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED));{
         //   SATiD.getInstance().startConnection();
      //  }

        //  PrintID.getInstance().startConnection();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
       // intent.setAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);

       // if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED))
            PrintID.getInstance().startConnection();
    }
    public void statusSat() {

        if (objparam.getSatModelo().equalsIgnoreCase("2")&& statuSat == 0) {
            sweda = new SATsweda(this);
          //  sweda.execute();

        }
        if (objparam.getSatModelo().equalsIgnoreCase("3")) {
            if (SATiD.getInstance().isConnected()) {
                statuSat = 1;
            } else {
                statuSat = 0;
            }


        }
        if (objparam.getSatModelo().equalsIgnoreCase("4")) {
            this.dimepDSAT = new DimepDSAT();


            String Retorno =   dimepDSAT.ConsultarSAT(UtilidadesGerais.gerarNumeroSessao());
            if(Retorno.length() > 35) {
                statuSat = 0;
            }
            else {
                statuSat = 1;

            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean efetuarVenda() {
        if(objparam.getSatModelo() != "1") {
            statusSat();
            if (statuSat == 1) {
                try {
                    DadosVenda dadosVenda = new DadosVenda();
                    String retornosatt = " ";
                    if (objparam.getSatModelo().equalsIgnoreCase("2")) {

                        try {
                            retornosatt = getSAT().enviarDadosVenda(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao(), dadosVenda.criarDadosVenda().toString());
                        } catch (Exception E) {


                            {
                            }
                        }
                    }
                    if (objparam.getSatModelo().equalsIgnoreCase("3") && SATiD.getInstance().isConnected()) {
                        retornosatt = SATiD.getInstance().EnviarDadosVenda(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao(), dadosVenda.criarDadosVenda().toString());
                    }
                    if (objparam.getSatModelo().equalsIgnoreCase("4")) {
                        this.dimepDSAT = new DimepDSAT();
                        retornosatt = dimepDSAT.EnviarDadosVenda(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao(), dadosVenda.criarDadosVenda().toString());
                    }
                    String dadosvenda = dadosVenda.criarDadosVenda().toString();
                    FileOutputStream savedadosVenda = new FileOutputStream(diretorioTemp + "/" + "dadosVenda" + ".xml");
                    savedadosVenda.write(dadosvenda.getBytes());
                    savedadosVenda.close();
                    retornosat = retornosatt.split("\\|");

                    if (retornosat[1].equalsIgnoreCase("06000")) {

                        chave = retornosat[8];
                        String vendas = UtilidadesGerais.converterBase64(retornosat[6]);
                        String[] hora = vendas.split("hEmi>");
                        String[] data = vendas.split("dEmi>");
                        if (objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
                            qrCode = retornosat[11];
                        } else {
                            qrCode = retornosat[8].substring(3) + "|" + retornosat[7] + "|" + retornosat[9] + "|" + retornosat[10] + "|" + retornosat[11];
                        }
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
                        if (objparam.getSatModelo().equalsIgnoreCase("3") && SATiD.getInstance().isConnected()) {
                            String consultarsttusat = SATiD.getInstance().ConsultarStatusOperacional(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao());
                            String[] separa = consultarsttusat.split("\\|");
                            long diferencaDias = UtilidadesGerais.calcularDiasSemComuinic(separa);
                            if (diferencaDias >= 5) {
                                tvSefazAlerta.setVisibility(VISIBLE);
                                tvSefazAlerta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sefaz, 0, 0, 0);
                                tvSefazAlerta.setText(String.valueOf(diferencaDias));

                            } else {
                                tvSefazAlerta.setVisibility(INVISIBLE);
                                tvSefazAlerta.clearAnimation();
                                tvSefazAlerta.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                tvSefazAlerta.setText("SFZ = " + String.valueOf(diferencaDias));
                            }
                        }
                        if (objparam.getSatModelo().equalsIgnoreCase("2") ) {
                            String consultarsttusat = getSAT().consultarStatusOperacional(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao());
                            String[] separa = consultarsttusat.split("\\|");
                            long diferencaDias = UtilidadesGerais.calcularDiasSemComuinic(separa);

                            if (diferencaDias >= 5) {
                                tvSefazAlerta.setVisibility(VISIBLE);
                                tvSefazAlerta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sefaz, 0, 0, 0);
                                tvSefazAlerta.setText(String.valueOf(diferencaDias));

                            } else {
                                tvSefazAlerta.setVisibility(INVISIBLE);
                                tvSefazAlerta.clearAnimation();
                                tvSefazAlerta.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                tvSefazAlerta.setText("SFZ = " + String.valueOf(diferencaDias));
                            }
                        }

                        if (objparam.getImpressoraConfirm().equalsIgnoreCase("N")) {
                            impCupomFiscal();
                        } else {


                        }


                        return true;
                    } else {

                        erroconexãosat = retornosat[3];
                        //    sweda = new SAT(this);
                        //   sweda.desativarEthSat();
                        return false;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            } else {
                erroconexãosat = "sem conexão Sat";
                //   sweda = new SAT(this);
                //  sweda.desativarEthSat();
                return false;
            }

        }

        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public SATsweda getSAT() {
        if (sweda == null) {

            sweda = new SATsweda(getApplicationContext());
        }

        return sweda;
    }
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cancelarVenda(Cursor cursor) {
        statusSat();
        String retornosatCanc = "";
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

                objCupomFiscal.setData(data);
                objCupomFiscal.setHora(horario);
                objCupomFiscal.setSatSerie(cfeCanc.substring(25,34));
                objdocumento.setTotaldocumentocdesc(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29)));
                String xmlvenda = cursor.getString(18);
                String[] qrcode = xmlvenda.split("assinaturaQRCODE>");
                qrCode = qrcode[1].toString();
                DadosVenda d = new DadosVenda();
                if (objparam.getSatModelo().equalsIgnoreCase("2")) {

                    try {
                        retornosatCanc = getSAT().cancelarUltimaVenda(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao(), cfeCanc, d.criarDadosVendaCanc(cfeCanc).toString());
                    } catch (Exception E) {


                        {
                        }
                    }
                }
                if (objparam.getSatModelo().equalsIgnoreCase("3") && SATiD.getInstance().isConnected()) {
                    retornosatCanc = SATiD.getInstance().CancelarUltimaVenda(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao(), cfeCanc, d.criarDadosVendaCanc(cfeCanc).toString());
                }
               if (objparam.getSatModelo().equalsIgnoreCase("4") ) {
                    retornosatCanc = dimepDSAT.CancelarUltimaVenda(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao(), cfeCanc, d.criarDadosVendaCanc(cfeCanc).toString());
                }
                String[] retornosatCan = retornosatCanc.split("\\|");
                if (retornosatCan[1].equalsIgnoreCase("07000")) {
                    String[] alterarDocCanc = new String[7];
                    alterarDocCanc[0] = iddocumento;
                    alterarDocCanc[1] = retornosatCan[8].substring(3);
                    alterarDocCanc[2] = UtilidadesGerais.converterBase64(retornosatCan[6]);

                    String[] dataCanc = alterarDocCanc[2].split("nCFe><dEmi>");
                    String[] horaCanc = dataCanc[1].split("hEmi>");
                    alterarDocCanc[3] = dataCanc[1].substring(0, 4) + "-" + dataCanc[1].substring(4, 6) + "-" + dataCanc[1].substring(6, 8) + " " + horaCanc[1].substring(0, 2) + ":" + horaCanc[1].substring(2, 4) + ":" + horaCanc[1].substring(4, 6);
                    objCupomFiscal.setDataCanc(dataCanc[1].substring(6, 8) + "/" + dataCanc[1].substring(4, 6) + "/" + dataCanc[1].substring(0, 4));
                    objCupomFiscal.setHoraCanc(horaCanc[1].substring(0, 2) + ":" + horaCanc[1].substring(2, 4) + ":" + horaCanc[1].substring(4, 6));
                    objCupomFiscal.setChaveCfeCanc(retornosatCan[8].substring(3));
                    qrCodeCanc =retornosatCan[8].substring(3)+"|"+retornosatCan[7] +"|"+retornosatCan[9] +"|"+retornosatCan[10] +"|"+ retornosatCan[11];qrCodeCanc = retornosatCan[11];
                    dadosOpenHelper.alterdocCancela(alterarDocCanc);
                    impCupomFiscalCanc();
                    layoutInflater = getLayoutInflater();
                    toastLayout = layoutInflater.inflate(layout.toastcancelamento, (ViewGroup) findViewById(R.id.toastcancelamento));
                    Toast toast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setView(toastLayout);
                    toast.show();

                } else {
                    inicianovodocumento();
                   // String[] alterarDocCanc = new String[3];
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Houve uma falha");
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


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (objparam.getSatModelo().equalsIgnoreCase("2")) {
                sweda = new SATsweda(this);
                //sweda.desativarEthSat();
            }
            inicianovodocumento();
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
    public void imprimirCupomOrc(StringBuilder cumpomorc) {

        if(objparam.getImpressoraModelo().equalsIgnoreCase("2")){
           // impr.setInterfaceComunicacao(0);
          //  impr.SelecionarAlinhamento(0);
          //  impr.SelecionarFonteCaracteres(8);

          //  impr.SelecionarEspacamentoEntreCaracteres(3);
            for (int i =1; i<=nViasImpOrc; i++) {
                conectImpSweda().imprimirTextos(cumpomorc.toString());
                conectImpSweda().acionarCortadorPapel();
            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
            if (PrintID.getInstance().isConnected()) {
                for (int i =1; i<=nViasImpOrc; i++) {
                    PrintID.getInstance().ImprimirFormatado(cumpomorc.toString(), false, false, false, false);
                    PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
                }
            } else {

            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
            mPrinter.clearCommandBuffer();
            try {

                for (int i =1; i<=nViasImpOrc; i++) {
                    mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                    mPrinter.addText(cumpomorc.toString());
                    mPrinter.addFeedLine(10);
                    mPrinter.addCut(Printer.CUT_FEED);
                    mPrinter.addCut(Printer.CUT_RESERVE);
                    mPrinter.sendData(Printer.PARAM_DEFAULT);
                }

            } catch (Epos2Exception e) {
                e.printStackTrace();
            }

        }


    }
    public void reimprimirCupom(StringBuilder cumpomreimprimir) {
        if(objparam.getImpressoraModelo().equalsIgnoreCase("2")){
            if (impr != null) {
                conectImpSweda().imprimirTextos(cumpomreimprimir.toString());
                String completachave1 = objCupomFiscal.getChaveCfe().substring(0, 44);
                conectImpSweda().ImprimirCodigoBarras(8, completachave1);
                int tamQrcode = qrCode.length() - 2;
                conectImpSweda().iImprimeQRCode_TextoDireita(qrCode.substring(0, tamQrcode));

                conectImpSweda().acionarCortadorPapel();
            }else{

            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
            mPrinter.clearCommandBuffer();
            try {


                mPrinter.addTextAlign(Printer.ALIGN_LEFT);

                mPrinter.addText(cumpomreimprimir.toString());
                String completachave1 =  objCupomFiscal.getChaveCfe().substring(0, 44);
                //String completachave2 =  objCupomFiscal.getChaveCfe().substring(0, 44);
                mPrinter.addSymbol(qrCode.substring(0, 410),
                        Printer.SYMBOL_QRCODE_MODEL_2,
                        Printer.LEVEL_L,
                        4,
                        4,
                        0);
                mPrinter.addBarcode(completachave1,
                        Printer.BARCODE_GS1_128,
                        Printer.HRI_BELOW,
                        Printer.FONT_B,
                        2,
                        70);




                mPrinter.addFeedLine(10);
                mPrinter.addCut(Printer.CUT_FEED);
                mPrinter.addCut(Printer.CUT_RESERVE);
                mPrinter.sendData(Printer.PARAM_DEFAULT);


            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
            if (PrintID.getInstance().isConnected()) {
                PrintID.getInstance().ImprimirFormatado(cumpomreimprimir.toString(), false, false, false, false);
                PrintID.getInstance().ConfigurarCodigoDeBarras(60, 2, EnumPosicaoCaracteresBarras.SEM_CARACTERES);
                String completachave1 = "  " + objCupomFiscal.getChaveCfe().substring(0, 22);
                String completachave2 = "  " + objCupomFiscal.getChaveCfe().substring(22, 44);
                PrintID.getInstance().ImprimirCodigoDeBarras(completachave1, EnumTipoCodigoBarras.CODE128);
                PrintID.getInstance().ImprimirCodigoDeBarras(completachave2, EnumTipoCodigoBarras.CODE128);
                PrintID.getInstance().ImprimirCodigoQR(qrCode.substring(0, 410), 4, EnumQRCorrecaoErro.MEDIO_BAIXO, EnumQRModelo.MODELO_2);
                PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
            } else {
            }
        }
    }
    public void impCupomFiscal() {
        CupomFiscalSat CupomFiscal = new CupomFiscalSat();
        CupomFiscal.montarCupom();
        if(objparam.getImpressoraModelo().equalsIgnoreCase("2")){
for (int i =1; i<=nViasImpVenda; i++) {
    conectImpSweda().imprimirTextos(CupomFiscal.cupomFiscalSat.toString());
    String completachave1 = objCupomFiscal.getChaveCfe().substring(0, 44);
    conectImpSweda().ImprimirCodigoBarras(8, completachave1);
    conectImpSweda().iImprimeQRCode_TextoDireita(qrCode);
    conectImpSweda().acionarCortadorPapel();
}
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
            if (PrintID.getInstance().isConnected()) {
                for (int i =1; i<=nViasImpVenda; i++) {
                    PrintID.getInstance().ImprimirFormatado(CupomFiscal.cupomFiscalSat.toString(), false, false, false, true);
                    PrintID.getInstance().ConfigurarCodigoDeBarras(60, 2, EnumPosicaoCaracteresBarras.SEM_CARACTERES);
                    String completachave1 = "  " + objCupomFiscal.getChaveCfe().substring(0, 22);
                    String completachave2 = "  " + objCupomFiscal.getChaveCfe().substring(22, 44);
                    PrintID.getInstance().ImprimirCodigoDeBarras(completachave1, EnumTipoCodigoBarras.CODE128);
                    PrintID.getInstance().ImprimirCodigoDeBarras(completachave2, EnumTipoCodigoBarras.CODE128);
                    PrintID.getInstance().ImprimirCodigoQR(qrCode, 4, EnumQRCorrecaoErro.BAIXO, EnumQRModelo.MODELO_1);
                    PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
                }
            } else {
            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
            mPrinter.clearCommandBuffer();
            try {

                for (int i =1; i<=nViasImpVenda; i++) {
                    mPrinter.addTextAlign(Printer.ALIGN_LEFT);

                    mPrinter.addText(CupomFiscal.cupomFiscalSat.toString());
                    String completachave1 = objCupomFiscal.getChaveCfe().substring(0, 44);
                    String completachave2 = objCupomFiscal.getChaveCfe().substring(0, 44);
                    mPrinter.addSymbol(qrCode,
                            Printer.SYMBOL_QRCODE_MODEL_2,
                            Printer.LEVEL_L,
                            5,
                            5,
                            0);
                    mPrinter.addBarcode(completachave1,
                            Printer.BARCODE_GS1_128,
                            Printer.HRI_BELOW,
                            Printer.FONT_B,
                            2,
                            70);


                    mPrinter.addFeedLine(10);
                    mPrinter.addCut(Printer.CUT_FEED);
                    mPrinter.addCut(Printer.CUT_RESERVE);
                    mPrinter.sendData(Printer.PARAM_DEFAULT);
                }

            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void impCupomFiscalCanc() {

        CupomFiscalSat CupomFiscal = new CupomFiscalSat();
        CupomFiscal.montarCupomCanc();
        if(objparam.getImpressoraModelo().equalsIgnoreCase("2")) {
            for (int i =1; i<=nViasImpCanc; i++) {
                conectImpSweda().imprimirTextos(CupomFiscal.cupomFiscalCancelado.toString());
                String completachave1 = objCupomFiscal.getChaveCfe().substring(0, 44);
                conectImpSweda().ImprimirCodigoBarras(8, completachave1);
                conectImpSweda().iImprimeQRCode_TextoDireita(qrCode);
                conectImpSweda().imprimirTextos(CupomFiscal.cupomFiscalCancelamento.toString());
                String completachaveCanc1 = objCupomFiscal.getChaveCfeCanc().substring(0, 44);
                conectImpSweda().ImprimirCodigoBarras(8, completachaveCanc1);
                conectImpSweda().iImprimeQRCode_TextoDireita(qrCodeCanc);
                conectImpSweda().acionarCortadorPapel();
            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
            if (PrintID.getInstance().isConnected()) {
                for (int i =1; i<=nViasImpCanc; i++) {
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
                }
                    inicianovodocumento();

            } else {
                inicianovodocumento();
            }
        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
            mPrinter.clearCommandBuffer();
            try {
                for (int i =1; i<=nViasImpCanc; i++) {
                    mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                    mPrinter.addText(CupomFiscal.cupomFiscalCancelado.toString());
                    String completachave1 = objCupomFiscal.getChaveCfe().substring(0, 44);
                    String completachave2 = objCupomFiscal.getChaveCfe().substring(0, 44);
                    mPrinter.addSymbol(qrCode,
                            Printer.SYMBOL_QRCODE_MODEL_2,
                            Printer.LEVEL_L,
                            5,
                            5,
                            0);
                    mPrinter.addBarcode(completachave1,
                            Printer.BARCODE_GS1_128,
                            Printer.HRI_BELOW,
                            Printer.FONT_B,
                            2,
                            70);

                    mPrinter.addText(CupomFiscal.cupomFiscalCancelamento.toString());
                    String completachaveCanc1 = objCupomFiscal.getChaveCfeCanc().substring(0, 44);
                    String completachaveCanc2 = "  " + objCupomFiscal.getChaveCfeCanc().substring(22, 44);
                    mPrinter.addBarcode(completachaveCanc1,
                            Printer.BARCODE_GS1_128,
                            Printer.HRI_BELOW,
                            Printer.FONT_B,
                            2,
                            70);
                    mPrinter.addSymbol(qrCodeCanc,
                            Printer.SYMBOL_QRCODE_MODEL_2,
                            Printer.LEVEL_L,
                            5,
                            5,
                            0);
                    mPrinter.addFeedLine(10);
                    mPrinter.addCut(Printer.CUT_FEED);
                    mPrinter.addCut(Printer.CUT_RESERVE);
                    mPrinter.sendData(Printer.PARAM_DEFAULT);
                }
                    inicianovodocumento();

            } catch (Epos2Exception e) {
                e.printStackTrace();
            }
        }

        inicianovodocumento();
    }
    public void imprimirRelOrc(View view ) {


        CupomRelatorios  CupomRelatotio = new CupomRelatorios();

        if (auxtipoRelatorio==0) {
            CupomRelatotio.cupomOrcamento();
            if (objparam.getImpressoraModelo().equalsIgnoreCase("2")) {
                if (impr != null) {

                    conectImpSweda().imprimirTextos(CupomRelatotio.cupomRelatorios.toString());
                    conectImpSweda().acionarCortadorPapel();
                }
                else{

                }
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
                mPrinter.clearCommandBuffer();
                try {
                    mPrinter.addTextAlign(Printer.ALIGN_LEFT);
                    mPrinter.addText(CupomRelatotio.cupomRelatorios.toString());
                    mPrinter.addFeedLine(10);
                    mPrinter.addCut(Printer.CUT_FEED);
                    mPrinter.addCut(Printer.CUT_RESERVE);
                    mPrinter.sendData(Printer.PARAM_DEFAULT);
                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
                if (PrintID.getInstance().isConnected()) {
                    PrintID.getInstance().ImprimirFormatado(CupomRelatotio.cupomRelatorios.toString(), false, false, false, false);

                    PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
                } else {
                }
            }
        }
        if (auxtipoRelatorio!=0){
            CupomRelatotio.cupomFechamento();
            if (objparam.getImpressoraModelo().equalsIgnoreCase("2")) {
                if (impr != null) {
                    conectImpSweda().imprimirTextos(CupomRelatotio.cupomRelatorios.toString());
                    conectImpSweda().acionarCortadorPapel();
                }
                else{

                }
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("4")) {
                mPrinter.clearCommandBuffer();
                try {


                    mPrinter.addTextAlign(Printer.ALIGN_LEFT);

                    mPrinter.addText(CupomRelatotio.cupomRelatorios.toString());
                    mPrinter.addFeedLine(10);
                    mPrinter.addCut(Printer.CUT_FEED);
                    mPrinter.addCut(Printer.CUT_RESERVE);
                    mPrinter.sendData(Printer.PARAM_DEFAULT);


                } catch (Epos2Exception e) {
                    e.printStackTrace();
                }
            }
            if (objparam.getImpressoraModelo().equalsIgnoreCase("3")) {
                try {


                    if (PrintID.getInstance().isConnected()) {
                        PrintID.getInstance().ImprimirFormatado(CupomRelatotio.cupomRelatorios.toString(), false, false, false, false);

                        PrintID.getInstance().AtivarGuilhotina(EnumTipoCorte.TOTAL);
                    } else {
                    }
                }
                catch (Exception e){

                }
            }
        }
    }
    public void versaoApp() {
        TextView tvVersion;
        tvVersion  =  findViewById(id.tvversion);
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        versaoAtual = pInfo.versionName;
        tvVersion.setText("V."+(versaoAtual));

    }
    public void atualizarApp(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Baixar atualização");
        builder.setMessage("Tem certeza que deseja atualizar aplicação? ");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                final Button btndesenv2 = (Button)findViewById(id.btndesenv2);
                btndesenv2.setText("AGUARDE ....");
                btndesenv2.setTextColor(Color.RED);
                btndesenv2.setTextSize(24);
                btndesenv2.setClickable(false);
                //diretorioAtualizaGeral = new File( Environment.getExternalStorageDirectory() + "/satflex/atualizacaogeral");
                VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
                if (verificarInternetStatus.executeCommand() == true) {
                    //verifica se tem internet e pega arquivos no firebase
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageBaixarApp = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
                    StorageReference baixarapp = storageBaixarApp.child("atualizacao/" + "remarca-flex.apk");

                    //baixa arquivos para a pasta do databases
                    final File localFilesbaixarapp = new File(diretorioAtualiza, baixarapp.getName());
                    baixarapp.getFile(localFilesbaixarapp);


                    baixarapp.getFile(localFilesbaixarapp).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            btndesenv2.setText("BAIXAR APLICAÇÃO");
                            btndesenv2.setTextColor(Color.parseColor("#FFFFFF"));
                            btndesenv2.setTextSize(20);
                            toastLayout = layoutInflater.inflate(layout.toastdownloadok, (ViewGroup) findViewById(id.toastdownloadok));
                            Toast toast1 = Toast.makeText(getApplicationContext(), "aguarde  ", Toast.LENGTH_LONG);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.setView(toastLayout);
                            toast1.show();
                            btndesenv2.setClickable(true);
                            instalarApp();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            btndesenv2.setText("BAIXAR APLICAÇÃO");
                            btndesenv2.setTextSize(20);
                            btndesenv2.setTextColor(Color.parseColor("#FFFFFF"));
                            btndesenv2.setClickable(true);
                            toastLayout = layoutInflater.inflate(layout.toastdownloadfail, (ViewGroup) findViewById(id.toastdownloadfail));
                            Toast toast1 = Toast.makeText(getApplicationContext(), "aguarde  ", Toast.LENGTH_LONG);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.setView(toastLayout);
                            toast1.show();
                        }
                    });
                }
                else{
                    btndesenv2.setText("BAIXAR APLICAÇÃO");
                    btndesenv2.setTextSize(20);
                    btndesenv2.setTextColor(Color.parseColor("#FFFFFF"));
                    btndesenv2.setClickable(true);
                    builder.setTitle("Falha no download");
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
        });

        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
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
    public void instalarApp(){
        try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "pm install -r sdcard/satflex/atualizacao/remarca-flex.apk"});
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void downloadArqConfig(View view) {
        final Button btnbaixararquivos = (Button)findViewById(R.id.btnbaixararquivos);
        btnbaixararquivos.setText("AGUARDE ....");
        btnbaixararquivos.setTextColor(Color.RED);
        btnbaixararquivos.setTextSize(24);
        btnbaixararquivos.setClickable(false);
        VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
        if (verificarInternetStatus.executeCommand() == true){
            FirebaseStorage storage = FirebaseStorage.getInstance();

            for (int i=0; i<25; i++){

                StorageReference storageRefsplash1 = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
                StorageReference splash1 = storageRefsplash1.child("arqconfig" + "/splash"+i+".JPG");
                final File localFilesplash1 = new File(diretorioImg, splash1.getName());
                splash1.getFile(localFilesplash1);
            }
            StorageReference storageRefLogo = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
            StorageReference logo = storageRefLogo.child("arqconfig" + "/logo.JPG");

            final File localFilelogo = new File(diretorioImg, logo.getName());
            logo.getFile(localFilelogo);

            StorageReference storageLegendaSplash = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
            StorageReference legendaSplash = storageLegendaSplash.child("arqconfig" + "/legendasplash.txt");
            final File localFileLegendaSplash = new File(diretorioImg, legendaSplash.getName());
            legendaSplash.getFile(localFileLegendaSplash);

            StorageReference storageRefNcm = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
            StorageReference ncm = storageRefNcm.child("arqconfig" + "/ncm.txt");
            final File localFileNCm = new File(diretorioTemp, ncm.getName());

            StorageReference storageRefAppSW = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
            StorageReference appSweda = storageRefAppSW.child("arqconfig" + "/appsweda.apk");
            final File localFileAppSw = new File(diretorioTemp, appSweda.getName());
            appSweda.getFile(localFileAppSw);
            ncm.getFile(localFileNCm).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    btnbaixararquivos.setText("ATUALIZAR ARQUIVOS CONFIG");
                    btnbaixararquivos.setTextColor(Color.parseColor("#FFFFFF"));
                    btnbaixararquivos.setTextSize(20);
                    toastLayout = layoutInflater.inflate(layout.toastdownloadok, (ViewGroup) findViewById(id.toastdownloadok));
                    Toast toast1 = Toast.makeText(getApplicationContext(), "aguarde  ", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.setView(toastLayout);
                    toast1.show();
                    btnbaixararquivos.setClickable(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    btnbaixararquivos.setText("ATUALIZAR ARQUIVOS CONFIG");
                    btnbaixararquivos.setTextSize(20);
                    btnbaixararquivos.setTextColor(Color.parseColor("#FFFFFF"));
                    btnbaixararquivos.setClickable(true);
                    toastLayout = layoutInflater.inflate(layout.toastdownloadfail, (ViewGroup) findViewById(id.toastdownloadfail));
                    Toast toast1 = Toast.makeText(getApplicationContext(), "aguarde  ", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.setView(toastLayout);
                    toast1.show();
                }
            });}
        else{
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                btnbaixararquivos.setText("ATUALIZAR ARQUIVOS CONFIG");
                btnbaixararquivos.setTextSize(20);
                btnbaixararquivos.setTextColor(Color.parseColor("#FFFFFF"));
                btnbaixararquivos.setClickable(true);
                builder.setTitle("Falha no download");
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


    }
    public void downloadBanco(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Baixar banco convertido");
        builder.setMessage("Tem certeza que deseja baixar banco convertido?" + "\n"  + "Obs.excluirá banco atual. ");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                final Button btnbaixarbanco = (Button)findViewById(id.btnbaixarbanco);
                btnbaixarbanco.setText("AGUARDE ....");
                btnbaixarbanco.setTextColor(Color.RED);
                btnbaixarbanco.setTextSize(24);
                btnbaixarbanco.setClickable(false);

                diretorioBancoConvertido = new File( "/data/data/com.example.ademirestudo/databases");
                VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
                if (verificarInternetStatus.executeCommand() == true) {
                    //verifica se tem internet e pega arquivos no firebase
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageBaixarBanco = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
                    StorageReference baixarbanco1 = storageBaixarBanco.child("BancoConvertido/" + "satflex");
                    StorageReference baixarbanco2 = storageBaixarBanco.child("BancoConvertido/" + "satflex-shm");
                    StorageReference baixarbanco3 = storageBaixarBanco.child("BancoConvertido/" + "satflex-wal");
                    //baixa arquivos para a pasta do databases
                    final File localFilesBaixarBanco1 = new File(diretorioBancoConvertido, baixarbanco1.getName());
                    baixarbanco1.getFile(localFilesBaixarBanco1);

                    final File localFilesBaixarBanco2 = new File(diretorioBancoConvertido, baixarbanco2.getName());
                    baixarbanco2.getFile(localFilesBaixarBanco2);

                    final File localFilesBaixarBanco3 = new File(diretorioBancoConvertido, baixarbanco3.getName());
                    baixarbanco3.getFile(localFilesBaixarBanco3);

                    baixarbanco3.getFile(localFilesBaixarBanco3).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            btnbaixarbanco.setText("BAIXAR BANCO CONVERTIDO");
                            btnbaixarbanco.setTextColor(Color.parseColor("#FFFFFF"));
                            btnbaixarbanco.setTextSize(20);
                            toastLayout = layoutInflater.inflate(layout.toastdownloadok, (ViewGroup) findViewById(id.toastdownloadok));
                            Toast toast1 = Toast.makeText(getApplicationContext(), "aguarde  ", Toast.LENGTH_LONG);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.setView(toastLayout);
                            toast1.show();
                            btnbaixarbanco.setClickable(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            btnbaixarbanco.setText("BAIXAR BANCO CONVERTIDO");
                            btnbaixarbanco.setTextSize(20);
                            btnbaixarbanco.setTextColor(Color.parseColor("#FFFFFF"));
                            btnbaixarbanco.setClickable(true);
                            toastLayout = layoutInflater.inflate(layout.toastdownloadfail, (ViewGroup) findViewById(id.toastdownloadfail));
                            Toast toast1 = Toast.makeText(getApplicationContext(), "aguarde  ", Toast.LENGTH_LONG);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.setView(toastLayout);
                            toast1.show();
                        }
                    });
                }
                else{
                    btnbaixarbanco.setText("BAIXAR BANCO CONVERTIDO");
                    btnbaixarbanco.setTextSize(20);
                    btnbaixarbanco.setTextColor(Color.parseColor("#FFFFFF"));
                    btnbaixarbanco.setClickable(true);
                    builder.setTitle("Falha no download");
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
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
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
    @Override
    public void onBackPressed() {

    }
    public static void criarDiretorios() {

        diretorioAtualiza = new File(Environment.getExternalStorageDirectory() + "/satflex/atualizacao");
        diretorioBanco = new File(Environment.getExternalStorageDirectory() + "/satflex/banco");
        diretorioXmlContador = new File(Environment.getExternalStorageDirectory() + "/satflex/xmlcontador");
        diretorioTemp = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/");
        diretorioImg = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/img/");
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
        if (!diretorioImg.exists()) {
            diretorioImg.mkdirs();
        }
        if (!diretorioXmlTemp.exists()) {
            diretorioXmlTemp.mkdirs();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            ValidarCPF_CNPJ valida = new ValidarCPF_CNPJ();
            String retornoteclado = data.getStringExtra("KEY");
            if (retornoteclado.length() == 13) {
                String parametrovalidador = retornoteclado.substring(0, 3) + retornoteclado.substring(3, 6) + retornoteclado.substring(6, 9) + retornoteclado.substring(11, 13);

                if (valida.validarCPF(parametrovalidador) == true) {
                    this.binformarCPF.setText("CPF: " + retornoteclado.substring(0,10) + retornoteclado.substring(11,13));
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

                        this.binformarCPF.setText("CNPJ: " + retornoteclado.substring(0,14)+retornoteclado.substring(15,17));
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
                    builder.setMessage("Tem certeza que deseja cancelar a venda?" + "\n" + "\n" + "Cupom: " + String.format("%06d", cursor.getInt(22)) + "\n" + "Total: R$ " + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + "\n" + "CFE: " + cfeCanc + "\n");

                    //define um botão como positivo
                    final AlertDialog.Builder sim = builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.O)
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public void onClick(DialogInterface arg0, int arg1) {
                            objCupomFiscal.setChaveCfe(cursor.getString(6));
                            objCupomFiscal.setCoo(Integer.parseInt(cursor.getString(22)));
                            cancelarVenda(cursor);
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
    public void efetivarOrcc() {
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
                objProdOrc.setQuantidade(tributacao.getDouble(1));
                objProdOrc.setTotalproduto(tributacao.getDouble(2) * tributacao.getDouble(1));
                objProdOrc.setTotaldesconto(tributacao.getDouble(5));
                objProdOrc.setDescontounitario(tributacao.getDouble(4));
                objProdOrc.setAcrescimounitario(tributacao.getDouble(6));
                objProdOrc.setTotalacrescimo(tributacao.getDouble(7));
                objProdOrc.setTotalprodutocdesc((tributacao.getDouble(2) * tributacao.getDouble(1)) - tributacao.getDouble(5) + objProdOrc.getTotalacrescimo());
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
    public void inicianovodocumento() {

        objdocumento = new modelDocumento();
        listadeItens.clear();
        binformarCPF.setText("Informar CPF/CNPJ");

        atualizarlista();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater ingflaterr = getMenuInflater();
        Menu teste = null;
        ingflaterr.inflate(R.menu.menu_orcamento, menu);


        return super.onCreateOptionsMenu(menu);
    }
    public void onClick(View view) {
        final ArrayList<modelParametros> listadeItensParam = new ArrayList<>();

        final AdapterParametros itensparam = new AdapterParametros(this, listadeItensParam);
        containerGridViewParam.setAdapter(itensparam);
        switch (view.getId()) {
            case id.btsatalerta:
                btnSatAlerta.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conectSatControlid();



                    }
                });
            case id.btnimpralerta:
                btnImpAlerta.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conectImpressoras();
                        // conectImpControlid();

                    }
                });

            case id.btncontador:
                btnContador.setOnClickListener(new OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       btnGravarAlt.setBackgroundResource(semfoco);
                                                       tvInfo.setText("");
                                                       btnContador.setBackgroundResource(corparametroselect);
                                                       btnDesenvolvedora.setBackgroundResource(corparametrosemfoco);
                                                       btnDiversos.setBackgroundResource(corparametrosemfoco);
                                                       btnEmitente.setBackgroundResource(corparametrosemfoco);
                                                       btnImpressora.setBackgroundResource(corparametrosemfoco);
                                                       btnRevenda.setBackgroundResource(corparametrosemfoco);
                                                       btnSat.setBackgroundResource(corparametrosemfoco);
                                                       listadeItensParam.clear();
                                                       modelParametros objParam = new modelParametros();
                                                       objParam.setGrupo("CONTADOR");
                                                       objParam.setNome("EMAIL");
                                                       objParam.setIdparametro(32);
                                                       objParam.setObservacao("E-mail de contato do contador.");
                                                       tvDescricao.setVisibility(INVISIBLE);
                                                       tvInfoDescr.setVisibility(INVISIBLE);
                                                       listadeItensParam.add(objParam);
                                                       itensparam.notifyDataSetChanged();
//
                                                   }
                                               }

                );

            case id.btndesenvolvedora:
                btnDesenvolvedora.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btnContador.setBackgroundResource(corparametrosemfoco);
                        btnDesenvolvedora.setBackgroundResource(corparametroselect);
                        btnDiversos.setBackgroundResource(corparametrosemfoco);
                        btnEmitente.setBackgroundResource(corparametrosemfoco);
                        btnImpressora.setBackgroundResource(corparametrosemfoco);
                        btnRevenda.setBackgroundResource(corparametrosemfoco);
                        btnSat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 3; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("ASSINATURA");
                                objParam.setIdparametro(5);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            } else {

                                if (i == 1) {
                                    modelParametros objParam = new modelParametros();
                                    objParam.setNome("CNPJ");
                                    objParam.setIdparametro(4);
                                    tvDescricao.setVisibility(INVISIBLE);
                                    tvInfoDescr.setVisibility(INVISIBLE);
                                    listadeItensParam.add(objParam);
                                    itensparam.notifyDataSetChanged();
//
                                } else {

                                    if (i == 2) {
                                        modelParametros objParam = new modelParametros();
                                        objParam.setNome("UF");
                                        objParam.setIdparametro(3);
                                        tvDescricao.setVisibility(INVISIBLE);
                                        tvInfoDescr.setVisibility(INVISIBLE);
                                        listadeItensParam.add(objParam);
                                        itensparam.notifyDataSetChanged();
//
                                    }
                                }
                            }
                        }
                    }
                });

            case id.btndiversos:
                btnDiversos.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btnContador.setBackgroundResource(corparametrosemfoco);
                        btnDesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btnDiversos.setBackgroundResource(corparametroselect);
                        btnEmitente.setBackgroundResource(corparametrosemfoco);
                        btnImpressora.setBackgroundResource(corparametrosemfoco);
                        btnRevenda.setBackgroundResource(corparametrosemfoco);
                        btnSat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 1; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("HORÁRIO BACKUP");
                                objParam.setIdparametro(35);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                        }
                    }
                });


            case id.btnemitente:
                btnEmitente.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvInfo.setText("");
                        btnGravarAlt.setBackgroundResource(semfoco);
                        btnEmitente.setBackgroundResource(corparametroselect);
                        btnContador.setBackgroundResource(corparametrosemfoco);
                        btnDesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btnDiversos.setBackgroundResource(corparametrosemfoco);
                        btnImpressora.setBackgroundResource(corparametrosemfoco);
                        btnRevenda.setBackgroundResource(corparametrosemfoco);
                        btnSat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 12; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NOMEFANTASIA");
                                objParam.setIdparametro(33);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            } else {

                                if (i == 1) {
                                    modelParametros objParam = new modelParametros();
                                    objParam.setNome("RAZÃO SOCIAL");
                                    objParam.setIdparametro(2);
                                    tvDescricao.setVisibility(INVISIBLE);
                                    tvInfoDescr.setVisibility(INVISIBLE);
                                    listadeItensParam.add(objParam);
                                    itensparam.notifyDataSetChanged();

                                } else {

                                    if (i == 2) {
                                        modelParametros objParam = new modelParametros();
                                        objParam.setNome("CNPJ");
                                        objParam.setIdparametro(1);
                                        tvDescricao.setVisibility(INVISIBLE);
                                        tvInfoDescr.setVisibility(INVISIBLE);
                                        listadeItensParam.add(objParam);
                                        itensparam.notifyDataSetChanged();

                                    } else {

                                        if (i == 3) {
                                            modelParametros objParam = new modelParametros();
                                            objParam.setNome("IE");
                                            objParam.setIdparametro(7);
                                            tvDescricao.setVisibility(INVISIBLE);
                                            tvInfoDescr.setVisibility(INVISIBLE);
                                            listadeItensParam.add(objParam);
                                            itensparam.notifyDataSetChanged();


                                        } else {

                                            if (i == 4) {
                                                modelParametros objParam = new modelParametros();
                                                objParam.setNome("CEP");
                                                objParam.setIdparametro(13);
                                                tvDescricao.setVisibility(INVISIBLE);
                                                tvInfoDescr.setVisibility(INVISIBLE);
                                                listadeItensParam.add(objParam);
                                                itensparam.notifyDataSetChanged();


                                            } else {

                                                if (i == 5) {
                                                    modelParametros objParam = new modelParametros();
                                                    objParam.setNome("UF");
                                                    objParam.setIdparametro(16);
                                                    tvDescricao.setVisibility(INVISIBLE);
                                                    tvInfoDescr.setVisibility(INVISIBLE);
                                                    listadeItensParam.add(objParam);
                                                    itensparam.notifyDataSetChanged();


                                                } else {

                                                    if (i == 6) {
                                                        modelParametros objParam = new modelParametros();
                                                        objParam.setNome("MUNICIPIO");
                                                        objParam.setIdparametro(12);
                                                        tvDescricao.setVisibility(INVISIBLE);
                                                        tvInfoDescr.setVisibility(INVISIBLE);
                                                        listadeItensParam.add(objParam);
                                                        itensparam.notifyDataSetChanged();


                                                    } else {

                                                        if (i == 7) {
                                                            modelParametros objParam = new modelParametros();
                                                            objParam.setNome("BAIRRO");
                                                            objParam.setIdparametro(11);
                                                            tvDescricao.setVisibility(INVISIBLE);
                                                            tvInfoDescr.setVisibility(INVISIBLE);
                                                            listadeItensParam.add(objParam);
                                                            itensparam.notifyDataSetChanged();


                                                        } else {

                                                            if (i == 8) {
                                                                modelParametros objParam = new modelParametros();
                                                                objParam.setNome("ENDEREÇO");
                                                                objParam.setGrupo("EMITENTE");
                                                                objParam.setIdparametro(8);
                                                                tvDescricao.setVisibility(INVISIBLE);
                                                                tvInfoDescr.setVisibility(INVISIBLE);
                                                                listadeItensParam.add(objParam);
                                                                itensparam.notifyDataSetChanged();


                                                            } else {

                                                                if (i == 9) {
                                                                    modelParametros objParam = new modelParametros();
                                                                    objParam.setNome("NÚMERO");
                                                                    objParam.setGrupo("EMITENTE");
                                                                    objParam.setIdparametro(9);
                                                                    tvDescricao.setVisibility(INVISIBLE);
                                                                    tvInfoDescr.setVisibility(INVISIBLE);
                                                                    listadeItensParam.add(objParam);
                                                                    itensparam.notifyDataSetChanged();


                                                                } else {

                                                                    if (i == 10) {
                                                                        modelParametros objParam = new modelParametros();
                                                                        objParam.setNome("COMPLEMENTO");
                                                                        objParam.setGrupo("EMITENTE");
                                                                        objParam.setIdparametro(10);
                                                                        tvDescricao.setVisibility(INVISIBLE);
                                                                        tvInfoDescr.setVisibility(INVISIBLE);
                                                                        listadeItensParam.add(objParam);
                                                                        itensparam.notifyDataSetChanged();


                                                                    } else {

                                                                        if (i == 11) {
                                                                            modelParametros objParam = new modelParametros();
                                                                            objParam.setNome("IMPOSTO ESTADUAL");
                                                                            objParam.setGrupo("EMITENTE");
                                                                            objParam.setIdparametro(20);
                                                                            tvDescricao.setVisibility(INVISIBLE);
                                                                            tvInfoDescr.setVisibility(INVISIBLE);
                                                                            listadeItensParam.add(objParam);
                                                                            itensparam.notifyDataSetChanged();


                                                                        } else {

                                                                            if (i == 12) {
                                                                                modelParametros objParam = new modelParametros();
                                                                                objParam.setNome("IMPOSTO FEDERAL");
                                                                                objParam.setGrupo("EMITENTE");
                                                                                objParam.setIdparametro(19);
                                                                                tvDescricao.setVisibility(INVISIBLE);
                                                                                tvInfoDescr.setVisibility(INVISIBLE);
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
            case id.btnimpressora:
                btnImpressora.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btnContador.setBackgroundResource(corparametrosemfoco);
                        btnDesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btnDiversos.setBackgroundResource(corparametrosemfoco);
                        btnEmitente.setBackgroundResource(corparametrosemfoco);
                        btnImpressora.setBackgroundResource(corparametroselect);
                        btnRevenda.setBackgroundResource(corparametrosemfoco);
                        btnSat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 5; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("COLUNAS");
                                objParam.setIdparametro(28);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 1) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("CONFIRMAÇAO");
                                objParam.setIdparametro(41);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 2) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("GUILHOTINA");
                                objParam.setIdparametro(52);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 3) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("MODELO");
                                objParam.setIdparametro(27);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 4) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NOME");
                                objParam.setIdparametro(29);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 5) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NUMEROVIAS");
                                objParam.setIdparametro(37);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                        }
                    }
                });
            case id.btnrevenda:
                btnRevenda.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btnContador.setBackgroundResource(corparametrosemfoco);
                        btnDesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btnDiversos.setBackgroundResource(corparametrosemfoco);
                        btnEmitente.setBackgroundResource(corparametrosemfoco);
                        btnImpressora.setBackgroundResource(corparametrosemfoco);
                        btnRevenda.setBackgroundResource(corparametroselect);
                        btnSat.setBackgroundResource(corparametrosemfoco);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 5; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("EMAIL");
                                objParam.setIdparametro(25);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 1) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("ENDEREÇO");
                                objParam.setIdparametro(23);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 2) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("INFORMATIVO");
                                objParam.setIdparametro(22);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 3) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("LOGOTIPO");
                                objParam.setIdparametro(21);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 4) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("NOME");
                                objParam.setIdparametro(26);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 5) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("TELEFONE");
                                objParam.setIdparametro(24);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                        }
                    }
                });

            case id.btnsat:
                btnSat.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnGravarAlt.setBackgroundResource(semfoco);
                        tvInfo.setText("");
                        btnContador.setBackgroundResource(corparametrosemfoco);
                        btnDesenvolvedora.setBackgroundResource(corparametrosemfoco);
                        btnDiversos.setBackgroundResource(corparametrosemfoco);
                        btnEmitente.setBackgroundResource(corparametrosemfoco);
                        btnImpressora.setBackgroundResource(corparametrosemfoco);
                        btnRevenda.setBackgroundResource(corparametrosemfoco);
                        btnSat.setBackgroundResource(corparametroselect);
                        listadeItensParam.clear();
                        for (int i = 0; i <= 5; i++) {
                            if (i == 0) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("AMBIENTE");
                                objParam.setIdparametro(15);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 1) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("ATIVAÇÃO");
                                objParam.setIdparametro(17);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 2) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("CAIXA");
                                objParam.setIdparametro(14);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 3) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("MODELO");
                                objParam.setIdparametro(18);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }
                            if (i == 4) {
                                modelParametros objParam = new modelParametros();
                                objParam.setNome("SERIE");
                                objParam.setIdparametro(6);
                                tvDescricao.setVisibility(INVISIBLE);
                                tvInfoDescr.setVisibility(INVISIBLE);
                                listadeItensParam.add(objParam);
                                itensparam.notifyDataSetChanged();
                            }

                        }
                    }
                });


            case id.btninformcpf:
                binformarCPF.setOnClickListener(new OnClickListener() {
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
            case id.etdatainicial:
                dtinicialcontador.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                        final int ano = calendar.get(Calendar.YEAR);
                        final int mes = calendar.get(Calendar.MONTH);
                        final int dia = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog date = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListenerContIni,
                                ano, mes, dia);
                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListenerContIni = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
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
            case id.etdatafinal:
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
                                dateSetListenerContFim,
                                ano, mes, 12);


                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListenerContFim = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
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

            case id.editDatainicial:
                dtinicialorcamento.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar calendar = new GregorianCalendar();
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                        final int ano = calendar.get(Calendar.YEAR);
                        final int mes = calendar.get(Calendar.MONTH);
                        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog date = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                dateSetListenerRelIni,
                                ano, mes, dia);
                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListenerRelIni = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
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
            case id.editDatafinal:
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
                                dateSetListenerRelFim,
                                ano, mes, 12);


                        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        date.show();
                    }
                });
                dateSetListenerRelFim = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
                        mes = mes + 1;
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
                tvDescricao.setVisibility(VISIBLE);
                tvInfoDescr.setVisibility(INVISIBLE);
                tvInfoDescr.setVisibility(VISIBLE);
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
                btnGravarAlt.setOnClickListener(new OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {


                                                        c.setValor(String.valueOf(tvInfo.getText()));
                                                        dadosOpenHelper.addalterparametro(c);
                                                        parametros();
                                                        layoutInflater = getLayoutInflater();
                                                        toastLayout = layoutInflater.inflate(layout.layouttoastparametro, (ViewGroup) findViewById(id.layouttoastparametro));
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
    private static void parametros() {

        Cursor revenda = (dadosOpenHelper.selecParametros());
        if (revenda.moveToFirst()) {

            for (int i = 0; i < revenda.getCount(); i++) {
                revenda.moveToPosition(i);
                if (revenda.getInt(0) == 32) {
                    objparam.setContadorEmail(revenda.getString(1));
                }
                if (revenda.getInt(0) == 2) {
                    objparam.setEmitenteRazaoSocial(removerAcento(revenda.getString(1)));
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
                    objparam.setEmitenteEndereco(removerAcento(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 9) {
                    objparam.setEmitenteNumero(revenda.getString(1));
                }
                if (revenda.getInt(0) == 10) {
                    objparam.setEmitenteComplemento(removerAcento(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 11) {
                    objparam.setEmitenteBairro(removerAcento(revenda.getString(1)));
                }
                if (revenda.getInt(0) == 12) {
                    objparam.setEmitenteMunicipio(removerAcento(revenda.getString(1)));
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
                if (revenda.getInt(0) == 27) {
                    objparam.setImpressoraModelo(revenda.getString(1));
                }
                if (revenda.getInt(0) == 28) {
                    objparam.setPortaSmtp(revenda.getString(1));
                }
                if (revenda.getInt(0) == 29) {
                    //  objparam.setImpressoraNome(revenda.getString(1));
                }
                if (revenda.getInt(0) == 35) {
                    objparam.setHoraReiniciar(revenda.getInt(1));
                }
                if (revenda.getInt(0) == 37) {
                    objparam.setNumeroVias(revenda.getString(1));
                    if (revenda.getInt(0) == 37) {
                        objparam.setNumeroVias(revenda.getString(1));
                        String nViasVendas[]={"1"};
                        String nViasCancel[] = {"1"};
                        String[] nViasOrcam= {"1"};

                        nViasVendas = objparam.getNumeroVias().split("venda=");
                        nViasVendas[1] = nViasVendas[1].replace(" ","");
                        nViasCancel = objparam.getNumeroVias().split("cancelamento=");
                        nViasCancel[1] = nViasCancel[1].replace(" ","");
                        nViasOrcam = objparam.getNumeroVias().split("orcamento=");
                        nViasOrcam[1] = nViasOrcam[1].replace(" ","");
                        try{
                            nViasImpVenda= Integer.parseInt(nViasVendas[1].substring(0,1));
                        }catch(NumberFormatException ex){
                            nViasImpVenda = 1;
                        }
                        try{
                           nViasImpCanc= Integer.parseInt(nViasCancel[1].substring(0,1));
                        }catch(NumberFormatException ex){
                            nViasImpCanc = 1;
                        }
                        try{
                            nViasImpOrc= Integer.parseInt(nViasOrcam[1].substring(0,1));
                        }catch(NumberFormatException ex){
                            nViasImpOrc = 1;
                        }
                    }
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

        if (id == R.id.action_orçamento|| id == R.id.action_orçamento2) {
            layoutCupom.setVisibility(INVISIBLE);
            layoutOrcamento.setVisibility(VISIBLE);
            return true;
        }
        if (id == R.id.action_venda || id == R.id.action_venda2 ) {

            layoutCupom.setVisibility(VISIBLE);
            layoutOrcamento.setVisibility(INVISIBLE);
            return true;
        }
        if ((id == R.id.cancelarVendaOrc) || (id == R.id.cancelarVenda)|| (id == R.id.cancelarVenda2))  {
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
            if (cursor.getCount() != 0) {
                cfeCancel = "Tem certeza que deseja cancelar a venda?" + "\n" + "\n" + "Cupom: " + String.format("%06d", cursor.getInt(22)) + "\n" + "Total: R$ " + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + "\n" + "CFE: " + cursor.getString(6) + "\n";
                //   "\"Tem certeza que deseja cancelar a venda?\" + \"\\n\" + \"\\n\" + \"Cupom: \" + String.format(\"%06d\", cursor.getInt(22)) + \"\\n\" + \"Total: R$ \" + converte.format(cursor.getDouble(11) - cursor.getDouble(10) + (cursor.getDouble(29))) + \"\\n\" + CFe" + cursor.getString(6);
            } else {
                cfeCancel = "Nenhuma Venda Realizada neste equipamento";
            }
            final String cfeCanc = cfeCancel;
            builder.setMessage(cfeCanc);
            //define um botão como positivo
            final AlertDialog.Builder sim = builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
               /* @TargetApi(Build.VERSION_CODES.O)
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onClick(DialogInterface arg0, int arg1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        objCupomFiscal.setChaveCfe(cursor.getString(6));
                        objCupomFiscal.setCoo(Integer.parseInt(cursor.getString(22)));
                        cancelarVenda(cursor);                    }
                }*/

                // @RequiresApi(api = Build.VERSION_CODES.O)
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onClick(DialogInterface arg0, int arg1) {
                    objCupomFiscal.setChaveCfe(cursor.getString(6));
                    objCupomFiscal.setCoo(Integer.parseInt(cursor.getString(22)));
                    cancelarVenda(cursor);
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
            if (cursor.getCount() == 0) {
                pbutton.setVisibility(INVISIBLE);
                nbutton.setText("Voltar");
            } else {
                pbutton.setVisibility(VISIBLE);
            }
            return true;
        }
        if ((id == R.id.reimprimircupomorc) || (id == R.id.reimprimircupom) || (id == R.id.reimprimircupom2)) {
            Intent intent = new Intent(getApplicationContext(), ReimprimirCupom.class);
            startActivity(intent);
            return true;
        }
        if ((id == R.id.efetivarOrc) || (id == R.id.efetivorcvenda2)) {
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
        String str = buscarProd.getText().toString();
        if (str.length()>0) {
            int indice = str.length()-1;
            buscarProd.setText(str.substring(0,indice));

            buscarProd.setSelection(indice);
        }
        btnClearPesqProd.setVisibility(view.VISIBLE);
    }
    public void buscarProduto(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
       TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button btnClearPesqProd;
        btnClearPesqProd = findViewById(id.btnClearPesqProd);

        String texto;
        texto = buscarProd.getText().toString();
       // final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
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

            objProdd.setDescricao(" ");
            listadeItensVendaProd.add(objProdd);

        }

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount() + 1; i++) {
                modelProdutos objProd = new modelProdutos();
                if (i == 0) {
                    objProd.setDescricao(" ");
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
    public void buscarProdutoScanner() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
       TextView contador = findViewById(id.textView10);
       imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button btnClearPesqProd;
       // btnClearPesqProd = findViewById(id.btnClearPesqProd);
        int tamanho = buscarProd.getText().toString().length() - 1;
        String texto;
        texto = buscarProd.getText().toString().substring(0,tamanho);
        buscarProd.setText("");
        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        itensVendaProd.clear();
        containerGridViewprod.setAdapter(itensVendaProd);
        SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();

        String rawQuery = "SELECT produto.idproduto, produto.descricao, categoria.cor, produto.preco, ncm.codigoncm, produto.origem, produto.csosn, " +
                "produto.aliqicms, produto.cstpis, produto.aliqpis, produto.cstcofins, produto.aliqcofins, produto.codcontribsocial, produto.cest, produto.cfop, " +
                " produto.precovariavel, produto.idunidade FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.status = 'A' and produto.codigoean LIKE '%" + texto + "%'";
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
        containerGridViewCateg.setVisibility(INVISIBLE);
    }

    public void venderProdutoScanner() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button btnClearPesqProd;
       //  btnClearPesqProd = findViewById(id.btnClearPesqProd);
       // int tamanho = buscarProd.getText();
        String texto;
        texto = buscarProd.getText().toString();
        buscarProd.setText("");
        //final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();
        itensVendaProd.clear();
        containerGridViewprod.setAdapter(itensVendaProd);
        SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();

        String rawQuery = "SELECT produto.idproduto, produto.descricao, categoria.cor, produto.preco, ncm.codigoncm, produto.origem, produto.csosn, " +
                "produto.aliqicms, produto.cstpis, produto.aliqpis, produto.cstcofins, produto.aliqcofins, produto.codcontribsocial, produto.cest, produto.cfop, " +
                " produto.precovariavel, produto.idunidade FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.status = 'A' and produto.codigoean LIKE '%" + texto + "%'";
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
                    objProd.setUndSigla("UN");
                    listadeItensVendaProd.add(objProd);
                    if (objProd.getPrecovariavel().equalsIgnoreCase("S")  ) {

                                if(Tecladonumerico.instance==null) {
                                    controleteclado = 7;
                                    itemposicaovariavel = objProd;
                                    Intent intent = new Intent(this, Tecladonumerico.class);
                                    ((Activity) this).startActivityForResult(intent, 2);
                                }

                            }
                    else
                    if (objProd.getIdunidade() == 2) {
                        objProd.setUndSigla("KG");
                        itemposicaovariavel = objProd;
                        controleteclado = 8;
                        Intent intents = new Intent(this, TecladoContribSocial.class);
                        ((Activity) this).startActivityForResult(intents, 3);

                    }
                    else
                    if (objProd.getIdunidade() == 3) {
                        objProd.setUndSigla("M");
                        controleteclado = 8;
                        Intent intents = new Intent(this, Tecladonumerico.class);
                        ((Activity) this).startActivityForResult(intents, 3);
                    }
                    else{
                        addprodvenda(objProd);
                    }


                }
            }
            cursor.close();
        }

        containerGridViewprod.setVisibility(VISIBLE);
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
        int resultCode = 0;
        int requestCode = 0;
        Intent data = null;
        super.onActivityResult(requestCode, resultCode, data);
       if (resultCode == RESULT_OK && requestCode == 1) {
           ValidarCPF_CNPJ valida = new ValidarCPF_CNPJ();
           String retornoteclado = data.getStringExtra("KEY");
           if (retornoteclado.length() == 13) {
               String parametrovalidador = retornoteclado.substring(0, 3) + retornoteclado.substring(3, 6) + retornoteclado.substring(6, 9) + retornoteclado.substring(11, 13);
               if (valida.validarCPF(parametrovalidador) == true) {
                   this.binformarCPF.setText("CPF: " + retornoteclado.substring(0, 10) + retornoteclado.substring(11, 13));
                   objdocumento.setCpfcnpj(parametrovalidador);

                   if (listadeItens.size() == 0) {
                       inicianovodocumento();
                   }
               }
           }
       }

        for (int i = 1; i <= listadeItens.size(); i++) {
            objCupom2 = new modelDocumentoProduto();
            objCupom2 = listadeItens.get(i - 1);
            if ((model.getIdproduto() == objCupom2.getIddoproduto()) && (model.getPreco() == objCupom2.getPreco())) {
                double quant = 0;
                quant = model.getQuantidade();
                objCupom = objCupom2;
                existe = true;
                objCupom.setQuantidade(objCupom.getQuantidade() + quant);
                objCupom.setTotalproduto(Double.valueOf(model.getPreco()) * objCupom.getQuantidade());
                objCupom.setTotaldesconto(objCupom.getTotaldesconto() + objCupom.getDescontounitario());
                objCupom.setTotalacrescimo(objCupom.getTotalacrescimo() + objCupom.getAcrescimounitario());
                objCupom.setTotalprodutocdesc((objCupom.getTotalprodutocdesc() + (objCupom.getPreco() * quant)) - objCupom.getDescontounitario() + objCupom.getAcrescimounitario());
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
            objCupom.setDescricao(removerAcento(model.getDescricao()));
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
        itensAdapter.notifyDataSetChanged();
        qtditensVenda.setText(String.valueOf(objdocumento.getTotalquantidade()));
        qtditensOrc.setText(String.valueOf(objdocumento.getTotalquantidade()));
        valorTotSemDescV.setText("R$ " + converte.format(objdocumento.getTotaldocumento()));
        valorTotSemDescO.setText("R$ " + converte.format(objdocumento.getTotaldocumento()));
        valorTotalOrc.setText("R$ " + converte.format(objdocumento.getTotaldocumentocdesc()));
        valorTotalVendas.setText("R$ " + converte.format(objdocumento.getTotaldocumentocdesc()));
        totDescontoOcr.setText("R$ " + converte.format(objdocumento.getTotaldesconto()));
        totDescontoV.setText("R$ " + converte.format(objdocumento.getTotaldesconto()));
        if (itensAdapter.getCount() == 0) {
            bFinalizaVenda.setBackgroundResource(semfoco);
            bFinalizaOrc.setBackgroundResource(semfoco);

        }
    }
    private void conectarBanc() {
        try {
            dadosOpenHelper = new DadosOpenHelper(this);
        } catch (SQLException ex) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
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
            conectImpressoras();

            if (itensAdapter.getCount() > 0) {
                if (objdocumento.getTotaldocumentocdesc() <= 10000) {
                    if (finalizarVendas.instance == null) {
                        Intent intent = new Intent(getApplication(), FinalizarVendas.class);
                        startActivity(intent);
                    }
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setCancelable(false);
                    builder.setTitle("                         Valor máximo excedido!");
                    builder.setMessage("     A Secretária da Fazenda definiu que na emissão de SAT CFe, o Valor Máximo permitido é de R$ 10.000,00 (dez mil reais).");
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
    public void lprod() {
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
                        startActivityForResult(intent, 6);
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
                        startActivityForResult(intent, 6);
                    }
                }
            });
        }

        AdapterProd adapterProd;
        adapterProd = new AdapterProd(this, listadeprod);
        listproduto.setAdapter(adapterProd);
    }
    private void lprodfiltraean() {
        ListView listproduto = findViewById(id.listproduto);
        ArrayList<modelProdutos> listadeprod;
        listadeprod = new ArrayList<>();
        String texto;
        texto = barraproduto.getText().toString();
        barraproduto.setText("");
        Cursor cursor = dadosOpenHelper.selecionarCadProdEan(texto);

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
                        startActivityForResult(intent, 6);
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
                        startActivityForResult(intent, 7);
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
                        startActivityForResult(intent, 7);
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
            lprodfiltra();
            clearprod.setVisibility(VISIBLE);

        }
        //
    }
    public void limpaprod(View view) {
        Button clearprod = findViewById(id.clearprod);
        barraproduto.setText(null);
        lprod();
        clearprod.setVisibility(INVISIBLE);
    }
    public void filtrandocateg(View view) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
        Button clearcateg = findViewById(id.clearcateg);
        String texto2;
        texto2 = barracategoria.getText().toString();
        if (texto2 != "") {
            lcategfiltra();
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
        fechamento.setVisibility(INVISIBLE);
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
        dataInicialAux = ano + "/" + mesString + "/" + diaString;
        Date datafinal = new Date();
        String dataformatadafinal = formataData.format(datafinal);
        dataInicialAux = ano + "-" + mesString + "-" + diaString;
        String data = diaString + "/" + mesString + "/" + ano;
        dtinicialorcamento.setText(data);
        dtfinalormaneto.setText(dataformatadafinal);


    }
    public void relatoriofechamento(View view) {
        auxtipoRelatorio = 1;
        fechamento.setVisibility(INVISIBLE);
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
        dataFinalAux = ano + "-" + mesString + "-" + diaauxString;
        Date datafinal = new Date();
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
                        valorTotalVendCanc += cursorQtdVendasCanc.getDouble(1) - cursorQtdVendasCanc.getDouble(2) + cursorQtdVendasCanc.getDouble(3);
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

    public void onClick(DialogInterface dialog, int which) {
        if (which == AlertDialog.BUTTON_POSITIVE) {

        }
        finish();
    }
    public void gerarRelatorio(View v) {

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
                        valor = cursor.getDouble(3) - cursor.getDouble(5) + cursor.getDouble(6);
                        cpf = cursor.getString(4);
                        modelOrcamento.setNumero(numero);
                        modelOrcamento.setCliente(cliente);
                        modelOrcamento.setQuantidade(cursor.getDouble(2));
                        modelOrcamento.setCpfcnpj(cpf);
                        modelOrcamento.setTotal(valor);
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
    public void novoproduto(View view) {

        if (CriarProd.instance == null) {
            Intent intent = new Intent(this, CriarProd.class);
            startActivityForResult(intent, 6);

        }
    }
    public void novacategoria(View view) {


        if (CriarCateg.instance == null) {
            Intent intent = new Intent(this, CriarCateg.class);
            startActivityForResult(intent, 7);
            CriarCateg criar = new CriarCateg();
        }
    }
    public void SelcVender(View view) {
        // impSweda();

        conectImpressoras();
        esconderTeclado(view);

        barracategoria.setText(null);
        barraproduto.setText(null);
        barraproduto.setFocusable(true);
        buscarProd.requestFocus();
        buscarProd.setCursorVisible(true);
        consultarCateg();
        VENDER.setBackgroundResource(selecionado);
        CADASTRO.setBackgroundResource(semfoco);
        RELATORIO.setBackgroundResource(semfoco);
        CONTADOR.setBackgroundResource(semfoco);
        CONFIGURACAO.setBackgroundResource(semfoco);
        SUPORTE.setBackgroundResource(semfoco);
        //  SAIR.setBackgroundResource(semfoco);
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
        dataInicialAux = ano + "-" + mesString + "-" + "01";
        Date data = new Date();
        data.setDate(1);
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
        tvTituloConfigurar.setVisibility(INVISIBLE);
       // tvserial.setVisibility(INVISIBLE);
        if (senhaConfiguracao.length()<1) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Informe a Senha de Acesso");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setMaxLines(1);
            input.setTransformationMethod(new PasswordTransformationMethod());
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    senhaConfiguracao = input.getText().toString();
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
                        tvTituloConfigurar.setVisibility(VISIBLE);
                        //tvserial.setVisibility(VISIBLE);
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

            android.app.AlertDialog alerta = builder.create();
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
        else{
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
            tvTituloConfigurar.setVisibility(VISIBLE);
            //tvserial.setVisibility(VISIBLE);
        }
    }
    public void SelcSuporte(View view) {
        if (objparam.getSatModelo().equalsIgnoreCase("2")||(objparam.getSatModelo().equalsIgnoreCase("11"))) {
            btnSuporteRemoto.setVisibility(VISIBLE);
        }

        else{
            btnSuporteRemoto.setVisibility(INVISIBLE);
        }
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


      /// final char[] CORTAR = new char[] { 0x1B, 'm' };

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
                    Process process = Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "poweroff"});
                    process.waitFor();
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
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void emailContador(View view) {
        VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
        esconderTeclado(view);

        final SQLiteDatabase d5 = dadosOpenHelper.getReadableDatabase();

        Cursor cursor = d5.rawQuery("SELECT documento.chave, documento.xml FROM documento WHERE documento.dthrcriacao between '" + dataInicialAux + "' and '" + dataFinalAux + "' and documento.operacao = 'CU'", null);
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
        if (cursor.moveToFirst() && cursor.getCount()>0) {
            try {
                compactararq compactarArq = new compactararq();
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
            enviaemail = new EnviarEmailThread(this, tvemailcontador.getText().toString(), "XMLs: " + objparam.getEmitenteRazaoSocial(), "Seguem os XMLs do período de: " +
                    diainicial + mesinicial + anoinicial + " até " + diafinal + mesfinal + anofinal + " anexados ao e-mail." + "\n\nRemarcaflex\n\n" +
                    "Mais que um simples Sistema de Emissão de Cupons Sat o Remarcaflex é uma Solução Completa pois fora as Obrigações Básicas também contempla uma Solução Administrativa para Cadastro e Controle de Dados fiscais. " +
                    "Econômica e de fácil uso para atender a legislação do Cupom Fiscal Eletrônico e para as Contabilidades a comodidade de receber automaticamente e com segurança os Arquivos Fiscais necessários para o fechamento contábil dos clientes. " +
                    "Todas estas facilidades para os clientes e contabilidades estarão suportadas pela nossa equipe de Suporte Técnico. O Remarcaflex cuida de suas Obrigações Fiscais dando mais tempo para o Comerciante cuidar do que mais importa que é seu negócio.\n" +
                    "Para mais informações acesse: " + "https://mailchi.mp/fb7f18e56d55/sat-flex");

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



    }
    public void addNcm(String ncm) {
        try {
            dadosOpenHelper.inserirNcm(ncm);
        } catch (Exception e) {

        }
    }
    public void inserNcm(View view) {
        incertNcm = new InsertNcmThread(this);
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
    public void ConfigMenuAnterior(View view) {

        layoutConfiguraparam.setVisibility(INVISIBLE);
        layoutStatusSistema.setVisibility(INVISIBLE);
        btnParametros.setVisibility(INVISIBLE);
        btnStatusSistema.setVisibility(INVISIBLE);
        tvTituloConfigurar.setVisibility(INVISIBLE);
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
        tvTituloConfigurar.setVisibility(VISIBLE);
    }

    public void extrairLogs(View view) {
        ConstraintLayout layoutConsultarStatusOper;
        layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
        layoutConsultarStatusOper.setVisibility(INVISIBLE);
        Button btninserirncm = (Button)findViewById(R.id.btninserirncm);
        Button btnatualizar = (Button)findViewById(R.id.btnatualizar);
        Button btnbaixararquivos = (Button)findViewById(R.id.btnbaixararquivos);
        Button btnbaixarbanco = (Button)findViewById(R.id.btnbaixarbanco);
        Button btndesenv2 = (Button)findViewById(R.id.btndesenv2);
        Button btndesenv3 = (Button)findViewById(R.id.btndesenv3);
        btninserirncm.setVisibility(VISIBLE);
        btnatualizar.setVisibility(VISIBLE);
        btnbaixararquivos.setVisibility(VISIBLE);
        btnbaixarbanco.setVisibility(VISIBLE);
        btndesenv2.setVisibility(VISIBLE);
        btndesenv3.setVisibility(VISIBLE);
        extrairlogs = new ExtrairLogsThread(this);
        extrairlogs.execute();


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void extrairLogss() {
        statusSat();
        extrairLogsSatResp =0;
        String consultarsttusat = "";
        if (statuSat == 1) {
            try {
                if (objparam.getSatModelo().equalsIgnoreCase("2")) {
                    consultarsttusat = getSAT().extrairLogs(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao());
                    sweda = new SATsweda(getApplicationContext());
                }
                if (objparam.getSatModelo().equalsIgnoreCase("3")) {
                    if (SATiD.getInstance().isConnected()) {
                        consultarsttusat = SATiD.getInstance().ExtrairLogs(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao());

                    }

                }
                if (objparam.getSatModelo().equalsIgnoreCase("4")) {
                    consultarsttusat = dimepDSAT.ExtrairLogs(UtilidadesGerais.gerarNumeroSessao(), objparam.getSatCodAtivacao());
                }
                String[] separa = consultarsttusat.split("\\|");
                String logsSat = UtilidadesGerais.converterBase64(separa[5]);
                FileOutputStream fos = new FileOutputStream(diretorioTemp + "/" + "LogSat" + ".txt");
                fos.write(logsSat.getBytes());
                fos.close();
                extrairLogsSatResp = 1;
            } catch (IOException e) {
                e.printStackTrace();
                extrairLogsSatResp = 0;
            }
        }
        else{
            extrairLogsSatResp = 0;
        }
    }
    public void pausa() {
        try {
            TimeUnit.SECONDS.sleep(3);

        } catch (InterruptedException ex) {
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void consultarStatusOperacional(View view) {
        Button btninserirncm = (Button)findViewById(R.id.btninserirncm);
        Button btnatualizar = (Button)findViewById(R.id.btnatualizar);
        Button btnbaixararquivos = (Button)findViewById(R.id.btnbaixararquivos);
        Button btnbaixarbanco = (Button)findViewById(R.id.btnbaixarbanco);
        Button btndesenv2 = (Button)findViewById(R.id.btndesenv2);
        Button btndesenv3 = (Button)findViewById(R.id.btndesenv3);
        btninserirncm.setVisibility(INVISIBLE);
        btnatualizar.setVisibility(INVISIBLE);
        btnbaixararquivos.setVisibility(INVISIBLE);
        btnbaixarbanco.setVisibility(INVISIBLE);
        btndesenv2.setVisibility(INVISIBLE);
        btndesenv3.setVisibility(INVISIBLE);
        String consultarsttusat = "";
        int cooIni = 0;
        int cooFim = 0;
        int cooPendente = 0;
        statusSat();
        pausa();
        Random random = new Random();
        int number = 0;
        String numeroSessao = "123456";
        number = random.nextInt(999999);
        numeroSessao = (String.format("%06d", number));
        StringBuilder statusSatStatusoper;
        statusSatStatusoper = new StringBuilder();
        ConstraintLayout layoutConsultarStatusOper;
        layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
        TextView tvConsultarStatusOperac = findViewById(R.id.tvconsultarstatusoperac);
        tvConsultarStatusOperac.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        layoutConsultarStatusOper.setVisibility(VISIBLE);

        statuSat =1;
        if (statuSat == 1) {
            try {
                if (objparam.getSatModelo().equalsIgnoreCase("2")) {
                    consultarsttusat = getSAT().consultarStatusOperacional(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao());
                    sweda = new SATsweda(getApplicationContext());
                }
                if (objparam.getSatModelo().equalsIgnoreCase("3") && SATiD.getInstance().isConnected()) {
                    consultarsttusat = SATiD.getInstance().ConsultarStatusOperacional(Integer.parseInt(numeroSessao), objparam.getSatCodAtivacao());
                }
                String[] separa = consultarsttusat.split("\\|");
                cooIni = Integer.parseInt(separa[21].substring(31, 37));
                cooFim = Integer.parseInt(separa[22].substring(31, 37));

                if (cooFim != 0) {
                    cooPendente = cooFim - cooIni + 1;

                } else {
                    cooPendente = 0;
                }


                statusSatStatusoper.append("          CONSULTAR STATUS OPERACIONAL" + "\n\n");
                statusSatStatusoper.append(separa[2]);
                statusSatStatusoper.append("\nNumero de Série: " + separa[5] + "\nTipo da LAN: " + separa[6] + "\nIP: " + separa[7] + "\nMac Adreass: " + separa[8]);
                statusSatStatusoper.append("\nMascara de Rede: " + separa[9] + "\nGateway: " + separa[10] + "\nDNS1: " + separa[11] + "\nDNS2: " + separa[12] + "\nStatus Rede: " + separa[13]);
                statusSatStatusoper.append("\nNivel da Bateria: " + separa[14] + "\nMemória Total: " + separa[15] + "\nMemória Usada: " + separa[16] + "\nData e Hora Atual: " + separa[17].

                        substring(6, 8) + "/" + separa[17].

                        substring(4, 6) + "/" + separa[17].

                        substring(0, 4) + " " + separa[17].

                        substring(8, 10) + ":" + separa[17].

                        substring(10, 12) + ":" + separa[17].

                        substring(12, 14) + "\nVersão Software Básico: " + separa[18]);
                statusSatStatusoper.append("\nVersão Layout: " + separa[19] + "\nUltimo CFE Emitido: " + separa[20].

                        substring(0, 28) + "\n" + separa[20].

                        substring(28, 44) + "\nCFE Inicial: " + separa[21].

                        substring(0, 28) + "\n" + separa[21].

                        substring(28, 44) + "\nCfe Final: " + separa[22].

                        substring(0, 28) + "\n" + separa[22].

                        substring(28, 44));
                statusSatStatusoper.append("\nCupons Pendentes: " + cooPendente);
                statusSatStatusoper.append("\nUltima Comunicação Sefaz: " + separa[24].

                        substring(6, 8) + "/" + separa[24].

                        substring(4, 6) + "/" + separa[24].

                        substring(0, 4) + " " + separa[24].

                        substring(8, 10) + ":" + separa[24].

                        substring(10, 12) + ":" + separa[24].

                        substring(12, 14) + "\n");
                tvConsultarStatusOperac.setText(statusSatStatusoper);


            } catch (Exception e) {
                statusSatStatusoper.append("Não foi possivel localizar o sat"+ e.getMessage()+e.getCause());
                tvConsultarStatusOperac.clearComposingText();
                tvConsultarStatusOperac.setText(statusSatStatusoper);
                sweda = new SATsweda(getApplicationContext());
                // sweda.desativarEthSat();
            }
        }
        else{
            statusSatStatusoper.append("Não foi possivel localizar o s@t");
            tvConsultarStatusOperac.clearComposingText();
            tvConsultarStatusOperac.setText(statusSatStatusoper);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    public void parametrosSistema(View view){
        layoutConfiguraparam.setVisibility(VISIBLE);
        layoutStatusSistema.setVisibility(INVISIBLE);
        btnParametros.setVisibility(INVISIBLE);
        btnStatusSistema.setVisibility(INVISIBLE);
        tvTituloConfigurar.setVisibility(INVISIBLE);
    }
    public void statusSistema(View view){
        Button btnConsultarStatusOper = findViewById(id.btnConsultarStatusOper);
        Button btnFecharAplicacao = findViewById(id.btnFecharAplicacao);
        Button btninserirncm = (Button)findViewById(R.id.btninserirncm);
        Button btnatualizar = (Button)findViewById(R.id.btnatualizar);
        Button btnbaixararquivos = (Button)findViewById(R.id.btnbaixararquivos);
        Button btnbaixarbanco = (Button)findViewById(R.id.btnbaixarbanco);
        Button btndesenv2 = (Button)findViewById(R.id.btndesenv2);
        Button btndesenv3 = (Button)findViewById(R.id.btndesenv3);
        btninserirncm.setVisibility(VISIBLE);
        btnatualizar.setVisibility(VISIBLE);
        btnbaixararquivos.setVisibility(VISIBLE);
        btnbaixarbanco.setVisibility(VISIBLE);
        btndesenv2.setVisibility(VISIBLE);
        btndesenv3.setVisibility(VISIBLE);
        btnConsultarStatusOper.setVisibility(VISIBLE);
        layoutConfiguraparam.setVisibility(INVISIBLE);
        layoutStatusSistema.setVisibility(VISIBLE);
        btnParametros.setVisibility(INVISIBLE);
        btnStatusSistema.setVisibility(INVISIBLE);
        tvTituloConfigurar.setVisibility(INVISIBLE);
    }
    public void fecharConsultaStatusOper(View view){
        ConstraintLayout layoutConsultarStatusOper;
        layoutConsultarStatusOper = findViewById(R.id.layconsultarstatusoper);
        layoutConsultarStatusOper.setVisibility(INVISIBLE);
        Button btninserirncm = (Button)findViewById(R.id.btninserirncm);
        Button btnatualizar = (Button)findViewById(R.id.btnatualizar);
        Button btnbaixararquivos = (Button)findViewById(R.id.btnbaixararquivos);
        Button btnbaixarbanco = (Button)findViewById(R.id.btnbaixarbanco);
        Button btndesenv2 = (Button)findViewById(R.id.btndesenv2);
        Button btndesenv3 = (Button)findViewById(R.id.btndesenv3);
        btninserirncm.setVisibility(VISIBLE);
        btnatualizar.setVisibility(VISIBLE);
        btnbaixararquivos.setVisibility(VISIBLE);
        btnbaixarbanco.setVisibility(VISIBLE);
        btndesenv2.setVisibility(VISIBLE);
        btndesenv3.setVisibility(VISIBLE);
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
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c","reboot now"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Fechar Aplicação", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                SAIR.setBackgroundResource(semfoco);
                finishAffinity();
                // finish();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
        Button nbutton = alerta.getButton(DialogInterface.BUTTON_NEGATIVE);
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Color.RED);
        gdDefault.setCornerRadius(4);
        gdDefault.setStroke(3, Color.parseColor("#000301"));
        nbutton.setBackground(gdDefault);
        nbutton.setTextSize(14);
        nbutton.setScaleY(1);
        nbutton.setScaleX(1);
        nbutton.setX(100);
        nbutton.setTextColor(Color.WHITE);
        Button pbutton = alerta.getButton(DialogInterface.BUTTON_POSITIVE);
        GradientDrawable gdDefault1 = new GradientDrawable();
        gdDefault1.setColor(Color.GREEN);
        gdDefault1.setCornerRadius(4);
        gdDefault1.setStroke(3, Color.parseColor("#000301"));
        pbutton.setBackground(gdDefault1);
        pbutton.setTextSize(14);
        pbutton.setScaleY(1);
        pbutton.setScaleX(1);
        pbutton.setX(-440);
        pbutton.setTextColor(Color.WHITE);
    }
    public void excluirOrcamento(View view){
    }
    private static String removerAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    public void esconderTeclado(View view){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView contador = findViewById(id.textView10);
        imm.hideSoftInputFromWindow(contador.getWindowToken(), 0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    protected void bloqueioFirebase() {
        statusFirebaseDatabase = firebaseDatabaseRef.child(objparam.getEmitenteCNPJ()).child("status");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        statusFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                if (value != null) {

                    if (value.equalsIgnoreCase("1")) {
                        String alterarStatus = "0002";
                        dadosOpenHelper.addStatus(alterarStatus);
                        builder.setCancelable(false);
                        builder.setTitle("              TERMINAL BLOQUEADO!");

                        builder.setMessage("           Entre em contato com o suporte\n" + "pelo número " + objparam.getRevendaTelefone() + "\n"+"CNPJ: " +objparam.getEmitenteCNPJ()+ "\n");
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
                    }else
                    {
                        if (value.equalsIgnoreCase("0")){
                            String alterarStatus = "0000";
                            dadosOpenHelper.addStatus(alterarStatus);
                        }
                    }
                } else {
                    builder.setCancelable(false);
                    builder.setTitle("             LICENÇA INVÁLIDA!");
                    builder.setMessage("           Entre em contato com o suporte\n" + "pelo número " + objparam.getRevendaTelefone() + "\n"+"CNPJ: " +objparam.getEmitenteCNPJ()+ "\n");
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void bloqueioFaltaComunic() {
        int dataSistema = 0;
        dataSistema = LocalDate.now().getDayOfYear();
        int dtComunic = objparam.getDtVerificacao();
        String alterarStatus;
        VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
        if (verificarInternetStatus.executeCommand() == true && objparam.getStatus().equalsIgnoreCase("0002") ==false ) {

            alterarStatus = "0000";
            dadosOpenHelper.addStatus(alterarStatus);
            dadosOpenHelper.adddtVerificacao(String.valueOf(dataSistema));
        }

        int diferenca = dataSistema - dtComunic;


        if (diferenca >= 10 && objparam.getStatus().equalsIgnoreCase("0002") == false) {
            alterarStatus = "0001";
            dadosOpenHelper.addStatus(alterarStatus);
        }
    }
    public void verificarBloqueio(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (objparam.getStatus().equalsIgnoreCase("0001")) {

            builder.setCancelable(false);
            builder.setTitle("TERMINAL BLOQUEADO\n" + "FALTA DE COMUNICAÇÃO COM A INTERNET !");
            builder.setMessage("           Entre em contato com o suporte\n" + "pelo número " + objparam.getRevendaTelefone() + "\n");
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
        if (objparam.getStatus().equalsIgnoreCase("0002")) {

            builder.setCancelable(false);
            builder.setTitle("TERMINAL BLOQUEADO\n" + "Ligue Suporte !");
            builder.setMessage("           Entre em contato com o suporte\n" + "pelo número " + objparam.getRevendaTelefone() + "\n");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void habilitarSuporteRemot(View view) {
        btnSuporteRemoto.setText("AGUARDE!...");
        getSAT().desativarEthSat();

        Intent iLaunch = getPackageManager().getLaunchIntentForPackage("com.anydesk.anydeskandroid");
       dadosOpenHelper.addSuporte(10);
        if (iLaunch != null) {
            startActivity(iLaunch);
        }
    }
    @Override
    public void onAttachedToWindow() {
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    public void appPermissaoImpSweda (View view) {

            try {
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "pm install -r sdcard/satflex/temp/appsweda.apk"});
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        buscarProd.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        ((keyCode == 160) || (keyCode == 66))) {
                    venderProdutoScanner();
                    Handler mHandler= new Handler();
                    mHandler.post(
                            new Runnable()
                            {
                                public void run()
                                {
                                    buscarProd.requestFocus();
                                }
                            }
                    );
                    return true;
                }
                return false;
            }
        });

        barraproduto.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        ((keyCode == 160) || (keyCode == 66))) {

                    lprodfiltraean();
                    return true;
                }
                return false;
            }
        });
        return false;
    }
    private void forcaRebootEquip(){
        Timer timer = null; if (timer == null) { timer = new Timer();

            TimerTask tarefa = new TimerTask() { public void run() {
                try {
                    final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

                    if (calendar.getTime().getHours() == objparam.getHoraReiniciar()){
                        try {
                            Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c","reboot now"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e) { e.printStackTrace(); } } };
            timer.scheduleAtFixedRate(tarefa, 1000 * 60 *60  , 1000 * 60 * 60);
        }
    }
}














