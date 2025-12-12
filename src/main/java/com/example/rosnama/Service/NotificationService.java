package com.example.rosnama.Service;

import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final WhatsAppService whatsAppService;

    // this between admin  event owner
    public void notifyOwnerPaymentSuccess(EventOwner owner, String eventTitle, Double amount) {
        emailService.sendEmail(
                owner.getEmail(),
                "Payment Successful – Event Published",
                """
                Dear %s,

                Your payment for the event "%s" has been completed successfully.
                Amount Paid: %.2f SAR
                Event Status: ACTIVE

                Regards,
                Rosnama Team
                """.formatted(owner.getUsername(), eventTitle, amount)
        );
    }

    // this is for admin and user
    public void notifyUserRegistrationSuccess(User user, String eventTitle, String date) {
        whatsAppService.whatsapp(user.getPhoneNumber(),eventTitle+"Registration Success");
    }

    // this is for admin  to event owner
    public void notifyOwnerStatusChange(EventOwner owner, String eventTitle, String newStatus) {
        emailService.sendEmail(
                owner.getEmail(),
                "Event Status Updated",
                """
                Hello %s,

                Your event "%s" status has been updated to: %s.

                Regards,
                Rosnama Team
                """.formatted(owner.getUsername(), eventTitle, newStatus)
        );
    }


}
