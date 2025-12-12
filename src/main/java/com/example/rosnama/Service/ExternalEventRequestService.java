package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.ExternalEvent;
import com.example.rosnama.Model.ExternalEventRequest;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalEventRequestService {

    private final ExternalEventRequestRepository externalEventRequestRepository;
    private final AdminRepository adminRepository;
    private final EventOwnerRepository eventOwnerRepository;
    private final ExternalEventRepository externalEventRepository;
    private final NotificationService notificationService;
    
    public List<ExternalEventRequest> getExternalEventRequests(Integer adminId){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");
        return externalEventRequestRepository.findAll();
    }

    public void offerPrice(Integer adminId, Integer requestId, Double price){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");

        ExternalEventRequest request = externalEventRequestRepository.findExternalEventRequestById(requestId);
        if(request == null)
            throw new ApiException("event request not found");

        // check staus not offered
        if (!request.getStatus().equals("Requested")) {
            throw new ApiException("event request already offered");
        }

        request.setPrice(price);
        request.setStatus("Offered");
        externalEventRequestRepository.save(request);

        ExternalEvent event = request.getExternalEvent();
        //notify owner that his event's status is offered
        notificationService.notify(
                event.getEventOwner().getEmail(),
                event.getEventOwner().getPhone(),
                "Event Price Offer",
                event.getEventOwner().getUsername(),
                "Your event:\n" + event.getTitle() +"\nhas been offered a price of: " + price + " SAR. Please review and respond."
        );
    }

    public void negotiates(Integer ownerId, Integer requestId, Double price, Integer adminId){
        ExternalEventRequest eventRequest = externalEventRequestRepository.findExternalEventRequestById(requestId);
        if(eventRequest == null)
            throw new ApiException("event request not found");

        if(!eventRequest.getStatus().equals("Offered"))
            throw new ApiException("event request waits for offer");

        EventOwner eventOwner = eventRequest.getExternalEvent().getEventOwner();
        if(!eventOwner.getId().equals(ownerId))
            throw new ApiException("access denied");

        eventRequest.setPrice(price);
        eventRequest.setStatus("Requested");

        externalEventRequestRepository.save(eventRequest);

        List<Admin> admins = adminRepository.findAll();
        //notify admin that owner negotiates the offer
        admins.forEach( admin ->
        notificationService.notify(
                admin.getEmail(),
                admin.getPhoneNumber(),
                "Event Price Negotiation",
                "Admin",
                "Event:\n"+ eventRequest.getExternalEvent().getTitle() +"\nowner has proposed a new price: " + price + " SAR."
        ));

    }

    public void acceptOfferAndPay(Integer ownerId, Integer requestId , Integer adminId){
        ExternalEventRequest request = externalEventRequestRepository.findExternalEventRequestById(requestId);
        if(request == null)
            throw new ApiException("request not found");

        ExternalEvent event = request.getExternalEvent();

        EventOwner eventOwner = event.getEventOwner();
        if(!eventOwner.getId().equals(ownerId))
            throw new ApiException("access denied");

        if(! (eventOwner.getBalance() >= request.getPrice()))
            throw new ApiException("event owner doesn't have enough money");


        eventOwner.setBalance(eventOwner.getBalance()-request.getPrice());
        eventOwnerRepository.save(eventOwner);

        event.setStatus("Upcoming");
        event.setExternalEventRequest(null);

        externalEventRepository.save(event);
        externalEventRequestRepository.delete(request);

        //notify owner that his event is activated
        notificationService.notify(
                eventOwner.getEmail(),
                eventOwner.getPhone(),
                "Event Price Negotiation",
                eventOwner.getUsername(),
                "You have accepted and paid the offered price of: " + request.getPrice() + " SAR.\n Your event:\n"+ event.getTitle() +"is now activated."
        );

        List<Admin> admins = adminRepository.findAll();
        //notify admin that his offer accepted and paid and the event activated
        admins.forEach( admin ->
        notificationService.notify(
                admin.getEmail(),
                admin.getPhoneNumber(),
                "Event Price Negotiation",
                "Admin",
                "Event:\n"+ event.getTitle() +"\nowner has accepted and paid the offered price of: " + request.getPrice() + " SAR.\n The event:\n"+ event.getTitle() +"is now activated."
        ));

    }


}
