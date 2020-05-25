package util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.database.DadosOpenHelper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class IncertNcm extends AsyncTask<Void,Void,Void> {
    MainActivity mainActivity = new MainActivity();
    private Context context;
    private ProgressDialog progressDialog;
    private String message;
    private AlertDialog alerta;
    StringBuffer myData;
    private static File diretorioTemp;
    private   DadosOpenHelper dadosOpenHelper;
    //dadosOpenHelper = new DadosOpenHelper(this);
   // private ProgressDialog progressDialog;
    public IncertNcm(Context context){
        //Initializing variables
        this.context = context;

    }
    public void conectarBanco(){
        try {

            dadosOpenHelper = new DadosOpenHelper(mainActivity.getApplicationContext());
            //conexao = dadosOpenHelper.getReadableDatabase();

        }catch (SQLException ex){
          //  AlertDialog.Builder dlg = new AlertDialog.Builder (this);
          //  dlg.setTitle("Erro");
          //  dlg.setMessage(ex.getMessage());
          //  dlg.setNeutralButton("OK",null);
           // dlg.show();
        }
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Atualização da tabela NCM","Aguarde por favor...",true,true);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atualizado com sucesso");
        builder.setMessage("A atualização da tabela NCM foi atualizada com sucesso");
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

    @Override
    protected Void doInBackground(Void... voids) {

//dadosOpenHelper = new DadosOpenHelper(mainActivity.getApplicationContext());
       //mainActivity.VENDER.setText("aa");
        diretorioTemp = new File(Environment.getExternalStorageDirectory() + "/satflex/temp/");
       // FileInputStream fis;
        //fis = openFileInput("test.txt");
      //  StringBuffer fileContent = new StringBuffer("");

       // byte[] buffer = new byte[1024];

        //while ((n = fis.read(buffer)) != -1)
       // {
          //  fileContent.append(new String(buffer, 0, n));
       // }
        try {
//            SQLiteDatabase db = dadosOpenHelper.getReadableDatabase();
                FileInputStream fis = new FileInputStream( diretorioTemp + "/" + "ncm" + ".txt");
                DataInputStream in = new DataInputStream(fis);
                BufferedReader br =
                        new BufferedReader(new InputStreamReader(in));
                String strLine;

                while ((strLine = br.readLine()) != null) {
                   // myData.append(strLine);
                mainActivity.addNcm(strLine);

                }
                in.close();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();

        }



     // mainActivity.VENDER.setText(myData);

        return null;
    }

}
