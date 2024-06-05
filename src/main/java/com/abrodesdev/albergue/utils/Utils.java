package com.abrodesdev.albergue.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {

    // Metodo para enviar un email con la nueva contraseña
    public String sendEmail(String to) throws MessagingException {
        // Configuracion del servidor de correo
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "");
        prop.put("mail.smtp.port", "");
        prop.put("mail.smtp.ssl.trust", "s");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(""));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Tu nueva contraseña");
        // Generar una contraseña aleatoria
        String pass = getRandomSpecialChars(15).map(Object::toString).reduce("", String::concat);

        String msg = "Tu nueva contraseña es: " + pass;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
        return pass;
    }

    // Metodo para generar una contraseña aleatoria
    public Stream<Character> getRandomSpecialChars(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 33, 122);
        return specialChars.mapToObj(data -> (char) data);
    }

    // Metodo para decodificar el header de autorizacion
    public String[] decodeAuth(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
            String[] credentials = decodedCredentials.split(":", 2);
            if (credentials.length == 2) {
                return credentials;
            }
        }
        return null;
    }
}
