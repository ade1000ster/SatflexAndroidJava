package com.example.ademirestudo.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import com.example.ademirestudo.Config;
import com.example.ademirestudo.MainActivity;
import com.example.ademirestudo.VerificarInternetStatus;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EnviarEmailThread extends AsyncTask<Void,Void,Void> {
    VerificarInternetStatus verificarInternetStatus = new VerificarInternetStatus();
 int envioEmail=0;


    MainActivity mainActivity = new MainActivity();
        //Declaring Variables
        private Context context;
        private Session session;
        private File pastaSatflex;
        //Information to send email
        private String email;
        private String subject;
        private String message;
        private AlertDialog alerta;

        //Progressdialog to show while sending email
        //private ProgressDialog progressDialog;
        private ProgressDialog progressDialog;

        //Class Constructor
        public EnviarEmailThread(Context context, String email, String subject, String message){
            //Initializing variables
            this.context = context;
            this.email = email;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           progressDialog = ProgressDialog.show(context,"Enviando E-mail para contador","Aguarde por favor...",true,true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Dismissing the progress dialog
            progressDialog.dismiss();

            //Showing a success message
            if (envioEmail ==1) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enviado com sucesso");
                builder.setMessage("O e-mail contendo os XMLs no período especificado foi\n foi enviado com sucesso para o e-mail do contador");
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
            if (envioEmail ==0) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Falha ao enviar e-mail");
                builder.setMessage("Verifique sua conexão com a internet.");
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

        @Override
        protected Void doInBackground(Void... params) {
            //Creating properties
            desativarEthSat();

            if (verificarInternetStatus.executeCommand() == true) {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.remarca-automacao.com.br");
                props.put("mail.smtp.socketFactory.port", "587");
                props.put("mail.smtp.socketFactory.class", "false");//javax.net.ssl.SSLSocketFactory
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "587");
              /*  props.put("mail.smtp.host", "smtp.gmail.com");
                //props.put("mail.smtp.socketFactory.port", "587");
               props.put("mail.smtp.socketFactory.port", mainActivity.objparam.getPortaSmtp());
             //  props.put("mail.smtp.socketFactory.class", "false");//javax.net.ssl.SSLSocketFactory
               props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", mainActivity.objparam.getPortaSmtp());*/
                envioEmail = 1;

               //Creating a new session
               session = Session.getDefaultInstance(props,
                       new javax.mail.Authenticator() {
                           //Authenticating the password
                           protected PasswordAuthentication getPasswordAuthentication() {
                               return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                           }
                       });

               try {
                   //Creating MimeMessage object
                   MimeMessage mm = new MimeMessage(session);
                   pastaSatflex = new File(Environment.getExternalStorageDirectory() + "/satflex/xmlcontador/xml.zip");

                   MimeBodyPart mbp1 = new MimeBodyPart();
                   MimeBodyPart mbp2 = new MimeBodyPart();
                   mbp1.setText(message);
                   Multipart mp = new MimeMultipart();
                   // anexa o arquivo na mensagem
                   if (pastaSatflex.exists()) {
                       FileDataSource fds = new FileDataSource(pastaSatflex);
                       mbp2.setDataHandler(new DataHandler(fds));
                       mbp2.setFileName(fds.getName());

                       mp.addBodyPart(mbp2);
                   } else {
                       Toast.makeText(context, "erro ", Toast.LENGTH_LONG).show();
                   }
                   mp.addBodyPart(mbp1);

                   //Setting sender address
                   mm.setFrom(new InternetAddress(Config.EMAIL));
                   //Adding receiver
                   mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                   //Adding subject
                   mm.setSubject(subject);
                   mm.setContent(mp);

                   //Sending email
                   Transport.send(mm);
                   if(mainActivity.objparam.getSatModelo().equalsIgnoreCase("2")) {
                       ativarEthSat();
                   }

               } catch (MessagingException e) {
                   e.printStackTrace();
               }
           }
else{

                   envioEmail=0;
               if(mainActivity.objparam.getSatModelo().equalsIgnoreCase("2")) {
                   ativarEthSat();
               }
               }



                return null;

        }
    public void ativarEthSat() {

                try {
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig wlan0 down"});
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 down"});
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 down"});
                    //   Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});

                } catch (
                        IOException e) {
                    e.printStackTrace();
                }

                pausa();
                if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth0")) {
                    try {

                        Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});

                    } catch (
                            IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth1")) {
                        try {

                            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});

                        } catch (
                                IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

    }
    private void desativarEthSat(){
        if(mainActivity.objparam.getSatModelo().equalsIgnoreCase("2")){

            if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth0")) {
                try{
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig wlan0 up"});
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 up"});
                    Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 down"});
                    pausa();
                } catch(
                        IOException e)

                {
                    e.printStackTrace();
                }}
            else{
                if (mainActivity.objparam.getEthLocal().equalsIgnoreCase("eth1")) {
                    try{
                        Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig wlan0 up"});
                        Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth1 up"});
                        Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "ifconfig eth0 down"});
                        pausa();
                    } catch(
                            IOException e)

                    {
                        e.printStackTrace();
                    }
                }}}}
    public void pausa() {
        try {
            TimeUnit.SECONDS.sleep(15   );

        } catch (InterruptedException ex) {
            // Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
