package serviceLayer.controllers;

import serviceLayer.controllers.interfaces.EmailControllerInterface;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import serviceLayer.exceptions.PolygonException;

public class EmailController implements EmailControllerInterface{
    
    /**
     * Method used to send emails
     * 
     * @param to String containing the email of the recieving person
     * @param title String contining the title of the email
     * @param body String containing the content of the email
     * @throws PolygonException throws the exception
     */
    public void send(String to, String title, String body) throws PolygonException {

        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // use for gmail: smtp.gmail.com
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    //return new PasswordAuthentication("datamatikermail@gmail.com", "datamatikermail123");
                    return new PasswordAuthentication("polygonmailtest4@gmail.com", "polygon1234");
                }
            });

            //Enable the debug mode
            mailSession.setDebug(true); 

            Message msg = new MimeMessage(mailSession);

            //Set the FROM, TO, DATE and SUBJECT fields
            msg.setFrom(new InternetAddress("polygonmailtest4@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSentDate(new Date());
            msg.setSubject(title);

            //Create the body of the mail
            msg.setText(body);

            //Ask the Transport class to send our mail message
            Transport.send(msg);

        } catch (Exception e) {
            
            throw new PolygonException("Exception:@EmailController:"+e.getMessage());
            
        }
        
    }

}


