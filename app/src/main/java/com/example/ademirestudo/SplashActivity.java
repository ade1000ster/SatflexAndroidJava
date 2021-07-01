package com.example.ademirestudo;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ademirestudo.database.DadosOpenHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.ademirestudo.MainActivity.objparam;

public class SplashActivity extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();
    VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
    int tempo = 0;
    private static DadosOpenHelper dadosOpenHelper;
    public int counter = 19000;
    private Button btnConRemota;
    private Button btnsuporteremot;
    private TextView tvCronometro;
    private TextView tvpublicoalvo;
    private ImageView ivSplash;
    private ImageView ivlogo;
    private ImageView ivPublicoAlvo;
    private Bitmap bitmapLogo1;
    private Bitmap[] bitmapSplash;
    private File logo1;
    private File[] splash;
    private int i = 4;
    private static File diretorioTempImg;
    private String[] publicoAlvo;
    private int primeiraVenda;
    private String versaoAtual;
    private String versaoDisponivel;
    private int codigoVersaoAtual;
    private int codigoVersaoDisponivel;
    private static File diretorioAtualiza;
    StorageReference firebaseStorageRef = FirebaseStorage.getInstance().getReference();
    private static DatabaseReference firebaseDatabaseRef = FirebaseDatabase.getInstance().getReference();
    private String dataFormatada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MainActivity mainActivity = new MainActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btnConRemota = (Button)findViewById(R.id.btnconremota) ;
        btnsuporteremot = (Button)findViewById(R.id.btnsuporteremot) ;
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        dataFormatada = formataData.format(data);
        conectarBanc();
        parametros();
      //  identificarEth();

        mainActivity.criarDiretorios();

        if(objparam.getSatModelo().equalsIgnoreCase("2")||objparam.getSatModelo().equalsIgnoreCase("10")) {
            btnsuporteremot.setVisibility(VISIBLE);

        }
        else{
            btnsuporteremot.setVisibility(INVISIBLE);
        }
        if(objparam.getSatModelo().equalsIgnoreCase("10")) {
            btnConRemota.setVisibility(VISIBLE);

        }
        if(objparam.getImpressoraModelo().equalsIgnoreCase("2")) {
            detectarImpSweda();
        }

        diretorioAtualiza = new File(Environment.getExternalStorageDirectory() + "/satflex/atualizacaogeral");
        if (!diretorioAtualiza.exists()) {
            diretorioAtualiza.mkdirs();
        }
        ivSplash = (ImageView) findViewById(R.id.ivsplash);
        ivlogo = (ImageView) findViewById(R.id.ivlogo);
        ivPublicoAlvo = (ImageView) findViewById(R.id.ivpublicoalvo);
        logo1 = new File("/sdcard/satflex/temp/img/logo.JPG");
        splash = new File[25];
        bitmapSplash = new Bitmap[25];
        publicoAlvo = new String[27];
       // desativarEthSat();
        compactarBanco();
        checaBackup();
        legendaSplash();
        carregarSplashImg();
        tvCronometro = findViewById(R.id.tvcronometro);
        tvpublicoalvo = findViewById(R.id.tvpublicoalvo);
        tempo = 19000;
        firebaseStorageRef = FirebaseStorage.getInstance().getReference();
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }

        }, tempo);

        new CountDownTimer(counter, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                tvCronometro.setText(String.valueOf(counter / 1000));
                if (counter == 10000) {
                    ivSplash.setImageBitmap(bitmapSplash[2]);
                }
                if (counter == 5000) {
                    ivSplash.setImageBitmap(bitmapSplash[3]);
                }
                if (i >= 4) {
                    ivPublicoAlvo.setImageBitmap(bitmapSplash[i]);
                    tvpublicoalvo.setText(publicoAlvo[i]);
                }
                i = i + 1;
                counter = counter - 1000;
            }

            @Override
            public void onFinish() {
                tvCronometro.setText("Iniciando RemarcaFlex");

            }
        }.start();


    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(
                SplashActivity.this, MainActivity.class
        );
        startActivity(intent);
        finish();
    }

    public void carregarSplashImg() {
        for (int i = 0; i < 25; i++) {
            splash[i] = new File("/sdcard/satflex/temp/img/splash" + i + ".JPG");
            if (splash[i].exists()) {

                bitmapSplash[i] = BitmapFactory.decodeFile(splash[i].getAbsolutePath());

                ivSplash.setImageBitmap(bitmapSplash[1]);

            }
        }


        if (logo1.exists()) {

            bitmapLogo1 = BitmapFactory.decodeFile(logo1.getAbsolutePath());

            ivlogo.setImageBitmap(bitmapLogo1);

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
    public void checaBackup(){
        String dataBackup1;
        String dataBackup2;
        String checaBackup = objparam.getChecarBackup();
        VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
        if (verificarInternetStatus.executeCommand() == true) {
            if (objparam.getDataBackup3().equalsIgnoreCase(dataFormatada) == false) {
                checaBackup = "0";
                dadosOpenHelper.checarBackup(checaBackup);
                parametros();
            }

            if (objparam.getChecarBackup().equalsIgnoreCase("0") == true) {
                dataBackup1 = objparam.getDataBackup2();
                dataBackup2 = objparam.getDataBackup3();
                dadosOpenHelper.dataBackup1(dataBackup1);
                dadosOpenHelper.dataBackup2(dataBackup2);
                backupBanco();
            }
        }
    }
    public void backupBanco() {
        String dataBackup3;
        final String[] checaBackup = {objparam.getChecarBackup()};
           dataBackup3 = dataFormatada;
           dadosOpenHelper.dataBackup3(dataBackup3);

           Uri file = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + "/satflex/banco/satflex.zip");
           StorageReference bkpBanco = firebaseStorageRef.child("Bkbanco/" + objparam.getEmitenteRazaoSocial() + "/" + objparam.getSatCaixa() + "/Bkp" + "_" + objparam.getEmitenteCNPJ() + " " + dataFormatada);
           bkpBanco.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   checaBackup[0] = "1";
                   dadosOpenHelper.checarBackup(checaBackup[0]);
               }
           });
           apagaBackup();

    }
    public void apagaBackup() {
        String nomecliente = objparam.getEmitenteRazaoSocial();
        String dataBackup1 = objparam.getDataBackup1();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://satflexandroid-4cfcf.appspot.com/");
        StorageReference desertRef = storageRef.child("Bkbanco/"+objparam.getEmitenteRazaoSocial() + "/" + objparam.getSatCaixa() + "/Bkp_" + objparam.getEmitenteCNPJ() + " " + dataBackup1);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }
    public void pausa() {
        try {
            TimeUnit.SECONDS.sleep(5);

        } catch (InterruptedException ex) {
            // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void desativarEthSat() {
        if (objparam.getSatModelo().equalsIgnoreCase("2")) {
            if (objparam.getEthLocal().equalsIgnoreCase("eth0")) {
                try {
                    //Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig wlan0 up"});
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 down"});

                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (objparam.getEthLocal().equalsIgnoreCase("eth1")) {
                    try {
                        // Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig wlan0 up"});
                        Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});
                        Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 down"});

                    } catch (
                            IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void detectarImpSweda(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[]{"/system/bin/su", "-c", "lsusb"});
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            byte[] content = new byte[inputStream.available()];
            inputStream.read(content);
            String teste = new String(content);
            if (teste.contains("0483")||teste.contains("1c8a")){
                if (teste.contains("1c8a")) {
                    mainActivity.impSweda300 = true;
                }
                Intent iLaunch = getPackageManager().getLaunchIntentForPackage("com.example.impsweda");
                if (iLaunch != null) {
                    startActivity(iLaunch);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void identificarEth() {
        if (objparam.getSatModelo().equalsIgnoreCase("2")) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});
                Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});
                Process process = runtime.exec(new String[]{"/system/bin/su", "-c", "/system/bin/ifconfig"});
                process.waitFor();
                InputStream inputStream = process.getInputStream();
                byte[] content = new byte[inputStream.available()];
                inputStream.read(content);
                String teste = new String(content);
                int retornoEth0 = teste.indexOf("eth0");
                int retornoEth1 = teste.indexOf("eth1");
                int posicaoInicialEth0 = retornoEth0 + 62;
                int posicaoFinalEth0 = posicaoInicialEth0 + 4;
                int posicaoInicialEth1 = retornoEth1 + 62;
                int posicaoFinalEth1 = posicaoInicialEth1 + 4;
                int posicaoInicialIpEth1 = retornoEth1 + 91;
                int posicaofinalIpEth1 = posicaoInicialIpEth1 + 12;
                int posicaoInicialIpEth0 = retornoEth0 + 91;
                int posicaofinalIpEth0 = posicaoInicialIpEth0 + 12;
                if (teste.substring(posicaoInicialEth0, posicaoFinalEth0).equalsIgnoreCase("r dm") && retornoEth0 != -1) {
                    objparam.setEthLocal("eth0");
                    objparam.setEthSat("eth1");
                    objparam.setIpLocal(teste.substring(posicaoInicialIpEth0, posicaofinalIpEth0));


                } else {
                    if (teste.substring(posicaoInicialEth0, posicaoFinalEth0).equalsIgnoreCase("r cd") && retornoEth0 != -1) {
                        objparam.setEthSat("eth0");
                        objparam.setEthLocal("eth1");

                    }
                }
                if (teste.substring(posicaoInicialEth1, posicaoFinalEth1).equalsIgnoreCase("r dm") && retornoEth1 != -1) {
                    objparam.setEthLocal("eth1");
                    objparam.setEthSat("eth0");
                    objparam.setIpLocal(teste.substring(posicaoInicialIpEth1, posicaofinalIpEth1));

                } else {
                    if (teste.substring(posicaoInicialEth1, posicaoFinalEth1).equalsIgnoreCase("r cd") && retornoEth1 != -1) {
                        objparam.setEthSat("eth1");
                        objparam.setEthLocal("eth0");


                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private static String removerAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    private void parametros() {

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
                if (revenda.getInt(0) == 29) {
                    //  mainActivity.objparam.setImpressoraNome(revenda.getString(1));
                }
                if (revenda.getInt(0) == 35) {
                     mainActivity.objparam.setHoraReiniciar(revenda.getInt(1));
                }
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
                        mainActivity.nViasImpVenda= Integer.parseInt(nViasVendas[1].substring(0,1));
                    }catch(NumberFormatException ex){
                    mainActivity.nViasImpVenda = 1;
                    }
                    try{
                        mainActivity.nViasImpCanc= Integer.parseInt(nViasCancel[1].substring(0,1));
                    }catch(NumberFormatException ex){
                        mainActivity.nViasImpCanc = 1;
                    }
                    try{
                        mainActivity.nViasImpOrc= Integer.parseInt(nViasOrcam[1].substring(0,1));
                    }catch(NumberFormatException ex){
                        mainActivity.nViasImpOrc = 1;
                    }
                }


                if (revenda.getInt(0) == 41) {
                    objparam.setImpressoraConfirm(revenda.getString(1));
                }

                if (revenda.getInt(0) == 40) {
                    objparam.setDtVerificacao(revenda.getInt(1));
                }

                if (revenda.getInt(0) == 39) {
                    objparam.setStatus(revenda.getString(1));
                }

                if (revenda.getInt(0) == 47) {
                    objparam.setCodversao(revenda.getInt(1));
                }
                if (revenda.getInt(0) == 49) {
                    objparam.setSenhaConfiguracao(revenda.getString(1));
                }

                if (revenda.getInt(0) == 53) {
                    objparam.setDataBackup1(revenda.getString(1));
                }
                if (revenda.getInt(0) == 54) {
                    objparam.setDataBackup2(revenda.getString(1));
                }
                if (revenda.getInt(0) == 55) {
                    objparam.setDataBackup3(revenda.getString(1));
                }
                if (revenda.getInt(0) == 58) {
                    objparam.setChecarBackup(revenda.getString(1));
                }

            }
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
    public void legendaSplash() {
        diretorioTempImg = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/img/");
        try {
            FileInputStream fis = new FileInputStream(diretorioTempImg + "/" + "legendasplash" + ".txt");
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));
            String strLine;
            int i = 1;
            while ((strLine = br.readLine()) != null) {
                publicoAlvo[i] = (strLine);
                i++;
            }
            in.close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void habilitarSuporteRemot(View view) {
        btnsuporteremot.setText("AGUARDE!...");
        desativarEthSat();
        objparam.setSatModelo("11");
        dadosOpenHelper.addSuporte(10);
        Intent iLaunch = getPackageManager().getLaunchIntentForPackage("com.anydesk.anydeskandroid");

        if (iLaunch != null) {
            startActivity(iLaunch);
        }

    }
    public void conexRemota (View view){
        objparam.setSatModelo("11");
        btnConRemota.setVisibility(INVISIBLE);
    }
}


