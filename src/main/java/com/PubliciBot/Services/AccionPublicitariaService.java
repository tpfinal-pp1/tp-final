package com.PubliciBot.Services;

import com.PubliciBot.DM.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by Max on 6/13/2017.
 */
public class AccionPublicitariaService {


    public AccionPublicitariaService() {

    }


    public boolean publicar(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        TipoMedio tipo = accionPublicitaria.getMedio().getTipoMedio();

        switch (tipo) {
            case EMAIL:
                return enviarMail(accionPublicitaria, mensajeLocal);


            case TWITTER:
                return false;


        }
        return false;
    }


    private boolean enviarMail(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("megafonomailer", "IvoVirginia2017");

                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("megafonomailer@gmail.com"));

            if(mensajeLocal.getAsunto()==null || mensajeLocal.getAsunto().equals(""))
                message.setSubject("Publicibot Mailer te envia una mensaje!");
            else{
                message.setSubject(mensajeLocal.getAsunto());
            }

            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(accionPublicitaria.getDestino()));

            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("megafonomailer@gmail.com"));

            /////////////////////////////// MAIL HTML CON IMAGEN  ///////////////////////////////

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>" + mensajeLocal.getTextoMensaje() + "<img src=\"cid:image\">";
            try {
                messageBodyPart.setContent(htmlText, "text/html");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            // add it
            multipart.addBodyPart(messageBodyPart);

            if (mensajeLocal.getImagenMensajePath() != null && mensajeLocal.getImagenMensajePath().trim() != "") {

                DataSource fds = new FileDataSource(mensajeLocal.getImagenMensajePath());

                if(Utils.isExistFile(mensajeLocal.getImagenMensajePath()))
                {
                    // second part (the image)
                    messageBodyPart = new MimeBodyPart();

                    messageBodyPart.setDataHandler(new DataHandler(fds));
                    messageBodyPart.setHeader("Content-ID", "<image>");

                    // add image to the multipart
                    multipart.addBodyPart(messageBodyPart);
                }
            }

            // put everything together
            message.setContent(multipart);

            ///////////////////////////////////////////////////////////////////////////////////////

            /////////////////////////////// MAIL COMUN - SOLO TEXTO ///////////////////////////////

            //message.setText("Girls!," +
            //		"\n\n The Megafono mailer is now running! Welcome onboard");
            ///////////////////////////////////////////////////////////////////////////////////////

            Transport.send(message);

            System.out.println("Enviado");
            return true;

        } catch (MessagingException e) {

            System.out.println("Error de Envio:" + e);
            return false;


        }
    }

    private boolean enviarTelegram(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        return false;
    }

    private boolean enviarWhatsApp(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        return false;
    }

    private boolean postearFacebook(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        return false;
    }

    private boolean postearLinkedIn(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        return false;
    }

    private boolean enviarTwitter(AccionPublicitaria accionPublicitaria, Mensaje mensajeLocal) {
        return false;
    }


    public AccionPublicitaria crearAccionStub(String destino) {
        return new AccionPublicitaria("TEST", 0, new Medio(), destino);
    }


}
