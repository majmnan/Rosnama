package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.EventOwner;
import com.example.rosnama.Model.EventRequest;
import com.example.rosnama.Repository.AdminRepository;
import com.example.rosnama.Repository.EventOwnerRepository;
import com.example.rosnama.Repository.EventRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventRequestService {
    private final EventRequestRepository eventRequestRepository;
    private final AdminRepository adminRepository;
    private final EventOwnerRepository eventOwnerRepository;

    public List<EventRequest> getEventRequests(Integer adminId){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");
        return eventRequestRepository.findAll();
    }

    //add

    public void offerPrice(Integer adminId, Integer requestId, Double price){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null)
            throw new ApiException("admin not found");

        EventRequest request = eventRequestRepository.findEventRequestById(requestId);
        if(request == null)
            throw new ApiException("event request not found");

        request.setPrice(price);
        request.setStatus("Offered");
    }

    public void negotiates(Integer ownerId, Integer requestId, Double price){
        EventRequest eventRequest = eventRequestRepository.findEventRequestById(requestId);
        if(eventRequest == null)
            throw new ApiException("event request not found");

        if(!eventRequest.getStatus().equals("Offered"))
            throw new ApiException("event request waits for offer");

        EventOwner eventOwner = eventRequest.getEvent()//.getOwner();
        if(!eventOwner.getId().equals(ownerId))
            throw new ApiException("access denied");


        eventRequest.setPrice(price);
        eventRequest.setStatus("Requested");
    }
}
