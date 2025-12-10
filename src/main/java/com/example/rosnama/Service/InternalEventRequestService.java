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



    public List<InternalEventRequest>getAllEventsRequest(Integer admin_id){
        Admin admin =adminRepository.findAdminById(admin_id);
        if(admin == null){
            throw new ApiException("Admin not found");
        }
        return internalEventRequestRepository.findAll();
    }


    public void offerPrice(Integer adminId, Integer requestId, Double price){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");

        InternalEventRequest request = internalEventRequestRepository.findInternalEventRequestById(requestId);
        if(request == null)
            throw new ApiException("event request not found");

        request.setPrice(price);
        request.setStatus("Offered");
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

    }




}
