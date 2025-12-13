package com.example.rosnama.Service;

import kong.unirest.HttpResponse;
import kong.unirest.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${ultramsg.token}")
    private String token;

    @Value("${ultramsg.instance-id}")
    private String instance;


    public void whatsapp(String phone , String msg){

        HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/"+instance+"/messages/chat")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("token", token)
                .field("to","+"+phone )
                .field("body", msg)

                .asString();
        System.out.println(response.getBody());
    }

}
