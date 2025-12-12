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
    }

    public void negotiates(Integer ownerId, Integer requestId, Double price){
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
    }

    public void acceptOfferAndPay(Integer ownerId, Integer requestId){
        ExternalEventRequest request = externalEventRequestRepository.findExternalEventRequestById(requestId);
        if(request == null)
            throw new ApiException("request not found");
        EventOwner eventOwner = request.getExternalEvent().getEventOwner();
        if(!eventOwner.getId().equals(ownerId))
            throw new ApiException("access denied");

        if(! (eventOwner.getBalance() >= request.getPrice()))
            throw new ApiException("event owner doesn't have enough money");


        eventOwner.setBalance(eventOwner.getBalance()-request.getPrice());
        eventOwnerRepository.save(eventOwner);

        ExternalEvent externalEvent = request.getExternalEvent();
        externalEvent.setStatus("Upcoming");
        externalEvent.setExternalEventRequest(null);

        externalEventRepository.save(request.getExternalEvent());
        externalEventRequestRepository.delete(request);

        // sending email to event owner
        notificationService.notifyOwnerPaymentSuccess(
                eventOwner,
                externalEvent.getTitle(),
                request.getPrice()
        );
    }


}
