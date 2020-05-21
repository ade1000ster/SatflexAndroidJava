package com.example.ademirestudo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Properties;

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


public class EnviarEmail extends AsyncTask<Void,Void,Void> {

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
        public EnviarEmail(Context context, String email, String subject, String message){
            //Initializing variables
            this.context = context;
            this.email = email;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Showing progress dialog while sending email
            progressDialog = ProgressDialog.show(context,"Enviando E-mail para contador","Aguarde por favor...",true,true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Dismissing the progress dialog
            progressDialog.dismiss();
            //Showing a success message
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

        @Override
        protected Void doInBackground(Void... params) {
            //Creating properties
            Properties props = new Properties();
            //props.put("mail.smtp.host", "smtp.gmail.com");
            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.remarca-automacao.com.br");
            props.put("mail.smtp.socketFactory.port", "587");
           props.put("mail.smtp.socketFactory.class", "false");//javax.net.ssl.SSLSocketFactory
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");


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
                pastaSatflex = new File(Environment.getExternalStorageDirectory() +"/satflex/xmlcontador/xml.zip");

                MimeBodyPart mbp1 = new MimeBodyPart();
                MimeBodyPart mbp2 = new MimeBodyPart();
                mbp1.setText(message);
                Multipart mp = new MimeMultipart();
                // anexa o arquivo na mensagem
                if ( pastaSatflex.exists()){
                FileDataSource fds = new FileDataSource(pastaSatflex );
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());

                mp.addBodyPart(mbp2);}
                else{
                    Toast.makeText(context,"erro ",Toast.LENGTH_LONG).show();
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

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
}
