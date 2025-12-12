package com.example.rosnama.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;
    @Value("${ultramsg.token}")
    private String token;

    @Value("${ultramsg.instance-id}")
    private String instance;

    public void notify(String email, String phone, String subject, String name, String msg) {
        String body = createBody(name, msg);
        sendEmail(email, subject, body);
        sendWhatsapp(phone, subject, body);
    }

    private void sendEmail(String toEmail,
                          String subject, String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("ibra9v1221@gmail.com");
        message.setSubject(subject);
        message.setTo(toEmail);
        message.setText(body);
        mailSender.send(message);
    }

    private void sendWhatsapp(String phone , String subject, String body){

        HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/"+instance+"/messages/chat")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", token)
                .field("to","+"+phone )
                .field("body",subject + "\n\n" + body)
                .asString();
        System.out.println(response.getBody());
    }

    private String createBody(String name, String msg){
        return
                """
                Hello %s,

                %s

                Regards,
                Rosnama Team
                """
                        .formatted(name,msg);
    }

}
