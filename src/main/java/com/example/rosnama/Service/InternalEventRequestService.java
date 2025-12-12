package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.*;
import com.example.rosnama.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalEventRequestService {

    private final InternalEventRequestRepository internalEventRequestRepository;
    private final AdminRepository adminRepository;
    private final EventOwnerRepository eventOwnerRepository;
    private final InternalEventRepository internalEventRepository;
    private final NotificationService notificationService;



    public List<InternalEventRequest>getAllEventsRequest(Integer adminId){
        Admin admin =adminRepository.findAdminById(adminId);
        if(admin == null){
            throw new ApiException("Admin not found");
        }
        return internalEventRequestRepository.findAll();
    }


    public void offerPrice(Integer requestId, Double price){

        InternalEventRequest request = internalEventRequestRepository.findInternalEventRequestById(requestId);
        if(request == null)
            throw new ApiException("event request not found");

        // check staus not offered
        if ( request.getStatus().equals("Offered")) {
            throw new ApiException("event request already offered");
        }

        request.setPrice(price);
        request.setStatus("Offered");
        internalEventRequestRepository.save(request);

        InternalEvent event = request.getInternalEvent();
        //notify owner that his event's status is offered
        List<Admin> admins = adminRepository.findAll();
        admins.forEach(admin ->
        notificationService.notify(
                event.getEventOwner().getEmail(),
                event.getEventOwner().getPhone(),
                "Event Price Offer",
                event.getEventOwner().getUsername(),
                "Your event:\n" + event.getTitle() +"\nhas been offered a price of: " + price + " SAR. Please review and respond."
        ));
    }




    public void negotiates(Integer ownerId, Integer requestId, Double price){
        InternalEventRequest eventRequest = internalEventRequestRepository.findInternalEventRequestById(requestId);
        if(eventRequest == null)
            throw new ApiException("event request not found");

        EventOwner eventOwner = eventRequest.getInternalEvent().getEventOwner();
        if(!eventOwner.getId().equals(ownerId))
            throw new ApiException("access denied");

        if(!eventRequest.getStatus().equals("Offered"))
            throw new ApiException("event request waits for offer");

        eventRequest.setPrice(price);
        eventRequest.setStatus("Requested");
        internalEventRequestRepository.save(eventRequest);

        List<Admin> admins = adminRepository.findAll();
        //notify admin that owner negotiates the offer
        admins.forEach( admin ->
        notificationService.notify(
                admin.getEmail(),
                admin.getPhoneNumber(),
                "Event Price Negotiation",
                "Admin",
                "Event:\n"+ eventRequest.getInternalEvent().getTitle() +"\nowner has proposed a new price: " + price + " SAR."
        ));
    }



    public void acceptOfferAndPay(Integer ownerId, Integer requestId){
        InternalEventRequest request = internalEventRequestRepository.findInternalEventRequestById(requestId);
        if(request == null)
            throw new ApiException("request not found");
        EventOwner eventOwner = request.getInternalEvent().getEventOwner();
        if(!eventOwner.getId().equals(ownerId))
            throw new ApiException("access denied");

        if(!(eventOwner.getBalance() >= request.getPrice()))
            throw new ApiException("event owner doesn't have enough money");

        eventOwner.setBalance(eventOwner.getBalance()-request.getPrice());
        eventOwnerRepository.save(eventOwner);

        InternalEvent event = request.getInternalEvent();

        event.setStatus("Active");
        event.setInternalEventRequest(null);
        internalEventRepository.save(request.getInternalEvent());
        internalEventRequestRepository.delete(request);

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
        admins.forEach(admin ->
        notificationService.notify(
                admin.getEmail(),
                admin.getPhoneNumber(),
                "Event Price Negotiation",
                "Admin",
                "Event:\n"+ event.getTitle() +"\nowner has accepted and paid the offered price of: " + request.getPrice() + " SAR.\n The event:\n"+ event.getTitle() +"is now activated."
        ));
    }





}
