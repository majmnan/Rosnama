package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WhatsAppTemplateService {

    @Value("${twilio.whatsapp-from}")
    private String from;


    public void sendEventTemplate(
            String to,
            String eventTitle,
            String date,
            String time
    ) {
        try {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + to),
                    new PhoneNumber("whatsapp:+14155238886"),
                    "Hello User \n\nThis is a TEST message from Rosnama.\n\nIf you see this, WhatsApp works "
            ).create();

            log.info("WhatsApp sandbox message sent. SID={}", message.getSid());

        } catch (Exception e) {
            log.error("Failed to send WhatsApp message", e);
            throw new ApiException("Failed to send WhatsApp notification");
        }
    }

}
